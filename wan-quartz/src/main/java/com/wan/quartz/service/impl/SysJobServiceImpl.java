package com.wan.quartz.service.impl;

import java.util.List;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wan.quartz.domain.SysJob;
import com.wan.quartz.mapper.SysJobMapper;
import com.wan.quartz.service.SysJobService;
import com.wan.quartz.util.JobUtil;

@Service
public class SysJobServiceImpl implements SysJobService {
	
	@Autowired
	private SysJobMapper jobMapper;
	
	//注入任务调度
    @Autowired
    private Scheduler scheduler;

	@Override
	public List<SysJob> listJobs(SysJob job) {
		return jobMapper.listJobs(job);
	}

	@Override
	public SysJob getJobById(Long id) {
		return jobMapper.getJobById(id);
	}

	@Override
	public SysJob getJobByJobName(String jobName) {
		return jobMapper.getJobByJobName(jobName);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertJob(SysJob job) throws ClassNotFoundException, SchedulerException {
		jobMapper.insertJob(job);
		JobUtil.createScheduleJob(scheduler, job);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateJob(SysJob job) throws SchedulerException {
		jobMapper.updateJob(job);
		JobUtil.updateScheduleJob(scheduler, job);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateJobStatus(SysJob job) throws SchedulerException {
		jobMapper.updateJob(job);
		
		String jobName = jobMapper.getJobById(job.getId()).getJobName();
		if ("1".equals(job.getStatus())) {
			JobUtil.resumeScheduleJob(scheduler, jobName);
		} else {
			JobUtil.pauseScheduleJob(scheduler, jobName);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteJob(Long id) throws SchedulerException {
		SysJob job = jobMapper.getJobById(id);
		jobMapper.deleteJob(id);
		JobUtil.deleteScheduleJob(scheduler, job.getJobName());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void runOnce(Long id) throws SchedulerException {
		SysJob job = jobMapper.getJobById(id);
		JobUtil.runOnce(scheduler, job.getJobName());
	}

}
