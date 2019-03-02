package com.pan.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pan.common.vo.ResultMsg;
import com.pan.entity.Picture;
import com.pan.query.QueryPicture;
import com.pan.service.PictureService;
import com.pan.util.TokenUtils;

/**
 * 
 * @author Administrator
 *
 */
@Controller
public class PictureController {
	
	private static final Logger logger=LoggerFactory.getLogger(PictureController.class);
	
	@Autowired
	private PictureService pictureService;
	
	/**
	 * 跳转用户相册
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/myPicturePage")
	public ModelAndView toMyPictures(){
		logger.info("进入我的相册页...");
		ModelAndView mav=new ModelAndView("html/picture/pictureManage");
		return mav;
	}
	
	/**
	 * 加载图片数据，分页查询
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/getPictures")
	@ResponseBody
	public ResultMsg getPictureList(Integer pageSize,Integer pageNo){
		String loingUserId = TokenUtils.getLoginUserId();
		QueryPicture queryPicture=new QueryPicture();
		queryPicture.setUserId(loingUserId);
		queryPicture.setPageSize(pageSize);
		queryPicture.setPageNo(pageNo);
		List<Picture> list=pictureService.findPagable(queryPicture);
		int total = pictureService.countByParams(queryPicture);
		Map<String,Object> data=new HashMap<String, Object>(2);
		data.put("total", total);
		data.put("list", list);
		return ResultMsg.ok("获取用户图片列表信息成功", data);
	}
	
	/**
	 * 删除图片
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/deletePicture")
	@ResponseBody
	public ResultMsg deletePicture(String pictureIds){
		logger.info("删除图片开始");
		String loingUserId = TokenUtils.getLoginUserId();
		ResultMsg resultMsg;
		pictureService.deleteByPictureIds(loingUserId, pictureIds);
		resultMsg=ResultMsg.ok("删除图片成功");
		return resultMsg;
	}
}
