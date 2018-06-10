package com.pan.query;



/**
 * @author 作者
 * @version 创建时间：2018年4月3日 下午3:49:39
 * 类说明
 */
public class QueryCollection extends QueryBase{
	/**
	 * 文章id
	 */
	private String articleId;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 收藏
	 */
	private String collectionId;
	/**
	 * 文章标题
	 */
	private String title;
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCollectionId() {
		return collectionId;
	}
	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "QueryCollectionVO [articleId=" + articleId + ", userId=" + userId + ", collectionId=" + collectionId
				+ ", title=" + title + "]";
	}
	
}
