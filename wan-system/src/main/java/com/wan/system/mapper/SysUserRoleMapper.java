package com.wan.system.mapper;

import java.util.List;

import com.wan.system.domain.SysUserRole;

public interface SysUserRoleMapper {

	/**
	 * 获取用户角色列表
	 * @return
	 */
	List<SysUserRole> listUserRoles(Long userId);
	
	/**
	 * 新增用户角色
	 * @param userRole
	 */
	void insertUserRole(SysUserRole userRole);
	
	/**
	 * 删除用户角色
	 * @param userId
	 */
	void deleteUserRoles(Long userId);
	
}
