package com.pan.service;

import java.util.List;
import java.util.Map;
import com.pan.dto.Tree;
import com.pan.entity.Role;
import com.pan.query.QueryRole;

/**
 * 
 * @author Administrator
 *
 */
public interface RoleService extends BaseService<Role>{
	/**
	 * 新增角色
	 * @param role
	 */
	void addRole(Role role);
	/**
	 * 删除角色
	 * @param roleId 角色id
	 */
	void deleteRole(String roleId);
	/**
	 * 为角色分配权限
	 * @param roleId
	 * @param permissions
	 */
	void allocatePermissionToRole(String roleId,String[] permissions);
	/**
	 * 查找唯一角色
	 * @param roleId
	 * @return
	 */
	Role findByRoleId(String roleId);
	/**
	 * 根据角色id获取权限层级树数据
	 * @return
	 */
	List<Tree> getRoleTreeData(String roleId);
	/**
	 * 获取用户角色id集合
	 * @param userId
	 * @return
	 */
	List<String> getRoleIdsByUserId(String userId);
	/**
	 * 重新缓存指定角色用户权限
	 * @param roleId
	 */
	void reCachePermissionByRoleId(String roleId);
	/**
	 * 编辑角色
	 * @param role
	 */
	void updateRole(Role role);
}
