package com.pan.mapper;

import java.util.List;
import java.util.Map;

import com.pan.entity.Article;

/**
 * 
 * @author Administrator
 *
 */
public interface ArticleMapper {
	/**
	 * 根据articleId查找文章，唯一一条文章数据
	 * @param articleId
	 * @return
	 */
	public Article findByArticleId(String articleId);
	/**
	 * 根据userId查找文章信息集合
	 * @param userId
	 * @return
	 */
	public List<Article> findListByUserId(String userId);
	/**
	 * 保存文章信息
	 * @param user
	 */
	public void saveArticle(Article article);
	/**
	 * 多条件查询，支持分页
	 * @return
	 */
	public List<Article> findByParams(Map<String,Object> params);
	/**
	 * 更新文章
	 * @param article
	 */
	public void updateArticle(Article article);
}
