package com.pan.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pan.entity.RolePermission;
import com.pan.service.RolePermissionService;

/**
 * @author 作者
 * @version 创建时间：2018年3月30日 上午9:25:19
 * 类说明
 */
public class RolePermissionServiceImpl implements RolePermissionService{
	
	@Autowired
	private RolePermissionService rolePermissionService;
	
	@Override
	public void deleteRolePermissionByPermissionId(String permissionId) {
		rolePermissionService.deleteRolePermissionByPermissionId(permissionId);
	}

	@Override
	public void deleteRolePermissionByRoleId(String roleId) {
		rolePermissionService.deleteRolePermissionByRoleId(roleId);
	}

	@Override
	public void addRolePermission(List<RolePermission> list) {
		rolePermissionService.addRolePermission(list);
	}

	@Override
	public void addRolePermission(RolePermission rolePermission) {
		List<RolePermission> list=new ArrayList<RolePermission>(1);
		list.add(rolePermission);
		this.addRolePermission(list);
	}

}
