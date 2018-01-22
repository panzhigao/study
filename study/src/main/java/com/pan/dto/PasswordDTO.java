package com.pan.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author panzhigao-wb
 *
 */
public class PasswordDTO {
	/**
	 * 当前密码
	 */
	@NotEmpty(message="当前密码不能为空")
	private String nowPassword;
	/**
	 * 新密码
	 */
	@NotEmpty(message="新密码不能为空")
	@Size(max=12,min=6,message="新密码长度必须在6-12之间")
	@Pattern(regexp="^[\\S]{6,12}$",message="新密码必须6到12位，且不能出现空格")
	private String newPassword;
	/**
	 * 确认密码
	 */
	@NotEmpty(message="确认密码不能为空")
	private String rePassword;
	public String getNowPassword() {
		return nowPassword;
	}
	public void setNowPassword(String nowPassword) {
		this.nowPassword = nowPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getRePassword() {
		return rePassword;
	}
	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}
	@Override
	public String toString() {
		return "PasswordDTO [nowPassword=" + nowPassword + ", newPassword="
				+ newPassword + ", rePassword=" + rePassword + "]";
	}
}
