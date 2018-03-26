package com.pan.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pan.common.exception.BusinessException;
import com.pan.entity.Picture;
import com.pan.mapper.PictureMapper;
import com.pan.service.PictureService;
import com.pan.util.JsonUtils;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class PictureServiceImpl implements PictureService{
	
	private static final Logger logger = LoggerFactory.getLogger(PictureServiceImpl.class);
	
	@Autowired
	private PictureMapper pictureMapper;
	
	@Value("${picture.dir}")
	private String pictureDir;
	
	@Override
	public void savePicture(Picture picture) {
		pictureMapper.savePicture(picture);
	}
	
	@Override
	public List<Picture> findByParams(Map<String, Object> params) {
		List<Picture> list = new ArrayList<Picture>();
		try {
			logger.info("分页查询文章参数为:{}", JsonUtils.toJson(params));
			String userId=(String) params.get("userId");
			if(StringUtils.isBlank(userId)){
				logger.info("用户id有误",userId);
				return list;
			}
			list = pictureMapper.findByParams(params);
		} catch (Exception e) {
			logger.error("分页查询文章异常", e);
		}
		return list;
	}
	
	@Override
	public void deleteByPictureId(String userId,String pictureId) {
		logger.info("删除的图片id为{}",pictureId);
		if(StringUtils.isBlank(pictureId)){
			logger.info("图片id不能为空",userId);
			throw new BusinessException("图片id不能为空");
		}
		Picture pictureInDb = pictureMapper.findByPictureId(pictureId);
		if(pictureInDb==null){
			logger.info("图片信息不存在",userId);
			throw new BusinessException("图片信息不存在");
		}
		//判断当前删除的图片是否属于当前登录用户
		if(!StringUtils.equals(pictureInDb.getUserId(), userId)){
			logger.info("图片信息不属于当前用户,不能删除,用户id：{}，图片所属用户id：{}",userId,pictureInDb.getUserId());
			throw new BusinessException("图片信息不属于当前用户,不能删除");
		}
		String picUrl=pictureInDb.getPicUrl();
		String fileName=picUrl.substring(picUrl.lastIndexOf("/"));
		File file=new File(pictureDir+fileName);
		if(file.exists()){
			boolean deleted = file.delete();
			if(deleted){
				logger.info("删除图片成功");
			}else{
				logger.error("删除图片失败");
				throw new BusinessException("删除图片失败");
			}
		}else{
			logger.error("删除图片失败，图片不存在",pictureDir+fileName);
			throw new BusinessException("删除图片失败，图片不存在");
		}
		pictureMapper.deleteByPictureId(pictureId);
	}
}
