package com.pan.vo;

import com.pan.entity.ScoreHistory;

public class ScoreHistoryVO extends ScoreHistory{

	/**
	 * 
	 */
	private static final long serialVersionUID = -431889301381801069L;
	/**
	 * 用户头像
	 */
	private String userPortrait;
	/**
	 * 用户昵称
	 */
	private String nickname;
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
	@Override
	public String toString() {
		return "ScoreHistoryVO [userPortrait=" + userPortrait + ", nickname="
				+ nickname + ", id=" + id + ", createTime=" + createTime
				+ ", createUser=" + createUser + ", updateTime=" + updateTime
				+ ", updateUser=" + updateUser + "]";
	}
}
