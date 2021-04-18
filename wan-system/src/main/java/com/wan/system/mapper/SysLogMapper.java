package com.wan.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wan.system.domain.SysLog;

public interface SysLogMapper {
	
	/**
	 * 获取日志列表
	 * @return
	 */
	List<SysLog> listLogs(SysLog log);
	
	/**
	 * 新增日志
	 * @param log
	 */
	void insertLog(SysLog log);
	
	/**
	 * 新增异常日志
	 * @param log
	 */
	void insertErrorLog(@Param("logId") Long logId, @Param("errorMessage") String errorMessage);
	
	/**
	 * 根据id获取日志
	 * @param id
	 * @return
	 */
	SysLog getLog(Long id);
	
	/**
	 * 正常执行日志保留三个月，删除三个月前日志
	 */
	void deleteExpiredLogs();
	
}
