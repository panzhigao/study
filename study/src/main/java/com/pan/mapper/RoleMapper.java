package com.pan.mapper;

import java.util.List;
import com.pan.entity.Role;
import com.pan.query.QueryRole;

/**
 * 
 * @author Administrator
 *
 */
public interface RoleMapper extends BaseMapper<Role>{
	/**
	 * 查询分页
	 * @param queryRoleVO
	 * @return
	 */
	int getCountByParams(QueryRole queryRoleVO);
	/**
	 * 获取用户选中角色
	 * @param userId
	 * @return
	 */
	List<Role> getRoleSelectedByUserId(String userId);
	/**
	 * 获取用户的角色
	 * @param userId
	 * @return
	 */
	List<String> getRoleByUserId(String userId);
}
