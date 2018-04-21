package com.rpc.spring.config.cache;

import com.rpc.spring.constant.Constant;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by huangdongbin on 2018/3/29.
 */
public class RegisterDataCache {

    // key:Constant.PREFIX_interfaceClass --> value: key:fullKey+"/"+ip:port --> value :ip:port/webApp/interfaceClass
    //key:grop_configure_type -- > value : key:fullKey+"/"+sub_desc --> value: config value
    private static final ConcurrentHashMap<String, Map<String, String>> registerMap = new ConcurrentHashMap(200);

    private RegisterDataCache(){
    }

    public static RegisterDataCache getInstance(){
        return InnerClass.INSTANCE;
    }

    private  static class InnerClass{
        private static final RegisterDataCache INSTANCE = new RegisterDataCache();
    }

    public ConcurrentHashMap<String, Map<String, String>> getRegisterServerMap() {
        return registerMap;
    }

    public List<String> getUrls(String providerKey) {
        Map<String,String> map = registerMap.get(providerKey);
        List<String> list = new ArrayList<>();
        for (String key:map.keySet()){
            list.add(map.get(key));
        }
        return Collections.unmodifiableList(list);
    }

    public synchronized void put(String dir, String key, String value) {
        Map<String, String> map = registerMap.get(dir);
        if (map == null)
            map = new HashMap<>();
        if (key != null )
            map.put(key, value);
        registerMap.put(dir, map);
    }

    public synchronized void modify(String dir, String key, String value) {
        if (registerMap.containsKey(dir)) {
            Map<String, String> map = registerMap.get(dir);
            if (map == null) {
                put(dir, key, value);
                return;
            }
            if (map.containsKey(key) && value == null)
                map.remove(key);
            else if(!map.containsKey(key) && value == null){
                //不处理
            }else
                map.put(key, value);
        }
    }

    public String getProperty(String subType, String defualtValue) {
        Map<String, String> propertiesMap = registerMap.get(Constant.SYSTEM_CONFIG);
        if (propertiesMap != null) {
            if (propertiesMap.containsKey(subType))
                return propertiesMap.get(subType);
        }
        return defualtValue;
    }
}
