package com.rpc.spring.config.register;

import com.rpc.etcd.EtcdClientPool;
import com.rpc.etcd.EtcdStructData;
import mousio.etcd4j.EtcdClient;

import java.util.Map;

/**
 */
public interface RegisterServer {

    void putDir(EtcdStructData etcdStructData,EtcdListener etcdListener);
    void putDirAndProperty(EtcdStructData etcdStructData,EtcdListener etcdListener);
    Map<Object, Object> getDir(String dir);
    void delete(String dir);
    void deleteDir(String dir);
    void wtachDir(String dir,EtcdListener etcdListener);

}
