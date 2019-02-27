package com.pan.service;

import java.util.Map;
import com.pan.entity.ArticleCheck;
import com.pan.query.QueryArticleCheck;

/**
 * @author panzhigao
 */
public interface ArticleCheckService extends BaseService<ArticleCheck>{
	/**
	 * 分页查询
	 * @param articleCheckVO
	 * @return
	 */
	Map<String,Object> findByParams(QueryArticleCheck articleCheckVO);

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
}
