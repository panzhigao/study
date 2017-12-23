package com.pan.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pan.entity.User;
import com.pan.mapper.UserMapper;
import com.pan.test.base.BaseTest;


public class UserTest extends BaseTest{
	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void test1(){
		User user = userMapper.findByUserId("1");
		System.out.println(user);
	}
	
}
