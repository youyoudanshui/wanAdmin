package com.wan.quartz.service;

import java.util.List;

import com.wan.quartz.domain.SysJobLog;

public interface SysJobLogService {
	
	List<SysJobLog> listJobLogs(SysJobLog logLog);
	
	void insertJobLog(SysJobLog jobLog);
	
	SysJobLog getJobLog(Long id);
	
	void deleteExpiredJobLogs();

}
