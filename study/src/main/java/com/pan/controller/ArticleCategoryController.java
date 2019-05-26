package com.pan.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.pan.common.vo.PageDataMsg;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.ArticleCategory;
import com.pan.query.QueryArticleCategory;
import com.pan.service.IArticleCategoryService;

@Controller
@RequestMapping("/user/articleCategory")
public class ArticleCategoryController {
	
	@Autowired
	private IArticleCategoryService articleCategoryService;
	
	/**
	 * 跳转文章分类管理页
	 * @return
	 */
	@RequestMapping(value="")
	@RequiresPermissions("articleCategory:load")
	public ModelAndView toAddPage(){
		ModelAndView mav=new ModelAndView("html/articleCategory/articleCategoryPage");
		return mav;
	}
	
	/**
	 * 加载文章分类列数据，分页查询
	 * @return
	 */
	@RequestMapping(value="getPageData")
	@ResponseBody
	@RequiresPermissions("articleCategory:load")
	public PageDataMsg getArticleCategoryList(QueryArticleCategory queryArticleCategory){
		return articleCategoryService.findPageableMap(queryArticleCategory);
	}
	
	/**
	 * 跳转文章分类新增页面
	 * @return
	 */
	@RequestMapping(value="addPage")
	@RequiresPermissions("articleCategory:doAdd")
	public String toArticleCategoryAddPage(){
		return "html/articleCategory/articleCategoryAdd";
	}
	
	/**
	 * 新增文章分类
	 * @param articleCategory
	 * @return
	 */
	@RequestMapping(value="doAdd")
	@ResponseBody
	@RequiresPermissions("articleCategory:doAdd")
	public ResultMsg addArticleCategory(ArticleCategory articleCategory){
		articleCategoryService.addArticleCategory(articleCategory);
		return ResultMsg.ok("新增文章分类成功");
	}
	
	/**
	 * 跳转文章编辑页面
	 * @return
	 */
	@RequestMapping(value="editPage")
	@RequiresPermissions("articleCategory:doEdit")
	public ModelAndView toArticleCategoryEditPage(Long articleCategoryId){
		ModelAndView mav=new ModelAndView("html/articleCategory/articleCategoryEdit");
		ArticleCategory articleCategory = articleCategoryService.selectByPrimaryKey(articleCategoryId);
		mav.addObject("articleCategory", articleCategory);
		return mav;
	}
	
	/**
	 * 更新文章分类
	 * @param articleCategory
	 * @return
	 */
	@RequestMapping(value="doEdit")
	@ResponseBody
	@RequiresPermissions("articleCategory:doEdit")
	public ResultMsg updateArticleCategory(ArticleCategory articleCategory){
		articleCategoryService.updateArticleCategory(articleCategory);
		return ResultMsg.ok("更新文章分类成功");
	}
	
	/**
	 * 删除文章分类
	 * @return
	 */
	@RequestMapping(value="doDelete")
	@ResponseBody
	@RequiresPermissions("articleCategory:doDelete")
	public ResultMsg deleteArticleCategoryList(Long articleCategoryId){
		articleCategoryService.deleteByArticleCategoryId(articleCategoryId);
		return ResultMsg.ok("删除文章分类成功");
	}

	/**
	 * 修改分类状态
	 * 禁用和启用
	 * @return
	 */
	@RequestMapping(method= RequestMethod.POST,value="changeStatus")
	@ResponseBody
	@RequiresPermissions("articleCategory:changeStatus")
	public ResultMsg changeCategoryStatus(Long articleCategoryId, Integer status){
		String message = articleCategoryService.changeCategoryStatus(articleCategoryId,status);
		return ResultMsg.ok(message);
	}

}
