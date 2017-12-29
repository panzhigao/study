package com.pan.service;

import com.pan.entity.Article;


/**
 * 
 * @author Administrator
 *
 */
public interface ArticleService {
	/**
	 * 保存文章
	 * @param user
	 * @param status 文章状态
	 */
	public void saveArticle(Article article);
}
