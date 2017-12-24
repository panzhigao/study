package com.pan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pan.entity.User;
import com.pan.mapper.UserMapper;
import com.pan.service.UserService;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;
	
	public void saveUser(User user) {
		userMapper.saveUser(user);
	}
}
