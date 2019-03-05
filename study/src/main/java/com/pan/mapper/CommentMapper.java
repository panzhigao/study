package com.pan.mapper;

import java.util.List;
import com.pan.entity.Comment;
import com.pan.vo.CommentVO;

/**
 * 
 * @author Administrator
 *
 */
//TODO 优化
public interface CommentMapper extends BaseMapper<Comment>{
	
	public List<CommentVO> findVOByArticleId(Comment comment);
	
	public Comment findByCommentId(String commentId);
	
	public void deleteByCommentId(String commentId);
	
	public void updatePraiseCounts(String commentId);
	
	public List<Comment> findByUserId(String userId);
}
