package com.pan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pan.util.MessageUtils;

@Controller
public class TestWebSocketController {
	
	@RequestMapping("/send/{userId}/{content}")
	public void sendToUser(@PathVariable("userId")String userId,@PathVariable("content")String content){
		boolean sendToUser = MessageUtils.sendToUser(userId, content);
		if(sendToUser){
			System.out.println("发送成功");
		}
		System.out.println("发送失败");
	}
}
