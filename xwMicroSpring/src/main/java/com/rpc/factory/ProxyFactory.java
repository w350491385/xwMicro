package com.rpc.factory;

import com.rpc.factory.proxy.JdkProxy;
import com.rpc.factory.proxy.handler.Handler;

/**
 * 代理工厂
 *
 */
public class ProxyFactory {
	
	private ProxyFactory(){}
	
	private static class SingleFactory{
		private final static ProxyFactory instance = new ProxyFactory();
	}
	
	public static ProxyFactory  getProxyFactory(){
		return SingleFactory.instance;
	}
	public JdkProxy getProxy(Handler handler){
		return new JdkProxy(handler);
	}	

}
