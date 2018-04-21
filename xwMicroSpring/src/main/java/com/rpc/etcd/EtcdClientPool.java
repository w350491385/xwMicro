package com.rpc.etcd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by huangdongbin on 2018/3/26.
 */
public class EtcdClientPool {

    private static Logger logger = LoggerFactory.getLogger(EtcdClientPool.class);
    private volatile int max = 5;
    private ConcurrentLinkedQueue<EtcdClientConnection> pools = new ConcurrentLinkedQueue();
    private EtcdClientConnection etcdClientConnection = null;
    private volatile int poolCount = 0;
    private String baseUri;

    public EtcdClientPool(String baseUri) throws URISyntaxException {
        this.baseUri = baseUri;
    }

    public EtcdClientPool(String baseUri, int max) throws URISyntaxException {
        this.max = max;
        this.pools = new ConcurrentLinkedQueue<>();
        this.baseUri = baseUri;
    }

    public EtcdClientConnection getConnection(int loop) {
        EtcdClientConnection etcdClientConnection = null;
        try {
            if ((etcdClientConnection = this.pools.poll()) != null)
                return etcdClientConnection;
            synchronized (this) {
                logger.info(this + " max is {},poolCount is {}", max, poolCount);
                if (this.max > this.poolCount) {
                    etcdClientConnection = createConnection();
                    this.poolCount++;
                } else {
                    if (loop > 0) {
                        Thread.sleep(200);//太快睡眠一下,在拿一把
                        return getConnection(loop - 1);
                    } else
                        throw new Exception("不能获取到 etcd 连接");
                }
            }
        } catch (Exception e) {
            logger.error("{}", e);
        }
        return etcdClientConnection;
    }

    private EtcdClientConnection createConnection() throws URISyntaxException {
        return new EtcdClientConnection(baseUri, this).get();
    }

    public void add(EtcdClientConnection clientConnection) {
        this.pools.add(clientConnection);
//        logger.info("-----releaseConnection-----" + this.pools.size());
    }
}
