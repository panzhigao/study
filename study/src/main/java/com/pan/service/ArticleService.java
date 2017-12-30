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
	 * @param user
	 * @param status 文章状态
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
	 * @return
	 */
	public List<Article> findByParams(Map<String,Object> params);
}
