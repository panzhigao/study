package com.pan.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.ScoreHistory;
import com.pan.service.UserService;
import com.pan.util.TokenUtils;

/**
 * 用户签到
 * @author Administrator
 *
 */
@Controller
public class CheckInController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 用户签到
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/api/user/checkIn")
	@ResponseBody
	public ResultMsg checkIn(){
		String loingUserId = TokenUtils.getLoginUserId();
		ScoreHistory scoreHistory = userService.checkIn(loingUserId);
		return ResultMsg.ok("签到成功",scoreHistory);
	}
}
