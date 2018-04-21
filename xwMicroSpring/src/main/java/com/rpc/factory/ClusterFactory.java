package com.rpc.factory;

import com.rpc.factory.cluster.Cluster;
import com.rpc.factory.cluster.ClusterType;

/**
 * cluster 工厂
 */
public class ClusterFactory {

	private ClusterFactory(){}
	
	private static class  ClusterSingle{
		private final static ClusterFactory instance = new ClusterFactory();
	}
	
	public static ClusterFactory getClusterFactory(){
		return ClusterSingle.instance;
	}
	
	public Cluster getCluster(String name){
		return ClusterType.getCluster(name);
	}
}
