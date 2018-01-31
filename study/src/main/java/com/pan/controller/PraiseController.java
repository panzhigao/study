package com.pan.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.Praise;
import com.pan.service.PraiseService;
import com.pan.util.CookieUtils;

@Controller
@RequestMapping("/api")
public class PraiseController {
	
	@Autowired
	private PraiseService praiseService;
	
	/**
	 * 点赞
	 * @return
	 */
	@RequestMapping(value="/user/praise",method=RequestMethod.POST)
	@ResponseBody
	public ResultMsg praise(HttpServletRequest request,String commentId){
		String loingUserId = CookieUtils.getLoginUserId(request);
		Praise praise=new Praise();
		praise.setUserId(loingUserId);
		praise.setCommentId(commentId);
		praiseService.addPraise(praise);
		return ResultMsg.ok("点赞成功");
	}
}
