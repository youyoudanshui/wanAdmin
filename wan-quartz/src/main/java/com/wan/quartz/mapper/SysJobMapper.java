package com.wan.quartz.mapper;

import java.util.List;

import com.wan.quartz.domain.SysJob;

public interface SysJobMapper {
	
	/**
	 * 获取定时任务列表
	 * @return
	 */
	List<SysJob> listJobs(SysJob job);
	
	/**
	 * 根据id获取定时任务
	 * @param id
	 * @return
	 */
	SysJob getJobById(Long id);
	
	/**
	 * 根据任务名称获取定时任务
	 * @param jobName
	 * @return
	 */
	SysJob getJobByJobName(String jobName);
	
	/**
	 * 新增定时任务
	 * @param job
	 */
	void insertJob(SysJob job);
	
	/**
	 * 修改定时任务
	 * @param job
	 */
	void updateJob(SysJob job);
	
	/**
	 * 删除定时任务
	 * @param id
	 */
	void deleteJob(Long id);
	
}
