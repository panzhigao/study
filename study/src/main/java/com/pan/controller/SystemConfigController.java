package com.pan.controller;

import java.util.Map;
import com.pan.query.QuerySystemConfig;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.SystemConfig;
import com.pan.service.SystemConfigService;

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
	@RequiresPermissions("/user/systemConfig")
	public ModelAndView toSystemConfigPage(){
		ModelAndView mav=new ModelAndView("html/system/systemConfig");
		return mav;
	}

	/**
	 * 获取分页系统配置数据
	 * @param querySystemConfig
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/systemConfig/getPageData")
	@ResponseBody
	@RequiresPermissions(value="/user/systemConfig")
	public Map<String,Object> getConfigList(QuerySystemConfig querySystemConfig){
		Map<String,Object> pageData=systemConfigService.findPageableMap(querySystemConfig);
		return pageData;
	}

	@RequestMapping(method=RequestMethod.POST,value="/user/systemConfig/doAdd")
	@ResponseBody
	@RequiresPermissions("/user/systemConfig/doAdd")
	public ResultMsg addSystemConfig(SystemConfig systemConfig){
		systemConfigService.addSystemConfig(systemConfig);
		return ResultMsg.ok("新增系统配置成功");
	}

	/**
	 * 跳转系统配置tab页，新增或编辑
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/systemConfig/tab")
	@RequiresPermissions({"/user/systemConfig/doAdd","/user/systemConfig/doEdit"})
	public ModelAndView getConfigTab(@RequestParam(name="configId",defaultValue="0") Long configId){
		ModelAndView mav=new ModelAndView("html/system/systemConfigTab");
		if(configId>0){
			SystemConfig systemConfig = systemConfigService.selectByPrimaryKey(configId);
			mav.addObject("config",systemConfig);
		}
		return mav;
	}
	
	/**
	 * 编辑系统配置
	 * @param systemConfig
	 * @param paramName
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/systemConfig/doEdit")
	@ResponseBody
	@RequiresPermissions("/user/systemConfig/doEdit")
	public ResultMsg editSystemConfig(SystemConfig systemConfig,String paramName){
		systemConfigService.updateSystemConfig(systemConfig);
		return ResultMsg.ok("编辑系统配置成功");	
	}
	
	/**
	 * 删除系统配置变量
	 * @param configId
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/systemConfig/doDelete")
	@ResponseBody
	@RequiresPermissions("/user/systemConfig/doDelete")
	public ResultMsg deleteSystemConfig(@RequestParam(defaultValue = "0") Long configId){
		systemConfigService.deleteSystemConfig(configId);
		return ResultMsg.ok("删除系统配置成功");
	}
}
