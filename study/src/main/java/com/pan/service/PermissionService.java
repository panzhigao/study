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
public interface PermissionService {
	/**
	 * 添加权限
	 */
	public void addPermission(Permission permission);
	/**
	 * 多条件查询，支持分页
	 * @param params 条件有userId,articleId
	 * @return
	 */
	public Map<String,Object> findByParams(Map<String,Object> params);
	/**
	 * 删除权限
	 * @param permissionId
	 */
	public void deletePermission(String permissionId);
	/**
	 * 获取所有权限
	 * @return
	 */
	public List<TreeNode> getTreeData();
	/**
	 * 根据角色id获取权限层级树数据
	 * @return
	 */
	public List<Tree> getPermissionTreeData(String roleId);
	/**
	 * 查找角色所有权限
	 * @param roleId
	 * @return
	 */
	public List<Permission> getPermissionByRoleId(String roleId);
	/**
	 * 根据permissionId查找唯一数据
	 * @param permissionId
	 * @return
	 */
	public Permission getByPermissionId(String permissionId);
	/**
	 * 更新权限
	 */
	public void updatePermission(Permission permission);
}
