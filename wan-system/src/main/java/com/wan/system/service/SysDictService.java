package com.wan.system.service;

import java.util.List;

import com.wan.system.domain.SysDict;

public interface SysDictService {
	
	List<SysDict> listDicts(SysDict dict);
	
	SysDict getDictById(Long id);
	
	SysDict getDictByTypeName(String typeName);
	
	void insertDict(SysDict dict);
	
	void updateDict(SysDict dict);
	
	void deleteDict(Long id);
	
}
