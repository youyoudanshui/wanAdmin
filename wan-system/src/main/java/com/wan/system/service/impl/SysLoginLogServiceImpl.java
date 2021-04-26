package com.wan.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wan.system.domain.SysLoginLog;
import com.wan.system.mapper.SysLoginLogMapper;
import com.wan.system.service.SysLoginLogService;

@Service
public class SysLoginLogServiceImpl implements SysLoginLogService {
	
	@Autowired
	private SysLoginLogMapper loginLogMapper;

	@Override
	public List<SysLoginLog> listLoginLogs(SysLoginLog loginLog) {
		return loginLogMapper.listLoginLogs(loginLog);
	}

	@Override
	public void insertLoginLog(SysLoginLog loginLog) {
		loginLogMapper.insertLoginLog(loginLog);
	}

	@Override
	public void deleteExpiredLoginLogs() {
		loginLogMapper.deleteExpiredLoginLogs();
	}

}
