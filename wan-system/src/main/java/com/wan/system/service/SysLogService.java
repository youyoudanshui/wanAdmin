package com.wan.system.service;

import java.util.List;

import com.wan.system.domain.SysLog;

public interface SysLogService {
	
	List<SysLog> listLogs(SysLog log);
	
	void insertLog(SysLog log);
	
	void insertErrorLog(Long logId, String errorMessage);
	
	SysLog getLog(Long id);
	
}
