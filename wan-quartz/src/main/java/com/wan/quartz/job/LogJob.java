package com.wan.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.wan.common.annotation.JobLog;
import com.wan.quartz.service.SysJobLogService;
import com.wan.system.service.SysLogService;

public class LogJob extends QuartzJobBean {
	
	@Autowired
	private SysLogService logService;
	
	@Autowired
	private SysJobLogService jobLogService;

	@Override
	@JobLog()
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		
		logService.deleteExpiredLogs();
		jobLogService.deleteExpiredJobLogs();
		
	}

}
