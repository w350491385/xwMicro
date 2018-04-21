package com.rpc.factory;

import com.rpc.factory.balance.Balance;
import com.rpc.factory.balance.BalanceType;

public class BalanceFactory {

	private BalanceFactory(){}
	
	private static class BalanceSingle{
		private final static  BalanceFactory instance = new BalanceFactory();
	}
	
	public static BalanceFactory getBalanceFactory(){
		return BalanceSingle.instance;
	}

	public Balance getBalance(String name){
		return BalanceType.getBalance(name);
	}
}
