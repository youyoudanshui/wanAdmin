package com.wan.system.aspect;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wan.common.aop.Log;
import com.wan.common.util.ReqUtil;
import com.wan.common.util.StringUtil;
import com.wan.system.domain.SysLog;
import com.wan.system.domain.SysUser;
import com.wan.system.service.SysLogService;

/**
 * 切面处理类，操作日志异常日志记录处理
 * @author wmj
 *
 */
@Aspect
@Component
public class SysLogAspect {
	
	@Autowired
	private SysLogService logService;
	
	/**
	 * 设置操作日志切入点 记录操作日志 在注解的位置切入代码
	 */
	@Pointcut("@annotation(com.wan.common.aop.Log)")
	public void logPointCut() {
	}
	
	/**
	 * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
	 * @param joinPoint
	 * @param keys
	 */
	@AfterReturning(value = "logPointCut()", returning = "result")
	public void doAfterReturning(JoinPoint joinPoint, Object result) {
		saveLog(joinPoint, result, null);
	}
	
	@AfterThrowing(pointcut = "logPointCut()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		saveLog(joinPoint, null, e);
	}
	
	protected void saveLog(JoinPoint joinPoint, Object result, Throwable e) {
		
		SysLog sysLog = new SysLog();
		
		try {
			
			HttpServletRequest request = ReqUtil.getRequest();
			
			// 从切面织入点处通过反射机制获取织入点处的方法
			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			// 获取切入点所在的方法
			Method method = signature.getMethod();
			// 获取操作
			Log log = method.getAnnotation(Log.class);
			if (log != null) {
				sysLog.setBusinessName(log.BusinessName());
				sysLog.setOperationType(log.OperationType().toString());
				sysLog.setContent(log.Content());
			}
			
			// 获取请求的类名
			String className = joinPoint.getTarget().getClass().getName();
			// 获取请求的方法名
			String methodName = method.getName();
			sysLog.setMethod(className + "." + methodName);
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			// 请求的参数
			Map<String, String[]> parameterMap = request.getParameterMap();
			if (parameterMap.size() > 0) {
				sysLog.setRequestParam(objectMapper.writeValueAsString(ReqUtil.converRequestMap()));
			} else {
				// 地址参数
				Object pathVariables = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
				if (pathVariables != null) {
					sysLog.setRequestParam(objectMapper.writeValueAsString((Map) pathVariables));
				}
			}
			
			// 请求地址
			String URI = request.getRequestURI();
			sysLog.setUrl(URI);
			
			// 返回参数
			if (result != null) {
				sysLog.setResponseParam(objectMapper.writeValueAsString(result));
			}
			
			// 用户信息
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			SysUser sessionUser = (SysUser) auth.getPrincipal();
			String IP = ((WebAuthenticationDetails) auth.getDetails()).getRemoteAddress();
			sysLog.setOperator(sessionUser.getId());
			sysLog.setIp(IP);
			
			// 异常信息
			if (e != null) {
				sysLog.setStatus("1");
			}
			
			logService.insertLog(sysLog);
			if (e != null) {
				String errorMessage = StringUtil.stackTraceToString(e);
				logService.insertErrorLog(sysLog.getId(), errorMessage);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

}
