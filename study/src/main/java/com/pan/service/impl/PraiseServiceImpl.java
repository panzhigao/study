package com.pan.service.impl;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pan.common.enums.MessageTypeEnum;
import com.pan.common.exception.BusinessException;
import com.pan.entity.Comment;
import com.pan.entity.Message;
import com.pan.entity.Praise;
import com.pan.entity.User;
import com.pan.mapper.CommentMapper;
import com.pan.mapper.PraiseMapper;
import com.pan.query.QueryPraise;
import com.pan.service.AbstractBaseService;
import com.pan.service.MessageService;
import com.pan.service.PraiseService;
import com.pan.util.IdUtils;
import com.pan.util.TokenUtils;
import com.pan.util.ValidationUtils;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class PraiseServiceImpl extends AbstractBaseService<Praise, PraiseMapper> implements PraiseService{
	
	private static final Logger logger=LoggerFactory.getLogger(PraiseServiceImpl.class);
	
	@Autowired
	private CommentMapper commentMapper;
	
	@Autowired
	private PraiseMapper praiseMapper;
	
	@Autowired
	private MessageService messageService;
	
	@Override
	protected PraiseMapper getBaseMapper() {
		return praiseMapper;
	}
	
	/**
	 * 给评论点赞
	 * 假如用户点赞的不是自己的评论，发送消息通知
	 */
	@Override
	public void addCommentPraise(Praise praise) {
		ValidationUtils.validateEntity(praise);
		//查询点赞的评论是否存在
		Comment commentInDb=commentMapper.findByCommentId(praise.getCommentId());
		if(commentInDb==null){
			logger.error("评论信息不存在:{}",praise.getCommentId());
			throw new BusinessException("评论信息不存在");
		}
		QueryPraise queryPraise=new QueryPraise();
		queryPraise.setUserId(praise.getUserId());
		queryPraise.setCommentId(praise.getCommentId());
		int countByParams = praiseMapper.countByParams(queryPraise);
		if(countByParams>0){
			throw new BusinessException("已经点过赞了");
		}
		praise.setId(IdUtils.generateId());
		praise.setArticleId(commentInDb.getArticleId());
		praise.setCreateTime(new Date());
		praiseMapper.insertSelective(praise);
		commentMapper.updatePraiseCounts(praise.getCommentId());
		User loginUser = TokenUtils.getLoginUser();
		//点赞人不是当前评论所有者
		if(!loginUser.getId().equals(commentInDb.getUserId())){
			Message message=new Message();
			message.setArticleId(praise.getArticleId());
			message.setCommentId(praise.getCommentId());
			message.setCommentContent(commentInDb.getCommentContent());
			message.setReceiverUserId(commentInDb.getUserId());
			//点赞消息
			messageService.sendMessageToUser(message,MessageTypeEnum.COMMENT_PRAISE);
		}		
	}
}
