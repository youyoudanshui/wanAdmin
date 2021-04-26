package com.wan.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.wan.common.enumerate.OperationType;
import com.wan.system.domain.SysLog;
import com.wan.system.domain.SysUser;
import com.wan.system.service.SysLogService;
import com.wan.system.service.SysUserService;

@Component("myLogoutSuccessHandler")
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
	
	@Autowired
	private SysLogService logService;
	
	@Autowired
	private SysUserService userService;
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		// 保存登出日志
		SysLog sysLog = new SysLog();
		sysLog.setBusinessName("退出登录");
		sysLog.setOperationType(OperationType.LOGOUT.toString());
		sysLog.setContent("退出登录");
		sysLog.setMethod(this.getClass().getName() + "." + "onLogoutSuccess");
		
		// 请求地址
		String URI = request.getRequestURI();
		sysLog.setUrl(URI);
		
		// 用户信息
		SysUser sessionUser = (SysUser) authentication.getPrincipal();
		String IP = ((WebAuthenticationDetails) authentication.getDetails()).getRemoteAddress();
		sysLog.setOperator(sessionUser.getId());
		sysLog.setIp(IP);
		
		logService.insertLog(sysLog);
		
		// 删除当前用户
		userService.removeUserSessionById(sessionUser.getId());
		
		response.sendRedirect(request.getContextPath() + "/login");
		
	}

}
