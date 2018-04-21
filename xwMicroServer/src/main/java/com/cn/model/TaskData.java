package com.cn.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class TaskData implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_data.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_data.biz_code
     *
     * @mbggenerated
     */
    private String bizCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_data.fullClass
     *
     * @mbggenerated
     */
    private String fullclass;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_data.createTime
     *
     * @mbggenerated
     */
    private Date createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_data.corn
     *
     * @mbggenerated
     */
    private String corn;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_data.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_data.groupName
     *
     * @mbggenerated
     */
    private String groupname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_data.triggerName
     *
     * @mbggenerated
     */
    private String triggername;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_data.updateTime
     *
     * @mbggenerated
     */
    private Date updatetime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_data.status
     *
     * @mbggenerated
     */
    private Boolean status;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_data.id
     *
     * @return the value of task_data.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_data.id
     *
     * @param id the value for task_data.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_data.biz_code
     *
     * @return the value of task_data.biz_code
     *
     * @mbggenerated
     */
    public String getBizCode() {
        return bizCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_data.biz_code
     *
     * @param bizCode the value for task_data.biz_code
     *
     * @mbggenerated
     */
    public void setBizCode(String bizCode) {
        this.bizCode = bizCode == null ? null : bizCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_data.fullClass
     *
     * @return the value of task_data.fullClass
     *
     * @mbggenerated
     */
    public String getFullclass() {
        return fullclass;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_data.fullClass
     *
     * @param fullclass the value for task_data.fullClass
     *
     * @mbggenerated
     */
    public void setFullclass(String fullclass) {
        this.fullclass = fullclass == null ? null : fullclass.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_data.createTime
     *
     * @return the value of task_data.createTime
     *
     * @mbggenerated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_data.createTime
     *
     * @param createtime the value for task_data.createTime
     *
     * @mbggenerated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_data.corn
     *
     * @return the value of task_data.corn
     *
     * @mbggenerated
     */
    public String getCorn() {
        return corn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_data.corn
     *
     * @param corn the value for task_data.corn
     *
     * @mbggenerated
     */
    public void setCorn(String corn) {
        this.corn = corn == null ? null : corn.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_data.name
     *
     * @return the value of task_data.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_data.name
     *
     * @param name the value for task_data.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_data.groupName
     *
     * @return the value of task_data.groupName
     *
     * @mbggenerated
     */
    public String getGroupname() {
        return groupname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_data.groupName
     *
     * @param groupname the value for task_data.groupName
     *
     * @mbggenerated
     */
    public void setGroupname(String groupname) {
        this.groupname = groupname == null ? null : groupname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_data.triggerName
     *
     * @return the value of task_data.triggerName
     *
     * @mbggenerated
     */
    public String getTriggername() {
        return triggername;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_data.triggerName
     *
     * @param triggername the value for task_data.triggerName
     *
     * @mbggenerated
     */
    public void setTriggername(String triggername) {
        this.triggername = triggername == null ? null : triggername.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_data.updateTime
     *
     * @return the value of task_data.updateTime
     *
     * @mbggenerated
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_data.updateTime
     *
     * @param updatetime the value for task_data.updateTime
     *
     * @mbggenerated
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_data.status
     *
     * @return the value of task_data.status
     *
     * @mbggenerated
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_data.status
     *
     * @param status the value for task_data.status
     *
     * @mbggenerated
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }
}