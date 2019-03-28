package com.pan.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.pan.entity.Picture;

/**
 * 
 * @author Administrator
 *
 */
public interface PictureMapper extends BaseMapper<Picture>{
	/**
	 * 根据pictureId查找图片
	 * @param pictureId
	 * @return
	 */
	Picture findByPictureId(Long pictureId);
	/**
	 * 根据图片ids删除图片
	 * @param picIds
	 * @param userId
	 */
	int deleteByPictureIds(@Param("picIds")List<String> picIds,@Param("userId")Long userId);
}
