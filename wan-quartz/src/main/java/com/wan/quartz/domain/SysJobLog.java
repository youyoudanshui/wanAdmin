package com.wan.quartz.domain;

import com.wan.common.annotation.Excel;
import com.wan.common.domain.BaseDTO;

public class SysJobLog extends BaseDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Excel(Name="任务名称")
	String jobName;
	
	/**
	 * 状态
	 */
	@Excel(Name="执行状态", Dict="0=正常,1=异常")
	String status;
	
	/**
	 * 异常信息
	 */
	@Excel(Name="异常信息")
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

	@Override
	public String toString() {
		return "SysJobLog [jobName=" + jobName + ", status=" + status + ", errorMessage=" + errorMessage + "]";
	}

}
