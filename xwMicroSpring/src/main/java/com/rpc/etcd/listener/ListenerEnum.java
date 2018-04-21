package com.rpc.etcd.listener;

import com.rpc.spring.config.register.EtcdListener;
import com.rpc.spring.constant.Constant;

/**
 * Created by Administrator on 2018/3/29.
 */
public enum ListenerEnum {

    REGISTER_SERVER(Constant.PREFIX,"",new RegisterServerEtcdListener(),"注册服务监控","server"),
    OTHER(Constant.SYSTEM_CONFIG,"/config",new OtherEtcdListener(),"其他普通配置","config");

    private String prentDir;//监控目录
    private String currentDir;//当前目录
    private EtcdListener instance;//监控对象
    private String desc;//描述
    private String end;//后缀 最后一个目录

    ListenerEnum(String prentDir,String currentDir, EtcdListener instance, String desc, String end) {
        this.prentDir = prentDir;
        this.currentDir = currentDir;
        this.instance = instance;
        this.desc = desc;
        this.end = end;
    }

    public String getRegisterDir(){
        return  this.prentDir;
    }

    public String getEnd() {
        return end;
    }

    public String getDesc() {
        return desc;
    }

    public String getDir(String group){
        if (group == null || "".equals(group))
            throw  new RuntimeException("goup 不能为null");
        if (group != null && group.startsWith("/")){
            return this.prentDir + group + this.currentDir;
        }
        return this.prentDir + "/" + group + this.currentDir;
    }

    public EtcdListener getInstance() {
        return instance;
    }
}
