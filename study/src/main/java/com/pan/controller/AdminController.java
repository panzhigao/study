package com.pan.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pan.dto.Tree;
import com.pan.entity.Permission;
import com.pan.util.CookieUtils;
import com.pan.util.JedisUtils;
import com.pan.util.JsonUtils;

/**
 * 网站首页
 * @author Administrator
 *
 */
@Controller
public class AdminController {
	/**
	 * 跳转网站主页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/admin")
	public ModelAndView toLogin(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav=new ModelAndView("html/admin");
		return mav;
	}
	
	/**
	 * 加载菜单栏数据
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/loadMenu")
	@ResponseBody
	public List<Tree> loadMenu(){
		String userId = CookieUtils.getLoginUserId();
		String roles = JedisUtils.getString("user_roles:"+userId);
		String[] arr=(String[]) JsonUtils.fromJson(roles, String[].class);
		Set<Permission> permissions=new HashSet<Permission>();
		for (int i = 0; i < arr.length; i++) {
			Map<String, String> hgetAll = JedisUtils.hgetAll("role_permissions:"+arr[i]);
			if(MapUtils.isNotEmpty(hgetAll)){				
				permissions.addAll(JsonUtils.mapToList(hgetAll,Permission.class));
			}
			
		}
		List<Tree> nodes=new ArrayList<Tree>(20);
		for (Permission permission : permissions) {
			Tree roleTree=new Tree();
			roleTree.setTitle(permission.getPermissionName());
			roleTree.setValue(permission.getPermissionId());
			roleTree.setId(permission.getPermissionId());
			roleTree.setpId(permission.getpId());
			roleTree.setUrl(permission.getUrl());
			roleTree.setIcon(permission.getIcon());
			roleTree.setSort(permission.getSort());
			nodes.add(roleTree);
		}
		List<Tree> buildTree = Tree.buildTree(nodes,true);
		return buildTree;
	}
}
