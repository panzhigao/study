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
	 * @param roleId
	 * @return
	 */
	List<Permission> getPermissionSelectedByRoleId(Long roleId);
	/**
	 * 查找角色所有权限
	 * @param roleId
	 * @return
	 */
	List<Permission> getPermissionByRoleId(Long roleId);
	/**
	 * 获取用户权限
	 * @param userId
	 * @return
	 */
	List<Permission> findPermissionsByUserId(Long userId);
	/**
	 * 根据父节点查询子权限
	 * @param userId
	 * @return
	 */
	List<Permission> findByParentId(Long parentId);
    /**
     * 根据父节点查询子节点个数
     * @param parentId
     * @return
     */
    int countByParentId(Long parentId);
}
