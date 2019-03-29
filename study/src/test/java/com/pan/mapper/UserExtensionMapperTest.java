package com.pan.mapper;

import com.pan.entity.UserExtension;
import com.pan.test.base.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class UserExtensionMapperTest extends BaseTest {
    
    @Autowired
    private UserExtensionMapper userExtensionMapper;
    
    @Test
    public void increaseCounts() throws Exception {
        UserExtension userExtension=new UserExtension();
        userExtension.setId(-1L);
        userExtension.setArticleCounts(1);
        int i = userExtensionMapper.increaseCounts(userExtension);
        assertTrue(i==0);
    }

}