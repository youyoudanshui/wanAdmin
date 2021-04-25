package com.wan.quartz.mapper;

import java.util.List;

import com.wan.quartz.domain.SysJobLog;

public interface SysJobLogMapper {
	
	/**
	 * 获取日志列表
	 * @return
	 */
	List<SysJobLog> listJobLogs(SysJobLog jobLog);
	
	/**
	 * 新增日志
	 * @param log
	 */
	void insertJobLog(SysJobLog jobLog);
	
	/**
	 * 根据id获取日志
	 * @param id
	 * @return
	 */
	SysJobLog getJobLog(Long id);
	
	/**
	 * 日志保留三个月，删除三个月前日志
	 */
	void deleteExpiredJobLogs();

}
