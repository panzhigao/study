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
import com.pan.common.constant.MyConstant;
import com.pan.common.exception.BusinessException;
import com.pan.dto.Tree;
import com.pan.dto.TreeNode;
import com.pan.entity.Permission;
import com.pan.entity.Role;
import com.pan.entity.RolePermission;
import com.pan.mapper.PermissionMapper;
import com.pan.service.PermissionService;
import com.pan.service.RolePermissionService;
import com.pan.service.RoleService;
import com.pan.util.BeanUtils;
import com.pan.util.CookieUtils;
import com.pan.util.IdUtils;
import com.pan.util.JedisUtils;
import com.pan.util.JsonUtils;
import com.pan.util.TokenUtils;
import com.pan.util.ValidationUtils;
import com.pan.vo.QueryRoleVO;


/**
 * 
 * @author Administrator
 * 
 */
@Service
public class PermissionServiceImpl implements PermissionService {

	private static final Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);
	
	@Autowired
	private PermissionMapper permissionMapper;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RolePermissionService rolePermissionService;
	
	@Override
	public void addPermission(Permission permission) {
		logger.info("新增权限：{}",permission);
		ValidationUtils.validateEntity(permission);
		if(StringUtils.isBlank(permission.getpId())){
			permission.setpId("0");
		}
		if(permission.getSort()==null){
			permission.setSort(1);
		}
		permission.setCreateTime(new Date());
		String loginUserId = TokenUtils.getLoingUserId();
		permission.setCreateUser(loginUserId);
		permission.setPermissionId(IdUtils.generatePermissionId());
		permissionMapper.addPermission(permission);
		QueryRoleVO queryRoleVO=new QueryRoleVO();
		queryRoleVO.setSuperAdminFlag("1");
		//自动为超级管理员添加权限
		TokenUtils.clearAuth();
//		List<Role> list = roleService.findByParams(queryRoleVO);
//		if(list.size()>0){
//			String roleId=list.get(0).getRoleId();
//			RolePermission rolePermission=new RolePermission(roleId,permission.getPermissionId());
//			rolePermissionService.addRolePermission(rolePermission);
//			JedisUtils.hset("role_permissions:"+roleId, permission.getPermissionId(), JsonUtils.toJson(permission));
//		}
	}

	@Override
	public Map<String, Object> findByParams(Map<String, Object> params) {
		Map<String,Object> pageData=new HashMap<String, Object>(2);
		List<Permission> list = new ArrayList<Permission>();
		try {
			logger.info("分页权限参数为:{}", JsonUtils.toJson(params));
			int total=permissionMapper.getCountByParams(params);
			//当查询记录大于0时，查询数据库记录，否则直接返回空集合
			if(total>0){				
				list = permissionMapper.findByParams(params);
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
	
	/**
	 * 删除权限
	 * 同时删除缓存中角色的权限信息
	 */
	@Override
	public void deletePermission(String permissionId) {
		//删除数据库信息
		permissionMapper.deletePermission(permissionId);
		//删除缓存数据
		TokenUtils.clearAuth();
//		List<Role> roles = roleService.findAll();
//		for (Role role : roles) {
//			String roleId=role.getRoleId();
//			JedisUtils.hdel("role_permissions:"+roleId, permissionId);
//		}
	}

	@Override
	public List<TreeNode> getTreeData() {
		List<Permission> list = this.permissionMapper.findAll();
		List<TreeNode> nodes=new ArrayList<TreeNode>();
		for (Permission permission : list) {
			TreeNode treeNode=new TreeNode();
			treeNode.setId(permission.getPermissionId());
			treeNode.setpId(permission.getpId());
			treeNode.setName(permission.getPermissionName());
			treeNode.setUrl(permission.getUrl());
			treeNode.setIcon(permission.getIcon());
			treeNode.setSort(permission.getSort());
			treeNode.setType(permission.getType());
			nodes.add(treeNode);
		}
		return nodes;
	}

	@Override
	public List<Tree> getPermissionTreeData(String roleId) {
		if(StringUtils.isBlank(roleId)){
			return new ArrayList<Tree>();
		}
		List<Permission> list = this.permissionMapper.getPermissionSelectedByRoleId(roleId);
		List<Tree> nodes=new ArrayList<Tree>(20);
		for (Permission permission : list) {
			Tree permissionTree=new Tree();
			permissionTree.setTitle(permission.getPermissionName());
			permissionTree.setValue(permission.getPermissionId());
			permissionTree.setId(permission.getPermissionId());
			permissionTree.setpId(permission.getpId());
			permissionTree.setIcon(permission.getIcon());
			permissionTree.setSort(permission.getSort());
			if(!StringUtils.equals(permission.getMarker(),"0")){
				permissionTree.setChecked(true);
			}
			nodes.add(permissionTree);
		}
		return Tree.buildTree(nodes,true);
	}

	@Override
	public List<Permission> getPermissionByRoleId(String roleId) {
		return permissionMapper.getPermissionByRoleId(roleId);
	}

	@Override
	public Permission getByPermissionId(String permissionId) {
		Map<String,Object> params=new HashMap<String, Object>(1);
		params.put("permissionId", permissionId);
		List<Permission> list = this.permissionMapper.findByParams(params);
		return list.size()==1?list.get(0):null;
	}

	@Override
	public void updatePermission(Permission permission) {
		if(StringUtils.isBlank(permission.getPermissionId())){
			throw new BusinessException("权限id为空");
		}
		Permission permissionInDb = getByPermissionId(permission.getPermissionId());
		if(permissionInDb==null){
			throw new BusinessException("权限已不存在");
		}
		if(MyConstant.PERMISSION_TYPE_MENU.equals(permission.getType())){
			permission.setUrl("  ");
		}
		String loginUserId = TokenUtils.getLoingUserId();
		permission.setUpdateUser(loginUserId);
		permission.setUpdateTime(new Date());
		permissionMapper.updatePermission(permission);
		TokenUtils.clearAllUserAuth();
//		try {
//			BeanUtils.copyPropertiesIgnoreNull(permission,permissionInDb);
//			//同步缓存
//			List<Role> roles = roleService.findAll();
//			for (Role role : roles) {
//				String roleId=role.getRoleId();
//				if(JedisUtils.hexists("role_permissions:"+roleId, permission.getPermissionId())){
//					JedisUtils.hset("role_permissions:"+roleId, permission.getPermissionId(), JsonUtils.toJson(permissionInDb));
//				}
//			}
//		}catch (Exception e) {
//			e.printStackTrace();
//			throw new BusinessException("修改权限失败");
//		}
	}

	@Override
	public List<Permission> getAll() {
		return permissionMapper.findAll();
	}

	@Override
	public List<Permission> findPermissionsByUserId(String userId) {
		return permissionMapper.findPermissionsByUserId(userId);
	}
}
