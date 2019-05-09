package com.pan.redissub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pan.common.enums.RedisChannelEnum;
import com.pan.service.impl.ArticleCategoryServiceImpl;

/**
 * 文章分类缓存
 * @author Administrator
 *
 */
@Service
public class ArticleCategoryProcessor implements SubProcessor{
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleCategoryProcessor.class);
	
	@Override
	public boolean checkChannel(String channelName) {
		return RedisChannelEnum.RECACHE_ARTICLE_CATEGORY.getName().equals(channelName);
	}
	
	@Override
	public void handel(String message) {
		logger.info("同步文章分类缓存,message={}",message);
        ArticleCategoryServiceImpl.refreshCache(Long.valueOf(message));
	}


}
