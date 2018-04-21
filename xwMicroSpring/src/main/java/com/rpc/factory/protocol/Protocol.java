package com.rpc.factory.protocol;

import com.rpc.factory.ParamsData;

/**
 * 协议 
 * @author Administrator
 */
public interface Protocol {

	<T> T refer(Class<T> clazz, String url, ParamsData params) throws Exception;
	
}
