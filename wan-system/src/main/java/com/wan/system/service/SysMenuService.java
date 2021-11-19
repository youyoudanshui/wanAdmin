package com.wan.system.service;

import java.util.List;

import com.wan.system.domain.SysMenu;

public interface SysMenuService {

	List<SysMenu> listMenus();
	
	List<SysMenu> listIndexMenus(Long userId);
	
	List<SysMenu> listMenusAndButtons(Long roleId);
	
	SysMenu getMenuById(Long id);
	
	void insertMenu(SysMenu menu);
	
	void updateMenu(SysMenu menu);
	
	void deleteMenu(Long id);
	
}
