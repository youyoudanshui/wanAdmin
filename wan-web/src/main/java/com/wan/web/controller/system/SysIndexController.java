package com.wan.web.controller.system;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.wan.security.util.SecurityUtil;
import com.wan.system.domain.SysMenu;
import com.wan.system.domain.SysUser;
import com.wan.system.service.SysMenuService;
import com.wan.web.controller.common.BaseController;

/**
 * 首页
 * @author wmj
 *
 */
@Controller
public class SysIndexController extends BaseController {
	
	@Autowired
	private SysMenuService menuService;
	
	@GetMapping("/index")
	public String index(ModelMap map) {
		getConfig(map);
		
		// 获取菜单列表
		List<SysMenu> menuList = menuService.listIndexMenus();
		map.put("menuList", menuList);
		
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
