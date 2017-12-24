package com.pan.test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pan.entity.User;
import com.pan.mapper.UserMapper;
import com.pan.test.base.BaseTest;
import com.pan.util.PasswordUtils;

/**
 * 用户测试类
 * @author Administrator
 *
 */
public class UserTest extends BaseTest{
	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void test1(){
		User user = userMapper.findByUserId("1");
		System.out.println(user);
	}
	
	/**
	 * 测试用户插入操作
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	@Test
	public void test2() throws NoSuchAlgorithmException, UnsupportedEncodingException{
		User user=new User();
		user.setUserId("aaaa123");
		user.setUsername("潘志高");
		String password="123456";
		String encryptPassword=PasswordUtils.getEncryptedPwd(password);
		user.setPassword(encryptPassword);
		user.setCreateTime(new Date());
		user.setStatus(User.STATUS_NORMAL);
		userMapper.saveUser(user);
	}
	
}
