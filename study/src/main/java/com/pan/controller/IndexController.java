package com.pan.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pan.common.vo.ResultMsg;
import com.pan.entity.UserExtension;
import com.pan.service.UserExtensionService;
import com.pan.vo.QueryUserExtensionVO;

/**
 * 网站首页
 * @author Administrator
 *
 */
@Controller
public class IndexController {
	
	@Autowired
	private UserExtensionService userExtensionService;
	
	/**
	 * 跳转网站主页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value={"/","/index"})
	public ModelAndView toLogin(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav=new ModelAndView("html/index");
		return mav;
	}
	
	/**
	 * 查询活跃用户信息，取前12条数据
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/api/getActiveUsers")
	@ResponseBody
	public ResultMsg loadActiveUsers(){
		QueryUserExtensionVO extensionVO=new QueryUserExtensionVO();
		extensionVO.setPageNo(1);
		extensionVO.setPageSize(12);
		extensionVO.setOrderByCondition("score");
		List<UserExtension> list = userExtensionService.findByParams(extensionVO);
		return ResultMsg.ok("获取活跃用户成功", list);
	}
}
