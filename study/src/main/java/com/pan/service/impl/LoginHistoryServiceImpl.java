package com.pan.service.impl;

import com.pan.entity.LoginHistory;
import com.pan.mapper.LoginHistoryMapper;
import com.pan.service.LoginHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author panzhigao
 */
@Service
public class LoginHistoryServiceImpl implements LoginHistoryService{

    @Autowired
    private LoginHistoryMapper loginHistoryMapper;

    /**
     * 新增登录历史
     * @param loginHistory
     * @return
     */
    @Override
    public int addLoginHistory(LoginHistory loginHistory) {
        return loginHistoryMapper.insert(loginHistory);
    }
}
