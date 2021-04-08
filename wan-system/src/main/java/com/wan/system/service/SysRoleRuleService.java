package com.wan.system.service;

import java.util.List;

import com.wan.system.domain.SysRole;
import com.wan.system.domain.SysRoleRule;

public interface SysRoleRuleService {

	void saveRoleRules(SysRole role);
	
	List<SysRoleRule> listRoleRulesByUserId(Long userId);
	
}
