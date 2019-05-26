package com.pan.service;

import java.util.List;
import com.pan.dto.Tree;
import com.pan.dto.TreeNode;
import com.pan.entity.Permission;

/**
 * 
 * @author Administrator
 *
 */
public interface IPermissionService extends BaseService<Permission>{
	/**
	 * 添加权限
	 */
	void addPermission(Permission permission);
	/**
	 * 根据权限id删除权限
	 * @param permissionId
	 */
	int deleteByPermissionId(Long permissionId);
	/**
	 * 获取所有权限
	 * @return
	 */
	List<TreeNode> getTreeData();
	/**
	 * 根据角色id获取权限层级树数据
	 * @return
	 */
	List<Tree> getPermissionTreeData(Long roleId);
	/**
	 * 查找角色所有权限
	 * @param roleId
	 * @return
	 */
	List<Permission> getPermissionByRoleId(Long roleId);
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
	List<Permission> findPermissionsByUserId(Long userId);
	
}
