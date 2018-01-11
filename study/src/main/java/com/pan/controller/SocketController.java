package com.pan.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import com.pan.common.handler.MyHandler;

@Controller
public class SocketController {
	@Autowired
	private MyHandler handler;

	@RequestMapping(value="/login/{userId}",produces = "text/plain; charset=utf-8")
	public @ResponseBody String login(HttpSession session,@PathVariable("userId") Integer userId) {
		System.out.println("登录接口,userId=" + userId);
		session.setAttribute("userId", userId);
		System.out.println(session.getAttribute("userId"));
		return "登录成功，id为"+userId;
	}

	@RequestMapping(value="/message/{userId}",produces = "application/json; charset=utf-8")
	public @ResponseBody String sendMessage(@PathVariable("userId") Integer userId) {
		boolean hasSend = handler.sendMessageToUser(userId, new TextMessage(
				"服务端发送一条消息给"+userId));
		System.out.println(hasSend);
		return "message";
	}
}
