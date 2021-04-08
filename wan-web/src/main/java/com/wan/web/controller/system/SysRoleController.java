package com.wan.web.controller.system;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.wan.common.aop.Log;
import com.wan.common.domain.Page;
import com.wan.common.domain.Result;
import com.wan.common.enumerate.OperationType;
import com.wan.common.util.ResultUtil;
import com.wan.system.domain.SysMenu;
import com.wan.system.domain.SysRole;
import com.wan.system.service.SysMenuService;
import com.wan.system.service.SysRoleRuleService;
import com.wan.system.service.SysRoleService;
import com.wan.web.controller.common.BaseController;

/**
 * 角色管理
 * @author wmj
 *
 */
@Controller
@RequestMapping("/system/role")
public class SysRoleController extends BaseController {
	
	@Autowired
	private SysRoleService roleService;
	
	@Autowired
	private SysRoleRuleService roleRuleService;
	
	@Autowired
	private SysMenuService menuService;
	
	private String prefix = "system/role";
	
	@GetMapping("")
	@PreAuthorize("hasAuthority('open:role:manage')")
	public String role() {
		return prefix + "/role";
	}
	
	@GetMapping("/list")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:role:manage')")
	public PageInfo<SysRole> list(SysRole role, Page page) {
		startPage(page);
		List<SysRole> list = roleService.listRoles(role);
		PageInfo<SysRole> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	@GetMapping("/add")
	@PreAuthorize("hasAuthority('open:role:add')")
	public String add(ModelMap map) {
		return prefix + "/add";
	}

	@GetMapping("/edit/{id}")
	@PreAuthorize("hasAuthority('open:role:edit')")
	public String edit(@PathVariable("id") Long id, ModelMap map) {
		SysRole role = roleService.getRoleById(id);
		map.put("role", role);
		return prefix + "/edit";
	}
	
	@PostMapping("/save")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:role:add')")
	@Log(BusinessName = "角色管理", OperationType = OperationType.INSERT, Content = "新增角色")
	public Result save(@Valid SysRole role, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtil.error(bindingResult.getFieldError().getDefaultMessage());
		}
		
		if (roleService.getRoleByRolename(role.getRolename()) != null) {
			return ResultUtil.error("该角色名已经存在");
		}
		
		roleService.insertRole(role);
		return ResultUtil.success();
	}
	
	@PostMapping("/update")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:role:edit')")
	@Log(BusinessName = "角色管理", OperationType = OperationType.UPDATE, Content = "修改角色")
	public Result update(@Valid SysRole role, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtil.error(bindingResult.getFieldError().getDefaultMessage());
		}
		
		roleService.updateRole(role);
		return ResultUtil.success();
	}
	
	@PostMapping("/remove/{id}")
	@ResponseBody
	@PreAuthorize("hasAuthority('do:role:remove')")
	@Log(BusinessName = "角色管理", OperationType = OperationType.DELETE, Content = "删除角色")
	public Result remove(@PathVariable("id") Long id) {
		roleService.deleteRole(id);
		return ResultUtil.success();
	}
	
	@GetMapping("/rule/{id}")
	@PreAuthorize("hasAuthority('open:role:rule')")
	public String rule(@PathVariable("id") Long roleId, ModelMap map) {
		SysRole role = roleService.getRoleAndRulesById(roleId);
		map.put("role", role);
		
		// 获取菜单列表
		List<SysMenu> menuList = menuService.listMenusAndButtons();
		map.put("menuList", menuList);
		
		return prefix + "/rule";
	}
	
	@PostMapping("/saveRules")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:role:rule')")
	@Log(BusinessName = "角色管理", OperationType = OperationType.INSERT, Content = "设置权限")
	public Result saveRules(SysRole role) {
		roleRuleService.saveRoleRules(role);
		return ResultUtil.success();
	}
	
	@GetMapping("/checkRolename")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:role:add')")
	public boolean checkRolename(String rolename) {
		if (roleService.getRoleByRolename(rolename) != null) {
			return false;
		}
		return true;
	}

}
