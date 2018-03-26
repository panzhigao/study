package com.pan.service;

import java.util.List;
import java.util.Map;

import com.pan.dto.Tree;
import com.pan.entity.Role;

/**
 * 
 * @author Administrator
 *
 */
public interface RoleService {
	/**
	 * 添加角色
	 */
	public void addRole(Role role);
	/**
	 * 多条件查询，支持分页
	 * @param params 条件有userId,articleId
	 * @return
	 */
	public Map<String,Object> findByParams(Map<String,Object> params);
	/**
	 * 删除角色
	 * @param permissionId
	 */
	public void deleteRole(String roleId);
	/**
	 * 为角色分配权限
	 * @param roleId
	 * @param permissions
	 */
	public void allocatePermissionToRole(String roleId,String[] permissions);
	/**
	 * 查找唯一角色
	 * @param roleId
	 * @return
	 */
	public Role findByRoleId(String roleId);
	/**
	 * 根据角色id获取权限层级树数据
	 * @return
	 */
	public List<Tree> getRoleTreeData(String roleId);
	/**
	 * 获取所有角色
	 * @return
	 */
	public List<Role> findAll();
	/**
	 * 获取用户角色id
	 * @param userId
	 * @return
	 */
	public List<String> getRoleByUserId(String userId);
	/**
	 * 重新缓存指定角色用户权限
	 * @param roleId
	 */
	public void recachePermissionByRoleId(String roleId);
	/**
	 * 编辑角色
	 */
	public void updateRole(Role role);
}
