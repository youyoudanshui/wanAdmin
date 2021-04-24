package com.wan.quartz.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.wan.common.util.StringUtil;
import com.wan.quartz.domain.SysJobLog;
import com.wan.quartz.service.SysJobLogService;

public class TestJob extends QuartzJobBean {
	
	@Autowired
	private SysJobLogService jobLogService;

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		
		SysJobLog jobLog = new SysJobLog();
		jobLog.setJobName(arg0.getJobDetail().getKey().getName());
		
		try {
			
			// 任务内容
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println(sdf.format(new Date()) + "：---------执行一次定时任务---------");
			
		} catch (Exception e) {
			
			String errorMessage = StringUtil.stackTraceToString(e);
			jobLog.setStatus("1");
			jobLog.setErrorMessage(errorMessage);
			
		}
		
		jobLogService.insertJobLog(jobLog);
		
	}

}
