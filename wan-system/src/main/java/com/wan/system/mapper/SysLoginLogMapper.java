package com.wan.system.mapper;

import java.util.List;

import com.wan.system.domain.SysLoginLog;

public interface SysLoginLogMapper {
	
	/**
	 * 获取日志列表
	 * @return
	 */
	List<SysLoginLog> listLoginLogs(SysLoginLog loginLog);
	
	/**
	 * 新增日志
	 * @param loginLog
	 */
	void insertLoginLog(SysLoginLog loginLog);
	
	/**
	 * 日志保留三个月，删除三个月前日志
	 */
	void deleteExpiredLoginLogs();

}
