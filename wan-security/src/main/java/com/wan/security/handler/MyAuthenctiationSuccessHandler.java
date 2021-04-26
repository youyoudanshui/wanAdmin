package com.wan.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.kaptcha.Constants;
import com.wan.common.util.ResultUtil;
import com.wan.system.domain.SysLoginLog;
import com.wan.system.domain.SysUser;
import com.wan.system.service.SysLoginLogService;
import com.wan.system.service.SysUserService;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

@Component("myAuthenctiationSuccessHandler")
public class MyAuthenctiationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private SysUserService userService;
	
	@Autowired
	private SysLoginLogService loginLogService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) 
			throws IOException, ServletException {
		
		SysUser user = (SysUser) authentication.getPrincipal();
		SysUser updateUser = new SysUser();
		updateUser.setId(user.getId());
		updateUser.setErrorTimes(0);
		updateUser.setIsLocked("0");
		userService.updateUser(updateUser);
		
		// 保存登录日志
		SysLoginLog loginLog = new SysLoginLog();
		loginLog.setLoginname(user.getLoginname());
		loginLog.setUserId(user.getId());
		loginLog.setLoginStatus("0");
		loginLog.setMessage("登录成功");
		loginLog.setIp(user.getIp());
		
		String agent = request.getHeader("User-Agent");
		UserAgent userAgent = UserAgent.parseUserAgentString(agent);  
		Browser browser = userAgent.getBrowser();  
		OperatingSystem os = userAgent.getOperatingSystem();
		
		loginLog.setUserAgent(agent);
		loginLog.setBrowser(browser.getName());
		loginLog.setOperatingSystem(os.getName());
		
		loginLogService.insertLoginLog(loginLog);
		
		// 删除session中验证码
		request.getSession().removeAttribute(Constants.KAPTCHA_SESSION_KEY);
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(ResultUtil.success()));
		
	}
	
}
