package com.wan.system.service;

import java.util.List;

import com.wan.system.domain.SysMenu;

public interface SysMenuService {

	List<SysMenu> listMenus();
	
	List<SysMenu> listIndexMenus();
	
	List<SysMenu> listMenusAndButtons();
	
	SysMenu getMenuById(Long id);
	
	void insertMenu(SysMenu menu);
	
	void updateMenu(SysMenu menu);
	
	void deleteMenu(Long id);
	
}
