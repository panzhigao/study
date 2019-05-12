package com.pan.service.impl;


import java.util.Date;
import java.util.List;
import com.pan.common.enums.RedisChannelEnum;
import com.pan.common.exception.BusinessException;
import com.pan.query.QuerySystemConfig;
import com.pan.util.Publisher;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pan.common.enums.OperateLogTypeEnum;
import com.pan.entity.SystemConfig;
import com.pan.mapper.SystemConfigMapper;
import com.pan.service.AbstractBaseService;
import com.pan.service.OperateLogService;
import com.pan.service.SystemConfigService;
import com.pan.util.TokenUtils;
import com.pan.util.ValidationUtils;

/**
 * @author panzhigao
 */
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

	/**
	 * 根据变量名获取唯一变量
	 * @return
	 */
	@Override
	public SystemConfig selectByParamName(String paramName) {
		QuerySystemConfig querySystemConfig=new QuerySystemConfig();
		querySystemConfig.setParamName(paramName);
		List<SystemConfig> pageable = systemConfigMapper.findPageable(querySystemConfig);
		if(CollectionUtils.isNotEmpty(pageable)){
			return pageable.get(0);
		}
		return null;
	}

	/**
	 * 编辑系统配置，并记录日志,刷新缓存
	 */
	@Override
	public void updateSystemConfig(SystemConfig systemConfig) {
		ValidationUtils.validateEntity(systemConfig);
		SystemConfig systemConfigInDb = getAndCheck(systemConfig.getId());
		//当修改了变量名，校验新变量名是否已存在
		if(!StringUtils.equals(systemConfig.getParamName(),systemConfigInDb.getParamName())){
			checkUniqueByParamName(systemConfig.getParamName());
		}
		systemConfig.setUpdateTime(new Date());
		systemConfig.setUpdateUserId(TokenUtils.getLoginUserId());
		int count = updateByPrimaryKeySelective(systemConfig);
		logger.info("更新系统配置，更新条数：{}",count);
		String format = String.format("变量原信息，id=%s,变量名=%s,",systemConfig.getId(),systemConfig.getParamName());
		String changedFields = ValidationUtils.getChangedFields(systemConfigInDb, systemConfig);
		operateLogService.addOperateLog(format+"："+changedFields,OperateLogTypeEnum.SYSTEM_CONFIG_EDIT);
		Publisher.sendMessage(RedisChannelEnum.RECACHE_SYSTEM_CONFIG.getName(),systemConfig.getParamName());
	}

	/**
	 * 新增系统变量
	 * @param systemConfig
	 */
	@Override
	public void addSystemConfig(SystemConfig systemConfig) {
		String content=systemConfig.toString();
		ValidationUtils.validateEntity(systemConfig);
		checkUniqueByParamName(systemConfig.getParamName());
		systemConfig.setCreateTime(new Date());
		systemConfig.setCreateUserId(TokenUtils.getLoginUserId());
		insertSelective(systemConfig);
		operateLogService.addOperateLog(content,OperateLogTypeEnum.SYSTEM_CONFIG_ADD);
	}

	/**
	 * 删除系统变量
	 * @param configId
	 */
	@Override
	public void deleteSystemConfig(Long configId) {
		SystemConfig systemConfig = selectByPrimaryKey(configId);
		if(systemConfig==null){
			throw new BusinessException("该变量不存在");
		}
		deleteByPrimaryKey(configId);
		operateLogService.addOperateLog(systemConfig.toString(),OperateLogTypeEnum.SYSTEM_CONFIG_EDIT);
		Publisher.sendMessage(RedisChannelEnum.RECACHE_SYSTEM_CONFIG.getName(),systemConfig.getParamName());
	}

	/**
	 * 根据角色id获取角色信息，并校验
	 * @param configId
	 * @return
	 */
	private SystemConfig getAndCheck(Long configId){
		if(configId==null){
			throw new BusinessException("配置ID未传入");
		}
		SystemConfig systemConfig = selectByPrimaryKey(configId);
		if(systemConfig==null){
			throw new BusinessException("该配置不存在");
		}
		return systemConfig;
	}

	/**
	 * 校验变量是否重复
	 * @param paramName
	 */
	private void checkUniqueByParamName(String paramName){
		QuerySystemConfig querySystemConfig=new QuerySystemConfig();
		querySystemConfig.setParamName(paramName);
		int count = systemConfigMapper.countByParams(querySystemConfig);
		if(count>0){
			throw new BusinessException("该变量名已存在，请重命名");
		}
	}
}
