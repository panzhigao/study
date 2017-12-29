package com.pan.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pan.service.impl.ArticleServiceImpl;

/**
 * id生成工具类
 * @author Administrator
 *
 */
public class IdUtils {
	
	private static final Logger logger=LoggerFactory.getLogger(ArticleServiceImpl.class);
	
	private static final String ARTICLE_KEY="article_id";
	
	/**
	 * 创建文章id
	 * @return
	 */
	public static String generateArticleId(){
		String articleId=null;
		try {
			long value=0L;
			if(!JedisUtils.existsKey(ARTICLE_KEY)){
				//默认值10001
				value=JedisUtils.increaseKey(ARTICLE_KEY, 10000L);
			}else{
				value=JedisUtils.increaseKey(ARTICLE_KEY);
			}
			articleId="a"+value;
		} catch (Exception e) {
			logger.error("生成文章id错误",e);
			throw e;
		}
		return articleId;
	}
}
