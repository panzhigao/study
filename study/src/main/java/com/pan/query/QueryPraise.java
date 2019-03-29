package com.pan.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author panzhigao
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class QueryPraise extends QueryBase{
	/**
	 * 评论id
	 */
	private Long commentId;
	/**
	 * 用户id
	 */
	private Long userId;
}
