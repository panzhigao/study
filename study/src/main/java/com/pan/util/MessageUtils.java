package com.pan.util;

import java.util.Set;

import org.springframework.web.socket.TextMessage;

import com.pan.websocket.MyHandler;

/**
 * 
 * @author panzhigao-wb
 *
 */
public class MessageUtils {
	
	private static MyHandler myHandler;

	public MyHandler getMyHandler() {
		return myHandler;
	}

	public void setMyHandler(MyHandler myHandler) {
		MessageUtils.myHandler = myHandler;
	}
	
	/**
	 * 发送消息到指定用户
	 * @param userId
	 * @param message
	 * @return
	 */
	public static boolean sendToUser(String userId,String message){
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
	public static boolean sendMessageToAllUsersWithException(String message,Set<String> userIdSet){
		boolean sendMessageToAllUsers = myHandler.sendMessageToAllUsersWithException(new TextMessage(message),userIdSet);
		return sendMessageToAllUsers;
	}
}
