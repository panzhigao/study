package com.pan.service;

import com.pan.entity.Collection;

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
	 * @param userId 用户id
	 * @param id 收藏id
	 */
	void removeCollection(Long userId,Long id);
	/**
	 * 查看文章是否收藏
	 * @param userId
	 * @param articleId 文章id
	 * @return
	 */
	boolean checkArticleCollected(Long userId,Long articleId);
}
