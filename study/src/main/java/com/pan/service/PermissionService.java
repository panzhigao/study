package com.pan.service;

import java.util.List;
import java.util.Map;

import com.pan.dto.Tree;
import com.pan.dto.TreeNode;
import com.pan.entity.Permission;

/**
 * 
 * @author Administrator
 *
 */
public interface PermissionService extends BaseService<Permission>{
	/**
	 * 添加权限
	 */
	void addPermission(Permission permission);
	/**
	 * 删除权限
	 * @param id
	 */
	int deletePermission(Long id);
	/**
	 * 获取所有权限
	 * @return
	 */
	List<TreeNode> getTreeData();
	/**
	 * 根据角色id获取权限层级树数据
	 * @return
	 */
	List<Tree> getPermissionTreeData(String roleId);
	/**
	 * 查找角色所有权限
	 * @param roleId
	 * @return
	 */
	List<Permission> getPermissionByRoleId(String roleId);
	/**
	 * 根据permissionId查找唯一数据
	 * @param permissionId
	 * @return
	 */
	Permission getByPermissionId(String permissionId);
	/**
	 * 更新权限
	 */
	void updatePermission(Permission permission);
	/**
	 * 获取所有权限
	 * @return
	 */
	List<Permission> getAll();
	/**
	 * 获取用户的所有权限
	 * @param userId
	 * @return
	 */
	List<Permission> findPermissionsByUserId(String userId);
	
}
