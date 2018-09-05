package com.pan.service;

import java.util.List;
import com.pan.entity.Picture;
import com.pan.query.QueryPicture;

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
	public List<Picture> findByParams(QueryPicture queryPicture);
	/**
	 * 批量删除用户下的图片
	 * @param userId
	 * @param pictureId
	 */
	public void deleteByPictureIds(String userId,String pictureId);
	/**
	 * 获取图片数
	 * @param queryPicture
	 * @return
	 */
	public int getCountByParams(QueryPicture queryPicture);
}
