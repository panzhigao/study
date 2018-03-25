package com.pan.mapper;

import java.util.List;

import com.pan.entity.RolePermission;


/**
 * 
 * @author Administrator
 *
 */
public interface RolePermissionMapper {
	/**
	 * 
	 * @param permissionId
	 */
	public void deleteRolePermissionByPermissionId(String permissionId);
	/**
	 * 
	 * @param roleId
	 */
	public void deleteRolePermissionByRoleId(String roleId);
	/**
	 * 新增角色权限
	 */
	public void addRolePermission(List<RolePermission> list);
}
