package com.pan.service.impl;

import java.util.Date;
import java.util.List;
import com.pan.common.enums.MessageStatusEnum;
import com.pan.common.enums.MessageTypeEnum;
import com.pan.common.enums.ScoreTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pan.common.exception.BusinessException;
import com.pan.entity.Article;
import com.pan.entity.Comment;
import com.pan.entity.Message;
import com.pan.entity.ScoreHistory;
import com.pan.entity.UserExtension;
import com.pan.entity.User;
import com.pan.mapper.CommentMapper;
import com.pan.query.QueryComment;
import com.pan.service.AbstractBaseService;
import com.pan.service.ArticleService;
import com.pan.service.CommentService;
import com.pan.service.MessageService;
import com.pan.service.ScoreHistoryService;
import com.pan.service.UserExtensionService;
import com.pan.service.UserService;
import com.pan.util.IdUtils;
import com.pan.util.JedisUtils;
import com.pan.util.JsonUtils;
import com.pan.util.MessageUtils;
import com.pan.util.TokenUtils;
import com.pan.util.ValidationUtils;
import com.pan.vo.CommentVO;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class CommentServiceImpl extends AbstractBaseService<Comment,CommentMapper> implements CommentService{
	
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
	private ScoreHistoryService scoreHistoryService;
	
	@Autowired
	private UserExtensionService userExtensionService;
	
	@Override
	protected CommentMapper getBaseMapper() {
		return commentMapper;
	}
	
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
		commentMapper.insertSelective(comment);
		JedisUtils.increaseKey("comment_count:"+comment.getArticleId());
		
		//发表评论加2分,
		ScoreHistory addScoreHistory = scoreHistoryService.addScoreHistory(TokenUtils.getLoginUserId(), ScoreTypeEnum.COMMENT);
		//修改当前登录人评论数
		//用户拓展表增加积分
		UserExtension userExtension=new UserExtension();
		userExtension.setUserId(addScoreHistory.getUserId());
		userExtension.setUpdateTime(new Date());
		userExtension.setScore(addScoreHistory.getScore());
		userExtension.setCommentCounts(1);
		userExtensionService.increaseCounts(userExtension);
		
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
		message.setMessageType(MessageTypeEnum.COMMENT.getCode());
		message.setStatus(MessageStatusEnum.MESSAGE_NOT_READED.getCode());
		message.setSenderUserId(comment.getUserId());
		message.setSenderName(userInDb.getNickname());
		message.setContentId(comment.getArticleId());
		message.setContentName(articleInDb.getTitle());
		message.setReceiverUserId(articleInDb.getUserId());
		message.setCreateTime(new Date());
		message.setCommentContent(comment.getCommentContent());
		messageService.addMessage(message);
		MessageUtils.sendToUser(articleInDb.getUserId(), JsonUtils.toJson(message));
		return comment;
	}
	
	/**
	 * 加载评论
	 * @param queryComment
	 * @return
	 */
	@Override
	public List<CommentVO> loadComments(QueryComment queryComment){
		return commentMapper.findVOByParams(queryComment);
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
		QueryComment queryComment=new QueryComment();
		queryComment.setArticleId(articleId);
		int total=commentMapper.countByParams(queryComment);
		return total;
	}

	@Override
	public List<Comment> loadUserComments(String userId) {
		return commentMapper.findVOByUserId(userId);
	}

}
