package com.pan.vo;

import com.pan.common.annotation.QueryType;
import com.pan.common.enums.QueryTypeEnum;

/**
 * @author 作者
 * @version 创建时间：2018年4月3日 下午2:10:39
 * 类说明
 */
public class PzgVO extends QueryVO{
	
	@QueryType(queryType=QueryTypeEnum.TERM)
	private String nickname;
	
	private String password;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
