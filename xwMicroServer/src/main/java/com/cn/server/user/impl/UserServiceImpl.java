package com.cn.server.user.impl;

import com.cn.bean.User;
import com.cn.server.user.UserService;
import com.rpc.annotation.ReadOnly;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Override
	public void addUser(String name) {
		System.out.println("addUser = "+name);
	}

	@ReadOnly
	@Override
	public String getName() {
		System.out.println("---------getName()-------------");
		return "hello world";
	}

	@ReadOnly
	@Override
	public User getUser(int id, User user) {
		User users = new User();
		users.setId(id);
		users.setAddress("深圳市南山区南邮大厦");
		users.setAge(28);
		users.setName("黄栋彬");
		return users;
	}

	@Override
	public List<User> listUsers() throws Exception {
		int i = 8/0;
		return new ArrayList<>(Arrays.asList(new User()));
	}
}
