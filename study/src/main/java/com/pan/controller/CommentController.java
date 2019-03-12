package com.pan.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pan.query.QueryComment;
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
		String loingUserId = TokenUtils.getLoginUserId();
		comment.setUserId(loingUserId);
		Comment addComment = commentService.addComment(comment);
		User loginUser = TokenUtils.getLoginUser();
		CommentVO commentVO=new CommentVO();
		BeanUtils.copyProperties(addComment, commentVO);
		commentVO.setUserPortrait(loginUser.getUserPortrait());
		commentVO.setNickname(loginUser.getNickname());
		TransFieldUtils.transEntity(commentVO);
		return ResultMsg.ok("评论成功",commentVO);
	}
	
	/**
	 * 加载评论信息
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/loadComments")
	@ResponseBody
	public ResultMsg loadComments(QueryComment queryComment){
		String loginUserId = TokenUtils.getLoginUserId();
		queryComment.setUserId(loginUserId);
		int count = commentService.countByParams(queryComment);
		List<CommentVO> resultList=new ArrayList<>();
		if(count>0){
			resultList= commentService.loadComments(queryComment);
			TransFieldUtils.transEntityCollection(resultList);
		}
		Map<String,Object> resultMap=new HashMap<>(4);
		resultMap.put("total", count);
		resultMap.put("data", resultList);
		return ResultMsg.ok("获取评论信息成功",resultMap);
	}
	
	/**
	 * 评论
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/deleteComment")
	@ResponseBody
	public ResultMsg deleteComments(String commentId){
		String loingUserId = TokenUtils.getLoginUserId();
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
		TransFieldUtils.transEntityCollection(loadUserComments);
		return ResultMsg.ok("加载用户评论成功",loadUserComments);
	}
}
