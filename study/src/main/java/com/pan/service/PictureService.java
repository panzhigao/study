package com.pan.service;

import com.pan.entity.Picture;

/**
 * 
 * @author Administrator
 *
 */
public interface PictureService extends BaseService<Picture>{
	/**
	 * 批量删除用户下的图片
	 * @param userId
	 * @param pictureId
	 */
	public void deleteByPictureIds(String userId,String pictureId);
	/**
	 * 
	 * @param pictureUrl 图片访问url
	 * @param picturePath 图片保存路径
	 * @return
	 */
	public void insertPicture(String pictureUrl,String picturePath);
}
