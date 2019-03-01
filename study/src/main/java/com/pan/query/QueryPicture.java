package com.pan.query;


import lombok.Data;

/**
 * @author panzhigao
 */
@Data
public class QueryPicture extends QueryBase{
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 图片id
	 */
	private String pictureId;
}
