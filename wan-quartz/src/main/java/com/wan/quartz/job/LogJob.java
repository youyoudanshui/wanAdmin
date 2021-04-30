package com.wan.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.wan.common.annotation.JobLog;
import com.wan.quartz.service.SysJobLogService;
import com.wan.system.service.SysLogService;
import com.wan.system.service.SysLoginLogService;

/**
 * 删除3个月前的过期日志，包括登录日志、操作日志、定时任务执行日志
 * @author wmj
 *
 */
public class LogJob extends QuartzJobBean {
	
	@Autowired
	private SysLogService logService;
	
	@Autowired
	private SysLoginLogService loginLogService;
	
	@Autowired
	private SysJobLogService jobLogService;

	@Override
	@JobLog()
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		
		logService.deleteExpiredLogs();
		loginLogService.deleteExpiredLoginLogs();
		jobLogService.deleteExpiredJobLogs();
		
	}

}
