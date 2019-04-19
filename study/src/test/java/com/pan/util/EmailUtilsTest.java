package com.pan.util;

import com.pan.test.base.BaseTest;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmailUtilsTest extends BaseTest {
    @Test
    public void sendMail() throws Exception {
        EmailUtils.sendMail("<span style='color:red'>666</span>","title","16253672@qq.com",true);

    }

}