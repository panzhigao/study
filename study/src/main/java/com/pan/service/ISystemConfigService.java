package com.pan.service;

import com.pan.entity.SystemConfig;

/**
 * @author panzhigao
 */
public interface ISystemConfigService extends BaseService<SystemConfig>{
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
