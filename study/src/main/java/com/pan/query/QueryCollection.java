package com.pan.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 作者
 * @version 创建时间：2018年4月3日 下午3:49:39
 * 类说明
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class QueryCollection extends QueryBase{
	/**
	 * 文章id
	 */
	private String articleId;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 收藏
	 */
	private String collectionId;
	/**
	 * 文章标题
	 */
	private String title;
}
