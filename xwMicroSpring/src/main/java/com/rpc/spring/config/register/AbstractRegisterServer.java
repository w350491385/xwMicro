package com.rpc.spring.config.register;

import com.rpc.spring.config.cache.RegisterDataCache;
import com.rpc.spring.constant.Constant;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by huangdongbin on 2018/3/27.
 */
public abstract class AbstractRegisterServer {

    public List<String> getUrls(String providerKey) {
      return RegisterDataCache.getInstance().getUrls(providerKey);
    }

    public void put(String dir, String key, String value) {
        RegisterDataCache.getInstance().put(dir,key,value);
    }

    public void modify(String dir, String key, String value) {
        RegisterDataCache.getInstance().modify(dir,key,value);
    }

    public String getProperty(String subType, String defualtValue) {
        return RegisterDataCache.getInstance().getProperty(subType,defualtValue);
    }

}
