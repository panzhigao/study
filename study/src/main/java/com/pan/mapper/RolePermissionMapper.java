package com.pan.mapper;


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
}
