package com.wan.web.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import com.wan.common.util.ExcelUtil;
import com.wan.common.util.FileUtil;
import com.wan.quartz.domain.SysJobLog;
import com.wan.quartz.service.SysJobLogService;
import com.wan.web.controller.common.BaseController;

/**
 * 定时任务日志
 * @author wmj
 *
 */
@Controller
@RequestMapping("/system/job/log")
public class SysJobLogController extends BaseController {
	
	@Autowired
	private SysJobLogService jobLogService;
	
	private String prefix = "system/job/log";
	
	@GetMapping("")
	@PreAuthorize("hasAuthority('open:job:manage')")
	public String log() {
		return prefix + "/log";
	}
	
	@GetMapping("/list")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:job:manage')")
	public PageInfo<SysJobLog> list(SysJobLog jobLog, Page page) {
		startPage(page);
		List<SysJobLog> list = jobLogService.listJobLogs(jobLog);
		PageInfo<SysJobLog> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	@GetMapping("/detail/{id}")
	@PreAuthorize("hasAuthority('open:job:manage')")
	public String detail(@PathVariable("id") Long id, ModelMap map) {
		SysJobLog jobLog = jobLogService.getJobLog(id);
		map.put("jobLog", jobLog);
		return prefix + "/detail";
	}
	
	@GetMapping("/export")
	@PreAuthorize("hasAuthority('open:job:manage')")
	public void export(SysJobLog jobLog, String fileName, HttpServletResponse response) {
		List<SysJobLog> list = jobLogService.listJobLogs(jobLog);
		ExcelUtil.exportHtmlExcel(list, SysJobLog.class, response, fileName + FileUtil.getRandomFileName(), null);
	}

}
