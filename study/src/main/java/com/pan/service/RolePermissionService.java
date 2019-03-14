package com.pan.service;

import java.util.List;

import com.pan.entity.RolePermission;

/**
 * @author 作者
 * @version 创建时间：2018年3月30日 上午9:23:16
 * 类说明
 */
public interface RolePermissionService {
	/**
	 * 根据权限删除角色权限信息
	 * @param permissionId 权限id
	 * @return
	 */
	int deleteRolePermissionByPermissionId(String permissionId);
	/**
	 * 
	 * @param roleId 角色id
	 * @return
	 */
	int deleteRolePermissionByRoleId(String roleId);
	/**
	 * 新增角色权限
	 * @param list 角色权限集合
	 * @return
	 */
	int addRolePermission(List<RolePermission> list);
	/**
	 * 新增角色权限
	 * @param rolePermission
	 * @return
	 */
	int addRolePermission(RolePermission rolePermission);
	
}
