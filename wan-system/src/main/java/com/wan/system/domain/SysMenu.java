package com.wan.system.domain;

import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.wan.common.domain.BaseDTO;

/**
 * 菜单类
 * @author wmj
 *
 */
public class SysMenu extends BaseDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Long pid;
	
	/*上级菜单名称*/
	String pname;
	
	/*状态*/
	@NotBlank(message = "状态是必填字段！")
	String status;
	
	/*类型*/
	@NotBlank(message = "类型是必填字段！")
	String type;
	
	/*名称*/
	@NotBlank(message = "名称是必填字段！")
	String name;
	
	/*图标*/
	String icon;
	
	String url;
	
	/*权限值*/
	@NotBlank(message = "权限值是必填字段！")
	String permissionValue;
	
	/*排序*/
	@NotNull(message = "排序是必填字段！")
	@Digits(fraction = 0, integer = 11, message = "排序只能输入数字！")
	Integer seq;
	
	List<SysMenu> submenus;

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPermissionValue() {
		return permissionValue;
	}

	public void setPermissionValue(String permissionValue) {
		this.permissionValue = permissionValue;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public List<SysMenu> getSubmenus() {
		return submenus;
	}

	public void setSubmenus(List<SysMenu> submenus) {
		this.submenus = submenus;
	}

	@Override
	public String toString() {
		return "SysMenuDO [pid=" + pid + ", pname=" + pname + ", status=" + status + ", name=" + name + ", icon=" + icon
				+ ", url=" + url + ", permissionValue=" + permissionValue + ", seq=" + seq + ", submenus=" + submenus
				+ "]";
	}
	
}
