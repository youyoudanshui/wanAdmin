package com.wan.system.mapper;

import java.util.List;

import com.wan.system.domain.SysDict;

public interface SysDictMapper {
	
	/**
	 * 获取数据字典列表
	 * @return
	 */
	List<SysDict> listDicts(SysDict dict);
	
	/**
	 * 根据id获取数据字典
	 * @param id
	 * @return
	 */
	SysDict getDictById(Long id);
	
	/**
	 * 根据typeName获取数据字典
	 * @param typeName
	 * @return
	 */
	SysDict getDictByTypeName(String typeName);
	
	/**
	 * 新增数据字典
	 * @param dict
	 */
	void insertDict(SysDict dict);
	
	/**
	 * 修改数据字典
	 * @param dict
	 */
	void updateDict(SysDict dict);
	
	/**
	 * 删除数据字典
	 * @param id
	 */
	void deleteDict(Long id);
	
}
