package com.pan.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

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
	private String userId;
	/**
	 * 文章id
	 */
	@NotEmpty(message = "文章id不能为空")
	private String articleId;
	/**
	 * 文章标题
	 */
	private String title;
}
