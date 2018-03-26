package com.pan.interceptor;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.pan.common.constant.MyConstant;
import com.pan.entity.User;
import com.pan.util.CookieUtils;


/**
 * 登陆拦截器
 * @author Administrator
 *
 */
public class SessionInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
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
			User loginUser = CookieUtils.getLoginUser(request);
			if(loginUser!=null){			
				modelAndView.addObject("user",loginUser);
				if(request.getSession().getAttribute(MyConstant.USER_ID)==null){					
					request.getSession().setAttribute(MyConstant.USER_ID, loginUser.getUserId());
				}
			}
		}
		String cookieValue = CookieUtils.getCookieValue(request, MyConstant.SESSION_ID);
		if(cookieValue==null){
			cookieValue=UUID.randomUUID().toString();
			CookieUtils.setCookie(request, response, MyConstant.SESSION_ID, cookieValue);
		}
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
