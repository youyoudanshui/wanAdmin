package com.wan.web.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.wan.common.domain.Page;
import com.wan.system.domain.SysLog;
import com.wan.system.domain.SysLoginLog;
import com.wan.system.service.SysLogService;
import com.wan.system.service.SysLoginLogService;
import com.wan.web.controller.common.BaseController;

/**
 * 日志
 * @author wmj
 *
 */
@Controller
@RequestMapping("/system/log")
public class SysLogController extends BaseController {
	
	@Autowired
	private SysLogService logService;
	
	@Autowired
	private SysLoginLogService loginLogService;
	
	private String prefix = "system/log";
	
	@GetMapping("/operLog")
	@PreAuthorize("hasAuthority('open:log:manage')")
	public String operLog() {
		return prefix + "/operLog";
	}
	
	@GetMapping("/loginLog")
	@PreAuthorize("hasAuthority('open:log:manage')")
	public String loginLog() {
		return prefix + "/loginLog";
	}
	
	@GetMapping("/errorLog")
	@PreAuthorize("hasAuthority('open:log:manage')")
	public String errorLog() {
		return prefix + "/errorLog";
	}
	
	@GetMapping("/list")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:log:manage')")
	public PageInfo<SysLog> list(SysLog log, Page page) {
		startPage(page);
		List<SysLog> list = logService.listLogs(log);
		PageInfo<SysLog> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	@GetMapping("/loginList")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:log:manage')")
	public PageInfo<SysLoginLog> loginList(SysLoginLog loginLog, Page page) {
		startPage(page);
		List<SysLoginLog> list = loginLogService.listLoginLogs(loginLog);
		PageInfo<SysLoginLog> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	@GetMapping("/detail/{id}")
	@PreAuthorize("hasAuthority('open:log:manage')")
	public String detail(@PathVariable("id") Long id, ModelMap map) {
		SysLog log = logService.getLog(id);
		map.put("log", log);
		return prefix + "/detail";
	}

}
