package com.rpc.factory.proxy;

import com.rpc.factory.proxy.handler.Handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JdkProxy implements InvocationHandler{
	
	private Handler handler;
	
	public JdkProxy(Handler handler){
		this.handler = handler;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return handler.exec(method,args);
	}

}
