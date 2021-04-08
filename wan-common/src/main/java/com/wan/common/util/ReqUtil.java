package com.wan.common.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class ReqUtil {

	public static HttpServletRequest getRequest() {
		// 获取RequestAttributes
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		
		if (requestAttributes == null) {
			return null;
		}
		
		// 从获取RequestAttributes中获取HttpServletRequest的信息
		HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
		return request;
	}
	
	/**
	 * 转换request请求参数
	 * @param paramMap
	 * @return
	 */
	public static Map<String, String> converRequestMap() {
		Map<String, String[]> parameterMap = getRequest().getParameterMap();
		Map<String, String> resultMap = new HashMap<String, String>();
		for (String key : parameterMap.keySet()) {
			resultMap.put(key, parameterMap.get(key)[0]);
		}
		return resultMap;
	}
	
}
