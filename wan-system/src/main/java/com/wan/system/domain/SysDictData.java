package com.wan.system.domain;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.wan.common.domain.BaseDTO;

public class SysDictData extends BaseDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String dictId;
	
	/*类型*/
	String typeName;
	
	/*键*/
	@NotBlank(message = "键是必填字段！")
	String key;
	
	/*值*/
	@NotBlank(message = "值是必填字段！")
	String value;
	
	/*状态*/
	@NotBlank(message = "状态是必填字段！")
	String status;
	
	/*排序*/
	@NotNull(message = "排序是必填字段！")
	@Digits(fraction = 0, integer = 11, message = "排序只能输入数字！")
	int seq;

	public String getDictId() {
		return dictId;
	}

	public void setDictId(String dictId) {
		this.dictId = dictId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	@Override
	public String toString() {
		return "SysDictData [dictId=" + dictId + ", typeName=" + typeName + ", key=" + key + ", value=" + value
				+ ", status=" + status + ", seq=" + seq + "]";
	}

}
