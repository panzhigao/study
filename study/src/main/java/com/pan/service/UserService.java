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
	 * 根据用户Id查找唯一用户信息
	 * @param username
	 * @return
	 */
	public User findByUserid(String userId);
	/**
	 * 校验用户登陆
	 * @param user
	 * @return
	 */
	public User checkLogin(User user);
	/**
	 * 更新用户登陆时间
	 * @param userId
	 */
	public void updateUserLastLoginTime(String userId);
}
