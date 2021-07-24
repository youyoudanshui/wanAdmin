package com.wan.system.domain;

import com.wan.common.annotation.Excel;
import com.wan.common.domain.BaseDTO;

/**
 * 日志类
 * @author wmj
 *
 */
public class SysLog extends BaseDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 状态
	 */
	String status;
	
	/**
	 * 业务名称
	 */
	@Excel(Name="业务名称")
	String businessName;
	
	/**
	 * 操作类型
	 */
	@Excel(Name="操作类型", Dict="INSERT=新增,UPDATE=修改,DELETE=删除,LOGIN=登录,LOGOUT=登出,UNKNOWN=未知")
	String operationType;
	
	/**
	 * 内容
	 */
	@Excel(Name="内容")
	String content;
	
	/**
	 * 请求方法
	 */
	@Excel(Name="请求方法")
	String method;
	
	/**
	 * 请求地址
	 */
	@Excel(Name="请求URI")
	String url;
	
	/**
	 * 请求参数
	 */
	@Excel(Name="请求参数")
	String requestParam;
	
	/**
	 * 返回参数
	 */
	@Excel(Name="返回参数")
	String responseParam;
	
	/**
	 * 操作人员
	 */
	Long operator;
	
	@Excel(Name="操作人员")
	String operatorName;
	
	@Excel(Name="请求IP")
	String ip;
	
	/**
	 * 异常信息
	 */
	@Excel(Name="异常信息")
	String errorMessage;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRequestParam() {
		return requestParam;
	}

	public void setRequestParam(String requestParam) {
		this.requestParam = requestParam;
	}

	public String getResponseParam() {
		return responseParam;
	}

	public void setResponseParam(String responseParam) {
		this.responseParam = responseParam;
	}

	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	@Override
	public String toString() {
		return "SysLog [status=" + status + ", businessName=" + businessName + ", operationType=" + operationType
				+ ", content=" + content + ", method=" + method + ", url=" + url + ", requestParam=" + requestParam
				+ ", responseParam=" + responseParam + ", operator=" + operator + ", operatorName=" + operatorName
				+ ", ip=" + ip + ", errorMessage=" + errorMessage + "]";
	}

}
