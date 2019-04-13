package com.pan.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.NotNull;


/**
 * 收藏实体
 * @author panzhigao
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class Collection extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7403757517265558708L;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 文章id
	 */
	@NotNull(message = "文章id不能为空")
	private Long articleId;
	/**
	 * 文章标题
	 */
	private String title;
}
