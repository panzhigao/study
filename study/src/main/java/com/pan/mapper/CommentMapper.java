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
	/**
	 *
	 * @param queryComment
	 * @return
	 */
	List<CommentVO> findVOByParams(QueryComment queryComment);
	/**
	 *
	 * @param commentId
	 */
	void updatePraiseCountsByPrimaryKey(Long commentId);
	/**
	 *
	 * @param userId
	 * @return
	 */
	List<Comment> findVOByUserId(Long userId);
}
