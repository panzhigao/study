package com.pan.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
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
import com.pan.entity.User;
import com.pan.service.PictureService;
import com.pan.util.CookieUtils;

/**
 * 
 * @author Administrator
 *
 */
@Controller
public class PicturesController {
	
	private static final Logger logger=LoggerFactory.getLogger(PicturesController.class);
	
	@Autowired
	private PictureService pictureService;
	
	/**
	 * 跳转用户相册
	 * @param request
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/my_pictures")
	public ModelAndView toMyPictures(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("content/myPictures");
		CookieUtils.getLoginUser(request);
		User user = CookieUtils.getLoginUser(request);
		mav.addObject("user", user);
		return mav;
	}
	
	/**
	 * 加载文章列数据，分页查询
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/get_pictures")
	@ResponseBody
	public List<Picture> getPictureList(HttpServletRequest request,Integer pageSize,Integer pageNo){
		String loingUserId = CookieUtils.getLoginUserId(request);
		Map<String,Object> params=new HashMap<String, Object>(3);
		params.put("userId", loingUserId);
		Integer offset=(pageNo-1)*pageSize;
		params.put("offset", offset);
		params.put("row", pageSize);
		List<Picture> list=pictureService.findByParams(params);
		return list;
	}
	
	/**
	 * 删除图片
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/delete_picture")
	@ResponseBody
	public ResultMsg deletePicture(HttpServletRequest request,String pictureId){
		logger.info("删除图片开始");
		String loingUserId = CookieUtils.getLoginUserId(request);
		ResultMsg resultMsg=null;
		pictureService.deleteByPictureId(loingUserId, pictureId);
		resultMsg=ResultMsg.ok("删除图片成功");
		return resultMsg;
	}
}
