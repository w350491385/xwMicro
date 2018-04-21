package com.rpc.factory;

import com.rpc.spring.config.cache.RegisterDataCache;
import com.rpc.spring.constant.Constant;
import com.rpc.util.ObjectCacheUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RpcData {

	private Method method;
	private Object[] args;
	private Class<?> clazz;
	private String url;
	private List<String> urls = null;
	private int timeout;
	private String interfaceClass; 
	private String group;
	private int retries = 0;//重试次数
	private  boolean async = false;
	private Class<?> result;
	
	public RpcData(Method method, Object[] args, Class<?> clazz, int timeout,String interfaceClass,String group,String url,int retries,Class<?> result,boolean async) {
		super();
		this.method = method;
		this.args = args;
		this.clazz = clazz;
		this.url = url;
		this.timeout = timeout;
		this.interfaceClass = interfaceClass;
		this.group = group;
		this.retries =  retries;
		this.result = result;
		this.async = async;
	}

	public boolean isAsync() {
		return async;
	}

	public String getGroup() {
		return group;
	}

	public int getTimeout() {
		return timeout;
	}

	public int getRetries() {
		return retries;
	}

	public String getMethodName() {
		return this.method.getName();
	}

	public Class<?>[] getParameterType(){
		return this.method.getParameterTypes();
	}
	
	public Object[] getArgs() {
		return this.args;
	}

	public Class<?> getClazz() {
		return this.clazz;
	}

	public Class<?> getResult() {
		return result;
	}

	//获取hystrix名称
	public String getHystrixName(){
		return  getClazz().getName() + "-" + getMethodName();
	}

	//获取hystrix组名称
	public String getHystrixGroupName(){
		return getGroup() + "-" + getClazz().getName();
	}

	/**
	 * 服务端key
	 * @return
     */
	public String getProviderKey(){
		return getGroup() + "_provider_" + getClazz().getName();
	}

	/**
	 * 消费端key
	 * @return
     */
	public String getConsumerKey(){
		return  getGroup() + "_consumer_" + getClazz().getName();
	}

	public List<String> getUrls() {
		if (this.url != null && !"".equals(url)){
			return new ArrayList<>(Arrays.asList(url));
		}
		return RegisterDataCache.getInstance().getUrls(Constant.PREFIX + "/" + this.interfaceClass);
	}

	public String getClusterKey(){
		return clazz.getName()+"_"+method.getName()+"."+ ObjectCacheUtils.getParameterNames(getParameterType());
	}
	
}
