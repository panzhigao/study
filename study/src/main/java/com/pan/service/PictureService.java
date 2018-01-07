package com.pan.service;

import java.util.List;
import java.util.Map;
import com.pan.entity.Picture;

/**
 * 
 * @author Administrator
 *
 */
public interface PictureService {
	/**
	 * 保存用户图片
	 * @param picture
	 */
	public void savePicture(Picture picture);
	/**
	 * 多条件查询，支持分页
	 * @param params 条件有userId
	 * @return
	 */
	public List<Picture> findByParams(Map<String,Object> params);
	/**
	 * 删除用户下的图片
	 * @param userId
	 * @param pictureId
	 */
	public void deleteByPictureId(String userId,String pictureId);
}
