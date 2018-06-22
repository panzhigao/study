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
public interface ArticleCheckMapper {
	/**
	 * 根据id查找唯一一条审核记录
	 * @param articleId
	 * @return
	 */
	public ArticleCheck findById(String id);
	/**
	 * 保存文章信息
	 * @param article
	 */
	public void saveArticleCheck(ArticleCheck articleCheck);
	/**
	 * 查询文章详细,支持分页
	 * @param params
	 * @return
	 */
	public List<ArticleCheck> findByParams(QueryArticleCheck queryArticleCheckVO);
	/**
	 * 查询文章详细,支持分页
	 * @param params
	 * @return
	 */
	public List<ArticleCheckVO> findVOByParams(QueryArticleCheck queryArticleCheckVO);
	/**
	 * 查询文章详细,支持分页
	 * @param params
	 * @return
	 */
	public int getCountByParams(QueryArticleCheck QueryArticleCheckVO);
	/**
	 * 更新文章
	 * @param article
	 */
	public int updateArticleCheck(ArticleCheck articleCheck);
}
