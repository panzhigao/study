package com.pan.controller;

import java.util.List;
import java.util.Map;
import com.pan.entity.ArticleCategory;
import com.pan.service.impl.ArticleCategoryServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.pan.common.enums.ArticleStatusEnum;
import com.pan.common.enums.ArticleTypeEnum;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.Article;
import com.pan.entity.User;
import com.pan.query.QueryArticle;
import com.pan.service.IArticleService;
import com.pan.service.IUserService;
import com.pan.util.JedisUtils;
import com.pan.util.TokenUtils;
import com.pan.util.TransFieldUtils;

/**
 * 用户创作
 * @author Administrator
 *
 */
@Controller
public class ArticleController {
	
	private static final Logger logger=LoggerFactory.getLogger(ArticleController.class);
	
	@Autowired
	private IArticleService articleService;
	
	@Autowired
	private IUserService userService;

	/**
	 * 跳转发文页面
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/article/addPage")
	@RequiresPermissions("article:doSave")
	public ModelAndView toAddPage(){
		ModelAndView mav=new ModelAndView("html/article/articleAdd");
		List<ArticleCategory> allThroughCache = ArticleCategoryServiceImpl.getAllThroughCache();
		mav.addObject("categoryList",allThroughCache);
		return mav;
	}
	
	/**
	 * 保存文章，文章为草稿状态或者待审核状态
	 * @return
	 */
	@RequestMapping(value={"/user/article/doSave"})
	@ResponseBody
	@RequiresPermissions("article:doSave")
	public ResultMsg saveArticle(Article article){
		logger.info("新建文章信息");
		ResultMsg resultMsg=null;
		articleService.saveArticle(article);
		if(ArticleStatusEnum.SKETCH.getCode().equals(article.getStatus())){				
			resultMsg=ResultMsg.ok("文章保存草稿成功");
		}else if(ArticleStatusEnum.IN_CHECK.getCode().equals(article.getStatus())){				
			resultMsg=ResultMsg.ok("文章发布成功,请等待审核");
		}
		return resultMsg;
	}
	
	/**
	 * 获取文章列表信息
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/article/mine")
	@RequiresPermissions("article:mine")
	public ModelAndView toArticleList(@RequestParam(defaultValue = "") String status){
		ModelAndView mav=new ModelAndView("html/article/articleManage");
		mav.addObject("status", status);
		return mav;
	}
	
	/**
	 * 加载文章列数据，分页查询
	 * @return
	 */
	@RequestMapping(value="/user/article/getPageData")
	@ResponseBody
	@RequiresPermissions("article:mine")
	public Map<String,Object> getUserArticleList(QueryArticle queryArticle){
		Long loginUserId = TokenUtils.getLoginUserId();
		queryArticle.setUserId(loginUserId);
		queryArticle.setType(ArticleTypeEnum.TYPE_ARTICLE.getCode());
		if(StringUtils.isNotBlank(queryArticle.getOrderCondition())){
			queryArticle.setOrderCondition(com.pan.util.StringUtils.camelToUnderline(queryArticle.getOrderCondition()));
		}else{			
			queryArticle.setOrderCondition("create_time desc");
		}
		Map<String,Object> pageData=articleService.findDTOPageableMap(queryArticle);
		return pageData;
	}
	
