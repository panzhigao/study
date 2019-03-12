package com.pan.mapper;

import java.util.List;
import com.pan.entity.Comment;
import com.pan.query.QueryComment;
import com.pan.vo.CommentVO;

/**
 * 
 * @author Administrator
 *
 */
//TODO 优化
public interface CommentMapper extends BaseMapper<Comment>{
	
	List<CommentVO> findVOByParams(QueryComment queryComment);
	
	Comment findByCommentId(String commentId);
	
	void deleteByCommentId(String commentId);
	
	void updatePraiseCounts(String commentId);
	
	List<Comment> findVOByUserId(String userId);
}
