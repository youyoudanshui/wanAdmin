package com.wan.common.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BaseDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Long id;
	
	/*创建时间*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	String gmtCreate;
	
	/*修改时间*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	String gmtModified;
	
	/*查询条件*/
	Map<String, String> query;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	
	public String getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(String gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Map<String, String> getQuery() {
		if (query == null) {
			query = new HashMap<String, String>();
		}
		return query;
	}

	public void setQuery(Map<String, String> query) {
		this.query = query;
	}
	
}
