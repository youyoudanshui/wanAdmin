package com.wan.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.wan.common.util.StringUtil;
import com.wan.quartz.domain.SysJobLog;
import com.wan.quartz.service.SysJobLogService;
import com.wan.system.service.SysLogService;

public class LogJob extends QuartzJobBean {
	
	@Autowired
	private SysLogService logService;
	
	@Autowired
	private SysJobLogService jobLogService;

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		
		SysJobLog jobLog = new SysJobLog();
		jobLog.setJobName(arg0.getJobDetail().getKey().getName());
		
		try {
			
			// 任务内容
			logService.deleteExpiredLogs();
			
		} catch (Exception e) {
			
			String errorMessage = StringUtil.stackTraceToString(e);
			jobLog.setStatus("1");
			jobLog.setErrorMessage(errorMessage);
			
		}
		
		jobLogService.insertJobLog(jobLog);
		
	}

}
