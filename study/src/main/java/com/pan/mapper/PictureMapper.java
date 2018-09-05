package com.pan.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pan.entity.Picture;
import com.pan.query.QueryPicture;

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
	 * 保存图片信息
	 * @param picture
	 */
	public void savePicture(Picture picture);
	/**
	 * 查询文章详细,支持分页
	 * @param params
	 * @return
	 */
	public List<Picture> findByParams(QueryPicture queryPicture);
	/**
	 * 根据图片ids删除图片
	 * @param pictureId
	 */
	public int deleteByPictureIds(@Param("picIds")List<String> picIds,@Param("userId")String userId);
	/**
	 * 查询条数
	 * @return
	 */
	public int getCountByParams(QueryPicture queryPicture);
}
