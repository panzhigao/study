package com.pan.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.pan.common.enums.UserStatusEnum;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.User;
import com.pan.util.JsonUtils;
import com.pan.util.TokenUtils;

/**
 * 登陆拦截器
 * @author Administrator
 *
 */
public class LoginInterceptor implements HandlerInterceptor{
	
	private static final Logger logger=LoggerFactory.getLogger(LoginInterceptor.class);
	
	public static boolean isAjax(HttpServletRequest request) {  
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));  
    } 
	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		User loginUser = TokenUtils.getLoginUser();
		if(loginUser==null||UserStatusEnum.STATUS_BLOCKED.getCode().equals(loginUser.getStatus())){
			String message="";
			if(loginUser==null){
				message="用户未登录";
			}else if(UserStatusEnum.STATUS_BLOCKED.getCode().equals(loginUser.getStatus())){
				message="用户被禁用";
				TokenUtils.clearAuth();
			}
			logger.info(message);
			if(isAjax(request)){
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json; charset=utf-8");  
	            PrintWriter writer= null;
				 try {  
					 writer=response.getWriter(); 
	                 ResultMsg resultMsg=ResultMsg.fail(message);
	                 writer.write(JsonUtils.toJson(resultMsg));
	                 writer.flush();  
	             } catch (IOException e) {  
	                 e.printStackTrace();  
	             } finally{
	            	 if(writer!=null){
	            		 writer.close();
	            	 }
	             }
			}else{
				response.sendRedirect("/login");
			}
			return false;
		}
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
