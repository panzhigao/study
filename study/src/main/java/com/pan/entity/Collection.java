package com.pan.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
	 * 收藏id
	 */
	private String collectionId;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 文章id
	 */
	private String articleId;
	/**
	 * 文章标题
	 */
	private String title;
}
