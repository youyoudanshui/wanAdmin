package com.wan.web.controller.system;

import java.util.List;

import javax.validation.Valid;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.wan.common.annotation.Log;
import com.wan.common.domain.Page;
import com.wan.common.domain.Result;
import com.wan.common.enumerate.OperationType;
import com.wan.common.util.ResultUtil;
import com.wan.quartz.domain.SysJob;
import com.wan.quartz.service.SysJobService;
import com.wan.web.controller.common.BaseController;

/**
 * 定时任务管理
 * @author wmj
 *
 */
@Controller
@RequestMapping("/system/job")
public class SysJobController extends BaseController {
	
	@Autowired
	private SysJobService jobService;
	
	private String prefix = "system/job";
	
	@GetMapping("")
	@PreAuthorize("hasAuthority('open:job:manage')")
	public String job() {
		return prefix + "/job";
	}
	
	@GetMapping("/list")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:job:manage')")
	public PageInfo<SysJob> list(SysJob job, Page page) {
		startPage(page);
		List<SysJob> list = jobService.listJobs(job);
		PageInfo<SysJob> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	@GetMapping("/add")
	@PreAuthorize("hasAuthority('open:job:add')")
	public String add(ModelMap map) {
		return prefix + "/add";
	}

	@GetMapping("/edit/{id}")
	@PreAuthorize("hasAuthority('open:job:edit')")
	public String edit(@PathVariable("id") Long id, ModelMap map) {
		SysJob job = jobService.getJobById(id);
		map.put("job", job);
		return prefix + "/edit";
	}
	
	@PostMapping("/save")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:job:add')")
	@Log(BusinessName = "定时任务管理", OperationType = OperationType.INSERT, Content = "新增类型定时任务")
	public Result save(@Valid SysJob job, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtil.error(bindingResult.getFieldError().getDefaultMessage());
		}
		
		try {
			jobService.insertJob(job);
			return ResultUtil.success();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return ResultUtil.error("定时任务类路径出错：请输入类的绝对路径");
		} catch (SchedulerException e) {
			e.printStackTrace();
			return ResultUtil.error("创建定时任务出错：" + e.getMessage());
		}
	}
	
	@PostMapping("/update")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:job:edit')")
	@Log(BusinessName = "定时任务管理", OperationType = OperationType.UPDATE, Content = "修改定时任务")
	public Result update(@Valid SysJob job, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtil.error(bindingResult.getFieldError().getDefaultMessage());
		}
		
		try {
			jobService.updateJob(job);
			return ResultUtil.success();
		} catch (SchedulerException e) {
			e.printStackTrace();
			return ResultUtil.error("更新定时任务出错：" + e.getMessage());
		}
	}
	
	@PostMapping("/updateStatus")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:job:edit')")
	@Log(BusinessName = "定时任务管理", OperationType = OperationType.UPDATE, Content = "修改定时任务状态")
	public Result updateStatus(SysJob job) {
		try {
			jobService.updateJobStatus(job);
			return ResultUtil.success();
		} catch (SchedulerException e) {
			e.printStackTrace();
			if ("1".equals(job.getStatus())) {
				return ResultUtil.error("启动定时任务出错：" + e.getMessage());
			} else {
				return ResultUtil.error("暂停定时任务出错：" + e.getMessage());
			}
		}
	}
	
	@PostMapping("/remove/{id}")
	@ResponseBody
	@PreAuthorize("hasAuthority('do:job:remove')")
	@Log(BusinessName = "定时任务管理", OperationType = OperationType.DELETE, Content = "删除定时任务")
	public Result remove(@PathVariable("id") Long id) {
		try {
			jobService.deleteJob(id);
			return ResultUtil.success();
		} catch (SchedulerException e) {
			e.printStackTrace();
			return ResultUtil.error("删除定时任务出错：" + e.getMessage());
		}
	}
	
	@RequestMapping("/runOnce/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('do:job:runOnce')")
	@Log(BusinessName = "定时任务管理", OperationType = OperationType.INSERT, Content = "运行一次定时任务")
    public Result runOnce(@PathVariable("id") Long id) {
		try {
			jobService.runOnce(id);
			return ResultUtil.success();
		} catch (SchedulerException e) {
			e.printStackTrace();
			return ResultUtil.error("运行定时任务出错：" + e.getMessage());
		}
    }
	
	@GetMapping("/checkJobName")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:job:add')")
	public boolean checkJobName(String jobName) {
		if (jobService.getJobByJobName(jobName) != null) {
			return false;
		}
		return true;
	}

}
