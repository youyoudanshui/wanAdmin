package com.wan.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wan.system.domain.SysNotice;
import com.wan.system.domain.SysUserNotice;
import com.wan.system.mapper.SysNoticeMapper;
import com.wan.system.mapper.SysUserNoticeMapper;
import com.wan.system.service.SysUserNoticeService;

@Service
public class SysUserNoticeServiceImpl implements SysUserNoticeService {
	
	@Autowired
	private SysUserNoticeMapper userNoticeMapper;
	
	@Autowired
	private SysNoticeMapper noticeMapper;

	@Override
	public List<SysUserNotice> listUserNotices(SysUserNotice notice) {
		return userNoticeMapper.listUserNotices(notice);
	}

	@Override
	public List<SysUserNotice> listUnreadUserNotices(Long userId) {
		return userNoticeMapper.listUnreadUserNotices(userId);
	}

	@Override
	public int countUnreadUserNotices(Long userId) {
		return userNoticeMapper.countUnreadUserNotices(userId);
	}

	@Override
	public SysUserNotice getUserNoticeById(Long id) {
		SysUserNotice userNotice = userNoticeMapper.getUserNoticeById(id);
		SysNotice notice = noticeMapper.getNoticeContent(userNotice.getNoticeId());
		userNotice.setContent(notice.getContent());
		return userNotice;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteUserNotices(Long[] ids) {
		SysUserNotice notice = new SysUserNotice();
		notice.setIsDeleted("1");
		
		for (Long id: ids) {
			notice.setId(id);
			userNoticeMapper.updateUserNotice(notice);
		}
	}

	@Override
	public void readUserNotice(Long id) {
		SysUserNotice notice = new SysUserNotice();
		notice.setId(id);
		notice.setIsRead("1");
		userNoticeMapper.updateUserNotice(notice);
	}

	@Override
	public void readAllUserNotice(Long userId) {
		userNoticeMapper.readAllUserNotice(userId);
	}

	@Override
	public void deleteExpiredUserNotices() {
		userNoticeMapper.deleteExpiredUserNotices();
	}

}
