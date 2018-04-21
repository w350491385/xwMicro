package com.rpc.spring.config.register;

import mousio.client.promises.ResponsePromise;
import mousio.etcd4j.responses.EtcdKeysResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by huangdongbin on 2018/3/26.
 */
public interface EtcdListener {

     Logger logger = LoggerFactory.getLogger(EtcdListener.class);

    void notify(String dir, ResponsePromise<EtcdKeysResponse> responsePromise);
}
