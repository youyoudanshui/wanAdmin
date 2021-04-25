package com.wan.quartz.domain;

import com.wan.common.domain.BaseDTO;

public class SysJobLog extends BaseDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String jobName;
	
	/**
	 * 状态
	 */
	String status;
	
	/**
	 * 异常信息
	 */
	String errorMessage;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
