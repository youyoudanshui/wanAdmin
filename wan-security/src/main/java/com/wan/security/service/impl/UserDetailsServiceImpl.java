package com.wan.security.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.wan.common.util.ReqUtil;
import com.wan.system.domain.SysRole;
import com.wan.system.domain.SysRoleRule;
import com.wan.system.domain.SysUser;
import com.wan.system.domain.SysUserRole;
import com.wan.system.mapper.SysRoleMapper;
import com.wan.system.mapper.SysRoleRuleMapper;
import com.wan.system.mapper.SysUserRoleMapper;
import com.wan.system.service.SysUserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private SysUserService userService;
	
	@Autowired
	private SysRoleMapper roleMapper;
	
	@Autowired
	private SysUserRoleMapper userRoleMapper;
	
	@Autowired
	private SysRoleRuleMapper roleRuleMapper;
	
	/**
	 * 用户名username实际为登录名loginname
	 */
	public UserDetails loadUserByUsername(String username) throws AuthenticationException {
		
		if (StringUtils.isBlank(username)) {
			throw new BadCredentialsException("用户名不能为空");
		}
		
		// 从数据库中取出用户信息
		SysUser user = userService.getUserByLoginname(username);
		
		// 判断用户是否存在
		if (user == null) {
			throw new BadCredentialsException("用户名不存在或密码错误");
		}
		
		// 判断用户是否被禁用
		if ("0".equals(user.getStatus())) {
			throw new DisabledException("账户被禁用，请联系管理员");
		}
		
		// 判断用户是否被锁
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		try {
			if ("1".equals(user.getIsLocked()) && now.getTime() < sdf.parse(user.getGmtLocked()).getTime()) {
				throw new LockedException("账户被锁，请稍后再试");
			}
		} catch (ParseException e) {
			e.printStackTrace();
			throw new DisabledException("账户异常，请联系管理员");
		}
        
        // IP
        user.setIp(ReqUtil.getRequest().getRemoteAddr());
		
		// 添加权限
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		List<SysUserRole> userRoleList = userRoleMapper.listUserRoles(user.getId());
		for (SysUserRole userRole: userRoleList) {
			SysRole role = roleMapper.getRoleById(userRole.getRoleId());
			authorities.add(new SimpleGrantedAuthority(role.getRolename()));
			
			List<SysRoleRule> roleRuleList = roleRuleMapper.listRoleRules(role.getId());
			for (SysRoleRule roleRule: roleRuleList) {
				authorities.add(new SimpleGrantedAuthority(roleRule.getPermissionValue()));
			}
		}
		
		// 返回UserDetails实现类
		user.setAuthorities(authorities);
		return user;
		
	}
}
