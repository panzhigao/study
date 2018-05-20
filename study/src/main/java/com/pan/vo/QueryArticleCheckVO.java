package com.pan.vo;

/**
 * @author 作者
 * @version 创建时间：2018年4月3日 下午3:49:39
 * 类说明
 */
public class QueryArticleCheckVO extends QueryVO{
	/**
	 * 文章id
	 */
	private String articleId;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 文章状态 0-审核未通过，1-草稿，2-发布中
	 */
	private String completeFlag;
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
	public String getCompleteFlag() {
		return completeFlag;
	}
	public void setCompleteFlag(String completeFlag) {
		this.completeFlag = completeFlag;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "QueryArticleCheckVO [articleId=" + articleId + ", userId=" + userId + ", completeFlag=" + completeFlag
				+ ", title=" + title + "]";
	}
}
