package com.rpc.spring.config.tag;

import com.caucho.hessian.server.HessianSkeleton;
import com.rpc.annotation.ReadOnly;
import com.rpc.etcd.EtcdStructData;
import com.rpc.etcd.listener.ListenerEnum;
import com.rpc.http.ServerProtocolProcessor;
import com.rpc.http.hessian.HessianServerProtocalProcessor;
import com.rpc.spring.config.register.EtcdListener;
import com.rpc.spring.config.register.EtcdRegisterServerFactory;
import com.rpc.spring.config.register.impl.EtcdRegisterServerImpl;
import com.rpc.spring.constant.Constant;
import com.rpc.util.ObjectCacheUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 服务端配置
 */
public class RpcConfigServer<T> implements InitializingBean, ApplicationContextAware, ApplicationListener,DisposableBean {

    private String id;//id
    private String name;//名称
    private String interfaceClass;//接口
    private T ref;//引用
    private boolean downgrade = false;//默认 false
    private Object downgradeProcessor;
    private String protocol;//hessian
    private List<RegisterConfig> registers;
    private ApplicationContext applicationContext;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getRef() {
        return ref;
    }

    public void setRef(T ref) {
        this.ref = ref;
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

    public boolean isDowngrade() {
        return downgrade;
    }

    public void setDowngrade(boolean downgrade) {
        this.downgrade = downgrade;
    }


    public Object getDowngradeProcessor() {
        return downgradeProcessor;
    }

    public void setDowngradeProcessor(Object downgradeProcessor) {
        this.downgradeProcessor = downgradeProcessor;
    }


    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

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

        HessianSkeleton skeleton = new HessianSkeleton(ref, Class.forName(interfaceClass));
        ServerProtocolProcessor processor = new HessianServerProtocalProcessor(skeleton, downgrade, true, downgradeProcessor, this.ref.getClass().getName());
        ObjectCacheUtils.addHandler(interfaceClass, processor);
        Method[] methods = this.ref.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(ReadOnly.class)) {
                ObjectCacheUtils.put(this.ref.getClass().getName(), this.ref.getClass().getName() + "." + method.getName()
                        + "." + ObjectCacheUtils.getParameterNames(method.getParameterTypes()));
            }
        }
    }

    public List<RegisterConfig> getRegisters() {
        return registers;
    }

    public void setRegisters(List<RegisterConfig> registers) {
        this.registers = registers;
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
            doRegister(registerConfig, etcdRegisterServer);
        }
    }

    private void doRegister(RegisterConfig registerConfig, EtcdRegisterServerImpl etcdRegisterServer) {
        String host = "";
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String port = registerConfig.getLocalPort();
        if (host == null || "".equals(host)) {
            throw new RuntimeException("没有获取到本地ip");
        }
        String dir = ListenerEnum.REGISTER_SERVER.getRegisterDir() + "/" + this.getInterfaceClass();//目录
        String key = dir+"/"+host + ":" + port;//属性值
        String value = registerConfig.getWebApp() != null && !"".equals(registerConfig.getWebApp())
                ? host + ":" + port + "/" + registerConfig.getWebApp() + "/" + this.getInterfaceClass() :
                host + ":" + port + "/" + this.getInterfaceClass();//对应的值
        boolean isTemp = true;//是否是零时
        EtcdStructData data = new EtcdStructData(dir,key, value, isTemp);
        etcdRegisterServer.putDirAndProperty(data, ListenerEnum.REGISTER_SERVER.getInstance());
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
