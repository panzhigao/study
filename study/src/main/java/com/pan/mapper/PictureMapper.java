package com.pan.mapper;

import java.util.List;
import java.util.Map;
import com.pan.entity.Picture;

/**
 * 
 * @author Administrator
 *
 */
public interface PictureMapper {
	/**
	 * 根据pictureId查找图片
	 * @param pictureId
	 * @return
	 */
	public Picture findByPictureId(String pictureId);
	/**
	 * 根据userId查找图片信息集合
	 * @param userId
	 * @return
	 */
	public List<Picture> findListByUserId(String userId);
	/**
	 * 保存图片信息
	 * @param picture
	 */
	public void savePicture(Picture picture);
	/**
	 * 查询文章详细,支持分页
	 * @param params
	 * @return
	 */
	public List<Picture> findByParams(Map<String,Object> params);
	/**
	 * 根据图片id删除图片
	 * @param pictureId
	 */
	public void deleteByPictureId(String pictureId);
}
