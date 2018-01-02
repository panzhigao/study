package com.pan.entity;

/**
 * 用户拓展信息
 * @author Administrator
 *
 */
public class UserExtension extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9116871386273495175L;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 用户简介
	 */
	private String userBrief;

	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserBrief() {
		return userBrief;
	}
	public void setUserBrief(String userBrief) {
		this.userBrief = userBrief;
	}
	@Override
	public String toString() {
		return "UserExtension [userId=" + userId + ", userBrief=" + userBrief
				+ ", getId()=" + getId() + ", getCreateTime()="
				+ getCreateTime() + ", getUpdateTime()=" + getUpdateTime()
				+ "]";
	}
}
