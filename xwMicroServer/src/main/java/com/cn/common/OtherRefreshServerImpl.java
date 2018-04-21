package com.cn.common;

import com.rpc.etcd.callback.EtcdRefreshServer;
import com.rpc.spring.config.cache.RegisterDataCache;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by Administrator on 2018/4/3.
 */
public class OtherRefreshServerImpl implements EtcdRefreshServer {

    @Override
    public void refresh(String beanName,String dir, String propetyName, String value) {

    }

}
