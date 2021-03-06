package com.pan.service;

import com.pan.entity.User;
import com.pan.entity.UserExtension;
import com.pan.test.base.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.*;

public class UserExtensionServiceTest extends BaseTest {

    @Autowired
    private IUserExtensionService userExtensionService;

    @Autowired
    private IUserService userService;

    @Test
    public void increaseCounts() throws Exception {
        User admin = userService.findByUsername("admin");
        assertNotNull(admin);
        UserExtension userExtension=new UserExtension();
        userExtension.setId(admin.getId());
        userExtension.setArticleCounts(2);
        userExtensionService.increaseCounts(userExtension);
    }

}