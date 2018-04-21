package com.rpc.util;

import com.rpc.Order;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * order 排序 
 * @author Administrator
 */
public class OrderSortUtils implements Comparator<Object> {

	private final static OrderSortUtils INSTANCE = new OrderSortUtils();
	
	private OrderSortUtils(){
	}
	
	public int compare(Object o1, Object o2) {
		boolean p1 = (o1 instanceof Order);
		boolean p2 = (o2 instanceof Order);
		if (p1 && !p2) {
			return -1;
		}
		else if (p2 && !p1) {
			return 1;
		}
		int i1 = ((Order)o1).getOrderLevel();
		int i2 = ((Order)o2).getOrderLevel();
		return (i1 < i2) ? -1 : (i1 > i2) ? 1 : 0;
	}
	
	public static void sort(List<?> list) {
		if (list.size() > 1) {
			Collections.sort(list, INSTANCE);
		}
	}

}
