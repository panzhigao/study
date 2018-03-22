package com.pan.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
	@SuppressWarnings("unchecked")
	@RequestMapping(method=RequestMethod.GET,value="/user/admin")
	public ModelAndView toLogin(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav=new ModelAndView("html/test");
		String userId = CookieUtils.getLoginUserId();
		//TODO 写成service方法
		Set<String> smembers = JedisUtils.smembers("user_roles:"+userId);
		String[] arr=new String[smembers.size()];
		arr=smembers.toArray(arr);
		for (int i = 0; i < arr.length; i++) {
			arr[i]="role_permissions:"+arr[i];
		}
		List<String> list = JedisUtils.mget(arr);
		Set<Permission> permissions=new HashSet<Permission>();
		for (String string : list) {
			List<Permission> p= (List<Permission>) JsonUtils.jsonToList(string, Permission.class);
			permissions.addAll(p);
		}
		mav.addObject("permissions", permissions);
		return mav;
	}
}
