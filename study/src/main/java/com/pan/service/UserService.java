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
	 * 根据用户名查找唯一用户信息
	 * @param username
	 * @return
	 */
	public User findByUsername(String username);
	/**
	 * 校验用户登陆
	 * @param user
	 * @return
	 */
	public User checkLogin(User user);
}
