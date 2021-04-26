package com.wan.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wan.common.util.ResultUtil;
import com.wan.system.domain.SysLoginLog;
import com.wan.system.domain.SysUser;
import com.wan.system.service.SysLoginLogService;
import com.wan.system.service.SysUserService;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

@Component("myAuthenctiationFailureHandler")
public class MyAuthenctiationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private SysUserService userService;
	
	@Autowired
	private SysLoginLogService loginLogService;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) 
			throws IOException, ServletException {
		
		String loginname = request.getParameter("username");
		
		if (exception instanceof BadCredentialsException) {
			SysUser user = userService.getUserByLoginname(loginname);
			
			int errorTimes = user.getErrorTimes();
			SysUser updateUser = new SysUser();
			updateUser.setId(user.getId());
			updateUser.setErrorTimes(errorTimes == 3 ? 1 : errorTimes + 1);
			if (errorTimes == 2) {
				updateUser.setIsLocked("1");
				exception = new BadCredentialsException("密码输入连续错误三次，账户锁定十分钟，请十分钟后再试", exception);
			} else {
				updateUser.setIsLocked("0");
				exception = new BadCredentialsException("用户名不存在或密码错误", exception);
			}
			userService.updateUser(updateUser);
		}
		
		// 保存登录日志
		SysLoginLog loginLog = new SysLoginLog();
		loginLog.setLoginname(loginname);
		loginLog.setLoginStatus("1");
		loginLog.setMessage(exception.getLocalizedMessage());
		loginLog.setIp(request.getRemoteAddr());
		
		String agent = request.getHeader("User-Agent");
		UserAgent userAgent = UserAgent.parseUserAgentString(agent);  
		Browser browser = userAgent.getBrowser();  
		OperatingSystem os = userAgent.getOperatingSystem();
		
		loginLog.setUserAgent(agent);
		loginLog.setBrowser(browser.getName());
		loginLog.setOperatingSystem(os.getName());
		
		loginLogService.insertLoginLog(loginLog);
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(ResultUtil.error(exception.getLocalizedMessage())));
		
	}
	
}
