package com.pan.service;

import com.pan.entity.User;

/**
 * 
 * @author Administrator
 *
 */
public interface UserService {
	/**
	 * 保存用户信息
	 * @param user
	 */
	public void saveUser(User user);
	/**
	 * 通过用户名查找唯一用户数据
	 * @param username
	 */
	public User findByUsername(String username);
	/**
	 * 校验用户登陆
	 * @param user
	 * @return
	 */
	public void checkLogin(User user);
}
