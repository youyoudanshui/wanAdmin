package com.wan.system.mapper;

import java.util.List;

import com.wan.system.domain.SysRoleRule;

public interface SysRoleRuleMapper {

	/**
	 * 获取角色权限列表
	 * @return
	 */
	List<SysRoleRule> listRoleRules(Long roleId);
	
	/**
	 * 获取用户权限列表
	 * @param userId
	 * @return
	 */
	List<SysRoleRule> listRoleRulesByUserId(Long userId);
	
	/**
	 * 新增角色权限
	 * @param roleRule
	 */
	void insertRoleRule(SysRoleRule roleRule);
	
	/**
	 * 删除角色权限
	 * @param roleId
	 */
	void deleteRoleRules(Long roleId);
	
}
