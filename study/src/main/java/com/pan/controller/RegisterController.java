package com.pan.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.pan.common.annotation.CheckUsernameGroup;
import com.pan.common.constant.MyConstant;
import com.pan.common.exception.BusinessException;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.User;
import com.pan.service.RoleService;
import com.pan.service.UserService;
import com.pan.util.CookieUtils;
import com.pan.util.JedisUtils;
import com.pan.util.JsonUtils;
import com.pan.util.ValidationUtils;
import com.pan.util.VerifyCodeUtils;

/**
 * 注册
 * @author Administrator
 *
 */
@Controller
public class RegisterController {
	
	private static final Logger logger=LoggerFactory.getLogger(LoginController.class);
	
	@Value("${cookie.maxAge}")
	private int cookieMaxage;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * 跳转注册页
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/register")
	public ModelAndView toRegisterPage(HttpServletRequest request,HttpServletResponse response){
		if(CookieUtils.getLoginUser(request)!=null){
			 ModelAndView mv = new ModelAndView("redirect:/user/home");
			 return mv;
		}
		String cookieValue = CookieUtils.getCookieValue(request, MyConstant.SESSION_ID);
		String vercode=VerifyCodeUtils.generateVerifyCode(4);
		JedisUtils.setString(MyConstant.USER_SESSION+cookieValue, vercode);
		JedisUtils.setStringExpire(MyConstant.USER_SESSION+cookieValue, vercode, 3600);
		ModelAndView mav=new ModelAndView("html/user/reg");
		mav.addObject("vercode", vercode);
		return mav;
	}
	
	/**
	 * 用户注册操作
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/doRegister")
	@ResponseBody
	public ResultMsg register(HttpServletRequest request,HttpServletResponse response,User user){
		logger.info("注册开始,用户信息为：{}",user);
		User saveUser = userService.saveUser(user);
		String token=UUID.randomUUID().toString();
		//设置cookie过期时间
		CookieUtils.setCookie(request, response, MyConstant.TOKEN,token,cookieMaxage);
		saveUser.setPassword(null);
		String json=JsonUtils.toJson(saveUser);
		JedisUtils.setStringExpire(MyConstant.USER_LOGINED+token, json, cookieMaxage);
		
		//用户角色信息放入redis
		List<String> list = roleService.getRoleByUserId(saveUser.getUserId());
		JedisUtils.setString("user_roles:"+saveUser.getUserId(), JsonUtils.toJson(list));
				
		//用户登录成功，将用户session添加到redis集合中
		request.getSession().setAttribute("userId", saveUser.getUserId());
		return ResultMsg.ok("用户注册成功");
	}
	
	/**
	 * 用户注册
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/checkUnique")
	@ResponseBody
	public Map<String,Object> checkUnique(String username){
		logger.info("校验用户名是否已注册：{}",username);
		User checkUser=new User();
		checkUser.setUsername(username);
		Map<String,Object> resultMap=new HashMap<String, Object>(2);
		try {
			ValidationUtils.validateEntityWithGroups(checkUser, CheckUsernameGroup.class);
		} catch (BusinessException e) {
			resultMap.put("valid", false);
			resultMap.put("msg", e.getMessage());
			return resultMap;
		}
		User user = userService.findByUsername(username);
		if(user==null){
			resultMap.put("valid", true);
		}else{
			resultMap.put("valid", false);
			resultMap.put("msg", "该用户名已被占用，请换一个");
		}
		return resultMap;
	}
}
