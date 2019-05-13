package com.pan.dto;

import java.io.Serializable;
import java.util.Date;
import com.pan.common.annotation.UnescapeHtml;
import lombok.Data;

/**
 * @author panzhigao
 */
@Data
public class ArticleDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1969627060058259340L;
	/**
	 * 文章id
	 */
	private Long id;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 文章状态 0-审核未通过，1-草稿，2-审核中，3-发布成功
	 */
	private Integer status;
	/**
	 * 发布时间
	 */
	private Date publishTime;
	/**
	 * 文章标题
	 */
	private String title;
	/**
	 * 文章内容
	 */
	@UnescapeHtml
	private String content;
	/**
	 * 文章概要
	 */
	private String outline;
	/**
	 * 文章摘要图片
	 */
	private String image;
	/**
	 * 评论数
	 */
	private Integer commentCount;
	/**
	 * 阅读次数
	 */
	private Integer viewCount;
	/**
	 * 文章类型
	 * 1-文章 2-系统消息
	 */
	private Integer type;
	/**
	 * 置顶系数
	 */
	private Integer top;
	/**
	 * 是否是精品贴,0-否，1-是
	 */
	private Integer highQuality;
	/**
	 * 分类id
	 */
	private Long categoryId;
	/**
	 * 用户昵称
	 */
	private String nickname;
	/**
	 * 用户头像
	 */
	private String userPortrait;
	/**
	 * 分类名称
	 */
	private String categoryName;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建人
	 */
	private Long createUserId;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 修改人
	 */
	private Long updateUserId;
	/**
	 * esId
	 */
	private String esId;
}
