package com.rpc.factory.protocol.hessian;

import com.caucho.hessian.client.HessianProxyFactory;
import com.rpc.factory.ParamsData;
import com.rpc.factory.protocol.Protocol;

public class HessianProtocol implements Protocol{

	@SuppressWarnings("unchecked")
	@Override
	public <T> T refer(Class<T> clazz, String url,ParamsData params) throws Exception {
		  HessianProxyFactory hessianProxyFactory =NewHessianProxyFactory.getHessianProxyFactory(params);
	      hessianProxyFactory.setConnectTimeout(params.getTimeout());//连接超时时间
	      hessianProxyFactory.setReadTimeout(params.getTimeout());//读取数据超时时间
       return (T) hessianProxyFactory.create(clazz, url, Thread.currentThread().getContextClassLoader());
	}

}
