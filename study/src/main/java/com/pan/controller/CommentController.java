package com.pan.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
	public ResultMsg comment(HttpServletRequest request,Comment comment){
		String loingUserId = TokenUtils.getLoingUserId();
		comment.setUserId(loingUserId);
		Comment addComment = commentService.addComment(comment);
		User loginUser = TokenUtils.getLoginUser();
		addComment.setUserPortrait(loginUser.getUserPortrait());
		addComment.setNickname(loginUser.getNickname());
		TransFieldUtils.transEntity(addComment);
		return ResultMsg.ok("评论成功",addComment);
	}
	
	/**
	 * 加载评论信息
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/loadComments")
	@ResponseBody
	public ResultMsg loadComments(HttpServletRequest request,String articleId){
		//TODO 分页
		String loingUserId = TokenUtils.getLoingUserId();
		List<Comment> comments = commentService.loadComments(loingUserId,articleId);
		TransFieldUtils.transEntityCollection(comments);
		return ResultMsg.ok("获取评论信息成功",comments);
	}
	
	/**
	 * 评论
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/deleteComment")
	@ResponseBody
	public ResultMsg deleteComments(HttpServletRequest request,String commentId){
		String loingUserId = TokenUtils.getLoingUserId();
		commentService.deleteByCommentId(commentId, loingUserId);
		return ResultMsg.ok("删除评论成功");
	}
}
