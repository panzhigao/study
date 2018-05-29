package com.pan.entity;

/**
 * 用户拓展信息
 * @author Administrator
 *
 */
public class UserExtension extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9116871386273495175L;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 用户昵称
	 */
	private String nickname;
	/**
	 * 用户头像
	 */
	private String userPortrait;
	/**
	 * 文章数
	 */
	private Integer articleCounts;
	/**
	 * 评论数
	 */
	private Integer commentCounts;
	/**
	 * 积分
	 */
	private Integer score;
	/**
	 * 用户简介
	 */
	private String userBrief;

	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public Integer getArticleCounts() {
		return articleCounts;
	}
	public void setArticleCounts(Integer articleCounts) {
		this.articleCounts = articleCounts;
	}
	public Integer getCommentCounts() {
		return commentCounts;
	}
	public void setCommentCounts(Integer commentCounts) {
		this.commentCounts = commentCounts;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	@Override
	public String toString() {
		return "UserExtension [userId=" + userId + ", nickname=" + nickname
				+ ", userPortrait=" + userPortrait + ", articleCounts="
				+ articleCounts + ", commentCounts=" + commentCounts
				+ ", score=" + score + ", userBrief=" + userBrief + ", id="
				+ id + ", createTime=" + createTime + ", createUser="
				+ createUser + ", updateTime=" + updateTime + ", updateUser="
				+ updateUser + "]";
	}
}
