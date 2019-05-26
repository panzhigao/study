package com.pan.service;

import com.pan.entity.Picture;

/**
 * 
 * @author Administrator
 *
 */
public interface IPictureService extends BaseService<Picture>{
	/**
	 * 批量删除用户下的图片
	 * @param userId
	 * @param pictureIds
	 */
	void deleteByPictureIds(Long userId,String pictureIds);
	/**
	 * 
	 * @param pictureUrl 图片访问url
	 * @param picturePath 图片保存路径
	 * @return
	 */
	void insertPicture(String pictureUrl,String picturePath);
}
