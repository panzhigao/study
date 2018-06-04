package com.pan.vo;

import java.util.Date;


/**
 * @author 作者
 * @version 创建时间：2018年4月3日 下午3:49:39
 * 类说明
 */
public class QueryScoreHistoryVO extends QueryVO{
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 积分类型，1-登陆，2-发表文章成功，3-回帖
	 */
	private String type;
	/**
	 * 积分
	 */
	private Integer score;
	/**
	 * 积分日期
	 */
	private Date scoreDate;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Date getScoreDate() {
		return scoreDate;
	}
	public void setScoreDate(Date scoreDate) {
		this.scoreDate = scoreDate;
	}
	@Override
	public String toString() {
		return "QueryScoreHistoryVO [userId=" + userId + ", type=" + type + ", score=" + score + ", scoreDate="
				+ scoreDate + "]";
	}
	
}
