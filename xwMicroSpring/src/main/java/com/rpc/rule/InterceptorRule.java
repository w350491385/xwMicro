package com.rpc.rule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截规则 
 */
public interface InterceptorRule {

	boolean preHandle(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	void afterCompletion(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
}
