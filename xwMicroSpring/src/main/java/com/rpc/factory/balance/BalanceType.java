package com.rpc.factory.balance;

/**
 * 负载均衡算法枚举 
 */
public enum BalanceType {
	ROUND("round",new RoundBalance());
	
	private String name;
	private Balance balance;
	
	BalanceType(String name,Balance balance){
		this.name = name;
		this.balance = balance;
	}
	
	Balance getBlance(){
		return this.balance;
	}
	
	String getName(){
		return this.name;
	}
	
	public static Balance getBalance(String name){
		switch(name.toLowerCase()){
			case "round":return ROUND.getBlance();
			default:return ROUND.getBlance();
		}
	}

}
