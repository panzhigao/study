package com.pan.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.pan.common.constant.MyConstant;

/**
 * 
 * @author panzhigao-wb
 *
 */
@Service
public class MyWebSocketHandler extends TextWebSocketHandler{
	
	private static final Logger logger = LoggerFactory.getLogger(MyWebSocketHandler.class);
    /**
     * 在线用户列表
     */
    private static final Map<Long, WebSocketSession> USERS;

    static {
        USERS = new ConcurrentHashMap<>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long userId = getClientId(session);
        logger.info(">>>>>>>>>>>>websocket成功建立连接,userId={}",userId);
        if (userId != null) {
            USERS.put(userId, session);
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {

    }

    /**
     * 发送信息给指定用户
     * @param clientId
     * @param message
     * @return
     */
    public boolean sendMessageToUser(Long clientId, TextMessage message) {
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
            logger.error(">>>>>>>>>>>>websocket发送消息失败,userId={}",clientId,e);
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
        Set<Long> userIds = USERS.keySet();
        WebSocketSession session = null;
        for (Long userId : userIds) {
            try {
                session = USERS.get(userId);
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (IOException e) {
                logger.error(">>>>>>>>>>>>websocket发送消息失败,userId={}",userId);
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
    public boolean sendMessageToAllUsersWithException(TextMessage message,Set<Long> userIdSet) {
        boolean allSendSuccess = true;
        Set<Long> userIds = USERS.keySet();
        WebSocketSession session;
        for (Long userId : userIds) {
        	if(userIdSet.contains(userId)){
        		continue;
        	}
            try {
                session = USERS.get(userId);
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (IOException e) {
                logger.error(">>>>>>>>>>>>websocket发送消息失败,userId={}",userId);
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
        logger.error(">>>>>>>>>>>>websocket连接出错,移除该连接",exception.getMessage());
        Long clientId = getClientId(session);
        if(clientId!=null){        	
        	USERS.remove(clientId);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long clientId = getClientId(session);
    	if(clientId!=null){
    		logger.info(">>>>>>>>>>>>关闭websocket连接：userId={}",clientId);
            USERS.remove(clientId);
    	}
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
    private Long getClientId(WebSocketSession session) {
        try {
            Long userId =(Long) session.getAttributes().get(MyConstant.USER_ID);
            logger.info(">>>>>>>>>>>>websocket获取用户标识:{}",userId);
            return userId;
        } catch (Exception e) {
            return null;
        }
    }
}
