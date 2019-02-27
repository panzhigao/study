package com.pan.mapper;

import com.pan.test.base.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;

public class OperateLogMapperTest extends BaseTest{

    @Autowired
    private OperateLogMapper operateLogMapper;

    public void selectByPrimaryKey(){
        operateLogMapper.selectByPrimaryKey(1L);
    }

}