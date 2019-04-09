package com.pan.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询文章分类
 * @author panzhigao
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class QueryArticleCategory extends QueryBase {
	/**
	 * 状态，0-未启用，1-启用
	 */
    private Integer status;
	/**
	 * 分类名称
	 */
	private String categoryName;
}
