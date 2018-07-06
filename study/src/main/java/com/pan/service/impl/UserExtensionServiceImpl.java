package com.pan.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pan.entity.UserExtension;
import com.pan.mapper.UserExtensionMapper;
import com.pan.query.QueryUserExtension;
import com.pan.service.UserExtensionService;

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
	public List<UserExtension> findByParams(QueryUserExtension queryUserExtensionVO) {
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

	@Override
	public int countByParams(QueryUserExtension extensionVO) {
		return userExtensionMapper.countByParams(extensionVO);
	}

}
