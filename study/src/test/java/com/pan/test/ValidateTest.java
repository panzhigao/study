package com.pan.test;


import com.pan.common.annotation.LoginGroup;
import com.pan.common.annotation.RegisterGroup;
import com.pan.common.annotation.UserEditGroup;
import com.pan.entity.User;
import com.pan.util.ValidationUtils;

/**
 * 校验测试
 * @author Administrator
 *
 */
public class ValidateTest {
	public static void main(String[] args) {
		User user=new User();
		user.setUsername("a221");
		user.setNickname("潘志");
		user.setPassword("123");
		user.setTelephone("13901043841");
		ValidationUtils.validateEntityWithGroups(user,new Class[]{LoginGroup.class,RegisterGroup.class,UserEditGroup.class});
	}
}
