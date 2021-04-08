package com.wan.web.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.wan.common.aop.Log;
import com.wan.common.domain.Page;
import com.wan.common.domain.Result;
import com.wan.common.enumerate.OperationType;
import com.wan.common.util.ResultUtil;
import com.wan.security.util.SecurityUtil;
import com.wan.system.domain.SysUser;
import com.wan.system.domain.SysUserNotice;
import com.wan.system.service.SysUserNoticeService;
import com.wan.web.controller.common.BaseController;

/**
 * 用户通知
 * @author wmj
 *
 */
@Controller
@RequestMapping("/system/user/notice")
public class SysUserNoticeController extends BaseController {
	
	@Autowired
	private SysUserNoticeService userNoticeService;
	
	private String prefix = "system/user/notice";
	
	@GetMapping("")
	public String notice() {
		return prefix + "/notice";
	}
	
	@GetMapping("/list")
	@ResponseBody
	public PageInfo<SysUserNotice> list(SysUserNotice notice, Page page) {
		// 用户信息
		SysUser user = SecurityUtil.getAuthUser();
		notice.setUserId(user.getId());
		
		startPage(page);
		List<SysUserNotice> list = userNoticeService.listUserNotices(notice);
		PageInfo<SysUserNotice> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	@GetMapping("/detail/{id}")
	public String detail(@PathVariable("id") Long id, ModelMap map) {
		SysUserNotice notice = userNoticeService.getUserNoticeById(id);
		map.put("notice", notice);
		return prefix + "/detail";
	}
	
	@GetMapping("/unread")
	@ResponseBody
	public Result unread() {
		// 用户信息
		SysUser user = SecurityUtil.getAuthUser();
		Long userId = user.getId();
		
		List<SysUserNotice> list = userNoticeService.listUnreadUserNotices(userId);
		int count = userNoticeService.countUnreadUserNotices(userId);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);
		data.put("count", count);
		return ResultUtil.success(data);
	}
	
	@PostMapping("/read/{id}")
	@ResponseBody
	@Log(BusinessName = "用户通知", OperationType = OperationType.UPDATE, Content = "已读通知")
	public Result read(@PathVariable("id") Long id) {
		userNoticeService.readUserNotice(id);
		return ResultUtil.success();
	}
	
	@PostMapping("/allRead")
	@ResponseBody
	@Log(BusinessName = "用户通知", OperationType = OperationType.UPDATE, Content = "全部已读")
	public Result allRead() {
		// 用户信息
		SysUser user = SecurityUtil.getAuthUser();
		Long userId = user.getId();
		
		userNoticeService.readAllUserNotice(userId);
		return ResultUtil.success();
	}
	
	@PostMapping("/remove")
	@ResponseBody
	@Log(BusinessName = "用户通知", OperationType = OperationType.UPDATE, Content = "删除通知")
	public Result remove(Long[] ids) {
		userNoticeService.deleteUserNotices(ids);
		return ResultUtil.success();
	}

}
