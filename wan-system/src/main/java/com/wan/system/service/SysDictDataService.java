package com.wan.system.service;

import java.util.List;

import com.wan.system.domain.SysDictData;

public interface SysDictDataService {
	
	List<SysDictData> listDictDatas(SysDictData data);
	
	SysDictData getDictDataById(Long id);
	
	String getDictDataValue(SysDictData data);
	
	List<SysDictData> getDictDataList(SysDictData data);
	
	void insertDictData(SysDictData data);
	
	void updateDictData(SysDictData data);
	
	void deleteDictData(SysDictData data);
	
}
