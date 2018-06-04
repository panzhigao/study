package com.pan.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pan.common.constant.MyConstant;
import com.pan.common.exception.BusinessException;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.User;
import com.pan.service.UserService;
import com.pan.util.JedisUtils;
import com.pan.util.PasswordUtils;

/**
 * 找回密码
 * @author panzhigao-wb
 *
 */
@Controller
public class ForgetController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/forget")
	public String index(){
		return "html/user/forget";
	}
	
	/**
	 * 发送验证码,找回密码
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value="/sendForgetValidationCode")
	@ResponseBody
	public ResultMsg sendValidationCode(HttpServletRequest request,String telephone){
		User user=new User();
		user.setTelephone(telephone);
		String sendValidationCode = userService.sendValidationCode(user,MyConstant.OPERATETYPE_FINDPASSWORD);
		return ResultMsg.ok("发送验证码成功",sendValidationCode);
	}
	
	/**
	 * 修改用户密码
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	@RequestMapping(method=RequestMethod.POST,value="/resetPassword")
	@ResponseBody
	public ResultMsg resetPassword(HttpServletRequest request,String telephone,String newPassword,String vercode) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		//校验新密码
		String codeInRedis = JedisUtils.getString(telephone);
		if(codeInRedis==null){
			throw new BusinessException("验证码过期");
		}
		if(StringUtils.equals(codeInRedis, vercode)){
			User userInDb = userService.findByUserTelephone(telephone);
			if(userInDb==null){
				throw new BusinessException("账号不存在");
			}
			userInDb.setPassword(PasswordUtils.getEncryptedPwd(newPassword));
			userService.updateUserByUserId(userInDb);
		}else{
			throw new BusinessException("验证码输入有误");
		}
		return ResultMsg.ok("密码修改成功");
	}
}
