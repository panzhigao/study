package com.pan.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pan.common.vo.ResultMsg;
import com.pan.entity.Comment;
import com.pan.entity.User;
import com.pan.service.CommentService;
import com.pan.util.TokenUtils;
import com.pan.util.TransFieldUtils;
import com.pan.vo.CommentVO;

@Controller
@RequestMapping("/api")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	/**
	 * 评论
	 * @return
	 */
	@RequestMapping(value="/user/comment",method=RequestMethod.POST)
	@ResponseBody
	public ResultMsg comment(Comment comment){
		String loingUserId = TokenUtils.getLoingUserId();
		comment.setUserId(loingUserId);
		Comment addComment = commentService.addComment(comment);
		User loginUser = TokenUtils.getLoginUser();
		CommentVO commentVO=new CommentVO();
		BeanUtils.copyProperties(addComment, commentVO);
		commentVO.setUserPortrait(loginUser.getUserPortrait());
		commentVO.setNickname(loginUser.getNickname());
		TransFieldUtils.transEntity(addComment);
		return ResultMsg.ok("评论成功",commentVO);
	}
	
	/**
	 * 加载评论信息
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/loadComments")
	@ResponseBody
	public ResultMsg loadComments(String articleId){
		//TODO 分页
		String loingUserId = TokenUtils.getLoingUserId();
		List<CommentVO> comments = commentService.loadComments(loingUserId,articleId);
		TransFieldUtils.transEntityCollection(comments);
		return ResultMsg.ok("获取评论信息成功",comments);
	}
	
	/**
	 * 评论
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/deleteComment")
	@ResponseBody
	public ResultMsg deleteComments(String commentId){
		String loingUserId = TokenUtils.getLoingUserId();
		commentService.deleteByCommentId(commentId, loingUserId);
		return ResultMsg.ok("删除评论成功");
	}
	
	/**
	 * 加载用户评论
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/u/loadUserComment")
	@ResponseBody
	public ResultMsg loadUserComments(String userId){
		List<Comment> loadUserComments = commentService.loadUserComments(userId);
		return ResultMsg.ok("加载用户评论成功",loadUserComments);
	}
}
