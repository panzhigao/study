package com.pan.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pan.common.exception.BusinessException;
import com.pan.entity.Comment;
import com.pan.mapper.CommentMapper;
import com.pan.service.CommentService;
import com.pan.util.IdUtils;
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
	
	/**
	 * 添加评论
	 */
	@Override
	public Comment addComment(Comment comment) {
		ValidationUtils.validateEntity(comment);
		comment.setCommentId(IdUtils.generateCommentId());
		comment.setCreateTime(new Date());
		commentMapper.addComment(comment);	
		return comment;
	}
	
	/**
	 * 根据文章id加载评论
	 * @param articleId
	 * @return
	 */
	@Override
	public List<Comment> loadComments(String articleId){
		return commentMapper.findByArticleId(articleId);
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
	}
}
