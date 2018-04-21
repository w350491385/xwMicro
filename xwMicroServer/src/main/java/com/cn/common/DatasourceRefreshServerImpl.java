package com.cn.common;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.rpc.etcd.callback.EtcdRefreshServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Properties;

/**
 */
public class DatasourceRefreshServerImpl implements EtcdRefreshServer , ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(DatasourceRefreshServerImpl.class);
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void refresh(String beanName,String dir, String propertyName, String value) {
        ComboPooledDataSource comboPooledDataSource = (ComboPooledDataSource)applicationContext.getBean(beanName);
        Properties properties = comboPooledDataSource.getProperties();
        if (properties.containsKey(propertyName)){
            Object oldValue = properties.get(propertyName);
            logger.debug("key is {},olde value {} change new value {}",propertyName,oldValue,value);
        }
        properties.put(propertyName,value);
        comboPooledDataSource.setProperties(properties);
        comboPooledDataSource.resetPoolManager();
    }
}
