package com.wan.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wan.system.domain.SysLog;
import com.wan.system.domain.SysUser;
import com.wan.system.domain.SysUserRole;
import com.wan.system.mapper.SysLogMapper;
import com.wan.system.mapper.SysUserMapper;
import com.wan.system.mapper.SysUserRoleMapper;
import com.wan.system.service.SysUserService;

@Service
public class SysUserServiceImpl implements SysUserService {
	
	@Autowired
	private SysUserMapper userMapper;
	
	@Autowired
	private SysUserRoleMapper userRoleMapper;
	
	@Autowired
	private SysLogMapper logMapper;
	
	@Autowired
    private SessionRegistry sessionRegistry;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public List<SysUser> listUsers(SysUser user) {
		return userMapper.listUsers(user);
	}

	@Override
	public SysUser getUserById(Long id) {
		SysUser user = userMapper.getUserById(id);
		return user;
	}

	@Override
	public SysUser getUserByLoginname(String loginname) {
		return userMapper.getUserByLoginname(loginname);
	}

	@Override
	public SysUser getUserAndRolesById(Long id) {
		SysUser user = userMapper.getUserById(id);
		
		// 角色
		List<SysUserRole> userRoleList = userRoleMapper.listUserRoles(id);
		int size = userRoleList.size();
		String[] roles = new String[size];
		for (int i = 0; i < size; i ++) {
			roles[i] = userRoleList.get(i).getRoleId().toString();
		}
		user.setRoles(roles);
		
		return user;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertUser(SysUser user) {
		// 密码加密
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userMapper.insertUser(user);
		
		// 角色
		saveUserRoles(user);
	}

	@Override
	public void updateUser(SysUser user) {
		userMapper.updateUser(user);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateUserAndRoles(SysUser user) {
		userMapper.updateUser(user);
		
		// 角色
		saveUserRoles(user);
	}
	
	@Transactional(rollbackFor = Exception.class)
	private void saveUserRoles(SysUser user) {
		Long userId = user.getId();
		String[] roles = user.getRoles();
		
		userRoleMapper.deleteUserRoles(userId);
		if (roles != null) {
			for (String role: roles) {
				SysUserRole userRole = new SysUserRole();
				userRole.setUserId(userId);
				userRole.setRoleId(Long.valueOf(role));
				userRoleMapper.insertUserRole(userRole);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateUserPwd(SysUser user, SysLog log) {
		// 密码加密
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userMapper.updateUser(user);
		logMapper.insertLog(log);
		
		// 删除当前用户
		removeUserSessionById(user.getId());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteUser(Long id) {
		userMapper.deleteUser(id);
		userRoleMapper.deleteUserRoles(id);
	}
	
	@Override
	public void removeUserSessionById(Long id) {
		List<Object> o = sessionRegistry.getAllPrincipals();
		for (Object principal: o) {
			if (principal instanceof SysUser) {
				final SysUser loggedUser = (SysUser) principal;
				if (id.equals(loggedUser.getId())) {
					List<SessionInformation> sessionsInfo = sessionRegistry.getAllSessions(principal, false);
					if (sessionsInfo != null && sessionsInfo.size() > 0) {
						for (SessionInformation sessionInformation: sessionsInfo) {
							sessionInformation.expireNow();
							sessionRegistry.removeSessionInformation(sessionInformation.getSessionId());
						}
					}
				}
			}
		}
	}
	
}
