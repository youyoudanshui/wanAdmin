package com.wan.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.wan.common.annotation.JobLog;
import com.wan.system.service.SysUserNoticeService;

/**
 * 删除3个月前的过期用户通知，只删除用户标记删除的
 * @author wmj
 *
 */
public class UserNoticeJob extends QuartzJobBean {
	
	@Autowired
	private SysUserNoticeService userNoticeService;

	@Override
	@JobLog()
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		
		userNoticeService.deleteExpiredUserNotices();
		
	}

}
