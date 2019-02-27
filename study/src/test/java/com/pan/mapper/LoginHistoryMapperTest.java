package com.pan.mapper;

import com.pan.entity.LoginHistory;
import com.pan.query.QueryLoginHistory;
import com.pan.test.base.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
import java.util.List;

public class LoginHistoryMapperTest extends BaseTest {

    @Autowired
    private LoginHistoryMapper loginHistoryMapper;


    @Test
    public void insert() throws Exception {
        LoginHistory loginHistory=new LoginHistory();
        loginHistory.setUserId("123");
        loginHistory.setUsername("pzg");
        loginHistory.setIpStr("127.0.0.1");
        loginHistory.setUserAgent("22222222222222222222222222222222222222222222");
        loginHistory.setCreateTime(new Date());
        int insert = loginHistoryMapper.insert(loginHistory);
        Assert.assertEquals(insert,1);
    }

    @Test
    public void findByParams() throws Exception {
        loginHistoryMapper.selectByPrimaryKey(1L);
        QueryLoginHistory queryLoginHistory=new QueryLoginHistory();
        queryLoginHistory.setPageNo(1);
        queryLoginHistory.setPageSize(1);
        List<LoginHistory> byParams = loginHistoryMapper.findByParams(queryLoginHistory);
        System.out.println(byParams);
    }
}