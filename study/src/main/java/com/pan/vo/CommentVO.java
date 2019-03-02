package com.pan.vo;

import com.pan.entity.Comment;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 作者
 * @version 创建时间：2018年6月1日 下午3:57:39
 * 类说明
 */
@Data
@EqualsAndHashCode(callSuper=true)
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
}
