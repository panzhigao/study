package com.pan.service;

import java.util.List;
import com.pan.entity.UserExtension;
import com.pan.query.QueryUserExtension;

/**
 * @author 作者
 * @version 创建时间：2018年5月29日 下午2:10:29
 * 类说明
 */
public interface UserExtensionService {
	
	public UserExtension findByUserId(String userId);
	/**
	 * 多条件查询，支持分页
	 * @param queryUserExtensionVO
	 * @return
	 */
	public List<UserExtension> findByParams(QueryUserExtension queryUserExtensionVO);
	/**
	 * 修改
	 * @param userExtension
	 */
	public void updateByUserId(UserExtension userExtension);
	/**
	 * 查询条数
	 * @param extensionVO
	 * @return
	 */
	public int countByParams(QueryUserExtension extensionVO);
	
}
