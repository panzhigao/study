package com.pan.mapper;

import java.util.List;
import com.pan.entity.ArticleCheck;
import com.pan.vo.QueryArticleCheckVO;

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
	public List<QueryArticleCheckVO> findByParams(QueryArticleCheckVO queryArticleCheckVO);
	/**
	 * 查询文章详细,支持分页
	 * @param params
	 * @return
	 */
	public int getCountByParams(QueryArticleCheckVO QueryArticleCheckVO);
	/**
	 * 更新文章
	 * @param article
	 */
	public int updateArticleCheck(ArticleCheck articleCheck);
}
