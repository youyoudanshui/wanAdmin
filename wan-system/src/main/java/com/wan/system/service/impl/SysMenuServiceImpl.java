package com.wan.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<SysMenu> listIndexMenus(Long userId) {
		List<SysMenu> list = menuMapper.listMenusByUserId(userId);
		return listMenuTrees(list);
	}
	
	@Override
	public List<SysMenu> listMenusAndButtons(Long roleId) {
		List<SysMenu> list = menuMapper.listMenusByRoleId(roleId);
		return listMenuTrees(list);
	}
	
	private List<SysMenu> listMenuTrees(List<SysMenu> list) {
		Map<Long, List<SysMenu>> map = new HashMap<Long, List<SysMenu>>();
		// 获取每个节点的直属子节点
		for (SysMenu menu: list) {
			Long pid = menu.getPid() == null ? 0 : menu.getPid();
			if (map.get(pid) == null) {
				map.put(pid, new ArrayList<SysMenu>());
			}
			map.get(pid).add(menu);
		}
		return formatMenuTree(map, 0L);
	}
	
	/**
	 * 利用递归格式化每个节点
	 * @param map
	 * @param pid
	 * @return
	 */
	private List<SysMenu> formatMenuTree(Map<Long, List<SysMenu>> map, Long pid) {
		List<SysMenu> list = new ArrayList<SysMenu>();
		if (map.get(pid) == null) {
	        return list;
	    }
	    for (SysMenu menu: map.get(pid)) {
	    	menu.setSubmenus(formatMenuTree(map, menu.getId()));
	    	list.add(menu);
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
	public void insertMenu(SysMenu menu) {
		menuMapper.insertMenu(menu);
	}

	@Override
	public void updateMenu(SysMenu menu) {
		menuMapper.updateMenu(menu);
	}

	@Override
	public void deleteMenu(Long id) {
		menuMapper.deleteMenu(id);
	}
	
}
