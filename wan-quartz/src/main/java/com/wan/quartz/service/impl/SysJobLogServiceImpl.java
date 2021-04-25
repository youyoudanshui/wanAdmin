package com.wan.quartz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wan.quartz.domain.SysJobLog;
import com.wan.quartz.mapper.SysJobLogMapper;
import com.wan.quartz.service.SysJobLogService;

@Service
public class SysJobLogServiceImpl implements SysJobLogService {
	
	@Autowired
	private SysJobLogMapper jobLogMapper;

	@Override
	public List<SysJobLog> listJobLogs(SysJobLog jobLog) {
		return jobLogMapper.listJobLogs(jobLog);
	}

	@Override
	public void insertJobLog(SysJobLog jobLog) {
		jobLogMapper.insertJobLog(jobLog);
	}

	@Override
	public SysJobLog getJobLog(Long id) {
		return jobLogMapper.getJobLog(id);
	}

	@Override
	public void deleteExpiredJobLogs() {
		jobLogMapper.deleteExpiredJobLogs();
	}

}
