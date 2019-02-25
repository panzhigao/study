package com.pan.common.filter;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pan.common.enums.UserStatusEnum;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.User;
import com.pan.util.JsonUtils;
import com.pan.util.TokenUtils;


/**
 * @author 作者
 * @version 创建时间：2018年6月22日 下午2:23:14
 * 类说明
 * 该过滤器用于拦截需要登录的ajax请求，如果是ajax请求，判断是否登录，未登录，返回json数据,返回false，登录，返回true
 * 不是ajax请求，返回true
 */
public class MyAuthenticationFilter extends AccessControlFilter{
	
	private static final Logger logger=LoggerFactory.getLogger(MyAuthenticationFilter.class);
	
	public static boolean isAjax(HttpServletRequest request) {  
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));  
    } 
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request,ServletResponse response, Object mappedValue) throws Exception {
		if(!SecurityUtils.getSubject().isAuthenticated()){			
			WebUtils.saveRequest(request);
		}
		//是ajax请求
		if(isAjax((HttpServletRequest)request)){
			User loginUser = TokenUtils.getLoginUser();
			//用户未登录或者用户被禁用
			if(loginUser==null||UserStatusEnum.STATUS_BLOCKED.getCode().equals(loginUser.getStatus())){
				String message="";
				if(loginUser==null){
					message="用户未登录";
				}else if(UserStatusEnum.STATUS_BLOCKED.getCode().equals(loginUser.getStatus())){//用户被禁用
					message="用户被禁用";
					TokenUtils.clearAuth();
				}
				logger.info(message);
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
				 return false;
			}
		}
		return true;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		return false;
	}

}
