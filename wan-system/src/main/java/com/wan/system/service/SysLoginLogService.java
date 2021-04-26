package com.wan.system.service;

import java.util.List;

import com.wan.system.domain.SysLoginLog;

public interface SysLoginLogService {
	
	List<SysLoginLog> listLoginLogs(SysLoginLog loginLog);
	
	void insertLoginLog(SysLoginLog loginLog);
	
	void deleteExpiredLoginLogs();
	
}
