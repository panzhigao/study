package com.pan.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.pan.service.UserService;
import com.pan.util.JedisUtils;
import com.pan.util.TokenUtils;
import com.pan.util.TransFieldUtils;
import com.pan.vo.QueryArticleVO;
import com.pan.vo.QueryCollectionVO;

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
	
	@Autowired
	private UserService userService;
	
	/**
	 * 跳转发文页面
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/article/addPage")
	@RequiresPermissions("/user/article/doSave")
	public ModelAndView writeArticle(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("html/article/add");
		User user = TokenUtils.getLoginUser();
		mav.addObject("user", user);
		return mav;
	}
	
	/**
	 * 保存文章，文章为草稿状态
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value={"/user/article/doSave"})
	@ResponseBody
	@RequiresPermissions("/user/article/doSave")
	public ResultMsg saveArticle(Article article){
		logger.info("发布文章开始");
		ResultMsg resultMsg=null;
		articleService.saveArticle(article);
		if(Article.STATUS_SKETCH.equals(article.getStatus())){				
			resultMsg=ResultMsg.ok("文章保存草稿成功");
		}else if(Article.STATUS_IN_REVIEW.equals(article.getStatus())){				
			resultMsg=ResultMsg.ok("文章发布成功,请等待审核");
		}
		return resultMsg;
	}
	
	/**
	 * 获取文章列表信息
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value={"/user/article/mine"})
	@RequiresPermissions("/user/article/mine")
	public ModelAndView toArticleList(){
		String loingUserId = TokenUtils.getLoingUserId();
		QueryArticleVO queryArticleVO=new QueryArticleVO();
		queryArticleVO.setUserId(loingUserId);
		queryArticleVO.setType(Article.TYPE_ARTICLE);
		QueryCollectionVO collectionVO=new QueryCollectionVO();
		collectionVO.setUserId(loingUserId);
		collectionVO.setTitle(Article.TYPE_ARTICLE);
		ModelAndView mav=new ModelAndView("html/article/articleManage");
		return mav;
	}
	
	/**
	 * 加载文章列数据，分页查询
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/article/getPageData")
	@ResponseBody
	@RequiresPermissions("/user/article/mine")
	public Map<String,Object> getUserArticleList(Integer pageSize,Integer pageNo,String status){
		String loingUserId = TokenUtils.getLoingUserId();
		QueryArticleVO queryArticleVO=new QueryArticleVO();
		queryArticleVO.setUserId(loingUserId);
		queryArticleVO.setPageSize(pageSize);
		queryArticleVO.setPageNo(pageNo);
		queryArticleVO.setStatus(status);
		queryArticleVO.setType(Article.TYPE_ARTICLE);
		Map<String,Object> pageData=articleService.findByParams(queryArticleVO);
		return pageData;
	}
	
	/**
	 * 跳转文章列详情页或者编辑页面
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/article/{articleId:^a\\d+}")
	@ResponseBody
	public ModelAndView toArticleDetailPage(@PathVariable("articleId")String articleId){
		//不存在抛出异常
		ModelAndView mav=new ModelAndView("html/article/detail");
		String loginUserId=null;
		if(TokenUtils.isAuthenticated()){
			loginUserId = TokenUtils.getLoingUserId();
		}
		String status=Article.STATUS_PUBLISHED;
		Article article=articleService.findByArticleIdAndStatus(articleId,status);
		//登录状态
		if(loginUserId!=null&&article==null){
			article=articleService.getByUserIdAndArticleId(loginUserId, articleId);
		}
		if(article==null){
			throw new BusinessException("文章不存在");
		}
		TransFieldUtils.transEntity(article);
		mav.addObject("article", article);
		if(Article.STATUS_PUBLISHED.equals(article.getStatus())){			
			long viewCount=JedisUtils.increaseKey("article_view_count:"+articleId);
			mav.addObject("viewCount",viewCount+article.getViewCount());
		}else{
			mav.addObject("viewCount",article.getViewCount());
		}
		User articleUser=userService.findByUserId(article.getUserId());
		mav.addObject("articleUser", articleUser);
		return mav;
	}
	
	/**
	 * 跳转文章列详情页或者编辑页面
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/article/edit/{articleId}")
	@ResponseBody
	@RequiresPermissions("/user/article/doEdit")
	public ModelAndView toArticlePage(@PathVariable("articleId")String articleId){
		ModelAndView mav=new ModelAndView("html/article/edit");
		String loingUserId = TokenUtils.getLoingUserId();
		Article article=articleService.getByUserIdAndArticleId(loingUserId, articleId);
		if(article==null){
			throw new BusinessException("文章不存在");
		}
		mav.addObject("article", article);
		return mav;
	}
	

	/**
	 * 保存文章，文章为草稿状态
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value={"/user/article/doEdit"})
	@ResponseBody
	@RequiresPermissions("/user/article/doEdit")
	public ResultMsg updateArticle(Article article,HttpServletRequest request){
		logger.info("发布文章开始",article);
		ResultMsg resultMsg=null;
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
	@RequestMapping(method=RequestMethod.POST,value={"/user/article/doDelete"})
	@ResponseBody
	@RequiresPermissions("/user/article/doDelete")
	public ResultMsg deleteArticle(String articleId,HttpServletRequest request){
		logger.info("删除的文章id:{}",articleId);
		String userId=TokenUtils.getLoingUserId();
		articleService.deleteArticle(articleId, userId);
		return ResultMsg.ok("删除文章成功");
	}
	
	/**
	 * 跳转文章主页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/article/index")
	public ModelAndView toArticleIndex(){
		ModelAndView mav=new ModelAndView("html/article/index");
		return mav;
	}
	
	/**
	 * 加载文章列数据，分页查询，该接口不用用户登陆，查询的是用户发表的文章
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/article/getPageData")
	@ResponseBody
	public Map<String,Object> getArticleList(Integer pageSize,Integer pageNo,String userId,String isHot,String type){
		QueryArticleVO articleVO=new QueryArticleVO();
		articleVO.setUserId(userId);
		articleVO.setPageSize(pageSize);
		articleVO.setPageNo(pageNo);
		articleVO.setStatus(Article.STATUS_PUBLISHED);
		articleVO.setIsHot(isHot);
		articleVO.setType(type);
		articleVO.setOrderCondition("publish_time desc");
		if(StringUtils.isBlank(type)){
			articleVO.setType(Article.TYPE_ARTICLE);
		}
		Map<String,Object> pageData=articleService.findByParams(articleVO);
		return pageData;
	}
	
	/**
	 * 获取文章条数
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/article/getCount")
	@ResponseBody
	public int getCount(String status,String type){
		QueryArticleVO articleVO=new QueryArticleVO();
		articleVO.setStatus(status);
		articleVO.setType(type);
		return articleService.getCount(articleVO);
	}
	
	/**
	 * 修改文章状态
	 * 置顶或取消
	 * 加精或取消
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/article/set")
	@ResponseBody
	@RequiresPermissions("/user/article/set")
	public ResultMsg set(String articleId,String stick,String highQuality){
		articleService.setArticle(articleId, stick, highQuality);
		return ResultMsg.ok();
	}
}
