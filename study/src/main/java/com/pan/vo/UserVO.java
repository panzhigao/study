package com.pan.vo;

import lombok.Data;

@Data
public class UserVO {
	/**
	 * 用户id
	 */
	private Long id;
    /**
     * 用户性别
     */
    private Integer sex;
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 用户状态
     * 0禁用，1正常
     */
    private Integer status;
    /**
     * 用户头像
     */
    private String userPortrait;
    /**
     * 当前用户是否是管理员
     * 0-否,1-是
     */
    private Integer adminFlag;
    /**
     * 地址
     */
    private String address;
	/**
	 * 用户简介
	 */
	private String userBrief;

}
