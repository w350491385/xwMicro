package com.cn.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskDataExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table task_data
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table task_data
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table task_data
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_data
     *
     * @mbggenerated
     */
    public TaskDataExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_data
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_data
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_data
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_data
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_data
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_data
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_data
     *
     * @mbggenerated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_data
     *
     * @mbggenerated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_data
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_data
     *
     * @mbggenerated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return offset;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table task_data
     *
     * @mbggenerated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andBizCodeIsNull() {
            addCriterion("biz_code is null");
            return (Criteria) this;
        }

        public Criteria andBizCodeIsNotNull() {
            addCriterion("biz_code is not null");
            return (Criteria) this;
        }

        public Criteria andBizCodeEqualTo(String value) {
            addCriterion("biz_code =", value, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizCodeNotEqualTo(String value) {
            addCriterion("biz_code <>", value, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizCodeGreaterThan(String value) {
            addCriterion("biz_code >", value, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizCodeGreaterThanOrEqualTo(String value) {
            addCriterion("biz_code >=", value, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizCodeLessThan(String value) {
            addCriterion("biz_code <", value, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizCodeLessThanOrEqualTo(String value) {
            addCriterion("biz_code <=", value, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizCodeLike(String value) {
            addCriterion("biz_code like", value, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizCodeNotLike(String value) {
            addCriterion("biz_code not like", value, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizCodeIn(List<String> values) {
            addCriterion("biz_code in", values, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizCodeNotIn(List<String> values) {
            addCriterion("biz_code not in", values, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizCodeBetween(String value1, String value2) {
            addCriterion("biz_code between", value1, value2, "bizCode");
            return (Criteria) this;
        }

        public Criteria andBizCodeNotBetween(String value1, String value2) {
            addCriterion("biz_code not between", value1, value2, "bizCode");
            return (Criteria) this;
        }

        public Criteria andFullclassIsNull() {
            addCriterion("fullClass is null");
            return (Criteria) this;
        }

        public Criteria andFullclassIsNotNull() {
            addCriterion("fullClass is not null");
            return (Criteria) this;
        }

        public Criteria andFullclassEqualTo(String value) {
            addCriterion("fullClass =", value, "fullclass");
            return (Criteria) this;
        }

        public Criteria andFullclassNotEqualTo(String value) {
            addCriterion("fullClass <>", value, "fullclass");
            return (Criteria) this;
        }

        public Criteria andFullclassGreaterThan(String value) {
            addCriterion("fullClass >", value, "fullclass");
            return (Criteria) this;
        }

        public Criteria andFullclassGreaterThanOrEqualTo(String value) {
            addCriterion("fullClass >=", value, "fullclass");
            return (Criteria) this;
        }

        public Criteria andFullclassLessThan(String value) {
            addCriterion("fullClass <", value, "fullclass");
            return (Criteria) this;
        }

        public Criteria andFullclassLessThanOrEqualTo(String value) {
            addCriterion("fullClass <=", value, "fullclass");
            return (Criteria) this;
        }

        public Criteria andFullclassLike(String value) {
            addCriterion("fullClass like", value, "fullclass");
            return (Criteria) this;
        }

        public Criteria andFullclassNotLike(String value) {
            addCriterion("fullClass not like", value, "fullclass");
            return (Criteria) this;
        }

        public Criteria andFullclassIn(List<String> values) {
            addCriterion("fullClass in", values, "fullclass");
            return (Criteria) this;
        }

        public Criteria andFullclassNotIn(List<String> values) {
            addCriterion("fullClass not in", values, "fullclass");
            return (Criteria) this;
        }

        public Criteria andFullclassBetween(String value1, String value2) {
            addCriterion("fullClass between", value1, value2, "fullclass");
            return (Criteria) this;
        }

        public Criteria andFullclassNotBetween(String value1, String value2) {
            addCriterion("fullClass not between", value1, value2, "fullclass");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("createTime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("createTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(Date value) {
            addCriterion("createTime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(Date value) {
            addCriterion("createTime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterion("createTime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createTime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(Date value) {
            addCriterion("createTime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("createTime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<Date> values) {
            addCriterion("createTime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<Date> values) {
            addCriterion("createTime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterion("createTime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("createTime not between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCornIsNull() {
            addCriterion("corn is null");
            return (Criteria) this;
        }

        public Criteria andCornIsNotNull() {
            addCriterion("corn is not null");
            return (Criteria) this;
        }

        public Criteria andCornEqualTo(String value) {
            addCriterion("corn =", value, "corn");
            return (Criteria) this;
        }

        public Criteria andCornNotEqualTo(String value) {
            addCriterion("corn <>", value, "corn");
            return (Criteria) this;
        }

        public Criteria andCornGreaterThan(String value) {
            addCriterion("corn >", value, "corn");
            return (Criteria) this;
        }

        public Criteria andCornGreaterThanOrEqualTo(String value) {
            addCriterion("corn >=", value, "corn");
            return (Criteria) this;
        }

        public Criteria andCornLessThan(String value) {
            addCriterion("corn <", value, "corn");
            return (Criteria) this;
        }

        public Criteria andCornLessThanOrEqualTo(String value) {
            addCriterion("corn <=", value, "corn");
            return (Criteria) this;
        }

        public Criteria andCornLike(String value) {
            addCriterion("corn like", value, "corn");
            return (Criteria) this;
        }

        public Criteria andCornNotLike(String value) {
            addCriterion("corn not like", value, "corn");
            return (Criteria) this;
        }

        public Criteria andCornIn(List<String> values) {
            addCriterion("corn in", values, "corn");
            return (Criteria) this;
        }

        public Criteria andCornNotIn(List<String> values) {
            addCriterion("corn not in", values, "corn");
            return (Criteria) this;
        }

        public Criteria andCornBetween(String value1, String value2) {
            addCriterion("corn between", value1, value2, "corn");
            return (Criteria) this;
        }

        public Criteria andCornNotBetween(String value1, String value2) {
            addCriterion("corn not between", value1, value2, "corn");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andGroupnameIsNull() {
            addCriterion("groupName is null");
            return (Criteria) this;
        }

        public Criteria andGroupnameIsNotNull() {
            addCriterion("groupName is not null");
            return (Criteria) this;
        }

        public Criteria andGroupnameEqualTo(String value) {
            addCriterion("groupName =", value, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameNotEqualTo(String value) {
            addCriterion("groupName <>", value, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameGreaterThan(String value) {
            addCriterion("groupName >", value, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameGreaterThanOrEqualTo(String value) {
            addCriterion("groupName >=", value, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameLessThan(String value) {
            addCriterion("groupName <", value, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameLessThanOrEqualTo(String value) {
            addCriterion("groupName <=", value, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameLike(String value) {
            addCriterion("groupName like", value, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameNotLike(String value) {
            addCriterion("groupName not like", value, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameIn(List<String> values) {
            addCriterion("groupName in", values, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameNotIn(List<String> values) {
            addCriterion("groupName not in", values, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameBetween(String value1, String value2) {
            addCriterion("groupName between", value1, value2, "groupname");
            return (Criteria) this;
        }

        public Criteria andGroupnameNotBetween(String value1, String value2) {
            addCriterion("groupName not between", value1, value2, "groupname");
            return (Criteria) this;
        }

        public Criteria andTriggernameIsNull() {
            addCriterion("triggerName is null");
            return (Criteria) this;
        }

        public Criteria andTriggernameIsNotNull() {
            addCriterion("triggerName is not null");
            return (Criteria) this;
        }

        public Criteria andTriggernameEqualTo(String value) {
            addCriterion("triggerName =", value, "triggername");
            return (Criteria) this;
        }

        public Criteria andTriggernameNotEqualTo(String value) {
            addCriterion("triggerName <>", value, "triggername");
            return (Criteria) this;
        }

        public Criteria andTriggernameGreaterThan(String value) {
            addCriterion("triggerName >", value, "triggername");
            return (Criteria) this;
        }

        public Criteria andTriggernameGreaterThanOrEqualTo(String value) {
            addCriterion("triggerName >=", value, "triggername");
            return (Criteria) this;
        }

        public Criteria andTriggernameLessThan(String value) {
            addCriterion("triggerName <", value, "triggername");
            return (Criteria) this;
        }

        public Criteria andTriggernameLessThanOrEqualTo(String value) {
            addCriterion("triggerName <=", value, "triggername");
            return (Criteria) this;
        }

        public Criteria andTriggernameLike(String value) {
            addCriterion("triggerName like", value, "triggername");
            return (Criteria) this;
        }

        public Criteria andTriggernameNotLike(String value) {
            addCriterion("triggerName not like", value, "triggername");
            return (Criteria) this;
        }

        public Criteria andTriggernameIn(List<String> values) {
            addCriterion("triggerName in", values, "triggername");
            return (Criteria) this;
        }

        public Criteria andTriggernameNotIn(List<String> values) {
            addCriterion("triggerName not in", values, "triggername");
            return (Criteria) this;
        }

        public Criteria andTriggernameBetween(String value1, String value2) {
            addCriterion("triggerName between", value1, value2, "triggername");
            return (Criteria) this;
        }

        public Criteria andTriggernameNotBetween(String value1, String value2) {
            addCriterion("triggerName not between", value1, value2, "triggername");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNull() {
            addCriterion("updateTime is null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNotNull() {
            addCriterion("updateTime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeEqualTo(Date value) {
            addCriterion("updateTime =", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotEqualTo(Date value) {
            addCriterion("updateTime <>", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThan(Date value) {
            addCriterion("updateTime >", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("updateTime >=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThan(Date value) {
            addCriterion("updateTime <", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThanOrEqualTo(Date value) {
            addCriterion("updateTime <=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIn(List<Date> values) {
            addCriterion("updateTime in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotIn(List<Date> values) {
            addCriterion("updateTime not in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeBetween(Date value1, Date value2) {
            addCriterion("updateTime between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotBetween(Date value1, Date value2) {
            addCriterion("updateTime not between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Boolean value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Boolean value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Boolean value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Boolean value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Boolean value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Boolean value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Boolean> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Boolean> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Boolean value1, Boolean value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Boolean value1, Boolean value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table task_data
     *
     * @mbggenerated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table task_data
     *
     * @mbggenerated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}