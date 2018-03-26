package com.pan.mapper;

import java.util.List;
import java.util.Map;
import com.pan.entity.Role;

/**
 * 
 * @author Administrator
 *
 */
public interface RoleMapper {
	/**
	 * 分页查询角色列表
	 * @param params
	 * @return
	 */
	public List<Role> findByParams(Map<String,Object> params);
	/**
	 * 查询分页
	 * @param params
	 * @return
	 */
	public int getCountByParams(Map<String,Object> params);
	/**
	 * 新增角色
	 * @param collection
	 */
	public void addRole(Role role);
	/**
	 * 删除角色
	 * @param userId
	 * @param articleId
	 */
	public void deleteRole(String roleId);
	/**
	 * 获取用户选中角色
	 * @param userId
	 * @return
	 */
	public List<Role> getRoleSelectedByUserId(String userId);
	/**
	 * 获取用户的角色
	 * @param userId
	 * @return
	 */
	public List<String> getRoleByUserId(String userId);
	/**
	 * 编辑角色
	 * @param role
	 */
	public void updateRole(Role role);
}
