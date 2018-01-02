package com.pan.dto;

import com.pan.entity.User;
import com.pan.entity.UserExtension;

/**
 * 
 * @author Administrator
 *
 */
public class UserInfoDTO {
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 手机号码
	 */
	private String telephone;
	/**
	 * 用户简介
	 */
	private String userBrief;
	/**
	 * 用户头像
	 */
	private String userPortrait;
	
	public UserInfoDTO() {
		
	}
	
	public UserInfoDTO(User user,UserExtension userExtension){
		if(user!=null){
			this.userId=user.getUserId();
			this.username=user.getUsername();
			this.nickname=user.getNickname();
			this.telephone=user.getTelephone();
			this.userPortrait=user.getUserPortrait();
		}
		if(userExtension!=null){
			this.userBrief=userExtension.getUserBrief();
		}
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getUserBrief() {
		return userBrief;
	}

	public void setUserBrief(String userBrief) {
		this.userBrief = userBrief;
	}

	public String getUserPortrait() {
		return userPortrait;
	}

	public void setUserPortrait(String userPortrait) {
		this.userPortrait = userPortrait;
	}
	
	
}
