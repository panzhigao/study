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
	 * 根据权限id,删除权限关联角色信息
	 * @param permissionId
	 * @return  删除条数
	 */
	int deleteRolePermissionByPermissionId(String permissionId);
	/**
	 * 根据角色id,删除权限关联角色信息
	 * @param roleId
	 * @return  删除条数
	 */
	int deleteRolePermissionByRoleId(String roleId);
	/**
	 * 批量新增角色权限
	 * @param list
	 * @return  删除条数
	 */
	int addRolePermission(List<RolePermission> list);
}
