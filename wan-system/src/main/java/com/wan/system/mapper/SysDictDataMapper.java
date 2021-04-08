package com.wan.system.mapper;

import java.util.List;

import com.wan.system.domain.SysDictData;

public interface SysDictDataMapper {
	
	/**
	 * 获取字典数据列表
	 * @return
	 */
	List<SysDictData> listDictDatas(SysDictData data);
	
	/**
	 * 根据id获取字典数据
	 * @param id
	 * @return
	 */
	SysDictData getDictDataById(Long id);
	
	/**
	 * 获取字典数据值
	 * @param data
	 * @return
	 */
	String getDictDataValue(SysDictData data);
	
	/**
	 * 新增字典数据
	 * @param data
	 */
	void insertDictData(SysDictData data);
	
	/**
	 * 修改字典数据
	 * @param data
	 */
	void updateDictData(SysDictData data);
	
	/**
	 * 删除字典数据
	 * @param id
	 */
	void deleteDictData(Long id);
	
	/**
	 * 根据dictId删除字典数据
	 * @param dictId
	 */
	void deleteDictDataByDictId(Long dictId);
	
}
