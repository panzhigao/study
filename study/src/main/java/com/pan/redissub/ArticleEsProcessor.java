package com.pan.redissub;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pan.common.constant.MyConstant;
import com.pan.common.enums.RedisChannelEnum;
import com.pan.service.ArticleService;
import com.pan.util.JedisUtils;
import com.pan.util.SpringContextUtils;

/**
 * 文章分类缓存
 * @author Administrator
 *
 */
@Service
public class ArticleEsProcessor implements SubProcessor{
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleEsProcessor.class);
	
	@Override
	public boolean checkChannel(String channelName) {
		return RedisChannelEnum.ARTICLE_ES_CREATE_OR_UPDATE.getName().equals(channelName);
	}
	
	@Override
	public void handel(String message) {
		logger.info("文章es更新,message={}",message);
		ArticleService articleService = SpringContextUtils.getBean(ArticleService.class);
    	String id = JedisUtils.brpoplpush(MyConstant.ARTICLE_ES_REDIS_LIST, MyConstant.ARTICLE_ES_REDIS_LIST_BAK);
    	if(StringUtils.isNumeric(id)){
    		logger.info("更新文章es数据,id={}",id);
    		articleService.updateArticleEs(Long.parseLong(id));
    	}
	}


}
