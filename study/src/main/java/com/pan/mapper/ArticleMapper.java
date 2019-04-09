package com.pan.mapper;

import java.util.List;

import com.pan.dto.ArticleDTO;
import com.pan.query.QueryArticle;
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
	/**
	 * 分页查询文章信息
	 * @param queryArticle
	 * @return
	 */
	List<ArticleDTO> findDTOPageable(QueryArticle queryArticle);
}
