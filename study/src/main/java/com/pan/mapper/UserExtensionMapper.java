package com.pan.mapper;

import com.pan.entity.UserExtension;

/**
 * 
 * @author Administrator
 *
 */
public interface UserExtensionMapper extends BaseMapper<UserExtension>{
	/**
	 * 根据userId查找用户信息，唯一一条用户数据
	 * @param userId
	 * @return
	 */
	UserExtension findByUserId(String userId);
	/**
	 * 根据用户id更新用户拓展信息
	 * @param userExtension
	 */
	void updateUserExtensionByUserId(UserExtension userExtension);
	/**
	 * 用户评论数，连续登录天数，连续签到天数，文章数变更
	 * @return
	 */
	int increaseCounts(UserExtension userExtension);
}
