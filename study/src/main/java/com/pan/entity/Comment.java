package com.pan.entity;
/**
 * 评论
 * @author panzhigao-wb
 *
 */
public class Comment extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 854160848698390694L;
	/**
	 * 评论id
	 */
	private String commentId;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 文章id
	 */
	private String articleId;
	/**
	 * 评论内容
	 */
	private String commentContent;
	/**
	 * 接收回复的评论用户
	 */
	private String replyToUserId;
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
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public String getReplyToUserId() {
		return replyToUserId;
	}
	public void setReplyToUserId(String replyToUserId) {
		this.replyToUserId = replyToUserId;
	}
	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", userId=" + userId
				+ ", articleId=" + articleId + ", commentContent="
				+ commentContent + ", replyToUserId=" + replyToUserId
				+ ", getId()=" + getId() + ", getCreateTime()="
				+ getCreateTime() + ", getUpdateTime()=" + getUpdateTime()
				+ "]";
	}
}
