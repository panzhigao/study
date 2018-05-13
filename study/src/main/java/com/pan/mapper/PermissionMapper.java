package com.pan.mapper;

import java.util.List;
import java.util.Map;
import com.pan.entity.Permission;

/**
 * 
 * @author Administrator
 *
 */
public interface PermissionMapper {
	/**
	 * 分页查询权限
	 * @param params
	 * @return
	 */
	public List<Permission> findByParams(Map<String,Object> params);
	/**
	 * 查询分页
	 * @param params
	 * @return
	 */
	public int getCountByParams(Map<String,Object> params);
	/**
	 * 新增权限
	 * @param collection
	 */
	public void addPermission(Permission permission);
	/**
	 * 删除权限
	 * @param userId
	 * @param articleId
	 */
	public void deletePermission(String permissionId);
	/**
	 * 查找所有权限
	 * @return
	 */
	public List<Permission> findAll();
	/**
	 * 查找角色选中的权限
	 * @return
	 */
	public List<Permission> getPermissionSelectedByRoleId(String roleId);
	/**
	 * 查找角色所有权限
	 * @param roleId
	 * @return
	 */
	public List<Permission> getPermissionByRoleId(String roleId);
	/**
	 * 更新权限
	 */
	public void updatePermission(Permission permission);
	/**
	 * 获取用户权限
	 * @param userId
	 * @return
	 */
	public List<Permission> findPermissionsByUserId(String userId);
}
