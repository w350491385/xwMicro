package com.rpc.factory;

import com.rpc.factory.protocol.Protocol;

/**
 * 协议工厂
 * @author Administrator
 */
public class ProtocolFactory {
	
	private ProtocolFactory(){}
	
	private static class ProtocolSingle{
		private final static  ProtocolFactory instance = new ProtocolFactory();
	}
	
	public static ProtocolFactory getProtocolFactory(){
		return ProtocolSingle.instance;
	}

	public Protocol getProtocol(String protocol) throws InstantiationException, IllegalAccessException{
//		ProType type = ProType.getProType(protocol);
//		return type.getObj();
		return null;
	}
}
