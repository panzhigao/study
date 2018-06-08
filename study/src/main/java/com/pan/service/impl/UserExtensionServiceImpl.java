package com.pan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pan.entity.UserExtension;
import com.pan.mapper.UserExtensionMapper;
import com.pan.service.UserExtensionService;
import com.pan.vo.QueryUserExtensionVO;

/**
 * @author 作者
 * @version 创建时间：2018年5月29日 下午2:10:49
 * 类说明
 */
@Service
public class UserExtensionServiceImpl implements UserExtensionService{
	
	@Autowired
	private UserExtensionMapper userExtensionMapper;
	
	@Override
	public List<UserExtension> findByParams(
			QueryUserExtensionVO queryUserExtensionVO) {
		return userExtensionMapper.findByParams(queryUserExtensionVO);
	}

	@Override
	public void updateByUserId(UserExtension userExtension) {
		userExtensionMapper.updateUserExtensionByUserId(userExtension);
	}

	@Override
	public UserExtension findByUserId(String userId) {
		return userExtensionMapper.findByUserId(userId);
	}

}
