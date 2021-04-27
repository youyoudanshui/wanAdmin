package com.wan.system.mapper;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.wan.system.domain.SysNotice;

public interface SysNoticeMapper {
	
	/**
	 * 获取通知列表
	 * @return
	 */
	List<SysNotice> listNotices(SysNotice notice);
	
	/**
	 * 根据id获取通知
	 * @param id
	 * @return
	 */
	SysNotice getNoticeById(Long id);
	
	/**
	 * 新增通知
	 * @param notice
	 */
	void insertNotice(SysNotice notice);
	
	/**
	 * 修改通知
	 * @param notice
	 */
	void updateNotice(SysNotice notice);
	
	/**
	 * 删除通知
	 * @param id
	 */
	void deleteNotice(Long id);
	
	/**
	 * 获取通知内容
	 * @param noticeId
	 * @return
	 */
	@Cacheable(value={"notice"}, key="#p0")
	SysNotice getNoticeContent(Long noticeId);
	
	/**
	 * 新增通知内容
	 * @param notice
	 */
	void insertNoticeContent(SysNotice notice);
	
	/**
	 * 修改通知内容
	 * @param notice
	 */
	@CacheEvict(value={"notice"}, key="#p0.id")
	void updateNoticeContent(SysNotice notice);
	
	/**
	 * 删除通知内容
	 * @param noticeId
	 */
	@CacheEvict(value={"notice"}, key="#p0")
	void deleteNoticeContent(Long noticeId);
	
}
