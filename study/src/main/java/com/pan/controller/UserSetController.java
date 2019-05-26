package com.pan.controller;

import com.pan.service.IUserExtensionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.pan.common.constant.MyConstant;
import com.pan.common.exception.BusinessException;
import com.pan.common.vo.ResultMsg;
import com.pan.dto.PasswordDTO;
import com.pan.entity.User;
import com.pan.entity.UserExtension;
import com.pan.service.IUserService;
import com.pan.util.PasswordUtils;
import com.pan.util.RSAUtil;
import com.pan.util.TokenUtils;
import com.pan.util.ValidationUtils;
import java.util.Date;

/**
 * 用户基本设置
 * 
 * @author Administrator
 *
 */
@Controller
public class UserSetController {

	@Autowired
	private IUserService userService;

	@Autowired
	private IUserExtensionService userExtensionService;

	@Value("${cookie.maxAge}")
	private int cookieMaxage;

	/**
	 * 登陆成功，跳转用户设置页面
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/user/set")
	@RequiresPermissions("user:set")
	public ModelAndView toSetPage() {
		ModelAndView mav = new ModelAndView("html/user/set");
		User loginUser = TokenUtils.getLoginUser();
		UserExtension userExtension = userExtensionService.selectByPrimaryKey(loginUser.getId());
		mav.addObject("user", loginUser);
		mav.addObject("userExtension", userExtension);
		return mav;
	}

	/**
	 * 修改个人信息
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/user/doSet")
	@ResponseBody
	@RequiresPermissions("user:set")
	public ResultMsg userEdit(User user, UserExtension userExtension) {
		userService.updateUserInfo(user, userExtension);
		return ResultMsg.ok("修改用户信息成功");
	}

	/**
	 * 修改用户密码
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/user/resetPassword")
	@ResponseBody
	@RequiresPermissions("user:set")
	public ResultMsg resetPassword(PasswordDTO passwordDTO) throws Exception {
		// 获取私钥
		String privateKey = (String) TokenUtils.getAttribute(MyConstant.PRIVATE_KEY);
		// 解密密码
		passwordDTO.setNewPassword(RSAUtil.decodeByPrivateKey(passwordDTO.getNewPassword(), privateKey));
		passwordDTO.setNowPassword(RSAUtil.decodeByPrivateKey(passwordDTO.getNowPassword(), privateKey));
		passwordDTO.setRePassword(RSAUtil.decodeByPrivateKey(passwordDTO.getRePassword(), privateKey));
		ValidationUtils.validateEntity(passwordDTO);
		if (!StringUtils.equals(passwordDTO.getNewPassword(), passwordDTO.getRePassword())) {
			throw new BusinessException("确认密码与密码不一致");
		}
		Long userId = TokenUtils.getLoginUserId();
		User userInDb = userService.selectByPrimaryKey(userId);
		boolean flag = PasswordUtils.validPassword(passwordDTO.getNowPassword(), userInDb.getPassword());
		if (!flag) {
			throw new BusinessException("密码输入错误");
		}
		User updateUser = new User();
		updateUser.setId(userId);
		updateUser.setPassword(PasswordUtils.getEncryptedPwd(passwordDTO.getNewPassword()));
		updateUser.setUpdateTime(new Date());
		userService.updateByPrimaryKeySelective(updateUser);
		return ResultMsg.ok("密码修改成功");
	}

	/**
	 * 发送验证码,用于绑定手机号
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/user/sendValidationCode")
	@ResponseBody
	@RequiresPermissions(value = "user:set")
	public ResultMsg sendValidationCode(String telephone) {
		User user = new User();
		user.setTelephone(telephone);
		String sendValidationCode = userService.sendValidationCode(user, "set");
		return ResultMsg.ok("发送验证码成功", sendValidationCode);
	}

	/**
	 * 确认绑定手机
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/user/confirmBind")
	@ResponseBody
	@RequiresPermissions(value = "user:set")
	public ResultMsg confirmBind(String telephone, String code) {
		Long loginUserId = TokenUtils.getLoginUserId();
		User user = new User();
		user.setId(loginUserId);
		user.setTelephone(telephone);
		userService.bindTelephone(user, code);
		return ResultMsg.ok("绑定手机成功");
	}
}
