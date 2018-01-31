package com.pan.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pan.common.constant.MyConstant;
import com.pan.common.exception.BusinessException;
import com.pan.entity.User;

/**
 * 
 * Cookie 工具类
 * @author Administrator
 */
public final class CookieUtils {

    private static final Logger logger = LoggerFactory.getLogger(CookieUtils.class);

    private static final int MIN_LENGTH=1;
    
    private static final int MAX_LENGTH=3;
    
    private static final String SPECIAL_CHAR=":";
    
    /**
     * 得到Cookie的值, 不编码
     * 
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        return getCookieValue(request, cookieName, false);
    }

    /**
     * 得到Cookie的值,
     * 
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, boolean isDecoder) {
        Cookie[] cookieList = request.getCookies();
        if (cookieList == null || cookieName == null) {
            return null;
        }
        String retValue = null;
        try {
            for (int i = 0; i < cookieList.length; i++) {
                if (cookieList[i].getName().equals(cookieName)) {
                    if (isDecoder) {
                        retValue = URLDecoder.decode(cookieList[i].getValue(), "UTF-8");
                    } else {
                        retValue = cookieList[i].getValue();
                    }
                    break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("Cookie Decode Error.", e);
        }
        return retValue;
    }

    /**
     * 得到Cookie的值,
     * 
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, String encodeString) {
        Cookie[] cookieList = request.getCookies();
        if (cookieList == null || cookieName == null) {
            return null;
        }
        String retValue = null;
        try {
            for (int i = 0; i < cookieList.length; i++) {
                if (cookieList[i].getName().equals(cookieName)) {
                    retValue = URLDecoder.decode(cookieList[i].getValue(), encodeString);
                    break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("Cookie Decode Error.", e);
        }
        return retValue;
    }

    /**
     * 设置Cookie的值 不设置生效时间默认浏览器关闭即失效,也不编码
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
            String cookieValue) {
        setCookie(request, response, cookieName, cookieValue, -1);
    }

    /**
     * 设置Cookie的值 在指定时间内生效,但不编码
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
            String cookieValue, int cookieMaxage) {
        setCookie(request, response, cookieName, cookieValue, cookieMaxage, false);
    }

    /**
     * 设置Cookie的值 不设置生效时间,但编码
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
            String cookieValue, boolean isEncode) {
        setCookie(request, response, cookieName, cookieValue, -1, isEncode);
    }

    /**
     * 设置Cookie的值 在指定时间内生效, 编码参数
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
            String cookieValue, int cookieMaxage, boolean isEncode) {
        doSetCookie(request, response, cookieName, cookieValue, cookieMaxage, isEncode);
    }

    /**
     * 设置Cookie的值 在指定时间内生效, 编码参数(指定编码)
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
            String cookieValue, int cookieMaxage, String encodeString) {
        doSetCookie(request, response, cookieName, cookieValue, cookieMaxage, encodeString);
    }
    
    /**
     * 清空所有cookie
     * @param request
     * @param response
     */
    public static void cleanCookies(HttpServletRequest request, HttpServletResponse response){
    	 Cookie[] cookieList = request.getCookies();
         if (cookieList == null||cookieList.length==0) {
             return;
         }
         for (int i = 0; i < cookieList.length; i++) {
        	 Cookie cookie=cookieList[i];
        	 deleteCookie(request, response, cookie.getName());
         }
    }
    
