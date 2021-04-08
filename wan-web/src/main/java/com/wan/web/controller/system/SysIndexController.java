package com.wan.web.controller.system;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.wan.security.util.SecurityUtil;
import com.wan.system.domain.SysMenu;
import com.wan.system.domain.SysRoleRule;
import com.wan.system.domain.SysUser;
import com.wan.system.service.SysMenuService;
import com.wan.system.service.SysRoleRuleService;
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
	
	@Autowired
	private SysRoleRuleService roleRuleService;
	
	@GetMapping("/index")
	public String index(ModelMap map) {
		getConfig(map);
		
		// 获取菜单列表
		List<SysMenu> menuList = menuService.listIndexMenus();
		map.put("menuList", menuList);
		
		// 用户信息
		SysUser user = SecurityUtil.getAuthUser();
		map.put("user", user);
		
		// 权限
		List<SysRoleRule> roleRuleList = roleRuleService.listRoleRulesByUserId(user.getId());
		String[] rules = new String[roleRuleList.size()];
		for (int i = 0; i < roleRuleList.size(); i ++) {
			rules[i] = roleRuleList.get(i).getPermissionValue();
		}
		map.put("rules", rules);
		
		return "index";
	}

	@GetMapping("/main")
	public String main() {
		return "main";
	}
}
