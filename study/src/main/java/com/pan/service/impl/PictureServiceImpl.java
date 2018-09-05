package com.pan.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.pan.common.exception.BusinessException;
import com.pan.entity.Picture;
import com.pan.mapper.PictureMapper;
import com.pan.query.QueryPicture;
import com.pan.service.PictureService;
import com.pan.util.JsonUtils;

/**
 * 
 * 图片方法
 *
 */
@Service
public class PictureServiceImpl implements PictureService{
	
	private static final Logger logger = LoggerFactory.getLogger(PictureServiceImpl.class);
	
	@Autowired
	private PictureMapper pictureMapper;
	
	@Value("${picture.saveDir}")
	private String pictureSaveDir;
	
	@Override
	public void savePicture(Picture picture) {
		pictureMapper.savePicture(picture);
	}
	
	@Override
	public List<Picture> findByParams(QueryPicture queryPicture) {
		List<Picture> list = new ArrayList<Picture>();
		try {
			logger.info("分页查询图片参数为:{}", JsonUtils.toJson(queryPicture));
			String userId=queryPicture.getUserId();
			if(StringUtils.isBlank(userId)){
				logger.info("用户id有误",userId);
				return list;
			}
			list = pictureMapper.findByParams(queryPicture);
		} catch (Exception e) {
			logger.error("分页查询文章异常", e);
		}
		return list;
	}
	
	@Override
	public void deleteByPictureIds(String userId,String pictureIds) {
		logger.info("删除的图片ids为{}",pictureIds);
		if(StringUtils.isBlank(pictureIds)){
			logger.info("图片id不能为空",userId);
			throw new BusinessException("图片id不能为空");
		}
		List<String> list=Arrays.asList(pictureIds.split(","));
		int length=list.size();
		int deleteCount = pictureMapper.deleteByPictureIds(list,userId);
		if(deleteCount!=length){
			logger.error("用户删除图片数：{}，实际删除图片数：{}",length,deleteCount);
			throw new BusinessException("删除图片信息失败，请重试");
		}
	}
	
	/**
	 * 删除图片
	 * @param filePath 图片实际路径
	 * @return
	 */
	private void deletePicture(String filePath){
		File file=new File(filePath);
		if(file.exists()){
			boolean deleted = file.delete();
			if(deleted){
				logger.info("删除图片成功");
			}else{
				logger.error("删除图片失败");
				throw new BusinessException("删除图片失败");
			}
		}else{
			logger.error("删除图片失败，图片不存在，图片路径：{}",filePath);
			throw new BusinessException("删除图片失败，图片不存在");
		}
	}
	
	@Override
	public int getCountByParams(QueryPicture queryPicture) {
		return pictureMapper.getCountByParams(queryPicture);
	}
}
