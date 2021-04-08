package com.wan.system.domain;

import java.util.Arrays;

import javax.validation.constraints.NotBlank;

import com.wan.common.domain.BaseDTO;

/**
 * 角色类
 * @author wmj
 *
 */
public class SysRole extends BaseDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 角色名
	 */
	@NotBlank(message = "角色名是必填字段！")
	String rolename;
	
	/**
	 * 具体描述
	 */
	@NotBlank(message = "具体描述是必填字段！")
	String description;
	
	/**
	 * 拥有权限
	 */
	String[] rules;

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String[] getRules() {
		return rules;
	}

	public void setRules(String[] rules) {
		this.rules = rules;
	}

	@Override
	public String toString() {
		return "SysRole [rolename=" + rolename + ", description=" + description + ", rules=" + Arrays.toString(rules)
				+ "]";
	}
	
}
