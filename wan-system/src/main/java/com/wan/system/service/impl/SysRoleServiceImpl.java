package com.wan.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wan.system.domain.SysRole;
import com.wan.system.domain.SysRoleRule;
import com.wan.system.mapper.SysRoleMapper;
import com.wan.system.mapper.SysRoleRuleMapper;
import com.wan.system.service.SysRoleService;

@Service
public class SysRoleServiceImpl implements SysRoleService {
	
	@Autowired
	private SysRoleMapper roleMapper;
	
	@Autowired
	private SysRoleRuleMapper roleRuleMapper;

	@Override
	public List<SysRole> listRoles(SysRole role) {
		return roleMapper.listRoles(role);
	}

	@Override
	public SysRole getRoleById(Long id) {
		return roleMapper.getRoleById(id);
	}

	@Override
	public SysRole getRoleAndRulesById(Long id) {
		SysRole role = roleMapper.getRoleById(id);
		
		// 权限
		List<SysRoleRule> roleRuleList = roleRuleMapper.listRoleRules(id);
		int size = roleRuleList.size();
		String[] rules = new String[size];
		for (int i = 0; i < size; i ++) {
			rules[i] = roleRuleList.get(i).getPermissionValue();
		}
		role.setRules(rules);
		
		return role;
	}

	@Override
	public SysRole getRoleByRolename(String rolename) {
		return roleMapper.getRoleByRolename(rolename);
	}

	@Override
	public void insertRole(SysRole role) {
		roleMapper.insertRole(role);
	}

	@Override
	public void updateRole(SysRole role) {
		roleMapper.updateRole(role);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteRole(Long id) {
		roleMapper.deleteRole(id);
		roleRuleMapper.deleteRoleRules(id);
	}

}
