package com.pan.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pan.common.vo.ResultMsg;
import com.pan.entity.Role;
import com.pan.service.RoleService;

/**
 * 角色管理
 * @author Administrator
 *
 */
@Controller
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(method=RequestMethod.GET,value="/user/role")
	public String toPermissionPage(){
		return "html/user/role";
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/user/role/add")
	@ResponseBody
	public ResultMsg addRole(Role role){
		roleService.addRole(role);
		return ResultMsg.ok("新增权限成功");
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/user/role/get_roles")
	@ResponseBody
	public Map<String,Object> loadRoles(Integer pageSize,Integer pageNo,String roleName){
		Map<String,Object> params=new HashMap<String, Object>(5);
		Integer offset=(pageNo-1)*pageSize;
		params.put("offset", offset);
		params.put("row", pageSize);
		params.put("roleName", roleName);
		Map<String,Object> pageData=roleService.findByParams(params);
		return pageData;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/user/role/delete")
	@ResponseBody
	public ResultMsg deleteRole(String roleId){
		roleService.deleteRole(roleId);
		return ResultMsg.ok("删除角色成功");
	}
	
	/**
	 * 为角色分配权限
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/role/allocate_permission")
	@ResponseBody
	public ResultMsg allocatePermission(String roleId,@RequestParam(value = "permissions[]")String[] permissions){
		roleService.allocatePermissionToRole(roleId, permissions);
		return ResultMsg.ok("分配角色权限成功");
	}
}
