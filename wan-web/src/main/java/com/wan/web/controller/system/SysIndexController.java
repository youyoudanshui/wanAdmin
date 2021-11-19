package com.wan.web.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.wan.security.util.SecurityUtil;
import com.wan.system.domain.SysUser;
import com.wan.web.controller.common.BaseController;

/**
 * 首页
 * @author wmj
 *
 */
@Controller
public class SysIndexController extends BaseController {
	
	@GetMapping("/")
	public String index(ModelMap map) {
		getConfig(map);
		
		// 用户信息
		SysUser user = SecurityUtil.getAuthUser();
		map.put("user", user);
		
		return "index";
	}

	@GetMapping("/main")
	public String main() {
		return "main";
	}
}
