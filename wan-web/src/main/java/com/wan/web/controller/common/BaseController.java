package com.wan.web.controller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import com.github.pagehelper.PageHelper;
import com.wan.common.domain.Page;
import com.wan.system.domain.SysConfig;
import com.wan.system.service.SysConfigService;

public class BaseController {
	
	@Autowired
	private SysConfigService configService;
	
	/**
	 * 分页
	 * @param page
	 */
	public void startPage(Page page) {
		if (page.getSort() == null) {
			PageHelper.startPage(page.getPage(), page.getLimit());
		} else {
			PageHelper.startPage(page.getPage(), page.getLimit(), page.getSort() + " " + page.getSortOrder());
		}
	}
	
	/**
	 * 获取网站配置信息
	 * @param map
	 */
	public void getConfig(ModelMap map) {
		SysConfig config = configService.getConfig();
		map.put("config", config);
	}
	
}
