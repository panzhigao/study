package com.pan.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pan.common.annotation.HasPermission;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.Role;
import com.pan.service.RoleService;
import com.pan.vo.QueryRoleVO;

/**
 * 角色管理
 * @author Administrator
 *
 */
@Controller
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * 跳转角色页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/role")
	@HasPermission
	public String toRolePage(){
		return "html/user/role";
	}
	
	/**
	 * 新增角色
	 * @param role
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/role/doAdd")
	@ResponseBody
	@HasPermission
	public ResultMsg addRole(Role role){
		roleService.addRole(role);
		return ResultMsg.ok("新增权限成功");
	}
	
	/**
	 * 获取分页角色信息
	 * @param queryRoleVO
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/role/getPageData")
	@ResponseBody
	@HasPermission(value="/user/role")
	public Map<String,Object> loadRoles(QueryRoleVO queryRoleVO){
		Map<String,Object> pageData=roleService.findPageData(queryRoleVO);
		return pageData;
	}
	
	/**
	 * 删除角色
	 * @param roleId
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/role/doDelete")
	@ResponseBody
	@HasPermission
	public ResultMsg deleteRole(String roleId){
		roleService.deleteRole(roleId);
		return ResultMsg.ok("删除角色成功");
	}
	
	/**
	 * 为角色分配权限
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/role/allocatePermission")
	@ResponseBody
	@HasPermission
	public ResultMsg allocatePermission(String roleId,@RequestParam(value = "permissions[]",required=false)String[] permissions){
		roleService.allocatePermissionToRole(roleId, permissions);
		return ResultMsg.ok("分配角色权限成功");
	}
	
	/**
	 * 编辑权限
	 * @param roleId
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/role/doEdit")
	@ResponseBody
	@HasPermission
	public ResultMsg editRole(Role role){
		roleService.updateRole(role);
		return ResultMsg.ok();
	}
}
