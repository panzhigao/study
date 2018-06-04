package com.pan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.pan.common.vo.ResultMsg;
import com.pan.service.ScoreHistoryService;
import com.pan.util.TokenUtils;

@Controller
@RequestMapping("/user")
public class ScoreHistoryController {
	
	@Autowired
	private ScoreHistoryService historyService;
	
	/**
	 * 签到
	 * @return
	 */
	@RequestMapping("/checkIn")
	@ResponseBody
	public ResultMsg checkIn(){
		String loingUserId = TokenUtils.getLoingUserId();
		historyService.checkIn(loingUserId);
		return ResultMsg.ok("签到成功");
	}
}
