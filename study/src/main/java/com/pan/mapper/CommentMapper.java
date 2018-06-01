package com.pan.mapper;

import java.util.List;
import java.util.Map;

import com.pan.entity.Comment;
import com.pan.vo.CommentVO;

/**
 * 
 * @author Administrator
 *
 */
public interface CommentMapper {
	
	public void addComment(Comment comment);
	
	public List<CommentVO> findVOByArticleId(Comment comment);
	
	public Comment findByCommentId(String commentId);
	
	public void deleteByCommentId(String commentId);
	
	public void updatePraiseCounts(String commentId);
	
	public int countComment(String articleId);
	
	public List<Comment> findByParam(Map<String,Object> params);
	
	public List<Comment> findByUserId(String userId);
}
