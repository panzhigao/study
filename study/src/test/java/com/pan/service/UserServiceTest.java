package com.pan.service;

import com.pan.entity.User;
import com.pan.query.QueryUser;
import com.pan.test.base.BaseTest;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.util.ThreadContext;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.junit.Assert.*;

public class UserServiceTest extends BaseTest {

    @Autowired
    private IUserService userService;

    @Autowired
    org.apache.shiro.mgt.SecurityManager securityManager;

    @Test
    public void registerUser() throws Exception {
        ThreadContext.bind(securityManager);
        User user=new User();
        user.setUsername("admin00");
        user.setNickname("admin00");
        user.setPassword("123456");
        userService.registerUser(user);
    }

    @Test
    public void findByUsername() throws Exception {
        User admin = userService.findByUsername("admin");
        assertNotNull(admin);
    }

    @Test
    public void findByUserTelephone() throws Exception {
        User byUserTelephone = userService.findByUserTelephone("18911536627");
        assertNotNull(byUserTelephone);
    }

    @Test
    public void checkLogin() throws Exception {

    }

    @Test
    public void updateUserLastLoginTime() throws Exception {
        User admin = userService.findByUsername("admin");
        userService.updateUserLastLoginTime(admin.getId());
    }

    @Test
    public void updateUserInfo() throws Exception {

    }

    @Test
    public void sendValidationCode() throws Exception {
    }

    @Test
    public void bindTelephone() throws Exception {
    }

    @Test
    public void updateUserByUserId() throws Exception {
    }

    @Test
    public void findPageData() throws Exception {
        QueryUser queryUser=new QueryUser();
        queryUser.setSex(1);
        Map<String, Object> pageData = userService.findPageData(queryUser);
        assertTrue(MapUtils.isEmpty(pageData));
    }

    @Test
    public void allocateRoleToUser() throws Exception {
    }

    @Test
    public void changeUserStatus() throws Exception {
    }

    @Test
    public void findUserByRoleId() throws Exception {
    }

    @Test
    public void checkIn() throws Exception {
        User user=userService.findByUsername("chenhe");
        userService.checkIn(user.getId());
    }
    
    @Test
    public void syncUserEsData(){
    	userService.syncUserEsData();
    }
    
}