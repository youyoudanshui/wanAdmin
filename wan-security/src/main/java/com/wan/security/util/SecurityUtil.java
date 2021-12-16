package com.wan.security.util;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.wan.common.util.ReqUtil;
import com.wan.system.domain.SysUser;

public class SecurityUtil {
	
	public static Authentication getAuth() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth;
	}

	public static SysUser getAuthUser() {
		Authentication auth = getAuth();
		SysUser user = (SysUser) auth.getPrincipal();
		return user;
	}
	
	public static WebAuthenticationDetails getAuthDetails() {
		Authentication auth = getAuth();
		WebAuthenticationDetails details = (WebAuthenticationDetails) auth.getDetails();
		return details;
	}
	
	@SuppressWarnings("unchecked")
	public static void updateAuthPrincipal(SysUser user) {
		Authentication auth = getAuth();
		user.setIp(ReqUtil.getRequest().getRemoteAddr());
		user.setAuthorities((Collection<GrantedAuthority>) auth.getAuthorities());
		user.setMenus(((SysUser) auth.getPrincipal()).getMenus());
		
		UsernamePasswordAuthenticationToken newToken = new UsernamePasswordAuthenticationToken(user, auth.getCredentials(), auth.getAuthorities());
		newToken.setDetails(auth.getDetails());
		SecurityContextHolder.getContext().setAuthentication(newToken);
	}
	
}
