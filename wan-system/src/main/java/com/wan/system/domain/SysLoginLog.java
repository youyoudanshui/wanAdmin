package com.wan.system.domain;

import com.wan.common.annotation.Excel;
import com.wan.common.domain.BaseDTO;

/**
 * 登录日志类
 * @author wmj
 *
 */
public class SysLoginLog extends BaseDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 登录名
	 */
	@Excel(Name="登录名")
	String loginname;
	
	Long userId;
	
	@Excel(Name="操作IP")
	String ip;
	
	/**
	 * 浏览器userAgent
	 */
	String userAgent;
	
	/**
	 * 浏览器
	 */
	@Excel(Name="浏览器")
	String browser;
	
	/**
	 * 操作系统
	 */
	@Excel(Name="操作系统")
	String operatingSystem;
	
	/**
	 * 登录状态
	 */
	@Excel(Name="登录状态", Dict="0=登录成功,1=登录失败")
	String loginStatus;
	
	/**
	 * 操作信息
	 */
	@Excel(Name="操作信息")
	String message;

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	@Override
	public String toString() {
		return "SysLoginLog [loginname=" + loginname + ", userId=" + userId + ", loginStatus=" + loginStatus
				+ ", message=" + message + ", ip=" + ip + ", userAgent=" + userAgent + ", browser=" + browser
				+ ", operatingSystem=" + operatingSystem + "]";
	}

}
