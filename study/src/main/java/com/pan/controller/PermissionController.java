package com.pan.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.pan.common.enums.ResultCodeEnum;
import com.pan.common.vo.ResultMsg;
import com.pan.dto.Tree;
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
	
	/**
	 * 跳转权限管理页面
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/permission")
	@RequiresPermissions("/user/permission")
	public String toPermissionPage(){
		return "html/permission/permissionPage";
	}
	
	/**
	 * 跳转权限新增页面
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/permission/addPage")
	@RequiresPermissions("/user/permission/doAdd")
	public String toPermissionAddPage(){
		return "html/permission/permissionAdd";
	}
	
	/**
	 * 跳转权限编辑页面
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/permissionEdit")
	@RequiresPermissions("/user/permission/doEdit")
	public String toPermissionEditPage(){
		return "html/permission/permissionEdit";
	}
	
	/**
	 * 新增权限
	 * @param permission
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/permission/doAdd")
	@ResponseBody
	@RequiresPermissions("/user/permission/doAdd")
	public ResultMsg addPermission(Permission permission){
		permissionService.addPermission(permission);
		return ResultMsg.ok("新增权限成功");
	}
	
	/**
	 * 获取权限数据
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/permission/getData")
	@ResponseBody
	@RequiresPermissions(value="/user/permission")
	public ResultMsg loadPermissions(){
		List<TreeNode> nodes=permissionService.getTreeData();
		return ResultMsg.build(ResultCodeEnum.SUCCESS, ResultCodeEnum.SUCCESS.getMsg(),nodes);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/user/permission/doDelete")
	@ResponseBody
	@RequiresPermissions("/user/permission/doDelete")
	public ResultMsg deletePermission(Long id){
		permissionService.deletePermission(id);
		return ResultMsg.ok("删除权限成功");
	}
	
	/**
	 * 获取权限树
	 * @param roleId
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST,RequestMethod.GET},value="/user/permission/getPermissionTree")
	@ResponseBody
	@RequiresPermissions(value="/user/permission")
	public List<Tree> loadRoleTree(String roleId){
		return permissionService.getPermissionTreeData(roleId);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/user/permission/detail")
	@ResponseBody
	@RequiresPermissions(value="/user/permission/doEdit")
	public ResultMsg loadPermissionDetail(String permissionId){
		Permission permission = permissionService.getByPermissionId(permissionId);
		return ResultMsg.ok("获取权限信息成功", permission);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/user/permission/doEdit")
	@ResponseBody
	@RequiresPermissions("/user/permission/doEdit")
	public ResultMsg editPermission(Permission permission){
		permissionService.updatePermission(permission);
		return ResultMsg.ok();
	}
}
