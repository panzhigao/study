package com.pan.mapper;

import com.pan.entity.UserExtension;

/**
 * 
 * @author Administrator
 *
 */
public interface UserExtensionMapper extends BaseMapper<UserExtension>{
	/**
	 * 用户评论数，连续登录天数，连续签到天数，文章数变更
	 * @return
	 */
	int increaseCounts(UserExtension userExtension);
}
