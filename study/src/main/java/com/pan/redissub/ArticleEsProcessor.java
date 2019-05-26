package com.pan.redissub;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pan.common.constant.MyConstant;
import com.pan.common.enums.RedisChannelEnum;
import com.pan.service.IArticleService;
import com.pan.util.JedisUtils;
import com.pan.util.SpringContextUtils;

/**
 * 文章es更新
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
		IArticleService articleService = SpringContextUtils.getBean(IArticleService.class);
    	String id = JedisUtils.brpoplpush(MyConstant.ARTICLE_ES_REDIS_LIST, MyConstant.ARTICLE_ES_REDIS_LIST_BAK);
    	if(StringUtils.isNumeric(id)){
    		logger.info("更新文章es数据,id={}",id);
    		boolean updateArticleEs = articleService.updateArticleEs(Long.parseLong(id));
    		//更新成功，删除备份数据
    		if(updateArticleEs){
    			//删除队列里所有id，（0表示所有）
    			JedisUtils.lrem(MyConstant.ARTICLE_ES_REDIS_LIST_BAK, 0, id);
    		}
    	}
	}


}
