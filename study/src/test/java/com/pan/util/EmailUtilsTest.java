package com.pan.util;

import com.pan.test.base.BaseTest;

import org.junit.Assert;
import org.junit.Test;

public class EmailUtilsTest extends BaseTest {
    @Test
    public void sendMail() throws Exception {
        boolean sendMail = EmailUtils.sendMail("<span style='color:red'>666</span>","title","",true);
        Assert.assertFalse(sendMail);
    }

}