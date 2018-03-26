package com.pan.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.pan.common.vo.ResultMsg;
import com.pan.dto.Tree;
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
	public ModelAndView toUserEditPage(){
		ModelAndView mav=new ModelAndView("html/user/userManage");
		return mav;
	}
	
	/**
	 * 获取文章列表信息
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value={"/user/userList"})
	@ResponseBody
	public Map<String,Object> getUserList(Integer pageSize,Integer pageNo){
		Map<String,Object> params=new HashMap<String, Object>(5);
		Integer offset=(pageNo-1)*pageSize;
		params.put("offset", offset);
		params.put("row", pageSize);
		params.put("type", "1");
		Map<String,Object> pageData=userService.findByParams(params);
		return pageData;
	}
	
	/**
	 * 获取角色树
	 * @param roleId
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST,RequestMethod.GET},value="/user/role/get_role_tree")
	@ResponseBody
	public List<Tree> loadRoleTree(String userId){
		return roleService.getRoleTreeData(userId);
	}
	
	/**
	 * 为用户分配角色
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/role/allocate_role")
	@ResponseBody
	public ResultMsg allocatePermission(String userId,@RequestParam(value = "roles[]")String[] roles){
		userService.allocateRoleToUser(userId, roles);
		return ResultMsg.ok("分配用户角色成功");
	}
	
	/**
	 * 修改用户状态
	 * 禁用和启用
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/manage/changeStatus")
	@ResponseBody
	public ResultMsg changeUserStatus(String userId,String status){
		String message = userService.changeUserStatus(userId, status);
		return ResultMsg.ok(message);
	}
}
