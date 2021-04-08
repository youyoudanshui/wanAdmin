package com.wan.system.mapper;

import java.util.List;

import com.wan.system.domain.SysRole;

public interface SysRoleMapper {
	
	/**
	 * 获取角色列表
	 * @return
	 */
	List<SysRole> listRoles(SysRole role);
	
	/**
	 * 根据id获取角色
	 * @param id
	 * @return
	 */
	SysRole getRoleById(Long id);
	
	/**
	 * 根据角色名获取角色
	 * @param rolename
	 * @return
	 */
	SysRole getRoleByRolename(String rolename);
	
	/**
	 * 新增角色
	 * @param role
	 */
	void insertRole(SysRole role);
	
	/**
	 * 修改角色
	 * @param role
	 */
	void updateRole(SysRole role);
	
	/**
	 * 删除角色
	 * @param id
	 */
	void deleteRole(Long id);
	
}
