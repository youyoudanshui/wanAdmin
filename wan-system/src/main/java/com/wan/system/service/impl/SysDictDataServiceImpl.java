package com.wan.system.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.wan.system.domain.SysDictData;
import com.wan.system.mapper.SysDictDataMapper;
import com.wan.system.service.SysDictDataService;

@Service
public class SysDictDataServiceImpl implements SysDictDataService {
	
	@Autowired
	private SysDictDataMapper dictDataMapper;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	String cachePrefix = "dictData::";

	@Override
	public List<SysDictData> listDictDatas(SysDictData data) {
		return dictDataMapper.listDictDatas(data);
	}

	@Override
	public SysDictData getDictDataById(Long id) {
		return dictDataMapper.getDictDataById(id);
	}

	@Override
	@Cacheable(value = {"dictData"}, key = "#data.typeName + '_' + #data.key", unless = "#result == null")
	public String getDictDataValue(SysDictData data) {
		return dictDataMapper.getDictDataValue(data);
	}

	@Override
	public void insertDictData(SysDictData data) {
		dictDataMapper.insertDictData(data);
	}

	@Override
	public void updateDictData(SysDictData data) {
		dictDataMapper.updateDictData(data);
		
		// 删除缓存
		Set<String> keys = stringRedisTemplate.keys(cachePrefix + data.getTypeName() + "_*");
		stringRedisTemplate.delete(keys);
	}

	@Override
	@CacheEvict(value={"dictData"}, key = "#data.typeName + '_' + #data.key")
	public void deleteDictData(SysDictData data) {
		dictDataMapper.deleteDictData(data.getId());
	}

}
