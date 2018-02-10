package com.pan.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * id生成工具类
 * @author Administrator
 *
 */
public class IdUtils {
	
	private static final Logger logger=LoggerFactory.getLogger(IdUtils.class);
	
	private static final String ARTICLE_KEY="article_id";
	
	private static final String PICTURE_KEY="picture_id";
	
	private static final String COMMENT_KEY="comment_id";
	
	private static final String PRAISE_KEY="praise_id";
	
	private static final String COLLECTION_KEY="collection_id";
	
	private static final String MESSAGE_KEY="message_id";
	
	private static final String PERMISSION_KEY="permission_id";
	
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
		String commentId=null;
		try {
			long value=0L;
			if(!JedisUtils.existsKey(COMMENT_KEY)){
				//默认值10000
				value=JedisUtils.increaseKey(COMMENT_KEY, 10000L);
			}else{
				value=JedisUtils.increaseKey(COMMENT_KEY);
			}
			commentId="c"+value;
		} catch (Exception e) {
			logger.error("生成评论id错误",e);
			throw e;
		}
		return commentId;
	}
	
	/**
	 * 创建赞id
	 * @return
	 */
	public static String generatePraiseId(){
		String praiseId=null;
		try {
			long value=0L;
			if(!JedisUtils.existsKey(PRAISE_KEY)){
				//默认值10000
				value=JedisUtils.increaseKey(PRAISE_KEY, 10000L);
			}else{
				value=JedisUtils.increaseKey(PRAISE_KEY);
			}
			praiseId="z"+value;
		} catch (Exception e) {
			logger.error("生成赞id错误",e);
			throw e;
		}
		return praiseId;
	}
	
	/**
	 * 创建赞id
	 * @return
	 */
	public static String generateCollectionId(){
		String praiseId=null;
		try {
			long value=0L;
			if(!JedisUtils.existsKey(COLLECTION_KEY)){
				//默认值10000
				value=JedisUtils.increaseKey(COLLECTION_KEY, 10000L);
			}else{
				value=JedisUtils.increaseKey(COLLECTION_KEY);
			}
			praiseId="collect"+value;
		} catch (Exception e) {
			logger.error("生成收藏id错误",e);
			throw e;
		}
		return praiseId;
	}
	
	/**
	 * 创建消息id
	 * @return
	 */
	public static String generateMessageId(){
		String messageId=null;
		try {
			long value=0L;
			if(!JedisUtils.existsKey(MESSAGE_KEY)){
				//默认值10000
				value=JedisUtils.increaseKey(MESSAGE_KEY, 10000L);
			}else{
				value=JedisUtils.increaseKey(MESSAGE_KEY);
			}
			messageId="m"+value;
		} catch (Exception e) {
			logger.error("生成消息id错误",e);
			throw e;
		}
		return messageId;
	}
	
	/**
	 * 创建权限id
	 * @return
	 */
	public static String generatePermissionId(){
		String permissionId=null;
		try {
			long value=0L;
			if(!JedisUtils.existsKey(PERMISSION_KEY)){
				//默认值10000
				value=JedisUtils.increaseKey(PERMISSION_KEY, 10000L);
			}else{
				value=JedisUtils.increaseKey(PERMISSION_KEY);
			}
			permissionId="p"+value;
		} catch (Exception e) {
			logger.error("生成权限id错误",e);
			throw e;
		}
		return permissionId;
	}
}
