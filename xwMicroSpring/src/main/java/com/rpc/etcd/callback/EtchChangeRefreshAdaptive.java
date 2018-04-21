package com.rpc.etcd.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class EtchChangeRefreshAdaptive {

    private static Logger logger = LoggerFactory.getLogger(EtchChangeRefreshAdaptive.class);
    //可以不存在
    private Map<String,String> refTarget = new HashMap<>();//获取需要刷新的对象 key-->beanName
    //必须存在
    private Map<String,EtcdRefreshServer> refRefreshServer = new HashMap<>();//获取具体的执行类 key-->beanRef

    public void setRefTarget(Map<String, String> refTarget) {
        this.refTarget = refTarget;
    }

    public void setRefRefreshServer(Map<String, EtcdRefreshServer> refRefreshServer) {
        this.refRefreshServer = refRefreshServer;
    }

    public void refresh(String typeName,String dir, String key, String value){
        if (!refRefreshServer.containsKey(typeName))
            throw new RuntimeException("typeName is "+typeName+",can not find EtcdRefreshServer");
        EtcdRefreshServer etcdRefreshServer = refRefreshServer.get(typeName);
        if (key.indexOf(".") == -1)
            throw new RuntimeException("key is " + key +",format is wrong");
        String propertyName = key.substring(key.lastIndexOf(".")+1);
        String beanName = refTarget.get(typeName);
        etcdRefreshServer.refresh(beanName,dir,propertyName,value);
        logger.debug("typeName is {} , key is {},value is {}",typeName,key,value);
    }
}
