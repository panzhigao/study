package com.pan.dto;

import com.pan.entity.User;
import com.pan.entity.UserExtension;
import lombok.Data;

/**
 * 
 * @author Administrator
 *
 */
@Data
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
}
