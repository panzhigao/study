package com.pan.query;

public class QueryPraise extends QueryBase{
	/**
	 * 评论id
	 */
	private String commentId;
	/**
	 * 用户id
	 */
	private String userId;
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "QueryPraise [commentId=" + commentId + ", userId=" + userId + "]";
	}
}
