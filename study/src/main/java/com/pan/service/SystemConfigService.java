package com.pan.service;

import java.util.List;
import com.pan.entity.SystemConfig;
import com.pan.vo.SystemConfigParam;

/**
 * @author panzhigao
 */
public interface SystemConfigService extends BaseService<SystemConfig>{
	/**
	 * 根据变量名获取唯一变量
	 * @return
	 */
	SystemConfig selectByParamName(String paramName);
	/**
	 * 编辑系统配置
	 * @param systemConfig
	 */
	void updateSystemConfig(SystemConfig systemConfig);
	/**
	 * 新增系统变量
	 * @param systemConfig
	 */
	void addSystemConfig(SystemConfig systemConfig);
	/**
	 * 删除变量
	 * @param configId
	 */
	void deleteSystemConfig(Long configId);
}
