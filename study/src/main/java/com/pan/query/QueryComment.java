package com.pan.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author panzhigao
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class QueryComment extends QueryBase{
	/**
	 * 评论id
	 */
	private String commentId;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 文章id
	 */
	private Long articleId;
}
