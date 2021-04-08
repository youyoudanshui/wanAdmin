package com.wan.system.domain;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.wan.common.domain.BaseDTO;

public class SysDict extends BaseDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*类型*/
	@NotBlank(message = "类型是必填字段！")
	String typeName;
	
	/*具体描述*/
	String description;
	
	/*状态*/
	@NotBlank(message = "状态是必填字段！")
	String status;
	
	List<SysDictData> items;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<SysDictData> getItems() {
		return items;
	}

	public void setItems(List<SysDictData> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "SysDict [typeName=" + typeName + ", description=" + description + ", status=" + status + ", items="
				+ items + "]";
	}

}
