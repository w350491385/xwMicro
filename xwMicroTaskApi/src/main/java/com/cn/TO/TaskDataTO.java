package com.cn.TO;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2018/4/3.
 */
public class TaskDataTO implements Serializable{
    private Long id;
    private String bizCode;
    private String fullclass;
    private Date createtime;
    private String corn;
    private String name;
    private String groupname;
    private String triggername;
    private Date updatetime;
    private Boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getFullclass() {
        return fullclass;
    }

    public void setFullclass(String fullclass) {
        this.fullclass = fullclass;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCorn() {
        return corn;
    }

    public void setCorn(String corn) {
        this.corn = corn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getTriggername() {
        return triggername;
    }

    public void setTriggername(String triggername) {
        this.triggername = triggername;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
