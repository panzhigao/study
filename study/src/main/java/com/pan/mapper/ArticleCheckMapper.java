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
	 * 查询文章详细,支持分页
	 * @param queryArticleCheck
	 * @return
	 */
	List<ArticleCheckVO> findVOByParams(QueryArticleCheck queryArticleCheck);
}
