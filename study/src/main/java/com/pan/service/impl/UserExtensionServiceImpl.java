package com.pan.service.impl;


import com.pan.service.AbstractBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pan.entity.UserExtension;
import com.pan.mapper.UserExtensionMapper;
import com.pan.service.IUserExtensionService;

/**
 * @author 作者
 * @version 创建时间：2018年5月29日 下午2:10:49
 * 类说明
 */
@Service
public class UserExtensionServiceImpl extends AbstractBaseService<UserExtension,UserExtensionMapper> implements IUserExtensionService{
	
	private static final Logger logger=LoggerFactory.getLogger(UserExtensionServiceImpl.class);
	
	@Autowired
	private UserExtensionMapper userExtensionMapper;


	/**
	 * 用户评论数，连续登录天数，连续签到天数，文章数变更
	 * @return
	 */
	@Override
	public int increaseCounts(UserExtension userExtension) {
		logger.debug("------增加用户拓展信息数据，用户id：{},新增积分：{}------",userExtension.getId(),userExtension.getTotalScore());
		if(userExtension.getArticleCounts()!=null){
			logger.debug("新增文章数：{}",userExtension.getArticleCounts());
		}
		if(userExtension.getCommentCounts()!=null){
			logger.debug("新增评论数：{}",userExtension.getCommentCounts());
		}
		if(userExtension.getContinuousCheckInDays()!=null){
			logger.debug("新增连续签到天数：{}",userExtension.getContinuousCheckInDays());
		}
		if(userExtension.getContinuousLoginDays()!=null){
			logger.debug("新增连续签登录天数：{}",userExtension.getContinuousLoginDays());
		}
		return userExtensionMapper.increaseCounts(userExtension);
	}

}
