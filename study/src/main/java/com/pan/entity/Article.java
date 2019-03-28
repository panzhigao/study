package com.pan.entity;

import java.util.Date;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;
import com.pan.common.annotation.UnescapeHtml;

/**
 * 文章实体类
 * @author Administrator
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class Article extends BaseEntity{
	/**
	 *
	 */
	private static final long serialVersionUID = -4357328851427096260L;
	/**
	 * 用户id
	 */
	private String userId;
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
	@NotEmpty(message="文章标题不能为空")
	@Size(max=100,message="文章最大100个字符")
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
	private Integer type;
	/**
	 * 置顶系数
	 */
	private Integer stick;
	/**
	 * 是否是精品贴,0-否，1-是
	 */
	private Integer highQuality;
}
