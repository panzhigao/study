package com.pan.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * 
 * @author panzhigao-wb
 *
 */
@Service
public class MyHandler extends TextWebSocketHandler{
	
	private static final Logger logger = LoggerFactory.getLogger(MyHandler.class);
    /**
     * 在线用户列表
     */
    private static final Map<String, WebSocketSession> USERS;
    /**
     * 用户标识
     */
    private static final String USER_ID = "userId";

    static {
        USERS = new HashMap<>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("成功建立连接");
        String userId = getClientId(session);
        logger.info("userId:{}",userId);
        //userId=4;
        if (userId != null) {
            USERS.put(userId, session);
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
//        logger.info(message.getPayload());
//        TextMessage message1 = new TextMessage("server:"+message);
//        try {
//            session.sendMessage(message1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 发送信息给指定用户
     * @param clientId
     * @param message
     * @return
     */
    public boolean sendMessageToUser(String clientId, TextMessage message) {
        if (USERS.get(clientId) == null) {
        	return false;
        }
        WebSocketSession session = USERS.get(clientId);
        logger.info("sendMessage:" + session);
        if (!session.isOpen()) {
        	return false;
        }
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 广播信息到所有用户
     * @param message
     * @return
     */
    public boolean sendMessageToAllUsers(TextMessage message) {
        boolean allSendSuccess = true;
        Set<String> userIds = USERS.keySet();
        WebSocketSession session = null;
        for (String userId : userIds) {
            try {
                session = USERS.get(userId);
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
                allSendSuccess = false;
            }
        }
        return  allSendSuccess;
    }
    
    /**
     * 广播信息到所有用户，指定用户除外
     * @param message
     * @return
     */
    public boolean sendMessageToAllUsersWithException(TextMessage message,Set<String> userIdSet) {
        boolean allSendSuccess = true;
        Set<String> userIds = USERS.keySet();
        WebSocketSession session = null;
        for (String userId : userIds) {
        	if(userIdSet.contains(userId)){
        		continue;
        	}
            try {
                session = USERS.get(userId);
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
                allSendSuccess = false;
            }
        }
        return  allSendSuccess;
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        logger.error("连接出错");
        USERS.remove(getClientId(session));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    	logger.info("连接已关闭：" + status);
        USERS.remove(getClientId(session));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 获取用户标识
     * @param session
     * @return
     */
    private String getClientId(WebSocketSession session) {
        try {
            String userId =(String) session.getAttributes().get(USER_ID);
            logger.info("获取用户标识:"+userId);
            return userId;
        } catch (Exception e) {
            return null;
        }
    }
}
