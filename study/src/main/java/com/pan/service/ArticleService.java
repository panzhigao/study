package com.pan.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

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
	public Map<String,Object> findByParams(Map<String,Object> params);
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
	/**
	 * 获取用户的文章详细信息
	 * @param userId
	 * @param articleId
	 * @return
	 */
	public Article getByArticleId(String articleId);
	/**
	 * 删除文章
	 * @param articleId
	 * @param userId
	 */
	public void deleteArticle(String articleId,String userId);
	/**
	 * 根据查询条件查询文章条数
	 * @param params
	 * @return
	 */
	public int getCount(Map<String, Object> params);
	/**
	 * 根据文章id和状态查找唯一文章信息
	 * @param articleId
	 * @param status
	 * @return
	 */
	public Article findByArticleIdAndStatus(String articleId,String status);
	/**
	 * 更新文章评论
	 * @param articleId
	 * @param commentCount
	 */
	public int updateArticleCommentCount(@Param("articleId")String articleId,@Param("commentCount")Integer commentCount);
	/**
	 * 更新文章阅读数
	 * @return
	 */
	public int updateArticleViewCount(@Param("articleId")String articleId,@Param("viewCount")Integer viewCount);
	/**
	 * 保存系统消息
	 * @param article
	 */
	public void saveSystemMessage(Article article);
	/**
	 * 审核通过文章
	 */
	public void passArticle(String articleId);
	/**
	 * 审核未通过文章
	 */
	public void notPassArticle(String articleId);
}
