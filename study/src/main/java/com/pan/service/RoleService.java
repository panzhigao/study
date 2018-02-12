package com.pan.service;

import java.util.Map;
import com.pan.entity.Role;

/**
 * 
 * @author Administrator
 *
 */
public interface RoleService {
	/**
	 * 添加角色
	 */
	public void addRole(Role role);
	/**
	 * 多条件查询，支持分页
	 * @param params 条件有userId,articleId
	 * @return
	 */
	public Map<String,Object> findByParams(Map<String,Object> params);
	/**
	 * 删除角色
	 * @param permissionId
	 */
	public void deleteRole(String roleId);
}
