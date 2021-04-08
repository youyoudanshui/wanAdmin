package com.wan.system.mapper;

import java.util.List;

import com.wan.system.domain.SysUserNotice;

public interface SysUserNoticeMapper {

	List<SysUserNotice> listUserNotices(SysUserNotice notice);
	
	List<SysUserNotice> listUnreadUserNotices(Long userId);
	
	int countUnreadUserNotices(Long userId);
	
	SysUserNotice getUserNoticeById(Long id);
	
	void insertUserNotices(Long noticeId);
	
	void updateUserNotice(SysUserNotice notice);
	
	void deleteUserNotices(Long noticeId);
	
	void readAllUserNotice(Long userId);
	
}
