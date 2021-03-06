package com.pan.service.impl;

import java.util.Date;
import java.util.List;
import com.pan.common.enums.MessageTypeEnum;
import com.pan.common.enums.ScoreTypeEnum;
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
import com.pan.service.IArticleService;
import com.pan.service.ICommentService;
import com.pan.service.IMessageService;
import com.pan.service.IScoreHistoryService;
import com.pan.service.IUserExtensionService;
import com.pan.service.IUserService;
import com.pan.util.IdUtils;
import com.pan.util.JedisUtils;
import com.pan.util.TokenUtils;
import com.pan.util.ValidationUtils;
import com.pan.vo.CommentVO;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class CommentServiceImpl extends AbstractBaseService<Comment,CommentMapper> implements ICommentService{
	
	private static final Logger logger=LoggerFactory.getLogger(CommentServiceImpl.class);
	
	@Autowired
	private CommentMapper commentMapper;
	
	@Autowired
	private IMessageService messageService;
	
	@Autowired
	private IArticleService articleService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IScoreHistoryService scoreHistoryService;
	
	@Autowired
	private IUserExtensionService userExtensionService;
	

	/**
	 * 添加评论
	 * 1.新增评论记录
	 * 2.发送消息
	 * 3.增加用户评论数
	 */
	@Override
	public Comment addComment(Comment comment) {
		ValidationUtils.validateEntity(comment);
		Long articleId=comment.getArticleId();
		Article articleInDb = articleService.selectByPrimaryKey(articleId);
		if(articleInDb==null){
			throw new BusinessException("文章不存在");
		}
		comment.setId(IdUtils.generateId());
		comment.setCreateTime(new Date());
		//新增评论
		commentMapper.insertSelective(comment);
		JedisUtils.increaseKey("comment_count:"+comment.getArticleId());
		
		//发表评论加2分,
		ScoreHistory addScoreHistory = scoreHistoryService.addScoreHistory(TokenUtils.getLoginUserId(), ScoreTypeEnum.COMMENT);
		//修改当前登录人评论数
		//用户拓展表增加积分
		UserExtension userExtension=new UserExtension();
		userExtension.setId(addScoreHistory.getUserId());
		userExtension.setUpdateTime(new Date());
		userExtension.setTotalScore(addScoreHistory.getScore());
		userExtension.setCommentCounts(1);
		userExtensionService.increaseCounts(userExtension);
		
		//发送消息
		//当文章用户评论自己的文章时，不发送消息
		if(articleInDb.getUserId().equals(comment.getUserId())){
			return comment;
		}
		User userInDb = userService.selectByPrimaryKey(articleInDb.getUserId());
		Message message=new Message();
		message.setSenderUserId(comment.getUserId());
		message.setSenderName(userInDb.getNickname());
		message.setArticleId(comment.getArticleId());
		message.setContentName(articleInDb.getTitle());
		message.setReceiverUserId(articleInDb.getUserId());
		message.setCreateTime(new Date());
		message.setCommentContent(comment.getCommentContent());
		messageService.sendMessageToUser(message,MessageTypeEnum.COMMENT);
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
	public void deleteByCommentId(Long commentId,Long userId) {
		logger.info("评论id:{}",commentId);
		Comment comment = commentMapper.selectByPrimaryKey(commentId);
		if(comment==null){
			throw new BusinessException("评论不存在");
		}
		if(!comment.getUserId().equals(userId)){
			throw new BusinessException("评论不属于当前登录用户");
		}
		commentMapper.deleteByPrimaryKey(commentId);
		JedisUtils.decreaseKey("comment_count:"+comment.getArticleId());
	}

	@Override
	public int getCommnetCount(Long articleId) {
		QueryComment queryComment=new QueryComment();
		queryComment.setArticleId(articleId);
		int total=commentMapper.countByParams(queryComment);
		return total;
	}

	@Override
	public List<Comment> loadUserComments(Long userId) {
		return commentMapper.findVOByUserId(userId);
	}

}
