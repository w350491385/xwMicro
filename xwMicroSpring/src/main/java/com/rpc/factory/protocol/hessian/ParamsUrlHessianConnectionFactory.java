package com.rpc.factory.protocol.hessian;

import com.caucho.hessian.client.HessianConnection;
import com.caucho.hessian.client.HessianURLConnection;
import com.caucho.hessian.client.HessianURLConnectionFactory;
import com.rpc.factory.ParamsData;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

/**
 * 继承 HessianURLConnectionFactory 重写相应方法
 */
public class ParamsUrlHessianConnectionFactory extends HessianURLConnectionFactory {

	private Map<String, String> headerParams = null;
	private Object[] args = null;

	public ParamsUrlHessianConnectionFactory(ParamsData paramsData) {
		super();
		this.headerParams = paramsData.getHeaderParams();
		this.args = paramsData.getValues();
	}

	@Override
	public HessianConnection open(URL url) throws IOException {
		HessianURLConnection hessianURLConnection = (HessianURLConnection) super.open(url);
		if (null != headerParams && !headerParams.isEmpty()) {
			Set<String> keySet = headerParams.keySet();
			for (String key : keySet) {
				// 向报文头中添加参数
				hessianURLConnection.addHeader(key, headerParams.get(key));
			}
		}
//		if (null != args) {
//			ObjectMapper objectMapper = new ObjectMapper();
//			ValueTypes[] values = new ValueTypes[args.length];
//			for (int i = 0; i < args.length; i++) {
//				values[i] = new ValueTypes(args[i].getClass(), objectMapper.writeValueAsString(args[i]));
//			}
//			hessianURLConnection.addHeader("valueTypes", objectMapper.writeValueAsString(values));
//		}
		return hessianURLConnection;
	}

}
