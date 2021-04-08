package com.wan.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SysConfigMapper {
	
	/**
	 * 获取系统配置列表
	 * @return
	 */
	List<Map<String, String>> listConfigs();
	
	/**
	 * 获取系统配置参数值
	 * @param configKey
	 * @return
	 */
	String getConfigValue(String configKey);
	
	/**
	 * 修改系统配置
	 * @param configKey
	 * @param configValue
	 */
	void updateConfig(@Param("configKey") String configKey, @Param("configValue") String configValue);
	
}
