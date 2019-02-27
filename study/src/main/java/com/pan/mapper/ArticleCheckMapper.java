package com.pan.mapper;

import java.util.List;

import com.pan.entity.ArticleCheck;
import com.pan.query.QueryArticleCheck;
import com.pan.vo.ArticleCheckVO;

/**
 * 
 * @author Administrator
 *
 */
public interface ArticleCheckMapper extends BaseMapper<ArticleCheck>{
	/**
	 * 根据id查找唯一一条审核记录
	 * @param id
	 * @return
	 */
	ArticleCheck findById(Long id);
	/**
	 * 查询文章详细,支持分页
	 * @param queryArticleCheckVO
	 * @return
	 */
	List<ArticleCheck> findByParams(QueryArticleCheck queryArticleCheckVO);
	/**
	 * 查询文章详细,支持分页
	 * @param queryArticleCheckVO
	 * @return
	 */
	List<ArticleCheckVO> findVOByParams(QueryArticleCheck queryArticleCheckVO);
	/**
	 * 查询文章详细,支持分页
	 * @param QueryArticleCheckVO
	 * @return
	 */
	int getCountByParams(QueryArticleCheck QueryArticleCheckVO);
	/**
	 * 更新文章
	 * @param articleCheck
	 */
	int updateArticleCheck(ArticleCheck articleCheck);
}
