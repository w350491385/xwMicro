package com.rpc.spring.config.tag;

import com.rpc.etcd.listener.ListenerEnum;
import com.rpc.factory.BalanceFactory;
import com.rpc.factory.ClusterFactory;
import com.rpc.factory.ProtocolFactory;
import com.rpc.factory.ProxyFactory;
import com.rpc.factory.protocol.hessian.HessianProtocol;
import com.rpc.factory.proxy.handler.Handler;
import com.rpc.factory.proxy.handler.JdkHandler;
import com.rpc.spring.config.cache.RegisterDataCache;
import com.rpc.spring.config.register.EtcdRegisterServerFactory;
import com.rpc.spring.config.register.impl.EtcdRegisterServerImpl;
import com.rpc.spring.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户端配置
 */
public class RpcConfigClient implements FactoryBean<Object>,InitializingBean, ApplicationContextAware, ApplicationListener,DisposableBean{

    private Logger logger = LoggerFactory.getLogger(RpcConfigClient.class);
    private String id;//id
    private String name;//名称
    private String interfaceClass;//接口
    private String url;//地址
    private int retries;//
    private Object proxyObj = null;

    private String cluster = "failover";//请求机制
    private String balance = "round";//负载均衡
    private String protocol = "hessian";//
    private Map<String, MethodConfig> methodReturnType = new HashMap<>();
    private Map<String, Class<?>> methodReturnTypeClazz = new HashMap<>();
    private Map<String,Integer> methodRetriesMap = new HashMap<>();
    private Map<String,Boolean> methodAsyncMap = new HashMap<>();
    private Map<String,Integer> methodTimeoutMap = new HashMap<>();

    private ProxyFactory proxyFactory = ProxyFactory.getProxyFactory();//代理工厂
    private ClusterFactory clusterFactory = ClusterFactory.getClusterFactory();//请求机制
    private ProtocolFactory protocolFactory = ProtocolFactory.getProtocolFactory();//协议
    private BalanceFactory balanceFactory = BalanceFactory.getBalanceFactory();//负载均衡
    private int timeout = 2000;

    private String group;//
    private List<RegisterConfig> registers;

    private ApplicationContext applicationContext;

    public List<RegisterConfig> getRegisters() {
        return registers;
    }

    public void setRegisters(List<RegisterConfig> registers) {
        this.registers = registers;
    }

    public Map<String, MethodConfig> getMethodReturnType() {
        return methodReturnType;
    }

    public void setMethodReturnType(Map<String, MethodConfig> methodReturnType) {
        this.methodReturnType = methodReturnType;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(String interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Object getObject() throws Exception {
        if (proxyObj == null)
            createProxy();
        return proxyObj;
    }

    /**
     * 创建代理对象
     */
    private void createProxy() {
        try {
            if (getRegisters() == null || getRegisters().size() == 0) {
                Map<String, RegisterConfig> registryConfigMap = applicationContext == null ? null : BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, RegisterConfig.class, false, false);
                if (registryConfigMap != null && registryConfigMap.size() > 0) {
                    List<RegisterConfig> registryConfigs = new ArrayList<>();
                    for (RegisterConfig config : registryConfigMap.values()) {
                        registryConfigs.add(config);
                    }
                    if (registryConfigs != null && registryConfigs.size() > 0) {
                        setRegisters(registryConfigs);
                    }
                }
            }

            Handler handler = new JdkHandler(clusterFactory.getCluster(this.cluster),
                    new HessianProtocol(), getObjectType(), balanceFactory.getBalance(balance),
                    this.timeout, this.interfaceClass, this.group, this.url, this.retries
                    , methodReturnTypeClazz,this.methodRetriesMap,this.methodAsyncMap,this.methodTimeoutMap);
            this.proxyObj = Proxy.newProxyInstance(ClassUtils.getDefaultClassLoader(), new Class<?>[]{getObjectType()},
                    proxyFactory.getProxy(handler));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Class<?> getObjectType() {
        try {
            return Class.forName(interfaceClass);
        } catch (ClassNotFoundException e) {
            logger.error("", e);
        }
        return null;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (String key : methodReturnType.keySet()) {
            methodReturnTypeClazz.put(key, Class.forName(methodReturnType.get(key).getReturnType()));
            methodRetriesMap.put(key,methodReturnType.get(key).getRetries());
            methodAsyncMap.put(key,methodReturnType.get(key).isAsync());
            methodTimeoutMap.put(key,methodReturnType.get(key).getTimeout());
        }
        createProxy();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (ContextRefreshedEvent.class.getName().equals(event.getClass().getName())) {
            if (getRegisters() != null && getRegisters().size() > 0)
                register();
        }
    }

    private void register() {
        EtcdRegisterServerFactory etcdRegisterServerFactory = EtcdRegisterServerFactory.getInstance();
        for (RegisterConfig registerConfig : getRegisters()) {
            EtcdRegisterServerImpl etcdRegisterServer = etcdRegisterServerFactory.get(registerConfig);
            //客户端注册目录监听
            String path = ListenerEnum.REGISTER_SERVER.getRegisterDir() + "/" + this.getInterfaceClass();
            Map<Object,Object> map = etcdRegisterServer.getDir(path);//获取目录数据
            for (Object key : map.keySet()) {
                RegisterDataCache.getInstance().put(path,path + (String)key,(String)map.get(key));
            }
            etcdRegisterServer.wtachDir(path, ListenerEnum.REGISTER_SERVER.getInstance());//监听
        }
    }

    @Override
    public void destroy() throws Exception {
        EtcdRegisterServerFactory etcdRegisterServerFactory = EtcdRegisterServerFactory.getInstance();
        for (RegisterConfig registerConfig : getRegisters()) {
            EtcdRegisterServerImpl etcdRegisterServer = etcdRegisterServerFactory.get(registerConfig);
            etcdRegisterServer.destroy();
        }
    }
}
