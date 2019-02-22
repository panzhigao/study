package com.pan.entity;

import com.pan.common.annotation.UnescapeHtml;
import com.pan.util.JsonUtils;

/**
 * 消息实体类
 * @author Administrator
 *
 */
public class Message extends BaseEntity{

	private static final long serialVersionUID = -8041567985696094383L;
	/**
	 * 消息id
	 */
	private String messageId;
	/**
	 * 消息状态 0-未读 1-已读
	 */
	private String status;
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
	private String messageType;
	/**
	 * 内容id
	 */
	private String contentId;
	/**
	 * 内容名称
	 */
	private String contentName;
	/**
	 * 评论内容
	 */
	@UnescapeHtml
	private String commentContent;
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getReceiverUserId() {
		return receiverUserId;
	}
	public void setReceiverUserId(String receiverUserId) {
		this.receiverUserId = receiverUserId;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getSenderUserId() {
		return senderUserId;
	}
	public void setSenderUserId(String senderUserId) {
		this.senderUserId = senderUserId;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	public String getContentName() {
		return contentName;
	}
	public void setContentName(String contentName) {
		this.contentName = contentName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	@Override
	public String toString() {
		return JsonUtils.toJson(this);
	}
	
}
