package com.pan.entity;

import java.util.Date;
import javax.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import com.pan.common.annotation.UnescapeHtml;

/**
 * 文章实体类
 * @author Administrator
 *
 */
@Data
public class Article extends BaseEntity{
	/**
	 *
	 */
	private static final long serialVersionUID = -4357328851427096260L;
	/**
	 * 审核未通过
	 */
	public static final String STATUS_NOT_PASS="0";
	/**
	 * 草稿状态
	 */
	public static final String STATUS_SKETCH="1";
	/**
	 * 审核中
	 */
	public static final String STATUS_IN_REVIEW="2";
	/**
	 * 发布成功
	 */
	public static final String STATUS_PUBLISHED="3";
	/**
	 * 文章类型 1-文章 2-系统消息
	 */
	public static final String TYPE_ARTICLE="1";
	/**
	 * 文章类型 1-文章 2-系统消息
	 */
	public static final String TYPE_SYSTEM_MESSAGE="2";
	/**
	 * 文章id
	 */
	private String articleId;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 文章状态 0-审核未通过，1-草稿，2-发布中
	 */
	private String status;
	/**
	 * 发布时间
	 */
	private Date publishTime;
	/**
	 * 文章标题
	 */
	@NotEmpty(message="文章标题不能为空")
	@Size(max=30,min=5,message="文章标题长度必须在5-30之间")
	private String title;
	/**
	 * 文章内容
	 */
	@NotEmpty(message="文章内容不能为空")
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
	private String type;
	/**
	 * 置顶系数
	 */
	private Integer stick;
	/**
	 * 是否是精品贴,0-否，1-是
	 */
	private String highQuality;
}
