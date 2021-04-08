package com.wan.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wan.common.aop.Log;
import com.wan.common.domain.Result;
import com.wan.common.enumerate.OperationType;
import com.wan.common.util.ResultUtil;
import com.wan.system.domain.SysConfig;
import com.wan.system.service.SysConfigService;

/**
 * 系统设置——网站配置
 * @author wmj
 *
 */
@Controller
@RequestMapping("/system/config")
public class SysConfigController {
	
	@Autowired
	private SysConfigService configService;
	
	private String prefix = "system/config";
	
	@GetMapping("")
	@PreAuthorize("hasAuthority('open:config:manage')")
	public String config(ModelMap map) {
		SysConfig config = configService.getConfig();
		map.put("config", config);
		return prefix + "/config";
	}
	
	@GetMapping("/get/{configKey}")
	@ResponseBody
	public Result get(@PathVariable("configKey") String configKey) {
		String configValue = configService.getConfigValue(configKey);
		return ResultUtil.success(configValue);
	}
	
	@PostMapping("/update")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:config:manage')")
	@Log(BusinessName = "网站配置", OperationType = OperationType.UPDATE, Content = "修改配置")
	public Result update(SysConfig config) throws IllegalArgumentException, IllegalAccessException {
		configService.updateConfigs(config);
		return ResultUtil.success();
	}

}
