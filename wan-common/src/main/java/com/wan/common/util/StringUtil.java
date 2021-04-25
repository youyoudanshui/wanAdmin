package com.wan.common.util;

public class StringUtil {
	
	public static String stackTraceToString(Throwable e) {
		
		StringBuffer strbuff = new StringBuffer();
		for (StackTraceElement stet : e.getStackTrace()) {
			strbuff.append(stet + "\n\t");
		}
		String message = e.getClass().getName() + ":" + e.getMessage() + "\n\t" + strbuff.toString();
		return message;
		
	}

}
