package com.pan.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pan.common.exception.BusinessException;
import com.pan.entity.Picture;
import com.pan.mapper.PictureMapper;
import com.pan.service.AbstractBaseService;
import com.pan.service.PictureService;
import com.pan.util.TokenUtils;

/**
 * 
 * 图片方法
 *
 */
@Service
public class PictureServiceImpl extends AbstractBaseService<Picture, PictureMapper> implements PictureService{
	
	private static final Logger logger = LoggerFactory.getLogger(PictureServiceImpl.class);
	
	@Autowired
	private PictureMapper pictureMapper;
		
	@Override
	protected PictureMapper getBaseMapper() {
		return pictureMapper;
	}
		
	@Override
	public void deleteByPictureIds(Long userId,String pictureIds) {
		logger.info("删除的图片ids为{}",pictureIds);
		if(pictureIds==null){
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

	@Override
	public void insertPicture(String pictureUrl, String picturePath) {
		Picture picture=new Picture();
		picture.setUserId(TokenUtils.getLoginUserId());
        picture.setPictureUrl(pictureUrl);
        picture.setPicturePath(picturePath);
        picture.setCreateTime(new Date());
        pictureMapper.insertSelective(picture);
	}
	
}
