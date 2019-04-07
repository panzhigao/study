package com.pan.controller;

import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pan.common.vo.ResultMsg;
import com.pan.query.QueryArticleCategory;
import com.pan.service.ArticleCategoryService;

@Controller
public class ArticleCategoryController {
	
	@Autowired
	private ArticleCategoryService articleCategoryService;
	
	/**
	 * 跳转文章分类管理页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/articleCategory")
	@RequiresPermissions("/user/articleCategory")
	public ModelAndView toAddPage(){
		ModelAndView mav=new ModelAndView("html/articleCategory/articleCategoryPage");
		return mav;
	}
	
	/**
	 * 加载文章分类列数据，分页查询
	 * @return
	 */
	@RequestMapping(value="/user/articleCategory/getPageData")
	@ResponseBody
	@RequiresPermissions("/user/articleCategory")
	public Map<String,Object> getArticleCategoryList(QueryArticleCategory queryArticleCategory){
		Map<String,Object> pageData=articleCategoryService.findPageableMap(queryArticleCategory);
		return pageData;
	}
	
	/**
	 * 删除文章分类
	 * @return
	 */
	@RequestMapping(value="/user/articleCategory/doDelete")
	@ResponseBody
	@RequiresPermissions("/user/articleCategory")
	public ResultMsg deleteArticleCategoryList(Long articleCategoryId){
		articleCategoryService.deleteByArticleCategoryId(articleCategoryId);
		return ResultMsg.ok("删除文章分类成功");
	}
	
}
