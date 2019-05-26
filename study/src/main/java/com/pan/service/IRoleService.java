package com.pan.service;

import java.util.List;
import com.pan.dto.Tree;
import com.pan.entity.Role;

/**
 * 
 * @author Administrator
 *
 */
public interface IRoleService extends BaseService<Role>{
	/**
	 * 新增角色
	 * @param role
	 */
	void addRole(Role role);
	/**
	 * 删除角色
	 * @param roleId 角色id
	 */
	void deleteRole(Long roleId);
	/**
	 * 为角色分配权限
	 * @param roleId
	 * @param permissions
	 */
	void allocatePermissionToRole(Long roleId,Long[] permissions);
	/**
	 * 根据角色id获取权限层级树数据
	 * @return
	 */
	List<Tree> getRoleTreeData(Long roleId);
	/**
	 * 获取用户角色id集合
	 * @param userId
	 * @return
	 */
	List<Long> getRoleIdsByUserId(Long userId);
	/**
	 * 重新缓存指定角色用户权限
	 * @param roleId
	 */
	void reCachePermissionByRoleId(Long roleId);
	/**
	 * 编辑角色
	 * @param role
	 */
	void updateRole(Role role);
}
