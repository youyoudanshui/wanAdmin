package com.wan.web.controller.system;

import java.util.List;

import javax.validation.Valid;

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

import com.wan.common.aop.Log;
import com.wan.common.domain.Result;
import com.wan.common.enumerate.OperationType;
import com.wan.common.util.ResultUtil;
import com.wan.system.domain.SysMenu;
import com.wan.system.service.SysMenuService;

/**
 * 系统设置——菜单管理
 * @author wmj
 *
 */
@Controller
@RequestMapping("/system/menu")
public class SysMenuController {
	
	@Autowired
	private SysMenuService menuService;
	
	private String prefix = "system/menu";
	
	@GetMapping()
	@PreAuthorize("hasAuthority('open:menu:manage')")
	public String menu() {
		return prefix + "/menu";
	}
	
	@GetMapping("/list")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:menu:manage')")
	public List<SysMenu> list() {
		List<SysMenu> list = menuService.listMenus();
		return list;
	}
	
	@GetMapping("/add/{pid}")
	@PreAuthorize("hasAuthority('open:menu:add')")
	public String add(@PathVariable("pid") Long pid, ModelMap map) {
		SysMenu pmenu;
		if (pid == -1) {
			pmenu = new SysMenu();
		} else {
			pmenu = menuService.getMenuById(pid);
		}
		map.put("pmenu", pmenu);
		return prefix + "/add";
	}

	@GetMapping("/edit/{id}")
	@PreAuthorize("hasAuthority('open:menu:edit')")
	public String edit(@PathVariable("id") Long id, ModelMap map) {
		SysMenu menu = menuService.getMenuById(id);
		map.put("menu", menu);
		return prefix + "/edit";
	}
	
	@PostMapping("/save")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:menu:add')")
	@Log(BusinessName = "菜单管理", OperationType = OperationType.INSERT, Content = "新增菜单")
	public Result save(@Valid SysMenu menu, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtil.error(bindingResult.getFieldError().getDefaultMessage());
		}
		
		menuService.insertMenu(menu);
		return ResultUtil.success();
	}
	
	@PostMapping("/update")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:menu:edit')")
	@Log(BusinessName = "菜单管理", OperationType = OperationType.UPDATE, Content = "修改菜单")
	public Result update(@Valid SysMenu menu, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtil.error(bindingResult.getFieldError().getDefaultMessage());
		}
		
		menuService.updateMenu(menu);
		return ResultUtil.success();
	}
	
	@PostMapping("/updateStatus")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:menu:edit')")
	@Log(BusinessName = "菜单管理", OperationType = OperationType.UPDATE, Content = "修改菜单状态")
	public Result updateStatus(SysMenu menu) {
		menuService.updateMenu(menu);
		return ResultUtil.success();
	}
	
	@PostMapping("/remove/{id}")
	@ResponseBody
	@PreAuthorize("hasAuthority('do:menu:remove')")
	@Log(BusinessName = "菜单管理", OperationType = OperationType.DELETE, Content = "删除菜单")
	public Result remove(@PathVariable("id") Long id) {
		menuService.deleteMenu(id);
		return ResultUtil.success();
	}
	
	@GetMapping("/removeCache")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:menu:manage')")
	@CacheEvict(value={"indexMenuList", "menuAndButtonList"}, allEntries=true)
	public Result removeCache() {
		return ResultUtil.success();
	}

}
