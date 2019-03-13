package com.pan.service;

import com.pan.entity.Collection;
import com.pan.query.QueryCollection;

/**
 * 
 * @author Administrator
 *
 */
public interface CollectionService extends BaseService<Collection>{
	/**
	 * 添加收藏
	 * @param collection
	 */
	void addCollection(Collection collection);
	/**
	 * 取消收藏
	 * @param userId
	 * @param articleId
	 */
	void removeCollection(String userId,String articleId);
	/**
	 * 查看文章是否收藏
	 * @param userId
	 * @param articleId
	 * @return
	 */
	Collection findUserCollection(String userId,String articleId);
}
