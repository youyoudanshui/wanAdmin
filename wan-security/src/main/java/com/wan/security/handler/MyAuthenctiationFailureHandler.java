package com.wan.security.handler;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wan.common.enumerate.OperationType;
import com.wan.common.util.ReqUtil;
import com.wan.common.util.ResultUtil;
import com.wan.system.domain.SysLog;
import com.wan.system.service.SysLogService;

@Component("myAuthenctiationFailureHandler")
public class MyAuthenctiationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private SysLogService logService;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) 
			throws IOException, ServletException {
		
		if (exception instanceof BadCredentialsException) {
			exception = new BadCredentialsException("用户名不存在或密码错误", exception);
		}
		
		// 保存登录日志
		SysLog sysLog = new SysLog();
		sysLog.setBusinessName("登录");
		sysLog.setOperationType(OperationType.LOGIN.toString());
		sysLog.setContent("登录失败，" + exception.getLocalizedMessage());
		sysLog.setMethod(this.getClass().getName() + "." + "onAuthenticationFailure");
		
		// 请求的参数
		Map<String, String[]> parameterMap = request.getParameterMap();
		if (parameterMap.size() > 0) {
			sysLog.setRequestParam(objectMapper.writeValueAsString(ReqUtil.converRequestMap()));
		}
		
		// 请求地址
		String URI = request.getRequestURI();
		sysLog.setUrl(URI);
		
		// 用户信息
		sysLog.setIp(request.getRemoteAddr());
		
		logService.insertLog(sysLog);
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(ResultUtil.error(exception.getLocalizedMessage())));
		
	}
	
}
