package com.pan.service;

import com.pan.entity.ArticleCheck;
import com.pan.query.QueryArticleCheck;
import com.pan.query.QueryBase;

import java.util.Map;

/**
 * @author panzhigao
 */
public interface ArticleCheckService extends BaseService<ArticleCheck>{
	/**
	 * 新增文章审核记录
	 * @param articleCheck
	 */
	void addArticleCheck(ArticleCheck articleCheck);
	/**
	 * 审核通过
	 * @param id
	 */
	void passArticleCheck(Long id);
	/**
	 * 审核未通过
	 * @param id
	 * @param reason
	 */
	void notPassArticle(Long id,String reason);
	/**
	 * 查询分页数据，包括总条数
	 * @param queryArticleCheck
	 * @return
	 */
	Map<String, Object> findVOPageableMap(QueryArticleCheck queryArticleCheck);
}
