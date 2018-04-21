package com.xw.etcd;

import com.rpc.spring.config.register.EtcdListener;
import mousio.client.promises.ResponsePromise;
import mousio.etcd4j.responses.EtcdKeysResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangdongbin on 2018/3/27.
 */
public class ServerEtcdListener implements EtcdListener {
    @Override
    public void notify(String dir, ResponsePromise<EtcdKeysResponse> responsePromise) {

    }
//    @Override
//    public void callback(String dir, ResponsePromise<EtcdKeysResponse> responsePromise) {
//        HashMap all = new HashMap();
//        Boolean refresh = refreshKV(dir, all, responsePromise);
//        if (refresh != null && refresh) {
//            System.out.println("dir is :" + dir + "," + all);
//        }
//    }
//
//    @Override
//    public Boolean refreshKV(String dir, Map<Object, Object> all, ResponsePromise<EtcdKeysResponse> responsePromise) {
//        return null;
//    }
}
