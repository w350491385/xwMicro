package com.cn.server.user;

import com.cn.bean.User;

import java.util.List;

public interface UserService {
	
	void addUser(String name);
	
	String getName();
	
	User getUser(int id,User user);

	List<User> listUsers()throws Exception;
}
