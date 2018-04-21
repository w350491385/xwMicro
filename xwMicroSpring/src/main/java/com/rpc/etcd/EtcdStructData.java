package com.rpc.etcd;

import com.rpc.spring.constant.Constant;

/**
 * Created by huangdongbin on 2018/3/28.
 */
public class EtcdStructData implements Comparable<EtcdStructData> {

    private String dir;//目录
    private String key;//属性值
    private String value;//对应的值
    private boolean isTemp;//是否是零时
    private int ttl = Constant.SECOND;//时间

    //属性构建
    public EtcdStructData(String dir, String key, String value, boolean isTemp) {
        this.dir = dir;
        this.key = key;
        this.value = value;
        this.isTemp = isTemp;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isTemp() {
        return isTemp;
    }

    public void setTemp(boolean temp) {
        isTemp = temp;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EtcdStructData)) return false;

        EtcdStructData that = (EtcdStructData) o;

        if (isTemp() != that.isTemp()) return false;
        if (getTtl() != that.getTtl()) return false;
        if (!getDir().equals(that.getDir())) return false;
        if (!getKey().equals(that.getKey())) return false;
        return getValue().equals(that.getValue());

    }

    @Override
    public int hashCode() {
        int result = getDir().hashCode();
        result = 31 * result + getKey().hashCode();
        result = 31 * result + getValue().hashCode();
        result = 31 * result + (isTemp() ? 1 : 0);
        result = 31 * result + getTtl();
        return result;
    }

    @Override
    public int compareTo(EtcdStructData o) {
        if (this.getTtl() > o.getTtl()){
            return 1;
        }else if(this.getTtl() < o.getTtl()){
            return -1;
        }
        return 0;

    }
}
