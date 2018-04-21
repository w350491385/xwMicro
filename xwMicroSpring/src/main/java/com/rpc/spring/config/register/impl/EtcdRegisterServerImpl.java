package com.rpc.spring.config.register.impl;

import com.rpc.etcd.EtcdClientConnection;
import com.rpc.etcd.EtcdClientPoolFactory;
import com.rpc.etcd.EtcdStructData;
import com.rpc.etcd.listener.ListenerEnum;
import com.rpc.etcd.listener.OtherEtcdListener;
import com.rpc.etcd.listener.RegisterServerEtcdListener;
import com.rpc.spring.config.register.AbstractRegisterServer;
import com.rpc.spring.config.register.EtcdListener;
import com.rpc.spring.config.register.RegisterServer;
import com.rpc.spring.constant.Constant;
import com.rpc.util.NamedThreadFactory;
import mousio.client.promises.ResponsePromise;
import mousio.etcd4j.responses.EtcdKeysResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by huangdongbin on 2018/3/21.
 */
public class EtcdRegisterServerImpl extends AbstractRegisterServer implements RegisterServer {
    private ScheduledExecutorService retryScheduledExecutorService = Executors.newScheduledThreadPool(1, new NamedThreadFactory("registerThreadPool", true));
    private ScheduledExecutorService ttlScheduledExecutorService = Executors.newScheduledThreadPool(1, new NamedThreadFactory("registerThreadPool_ttl", true));
    private ConcurrentHashMap<String, EtcdListener> callBackMap = new ConcurrentHashMap<>(200);
    private static Logger logger = LoggerFactory.getLogger(EtcdRegisterServerImpl.class);
    private EtcdClientPoolFactory etcdClientPoolFactory = null;
    private final static int loop = 3;
    private Lock lock = new ReentrantLock();
    private ConcurrentHashMap<String, List<EtcdStructData>> ttlTempDataMap = new ConcurrentHashMap(200);
    private List<EtcdStructData> failData = new ArrayList<>();
    private volatile boolean destory = false;//

    public void destroy(){
        try {
            lock.lock();
            if (!destory){
                destory = true;
                callBackMap = null;
                ttlTempDataMap = null;
                retryScheduledExecutorService.shutdown();
                ttlScheduledExecutorService.shutdown();
            }
        }finally {
            lock.unlock();
        }
    }

    public EtcdRegisterServerImpl(String baseUri, int max, String group) {
        this.etcdClientPoolFactory = EtcdClientPoolFactory.getInstance().build(baseUri, max);
        startThreadPool();
        startTtlThreadPool();
        initSystemConfigure(group);
    }

    public EtcdRegisterServerImpl(String baseUri, String group) {
        this.etcdClientPoolFactory = EtcdClientPoolFactory.getInstance().build(baseUri);
        startThreadPool();
        startTtlThreadPool();
        initSystemConfigure(group);
    }

    private void initSystemConfigure(String group) {
        EtcdStructData etcdStructData = new EtcdStructData(ListenerEnum.OTHER.getDir(group), "", "", false);
        putDir(etcdStructData, ListenerEnum.OTHER.getInstance());
    }