	/**
	 * 跳转文章列详情页或者编辑页面
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/article/{articleId:^\\d+}")
	public ModelAndView toArticleDetailPage(@PathVariable("articleId")Long articleId){
		ModelAndView mav=new ModelAndView("html/article/articleDetail");
		Long loginUserId=null;
		if(TokenUtils.isAuthenticated()){
			loginUserId = TokenUtils.getLoginUserId();
		}
		//查询文章信息
		QueryArticle queryArticle=new QueryArticle();
		queryArticle.setId(articleId);
		queryArticle.setUserId(loginUserId);
		Article article=articleService.checkAndGetArticle(queryArticle);
		TransFieldUtils.transEntity(article);
		mav.addObject("article", article);
		if(ArticleStatusEnum.PUBLIC_SUCCESS.getCode().equals(article.getStatus())){			
			long viewCount=JedisUtils.increaseKey("article_view_count:"+articleId);
			mav.addObject("viewCount",viewCount+article.getViewCount());
			String string = JedisUtils.getString("comment_count:"+articleId);
			long commentCount=article.getCommentCount();
			if(StringUtils.isNumeric(string)){
				commentCount=commentCount+Long.parseLong(string);
			}
			mav.addObject("commentCount",commentCount);
		}else{
			mav.addObject("viewCount",article.getViewCount());
		}
		User articleUser=userService.selectByPrimaryKey(article.getUserId());
		mav.addObject("articleUser", articleUser);
		return mav;
	}
	
	/**
	 * 跳转文章列详情页或者编辑页面
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/article/edit/{articleId:^\\d+}")
	@ResponseBody
	@RequiresPermissions("article:doEdit")
	public ModelAndView toArticlePage(@PathVariable("articleId")Long articleId){
		ModelAndView mav=new ModelAndView("html/article/articleEdit");
		Long loginUserId = TokenUtils.getLoginUserId();
		Article article=articleService.getAndCheckByUserId(loginUserId, articleId);
		mav.addObject("article", article);
		List<ArticleCategory> allThroughCache = ArticleCategoryServiceImpl.getAllThroughCache();
		mav.addObject("categoryList",allThroughCache);
		return mav;
	}
	

	/**
	 * 编辑文章，保存草稿文章为草稿状态，保存发布文章状态为审核中
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value={"/user/article/doEdit"})
	@ResponseBody
	@RequiresPermissions("article:doEdit")
	public ResultMsg updateArticle(Article article){
		logger.info("编辑文章信息",article);
		ResultMsg resultMsg=null;
		articleService.updateArticle(article);
		if(ArticleStatusEnum.SKETCH.getCode().equals(article.getStatus())){				
			resultMsg=ResultMsg.ok("文章保存草稿成功");
		}else if(ArticleStatusEnum.IN_CHECK.getCode().equals(article.getStatus())){				
			resultMsg=ResultMsg.ok("文章发布成功,请等待审核");
		}
		return resultMsg;
	}
	
	/**
	 * 删除文章
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value={"/user/article/doDelete"})
	@ResponseBody
	@RequiresPermissions("article:doDelete")
	public ResultMsg deleteArticle(Long articleId){
		logger.info("删除的文章id:{}",articleId);
		Long userId=TokenUtils.getLoginUserId();
		articleService.deleteArticle(articleId, userId);
		return ResultMsg.ok("删除文章成功");
	}
	
	/**
	 * 跳转文章主页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value={"/article/index"})
	public ModelAndView toArticleIndex(){
		ModelAndView mav=new ModelAndView("html/article/articleIndex");
		return mav;
	}
	
	/**
	 * 跳转文章主页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value={"/article/category/{categoryId:^\\d+}"})
	public ModelAndView toArticleIndex(@PathVariable("categoryId")Long categoryId){
		ModelAndView mav=new ModelAndView("html/article/articleIndex");
		mav.addObject("categoryId",categoryId);
		return mav;
	}
	
	/**
	 * 加载文章列数据，分页查询，该接口不用用户登陆，查询的是用户发表成功的文章
	 * @return
	 */
	@RequestMapping(value="/article/getPageData")
	@ResponseBody
	public Map<String,Object> getArticleList(QueryArticle queryArticle){
		queryArticle.setStatus(ArticleStatusEnum.PUBLIC_SUCCESS.getCode());
		queryArticle.setOrderCondition(null);
		if(queryArticle.getType()==null){
			queryArticle.setType(ArticleTypeEnum.TYPE_ARTICLE.getCode());
		}
		Map<String,Object> pageData=articleService.findDTOPageableMap(queryArticle);
		return pageData;
	}
	
	/**
	 * 获取文章条数
	 * @return
	 */
	@RequestMapping(value="/article/getCount")
	@ResponseBody
	public int getCount(Integer status,Integer type,Long categoryId){
		QueryArticle queryArticle=new QueryArticle();
		queryArticle.setStatus(status);
		queryArticle.setType(type);
		queryArticle.setCategoryId(categoryId);
		return articleService.getCount(queryArticle);
	}
	
	/**
	 * 修改文章状态
	 * 置顶或取消
	 * 加精或取消
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/article/set")
	@ResponseBody
	@RequiresPermissions("article:set")
	public ResultMsg set(Long articleId,Integer top,Integer highQuality){
		articleService.setArticle(articleId, top, highQuality);
		return ResultMsg.ok();
	}
	
	/**
	 * 下线文章
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value={"/user/article/offline"})
	@ResponseBody
	@RequiresPermissions("article:offline")
	public ResultMsg offlineArticle(Long articleId){
		logger.info("下线的文章id:{}",articleId);
		Long userId=TokenUtils.getLoginUserId();
		articleService.offlineArticle(articleId, userId);
		return ResultMsg.ok("下线文章成功");
	}
	
	/**
	 * 同步文章es数据
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value={"/user/article/syncEs"})
	@ResponseBody
	@RequiresPermissions("article:syncEs")
	public ResultMsg syncArticleEs(){
		int syncArticleEsData = articleService.syncArticleEsData();
		return ResultMsg.ok("同步文章es数据成功"+syncArticleEsData+"条数据");
	}
}
