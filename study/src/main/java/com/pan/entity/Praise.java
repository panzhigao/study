package com.pan.entity;

import org.hibernate.validator.constraints.NotEmpty;

public class Praise extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2529849766641254538L;
	/**
	 * 赞id
	 */
	private String praiseId;
	/**
	 * 评论id
	 */
	@NotEmpty(message="评论id不能为空")
	private String commentId;
	/**
	 * 用户id
	 */
	private String userId;
	public String getPraiseId() {
		return praiseId;
	}
	public void setPraiseId(String praiseId) {
		this.praiseId = praiseId;
	}
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
		return "Praise [praiseId=" + praiseId + ", commentId=" + commentId
				+ ", userId=" + userId + ", getId()=" + getId()
				+ ", getCreateTime()=" + getCreateTime() + ", getUpdateTime()="
				+ getUpdateTime() + "]";
	}
}
