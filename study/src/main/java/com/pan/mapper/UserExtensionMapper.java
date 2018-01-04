package com.pan.mapper;

import com.pan.entity.UserExtension;

/**
 * 
 * @author Administrator
 *
 */
public interface UserExtensionMapper {
	/**
	 * 根据userId查找用户信息，唯一一条用户数据
	 * @param userId
	 * @return
	 */
	public UserExtension findByUserId(String userId);
	/**
	 * 保存用户拓展信息
	 * @param userExtension
	 */
	public void saveUserExtension(UserExtension userExtension);
	/**
	 * 根据用户id更新用户拓展信息
	 * @param userExtension
	 */
	public void updateUserExtensionByUserId(UserExtension userExtension);
}
