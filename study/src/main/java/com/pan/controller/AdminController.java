package com.pan.controller;


import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.pan.common.constant.MyConstant;
import com.pan.dto.Tree;
import com.pan.entity.Article;
import com.pan.entity.User;
import com.pan.entity.UserExtension;
import com.pan.query.QueryArticle;
import com.pan.query.QueryCollection;
import com.pan.query.QueryPraise;
import com.pan.service.ArticleService;
import com.pan.service.CollectionService;
import com.pan.service.MessageService;
import com.pan.service.PraiseService;
import com.pan.service.UserExtensionService;
import com.pan.util.DateUtils;
import com.pan.util.TokenUtils;

/**
 * 网站首页
 * @author Administrator
 *
 */
@Controller
public class AdminController {
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private CollectionService collectionService;
	
	@Autowired
	private UserExtensionService userExtensionService;
	
	@Autowired
	private PraiseService praiseService;
	
	/**
	 * 跳转用户后台
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/admin")
	public ModelAndView toAdminPage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav=new ModelAndView("html/admin");
		return mav;
	}
	
	/**
	 * 跳转用户后台主页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/main")
	public ModelAndView toMainPage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav=new ModelAndView("html/main/mainPage");
		User loginUser = TokenUtils.getLoginUser();
		String loingUserId = loginUser.getUserId();
		//未读消息数
		int unReadMessageCount = messageService.countMessage(loingUserId, MyConstant.MESSAGE_NOT_READED);
		//文章总数
		QueryArticle queryArticle=new QueryArticle();
		queryArticle.setUserId(loingUserId);
		queryArticle.setType(Article.TYPE_ARTICLE);
		int articleTotalCount = articleService.getCount(queryArticle);
		//待审核文章数
		queryArticle.setStatus(Article.STATUS_IN_REVIEW);
		int articleWaitReviewCount = articleService.getCount(queryArticle);
		//收藏数
		QueryCollection queryCollection=new QueryCollection();
		queryCollection.setUserId(loingUserId);
		int collectionCount = collectionService.getCount(queryCollection);
		UserExtension findByUserId = userExtensionService.findByUserId(loingUserId);
		Integer score = findByUserId.getScore();
		QueryPraise queryPraise=new QueryPraise();
		queryPraise.setUserId(loingUserId);
		int praiseCount = praiseService.getCount(queryPraise);
		Date lastLoginTime = loginUser.getLastLoginTime();
		mav.addObject("unReadMessageCount", unReadMessageCount);
		mav.addObject("articleTotalCount", articleTotalCount);
		mav.addObject("articleWaitReviewCount", articleWaitReviewCount);
		mav.addObject("collectionCount", collectionCount);
		mav.addObject("score", score);
		mav.addObject("praiseCount", praiseCount);
		mav.addObject("ss", lastLoginTime);
		mav.addObject("lastLoginTime", DateUtils.getDateStr(lastLoginTime, DateUtils.FORMAT_DATE3));
		return mav;
	}
	
	/**
	 * 加载菜单栏数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method=RequestMethod.POST,value="/user/loadMenu")
	@ResponseBody
	public List<Tree> loadMenu(){
		List<Tree> buildTree=((List<Tree>) TokenUtils.getAttribute("menus"));
		return buildTree;
	}
}
