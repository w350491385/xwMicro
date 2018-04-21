package com.rpc.etcd.listener;

import com.rpc.spring.config.register.AbstractRegisterServer;
import com.rpc.spring.config.register.EtcdListener;
import mousio.client.promises.ResponsePromise;
import mousio.etcd4j.responses.EtcdKeysResponse;
import org.apache.log4j.spi.LoggerFactory;
import org.slf4j.Logger;

import java.util.Map;

/**
 * Created by huangdongbin on 2018/3/28.
 */
public class RegisterServerEtcdListener extends AbstractRegisterServer implements EtcdListener {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(RegisterServerEtcdListener.class);
    @Override
    public void notify(String dir, ResponsePromise<EtcdKeysResponse> responsePromise) {
        try {
            EtcdKeysResponse response = responsePromise.get();
            modify(dir, response.node.key, response.node.value);
            logger.debug("dir is {} , key is {} ,value is {} ", dir, response.node.key, response.node.value);
        }catch (Exception e){
            logger.error("",e);
        }
    }
}
