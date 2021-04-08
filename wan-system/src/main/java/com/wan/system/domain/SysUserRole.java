package com.wan.system.domain;

import com.wan.common.domain.BaseDTO;

/**
 * 用户角色类
 * @author wmj
 *
 */
public class SysUserRole extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Long userId;
	
	Long roleId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "SysUserRole [userId=" + userId + ", roleId=" + roleId + "]";
	}
	
}
