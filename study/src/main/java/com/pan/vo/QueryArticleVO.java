package com.pan.vo;

import com.pan.common.annotation.QueryParam;


/**
 * @author 作者
 * @version 创建时间：2018年4月3日 下午3:49:39
 * 类说明
 */
@QueryParam(highLightFlag=true)
public class QueryArticleVO extends QueryVO{
	/**
	 * 文章id
	 */
	@QueryParam(highLightFlag=false)
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
	 * 文章标题
	 */
	private String title;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "QueryArticleVO [articleId=" + articleId + ", userId=" + userId
				+ ", status=" + status + ", title=" + title + ", type=" + type
				+ "]";
	}	
}
