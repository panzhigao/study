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
	 * 根据userId查找文章信息集合
	 * @param userId
	 * @return
	 */
	List<Article> findListByUserId(Long userId);
	/**
	 * 更新文章，返回更新文章条数
	 * @param article
	 * @return
	 */
	int updateArticleByArticleId(Article article);
	/**
	 * 删除文章
	 * @param userId
	 * @param articleId
	 * @return
	 */
	int deleteByUserIdAndArticleId(@Param("userId")Long userId,@Param("articleId")Long articleId);
	/**
	 * 获取最大置顶值
	 * @return
	 */
	int getMaxStick();
}
