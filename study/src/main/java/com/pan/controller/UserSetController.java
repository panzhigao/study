package com.pan.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.pan.common.exception.BusinessException;
import com.pan.common.vo.ResultMsg;
import com.pan.dto.PasswordDTO;
import com.pan.entity.User;
import com.pan.entity.UserExtension;
import com.pan.service.UserService;
import com.pan.util.PasswordUtils;
import com.pan.util.TokenUtils;
import com.pan.util.ValidationUtils;


/**
 * 用户基本设置
 * @author Administrator
 *
 */
@Controller
public class UserSetController {
	
	@Autowired
	private UserService userService;
	
	@Value("${cookie.maxAge}")
	private int cookieMaxage;
	
	/**
	 * 登陆成功，跳转用户设置页面
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/user/set")
	@RequiresPermissions("/user/set")
	public ModelAndView toSetPage(HttpServletRequest request){
		ModelAndView mav=new ModelAndView("html/user/set");
		User loingUser = TokenUtils.getLoginUser();
		UserExtension userExtension=userService.findExtensionByUserId(loingUser.getUserId());
		mav.addObject("user",loingUser);
		mav.addObject("userExtension",userExtension);
		return mav;
	}
		
	/**
	 * 修改个人信息
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/doSet")
	@ResponseBody
	@RequiresPermissions("/user/set")
	public ResultMsg userEdit(User user,UserExtension userExtension){
		ResultMsg resultMsg=null;
		String userId=TokenUtils.getLoginUserId();
		user.setUserId(userId);
		userService.updateUserInfo(user, userExtension);
		resultMsg=ResultMsg.ok("修改用户信息成功");
		return resultMsg;
	}
	
	/**
	 * 修改用户密码
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/resetPassword")
	@ResponseBody
	@RequiresPermissions("/user/set")
	public ResultMsg resetPassword(HttpServletRequest request,PasswordDTO passwordDTO) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		ValidationUtils.validateEntity(passwordDTO);
		if(!StringUtils.equals(passwordDTO.getNewPassword(), passwordDTO.getRePassword())){
			throw new BusinessException("确认密码与密码不一致");
		}
		String userId=TokenUtils.getLoginUserId();
		User userInDb = userService.findByUserId(userId);
		boolean flag=PasswordUtils.validPassword(passwordDTO.getNowPassword(),userInDb.getPassword());
		if(!flag){
			throw new BusinessException("密码输入错误");
		}
		userInDb.setPassword(PasswordUtils.getEncryptedPwd(passwordDTO.getNewPassword()));
		userService.updateUserByUserId(userInDb);
		return ResultMsg.ok("密码修改成功");
	}
	
	/**
	 * 发送验证码,用于绑定手机号
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/sendValidationCode")
	@ResponseBody
	@RequiresPermissions(value="/user/set")
	public ResultMsg sendValidationCode(HttpServletRequest request,String telephone){
		User user=new User();
		user.setTelephone(telephone);
		String sendValidationCode = userService.sendValidationCode(user,"set");
		return ResultMsg.ok("发送验证码成功",sendValidationCode);
	}
	
	/**
	 * 确认绑定手机
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/user/confirmBind")
	@ResponseBody
	@RequiresPermissions(value="/user/set")
	public ResultMsg confirmBind(String telephone,String code){
		String loginUserId = TokenUtils.getLoginUserId();
		User user=new User();
		user.setUserId(loginUserId);
		user.setTelephone(telephone);
		userService.bindTelephone(user, code);
		return ResultMsg.ok("绑定手机成功");
	}
}
