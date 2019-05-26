package com.pan.service;

import com.pan.entity.UserExtension;

/**
 * @author 作者
 * @version 创建时间：2018年5月29日 下午2:10:29
 * 类说明
 */
public interface IUserExtensionService extends BaseService<UserExtension>{
	/**
	 * 用户评论数，连续登录天数，连续签到天数，文章数变更
	 * @param userExtension
	 * @return
	 */
	int increaseCounts(UserExtension userExtension);
}
