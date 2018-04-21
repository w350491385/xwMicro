package com.rpc.factory.proxy.handler;

import java.lang.reflect.Method;

public interface Handler {

	Object exec(Method method, Object[] args) throws Exception;
	
}
