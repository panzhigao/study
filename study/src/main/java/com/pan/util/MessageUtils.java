package com.pan.util;

import org.springframework.web.socket.TextMessage;

import com.pan.websocket.MyHandler;

public class MessageUtils {
	
	private static MyHandler myHandler;

	public MyHandler getMyHandler() {
		return myHandler;
	}

	public void setMyHandler(MyHandler myHandler) {
		MessageUtils.myHandler = myHandler;
	}
	
	//发送消息到指定用户
	public static boolean sendToUser(String userId,String message){
		boolean sendMessageToUser = myHandler.sendMessageToUser(userId, new TextMessage(message));
		return sendMessageToUser;
	}
}
