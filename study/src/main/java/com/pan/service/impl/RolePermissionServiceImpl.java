package com.pan.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pan.entity.RolePermission;
import com.pan.mapper.RolePermissionMapper;
import com.pan.service.IRolePermissionService;

/**
 * @author 作者
 * @version 创建时间：2018年3月30日 上午9:25:19
 * 类说明
 */
@Service
public class RolePermissionServiceImpl implements IRolePermissionService{
	
	@Autowired
	private RolePermissionMapper rolePermissionMapper;
	
	@Override
	public int deleteRolePermissionByPermissionId(Long permissionId) {
		return rolePermissionMapper.deleteRolePermissionByPermissionId(permissionId);
	}

	@Override
	public int deleteRolePermissionByRoleId(Long roleId) {
		return rolePermissionMapper.deleteRolePermissionByRoleId(roleId);
	}

	@Override
	public int addRolePermission(List<RolePermission> list) {
		return rolePermissionMapper.batchAddRolePermission(list);
	}

	@Override
	public int addRolePermission(RolePermission rolePermission) {
		List<RolePermission> list=new ArrayList<RolePermission>(1);
		list.add(rolePermission);
		return this.addRolePermission(list);
	}

}
