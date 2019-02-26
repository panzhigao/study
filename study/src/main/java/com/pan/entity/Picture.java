package com.pan.entity;

import lombok.Data;

/**
 * 图片信息
 * @author Administrator
 *
 */
@Data
public class Picture extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7161784937128201170L;
	/**
	 * 图片id
	 */
	private String pictureId;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 图片路径
	 */
	private String picUrl;
}
