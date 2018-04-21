package com.rpc.etcd;

import com.rpc.spring.config.register.EtcdListener;
import mousio.client.promises.ResponsePromise;
import mousio.client.retry.RetryNTimes;
import mousio.etcd4j.EtcdClient;
import mousio.etcd4j.promises.EtcdResponsePromise;
import mousio.etcd4j.responses.EtcdAuthenticationException;
import mousio.etcd4j.responses.EtcdException;
import mousio.etcd4j.responses.EtcdKeysResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by huangdongbin on 2018/3/26.
 */
public class EtcdClientConnection implements AutoCloseable {
    private static Logger logger = LoggerFactory.getLogger(EtcdClientConnection.class);
    private static final int connect_timeout_second = 5;
    private EtcdClient etcdClient = null;
    private URI[] baseUri = null;
    private EtcdClientPool pool = null;

    public EtcdClientConnection(String baseUri, EtcdClientPool pool) throws URISyntaxException {
        this.pool = pool;
        buildEtcdClient(baseUri);
    }

    private void buildEtcdClient(String baseUri) throws URISyntaxException {
        String[] baseUris = baseUri.split("/;");
        int length = baseUris.length;
        this.baseUri = new URI[length];
        for (int index = 0; index < length; index++) {
            this.baseUri[index] = new URI(baseUris[index]);
        }
    }

    public EtcdClientConnection get() {
        this.etcdClient = new EtcdClient(baseUri);
        etcdClient.setRetryHandler(new RetryNTimes(200, 5));//fail connect，每隔200ms Retry，最多Reconnect 5 times.
        return this;
    }

    public void putDir(String dir) throws Exception {
        try {
            etcdClient.getDir(dir).recursive().timeout(connect_timeout_second, TimeUnit.SECONDS).send().get();
            etcdClient.putDir(dir).prevExist(true).timeout(connect_timeout_second, TimeUnit.SECONDS).send().get();
        } catch (EtcdException | EtcdAuthenticationException e) {
//            logger.error("",e);
            try {
                etcdClient.putDir(dir).prevExist(false).timeout(connect_timeout_second, TimeUnit.SECONDS).send().get();
            } catch (Exception e1) {
                throw e1;
            }
        }
    }

    public void putProperty(String key, String value) throws Exception {
        try {
            etcdClient.get(key).timeout(connect_timeout_second, TimeUnit.SECONDS).send().get();
            etcdClient.put(key, value).prevExist(true).timeout(connect_timeout_second, TimeUnit.SECONDS).send().get();
        } catch (EtcdException | EtcdAuthenticationException e) {
//            logger.error("",e);
            try {
                etcdClient.put(key, value).prevExist(false).timeout(connect_timeout_second, TimeUnit.SECONDS).send().get();
            } catch (Exception e1) {
                throw e1;
            }
        }
    }

    /**
     * 创建持续ttl秒的目录
     *
     * @param dir
     * @param ttl 持续存在 秒钟
     */
    public void putDir(String dir, int ttl) throws Exception {
        try {
            etcdClient.getDir(dir).recursive().timeout(connect_timeout_second, TimeUnit.SECONDS).send().get();
            etcdClient.putDir(dir).prevExist(true).ttl(ttl).timeout(connect_timeout_second, TimeUnit.SECONDS).send().get();
        } catch (EtcdException | EtcdAuthenticationException e) {
//            logger.error("",e);
            try {
                etcdClient.putDir(dir).prevExist(false).ttl(ttl).timeout(connect_timeout_second, TimeUnit.SECONDS).send().get();
            } catch (Exception e1) {
                throw e1;
            }
        }
    }

    public void refresh(String dir, int ttl) throws Exception {
        etcdClient.refresh(dir, ttl).timeout(connect_timeout_second, TimeUnit.SECONDS).send().get();
    }

    public void deleteDir(String dir) throws Exception {
        etcdClient.deleteDir(dir).timeout(connect_timeout_second, TimeUnit.SECONDS).send().get();
    }

    public void delete(String key) throws Exception {
        etcdClient.delete(key).timeout(connect_timeout_second, TimeUnit.SECONDS).send().get();

    }

    public Map<Object, Object> getDir(String dir) {
        Map<Object, Object> result = new HashMap<>();
        try {
            EtcdKeysResponse response = etcdClient.getDir(dir).recursive().timeout(connect_timeout_second, TimeUnit.SECONDS).send().get();
            List<EtcdKeysResponse.EtcdNode> list = response.node.nodes;
            Object K, V;
            for (EtcdKeysResponse.EtcdNode item : list) {
                K = item.key.replace(dir, "").replace("//", "");
                V = item.value;
                result.put(K, V);
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return result;
    }

    public void watchDir(final String dir, final EtcdListener listener) {
        try {
            EtcdResponsePromise<EtcdKeysResponse> promise = etcdClient.getDir(dir).recursive().waitForChange().send();
            promise.addListener(new ResponsePromise.IsSimplePromiseResponseHandler<EtcdKeysResponse>() {
                @Override
                public void onResponse(ResponsePromise<EtcdKeysResponse> response) {
                    try {
                        logger.info("dir:" + dir + " change.");
                        if (listener != null)
                            listener.notify(dir, response);
                        logger.info("dir:" + dir + " process change completed.");
                        watchDir(dir, listener);
                    } catch (Exception e) {
                        logger.error("dir:" + dir + " watch failed.", e);
                    }
                }
            });
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    @Override
    public void close() throws Exception {
        if (this.etcdClient != null) {
            this.pool.add(this);
        }
    }
}
