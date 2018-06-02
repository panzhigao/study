package com.pan.entity;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.pan.common.annotation.UnescapeHtml;
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
	@Size(max=300,message="评论内容不能超过300个字")
	@UnescapeHtml
	private String commentContent;
	/**
	 * 接收回复的评论用户
	 */
	private String replyToUserId;
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
