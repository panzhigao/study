package com.pan.mapper;

import com.pan.test.base.BaseTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class OperateLogMapperTest extends BaseTest{

    @Autowired
    private OperateLogMapper operateLogMapper;
    
    @Test
    public void selectByPrimaryKey(){
        operateLogMapper.selectByPrimaryKey(1L);
    }

}