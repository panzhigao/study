package com.pan.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.pan.entity.User;
import com.pan.util.TokenUtils;


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
			User loginUser=TokenUtils.getLoingUser();
			if(loginUser!=null){			
				modelAndView.addObject("user",loginUser);
			}
		}
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
