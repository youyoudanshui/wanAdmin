package com.wan.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wan.system.domain.SysNotice;
import com.wan.system.mapper.SysNoticeMapper;
import com.wan.system.mapper.SysUserNoticeMapper;
import com.wan.system.service.SysNoticeService;

@Service
public class SysNoticeServiceImpl implements SysNoticeService {
	
	@Autowired
	private SysNoticeMapper noticeMapper;
	
	@Autowired
	private SysUserNoticeMapper userNoticeMapper;

	@Override
	public List<SysNotice> listNotices(SysNotice notice) {
		return noticeMapper.listNotices(notice);
	}

	@Override
	public SysNotice getNoticeById(Long id) {
		return noticeMapper.getNoticeById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertNotice(SysNotice notice) {
		noticeMapper.insertNotice(notice);
		noticeMapper.insertNoticeContent(notice);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateNotice(SysNotice notice) {
		noticeMapper.updateNotice(notice);
		noticeMapper.updateNoticeContent(notice);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteNotice(Long id) {
		noticeMapper.deleteNotice(id);
		noticeMapper.deleteNoticeContent(id);
		userNoticeMapper.deleteUserNoticesByNoticeId(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void sendNotice(Long id) {
		userNoticeMapper.insertUserNotices(id);
		
		// 更新发送状态
		SysNotice notice = new SysNotice();
		notice.setId(id);
		notice.setIsSent("1");
		noticeMapper.updateNotice(notice);
	}

}
