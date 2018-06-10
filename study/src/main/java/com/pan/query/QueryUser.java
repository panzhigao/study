package com.pan.query;


/**
 * @author 作者
 * @version 创建时间：2018年3月28日 下午5:17:24
 * 类说明
 */
public class QueryUser extends QueryBase{
	/**
	 * 用户性别
	 */
	private String sex;
	/**
	 * 用户昵称
	 */
	private String nickname;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 手机号
	 */
	private String telephone;
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "QueryUserVO [sex=" + sex + ", nickname=" + nickname
				+ ", username=" + username + ", status=" + status
				+ ", telephone=" + telephone + "]";
	}
	
}
