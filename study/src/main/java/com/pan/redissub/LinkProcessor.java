package com.pan.redissub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pan.common.constant.MyConstant;
import com.pan.common.enums.RedisChannelEnum;
import com.pan.service.impl.LinkServiceImpl;

/**
 * 链接缓存
 * @author Administrator
 *
 */
@Service
public class LinkProcessor implements SubProcessor{
	
	private static final Logger logger = LoggerFactory.getLogger(LinkProcessor.class);
	
	@Override
	public boolean checkChannel(String channelName) {
		return RedisChannelEnum.RECACHE_LINK.getName().equals(channelName);
	}
	
	@Override
	public void handel(String message) {
		logger.info("同步链接缓存,message={}",message);
		LinkServiceImpl.onlineLinkCache.refresh(MyConstant.DEFAULT_KEY);
	}
}
