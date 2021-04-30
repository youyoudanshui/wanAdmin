package com.wan.system.service;

import java.util.List;

import com.wan.system.domain.SysUserNotice;

public interface SysUserNoticeService {
	
	List<SysUserNotice> listUserNotices(SysUserNotice notice);
	
	List<SysUserNotice> listUnreadUserNotices(Long userId);
	
	int countUnreadUserNotices(Long userId);
	
	SysUserNotice getUserNoticeById(Long id);
	
	void deleteUserNotices(Long[] ids);
	
	void readUserNotice(Long id);
	
	void readAllUserNotice(Long userId);
	
	void deleteExpiredUserNotices();
	
}
