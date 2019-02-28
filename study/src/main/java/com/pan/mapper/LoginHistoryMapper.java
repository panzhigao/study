package com.pan.mapper;


import com.pan.entity.LoginHistory;
import com.pan.query.QueryLoginHistory;

import java.util.List;

/**
 * 
 * @author Administrator
 *
 */
public interface LoginHistoryMapper extends BaseMapper<LoginHistory>{
	/**
	 * 新增登录历史
	 * @param loginHistory
	 * @return
	 */
	int insert(LoginHistory loginHistory);
}
