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

	private static final String GLOBAL_SYSTEM_ID="global_system_id";

	private static final String ARTICLE_KEY="article_id";

	private static final String PICTURE_KEY="picture_id";

	private static final String COMMENT_KEY="comment_id";

	private static final String PRAISE_KEY="praise_id";

	private static final String COLLECTION_KEY="collection_id";

	private static final String MESSAGE_KEY="message_id";

	private static final String PERMISSION_KEY="permission_id";

	private static final String ROLE_KEY="role_id";
	 /**
     * 日期起始点
     */
    private final static long EPOCH=1463108596098L;

    public static long generateId(){
		long value=0;
		try {
			if(!JedisUtils.existsKey(GLOBAL_SYSTEM_ID)){
				//默认值10000
				value=JedisUtils.increaseKey(GLOBAL_SYSTEM_ID, 10000L);
			}else{
				value=JedisUtils.increaseKey(GLOBAL_SYSTEM_ID);
			}
			value=((System.currentTimeMillis() - EPOCH) / 60000+value);
		} catch (Exception e) {
			logger.error("生成id失败",e);
		}
		return value;
	}

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
			articleId="a"+((System.currentTimeMillis() - EPOCH) / 60000+value);
		} catch (Exception e) {
			logger.error("生成文章id错误",e);
		}
		return articleId;
	}

	/**
	 * 创建图片id
	 * @return
	 */
	public static String generatePictureId(){
		String pictureId=null;
		try {
			long value;
			if(!JedisUtils.existsKey(PICTURE_KEY)){
				//默认值10000
				value=JedisUtils.increaseKey(PICTURE_KEY, 10000L);
			}else{
				value=JedisUtils.increaseKey(PICTURE_KEY);
			}
			pictureId="pic"+((System.currentTimeMillis() - EPOCH) / 60000+value);
		} catch (Exception e) {
			logger.error("生成图片id错误",e);
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
			long value;
			if(!JedisUtils.existsKey(COMMENT_KEY)){
				//默认值10000
				value=JedisUtils.increaseKey(COMMENT_KEY, 10000L);
			}else{
				value=JedisUtils.increaseKey(COMMENT_KEY);
			}
			commentId="c"+((System.currentTimeMillis() - EPOCH) / 60000+value);
		} catch (Exception e) {
			logger.error("生成评论id错误",e);
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
			long value;
			if(!JedisUtils.existsKey(PRAISE_KEY)){
				//默认值10000
				value=JedisUtils.increaseKey(PRAISE_KEY, 10000L);
			}else{
				value=JedisUtils.increaseKey(PRAISE_KEY);
			}
			praiseId="z"+((System.currentTimeMillis() - EPOCH) / 60000+value);
		} catch (Exception e) {
			logger.error("生成赞id错误",e);
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
			long value;
			if(!JedisUtils.existsKey(MESSAGE_KEY)){
				//默认值10000
				value=JedisUtils.increaseKey(MESSAGE_KEY, 10000L);
			}else{
				value=JedisUtils.increaseKey(MESSAGE_KEY);
			}
			messageId="m"+((System.currentTimeMillis() - EPOCH) / 60000+value);
		} catch (Exception e) {
			logger.error("生成消息id错误",e);
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
			long value;
			if(!JedisUtils.existsKey(PERMISSION_KEY)){
				//默认值10000
				value=JedisUtils.increaseKey(PERMISSION_KEY, 10000L);
			}else{
				value=JedisUtils.increaseKey(PERMISSION_KEY);
			}
			permissionId="p"+((System.currentTimeMillis() - EPOCH) / 60000+value);
		} catch (Exception e) {
			logger.error("生成权限id错误",e);
		}
		return permissionId;
	}

	/**
	 * 创建角色id
	 * @return
	 */
	public static String generateRoleId(){
		String roleId=null;
		try {
			long value;
			if(!JedisUtils.existsKey(ROLE_KEY)){
				//默认值10000
				value=JedisUtils.increaseKey(ROLE_KEY, 10000L);
			}else{
				value=JedisUtils.increaseKey(ROLE_KEY);
			}
			roleId="r"+((System.currentTimeMillis() - EPOCH) / 60000+value);
		} catch (Exception e) {
			logger.error("生成角色id错误",e);
		}
		return roleId;
	}
}
