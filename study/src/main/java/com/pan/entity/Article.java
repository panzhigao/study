package com.pan.entity;

import java.util.Date;

/**
 * 文章实体类
 * @author Administrator
 *
 */
public class Article extends BaseEntity{
	/**
	 * 审核未通过
	 */
	public static final String STATUS_NOT_PASS="0";
	/**
	 * 草稿状态
	 */
	public static final String STATUS_SKETCH="1";
	/**
	 * 审核中
	 */
	public static final String STATUS_IN_REVIEW="2";
	/**
	 * 发布成功
	 */
	public static final String STATUS_PUBLISHED="3";
	/**
	 * 
	 */
	private static final long serialVersionUID = -4357328851427096260L;
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
	private String status;
	/**
	 * 发布时间
	 */
	private Date publishTime;
	/**
	 * 文章标题
	 */
	private String title;
	/**
	 * 文章内容
	 */
	private String content;
	/**
	 * 文章概要
	 */
	private String outline;
	
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getOutline() {
		return outline;
	}

	public void setOutline(String outline) {
		this.outline = outline;
	}

	@Override
	public String toString() {
		return "Article [articleId=" + articleId + ", userId=" + userId
				+ ", status=" + status + ", publishTime=" + publishTime
				+ ", title=" + title + ", content=" + content + ", outline="
				+ outline + ", getId()=" + getId() + ", getCreateTime()="
				+ getCreateTime() + ", getUpdateTime()=" + getUpdateTime()
				+ "]";
	}
}
