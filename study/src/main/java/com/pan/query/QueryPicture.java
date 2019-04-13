package com.pan.query;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author panzhigao
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class QueryPicture extends QueryBase{
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 图片id
	 */
	private String pictureId;
}
