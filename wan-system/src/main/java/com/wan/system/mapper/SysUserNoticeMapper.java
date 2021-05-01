package com.wan.system.mapper;

import java.util.List;

import com.wan.system.domain.SysUserNotice;

public interface SysUserNoticeMapper {
	
	/**
	 * 获取用户通知列表
	 * @param notice
	 * @return
	 */
	List<SysUserNotice> listUserNotices(SysUserNotice notice);
	
	/**
	 * 获取用户未读通知列表7条
	 * @param userId
	 * @return
	 */
	List<SysUserNotice> listUnreadUserNotices(Long userId);
	
	/**
	 * 获取用户未读通知数
	 * @param userId
	 * @return
	 */
	int countUnreadUserNotices(Long userId);
	
	/**
	 * 根据id获取用户通知
	 * @param id
	 * @return
	 */
	SysUserNotice getUserNoticeById(Long id);
	
	/**
	 * 新增用户通知
	 * @param noticeId
	 */
	void insertUserNotices(Long noticeId);
	
	/**
	 * 修改用户通知
	 * @param notice
	 */
	void updateUserNotice(SysUserNotice notice);
	
	/**
	 * 根据用户id删除用户通知
	 * @param userId
	 */
	void deleteUserNoticesByUserId(Long userId);
	
	/**
	 * 根据通知id删除用户通知
	 * @param noticeId
	 */
	void deleteUserNoticesByNoticeId(Long noticeId);
	
	/**
	 * 标记全部用户通知已读
	 * @param userId
	 */
	void readAllUserNotice(Long userId);
	
	/**
	 * 删除过期用户通知，仅删除用户标记删除的
	 */
	void deleteExpiredUserNotices();
	
}
