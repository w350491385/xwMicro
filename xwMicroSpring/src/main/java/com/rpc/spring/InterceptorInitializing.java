package com.rpc.spring;

import com.rpc.rule.InterceptorRule;
import com.rpc.util.ObjectCacheUtils;
import com.rpc.util.OrderSortUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InterceptorInitializing implements InitializingBean,BeanFactoryAware {

	private ConfigurableListableBeanFactory beanFactory;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Set<String> ruleSet = new HashSet<>();
		String[] interceptorRules = beanFactory.getBeanNamesForType(InterceptorRule.class, true, false);
		List<InterceptorRule> ruleList = new ArrayList<InterceptorRule>();
		for (String trName : interceptorRules) {
			if (!ruleSet.contains(trName) && beanFactory.isTypeMatch(trName, InterceptorRule.class)) {
				ruleList.add(beanFactory.getBean(trName, InterceptorRule.class));
				ruleSet.add(trName);
			}
		}
		OrderSortUtils.sort(ruleList);
		ObjectCacheUtils.setRules(ruleList);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
	}

}
