package com.pan.controller;

import java.util.List;
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
import com.pan.dto.Tree;
import com.pan.query.QueryUser;
import com.pan.service.RoleService;
import com.pan.service.UserService;

/**
 * 
 * @author Administrator
 * 用户管理
 */
@Controller
public class UserManageController {
		
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * 跳转用户管理
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/manage")
	@RequiresPermissions(value="/user/manage")
	public ModelAndView toUserEditPage(){
		ModelAndView mav=new ModelAndView("html/userManage/userManagePage");
		return mav;
	}
	
	/**
	 * 获取用户列表信息
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value={"/user/userList"})
	@ResponseBody
	@RequiresPermissions(value="/user/manage")
	public Map<String,Object> getUserList(QueryUser queryUser){
		Map<String,Object> pageData=userService.findPageData(queryUser);
		return pageData;
	}
	
	/**
	 * 获取角色树
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST,RequestMethod.GET},value="/user/role/getRoleTree")
	@ResponseBody
	@RequiresPermissions(value="/user/manage")
	public List<Tree> loadRoleTree(String userId){
		return roleService.getRoleTreeData(userId);
	}
	
	/**
	 * 为用户分配角色
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/role/allocateRole")
	@ResponseBody
	@RequiresPermissions("/user/role/allocateRole")
	public ResultMsg allocatePermission(String userId,@RequestParam(value = "roles[]",required=false)String[] roleIds){
		userService.allocateRoleToUser(userId, roleIds);
		return ResultMsg.ok("分配用户角色成功");
	}
	
	/**
	 * 修改用户状态
	 * 禁用和启用
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/manage/changeStatus")
	@ResponseBody
	@RequiresPermissions("/user/manage/changeStatus")
	public ResultMsg changeUserStatus(String userId,String status){
		String message = userService.changeUserStatus(userId, status);
		return ResultMsg.ok(message);
	}
}
