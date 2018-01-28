package com.pan.entity;

import java.util.Date;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.pan.common.annotation.CheckUsernameGroup;
import com.pan.common.annotation.LoginGroup;
import com.pan.common.annotation.RegisterGroup;
import com.pan.common.annotation.TelephoneBindGroup;
import com.pan.common.annotation.UserEditGroup;

/**
 * 用户信息实体类
 * @author Administrator
 *
 */
public class User extends BaseEntity{
	/**
	 * 禁用状态
	 */
	public static final String STATUS_BLOCKED="0";
	/**
	 * 正常状态
	 */
	public static final String STATUS_NORMAL="1";
	/**
	 * 
	 */
	private static final long serialVersionUID = -2820248584713373399L;
	
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 用户性别
	 */
	private String sex;
	/**
	 * 用户名
	 */
	@NotEmpty(message="用户名不能为空",groups={LoginGroup.class,RegisterGroup.class,CheckUsernameGroup.class})
	@Size(max=15,min=5,message="用户名长度必须在5-15之间",groups={RegisterGroup.class,CheckUsernameGroup.class})
	@Pattern(regexp="^(?![0-9]+$)([a-zA-Z0-9_\\.\\-]+$)",message="用户名只能包含字母，数字，点和下划线,且不能全为数字",groups={RegisterGroup.class,CheckUsernameGroup.class})
	private String username;
	/**
	 * 昵称
	 */
	@NotEmpty(message="昵称不能为空",groups={RegisterGroup.class,UserEditGroup.class})
	@Size(max=10,min=2,message="昵称长度必须在2-10之间",groups={RegisterGroup.class,UserEditGroup.class})
	@Pattern(regexp="^[\\u4e00-\\u9fff\\w]{2,10}$",message="昵称只能包含2-10位字母，数字，中文和下划线",groups={RegisterGroup.class,UserEditGroup.class})
	private String nickname;
	/**
	 * 用户密码
	 */
	@NotEmpty(message="密码不能为空",groups={LoginGroup.class,RegisterGroup.class})
	@Size(max=12,min=6,message="密码长度必须在6-12之间",groups={RegisterGroup.class})
	@Pattern(regexp="^[a-zA-Z0-9_\\.\\-]+$",message="密码只能包含字母，数字，点和下划线",groups={RegisterGroup.class})
	private String password;

	/**
	 * 最近登录时间
	 */
	private Date lastLoginTime;
	/**
	 * 用户状态
	 * 0禁用，1正常
	 */
	private String status;
	/**
	 * 手机号码
	 */
	@NotEmpty(message="手机号不能为空",groups={TelephoneBindGroup.class})
	@Pattern(regexp="^1[3|5|8]{1}[0-9]{9}$",message="请输入正确的手机号码",groups={TelephoneBindGroup.class})
	private String telephone;
	/**
	 * 用户头像
	 */
	private String userPortrait;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	
	public String getUserPortrait() {
		return userPortrait;
	}
	public void setUserPortrait(String userPortrait) {
		this.userPortrait = userPortrait;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", sex=" + sex + ", username="
				+ username + ", nickname=" + nickname + ", password="
				+ password + ", lastLoginTime=" + lastLoginTime + ", status="
				+ status + ", telephone=" + telephone + ", userPortrait="
				+ userPortrait + ", getId()=" + getId() + ", getCreateTime()="
				+ getCreateTime() + ", getUpdateTime()=" + getUpdateTime()
				+ "]";
	}

}
