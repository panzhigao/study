package com.pan.util;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.socket.TextMessage;
import com.pan.websocket.MyWebSocketHandler;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


/**
 * 消息工具类
 * @author panzhigao
 */
public class MessageUtils {
	
	private static MyWebSocketHandler myHandler;

	public MyWebSocketHandler getMyHandler() {
		return myHandler;
	}

	public void setMyHandler(MyWebSocketHandler myHandler) {
		MessageUtils.myHandler = myHandler;
	}
	
	/**
	 * 发送消息到指定用户
	 * @param userId
	 * @param message
	 * @return
	 */
	public static boolean sendToUser(Long userId,String message){
		boolean sendMessageToUser = myHandler.sendMessageToUser(userId, new TextMessage(message));
		return sendMessageToUser;
	}
	
	/**
	 * 发送到所以用户
	 * @param message
	 * @return 是否全部发送成功
	 */
	public static boolean sendToAllUser(String message){
		boolean sendMessageToAllUsers = myHandler.sendMessageToAllUsers(new TextMessage(message));
		return sendMessageToAllUsers;
	}
	
	/**
	 * 发送到所以用户,指定用户除外
	 * @param message
	 * @return 是否全部发送成功
	 */
	public static boolean sendMessageToAllExceptionUser(String message,Set<Long> userIdSet){
		boolean sendMessageToAllUsers = myHandler.sendMessageToAllUsersWithException(new TextMessage(message),userIdSet);
		return sendMessageToAllUsers;
	}
}
