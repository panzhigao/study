package com.pan.service;

import java.util.List;
import java.util.Map;

import com.pan.entity.Article;


/**
 * 
 * @author Administrator
 *
 */
public interface ArticleService {
	/**
	 * 保存文章
	 * @param article
	 */
	public void saveArticle(Article article);
	/**
	 * 根据userId获取用户下的文章
	 * @param userId
	 * @return
	 */
	public List<Article> findListByUserId(String userId);
	/**
	 * 多条件查询，支持分页
	 * @param params 条件有userId,articleId
	 * @return
	 */
	public List<Article> findByParams(Map<String,Object> params);
	/**
	 * 获取用户的文章详细信息
	 * @param userId
	 * @param articleId
	 * @return
	 */
	public Article getByUserIdAndArticleId(String userId,String articleId);
	/**
	 * 修改文章信息
	 * @param article
	 */
	public void updateArticle(Article article);
}
