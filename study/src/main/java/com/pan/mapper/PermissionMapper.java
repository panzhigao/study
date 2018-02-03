package com.pan.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.pan.entity.Collection;
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
}
