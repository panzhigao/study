package com.pan.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.pan.common.vo.PageDataMsg;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.Link;
import com.pan.query.QueryLink;
import com.pan.service.ILinkService;

/**
 * 链接管理
 * @author Administrator
 *
 */
@Controller
public class LinkController {
	
	private static final Logger logger=LoggerFactory.getLogger(LinkController.class);
	
	@Autowired
	private ILinkService linkService;
	
	/**
	 * 跳转链接管理主页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/link/index")
	@RequiresPermissions("link:load")
	public ModelAndView toLinkIndex(){
		ModelAndView mav=new ModelAndView("html/link/linkPage");
		return mav;
	}
	
	/**
	 * 获取分页数据
	 * @param queryLink
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/link/getPageData")
	@ResponseBody
	@RequiresPermissions("link:load")
	public PageDataMsg getLinkList(QueryLink queryLink){
		return linkService.findPageableMap(queryLink);
	}
	
	/**
	 * 跳转链接新增或编辑页面
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value={"/user/link/addPage"})
	@RequiresPermissions(value={"link:doAdd","link:doEdit"},logical=Logical.OR)
	public ModelAndView toLinkAddPage(Long id){
		ModelAndView mav=new ModelAndView("html/link/linkAdd");
		if(id!=null){
			Link selectByPrimaryKey = linkService.selectByPrimaryKey(id);
			if(selectByPrimaryKey!=null){
				mav.addObject("link",selectByPrimaryKey);
			}
		}
		return mav;
	}
	
	/**
	 * 新增链接
	 * @param permission
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value={"/user/link/doAdd"})
	@ResponseBody
	@RequiresPermissions("link:doAdd")
	public ResultMsg addLink(Link link){
		linkService.addLink(link);
		return ResultMsg.ok("新增链接成功");
	}
	
	/**
	 * 编辑链接
	 * @param permission
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/link/doEdit")
	@ResponseBody
	@RequiresPermissions("link:doEdit")
	public ResultMsg editLink(Link link){
		linkService.editLink(link);
		return ResultMsg.ok("编辑链接成功");
	}
	
	/**
	 * 编辑链接
	 * @param permission
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/link/doDelete")
	@ResponseBody
	@RequiresPermissions("link:doDelete")
	public ResultMsg deleteLink(Long id){
		linkService.deleteLink(id);
		return ResultMsg.ok("删除链接成功");
	}
	
	/**
	 * 修改链接状态
	 * 上线和下线
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/link/changeStatus")
	@ResponseBody
	@RequiresPermissions("link:changeStatus")
	public ResultMsg changeLinkStatus(Long id,Integer status){
		logger.info("更新链接状态,id={},status={}",id,status);
		String message = linkService.changeLinkStatus(id, status);
		return ResultMsg.ok(message);
	}
}
