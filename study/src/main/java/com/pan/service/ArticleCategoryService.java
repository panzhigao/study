package com.pan.service;

import com.pan.entity.ArticleCategory;

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
}
