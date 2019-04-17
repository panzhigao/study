package com.pan.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.pan.entity.SystemConfig;
import com.pan.service.SystemConfigService;

/**
 * 系统配置
 * @author Administrator
 *
 */
public class SystemConfigUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(SystemConfigUtils.class);
	
	public static LoadingCache<String, SystemConfig> systemConfigCache;
	
	@Autowired
	private SystemConfigService systemConfigService;
		
	/**
	 * 初始化配置
	 */
	public void init() {
		logger.info("初始化系统配置。。。");
		systemConfigCache = CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.HOURS).build(new CacheLoader<String, SystemConfig>() {
			@Override
			public SystemConfig load(String key) throws Exception {
				logger.info("systemConfigCache加载系统配置，key={}",key);
				SystemConfig selectByPrimaryKey = systemConfigService.selectByParamName(key);
				if(selectByPrimaryKey==null){
					selectByPrimaryKey=new SystemConfig();
					selectByPrimaryKey.setParamName(key);
					selectByPrimaryKey.setParamValue(StringUtils.EMPTY);
				}
				TransFieldUtils.transEntity(selectByPrimaryKey);
				return selectByPrimaryKey;
			}
		});
	}

	/**
	 * 获取系统配置
	 * @return
	 */
	public static SystemConfig getSystemConfig(String key){
		try {
			return systemConfigCache.get(key);
		} catch (ExecutionException e) {
			logger.error("获取系统配置失败",e);
		}
		return new SystemConfig();
	}

	/**
	 * 获取系统配置
	 * @return
	 */
	public static String getParamValue(String key){
		SystemConfig systemConfig = getSystemConfig(key);
		return systemConfig.getParamValue();
	}
	
	/**
	 * 重载系统配置
	 */
	public static void refreshSystemConfig(String key){
		systemConfigCache.refresh(key);
	}
}
