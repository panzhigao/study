package com.pan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pan.common.exception.BusinessException;
import com.pan.dto.Tree;
import com.pan.entity.Permission;
import com.pan.entity.Role;
import com.pan.entity.RolePermission;
import com.pan.mapper.RoleMapper;
import com.pan.service.PermissionService;
import com.pan.service.RoleService;
import com.pan.util.IdUtils;
import com.pan.util.JedisUtils;
import com.pan.util.JsonUtils;
import com.pan.util.ValidationUtils;

@Service
public class RoleServiceImpl implements RoleService{
	
	private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private PermissionService permissionService;
	
	@Override
	public void addRole(Role role) {
		logger.info("新增角色：{}",role);
		ValidationUtils.validateEntity(role);
		role.setCreateTime(new Date());
		role.setRoleId(IdUtils.generateRoleId());
		roleMapper.addRole(role);
	}

	@Override
	public Map<String, Object> findByParams(Map<String, Object> params) {
		Map<String,Object> pageData=new HashMap<String, Object>(2);
		List<Role> list = new ArrayList<Role>();
		try {
			logger.info("分页权限参数为:{}", JsonUtils.toJson(params));
			int total=roleMapper.getCountByParams(params);
			//当查询记录大于0时，查询数据库记录，否则直接返回空集合
			if(total>0){				
				list = roleMapper.findByParams(params);
			}
			pageData.put("data", list);
			pageData.put("total", total);
			pageData.put("code", "200");
			pageData.put("msg", "");
		} catch (Exception e) {
			logger.error("分页查询权限异常", e);
		}
		return pageData;
	}
	
	//TODO 删除角色，连同其下的权限删除
	@Override
	public void deleteRole(String roleId) {
		roleMapper.deleteRole(roleId);
	}

	@Override
	public void allocatePermissionToRole(String roleId, String[] permissions) {
		Role role = findByRoleId(roleId);
		if(role==null){
			throw new BusinessException("该角色不存在");
		}
		//TODO 增加日志
		//删除该角色下的所有权限，再重新添加
		roleMapper.deleteRolePermissionByRoleId(roleId);
		List<RolePermission> list=new ArrayList<RolePermission>();
		for (String string : permissions) {
			RolePermission rolePermission=new RolePermission();
			rolePermission.setPermissionId(string);
			rolePermission.setRoleId(roleId);
			rolePermission.setCreateTime(new Date());
			list.add(rolePermission);
		}
		roleMapper.addRolePermission(list);
		recachePermissionByRoleId(roleId);
	}

	@Override
	public Role findByRoleId(String roleId) {
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("roleId", roleId);
		List<Role> list = roleMapper.findByParams(params);
		if(list.size()==1){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public List<Tree> getRoleTreeData(String userId) {
		if(StringUtils.isBlank(userId)){
			return new ArrayList<Tree>();
		}
		List<Role> list = this.roleMapper.getRoleSelectedByUserId(userId);
		List<Tree> nodes=new ArrayList<Tree>(20);
		for (Role role : list) {
			Tree roleTree=new Tree();
			roleTree.setTitle(role.getRoleName());
			roleTree.setValue(role.getRoleId());
			roleTree.setId(role.getRoleId());
			roleTree.setpId("0");
			if(!StringUtils.equals(role.getMarker(),"0")){
				roleTree.setChecked(true);
			}
			nodes.add(roleTree);
		}
		return Tree.buildTree(nodes);
	}

	@Override
	public List<Role> findAll() {
		return roleMapper.findByParams(new HashMap<String,Object>());
	}

	@Override
	public List<String> getRoleByUserId(String userId) {
		return this.roleMapper.getRoleByUserId(userId);
	}
	
	/**
	 * 重新缓存指定角色用户权限
	 * @param roleId
	 */
	@Override
	public void recachePermissionByRoleId(String roleId){
		try {
			List<Permission> permissionList = permissionService.getPermissionByRoleId(roleId);
			String listStr=JsonUtils.toJson(permissionList);
			JedisUtils.setString("role_permissions:"+roleId, listStr);
		} catch (Exception e) {
			logger.error("缓存角色权限失败,roleId:{}",roleId);
		}
	}
}
