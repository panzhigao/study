package com.pan.service.impl;

import com.pan.common.enums.OperateLogTypeEnum;
import com.pan.entity.ExceptionLog;
import com.pan.entity.OperateLog;
import com.pan.mapper.ExceptionLogMapper;
import com.pan.query.QueryExceptionLog;
import com.pan.service.AbstractBaseService;
import com.pan.service.ExceptionLogService;
import com.pan.util.BeanUtils;
import com.pan.util.IPUtils;
import com.pan.util.JsonUtils;
import com.pan.vo.ExceptionLogVO;
import com.pan.vo.OperateLogVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author panzhigao
 */
@Service
public class ExceptionLogServiceImpl extends AbstractBaseService<ExceptionLog, ExceptionLogMapper> implements ExceptionLogService {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionLogServiceImpl.class);

    @Autowired
    private ExceptionLogMapper exceptionLogMapper;

    @Override
    protected ExceptionLogMapper getBaseMapper() {
        return exceptionLogMapper;
    }

    @Override
    public Map<String, Object> findByParams(QueryExceptionLog queryExceptionLog) {
        Map<String, Object> pageData = new HashMap<String, Object>(4);
        List<ExceptionLogVO> list = new ArrayList<>();
        try {
            logger.info("分页查询操作日志参数为:{}", JsonUtils.toJson(queryExceptionLog));
            int total = exceptionLogMapper.countByParams(queryExceptionLog);
            // 当查询记录大于0时，查询数据库记录，否则直接返回空集合
            if (total > 0) {
                List<ExceptionLog> findPageable = exceptionLogMapper.findPageable(queryExceptionLog);
                findPageable.forEach(log->{
                    ExceptionLogVO logVO=new ExceptionLogVO();
                    BeanUtils.copyProperties(log, logVO);
                    logVO.setIpStr(IPUtils.integer2Ip(log.getIp()));
                    list.add(logVO);
                });
            }
            pageData.put("data", list);
            pageData.put("total", total);
            pageData.put("code", "200");
            pageData.put("msg", "");
        } catch (Exception e) {
            logger.error("分页查询操作日志异常", e);
        }
        return pageData;
    }
}
