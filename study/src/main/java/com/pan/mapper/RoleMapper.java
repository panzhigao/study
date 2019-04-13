package com.pan.mapper;

import java.util.List;
import com.pan.entity.Role;

/**
 * 
 * @author Administrator
 *
 */
public interface RoleMapper extends BaseMapper<Role>{
	/**
	 * 获取用户选中角色
	 * @param userId
	 * @return
	 */
	List<Role> getRoleSelectedByUserId(Long userId);
	/**
	 * 获取用户的角色id集合
	 * @param userId
	 * @return
	 */
	List<Long> getRoleIdsByUserId(Long userId);
	/**
	 * 根据角色id查询多条角色信息
	 * @param roleIds
	 * @return
	 */
	List<Role> findByRoleIds(Long[] roleIds);
}
