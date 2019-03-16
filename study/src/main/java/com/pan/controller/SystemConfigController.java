package com.pan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pan.common.annotation.HasPermission;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.SystemConfig;
import com.pan.service.SystemConfigService;
import com.pan.vo.SystemConfigParam;

/**
 * 系统配置
 * @author Administrator
 *
 */
@Controller
public class SystemConfigController {
	
	@Autowired
	private SystemConfigService systemConfigService;
	
	/**
	 * 跳转用户系统配置管理页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/systemConfig")
	@HasPermission("/user/systemConfig")
	public ModelAndView toSystemConfigPage(){
		ModelAndView mav=new ModelAndView("html/system/systemConfig");
		List<SystemConfigParam> params = systemConfigService.findSystemConfigParamList();
		mav.addObject("params", params);
		return mav;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="user/systemConfig/edit")
	@ResponseBody
	@HasPermission("/user/systemConfig")
	public ResultMsg editSystemConfig(SystemConfig systemConfig){
		systemConfigService.updateSystemConfig(systemConfig);
		return ResultMsg.ok("编辑系统配置成功");	
	}
}
