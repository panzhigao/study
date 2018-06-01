package com.pan.vo;

import com.pan.entity.Comment;

/**
 * @author 作者
 * @version 创建时间：2018年6月1日 下午3:57:39
 * 类说明
 */
public class CommentVO extends Comment{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7331692834185889290L;
	/**
	 * 用户头像
	 */
	private String userPortrait;
	/**
	 * 用户名
	 */
	private String nickname;
	/**
	 * 是否已点赞
	 */
	private String isChecked;
	/**
	 * 文章标题
	 */
	private String title;
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
	public String getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "CommentVO [userPortrait=" + userPortrait + ", nickname="
				+ nickname + ", isChecked=" + isChecked + ", title=" + title
				+ ", id=" + id + ", createTime=" + createTime + ", createUser="
				+ createUser + ", updateTime=" + updateTime + ", updateUser="
				+ updateUser + "]";
	}
}
