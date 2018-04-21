package com.rpc.factory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ParamsData implements Serializable{
	/**
	 */
	private static final long serialVersionUID = 1L;
	private String clazz;//接口
	private String methodName;
	private int timeout;
	private String clusterKey;
	private Class<?>[] parameterTypes;
	private Object[] values;
	
	public ParamsData() {
		super();
	}
	public ParamsData(String clazz, String methodName, int timeout,String clusterKey,Class<?>[] parameterTypes,Object[] values) {
		super();
		this.clazz = clazz;
		this.methodName = methodName;
		this.timeout = timeout;
		this.clusterKey = clusterKey;
		this.parameterTypes = parameterTypes;
		this.values = values;
	}
	public String getClazz() {
		return clazz;
	}
	public String getMethodName() {
		return methodName;
	}
	public int getTimeout() {
		return timeout;
	}
	
	public String getClusterKey() {
		return clusterKey;
	}
	
	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}
	
	public ParamsData setClazz(String clazz) {
		this.clazz = clazz;
		return this;
	}
	public ParamsData setMethodName(String methodName) {
		this.methodName = methodName;
		return this;
	}
	public ParamsData setTimeout(int timeout) {
		this.timeout = timeout;
		return this;
	}
	public ParamsData setClusterKey(String clusterKey) {
		this.clusterKey = clusterKey;
		return this;
	}
	public ParamsData setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
		return this;
	}
	public Object[] getValues() {
		return values;
	}
	public ParamsData setValues(Object[] values) {
		this.values = values;
		return this;
	}
	public Map<String,String> getHeaderParams(){
		Map<String,String> params = new HashMap<String,String>();
		params.put("methodName",this.methodName);
		params.put("clazz", this.clazz);
		params.put("clusterKey", this.clusterKey);
		return params;
	}
}
