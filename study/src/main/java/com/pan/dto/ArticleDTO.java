package com.pan.dto;

import com.pan.entity.Article;

public class ArticleDTO extends Article{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1969627060058259340L;
	/**
	 * 用户昵称
	 */
	private String nickname;
	/**
	 * 用户头像
	 */
	private String userPortrait;
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
	
}
