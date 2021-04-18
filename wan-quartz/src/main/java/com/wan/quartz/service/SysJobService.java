package com.wan.quartz.service;

import java.util.List;

import org.quartz.SchedulerException;

import com.wan.quartz.domain.SysJob;

public interface SysJobService {
	
	List<SysJob> listJobs(SysJob job);
	
	SysJob getJobById(Long id);
	
	SysJob getJobByJobName(String jobName);
	
	void insertJob(SysJob job) throws ClassNotFoundException, SchedulerException;
	
	void updateJob(SysJob job) throws SchedulerException;
	
	void updateJobStatus(SysJob job) throws SchedulerException;
	
	void deleteJob(Long id) throws SchedulerException;
	
	void runOnce(Long id) throws SchedulerException;
	
}
