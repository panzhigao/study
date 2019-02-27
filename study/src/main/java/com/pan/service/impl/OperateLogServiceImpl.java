package com.pan.service.impl;

import com.pan.common.enums.OperateLogTypeEnum;
import com.pan.entity.OperateLog;
import com.pan.entity.User;
import com.pan.mapper.BaseMapper;
import com.pan.mapper.OperateLogMapper;
import com.pan.service.AbstractBaseService;
import com.pan.service.OperateLogService;
import com.pan.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author panzhigao
 */
@Service
public class OperateLogServiceImpl extends AbstractBaseService<OperateLog,OperateLogMapper> implements OperateLogService {

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Override
    protected BaseMapper getBaseMapper() {
        return operateLogMapper;
    }

    @Override
    public int addOperateLog(String content, OperateLogTypeEnum operateLogTypeEnum) {
        User loginUser = TokenUtils.getLoginUser();
        StringBuilder stringBuilder=new StringBuilder(loginUser.getNickname()+"("+loginUser.getUsername()+")");
        stringBuilder.append(operateLogTypeEnum.getDesc()).append(",");
        stringBuilder.append(content);
        OperateLog operateLog=new OperateLog();
        operateLog.setUserId(loginUser.getUserId());
        operateLog.setUsername(loginUser.getUsername());
        operateLog.setContent(stringBuilder.toString());
        operateLog.setOperateType(operateLogTypeEnum.getCode());
        operateLog.setIpStr(TokenUtils.getIp());
        operateLog.setCreateTime(new Date());
        return operateLogMapper.insertSelective(operateLog);
    }
}
