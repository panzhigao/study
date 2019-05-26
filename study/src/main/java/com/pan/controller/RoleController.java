package com.pan.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.pan.common.vo.PageDataMsg;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.Role;
import com.pan.query.QueryRole;
import com.pan.service.IRoleService;

/**
 * 角色管理
 * @author Administrator
 *
 */
@Controller
public class RoleController {
	
	@Autowired
	private IRoleService roleService;
	
	/**
	 * 跳转角色页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/role")
	@RequiresPermissions("role:load")
	public String toRolePage(){
		return "html/role/rolePage";
	}
	
	/**
	 * 获取角色新增或者编辑页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/role/tab")
	@RequiresPermissions({"role:doAdd","role:doEdit"})
	public ModelAndView getRoleTab(@RequestParam(name="roleId",defaultValue="0") Long roleId){
		ModelAndView mav=new ModelAndView("html/role/roleTab");
		if(roleId>0){
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
	@RequiresPermissions("role:doAdd")
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
	@RequiresPermissions(value="role:load")
	public PageDataMsg loadRoles(QueryRole queryRole){
		return roleService.findPageableMap(queryRole);
	}
	
	/**
	 * 删除角色
	 * @param roleId
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/role/doDelete")
	@ResponseBody
	@RequiresPermissions("role:doDelete")
	public ResultMsg deleteRole(@RequestParam(defaultValue = "0")Long roleId){
		roleService.deleteRole(roleId);
		return ResultMsg.ok("删除角色成功");
	}
	
	/**
	 * 为角色分配权限
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/role/allocatePermission")
	@ResponseBody
	@RequiresPermissions("role:allocatePermission")
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
	@RequiresPermissions("role:doEdit")
	public ResultMsg editRole(Role role){
		roleService.updateRole(role);
		return ResultMsg.ok();
	}
}
