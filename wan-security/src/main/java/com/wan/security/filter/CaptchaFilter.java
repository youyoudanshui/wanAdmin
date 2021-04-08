package com.wan.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.code.kaptcha.Constants;
import com.wan.security.exception.CaptchaInvalidException;

@Component
public class CaptchaFilter extends OncePerRequestFilter {
	
	private AuthenticationFailureHandler authenticationFailureHandler;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if (request.getRequestURI().indexOf("/doLogin") > -1) {
			
			String s_captcha = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
			String captcha = request.getParameter("captcha");
			
			if (StringUtils.isBlank(captcha)) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, new CaptchaInvalidException("验证码不能为空"));
				return;
			}
			
			if (!StringUtils.equalsIgnoreCase(s_captcha, captcha)) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, new CaptchaInvalidException("验证码错误，请重新输入"));
				return;
			}
			
		}	
		
		filterChain.doFilter(request, response);
	}
	
	public void setAuthenticationFailureHandler(AuthenticationFailureHandler authFailureHandler) {
		this.authenticationFailureHandler = authFailureHandler;
	}

}
