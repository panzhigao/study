package com.pan.controller;

import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pan.common.enums.OperateLogTypeEnum;
import com.pan.query.QueryOperateLog;
import com.pan.service.OperateLogService;

/**
 * 操作日志
 * @author Administrator
 *
 */
@Controller
public class OperateLogController {
	
	@Autowired
	private OperateLogService operateLogService;
	
	/**
	 * 跳转操作日志列表
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/operateLog")
	@RequiresPermissions("operateLog:load")
	public ModelAndView toLogList(){
		ModelAndView mav=new ModelAndView("html/system/operateLog");
		mav.addObject("types", OperateLogTypeEnum.getEnums());
		return mav;
	}
	
	/**
	 * 加载操作日志数据，分页查询
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/operateLog/getPageData")
	@ResponseBody
	@RequiresPermissions("operateLog:load")
	public Map<String,Object> getUserArticleList(QueryOperateLog queryOperateLog){
		return operateLogService.findByParams(queryOperateLog);
	}
	
}
