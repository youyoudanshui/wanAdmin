package com.wan.quartz.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wan.common.util.StringUtil;
import com.wan.quartz.domain.SysJobLog;
import com.wan.quartz.service.SysJobLogService;

/**
 * 切面处理类，定时任务执行日志异常日志记录处理
 * @author wmj
 *
 */
@Aspect
@Component
public class SysJobLogAspect {
	
	@Autowired
	private SysJobLogService jobLogService;
	
	/**
	 * 设置操作日志切入点 记录操作日志 在注解的位置切入代码
	 */
	@Pointcut("@annotation(com.wan.common.annotation.JobLog)")
	public void logPointCut() {
	}
	
	/**
	 * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
	 * @param joinPoint
	 * @param keys
	 */
	@AfterReturning(value = "logPointCut()", returning = "result")
	public void doAfterReturning(JoinPoint joinPoint, Object result) {
		saveLog(joinPoint, result, null);
	}
	
	@AfterThrowing(pointcut = "logPointCut()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		saveLog(joinPoint, null, e);
	}
	
	protected void saveLog(JoinPoint joinPoint, Object result, Throwable e) {
		
		SysJobLog sysJobLog = new SysJobLog();
		
		try {
			
			// 获取入参
			Object[] param = joinPoint.getArgs();
			JobExecutionContext jec = (JobExecutionContext) param[0];
			
			sysJobLog.setJobName(jec.getJobDetail().getKey().getName());
			
			// 异常信息
			if (e != null) {
				sysJobLog.setStatus("1");
				String errorMessage = StringUtil.stackTraceToString(e);
				sysJobLog.setErrorMessage(errorMessage);
			}
			
			jobLogService.insertJobLog(sysJobLog);
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

}
