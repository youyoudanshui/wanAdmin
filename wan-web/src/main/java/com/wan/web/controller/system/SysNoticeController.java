package com.wan.web.controller.system;

import java.util.List;

import javax.validation.Valid;

import net.dreamlu.mica.core.utils.Charsets;
import net.dreamlu.mica.xss.core.XssCleanIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.wan.common.annotation.Log;
import com.wan.common.domain.Page;
import com.wan.common.domain.Result;
import com.wan.common.enumerate.OperationType;
import com.wan.common.util.ResultUtil;
import com.wan.common.websocket.WebSocket;
import com.wan.system.domain.SysNotice;
import com.wan.system.service.SysNoticeService;
import com.wan.web.controller.common.BaseController;
import org.springframework.web.util.HtmlUtils;

/**
 * 通知
 * @author wmj
 *
 */
@Controller
@RequestMapping("/system/notice")
public class SysNoticeController extends BaseController {
	
	@Autowired
	private SysNoticeService noticeService;
	
	@Autowired
	private WebSocket webSocket;
	
	private String prefix = "system/notice";
	
	@GetMapping("")
	@PreAuthorize("hasAuthority('open:notice:manage')")
	public String notice() {
		return prefix + "/notice";
	}
	
	@GetMapping("/list")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:notice:manage')")
	public PageInfo<SysNotice> list(SysNotice notice, Page page) {
		startPage(page);
		List<SysNotice> list = noticeService.listNotices(notice);
		PageInfo<SysNotice> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	@GetMapping("/add")
	@PreAuthorize("hasAuthority('open:notice:manage')")
	public String add() {
		return prefix + "/add";
	}

	@GetMapping("/edit/{id}")
	@PreAuthorize("hasAuthority('open:notice:manage')")
	public String edit(@PathVariable("id") Long id, ModelMap map) {
		SysNotice notice = noticeService.getNoticeById(id);
		notice.setContent(HtmlUtils.htmlEscape(notice.getContent(), Charsets.UTF_8_NAME));
		map.put("notice", notice);
		
		return prefix + "/edit";
	}
	
	@PostMapping("/save")
	@ResponseBody
	@PreAuthorize("hasAuthority('do:notice:add')")
	@Log(BusinessName = "通知管理", OperationType = OperationType.INSERT, Content = "新增通知")
	@XssCleanIgnore
	public Result save(@Valid SysNotice notice, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtil.error(bindingResult.getFieldError().getDefaultMessage());
		}
		
		noticeService.insertNotice(notice);
		return ResultUtil.success();
	}
	
	@PostMapping("/update")
	@ResponseBody
	@PreAuthorize("hasAuthority('do:notice:edit')")
	@Log(BusinessName = "通知管理", OperationType = OperationType.UPDATE, Content = "修改通知")
	@XssCleanIgnore
	public Result update(@Valid SysNotice notice, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtil.error(bindingResult.getFieldError().getDefaultMessage());
		}
		
		noticeService.updateNotice(notice);
		return ResultUtil.success();
	}
	
	@PostMapping("/send/{id}")
	@ResponseBody
	@PreAuthorize("hasAuthority('do:notice:send')")
	@Log(BusinessName = "通知管理", OperationType = OperationType.INSERT, Content = "发送通知")
	public Result send(@PathVariable("id") Long id) {
		SysNotice notice = noticeService.getNoticeById(id);
		if ("1".equals(notice.getIsSent())) {
			return ResultUtil.error("已向用户发送该条通知");
		}
		
		noticeService.sendNotice(id);
		
		// 向客户端推送消息
		webSocket.sendMessage("getUnreadNotices");
		
		return ResultUtil.success();
	}
	
	@PostMapping("/remove/{id}")
	@ResponseBody
	@PreAuthorize("hasAuthority('do:notice:remove')")
	@Log(BusinessName = "通知管理", OperationType = OperationType.DELETE, Content = "删除通知")
	public Result remove(@PathVariable("id") Long id) {
		SysNotice notice = noticeService.getNoticeById(id);
		
		noticeService.deleteNotice(id);
		
		if ("1".equals(notice.getIsSent())) {
			// 向客户端推送消息
			webSocket.sendMessage("getUnreadNotices");
		}
		
		return ResultUtil.success();
	}
	
	@GetMapping("/removeCache")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:notice:manage')")
	@CacheEvict(value="notice", allEntries=true)
	public Result removeCache() {
		return ResultUtil.success();
	}

}
