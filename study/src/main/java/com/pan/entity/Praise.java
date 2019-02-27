package com.pan.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

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
	 * 赞id
	 */
	private String praiseId;
	/**
	 * 评论id
	 */
	@NotEmpty(message="评论id不能为空")
	private String commentId;
	/**
	 * 用户id
	 */
	private String userId;
}
