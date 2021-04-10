package com.wan.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.wan.system.domain.SysMenu;
import com.wan.system.mapper.SysMenuMapper;
import com.wan.system.service.SysMenuService;

@Service
public class SysMenuServiceImpl implements SysMenuService {
	
	@Autowired
	private SysMenuMapper menuMapper;

	@Override
	public List<SysMenu> listMenus() {
		return menuMapper.listMenus();
	}
	
	@Override
	@Cacheable("indexMenuList")
	public List<SysMenu> listIndexMenus() {
		// 一级菜单
		List<SysMenu> list = menuMapper.listPrimaryMenus();
		for (SysMenu pmenu: list) {
			// 二级菜单
			List<SysMenu> submenus2 = menuMapper.listMenusByPid(pmenu.getId());
			for (SysMenu menu2: submenus2) {
				// 三级菜单
				List<SysMenu> submenus3 = menuMapper.listMenusByPid(menu2.getId());
				menu2.setSubmenus(submenus3);
			}
			pmenu.setSubmenus(submenus2);
		}
		return list;
	}
	
	@Override
	@Cacheable("menuAndButtonList")
	public List<SysMenu> listMenusAndButtons() {
		// 一级菜单
		List<SysMenu> list = menuMapper.listPrimaryMenus();
		for (SysMenu pmenu: list) {
			// 二级菜单
			List<SysMenu> submenus2 = menuMapper.listMenusByPid(pmenu.getId());
			for (SysMenu menu2: submenus2) {
				// 三级菜单
				List<SysMenu> submenus3 = menuMapper.listMenusByPid(menu2.getId());
				for (SysMenu menu3: submenus3) {
					// 四级按钮
					List<SysMenu> submenus4 = menuMapper.listButtonsByPid(menu3.getId());
					menu3.setSubmenus(submenus4);
				}
				
				if (submenus3.size() == 0) {
					submenus3 = menuMapper.listButtonsByPid(menu2.getId());
				}
				menu2.setSubmenus(submenus3);
			}
			
			if (submenus2.size() == 0) {
				submenus2 = menuMapper.listButtonsByPid(pmenu.getId());
			}
			pmenu.setSubmenus(submenus2);
		}
		return list;
	}

	@Override
	public SysMenu getMenuById(Long id) {
		SysMenu menu = menuMapper.getMenuById(id);
		Long pid = menu.getPid();
		if (pid != null) {
			SysMenu pmenu = menuMapper.getMenuById(pid);
			menu.setPname(pmenu.getName());
		}
		return menu;
	}

	@Override
	@CacheEvict(value={"indexMenuList", "menuAndButtonList"}, allEntries=true)
	public void insertMenu(SysMenu menu) {
		menuMapper.insertMenu(menu);
	}

	@Override
	@CacheEvict(value={"indexMenuList", "menuAndButtonList"}, allEntries=true)
	public void updateMenu(SysMenu menu) {
		menuMapper.updateMenu(menu);
	}

	@Override
	@CacheEvict(value={"indexMenuList", "menuAndButtonList"}, allEntries=true)
	public void deleteMenu(Long id) {
		menuMapper.deleteMenu(id);
	}
	
}
