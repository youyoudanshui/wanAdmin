package com.wan.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.wan.common.enumerate.OperationType;
import com.wan.system.domain.SysLog;
import com.wan.system.service.SysLogService;

public class LogJob extends QuartzJobBean {
	
	@Autowired
	private SysLogService logService;

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		
		logService.deleteExpiredLogs();
		
		// 保存日志
		SysLog sysLog = new SysLog();
		sysLog.setBusinessName("运行定时任务");
		sysLog.setOperationType(OperationType.DELETE.toString());
		sysLog.setContent("删除过期日志");
		sysLog.setMethod(this.getClass().getName() + ".executeInternal");
		
		logService.insertLog(sysLog);
		
	}

}
