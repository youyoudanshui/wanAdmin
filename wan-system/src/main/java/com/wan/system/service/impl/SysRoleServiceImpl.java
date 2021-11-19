package com.wan.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wan.system.domain.SysRole;
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
	@Cacheable("roleList")
	public List<SysRole> listAllRoles() {
		return roleMapper.listAllRoles();
	}

	@Override
	public SysRole getRoleById(Long id) {
		return roleMapper.getRoleById(id);
	}

	@Override
	public SysRole getRoleByRolename(String rolename) {
		return roleMapper.getRoleByRolename(rolename);
	}

	@Override
	@CacheEvict(value="roleList", allEntries=true)
	public void insertRole(SysRole role) {
		roleMapper.insertRole(role);
	}

	@Override
	@CacheEvict(value="roleList", allEntries=true)
	public void updateRole(SysRole role) {
		roleMapper.updateRole(role);
	}

	@Override
	@CacheEvict(value="roleList", allEntries=true)
	@Transactional(rollbackFor = Exception.class)
	public void deleteRole(Long id) {
		roleMapper.deleteRole(id);
		roleRuleMapper.deleteRoleRules(id);
	}

}
