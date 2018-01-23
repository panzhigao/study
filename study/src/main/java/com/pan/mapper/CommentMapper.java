package com.pan.mapper;

import java.util.List;

import com.pan.entity.Comment;

/**
 * 
 * @author Administrator
 *
 */
public interface CommentMapper {
	
	public void addComment(Comment comment);
	
	public List<Comment> findByArticleId(String articleId);
	
	public Comment findByCommentId(String commentId);
	
	public void deleteByCommentId(String commentId);
}
