package com.pan.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.pan.common.constant.MyConstant;
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
				SystemConfig selectByPrimaryKey = systemConfigService.selectByPrimaryKey(1L);
				if(selectByPrimaryKey==null){
					selectByPrimaryKey=new SystemConfig();
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
	public static SystemConfig getSystemConfig(){
		try {
			return systemConfigCache.get(MyConstant.SYSTEM_CONFIG);
		} catch (ExecutionException e) {
			logger.error("获取系统配置失败",e);
		}
		return new SystemConfig();
	}
	
	/**
	 * 重载系统配置
	 */
	public static void refreshSystemConfig(){
		systemConfigCache.refresh(MyConstant.SYSTEM_CONFIG);
	}
}
