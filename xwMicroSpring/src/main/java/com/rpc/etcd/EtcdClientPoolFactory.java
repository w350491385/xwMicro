package com.rpc.etcd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;

/**
 * Created by huangdongbin on 2018/3/26.
 */
public class EtcdClientPoolFactory {

    private static Logger logger = LoggerFactory.getLogger(EtcdClientPoolFactory.class);
    private EtcdClientPool pool = null;
    private final  static  EtcdClientPoolFactory instance = new EtcdClientPoolFactory();

    private EtcdClientPoolFactory(){}

    public static EtcdClientPoolFactory getInstance(){
        return  instance;
    }

    public synchronized EtcdClientPoolFactory build(String baseUri){
        try {
            if (pool == null)
                pool = new EtcdClientPool(baseUri);
        } catch (URISyntaxException e) {
            logger.error("",e);
        }
        return this;
    }

    public synchronized EtcdClientPoolFactory build(String baseUri,int max){
        try {
            if (pool == null)
                pool = new EtcdClientPool(baseUri,max);
        } catch (URISyntaxException e) {
            logger.error("",e);
        }
        return this;
    }

    public EtcdClientPool getEtcdClientPool() throws Exception {
        if (pool == null)
            throw  new Exception(" EtcdClientPool is null ,must before invoker build method");
        return pool;
    }

}
