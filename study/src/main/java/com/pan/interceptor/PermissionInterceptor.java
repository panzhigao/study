package com.pan.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.pan.common.annotation.HasPermission;
import com.pan.common.vo.ResultMsg;
import com.pan.util.JsonUtils;
import com.pan.util.PermissionUtils;

/**
 * 权限拦截器
 * @author 作者
 * @version 创建时间：2018年4月2日 下午5:42:28
 * 类说明
 */
public class PermissionInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HandlerMethod hm =  (HandlerMethod) handler;//将其强转过来  
        HasPermission hasPermission = hm.getMethodAnnotation(HasPermission.class);//拿到里面的自定义注解对象//通过反射  
        if(hasPermission!=null){
        	String url=hasPermission.value();
        	if(StringUtils.isBlank(url)){
        		url=request.getServletPath();
        	}
        	boolean flag=PermissionUtils.hasPermssion(url);
        	if(!flag){
    			if(isAjax(request)){
    				response.setCharacterEncoding("UTF-8");
    				response.setContentType("application/json; charset=utf-8");  
    	            PrintWriter writer= null;
    				 try {  
    					 writer=response.getWriter(); 
    	                 ResultMsg resultMsg=ResultMsg.fail("对不起，你没有权限");
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
    				response.sendRedirect("/study//unauth");
    			}
    			return false;
        	}
        }
        return true;
	}
	
	public static boolean isAjax(HttpServletRequest request) {  
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));  
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
