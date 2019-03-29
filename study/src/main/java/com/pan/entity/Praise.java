package com.pan.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * 点赞实体
 * @author panzhigao
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class Praise extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2529849766641254538L;
	/**
	 * 文章id
	 */
	private Long articleId;
	/**
	 * 评论id
	 */
	@NotNull(message="评论id不能为空")
	private Long commentId;
	/**
	 * 用户id
	 */
	private Long userId;
}
