package com.pan.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.Article;
import com.pan.service.ArticleService;
import com.pan.vo.QueryArticleVO;

/**
 * 审核文章
 * @author Administrator
 *
 */
@Controller
public class CheckController {
	
	@Autowired
	private ArticleService articleService;
	
	/**
	 * 跳转文章审核页面
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/check")
	public ModelAndView toIndex(){
		QueryArticleVO queryArticleVO=new QueryArticleVO();
		//审核中
		queryArticleVO.setStatus( Article.STATUS_IN_REVIEW);
		//类型为文章
		queryArticleVO.setType(Article.TYPE_ARTICLE);
		int articleCounts=articleService.getCount(queryArticleVO);
		ModelAndView mav=new ModelAndView("html/user/check");
		mav.addObject("articleCounts", articleCounts);
		return mav;
	}
	
	/**
	 * 加载文章列数据，文章状态为审核中
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/check/getPageData")
	@ResponseBody
	public Map<String,Object> getArticleList(Integer pageSize,Integer pageNo){
		QueryArticleVO queryArticleVO=new QueryArticleVO();
		queryArticleVO.setPageSize(pageSize);
		queryArticleVO.setPageNo(pageNo);
		queryArticleVO.setStatus(Article.STATUS_IN_REVIEW);
		queryArticleVO.setType(Article.TYPE_ARTICLE);
		Map<String,Object> pageData=articleService.findByParams(queryArticleVO);
		return pageData;
	}
	
	/**
	 * 文章审核通过
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/check/pass")
	@ResponseBody
	public ResultMsg articlePass(String articleId){
		articleService.passArticle(articleId);
		return ResultMsg.ok("文章通过审核");
	}
	
	/**
	 * 文章未审核通过
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/check/notPass")
	@ResponseBody
	public ResultMsg articleNotPass(String articleId,String reason){
		articleService.notPassArticle(articleId,reason);
		return ResultMsg.ok("文章未通过审核");
	}
}
