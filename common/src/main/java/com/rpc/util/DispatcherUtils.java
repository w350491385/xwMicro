package com.rpc.util;

public class DispatcherUtils {

	public static String getInterface(String uri) {
		if(uri.indexOf("/")!=-1){
			int lastIndex = uri.indexOf("?");
			if(lastIndex > 0)
				uri = uri.substring(uri.lastIndexOf("/")+1,lastIndex);
			else
				uri = uri.substring(uri.lastIndexOf("/")+1);
		}
		return uri;
	}
}
