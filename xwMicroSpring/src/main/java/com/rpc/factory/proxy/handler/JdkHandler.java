package com.rpc.factory.proxy.handler;

import com.rpc.factory.RpcData;
import com.rpc.factory.balance.Balance;
import com.rpc.factory.cluster.Cluster;
import com.rpc.factory.protocol.Protocol;
import org.omg.PortableInterceptor.INACTIVE;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 */
public class JdkHandler implements Handler {

    private Cluster cluster;//集群
    private Protocol protocol;//协议
    private Balance balance;//负载均衡
    private Class<?> clazz;//代理对象字节
    private int timeout;
    private String interfaceClass;
    private String group;
    private String url;
    private int retries;//
    private Map<String,Class<?>> result = new HashMap<>();//降级对象class
    private Map<String,Integer> retriesMap = new HashMap<>();
    private Map<String,Boolean> methodAsyncMap = new HashMap<>();
    private Map<String,Integer> methodTimeoutMap = new HashMap<>();

    public JdkHandler(Cluster cluster, Protocol protocol, Class<?> clazz, Balance balance, int timeout,
                      String interfaceClass, String group, String url, int retries, Map<String, Class<?>> result,
                      Map<String, Integer> retriesMap, Map<String, Boolean> methodAsyncMap, Map<String, Integer> methodTimeoutMap) {
        super();
        this.cluster = cluster;
        this.protocol = protocol;
        this.clazz = clazz;
        this.balance = balance;
        this.timeout = timeout;
        this.interfaceClass = interfaceClass;
        this.group = group;
        this.url = url;
        this.retries = retries;
        this.result = result;
        this.retriesMap =retriesMap;
        this.methodAsyncMap = methodAsyncMap;
        this.methodTimeoutMap = methodTimeoutMap;
    }

    @Override
    public Object exec(Method method, Object[] args) throws Exception {
        if ("toString".equals(method)) {
            return this.toString();
        } else if ("hashCode".equals(method)) {
            return this.hashCode();
        }
        String methodName = method.getName();
        Class<?> tempclazz = getReturnClassType(methodName,method.getReturnType());
        int retries = getRetries(methodName);
        boolean async = getMethodAsync(methodName);
        int timeout = getMethodTimeout(methodName);
        return cluster.cluster(balance, protocol,
                new RpcData(method, args, clazz, timeout, this.interfaceClass, this.group, this.url, retries, tempclazz,async));
    }

    private int getMethodTimeout(String methodName) {
        int timeout = this.timeout;
        if (this.methodTimeoutMap.containsKey(methodName)) {
            timeout = this.methodTimeoutMap.get(methodName);
        }
        return timeout;
    }

    private boolean getMethodAsync(String methodName) {
        boolean async = false;
        if (this.methodAsyncMap.containsKey(methodName)){
            async = this.methodAsyncMap.get(methodName);
        }
        return async;
    }

    private int getRetries(String methodName) {
        int retries = this.retries;
        if (this.retriesMap.containsKey(methodName))
            retries = this.retriesMap.get(methodName);
        return retries;
    }

    private Class<?> getReturnClassType(String methodName,Class<?> tempClass ) {
        Class<?> tempclazz = this.result.get(methodName);
        if (tempclazz == null) {
            if (!tempClass.equals(Void.TYPE) && !tempClass.isInterface() && !Modifier.isAbstract(tempClass.getModifiers())) {
                tempclazz = tempClass;
                this.result.put(methodName,tempclazz);
            }
        }
        return tempclazz;
    }

    public String toString() {
        return this.clazz.toString();
    }
}
