package com.cn.controller;

import com.cn.bean.User;
import com.cn.server.user.UserService;
import com.rpc.http.RequestContext;
import com.xw.vo.SelfParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户动态控制类
 */
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/test1", method = RequestMethod.GET)
	public Object test1(@RequestParam(value = "name",required = false)String name,@RequestParam(value = "address") String address ,SelfParam param){
		User user = new User();
		user.setName(name);
		user.setAddress(address);
		return param;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public Object test1(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userService.getUser(10,new User());
		return user;
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/test2", method = RequestMethod.GET)
	public Object test2(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return userService.listUsers();
	}


	/**
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getName", method = RequestMethod.GET)
	public Object getName(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String name = userService.getName();
		if (name == null)
			name ="异步获取到空";
		String requestContextName = (String) RequestContext.getInstance().getResult();
		return name + "------requestContextName == " + requestContextName;
	}
	
}
