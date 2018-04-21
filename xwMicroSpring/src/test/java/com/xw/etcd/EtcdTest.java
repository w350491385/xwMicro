package com.xw.etcd;

import com.rpc.etcd.EtcdStructData;
import com.rpc.etcd.listener.ListenerEnum;
import com.rpc.spring.config.register.impl.EtcdRegisterServerImpl;
import io.netty.util.HashedWheelTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by huangdongbin on 2018/3/27.
 */
public class EtcdTest {
    private static Logger logger = LoggerFactory.getLogger(EtcdTest.class);
    public static  void main(String[] args){
        HashedWheelTimer timer = null;
        EtcdRegisterServerImpl etcdRegisterServer = new EtcdRegisterServerImpl("http://192.168.1.158:2379",5,"server");
        String rootDir = "/host/";
//        deleteDir(etcdRegisterServer, rootDir);
//        buildDir(etcdRegisterServer, rootDir);
        putProperty(etcdRegisterServer,"/host/dir/mysql.address/127.0.0.1:99","127.0.0.1:99/"+EtcdStructData.class.getName());
        putProperty(etcdRegisterServer,"/host/dir/mysql.address/127.0.0.1:88","127.0.0.1:88/"+EtcdTest.class.getName());
    }

    private static void putProperty(EtcdRegisterServerImpl etcdRegisterServer,String key, String value) {
        EtcdStructData etcdStructData = new EtcdStructData("/host/dir/mysql.address",key,value,true);
        etcdRegisterServer.putDirAndProperty(etcdStructData, ListenerEnum.REGISTER_SERVER.getInstance());
    }

    private static void deleteDir(EtcdRegisterServerImpl etcdRegisterServer, String rootDir) {
        Map<Object,Object> map = etcdRegisterServer.getDir(rootDir);
        for (Object key : map.keySet()){
            if (map.get(key) == null)
                etcdRegisterServer.delete(rootDir+key);
        }
    }

    private static void buildDir(final EtcdRegisterServerImpl etcdRegisterServer, final String rootDir) {
        for (int i = 0 ;i < 20 ; i++){
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    etcdRegisterServer.putDir(rootDir + finalI,6000,new ServerEtcdListener());
                }
            }).start();
        }
    }
}
