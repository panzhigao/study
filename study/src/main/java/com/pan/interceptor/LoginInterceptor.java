package com.pan.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.pan.entity.User;
import com.pan.service.impl.UserServiceImpl;
import com.pan.util.CookieUtils;

/**
 * 登陆拦截器
 * @author Administrator
 *
 */
public class LoginInterceptor implements HandlerInterceptor{
	
	private static final Logger logger=LoggerFactory.getLogger(LoginInterceptor.class);
	
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		User loginUser = CookieUtils.getLoginUser(request);
		if(loginUser==null){
			logger.info("用户未登录，跳转登录页");
			response.sendRedirect("/study/login");
			return false;
		}
		return true;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
