package com.rpc.etcd.listener;

import com.rpc.spring.ApplicationContextUtils;
import com.rpc.etcd.callback.EtchChangeRefreshAdaptive;
import com.rpc.spring.config.register.AbstractRegisterServer;
import com.rpc.spring.config.register.EtcdListener;
import mousio.client.promises.ResponsePromise;
import mousio.etcd4j.responses.EtcdKeysResponse;
import org.slf4j.Logger;

/**
 */
public class OtherEtcdListener extends AbstractRegisterServer implements EtcdListener {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(OtherEtcdListener.class);

    @Override
    public void notify(String dir, ResponsePromise<EtcdKeysResponse> responsePromise) {
        if (dir.endsWith(ListenerEnum.OTHER.getEnd())) {
            EtcdKeysResponse response = null;
            try {
                response = responsePromise.get();
                String key = response.node.key;
                String value = response.node.value;
                if (ApplicationContextUtils.applicationContext == null){
                    logger.error("ApplicationContextUtils.applicationContext is null , must config");
                }
                EtchChangeRefreshAdaptive adptive = ApplicationContextUtils.applicationContext.getBean(EtchChangeRefreshAdaptive.class);
                String type = "other";
                String newKey = key.substring(key.lastIndexOf("/")+1);
                if (newKey.toLowerCase().startsWith("datasource")) {//数据库配置
                    type = "datasource";
                } else if ( key.toLowerCase().startsWith("redis")) {//redis配置
                    type = "redis";
                } else if (key.toLowerCase().startsWith("schedule")) {//定时任务配置
                    type = "schedule";
                }
                adptive.refresh(type,dir,key,value);//刷新容器
                modify(dir, key, value);//配置统一更新
                logger.debug("dir is {} , key is {} ,value is {} ", dir, key, value);
            } catch (Exception e) {
                logger.error("", e);
            }
            return;
        }
        logger.info("dir {} 没有被监听", dir);
    }
}
