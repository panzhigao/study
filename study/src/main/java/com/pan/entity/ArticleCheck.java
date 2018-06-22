package com.pan.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.pan.common.annotation.UnescapeHtml;

public class ArticleCheck extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4213601853452299514L;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 文章id
	 */
	@NotNull(message="文章id不能为空")
	private String articleId;
	/**
	 * 文章标题
	 */
	@NotNull(message="文章标题不能为空")
	private String title;
	/**
	 * 是否审核完成，0-否，1-是
	 */
	private String completeFlag;
	/**
	 * 审核类型，0-创建，1-修改
	 */
	private String checkType;
	/**
	 * 审核人id
	 */
	private String checkUserId;
	/**
	 * 审核人姓名
	 */
	private String checkUsername;
	/**
	 * 审核时间
	 */
	private Date checkTime;
	/**
	 * 通过标志，是否通过，0-否，1-是
	 */
	private String approveFlag;
	/**
	 * 文章内容
	 */
	@UnescapeHtml
	private String content;
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCompleteFlag() {
		return completeFlag;
	}
	public void setCompleteFlag(String completeFlag) {
		this.completeFlag = completeFlag;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public String getCheckUserId() {
		return checkUserId;
	}
	public void setCheckUserId(String checkUserId) {
		this.checkUserId = checkUserId;
	}
	public String getCheckUsername() {
		return checkUsername;
	}
	public void setCheckUsername(String checkUsername) {
		this.checkUsername = checkUsername;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	
	public String getApproveFlag() {
		return approveFlag;
	}
	public void setApproveFlag(String approveFlag) {
		this.approveFlag = approveFlag;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "ArticleCheck [userId=" + userId + ", articleId=" + articleId
				+ ", title=" + title + ", completeFlag=" + completeFlag
				+ ", checkType=" + checkType + ", checkUserId=" + checkUserId
				+ ", checkUsername=" + checkUsername + ", checkTime="
				+ checkTime + ", approveFlag=" + approveFlag + ", content="
				+ content + ", id=" + id + ", createTime=" + createTime
				+ ", createUser=" + createUser + ", updateTime=" + updateTime
				+ ", updateUser=" + updateUser + "]";
	}
	
	public enum CompleteFlagEnum{
		COMPLETE("已完成","1"),
		NOT_COMPLETE("未完成","0");
		private String name;
		private String code;
		
		CompleteFlagEnum(String name,String code){
			this.name=name;
			this.code=code;
		}
		public String getName() {
			return name;
		}
		public String getCode() {
			return code;
		}	
	}
	
	public enum CheckTypeEnum{
		CREATE("创建","0"),
		UPDATE("修改","1");
		private String name;
		private String code;
		
		CheckTypeEnum(String name,String code){
			this.name=name;
			this.code=code;
		}
		public String getName() {
			return name;
		}
		public String getCode() {
			return code;
		}	
	}
	
	public enum ApproveFlagEnum{
		APPROVED("通过","1"),
		NOT_APPROVED("未通过","0");
		private String name;
		private String code;
		
		ApproveFlagEnum(String name,String code){
			this.name=name;
			this.code=code;
		}
		public String getName() {
			return name;
		}
		public String getCode() {
			return code;
		}	
	}
}
