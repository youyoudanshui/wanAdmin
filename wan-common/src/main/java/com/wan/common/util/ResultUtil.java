package com.wan.common.util;

import com.wan.common.domain.Result;

/**
 * 
 * @author wmj
 *
 */
public class ResultUtil {
	
	public static Result success(Object data) {
		Result rs = new Result();
		rs.setCode(Result.SUCCESS);
		rs.setMessage("执行成功");
		rs.setData(data);
		return rs;
	}
	
	public static Result success() {
		return success(null);
	}
	
	public static Result error(String msg) {
		Result rs = new Result();
		rs.setCode(Result.ERROR);
		rs.setMessage(msg);
		return rs;
	}
	
	public static Result error() {
		return error("执行失败");
	}
	
}
