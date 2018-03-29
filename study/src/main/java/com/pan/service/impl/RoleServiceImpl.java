package com.pan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
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
import com.pan.mapper.RolePermissionMapper;
import com.pan.service.PermissionService;
import com.pan.service.RoleService;
import com.pan.util.CookieUtils;
import com.pan.util.IdUtils;
import com.pan.util.JedisUtils;
import com.pan.util.JsonUtils;
import com.pan.util.ValidationUtils;
import com.pan.vo.QueryRoleVO;

@Service
public class RoleServiceImpl implements RoleService{
	
	private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private RolePermissionMapper rolePermissionMapper;

	@Override
	public void addRole(Role role) {
		logger.info("新增角色：{}",role);
		ValidationUtils.validateEntity(role);
		String loginUserId = CookieUtils.getLoginUserId();
		role.setCreateTime(new Date());
		role.setCreateUser(loginUserId);
		role.setRoleId(IdUtils.generateRoleId());
		roleMapper.addRole(role);
	}

	@Override
	public Map<String, Object> findPageData(QueryRoleVO queryRoleVO) {
		Map<String,Object> pageData=new HashMap<String, Object>(2);
		List<Role> list = new ArrayList<Role>();
		try {
			logger.info("分页权限参数为:{}", JsonUtils.toJson(queryRoleVO));
			int total=roleMapper.getCountByParams(queryRoleVO);
			//当查询记录大于0时，查询数据库记录，否则直接返回空集合
			if(total>0){				
				list = roleMapper.findByParams(queryRoleVO);
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
	
	
	@Override
	public void deleteRole(String roleId) {
		//删除角色信息
		roleMapper.deleteRole(roleId);
		//删除角色下关联的权限
		rolePermissionMapper.deleteRolePermissionByRoleId(roleId);
		//清除缓存中角色的权限信息
		JedisUtils.delete("role_permissions:"+roleId);
	}

	@Override
	public void allocatePermissionToRole(String roleId, String[] permissions) {
		Role role = findByRoleId(roleId);
		if(role==null){
			throw new BusinessException("该角色不存在");
		}
		//TODO 增加日志
		//删除该角色下的所有权限，再重新添加
		rolePermissionMapper.deleteRolePermissionByRoleId(roleId);
		if(ArrayUtils.isNotEmpty(permissions)){
			List<RolePermission> list=new ArrayList<RolePermission>();
			for (String string : permissions) {
				RolePermission rolePermission=new RolePermission();
				rolePermission.setPermissionId(string);
				rolePermission.setRoleId(roleId);
				rolePermission.setCreateTime(new Date());
				list.add(rolePermission);
			}
			rolePermissionMapper.addRolePermission(list);
		}
		recachePermissionByRoleId(roleId);
	}

	@Override
	public Role findByRoleId(String roleId) {
		QueryRoleVO queryRoleVO=new QueryRoleVO();
		queryRoleVO.setRoleId(roleId);
		List<Role> list = roleMapper.findByParams(queryRoleVO);
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
		return Tree.buildTree(nodes,false);
	}

	@Override
	public List<Role> findAll() {
		return roleMapper.findByParams(new QueryRoleVO());
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
			//清除原有角色权限缓存
			JedisUtils.delete("role_permissions:"+roleId);
			if(CollectionUtils.isNotEmpty(permissionList)){				
				JedisUtils.hmset("role_permissions:"+roleId, JsonUtils.listToMap(permissionList, "permissionId"));
			}
		} catch (Exception e) {
			logger.error("缓存角色权限失败,roleId:{}",roleId);
		}
	}

	@Override
	public void updateRole(Role role) {
		ValidationUtils.validateEntityWithGroups(role);
		role.setUpdateTime(new Date());
		String loginUserId = CookieUtils.getLoginUserId();
		role.setUpdateUser(loginUserId);
		roleMapper.updateRole(role);
	}

	@Override
	public List<Role> findByParams(QueryRoleVO queryRoleVO) {
		return roleMapper.findByParams(queryRoleVO);
	}
}
