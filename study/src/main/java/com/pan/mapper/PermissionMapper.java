package com.pan.mapper;

import java.util.List;
import com.pan.entity.Permission;

/**
 * 
 * @author Administrator
 *
 */
public interface PermissionMapper extends BaseMapper<Permission>{
	/**
	 * 查找所有权限
	 * @return
	 */
	List<Permission> findAll();
	/**
	 * 查找角色选中的权限
	 * @return
	 */
	List<Permission> getPermissionSelectedByRoleId(String roleId);
	/**
	 * 查找角色所有权限
	 * @param roleId
	 * @return
	 */
	List<Permission> getPermissionByRoleId(String roleId);
	/**
	 * 获取用户权限
	 * @param userId
	 * @return
	 */
	List<Permission> findPermissionsByUserId(String userId);
	/**
	 * 根据权限id查询唯一一条权限信息
	 * @param permissionId
	 * @return
	 */
	Permission selectByPermissionId(String permissionId);
}
