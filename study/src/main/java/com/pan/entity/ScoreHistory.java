package com.pan.entity;

import java.util.Date;

/**
 * 积分历史
 * @author Administrator
 *
 */
public class ScoreHistory extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 803308973609346463L;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 积分类型，1-登陆，2-发表文章成功，3-回帖，4-签到
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
		return "ScoreHistory [userId=" + userId + ", type=" + type + ", score=" + score + ", scoreDate=" + scoreDate
				+ ", id=" + id + ", createTime=" + createTime + ", createUser=" + createUser + ", updateTime="
				+ updateTime + ", updateUser=" + updateUser + "]";
	}
	
	public static enum ScoreType{
		LOGIN("1","登陆",5),
		PUBLISH_ARTICLE("2","发表文章成功",5),
		COMMENT("3","回帖",2),
		CHECK_IN("4","签到",5);
		String code;
		String name;
		Integer score;
		private ScoreType(String code, String name, Integer score) {
			this.code = code;
			this.name = name;
			this.score = score;
		}
		public String getCode() {
			return code;
		}
		public String getName() {
			return name;
		}
		public Integer getScore() {
			return score;
		}
		
		
	}
}
