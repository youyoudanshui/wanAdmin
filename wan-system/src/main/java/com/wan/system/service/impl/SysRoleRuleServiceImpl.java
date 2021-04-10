package com.wan.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wan.system.domain.SysRole;
import com.wan.system.domain.SysRoleRule;
import com.wan.system.mapper.SysRoleRuleMapper;
import com.wan.system.service.SysRoleRuleService;

@Service
public class SysRoleRuleServiceImpl implements SysRoleRuleService {
	
	@Autowired
	private SysRoleRuleMapper roleRuleMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveRoleRules(SysRole role) {
		Long roleId = role.getId();
		String[] rules = role.getRules();
		
		roleRuleMapper.deleteRoleRules(roleId);
		if (rules != null) {
			for (String rule: rules) {
				SysRoleRule roleRule = new SysRoleRule();
				roleRule.setRoleId(roleId);
				roleRule.setPermissionValue(rule);
				roleRuleMapper.insertRoleRule(roleRule);
			}
		}
	}
	
}
