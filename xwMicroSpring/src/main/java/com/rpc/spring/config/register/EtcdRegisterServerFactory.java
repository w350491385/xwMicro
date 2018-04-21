package com.rpc.spring.config.register;

import com.rpc.spring.config.register.impl.EtcdRegisterServerImpl;
import com.rpc.spring.config.tag.RegisterConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 */
public class EtcdRegisterServerFactory {

    private final static EtcdRegisterServerFactory etecdRegisterServerFactory = new EtcdRegisterServerFactory();
    private static Map<RegisterConfig,EtcdRegisterServerImpl> etcdRegisterServer = new ConcurrentHashMap<>();
    private Lock lock = new ReentrantLock();

    private EtcdRegisterServerFactory() {
    }

    public static EtcdRegisterServerFactory getInstance() {
        return etecdRegisterServerFactory;
    }

    public EtcdRegisterServerImpl get(RegisterConfig registerConfig) {
        try{
            lock.lock();
            EtcdRegisterServerImpl tempEtcdRegisterServer = etcdRegisterServer.get(registerConfig);
            if (tempEtcdRegisterServer == null)
                tempEtcdRegisterServer = new EtcdRegisterServerImpl(registerConfig.getAddress(),registerConfig.getGroup());
            etcdRegisterServer.put(registerConfig,tempEtcdRegisterServer);
            return tempEtcdRegisterServer;
        }finally {
            lock.unlock();
        }
    }
}
