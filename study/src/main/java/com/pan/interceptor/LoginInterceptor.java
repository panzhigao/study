package com.pan.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.pan.entity.User;
import com.pan.util.CookieUtils;

/**
 * 登陆拦截器
 * @author Administrator
 *
 */
public class LoginInterceptor implements HandlerInterceptor{

	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		User loginUser = CookieUtils.getLoginUser(request);
		if(loginUser==null){
			response.sendRedirect("/study/login");
			return false;
		}
		return true;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		User loginUser = CookieUtils.getLoginUser(request);
		modelAndView.addObject("user",loginUser);
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
