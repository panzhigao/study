package com.pan.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pan.common.vo.ResultMsg;
import com.pan.entity.Role;
import com.pan.query.QueryRole;
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
	
	/**
	 * 跳转角色页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/role")
	@RequiresPermissions("/user/role")
	public String toRolePage(){
		return "html/role/rolePage";
	}
	
	/**
	 * 获取角色新增或者编辑页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/role/tab")
	@RequiresPermissions({"/user/role/doAdd","/user/role/doEdit"})
	public ModelAndView getRoleTab(Long roleId){
		ModelAndView mav=new ModelAndView("html/role/roleTab");
		if(roleId!=null){
			Role role = roleService.selectByPrimaryKey(roleId);
			mav.addObject("role",role);
		}
		return mav;
	}
	
	/**
	 * 新增角色
	 * @param role
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/role/doAdd")
	@ResponseBody
	@RequiresPermissions("/user/role/doAdd")
	public ResultMsg addRole(Role role){
		roleService.addRole(role);
		return ResultMsg.ok("新增权限成功");
	}
	
	/**
	 * 获取分页角色信息
	 * @param queryRole
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/role/getPageData")
	@ResponseBody
	@RequiresPermissions(value="/user/role")
	public Map<String,Object> loadRoles(QueryRole queryRole){
		Map<String,Object> pageData=roleService.findPageableMap(queryRole);
		return pageData;
	}
	
	/**
	 * 删除角色
	 * @param roleId
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/role/doDelete")
	@ResponseBody
	@RequiresPermissions("/user/role/doDelete")
	public ResultMsg deleteRole(Long roleId){
		roleService.deleteRole(roleId);
		return ResultMsg.ok("删除角色成功");
	}
	
	/**
	 * 为角色分配权限
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/role/allocatePermission")
	@ResponseBody
	@RequiresPermissions("/user/role/allocatePermission")
	public ResultMsg allocatePermission(Long roleId,@RequestParam(value = "permissions[]",required=false)Long[] permissions){
		roleService.allocatePermissionToRole(roleId, permissions);
		return ResultMsg.ok("分配角色权限成功");
	}
	
	/**
	 * 编辑权限
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/role/doEdit")
	@ResponseBody
	@RequiresPermissions("/user/role/doEdit")
	public ResultMsg editRole(Role role){
		roleService.updateRole(role);
		return ResultMsg.ok();
	}
}
