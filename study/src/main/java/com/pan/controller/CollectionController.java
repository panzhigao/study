package com.pan.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pan.common.vo.ResultMsg;
import com.pan.entity.Collection;
import com.pan.service.CollectionService;
import com.pan.util.CookieUtils;

@Controller
public class CollectionController {
	
	@Autowired
	private CollectionService collectionService;
	
	
	/**
	 * 添加收藏
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/collection/add")
	@ResponseBody
	public ResultMsg addCollection(HttpServletRequest request,String articleId){
		String loingUserId = CookieUtils.getLoginUserId(request);
		Collection collection=new Collection();
		collection.setArticleId(articleId);
		collection.setUserId(loingUserId);
		collectionService.addCollection(collection);
		return ResultMsg.ok("收藏成功");
	}
	
	/**
	 * 取消收藏
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/collection/remove")
	@ResponseBody
	public ResultMsg removeCollection(HttpServletRequest request,String articleId){
		String loingUserId = CookieUtils.getLoginUserId(request);
		collectionService.removeCollection(loingUserId, articleId);
		return ResultMsg.ok("取消收藏成功");
	}
	
	/**
	 * 查看文章是否收藏
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/collection/find")
	@ResponseBody
	public ResultMsg findCollection(HttpServletRequest request,String articleId){
		String loingUserId = CookieUtils.getLoginUserId(request);
		Collection collection = collectionService.findUserCollection(loingUserId, articleId);
		if(collection!=null){
			return ResultMsg.ok("已收藏",true);
		}
		return ResultMsg.ok("未收藏",false);
	}
	
	/**
	 * 加载文章列数据，分页查询
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/collection/get_collections")
	@ResponseBody
	public Map<String,Object> getUserCollectionList(HttpServletRequest request,Integer pageSize,Integer pageNo){
		String loingUserId = CookieUtils.getLoginUserId(request);
		Map<String,Object> params=new HashMap<String, Object>(5);
		params.put("userId", loingUserId);
		Integer offset=(pageNo-1)*pageSize;
		params.put("offset", offset);
		params.put("row", pageSize);
		Map<String,Object> pageData=collectionService.findByParams(params);
		return pageData;
	}
}
