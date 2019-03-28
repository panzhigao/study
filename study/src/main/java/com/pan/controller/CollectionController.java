package com.pan.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.Collection;
import com.pan.query.QueryCollection;
import com.pan.service.CollectionService;
import com.pan.util.TokenUtils;

/**
 * @author panzhigao
 */
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
	public ResultMsg addCollection(String articleId){
		String loginUserId = TokenUtils.getLoginUserId();
		Collection collection=new Collection();
		collection.setArticleId(articleId);
		collection.setUserId(loginUserId);
		collectionService.addCollection(collection);
		return ResultMsg.ok("收藏成功");
	}
	
	/**
	 * 取消收藏
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/collection/remove")
	@ResponseBody
	public ResultMsg removeCollection(Long id){
		String loginUserId = TokenUtils.getLoginUserId();
		collectionService.removeCollection(loginUserId,id);
		return ResultMsg.ok("取消收藏成功");
	}
	
	/**
	 * 查看文章是否收藏
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/collection/find")
	@ResponseBody
	public ResultMsg findCollection(String articleId){
		String loginUserId = TokenUtils.getLoginUserId();
		boolean flag = collectionService.checkArticleCollected(loginUserId, articleId);
		if(flag){
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
	public Map<String,Object> getUserCollectionList(QueryCollection collection){
		String loginUserId = TokenUtils.getLoginUserId();
		collection.setUserId(loginUserId);
		Map<String,Object> pageData=collectionService.findPageableMap(collection);
		return pageData;
	}
}
