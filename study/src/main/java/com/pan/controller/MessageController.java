package com.pan.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.pan.common.enums.ArticleStatusEnum;
import com.pan.common.enums.ArticleTypeEnum;
import com.pan.common.enums.MessageStatusEnum;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.Article;
import com.pan.entity.Message;
import com.pan.query.QueryArticle;
import com.pan.service.ArticleService;
import com.pan.service.MessageService;
import com.pan.util.TokenUtils;
import com.pan.util.TransFieldUtils;


@Controller
public class MessageController {
	
	private static final Logger logger=LoggerFactory.getLogger(MessageController.class);
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping(method=RequestMethod.GET,value="/user/message")
	@RequiresPermissions("/user/message")
	public ModelAndView toIndex(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("html//message/messagePage");
		return mav;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/user/message/load")
	@ResponseBody
	@RequiresPermissions(value="/user/message")
	public ResultMsg loadMessages(){
		String loginUserId = TokenUtils.getLoginUserId();
		List<Message> list=messageService.findByReceiverUserId(loginUserId);
		TransFieldUtils.transEntityCollection(list);
		return ResultMsg.ok("加载消息成功", list);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/user/message/count")
	@ResponseBody
	@RequiresPermissions(value="/user/message")
	public ResultMsg getUnreadMessageCount(){
		String loginUserId = TokenUtils.getLoginUserId();
		int count=messageService.countMessage(loginUserId, MessageStatusEnum.MESSAGE_NOT_READED.getCode());
		return ResultMsg.ok("统计成功", count);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/user/message/clean")
	@ResponseBody
	@RequiresPermissions(value="/user/message")
	public ResultMsg cleanMessage(String messageId){
		String loginUserId = TokenUtils.getLoginUserId();
		int count=messageService.cleanMessage(loginUserId, messageId);
		return ResultMsg.ok("消息置为已读成功", count);
	}
	
	/**
	 * 跳转发送消息页面
	 * @param request
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/systemMessage")
	@RequiresPermissions("/user/systemMessage")
	public ModelAndView toSendMessageIndex(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("html/sendMessage/sendMessagePage");
		return mav;
	}
	
	/**
	 * 发送系统消息
	 * @param article
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/message/send")
	@ResponseBody
	@RequiresPermissions(value="/user/systemMessage")
	public ResultMsg sendMessage(Article article){
		logger.info("发布消息开始");
		String userId=TokenUtils.getLoginUserId();
		article.setUserId(userId);
		articleService.saveSystemMessage(article);
		return ResultMsg.ok("消息发布成功");
	}
	
	/**
	 * 加载消息列表，分页查询，该接口不用用户登陆，查询的系统消息
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/message/getMessages")
	@ResponseBody
	@RequiresPermissions(value="/user/systemMessage")
	public Map<String,Object> getArticleList(Integer pageSize,Integer pageNo){
		QueryArticle queryArticle=new QueryArticle();
		queryArticle.setPageSize(pageSize);
		queryArticle.setPageNo(pageNo);
		queryArticle.setStatus(ArticleStatusEnum.PUBLIC_SUCCESS.getCode());
		queryArticle.setType(ArticleTypeEnum.TYPE_SYSTEM_MESSAGE.getCode());
		Map<String,Object> pageData=articleService.findByParams(queryArticle);
		return pageData;
	}
}
