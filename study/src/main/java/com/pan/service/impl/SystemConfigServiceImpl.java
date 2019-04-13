package com.pan.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.pan.common.constant.RedisChannelConstant;
import com.pan.common.enums.CacheSyncEnum;
import com.pan.util.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pan.common.annotation.LogMeta;
import com.pan.common.enums.OperateLogTypeEnum;
import com.pan.entity.SystemConfig;
import com.pan.mapper.SystemConfigMapper;
import com.pan.service.AbstractBaseService;
import com.pan.service.OperateLogService;
import com.pan.service.SystemConfigService;
import com.pan.util.TokenUtils;
import com.pan.util.ValidationUtils;
import com.pan.vo.SystemConfigParam;

@Service
public class SystemConfigServiceImpl extends AbstractBaseService<SystemConfig,SystemConfigMapper> implements SystemConfigService{
	
	private static final Logger logger = LoggerFactory.getLogger(SystemConfigServiceImpl.class);
	
	@Autowired
	private SystemConfigMapper systemConfigMapper;
	
	@Autowired
	private OperateLogService operateLogService;
	
	@Override
	protected SystemConfigMapper getBaseMapper() {
		return systemConfigMapper;
	}

	@Override
	public List<SystemConfigParam> findSystemConfigParamList() {
		List<SystemConfigParam> resultList=new ArrayList<>();
		SystemConfig systemConfig = systemConfigMapper.selectByPrimaryKey(1L);
		if(systemConfig!=null){
			//获取系统配置里所有参数
			Field[] declaredFields = systemConfig.getClass().getDeclaredFields();
			SystemConfigParam configParam;
			for(Field f:declaredFields){
				LogMeta annotation = f.getAnnotation(LogMeta.class);
				if(annotation!=null){
					f.setAccessible(true);
					try {
						Object object = f.get(systemConfig);
						String value=object==null?"":String.valueOf(object);
						configParam=new SystemConfigParam();
						configParam.setParamValue(value);
						configParam.setParamName(f.getName());
						configParam.setParamDesc(annotation.fieldDesc());
						resultList.add(configParam);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return resultList;
	}
	
	/**
	 * 编辑系统配置，并记录日志,刷新缓存
	 */
	@Override
	public void updateSystemConfig(SystemConfig systemConfig) {
		SystemConfig systemConfigInDb = systemConfigMapper.selectByPrimaryKey(1L);
		systemConfig.setId(1L);
		systemConfig.setUpdateTime(new Date());
		systemConfig.setUpdateUserId(TokenUtils.getLoginUserId());
		int count = systemConfigMapper.updateByPrimaryKeySelective(systemConfig);
		logger.info("更新系统配置，更新条数：{}",count);
		String changedFields = ValidationUtils.getChangedFields(systemConfigInDb, systemConfig);
		operateLogService.addOperateLog(changedFields,OperateLogTypeEnum.SYSTEM_CONFIG_EDIT);
		Publisher.sendMessage(RedisChannelConstant.CHANNEL_CACHE_SYNC, CacheSyncEnum.SYSTEM_CONFIG.getName()+":"+1);
	}

}