    /**
     * 删除Cookie带cookie域名
     */
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response,
            String cookieName) {
        doSetCookie(request, response, cookieName, null, 0, false);
    }

    /**
     * 设置Cookie的值，并使其在指定时间内生效
     * 
     * @param cookieMaxage cookie生效的最大秒数
     */
    private static final void doSetCookie(HttpServletRequest request, HttpServletResponse response,
            String cookieName, String cookieValue, int cookieMaxage, boolean isEncode) {
        try {
            if (cookieValue == null) {
                cookieValue = "";
            } else if (isEncode) {
                cookieValue = URLEncoder.encode(cookieValue, "utf-8");
            }
            Cookie cookie = new Cookie(cookieName, cookieValue);
            cookie.setMaxAge(cookieMaxage);            	
            //设置域名的cookie
            if (null != request){
            	cookie.setDomain(getDomainName(request));            	
            }
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (Exception e) {
            logger.error("Cookie Encode Error.", e);
        }
    }

    /**
     * 设置Cookie的值，并使其在指定时间内生效
     * 
     * @param cookieMaxage cookie生效的最大秒数
     */
    private static final void doSetCookie(HttpServletRequest request, HttpServletResponse response,
            String cookieName, String cookieValue, int cookieMaxage, String encodeString) {
        try {
            if (cookieValue == null) {
                cookieValue = "";
            } else {
                cookieValue = URLEncoder.encode(cookieValue, encodeString);
            }
            Cookie cookie = new Cookie(cookieName, cookieValue);
            if (cookieMaxage > 0){            	
            	cookie.setMaxAge(cookieMaxage);
            }
         // 设置域名的cookie
            if (null != request){
            	cookie.setDomain(getDomainName(request));            	
            }
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (Exception e) {
            logger.error("Cookie Encode Error.", e);
        }
    }

    /**
     * 得到cookie的域名
     */
    private static final String getDomainName(HttpServletRequest request) {
        String domainName = null;

        String serverName = request.getRequestURL().toString();
        if (serverName == null || serverName.equals("")) {
            domainName = "";
        } else {
            serverName = serverName.toLowerCase();
            serverName = serverName.substring(7);
            final int end = serverName.indexOf("/");
            serverName = serverName.substring(0, end);
            final String[] domains = serverName.split("\\.");
            int len = domains.length;
            if (len > MAX_LENGTH) {
                // www.xxx.com.cn
                domainName = "." + domains[len - 3] + "." + domains[len - 2] + "." + domains[len - 1];
            } else if (len <= MAX_LENGTH && len > MIN_LENGTH) {
                // xxx.com or xxx.cn
                domainName = "." + domains[len - 2] + "." + domains[len - 1];
            } else {
                domainName = serverName;
            }
        }
        if (domainName != null && domainName.indexOf(SPECIAL_CHAR) > 0) {
            String[] ary = domainName.split("\\:");
            domainName = ary[0];
        }
        return domainName;
    }
    
    /**
     * 获取登陆用户信息
     * @return
     */
    public static User getLoginUser(HttpServletRequest request){
    	User user=null;
    	String cookieValue = getCookieValue(request, MyConstant.TOKEN);
    	if(cookieValue!=null){
    		String string = JedisUtils.getString(MyConstant.USER_LOGINED+cookieValue);
    		if(string!=null){
    			try {
					user=(User) JsonUtils.fromJson(string, User.class);
				} catch (Exception e) {
					logger.error("reids获取登陆用户信息失败", e);
				}	
    		}
    	}
    	return user;
    }
    

    /**
     * 获取登陆用户id
     * @return
     */
    public static String getLoginUserId(HttpServletRequest request){
    	User loginUser = getLoginUser(request);
    	String userId=null;
    	if(loginUser!=null){
    		userId=loginUser.getUserId();
    	}
    	return userId;
    }
    
    public static void validateVercode(HttpServletRequest request,String vercode){
    	if(StringUtils.isBlank(vercode)){
			throw new BusinessException("验证码不能为空");
		}
    	String cookieValue = getCookieValue(request, MyConstant.SESSION_ID);
    	if(cookieValue==null){
    		throw new BusinessException("验证码失效");
    	}
    	String redisVercode = JedisUtils.getString(MyConstant.USER_SESSION+cookieValue);
    	if(redisVercode==null){
    		throw new BusinessException("验证码失效");
    	}
    	if(!StringUtils.equalsIgnoreCase(vercode, redisVercode)){
    		throw new BusinessException("验证码输入错误");
    	}
    }
}
