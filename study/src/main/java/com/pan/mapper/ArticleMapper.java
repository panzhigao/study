package com.pan.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.pan.entity.Article;

/**
 * 
 * @author Administrator
 *
 */
public interface ArticleMapper extends BaseMapper<Article>{
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
	 * 更新文章，返回更新文章条数
	 * @param article
	 */
	int updateArticleByArticleId(Article article);
//	/**
//	 * 根据条条件查询，不分页
//	 * @param article
//	 * @return
//	 */
//	List<Article> findByCondition(Article article);
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
