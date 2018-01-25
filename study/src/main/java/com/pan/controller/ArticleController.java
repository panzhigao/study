package com.pan.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pan.common.exception.BusinessException;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.Article;
import com.pan.entity.User;
import com.pan.service.ArticleService;
import com.pan.service.CommentService;
import com.pan.service.UserService;
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
	
	private static final String OPERATE_EDIT="edit";
	
	private static final String OPERATE_DETAIL="detail";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommentService commentService;
	/**
	 * 跳转发文页面
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/articleAdd")
	public ModelAndView writeArticle(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("html/jie/add");
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
		String userId=CookieUtils.getLoingUserId(request);
		article.setUserId(userId);
		articleService.saveArticle(article);
		if(Article.STATUS_SKETCH.equals(article.getStatus())){				
			resultMsg=ResultMsg.ok("文章保存草稿成功");
		}else if(Article.STATUS_IN_REVIEW.equals(article.getStatus())){				
			resultMsg=ResultMsg.ok("文章发布成功,请等待审核");
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
	 * 加载文章列数据，分页查询
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/get_articles")
	@ResponseBody
	public Map<String,Object> getUserArticleList(HttpServletRequest request,Integer pageSize,Integer pageNo,String status){
		String loingUserId = CookieUtils.getLoingUserId(request);
		Map<String,Object> params=new HashMap<String, Object>(5);
		params.put("userId", loingUserId);
		Integer offset=(pageNo-1)*pageSize;
		params.put("offset", offset);
		params.put("row", pageSize);
		params.put("status", status);
		Map<String,Object> pageData=articleService.findByParams(params);
		return pageData;
	}
	
	/**
	 * 跳转文章列详情页或者编辑页面
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/article/{opeate}/{articleId}")
	@ResponseBody
	public ModelAndView toArticlePage(HttpServletRequest request,@PathVariable("opeate")String opeate,@PathVariable("articleId")String articleId){
		//不存在的操作跳转登录页
		ModelAndView mav=new ModelAndView("login");
		Article article=articleService.getByArticleId(articleId);
		if(article==null){
			throw new BusinessException("文章已不存在");
		}
		mav.addObject("article", article);
		if(OPERATE_DETAIL.equals(opeate)){
			mav.setViewName("html/jie/detail");
			int commentCount=commentService.getCommnetCount(articleId);
			mav.addObject("commentCount",commentCount);
		}else if(OPERATE_EDIT.equals(opeate)){
			mav.setViewName("html/jie/edit");
		}
		User articleUser=userService.findByUserid(article.getUserId());
		User loginUser = CookieUtils.getLoginUser(request);
		mav.addObject("user", loginUser);
		mav.addObject("articleUser", articleUser);
		return mav;
	}
	

	/**
	 * 保存文章，文章为草稿状态
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value={"/user/edit_article"})
	@ResponseBody
	public ResultMsg updateArticle(Article article,HttpServletRequest request){
		logger.info("发布文章开始",article);
		ResultMsg resultMsg=null;
		String userId=CookieUtils.getLoingUserId(request);
		article.setUserId(userId);
		articleService.updateArticle(article);
		if(Article.STATUS_SKETCH.equals(article.getStatus())){				
			resultMsg=ResultMsg.ok("文章保存草稿成功");
		}else if(Article.STATUS_IN_REVIEW.equals(article.getStatus())){				
			resultMsg=ResultMsg.ok("文章发布成功,请等待审核");
		}
		return resultMsg;
	}
	
	/**
	 * 删除文章
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value={"/user/delete_article"})
	@ResponseBody
	public ResultMsg deleteArticle(String articleId,HttpServletRequest request){
		logger.info("删除的文章id:{}",articleId);
		String userId=CookieUtils.getLoingUserId(request);
		articleService.deleteArticle(articleId, userId);
		return ResultMsg.ok("删除文章成功");
	}
	
	/**
	 * 跳转文章主页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/article/index")
	public ModelAndView toArticleIndex(){
		ModelAndView mav=new ModelAndView("html/jie/index");
		return mav;
	}
	
	/**
	 * 加载文章列数据，分页查询
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/article/get_articles")
	@ResponseBody
	public Map<String,Object> getArticleList(HttpServletRequest request,Integer pageSize,Integer pageNo){
		Map<String,Object> params=new HashMap<String, Object>(5);
		Integer offset=(pageNo-1)*pageSize;
		params.put("offset", offset);
		params.put("row", pageSize);
		params.put("status", Article.STATUS_PUBLISHED);
		Map<String,Object> pageData=articleService.findByParams(params);
		return pageData;
	}
	
	/**
	 * 获取文章条数
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/article/get_count")
	@ResponseBody
	public int getCount(String status){
		Map<String,Object> params=new HashMap<String, Object>(5);
		params.put("status", status);
		return articleService.getCount(params);
	}
}
