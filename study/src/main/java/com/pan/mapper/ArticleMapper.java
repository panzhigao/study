package com.pan.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

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
	 * @param article
	 */
	public void saveArticle(Article article);
	/**
	 * 查询文章详细,支持分页
	 * @param params
	 * @return
	 */
	public List<Article> findByParams(Map<String,Object> params);
	/**
	 * 查询文章详细,支持分页
	 * @param params
	 * @return
	 */
	public int getCountByParams(Map<String,Object> params);
	/**
	 * 更新文章
	 * @param article
	 */
	public int updateArticle(Article article);
	/**
	 * 根据条条件查询，不分页
	 * @param article
	 * @return
	 */
	public List<Article> findByCondition(Article article);
	/**
	 * 删除文章
	 * @param userId
	 * @param articleId
	 * @return
	 */
	public int deleteByUserIdAndArticleId(@Param("userId")String userId,@Param("articleId")String articleId);
}
