package com.pan.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.pan.entity.Article;
import com.pan.query.QueryArticle;

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
	Article findByArticleId(String articleId);
	/**
	 * 根据userId查找文章信息集合
	 * @param userId
	 * @return
	 */
	List<Article> findListByUserId(String userId);
	/**
	 * 保存文章信息
	 * @param article
	 */
	void saveArticle(Article article);
	/**
	 * 查询文章详细,支持分页
	 * @param queryArticleVO
	 * @return
	 */
	List<Article> findByParams(QueryArticle queryArticleVO);
	/**
	 * 查询文章详细,支持分页
	 * @param queryArticleVO
	 * @return
	 */
	int getCountByParams(QueryArticle queryArticleVO);
	/**
	 * 更新文章，返回更新文章条数
	 * @param article
	 */
	int updateArticle(Article article);
	/**
	 * 根据条条件查询，不分页
	 * @param article
	 * @return
	 */
	List<Article> findByCondition(Article article);
	/**
	 * 删除文章
	 * @param userId
	 * @param articleId
	 * @return
	 */
	int deleteByUserIdAndArticleId(@Param("userId")String userId,@Param("articleId")String articleId);
	/**
	 * 获取最大置顶值
	 * @return
	 */
	int getMaxStick();
}
