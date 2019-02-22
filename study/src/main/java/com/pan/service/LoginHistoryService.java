package com.pan.service;

import com.pan.entity.LoginHistory;

/**
 * @author panzhigao
 */
public interface LoginHistoryService {
    /**
     * 新增登录历史
     * @param loginHistory
     * @return
     */
    int addLoginHistory(LoginHistory loginHistory);
}
