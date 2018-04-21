package com.rpc.factory.balance;

import com.rpc.factory.RpcData;
import com.rpc.spring.constant.Constant;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 轮询 
 */
public class RoundBalance implements Balance {
	
	private ConcurrentHashMap<String, AtomicInteger> sequences = new ConcurrentHashMap<>();

	public String getUrl(RpcData rpcData){
		List<String> urls = rpcData.getUrls();
		String key = rpcData.getClusterKey();
		int length = urls.size();
		AtomicInteger atomicNum = sequences.get(key);
		if(atomicNum==null || atomicNum.get() < 0){
			atomicNum = new AtomicInteger(0);
			sequences.put(key, atomicNum);
		}
		return Constant.HTTP_PROTOCOL + urls.get(atomicNum.getAndIncrement()%length);
	}

}
