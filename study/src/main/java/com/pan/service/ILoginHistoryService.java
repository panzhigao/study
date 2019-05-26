package com.pan.service;

import java.util.List;

import com.pan.entity.LoginHistory;
import com.pan.query.QueryLoginHistory;
import com.pan.vo.LoginHistoryVO;

/**
 * @author panzhigao
 */
public interface ILoginHistoryService extends BaseService<LoginHistory>{
	
	List<LoginHistoryVO> findVOPageable(QueryLoginHistory queryLoginHistory);
	
}
