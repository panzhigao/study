package com.pan.service;

import java.util.List;
import com.pan.entity.SystemConfig;
import com.pan.vo.SystemConfigParam;

public interface SystemConfigService extends BaseService<SystemConfig>{
	/**
	 * 获取系统参数
	 * @return
	 */
	List<SystemConfigParam> findSystemConfigParamList();
	/**
	 * 编辑系统配置
	 * @param systemConfig
	 */
	void updateSystemConfig(SystemConfig systemConfig);
	
}
