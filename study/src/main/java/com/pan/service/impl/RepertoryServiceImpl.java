package com.pan.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pan.entity.Repertory;
import com.pan.mapper.RepertoryMapper;
import com.pan.service.AbstractBaseService;
import com.pan.service.IRepertoryService;
import com.pan.util.TokenUtils;

@Service
public class RepertoryServiceImpl extends AbstractBaseService<Repertory,RepertoryMapper> implements IRepertoryService{
	
	@Autowired
	private RepertoryMapper repertoryMapper;

	@Override
	public void addRepertory(Repertory repertory) {
		repertory.setCreateTime(new Date());
		repertory.setCreateUserId(TokenUtils.getLoginUserId());
		repertoryMapper.insertSelective(repertory);
	}

}
