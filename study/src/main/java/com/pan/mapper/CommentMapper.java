package com.pan.mapper;

import java.util.List;
import java.util.Map;

import com.pan.entity.Comment;

/**
 * 
 * @author Administrator
 *
 */
public interface CommentMapper {
	
	public void addComment(Comment comment);
	
	public List<Comment> findByArticleId(Comment comment);
	
	public Comment findByCommentId(String commentId);
	
	public void deleteByCommentId(String commentId);
	
	public void updatePraiseCounts(String commentId);
	
	public int countComment(String articleId);
	
	public List<Comment> findByParam(Map<String,Object> params);
}
