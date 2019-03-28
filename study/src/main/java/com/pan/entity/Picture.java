package com.pan.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 图片信息
 * @author Administrator
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
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
	private Long userId;
	/**
	 * 图片路径
	 */
	private String pictureUrl;
	/**
	 * 图片保存路径
	 */
	private String picturePath;
}
