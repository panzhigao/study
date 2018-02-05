package com.pan.entity;

import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.pan.util.JsonUtils;


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
	@NotEmpty(message="文章标题不能为空")
	@Size(max=30,min=5,message="文章标题长度必须在5-30之间")
	private String title;
	/**
	 * 文章内容
	 */
	@NotEmpty(message="文章内容不能为空")
	private String content;
	/**
	 * 文章概要
	 */
	private String outline;
	/**
	 * 文章摘要图片
	 */
	private String image;
	/**
	 * 用户昵称
	 */
	private String nickname;
	/**
	 * 用户头像
	 */
	private String userPortrait;
	/**
	 * 评论数
	 */
	private Integer commentCount;
	/**
	 * 阅读次数
	 */
	private Integer viewCount;
	/**
	 * 文章类型
	 * 1-文章 2-系统消息
	 */
	private String type;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUserPortrait() {
		return userPortrait;
	}

	public void setUserPortrait(String userPortrait) {
		this.userPortrait = userPortrait;
	}
	
	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
	
	public Integer getViewCount() {
		return viewCount;
	}
	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return JsonUtils.toJson(this);
	}
}
