package com.wan.quartz.domain;

import javax.validation.constraints.NotBlank;

import com.wan.common.domain.BaseDTO;

public class SysJob extends BaseDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /*任务名称 */
	@NotBlank(message = "任务名称是必填字段！")
    String jobName;

    /*任务执行类 */
	@NotBlank(message = "任务执行类是必填字段！")
    String jobClass;

    /*任务状态 启动还是暂停*/
	@NotBlank(message = "任务状态是必填字段！")
    String status;

    /*任务运行时间表达式 */
	@NotBlank(message = "任务运行时间表达式是必填字段！")
    String cronExpression;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	
}
