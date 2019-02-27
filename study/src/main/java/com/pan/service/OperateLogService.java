package com.pan.service;

import com.pan.common.enums.OperateLogTypeEnum;
import com.pan.entity.OperateLog;

/**
 * @author panzhigao
 */
public interface OperateLogService extends BaseService<OperateLog>{
    /**
     * 新增操作日志
     * @param content
     * @param operateLogTypeEnum
     * @return
     */
    int addOperateLog(String content, OperateLogTypeEnum operateLogTypeEnum);
}
