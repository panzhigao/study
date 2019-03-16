package com.pan.service;

import com.pan.entity.UserExtension;
import com.pan.query.QueryUserExtension;

/**
 * @author 作者
 * @version 创建时间：2018年5月29日 下午2:10:29
 * 类说明
 */
public interface UserExtensionService extends BaseService<UserExtension>{
	/**
	 * 根据userId获取用户拓展信息
	 * @param userId
	 * @return
	 */
	UserExtension findByUserId(String userId);
	/**
	 * 修改
	 * @param userExtension
	 */
	void updateByUserId(UserExtension userExtension);
	/**
	 * 查询条数
	 * @param extensionVO
	 * @return
	 */
	int countByParams(QueryUserExtension extensionVO);
	/**
	 * 用户评论数，连续登录天数，连续签到天数，文章数变更
	 * @param userExtension
	 * @return
	 */
	int increaseCounts(UserExtension userExtension);
}
