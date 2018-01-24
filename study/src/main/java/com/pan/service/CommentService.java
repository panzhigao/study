package com.pan.service;

import java.util.List;

import com.pan.entity.Comment;

/**
 * 
 * @author Administrator
 *
 */
public interface CommentService {
	/**
	 * 添加评论
	 */
	public Comment addComment(Comment comment);
	/**
	 * 根据文章id加载评论
	 * @param articleId
	 * @return
	 */
	public List<Comment> loadComments(String userId,String articleId);
	/**
	 * 删除评论
	 * @param commentId
	 */
	public void deleteByCommentId(String commentId,String userId);
	/**
	 * 统计文章评论数
	 * @param articleId
	 * @return
	 */
	public int getCommnetCount(String articleId);
}
