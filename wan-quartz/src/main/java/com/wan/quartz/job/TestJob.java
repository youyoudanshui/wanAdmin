package com.wan.quartz.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.wan.common.annotation.JobLog;

public class TestJob extends QuartzJobBean {

	@Override
	@JobLog()
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(new Date()) + "：---------执行一次定时任务---------");
		
	}

}
