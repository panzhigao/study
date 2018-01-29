package com.pan.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pan.common.vo.ResultMsg;
import com.pan.entity.User;
import com.pan.service.UserService;

@Controller
public class ForgetController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/forget")
	public String index(){
		return "html/user/forget";
	}
	
	/**
	 * 发送验证码,找回密码
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/sendForgetValidationCode")
	@ResponseBody
	public ResultMsg sendValidationCode(HttpServletRequest request,String telephone){
		User user=new User();
		user.setTelephone(telephone);
		String sendValidationCode = userService.sendValidationCode(user,"findPassword");
		return ResultMsg.ok("发送验证码成功",sendValidationCode);
	}
}
