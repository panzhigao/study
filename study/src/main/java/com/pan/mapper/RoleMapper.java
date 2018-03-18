package com.pan.mapper;

import java.util.List;
import java.util.Map;

import com.pan.entity.Role;
import com.pan.entity.RolePermission;

/**
 * 
 * @author Administrator
 *
 */
public interface RoleMapper {
	/**
	 * 分页查询角色列表
	 * @param params
	 * @return
	 */
	public List<Role> findByParams(Map<String,Object> params);
	/**
	 * 查询分页
	 * @param params
	 * @return
	 */
	public int getCountByParams(Map<String,Object> params);
	/**
	 * 新增角色
	 * @param collection
	 */
	public void addRole(Role role);
	/**
	 * 删除角色
	 * @param userId
	 * @param articleId
	 */
	public void deleteRole(String roleId);
	/**
	 * 新增权限
	 * @param roleId
	 * @param permissions
	 */
	public void addRolePermission(List<RolePermission> list);
}
