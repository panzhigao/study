package com.pan.redissub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pan.common.enums.RedisChannelEnum;
import com.pan.util.SystemConfigUtils;

/**
 * 文章分类缓存
 * @author Administrator
 *
 */
@Service
public class SystemConfigProcessor implements SubProcessor{
	
	private static final Logger logger = LoggerFactory.getLogger(SystemConfigProcessor.class);
	
	@Override
	public boolean checkChannel(String channelName) {
		return RedisChannelEnum.RECACHE_SYSTEM_CONFIG.getName().equals(channelName);
	}

	@Override
	public void handel(String message) {
		logger.info("同步系统配置缓存,message={}",message);
		SystemConfigUtils.refreshSystemConfig(message);
	}

}
