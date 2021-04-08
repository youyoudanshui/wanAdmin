package com.wan.system.service;

import java.util.List;

import com.wan.system.domain.SysLog;
import com.wan.system.domain.SysUser;

public interface SysUserService {
	
	List<SysUser> listUsers(SysUser user);
	
	SysUser getUserById(Long id);
	
	SysUser getUserByLoginname(String loginname);
	
	SysUser getUserAndRolesById(Long id);
	
	void insertUser(SysUser user);
	
	void updateUser(SysUser user);
	
	void updateUserAndRoles(SysUser user);

	void updateUserPwd(SysUser user, SysLog log);
	
	void deleteUser(Long id);
	
	void removeUserSessionById(Long id);
	
}
