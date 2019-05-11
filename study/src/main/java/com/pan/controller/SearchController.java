package com.pan.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.pan.common.enums.ArticleStatusEnum;
import com.pan.common.vo.ResultMsg;
import com.pan.dto.ArticleDTO;
import com.pan.query.QueryArticle;
import com.pan.service.ArticleService;

/**
 * @author 作者
 * @version 创建时间：2018年4月4日 下午6:21:30
 * 搜索类
 */
@Controller
public class SearchController {
	
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping(method=RequestMethod.GET,value="/search")
	public ModelAndView getSearchContent(String q){
		ModelAndView mav=new ModelAndView("html/search/searchPage");
		mav.addObject("q", q);
		return mav;
	}
	
	/**
	 * 返回搜索结果
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/doSearch")
	@ResponseBody
	public ResultMsg doSearch(String q,String type){
		QueryArticle queryArticleVO=new QueryArticle();
		queryArticleVO.setTitle(q);
		queryArticleVO.setStatus(ArticleStatusEnum.PUBLIC_SUCCESS.getCode());
		List<ArticleDTO> searchContent = articleService.queryFromEsByCondition(queryArticleVO);
		
		
		
		return ResultMsg.ok("返回搜索查询结果成功", searchContent);
	}
	
}
