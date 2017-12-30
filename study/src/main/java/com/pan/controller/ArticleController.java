package com.pan.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pan.common.exception.BusinessException;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.Article;
import com.pan.entity.User;
import com.pan.service.ArticleService;
import com.pan.util.CookieUtils;

/**
 * 用户创作
 * @author Administrator
 *
 */
@Controller
public class ArticleController {
	
	private static final Logger logger=LoggerFactory.getLogger(ArticleController.class);
	
	@Autowired
	private ArticleService articleService;
	
	/**
	 * 跳转发文页面
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/article")
	public ModelAndView writeArticle(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("content/article");
		User user = CookieUtils.getLoginUser(request);
		mav.addObject("user", user);
		return mav;
	}
	
	/**
	 * 保存文章，文章为草稿状态
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value={"/user/save_article"})
	@ResponseBody
	public ResultMsg saveArticle(Article article,HttpServletRequest request){
		logger.info("发布文章开始");
		ResultMsg resultMsg=null;
		try {
			if(Article.STATUS_SKETCH.equals(article.getStatus())){
				resultMsg=ResultMsg.ok("文章保存草稿成功");
			}else if(Article.STATUS_IN_REVIEW.equals(article.getStatus())){
				resultMsg=ResultMsg.ok("文章发布成功,请等待审核");
			}else{
				resultMsg=ResultMsg.fail("文章状态有误，请重新刷新页面");
				return resultMsg;
			}
			String userId=CookieUtils.getLoingUserId(request);
			article.setUserId(userId);
			articleService.saveArticle(article);
		}catch(BusinessException e){
			resultMsg=ResultMsg.fail(e.getMessage());
		}catch (Exception e) {
			resultMsg=ResultMsg.fail("文章保存草稿失败");
		}
		return resultMsg;
	}
	
	/**
	 * 跳转文章列表页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value={"/user/my_articles"})
	public ModelAndView toArticleList(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("content/articleList");
		User user = CookieUtils.getLoginUser(request);
		mav.addObject("user", user);
		return mav;
	}
	
	/**
	 * 跳转文章列数据
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value={"/user/get_articles"})
	@ResponseBody
	public List<Article> getArticleList(HttpServletRequest request,Integer pageSize,Integer pageNo){
		String loingUserId = CookieUtils.getLoingUserId(request);
		Map<String,Object> params=new HashMap<String, Object>(5);
		params.put("userId", loingUserId);
		Integer offset=(pageNo-1)*pageSize;
		params.put("offset", offset);
		params.put("row", pageSize);
		List<Article> list=articleService.findByParams(params);
		return list;
	}
	
}
