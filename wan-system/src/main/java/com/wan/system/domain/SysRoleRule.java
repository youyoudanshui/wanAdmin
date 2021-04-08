package com.wan.system.domain;

import com.wan.common.domain.BaseDTO;

/**
 * 角色权限类
 * @author wmj
 *
 */
public class SysRoleRule extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Long roleId;
	
	String permissionValue;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getPermissionValue() {
		return permissionValue;
	}

	public void setPermissionValue(String permissionValue) {
		this.permissionValue = permissionValue;
	}

	@Override
	public String toString() {
		return "SysRoleRule [roleId=" + roleId + ", permissionValue=" + permissionValue + "]";
	}
	
}
