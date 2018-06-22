package com.pan.vo;

import com.pan.entity.ArticleCheck;

/**
 * @author 作者
 * @version 创建时间：2018年6月22日 下午3:28:21
 * 类说明
 */
public class ArticleCheckVO extends ArticleCheck{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2289815052936357272L;
	
	private String username;
	
	private String nickname;

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

	@Override
	public String toString() {
		return "ArticleCheckVO [username=" + username + ", nickname="
				+ nickname + ", id=" + id + ", createTime=" + createTime
				+ ", createUser=" + createUser + ", updateTime=" + updateTime
				+ ", updateUser=" + updateUser + "]";
	}
}
