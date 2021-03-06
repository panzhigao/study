package com.pan.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.ArticleCheck;
import com.pan.query.QueryArticleCheck;
import com.pan.service.IArticleCheckService;
import com.pan.util.TransFieldUtils;

/**
 * @author panzhigao
 */
@Controller
public class ArticleCheckController {
	
	@Autowired
	private IArticleCheckService articleCheckService;
	
	/**
	 * 跳转文章审核页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/articleCheck")
	@RequiresPermissions("articleCheck:load")
	public ModelAndView toIndex(){
		ModelAndView mav=new ModelAndView("html/articleCheck/articleCheck");
		return mav;
	}
	
	/**
	 * 加载审核数据
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/articleCheck/getPageData")
	@ResponseBody
	@RequiresPermissions(value="articleCheck:load")
	public Map<String,Object> getArticleCheckList(QueryArticleCheck queryArticleCheck){
		if(StringUtils.isNotBlank(queryArticleCheck.getOrderCondition())){
			queryArticleCheck.setOrderCondition(com.pan.util.StringUtils.camelToUnderline(queryArticleCheck.getOrderCondition()));
		}else{			
			queryArticleCheck.setOrderCondition("create_time desc");
		}
		Map<String,Object> pageData=articleCheckService.findVOPageableMap(queryArticleCheck);
		return pageData;
	}
	
	/**
	 * 文章审核通过
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/articleCheck/pass")
	@ResponseBody
	@RequiresPermissions(value="articleCheck:load")
	public ResultMsg articlePass(Long id){
		articleCheckService.passArticleCheck(id);
		return ResultMsg.ok("文章通过审核");
	}
	
	/**
	 * 文章未审核通过
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/articleCheck/notPass")
	@ResponseBody
	@RequiresPermissions(value="articleCheck:load")
	public ResultMsg articleNotPass(Long id,String reason){
		articleCheckService.notPassArticle(id, reason);
		return ResultMsg.ok("文章未通过审核");
	}
	
	/**
	 * 文章审核详情
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/articleCheck/detail")
	@RequiresPermissions(value="articleCheck:load")
	public ModelAndView articleCheckDetail(Long id){
		ModelAndView mav=new ModelAndView();
		mav.setViewName("html/articleCheck/articleCheckDetail");
		ArticleCheck articleCheck = articleCheckService.selectByPrimaryKey(id);
		if(articleCheck!=null){
			TransFieldUtils.transEntity(articleCheck);
		}
		mav.addObject("articleCheck", articleCheck);
		return mav;
	}
}
