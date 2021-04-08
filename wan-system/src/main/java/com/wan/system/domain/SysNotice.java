package com.wan.system.domain;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wan.common.domain.BaseDTO;

/**
 * 通知类
 * @author wmj
 *
 */
public class SysNotice extends BaseDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*标题*/
	@NotBlank(message = "标题是必填字段！")
	String title;
	
	/*内容*/
	@NotBlank(message = "内容是必填字段！")
	String content;
	
	/*是否已发送*/
	String isSent;
	
	/*发送时间*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	String gmtSent;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIsSent() {
		return isSent;
	}

	public void setIsSent(String isSent) {
		this.isSent = isSent;
	}

	public String getGmtSent() {
		return gmtSent;
	}

	public void setGmtSent(String gmtSent) {
		this.gmtSent = gmtSent;
	}

	@Override
	public String toString() {
		return "SysNotice [title=" + title + ", content=" + content + ", isSent=" + isSent + ", gmtSent=" + gmtSent
				+ "]";
	}

}
