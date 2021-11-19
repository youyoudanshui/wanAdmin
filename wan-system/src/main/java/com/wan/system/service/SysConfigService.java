package com.wan.system.service;

import com.wan.system.domain.SysConfig;

public interface SysConfigService {
	
	SysConfig getConfig();
	
	void updateConfigs(SysConfig config) throws IllegalArgumentException, IllegalAccessException;
	
}
