package com.rpc.http;

import com.rpc.util.DispatcherUtils;
import com.rpc.util.ObjectCacheUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * rpc 控制中心
 */
public class ServerDispatcher extends HttpServlet {

	private static final long serialVersionUID = -5240824779593394246L;
	private static Logger logger = LoggerFactory.getLogger(ServerDispatcher.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = DispatcherUtils.getInterface(req.getRequestURI());
		ServerProtocolProcessor processor = ObjectCacheUtils.get(uri);
		try {
			if(processor != null)
				processor.exec(req, resp);
			else {
				try(OutputStream outputStream = resp.getOutputStream();
					PrintWriter writer = new PrintWriter(outputStream);){
					resp.setContentType("text/html");
					writer.write("processor is null");
				}
				logger.info(" get uri is {}" , uri);
				return;
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}
}
