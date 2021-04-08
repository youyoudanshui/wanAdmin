package com.wan.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

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
	
}
