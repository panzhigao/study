package com.pan.mapper;

import com.pan.entity.User;

/**
 * 
 * @author Administrator
 *
 */
public interface UserMapper {
	/**
	 * 根据userId查找用户信息，唯一一条用户数据
	 * @param userId
	 * @return
	 */
	public User findByUserId(String userId);
	/**
	 * 保存用户信息
	 * @param user
	 */
	public void saveUser(User user);
}
