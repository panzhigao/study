package com.pan.interceptor;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pan.common.constant.MyConstant;
import com.pan.service.ArticleCategoryService;
import com.pan.service.impl.ArticleCategoryServiceImpl;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.pan.entity.Link;
import com.pan.entity.User;
import com.pan.service.LinkService;
import com.pan.util.IPUtils;
import com.pan.util.SpringContextUtils;
import com.pan.util.SystemConfigUtils;
import com.pan.util.TokenUtils;


/**
 * 会话拦截器
 * @author Administrator
 *
 */
public class SessionInterceptor implements HandlerInterceptor{

	/**
	 * 1.禁止非同源iframe嵌套
	 * 2.获取发起请求的设备信息
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		response.addHeader("X-frame-options", "SAMEORIGIN");
		String userAgent = request.getHeader(MyConstant.USER_AGENT_HEADER);
		TokenUtils.setAttribute(MyConstant.USER_AGENT,userAgent);
		String ipAddress = IPUtils.getIpAddress(request);
		TokenUtils.setAttribute(MyConstant.USER_IP,ipAddress);
		return true;
	}
	
	/**
	 * 为当前用户生成唯一sessionId,用户标注唯一会话
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if(modelAndView!=null){
			User loginUser=TokenUtils.getLoginUser();
			if(loginUser!=null){			
				modelAndView.addObject(MyConstant.USER,loginUser);
			}
			modelAndView.addObject(MyConstant.SYSTEM_CONFIG,SystemConfigUtils.getSystemConfig());
			LinkService linkService = SpringContextUtils.getBean(LinkService.class);
			List<Link> onlineLinkList = linkService.getOnlineLinkList();
			modelAndView.addObject(MyConstant.ONLINE_LINK_LIST,onlineLinkList);
			modelAndView.addObject(MyConstant.ONLINE_ARTICLE_CATEGORY_LIST, ArticleCategoryServiceImpl.getOnlineCategoryThroughCache());
		}
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
