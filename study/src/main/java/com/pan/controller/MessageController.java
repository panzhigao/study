package com.pan.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.pan.common.annotation.HasPermission;
import com.pan.common.constant.MyConstant;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.Article;
import com.pan.entity.Message;
import com.pan.query.QueryArticle;
import com.pan.service.ArticleService;
import com.pan.service.MessageService;
import com.pan.util.TokenUtils;


@Controller
public class MessageController {
	
	private static final Logger logger=LoggerFactory.getLogger(MessageController.class);
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping(method=RequestMethod.GET,value="/user/message")
	@HasPermission
	public ModelAndView toIndex(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("html//message/messagePage");
		return mav;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/user/message/load")
	@ResponseBody
	@HasPermission(value="/user/message")
	public ResultMsg loadMessages(){
		String loginUserId = TokenUtils.getLoingUserId();
		List<Message> list=messageService.findByReceiverUserId(loginUserId);
		return ResultMsg.ok("加载消息成功", list);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/user/message/count")
	@ResponseBody
	@HasPermission(value="/user/message")
	public ResultMsg getUnreadMessageCount(){
		String loginUserId = TokenUtils.getLoingUserId();
		int count=messageService.countMessage(loginUserId, MyConstant.MESSAGE_NOT_READED);
		return ResultMsg.ok("统计成功", count);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/user/message/clean")
	@ResponseBody
	@HasPermission(value="/user/message")
	public ResultMsg cleanMessage(String messageId){
		String loginUserId = TokenUtils.getLoingUserId();
		int count=messageService.cleanMessage(loginUserId, messageId);
		return ResultMsg.ok("消息置为已读成功", count);
	}
	
	/**
	 * 跳转发送消息页面
	 * @param request
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/systemMessage")
	@HasPermission
	public ModelAndView toSendMessageIndex(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("html/sendMessage/sendMessagePage");
		return mav;
	}
	
	/**
	 * 发送系统消息
	 * @param article
	 * @param messageId
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/message/send")
	@ResponseBody
	@HasPermission(value="/user/systemMessage")
	public ResultMsg sendMessage(Article article){
		logger.info("发布消息开始");
		String userId=TokenUtils.getLoingUserId();
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
	@HasPermission(value="/user/systemMessage")
	public Map<String,Object> getArticleList(Integer pageSize,Integer pageNo){
		QueryArticle queryArticleVO=new QueryArticle();
		queryArticleVO.setPageSize(pageSize);
		queryArticleVO.setPageNo(pageNo);
		queryArticleVO.setStatus(Article.STATUS_PUBLISHED);
		queryArticleVO.setType(Article.TYPE_SYSTEM_MESSAGE);
		Map<String,Object> pageData=articleService.findByParams(queryArticleVO);
		return pageData;
	}
}
