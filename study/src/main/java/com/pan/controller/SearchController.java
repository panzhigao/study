package com.pan.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.pan.common.enums.ArticleStatusEnum;
import com.pan.common.vo.PageDataMsg;
import com.pan.common.vo.ResultMsg;
import com.pan.query.QueryArticle;
import com.pan.query.QueryUser;
import com.pan.service.IArticleService;
import com.pan.service.IUserService;

/**
 * @author 作者
 * @version 创建时间：2018年4月4日 下午6:21:30
 * 搜索类
 */
@Controller
public class SearchController {
	
	@Autowired
	private IArticleService articleService;
	
	@Autowired
	private IUserService userService;
		
	@RequestMapping(method=RequestMethod.GET,value="/search")
	public ModelAndView getSearchContent(String q,String type){
		ModelAndView mav=new ModelAndView("html/search/searchPage");
		if(!"u".equals(type)){
			type="a";
		}
		mav.addObject("q", q);
		mav.addObject("type", type);
		return mav;
	}
	
	/**
	 * 返回搜索结果
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/doSearch")
	@ResponseBody
	public ResultMsg doSearch(String q,String type,@RequestParam(defaultValue="10")Integer pageSize,@RequestParam(defaultValue="1")Integer pageNo){
		if("u".equals(type)){
			QueryUser queryUser=new QueryUser();
			queryUser.setNickname(q);
			queryUser.setPageSize(pageSize);
			queryUser.setPageNo(pageNo);
			PageDataMsg data = userService.queryUserFromEs(queryUser);
			return ResultMsg.ok("返回搜索用户结果成功", data);
		}else{
			QueryArticle queryArticle=new QueryArticle();
			queryArticle.setPageSize(pageSize);
			queryArticle.setPageNo(pageNo);
			queryArticle.setTitle(q);
			queryArticle.setStatus(ArticleStatusEnum.PUBLIC_SUCCESS.getCode());
			PageDataMsg data = articleService.queryArticleFromEs(queryArticle);
			return ResultMsg.ok("返回搜索文章结果成功", data);
		}
	}
	
}
