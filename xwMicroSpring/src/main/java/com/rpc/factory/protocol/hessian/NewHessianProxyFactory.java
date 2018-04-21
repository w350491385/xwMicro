package com.rpc.factory.protocol.hessian;

import com.caucho.hessian.client.HessianProxyFactory;
import com.rpc.factory.ParamsData;

public class NewHessianProxyFactory {

	public static HessianProxyFactory getHessianProxyFactory(ParamsData params){
		HessianProxyFactory proxy = new HessianProxyFactory();
		ParamsUrlHessianConnectionFactory paramsConnection = new ParamsUrlHessianConnectionFactory(params);
		proxy.setConnectionFactory(paramsConnection);
		paramsConnection.setHessianProxyFactory(proxy);
		return proxy;
	}
}
