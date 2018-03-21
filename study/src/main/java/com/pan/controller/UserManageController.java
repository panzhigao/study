package com.pan.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
	public ModelAndView toUserEditPage(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("html/user/userManage");
		return mav;
	}
	
	/**
	 * 获取文章列表信息
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value={"/user/userList"})
	@ResponseBody
	public Map<String,Object> getUserList(HttpServletRequest request,Integer pageSize,Integer pageNo){
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
	public List<Tree> loadRoleTree(String roleId){
		return roleService.getRoleTreeData(roleId);
	}
}