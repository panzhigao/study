package com.pan.service;

import com.pan.entity.User;
import com.pan.entity.UserExtension;

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
	 * 根据手机号查找唯一用户信息
	 * @param username
	 * @return
	 */
	public User findByUserTelephone(String telephone);
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
	/**
	 * 修改用户信息
	 */
	public void updateUserInfo(User user,UserExtension userExtension);
	/**
	 * 查找用户其他信息
	 */
	public  UserExtension findByUserId(String userId);
}
