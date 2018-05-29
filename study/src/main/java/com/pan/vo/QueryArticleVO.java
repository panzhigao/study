package com.pan.vo;

import com.pan.common.annotation.QueryParam;


/**
 * @author 作者
 * @version 创建时间：2018年4月3日 下午3:49:39
 * 类说明
 */
public class QueryArticleVO extends QueryVO{
	/**
	 * 文章id
	 */
	//@QueryParam(highLightFlag=false)
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
	@QueryParam(highLightFlag=true)
	private String title;
	/**
	 * 文章类型
	 * 1-文章 2-系统消息
	 */
	private String type;
	/**
	 * 是否热门
	 */
	private String isHot;
	/**
	 * 排序规则
	 */
	private String orderCondition;
	/**
	 * 置顶系数
	 */
	private Integer stick;
	/**
	 * 是否是精品贴
	 */
	private String highQuality;
	
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
	
	public String getIsHot() {
		return isHot;
	}
	public void setIsHot(String isHot) {
		this.isHot = isHot;
	}
	
	public String getOrderCondition() {
		return orderCondition;
	}
	public void setOrderCondition(String orderCondition) {
		this.orderCondition = orderCondition;
	}
	public Integer getStick() {
		return stick;
	}
	public void setStick(Integer stick) {
		this.stick = stick;
	}
	public String getHighQuality() {
		return highQuality;
	}
	public void setHighQuality(String highQuality) {
		this.highQuality = highQuality;
	}
	@Override
	public String toString() {
		return "QueryArticleVO [articleId=" + articleId + ", userId=" + userId
				+ ", status=" + status + ", title=" + title + ", type=" + type
				+ ", isHot=" + isHot + ", orderCondition=" + orderCondition
				+ ", stick=" + stick + ", highQuality=" + highQuality
				+ ", pageSize=" + pageSize + ", pageNo=" + pageNo
				+ ", orderByCondition=" + orderByCondition
				+ ", whereCondition=" + whereCondition + "]";
	}	
}
