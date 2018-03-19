package com.pan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pan.common.enums.ResultCodeEmun;
import com.pan.common.vo.ResultMsg;
import com.pan.dto.RoleTree;
import com.pan.dto.TreeNode;
import com.pan.entity.Permission;
import com.pan.service.PermissionService;

/**
 * 权限管理
 * @author Administrator
 *
 */
@Controller
public class PermissionController {
	
	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping(method=RequestMethod.GET,value="/user/permission")
	public String toPermissionPage(){
		return "html/user/permission";
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/user/permission/add")
	@ResponseBody
	public ResultMsg addPermission(Permission permission){
		permissionService.addPermission(permission);
		return ResultMsg.ok("新增权限成功");
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/user/permission/get_permissions")
	@ResponseBody
	public ResultMsg loadPermissions(Integer pageSize,Integer pageNo,String permissionName){
		List<TreeNode> nodes=permissionService.getTreeData();
		return ResultMsg.build(ResultCodeEmun.SUCCESS, ResultCodeEmun.SUCCESS.getMsg(),nodes);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/user/permission/delete")
	@ResponseBody
	public ResultMsg deletePermissions(String permissionId){
		permissionService.deletePermission(permissionId);
		return ResultMsg.ok("删除权限成功");
	}
	
	@RequestMapping(method={RequestMethod.POST,RequestMethod.GET},value="/user/permission/get_role_tree")
	@ResponseBody
	public List<RoleTree> loadRoleTree(String roleId){
		return permissionService.getRoleTreeData(roleId);
	}
}
