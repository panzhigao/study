package com.pan.service;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.pan.dto.ArticleDTO;
import com.pan.entity.Article;
import com.pan.query.QueryArticle;


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
	public Map<String,Object> findByParams(QueryArticle queryArticleVO);
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
	public int getCount(QueryArticle queryArticleVO);
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
	 * 
	 * @return
	 */
	public List<ArticleDTO> queryFromEsByCondition(QueryArticle queryArticleVO);
	/**
	 * 根据文章标题搜索文章
	 * @param title
	 * @return
	 */
	public List<ArticleDTO> searchArticleByTitle(String title);
	/**
	 * 根据文章标题搜索文章
	 * @param title
	 * @return
	 */
	public List<Article> findByCondition(QueryArticle queryArticleVO);
	/**
	 * 获取文章最大置顶值
	 * @return
	 */
	public int getMaxStick();
	/**
	 * 设置文章置顶或加精
	 * @param articleId
	 * @param stick
	 * @param highQuality
	 * @return
	 */
	public void setArticle(String articleId,String stick,String highQuality);
}
