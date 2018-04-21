package com.rpc.factory.cluster;

/**
 * 集群类型枚举
 */
public enum ClusterType {
	
	FAILOVER("failover",new FailOverCluster()),FAILOVER_NEW("failover_new",new FailOverNewCluster());
	
	private String name;
	private Cluster cluster;
	
	private ClusterType(String name,Cluster cluster){
		this.name = name;
		this.cluster = cluster;
	}
	
	public Cluster  getCluster(){
		return this.cluster;
	}
	
	public String getName(){
		return this.name;
	}
	
	public static Cluster getCluster(String name){
		switch(name.toLowerCase()){
			case "failover":return FAILOVER.getCluster();
			case "failover_new":return FAILOVER_NEW.getCluster();
			default : return FAILOVER.getCluster();
		}
	}

}
