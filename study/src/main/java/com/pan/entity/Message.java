package com.pan.entity;

import com.pan.common.annotation.UnescapeHtml;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 消息实体类
 * 
 * @author Administrator
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Message extends BaseEntity {

	private static final long serialVersionUID = -8041567985696094383L;
	/**
	 * 消息id
	 */
	private String messageId;
	/**
	 * 消息状态 0-未读 1-已读
	 */
	private Integer status;
	/**
	 * 消息接收者id
	 */
	private String receiverUserId;
	/**
	 * 发送消息用户名
	 */
	private String senderName;
	/**
	 * 发送者用户id
	 */
	private String senderUserId;
	/**
	 * 消息类别 1-评论 2-点赞 3-系统消息
	 */
	private Integer messageType;
	/**
	 * 文章id
	 */
	private String articleId;
	/**
	 * 评论id
	 */
	private String commentId;
	/**
	 * 内容名称
	 */
	private String contentName;
	/**
	 * 评论内容
	 */
	@UnescapeHtml
	private String commentContent;
}
