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
public interface ArticleService extends BaseService<Article>{
	/**
	 * 保存文章
	 * @param article
	 */
	void saveArticle(Article article);
	/**
	 * 根据userId获取用户下的文章
	 * @param userId
	 * @return
	 */
	List<Article> findListByUserId(String userId);
	/**
	 * 校验并获取用户的文章详细信息，
	 * @param userId
	 * @param articleId
	 * @return
	 */
	Article getAndCheckByUserId(String userId,String articleId);
	/**
	 * 修改文章信息
	 * @param article
	 */
	void updateArticle(Article article);
	/**
	 * 获取用户的文章详细信息
	 * @param articleId
	 * @return
	 */
	Article getByArticleId(String articleId);
	/**
	 * 获取用户的文章详细信息,并校验文章是否存在
	 * @param articleId
	 * @return
	 */
	Article getAndCheckByArticleId(String articleId);
	/**
	 * 删除文章
	 * @param articleId
	 * @param userId
	 */
	void deleteArticle(String articleId,String userId);
	/**
	 * 根据查询条件查询文章条数
	 * @param queryArticle
	 * @return
	 */
	int getCount(QueryArticle queryArticle);
	/**
	 * 根据文章id和状态查找唯一文章信息
	 * @param articleId
	 * @param status
	 * @return
	 */
	Article findByArticleIdAndStatus(String articleId,Integer status);
	/**
	 * 更新文章评论
	 * @param articleId
	 * @param commentCount
	 * @return 更新条数
	 */
	int updateArticleCommentCount(@Param("articleId")String articleId,@Param("commentCount")Integer commentCount);
	/**
	 * 更新文章阅读数
	 * @return
	 */
	int updateArticleViewCount(@Param("articleId")String articleId,@Param("viewCount")Integer viewCount);
	/**
	 * 保存系统消息
	 * @param article
	 */
	void saveSystemMessage(Article article);
	/**
	 * @param queryArticleVO
	 * @return
	 */
	List<ArticleDTO> queryFromEsByCondition(QueryArticle queryArticleVO);
	/**
	 * 根据文章标题搜索文章
	 * @param title
	 * @return
	 */
	List<ArticleDTO> searchArticleByTitle(String title);
//	/**
//	 * 根据文章标题搜索文章
//	 * @param queryArticle
//	 * @return
//	 */
//	List<Article> findByCondition(QueryArticle queryArticle);
	/**
	 * 获取文章最大置顶值
	 * @return
	 */
	int getMaxStick();
	/**
	 * 设置文章置顶或加精
	 * @param articleId
	 * @param stick
	 * @param highQuality
	 * @return
	 */
	void setArticle(String articleId,Integer stick,Integer highQuality);
	/**
	 * 查询并校验文章信息，判断是否有当前文章的权限
	 * @param queryArticleVO
	 * @return
	 */
	Article checkAndGetArticle(QueryArticle queryArticleVO);
}
