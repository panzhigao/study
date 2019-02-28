package com.pan.service.impl;

import com.pan.entity.LoginHistory;
import com.pan.mapper.LoginHistoryMapper;
import com.pan.service.AbstractBaseService;
import com.pan.service.LoginHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author panzhigao
 */
@Service
public class LoginHistoryServiceImpl extends AbstractBaseService<LoginHistory,LoginHistoryMapper> implements LoginHistoryService{

    @Autowired
    private LoginHistoryMapper loginHistoryMapper;

    @Override
    protected LoginHistoryMapper getBaseMapper() {
        return loginHistoryMapper;
    }
}
