package com.pan.entity;

import com.pan.util.JsonUtils;

public class Collection extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7403757517265558708L;
	/**
	 * 收藏id
	 */
	private String collectionId;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 文章id
	 */
	private String articleId;
	/**
	 * 文章标题
	 */
	private String title;
	
	public String getCollectionId() {
		return collectionId;
	}
	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return JsonUtils.toJson(this);
	}
}
