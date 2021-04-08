package com.wan.system.mapper;

import java.util.List;

import com.wan.system.domain.SysMenu;

public interface SysMenuMapper {

	/**
	 * 获取所有菜单列表
	 * @return
	 */
	List<SysMenu> listMenus();
	
	/**
	 * 获取一级菜单列表
	 * @return
	 */
	List<SysMenu> listPrimaryMenus();
	
	/**
	 * 根据父级菜单ID获取子菜单列表
	 * @param pid
	 * @return
	 */
	List<SysMenu> listMenusByPid(Long pid);
	
	/**
	 * 根据父级菜单ID获取子按钮列表
	 * @param pid
	 * @return
	 */
	List<SysMenu> listButtonsByPid(Long pid);
	
	/**
	 * 根据id获取菜单
	 * @param id
	 * @return
	 */
	SysMenu getMenuById(Long id);
	
	/**
	 * 新增菜单
	 * @param menu
	 */
	void insertMenu(SysMenu menu);
	
	/**
	 * 修改菜单
	 * @param menu
	 */
	void updateMenu(SysMenu menu);
	
	/**
	 * 删除菜单
	 * @param id
	 */
	void deleteMenu(Long id);
	
}
