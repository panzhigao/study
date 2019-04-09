package com.pan.service;

import com.pan.entity.ArticleCategory;

import java.util.List;

public interface ArticleCategoryService extends BaseService<ArticleCategory>{
	/**
	 * 新增文章分类
	 * @param articleCategory
	 */
	void addArticleCategory(ArticleCategory articleCategory);
	/**
	 * 根据文章分类删除
	 * @param articleCategoryId
	 * @return
	 */
	int deleteByArticleCategoryId(Long articleCategoryId);
	/**
	 * 更新文章分类
	 * @param articleCategory
	 */
	void updateArticleCategory(ArticleCategory articleCategory);
	/**
	 * 修改文章分类状态
	 * @param articleCategoryId 分类id
	 * @param status
	 * @return 返回状态内容
	 */
	String changeCategoryStatus(Long articleCategoryId,Integer status);
}
