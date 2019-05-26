package com.pan.service.impl;

import com.pan.entity.LoginHistory;
import com.pan.mapper.LoginHistoryMapper;
import com.pan.query.QueryLoginHistory;
import com.pan.service.AbstractBaseService;
import com.pan.service.ILoginHistoryService;
import com.pan.util.BeanUtils;
import com.pan.util.IPUtils;
import com.pan.vo.LoginHistoryVO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author panzhigao
 */
@Service
public class LoginHistoryServiceImpl extends AbstractBaseService<LoginHistory,LoginHistoryMapper> implements ILoginHistoryService{

    @Autowired
    private LoginHistoryMapper loginHistoryMapper;

    @Override
    protected LoginHistoryMapper getBaseMapper() {
        return loginHistoryMapper;
    }

	@Override
	public List<LoginHistoryVO> findVOPageable(QueryLoginHistory queryLoginHistory) {
		List<LoginHistory> historyList = loginHistoryMapper.findPageable(queryLoginHistory);
		List<LoginHistoryVO> resultList=new ArrayList<LoginHistoryVO>(historyList.size());
		historyList.forEach(one->{
			LoginHistoryVO vo=new LoginHistoryVO();
			BeanUtils.copyProperties(one, vo);
			vo.setIpStr(IPUtils.integer2Ip(one.getIp()));
			resultList.add(vo);
		});
		return resultList;
	}
}
