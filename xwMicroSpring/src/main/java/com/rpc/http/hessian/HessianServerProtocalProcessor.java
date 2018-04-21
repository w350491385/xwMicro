package com.rpc.http.hessian;

import com.caucho.hessian.io.AbstractHessianOutput;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.HessianFactory;
import com.caucho.hessian.server.HessianSkeleton;
import com.rpc.http.ServerProtocolProcessor;
import com.rpc.rule.InterceptorRule;
import com.rpc.util.ObjectCacheUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * hessian 处理
 */
public class HessianServerProtocalProcessor implements ServerProtocolProcessor {

	private static Logger logger = LoggerFactory.getLogger(HessianServerProtocalProcessor.class);

	private HessianSkeleton hessianSkeleton;
	private boolean isDowngrade;// 是否降级 true:是；false：否
	private boolean isUse;// 是否可用
	private Object downgradeProcessor;
	private String clazzName;

	public HessianServerProtocalProcessor(HessianSkeleton hessianSkeleton, boolean isDowngrade, boolean isUse,
			Object downgradeProcessor, String clazzName) {
		super();
		this.hessianSkeleton = hessianSkeleton;
		this.isDowngrade = isDowngrade;
		this.isUse = isUse;
		this.downgradeProcessor = downgradeProcessor;
		this.clazzName = clazzName;
	}

	public void exec(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Hessian2Output out = null;
			try {
				out = new HessianFactory().createHessian2Output(response.getOutputStream());
				if (!isUse) {
					response.setStatus(410);
					out.writeReply(null);
					return;
				}
				if (!perHandler(request, response)) {
					out.writeReply(null);
					return;
				}
				if (isDowngrade
						&& ObjectCacheUtils.containClusterKey(this.clazzName, request.getHeader("clusterKey"))) {
					if (downgradeProcessor != null) {
						deal(request, out);
						return;
					}
					logger.warn("downgradeProcessor 引用对象不存在");
					return;
				}
			} finally {
				if (out != null)
					out.close();
			}
			hessianSkeleton.invoke(request.getInputStream(), response.getOutputStream());
		} finally {
			afterHandler(request, response);
		}
	}

	/**
	 * 后置拦截
	 * @param request
	 * @param response
	 */
	private void afterHandler(HttpServletRequest request, HttpServletResponse response) {
		List<InterceptorRule> rules = ObjectCacheUtils.getRules();
		for (InterceptorRule rule : rules) {
			try {
				rule.afterCompletion(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 前置拦截
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean perHandler(HttpServletRequest request, HttpServletResponse response) {
		List<InterceptorRule> rules = ObjectCacheUtils.getRules();
		for (InterceptorRule rule : rules) {
			try {
				if (!rule.preHandle(request, response))
					return false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * 降级处理
	 * @param request
	 * @param output
	 * @return
	 * @throws Exception
	 */
	private AbstractHessianOutput deal(HttpServletRequest request, Hessian2Output output) throws Exception {
//		Object[] objects = RequestUtils.getObjects(request);
//		MethodInvoker methodInvoker = new MethodInvoker();
//		methodInvoker.setTargetObject(downgradeProcessor);
//		methodInvoker.setTargetMethod(request.getHeader("methodName"));
//		methodInvoker.setArguments(objects);
//		methodInvoker.prepare();
//		Object object = methodInvoker.invoke();
//		output.writeReply(object);
//		return output;
		return null;
	}
	
	public boolean isDowngrade() {
		return isDowngrade;
	}

	public void setDowngrade(boolean isDowngrade) {
		this.isDowngrade = isDowngrade;
	}

	public boolean isUse() {
		return isUse;
	}

	public void setUse(boolean isUse) {
		this.isUse = isUse;
	}

	public String getClazzName() {
		return clazzName;
	}

}
