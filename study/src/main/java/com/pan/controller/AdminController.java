package com.pan.controller;


import java.util.Date;
import java.util.List;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.pan.common.constant.PageConstant;
import com.pan.common.constant.PermissionConstant;
import com.pan.common.enums.ArticleTypeEnum;
import com.pan.common.enums.CompleteFlagEnum;
import com.pan.common.enums.MessageStatusEnum;
import com.pan.dto.Tree;
import com.pan.entity.User;
import com.pan.entity.UserExtension;
import com.pan.query.QueryArticle;
import com.pan.query.QueryArticleCheck;
import com.pan.query.QueryCollection;
import com.pan.query.QueryLoginHistory;
import com.pan.query.QueryPicture;
import com.pan.query.QueryPraise;
import com.pan.service.IArticleCheckService;
import com.pan.service.IArticleService;
import com.pan.service.ICollectionService;
import com.pan.service.ILoginHistoryService;
import com.pan.service.IMessageService;
import com.pan.service.IPictureService;
import com.pan.service.IPraiseService;
import com.pan.service.IUserExtensionService;
import com.pan.util.TokenUtils;
import com.pan.vo.LoginHistoryVO;

/**
 * 用户后台管理
 * @author Administrator
 *
 */
@Controller
public class AdminController {
	
	@Autowired
	private IMessageService messageService;
	
	@Autowired
	private IArticleService articleService;
	
	@Autowired
	private ICollectionService collectionService;
	
	@Autowired
	private IUserExtensionService userExtensionService;
	
	@Autowired
	private IPraiseService praiseService;
	
	@Autowired
	private IPictureService pictureService;
	
	@Autowired
	private ILoginHistoryService loginHistoryService;
	
	@Autowired
	private IArticleCheckService articleCheckService;

	/**
	 * 跳转用户后台
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/admin")
	public ModelAndView toAdminPage(){
		ModelAndView mav=new ModelAndView("html/admin");
		return mav;
	}
	
	/**
	 * 跳转用户后台主页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/main")
	public ModelAndView toMainPage(){
		ModelAndView mav=new ModelAndView("html/main/mainPage");
		User loginUser = TokenUtils.getLoginUser();
		Long loginUserId = loginUser.getId();
		//未读消息数
		int unReadMessageCount = messageService.countMessage(loginUserId, MessageStatusEnum.MESSAGE_NOT_READED.getCode());
		//文章总数
		QueryArticle queryArticle=new QueryArticle();
		queryArticle.setUserId(loginUserId);
		queryArticle.setType(ArticleTypeEnum.TYPE_ARTICLE.getCode());
		int articleTotalCount = articleService.getCount(queryArticle);
		//如果有审核文章的权限，查询待审核文章数
		if(SecurityUtils.getSubject().isPermitted(PermissionConstant.ARTICLE_CHECK_PERMISSION)){
			QueryArticleCheck queryArticleCheck=new QueryArticleCheck();
			queryArticleCheck.setCompleteFlag(CompleteFlagEnum.NOT_COMPLETE.getCode());
			int articleWaitCheckCount = articleCheckService.countByParams(queryArticleCheck);
			mav.addObject("articleWaitCheckCount", articleWaitCheckCount);
		}
		//收藏数
		QueryCollection queryCollection=new QueryCollection();
		queryCollection.setUserId(loginUserId);
		int collectionCount = collectionService.countByParams(queryCollection);
		UserExtension findByUserId = userExtensionService.selectByPrimaryKey(loginUserId);
		Integer totalScore = findByUserId.getTotalScore();
		QueryPraise queryPraise=new QueryPraise();
		queryPraise.setUserId(loginUserId);
		//我的点赞数
		int praiseCount = praiseService.countByParams(queryPraise);
		QueryPicture queryPicture=new QueryPicture();
		queryPicture.setUserId(loginUserId);
		int pictureCount = pictureService.countByParams(queryPicture);
		//上次登陆时间
		Date lastLoginTime = loginUser.getLastLoginTime();
		//最近5次登陆记录
		QueryLoginHistory queryLoginHistory=new QueryLoginHistory();
		queryLoginHistory.setUserId(TokenUtils.getLoginUserId());
		queryLoginHistory.setPageNo(PageConstant.DEFAULT_PAGE_NO);
		queryLoginHistory.setPageSize(PageConstant.PAGE_SIZE_5);
		List<LoginHistoryVO> loginHistoryList = loginHistoryService.findVOPageable(queryLoginHistory);
		UserExtension extension = userExtensionService.selectByPrimaryKey(loginUserId);
		mav.addObject("unReadMessageCount", unReadMessageCount);
		mav.addObject("articleTotalCount", articleTotalCount);
		mav.addObject("collectionCount", collectionCount);
		mav.addObject("totalScore", totalScore);
		mav.addObject("praiseCount", praiseCount);
		mav.addObject("lastLoginTime", lastLoginTime);
		mav.addObject("pictureCount", pictureCount);
		mav.addObject("loginHistoryList", loginHistoryList);
		mav.addObject("extension", extension);
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
