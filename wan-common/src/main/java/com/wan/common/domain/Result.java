package com.wan.common.domain;

/**
 * http请求返回对象
 * @author wmj
 *
 */
public class Result {
	
	public static final int SUCCESS = 0;
	public static final int ERROR = 1;
	
	/*状态码，0成功，1失败*/
	private Integer code;
	
	/*消息描述*/
	private String message;
	
	/*数据对象*/
	private Object data;
	
	public Result() {
		
	}
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
