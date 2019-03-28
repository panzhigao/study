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
public interface CommentMapper extends BaseMapper<Comment>{
	
	List<CommentVO> findVOByParams(QueryComment queryComment);

	void updatePraiseCounts(Long commentId);
	
	List<Comment> findVOByUserId(Long userId);
}
