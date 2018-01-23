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
	
	private static final String PICTURE_KEY="picture_id";
	
	private static final String COMMENT_KEY="comment_id";
	
	/**
	 * 创建文章id
	 * @return
	 */
	public static String generateArticleId(){
		String articleId=null;
		try {
			long value=0L;
			if(!JedisUtils.existsKey(ARTICLE_KEY)){
				//默认值10000
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
	
	/**
	 * 创建文章id
	 * @return
	 */
	public static String generatePictureId(){
		String pictureId=null;
		try {
			long value=0L;
			if(!JedisUtils.existsKey(PICTURE_KEY)){
				//默认值10000
				value=JedisUtils.increaseKey(PICTURE_KEY, 10000L);
			}else{
				value=JedisUtils.increaseKey(PICTURE_KEY);
			}
			pictureId="pic"+value;
		} catch (Exception e) {
			logger.error("生成图片id错误",e);
			throw e;
		}
		return pictureId;
	}
	
	/**
	 * 创建评论id
	 * @return
	 */
	public static String generateCommentId(){
		String pictureId=null;
		try {
			long value=0L;
			if(!JedisUtils.existsKey(COMMENT_KEY)){
				//默认值10000
				value=JedisUtils.increaseKey(COMMENT_KEY, 10000L);
			}else{
				value=JedisUtils.increaseKey(COMMENT_KEY);
			}
			pictureId="c"+value;
		} catch (Exception e) {
			logger.error("生成图片id错误",e);
			throw e;
		}
		return pictureId;
	}
}
