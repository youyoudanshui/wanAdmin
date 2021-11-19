package com.wan.system.service;

import java.util.List;

import com.wan.system.domain.SysRole;

public interface SysRoleService {
	
	List<SysRole> listRoles(SysRole role);
	
	List<SysRole> listAllRoles();
	
	SysRole getRoleById(Long id);
	
	SysRole getRoleByRolename(String rolename);
	
	void insertRole(SysRole role);
	
	void updateRole(SysRole role);
	
	void deleteRole(Long id);
	
}
