package com.pan.mapper;

import com.pan.entity.OperateLog;
import com.pan.test.base.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class OperateLogMapperTest extends BaseTest{

    @Autowired
    private OperateLogMapper operateLogMapper;

    public void selectByPrimaryKey(){
        operateLogMapper.selectByPrimaryKey(1L);
    }

}