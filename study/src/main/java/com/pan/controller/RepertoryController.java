package com.pan.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.pan.common.vo.PageDataMsg;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.Repertory;
import com.pan.query.QueryRepertory;
import com.pan.service.IRepertoryService;

/**
 * 库存
 * @author Administrator
 *
 */
@Controller
public class RepertoryController {
	
	@Autowired
	private IRepertoryService repertoryService;
	
	/**
	 * 跳转库存管理主页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/repertory/index")
	@RequiresPermissions("repertory:load")
	public ModelAndView toLinkIndex(){
		ModelAndView mav=new ModelAndView("html/repertory/repertoryPage");
		return mav;
	}
	
	/**
	 * 跳转库存新增
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value={"/user/repertory/addPage"})
	@RequiresPermissions(value={"repertory:doAdd"})
	public ModelAndView toLinkAddPage(Long id){
		ModelAndView mav=new ModelAndView("html/repertory/repertoryAdd");
		return mav;
	}
	
	/**
	 * 新增库存
	 * @param permission
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value={"/user/repertory/doAdd"})
	@ResponseBody
	@RequiresPermissions("repertory:doAdd")
	public ResultMsg addRepertory(Repertory repertory){
		repertoryService.addRepertory(repertory);
		return ResultMsg.ok("新增库存成功");
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/user/repertory/doDelete")
	@ResponseBody
	@RequiresPermissions("repertory:doDelete")
	public ResultMsg deletePermission(Long id){
		repertoryService.deleteByPrimaryKey(id);
		return ResultMsg.ok("删除库存成功");
	}
	
	/**
	 * 获取分页数据
	 * @param queryLink
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/repertory/getPageData")
	@ResponseBody
	@RequiresPermissions("repertory:load")
	public PageDataMsg getRepertoryList(QueryRepertory queryRepertory){
		return repertoryService.findPageableMap(queryRepertory);
	}
	
}
