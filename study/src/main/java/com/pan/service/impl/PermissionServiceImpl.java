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

import com.pan.dto.RoleTree;
import com.pan.dto.TreeNode;
import com.pan.entity.Permission;
import com.pan.mapper.PermissionMapper;
import com.pan.service.PermissionService;
import com.pan.util.IdUtils;
import com.pan.util.JsonUtils;
import com.pan.util.ValidationUtils;


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
	
	@Override
	public void addPermission(Permission permission) {
		logger.info("新增权限：{}",permission);
		ValidationUtils.validateEntity(permission);
		if(StringUtils.isBlank(permission.getPId())){
			permission.setPId("0");
		}
		permission.setCreateTime(new Date());
		permission.setPermissionId(IdUtils.generatePermissionId());
		permissionMapper.addPermission(permission);
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

	@Override
	public void deletePermission(String permissionId) {
		permissionMapper.deletePermission(permissionId);
	}

	@Override
	public List<TreeNode> getTreeData() {
		List<Permission> list = this.permissionMapper.findAll();
		List<TreeNode> nodes=new ArrayList<TreeNode>();
		for (Permission permission : list) {
			TreeNode treeNode=new TreeNode();
			treeNode.setId(permission.getPermissionId());
			treeNode.setpId(permission.getPId());
			treeNode.setName(permission.getPermissionName());
			treeNode.setUrl(permission.getUrl());
			//treeNode.setData(permission.getUrl());
			nodes.add(treeNode);
		}
		return nodes;
	}

	@Override
	public List<RoleTree> getRoleTreeData() {
		List<Permission> list = this.permissionMapper.findAll();
		List<RoleTree> nodes=new ArrayList<RoleTree>(20);
		for (Permission permission : list) {
			RoleTree roleTree=new RoleTree();
			roleTree.setTitle(permission.getPermissionName());
			roleTree.setValue(permission.getPermissionId());
			roleTree.setId(permission.getPermissionId());
			roleTree.setpId(permission.getPId());
			nodes.add(roleTree);
		}
		return RoleTree.buildTree(nodes);
	}
}
