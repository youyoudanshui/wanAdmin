package com.wan.system.service.impl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.wan.system.domain.SysConfig;
import com.wan.system.mapper.SysConfigMapper;
import com.wan.system.service.SysConfigService;

@Service
public class SysConfigServiceImpl implements SysConfigService {
	
	@Autowired
	private SysConfigMapper configMapper;

	@Override
	@Cacheable("config")
	public SysConfig getConfig() {
		Map<String, String> configMap = new HashMap<String, String>();
		
		List<Map<String, String>> list = configMapper.listConfigs();
		for (Map<String, String> map: list) {
			configMap.put(map.get("config_key"), map.get("config_value"));
		}
		
		SysConfig config = new SysConfig(configMap);
		return config;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value="config", allEntries=true)
	public void updateConfigs(SysConfig config) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = config.getClass().getDeclaredFields();
		
		for (Field field : fields) {
			ReflectionUtils.makeAccessible(field);
			
			String configKey = field.getName();
			
			if ("serialVersionUID".equals(configKey) || field.get(config) == null) continue;
			
			String configValue = field.get(config).toString();
			Map<String, String> configMap = configMapper.getConfigByConfigKey(configKey);
			
			if (configMap == null) {
				// 不存在该参数时新增
				configMapper.insertConfig(configKey, configValue);
			} else {
				// 值改变时更新
				if (!configValue.equals(configMap.get("config_value"))) {
					configMapper.updateConfig(configKey, configValue);
				}
			}
		}
	}
	
}
