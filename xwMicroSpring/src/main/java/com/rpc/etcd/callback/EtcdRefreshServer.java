package com.rpc.etcd.callback;

/**
 * Created by Administrator on 2018/4/2.
 */
public interface EtcdRefreshServer {

    void refresh(String beanName,String dir,String propetyName,String value);
}
