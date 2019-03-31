package com.pan.service.impl;

import com.pan.entity.OperateLog;
import com.pan.service.OperateLogService;
import com.pan.test.base.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;


public class OperateLogServiceImplTest extends BaseTest{

    @Autowired
    private OperateLogService operateLogService;

    @Test
    public void selectByPrimaryKey() throws Exception {
        OperateLog operateLog = operateLogService.selectByPrimaryKey(2L);
        System.out.println(operateLog);
    }

    @Test
    public void deleteByPrimaryKey() throws Exception {

    }

    @Test
    public void insertSelective() throws Exception {
        OperateLog operateLog=new OperateLog();
        operateLog.setUserId(123L);
        operateLog.setUsername("pzg");
        operateLog.setContent("77777");
        operateLog.setCreateTime(new Date());
        operateLog.setIp(299992222);
        operateLog.setOperateType(2);
        operateLogService.insertSelective(operateLog);
    }

    @Test
    public void updateByPrimaryKeySelective() throws Exception {
        OperateLog operateLog = operateLogService.selectByPrimaryKey(26L);
        operateLog.setContent("111223455");
        int i = operateLogService.updateByPrimaryKeySelective(operateLog);
        Assert.assertEquals(1,i);
    }

}