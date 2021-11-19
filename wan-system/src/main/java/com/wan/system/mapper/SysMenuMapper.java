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
	 * 根据用户ID获取菜单列表
	 * @param userId
	 * @return
	 */
	List<SysMenu> listMenusByUserId(Long userId);
	
	/**
	 * 根据角色ID获取菜单列表
	 * @param userId
	 * @return
	 */
	List<SysMenu> listMenusByRoleId(Long roleId);
	
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
