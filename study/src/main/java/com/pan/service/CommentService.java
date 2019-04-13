package com.pan.service;

import java.util.List;
import com.pan.entity.Comment;
import com.pan.query.QueryComment;
import com.pan.vo.CommentVO;

/**
 * 
 * @author Administrator
 *
 */
public interface CommentService extends BaseService<Comment>{
	/**
	 * 添加评论
	 */
	Comment addComment(Comment comment);
	/**
	 * 根据文章id加载评论
	 * @param queryComment
	 * @return
	 */
	List<CommentVO> loadComments(QueryComment queryComment);
	/**
	 * 删除评论
	 * @param commentId
	 */
	void deleteByCommentId(Long commentId,Long userId);
	/**
	 * 统计文章评论数
	 * @param articleId
	 * @return
	 */
	int getCommnetCount(Long articleId);
	/**
	 * 根据用户评论
	 * @param userId
	 * @return
	 */
	List<Comment> loadUserComments(Long userId);
}
