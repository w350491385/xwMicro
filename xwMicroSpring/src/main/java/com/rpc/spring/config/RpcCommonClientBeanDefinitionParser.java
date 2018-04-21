package com.rpc.spring.config;

import com.rpc.spring.config.tag.MethodConfig;
import com.rpc.spring.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 客户端标签解析
 */
public class RpcCommonClientBeanDefinitionParser implements BeanDefinitionParser {
	
private static Logger logger = LoggerFactory.getLogger(RpcCommonClientBeanDefinitionParser.class);
	
	private Class<?> clazz;
	private boolean required;	

	public RpcCommonClientBeanDefinitionParser(Class<?> clazz, boolean required) {
		super();
		this.clazz = clazz;
		this.required = required;
	}

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		return parse(element,parserContext,clazz,required);
	}

	private BeanDefinition parse(Element element, ParserContext parserContext, Class<?> clazz2, boolean required2) {
		RootBeanDefinition rootBean = new RootBeanDefinition();
		rootBean.setBeanClass(clazz);
		rootBean.setLazyInit(false);
		String id = element.getAttribute("id");
		if(id == null ||"".equals(id)){
			String name = element.getAttribute("name");
			if(name == null || "".equals(name)){
				name =  element.getAttribute("interfaceClass");
			}
			id = name;
			if(parserContext.getRegistry().containsBeanDefinition(name)){
				logger.error("parse element id = "+name+" already exsits");
			}
		}
		if(id !=null && id.length()>0){
			parserContext.getRegistry().registerBeanDefinition(id, rootBean);
			rootBean.getPropertyValues().add(Constant.ID, id);
		}		
		rootBean.getPropertyValues().add(Constant.NAME, element.getAttribute(Constant.NAME));
		rootBean.getPropertyValues().add(Constant.INTERFACECLASS, element.getAttribute(Constant.INTERFACECLASS));
		rootBean.getPropertyValues().add(Constant.URL, element.getAttribute(Constant.URL));
		rootBean.getPropertyValues().add(Constant.TIMEOUT, element.getAttribute(Constant.TIMEOUT));
		rootBean.getPropertyValues().add(Constant.RETRIES, element.getAttribute(Constant.RETRIES));
		rootBean.getPropertyValues().add(Constant.PROTOCOL, element.getAttribute(Constant.PROTOCOL));
		rootBean.getPropertyValues().add(Constant.CLUSTER, element.getAttribute(Constant.CLUSTER));
		rootBean.getPropertyValues().add(Constant.BALANCE, element.getAttribute(Constant.BALANCE));
		rootBean.getPropertyValues().add(Constant.GROUP, element.getAttribute(Constant.GROUP));

		//获取map 键值对数据
		ManagedMap managedMap = addmethodParameters(id,element.getChildNodes());
		if (managedMap != null)
			rootBean.getPropertyValues().addPropertyValue(Constant.METHODRETURNTYPE, managedMap);

		return rootBean;
	}

	private ManagedMap addmethodParameters(String id,NodeList nodeList) {
		if (nodeList != null && nodeList.getLength() > 0) {
			ManagedMap parameters = null;
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node instanceof Element) {
					if (Constant.CLIENTMETHOD.equals(node.getNodeName())) {
						if (parameters == null) {
							parameters = new ManagedMap();
						}
						RootBeanDefinition methodBeanDefinition = new RootBeanDefinition();
						methodBeanDefinition.setBeanClass(MethodConfig.class);
						methodBeanDefinition.setLazyInit(false);

						String method = ((Element) node).getAttribute(Constant.METHOD);
						String returnType = ((Element) node).getAttribute(Constant.RETURNTYPE);
						String reties = ((Element) node).getAttribute(Constant.RETRIES);
						methodBeanDefinition.getPropertyValues().add(Constant.METHOD,method);
						methodBeanDefinition.getPropertyValues().add(Constant.RETURNTYPE,returnType);
						methodBeanDefinition.getPropertyValues().add(Constant.RETRIES,reties);
						methodBeanDefinition.getPropertyValues().add(Constant.TIMEOUT,((Element) node).getAttribute(Constant.TIMEOUT));
						methodBeanDefinition.getPropertyValues().add(Constant.ASYNC,((Element) node).getAttribute(Constant.ASYNC));
						String name = id + "." + method;
						BeanDefinitionHolder methodBeanDefinitionHolder = new BeanDefinitionHolder(
								methodBeanDefinition, name);
						parameters.put(method,methodBeanDefinitionHolder);
					}
				}
			}
			return parameters;
		}
		return null;
	}

}
