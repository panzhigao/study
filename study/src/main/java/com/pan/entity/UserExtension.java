package com.pan.entity;

import lombok.Data;

/**
 * 用户拓展信息
 * @author Administrator
 *
 */
@Data
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
	/**
	 * 连续登陆天数
	 */
	private Integer continuousLoginDays;
	/**
	 * 连续签到天数
	 */
	private Integer continuousCheckInDays;
}
