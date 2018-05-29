package com.pan.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pan.common.constant.MyConstant;
import com.pan.common.exception.BusinessException;
import com.pan.entity.Article;
import com.pan.entity.Comment;
import com.pan.entity.Message;
import com.pan.entity.User;
import com.pan.entity.UserExtension;
import com.pan.mapper.CommentMapper;
import com.pan.service.ArticleService;
import com.pan.service.CommentService;
import com.pan.service.MessageService;
import com.pan.service.UserExtensionService;
import com.pan.service.UserService;
import com.pan.util.IdUtils;
import com.pan.util.JedisUtils;
import com.pan.util.JsonUtils;
import com.pan.util.MessageUtils;
import com.pan.util.TokenUtils;
import com.pan.util.ValidationUtils;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class CommentServiceImpl implements CommentService{
	
	private static final Logger logger=LoggerFactory.getLogger(CommentServiceImpl.class);
	
	@Autowired
	private CommentMapper commentMapper;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserExtensionService userExtensionService;
	/**
	 * 添加评论
	 * 1.新增评论记录
	 * 2.发送消息
	 * 3.增加用户评论数
	 */
	@Override
	public Comment addComment(Comment comment) {
		ValidationUtils.validateEntity(comment);
		String articleId=comment.getArticleId();
		Article articleInDb = articleService.getByArticleId(articleId);
		if(articleInDb==null){
			throw new BusinessException("文章不存在");
		}
		comment.setCommentId(IdUtils.generateCommentId());
		comment.setCreateTime(new Date());
		//新增评论
		commentMapper.addComment(comment);
		JedisUtils.increaseKey("comment_count:"+comment.getArticleId());
		
		//发送消息
		//当文章用户评论自己的文章时，不发送消息
		if(StringUtils.equals(articleInDb.getUserId(),comment.getUserId())){
			return comment;
		}
		User userInDb = userService.findByUserId(comment.getUserId());
		Message message=new Message();
		if(userInDb!=null){
			message.setSenderName(userInDb.getNickname());
		}
		message.setMessageId(IdUtils.generateMessageId());
		message.setMessageType(MyConstant.MESSAGE_TYPE_COMMENT);
		message.setStatus(MyConstant.MESSAGE_NOT_READED);
		message.setSenderUserId(comment.getUserId());
		message.setSenderName(userInDb.getNickname());
		message.setContentId(comment.getArticleId());
		message.setContentName(articleInDb.getTitle());
		message.setReceiverUserId(articleInDb.getUserId());
		message.setCreateTime(new Date());
		message.setCommentContent(comment.getCommentContent());
		messageService.addMessage(message);
		//String messageStr=message.getSenderName()+"评论了您的文章："+articleInDb.getTitle();
		MessageUtils.sendToUser(articleInDb.getUserId(), JsonUtils.toJson(message));
		//修改当前人的评论数
		String loingUserId = TokenUtils.getLoingUserId();
		UserExtension userExtensionInDb=new UserExtension();
		userExtensionInDb.setUserId(loingUserId);
		userExtensionInDb.setCommentCounts(1);
		userExtensionInDb.setScore(2);
		userExtensionService.updateById(userExtensionInDb);
		return comment;
	}
	
	/**
	 * 根据文章id加载评论
	 * @param articleId
	 * @return
	 */
	@Override
	public List<Comment> loadComments(String userId,String articleId){
		Comment comment=new Comment();
		comment.setUserId(userId);
		comment.setArticleId(articleId);
		return commentMapper.findByArticleId(comment);
	}
	
	@Override
	public void deleteByCommentId(String commentId,String userId) {
		logger.info("评论id:{}",commentId);
		Comment comment = commentMapper.findByCommentId(commentId);
		if(comment==null){
			throw new BusinessException("评论不存在");
		}
		if(!StringUtils.equals(comment.getUserId(), userId)){
			throw new BusinessException("评论不属于当前登录用户");
		}
		commentMapper.deleteByCommentId(commentId);
		JedisUtils.decreaseKey("comment_count:"+comment.getArticleId());
	}

	@Override
	public int getCommnetCount(String articleId) {
		int total=commentMapper.countComment(articleId);
		return total;
	}
}
