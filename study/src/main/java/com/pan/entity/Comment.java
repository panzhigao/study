package com.pan.entity;

import org.hibernate.validator.constraints.NotEmpty;

import com.pan.util.JsonUtils;

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
	@NotEmpty(message="文章id不能为空")
	private String articleId;
	/**
	 * 评论内容
	 */
	@NotEmpty(message="评论内容不能为空")
	private String commentContent;//TODO 评论字符限制
	/**
	 * 接收回复的评论用户
	 */
	private String replyToUserId;
	/**
	 * 用户头像
	 */
	private String userPortrait;
	/**
	 * 用户名
	 */
	private String nickname;
	/**
	 * 是否已点赞
	 */
	private String isChecked;
	/**
	 * 赞的数目,默认0
	 */
	private Integer praiseCounts=0;
	
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
	
	public String getUserPortrait() {
		return userPortrait;
	}
	public void setUserPortrait(String userPortrait) {
		this.userPortrait = userPortrait;
	}
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}
	
	public Integer getPraiseCounts() {
		return praiseCounts;
	}
	public void setPraiseCounts(Integer praiseCounts) {
		this.praiseCounts = praiseCounts;
	}
	@Override
	public String toString() {
		return JsonUtils.toJson(this);
	}
}