    private void startTtlThreadPool() {
        ttlScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        }, Constant.SECOND / 2, Constant.SECOND / 2, TimeUnit.SECONDS);
    }

    private void startThreadPool() {
        retryScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                retry();
            }
        }, 2, 2, TimeUnit.SECONDS);
    }

    private void refresh() {
        if (ttlTempDataMap != null && ttlTempDataMap.size() != 0) {
            ConcurrentHashMap<String, List<EtcdStructData>> tempTtlMap = new ConcurrentHashMap<>(ttlTempDataMap);
            for (Map.Entry<String, List<EtcdStructData>> entrySet : tempTtlMap.entrySet()) {
                try (EtcdClientConnection pool = etcdClientPoolFactory.getEtcdClientPool().getConnection(loop)) {
                    if (entrySet.getValue() != null && entrySet.getValue().size() > 0) {
                        String dir = entrySet.getKey();
                        int ttl = entrySet.getValue().get(0).getTtl();
                        pool.refresh(dir, ttl);
                        logger.debug("{} refresh  ttl is {}", dir, ttl);
                    }
                } catch (Exception e) {
                    logger.error("", e);
                    failData.addAll(entrySet.getValue());
                }
            }
        }
    }

    //重试
    private void retry() {
        if (failData != null && failData.size() != 0) {
            ArrayList<EtcdStructData> tempFailList = new ArrayList(failData);
            for (EtcdStructData data : tempFailList) {
                logger.debug("{} retry register", data.getKey());
                putDirAndProperty(data,ListenerEnum.REGISTER_SERVER.getInstance());
                failData.remove(data);
            }
        }
    }

    @Override
    public void putDir(EtcdStructData etcdStructData,EtcdListener etcdListener) {
        try (EtcdClientConnection pool = etcdClientPoolFactory.getEtcdClientPool().getConnection(loop)) {
            lock.lock();
            addTTLData(etcdStructData);
            if (!callBackMap.containsKey(etcdStructData.getDir())) {
                if (etcdStructData.isTemp()) {
                    pool.putDir(etcdStructData.getDir(), etcdStructData.getTtl());
                } else {
                    pool.putDir(etcdStructData.getDir());
                }
            }
            super.put(etcdStructData.getDir(), null, null);
            watchDir(pool, etcdStructData,etcdListener);
        } catch (Exception e) {
            logger.error("", e);
            if (!failData.contains(etcdStructData))
                failData.add(etcdStructData);
        } finally {
            lock.unlock();
        }
    }

    private void addTTLData(EtcdStructData etcdStructData) {
        if (etcdStructData.isTemp()) {
            List<EtcdStructData> dataList = ttlTempDataMap.get(etcdStructData.getDir());
            if (dataList == null) {
                dataList = new ArrayList<>();
                ttlTempDataMap.put(etcdStructData.getDir(), dataList);
            }
            if (!dataList.contains(etcdStructData)) {
                dataList.add(etcdStructData);
                Collections.sort(dataList);
            }
        }
    }

    @Override
    public void putDirAndProperty(EtcdStructData etcdStructData,EtcdListener etcdListener) {
        try (EtcdClientConnection pool = etcdClientPoolFactory.getEtcdClientPool().getConnection(loop)) {
            lock.lock();
            addTTLData(etcdStructData);
            if (!callBackMap.containsKey(etcdStructData.getDir())) {
                if (etcdStructData.isTemp()) {
                    pool.putDir(etcdStructData.getDir(), etcdStructData.getTtl());
                } else {
                    pool.putDir(etcdStructData.getDir());
                }
            }
            pool.putProperty(etcdStructData.getKey(), etcdStructData.getValue());
            super.put(etcdStructData.getDir(), etcdStructData.getKey(), etcdStructData.getValue());
            watchDir(pool, etcdStructData,etcdListener);
        } catch (Exception e) {
            logger.error("", e);
            if (!failData.contains(etcdStructData))
                failData.add(etcdStructData);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Map<Object, Object> getDir(String dir) {
        try (EtcdClientConnection pool = etcdClientPoolFactory.getEtcdClientPool().getConnection(loop)) {
            return pool.getDir(dir);
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    @Override
    public void deleteDir(String dir) {
        try (EtcdClientConnection pool = etcdClientPoolFactory.getEtcdClientPool().getConnection(loop)) {
            pool.deleteDir(dir);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    @Override
    public void wtachDir(String dir,EtcdListener etcdListener) {
        try (EtcdClientConnection pool = etcdClientPoolFactory.getEtcdClientPool().getConnection(loop)) {
            watchDir(pool, new EtcdStructData(dir, dir, dir, true),etcdListener);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    @Override
    public void delete(String key) {
        try (EtcdClientConnection pool = etcdClientPoolFactory.getEtcdClientPool().getConnection(loop)) {
            pool.delete(key);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    private void watchDir(EtcdClientConnection pool, EtcdStructData etcdStructData,EtcdListener listener) {
        String dir = etcdStructData.getDir();
        EtcdListener callBack = callBackMap.get(dir);
        if (callBack == null) {
            callBack = listener;
            callBackMap.put(dir, callBack);
        }
        pool.watchDir(dir, callBack);
    }
}
