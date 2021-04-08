package com.wan.system.service;

import java.util.List;

import com.wan.system.domain.SysNotice;

public interface SysNoticeService {
	
	List<SysNotice> listNotices(SysNotice notice);
	
	SysNotice getNoticeById(Long id);
	
	void insertNotice(SysNotice notice);
	
	void updateNotice(SysNotice notice);
	
	void deleteNotice(Long id);
	
	void sendNotice(Long id);
	
}
