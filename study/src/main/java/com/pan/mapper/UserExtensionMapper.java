package com.pan.mapper;

import java.util.List;
import com.pan.entity.UserExtension;
import com.pan.query.QueryUserExtension;

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
	/**
	 * 多条件查询用户拓展信息，支持分页
	 * @param extensionVO
	 */
	public List<UserExtension> findByParams(QueryUserExtension extensionVO);
	/**
	 * 用户评论数，连续登录天数，连续签到天数，文章数变更
	 * @return
	 */
	public int increaseCounts(UserExtension userExtension);
	/**
	 * 查询条数
	 * @return
	 */
	public int countByParams(QueryUserExtension extensionVO);
}
