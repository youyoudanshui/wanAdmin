package com.wan.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wan.common.domain.BaseDTO;

/**
 * 用户通知类
 * @author wmj
 *
 */
public class SysUserNotice extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Long userId;
	
	Long noticeId;
	
	/*标题*/
	String title;
	
	/*内容*/
	String content;
	
	/*是否已读*/
	String isRead;
	
	/*发送时间*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	String gmtRead;
	
	/*是否删除*/
	String isDeleted;
	
	/*删除时间*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	String gmtDeleted;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}

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

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	public String getGmtRead() {
		return gmtRead;
	}

	public void setGmtRead(String gmtRead) {
		this.gmtRead = gmtRead;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getGmtDeleted() {
		return gmtDeleted;
	}

	public void setGmtDeleted(String gmtDeleted) {
		this.gmtDeleted = gmtDeleted;
	}

	@Override
	public String toString() {
		return "SysUserNotice [userId=" + userId + ", noticeId=" + noticeId + ", title=" + title + ", content="
				+ content + ", isRead=" + isRead + ", gmtRead=" + gmtRead + ", isDeleted=" + isDeleted + ", gmtDeleted="
				+ gmtDeleted + "]";
	}
	
}
