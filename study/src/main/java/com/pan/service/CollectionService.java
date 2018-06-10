package com.pan.service;

import java.util.Map;
import com.pan.entity.Collection;
import com.pan.query.QueryCollection;

/**
 * 
 * @author Administrator
 *
 */
public interface CollectionService{
	/**
	 * 多条件查询，支持分页
	 * @param params 条件有userId,articleId
	 * @return
	 */
	public Map<String,Object> findByParams(QueryCollection queryCollectionVO);	
	/**
	 * 添加收藏
	 * @param collection
	 */
	public void addCollection(Collection collection);
	/**
	 * 取消收藏
	 * @param collection
	 */
	public void removeCollection(String userId,String articleId);
	/**
	 * 查看文章是否收藏
	 * @param userId
	 * @param articleId
	 * @return
	 */
	public Collection findUserCollection(String userId,String articleId);
	/**
	 * 查询条数
	 * @param params
	 * @return
	 */
	public int getCount(QueryCollection queryCollection);
}
