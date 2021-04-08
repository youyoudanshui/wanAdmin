package com.wan.system.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wan.system.domain.SysDict;
import com.wan.system.mapper.SysDictDataMapper;
import com.wan.system.mapper.SysDictMapper;
import com.wan.system.service.SysDictService;

@Service
public class SysDictServiceImpl implements SysDictService {
	
	@Autowired
	private SysDictMapper dictMapper;
	
	@Autowired
	private SysDictDataMapper dictDataMapper;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	String cachePrefix = "dictData::";

	@Override
	public List<SysDict> listDicts(SysDict dict) {
		return dictMapper.listDicts(dict);
	}

	@Override
	public SysDict getDictById(Long id) {
		return dictMapper.getDictById(id);
	}

	@Override
	public SysDict getDictByTypeName(String typeName) {
		return dictMapper.getDictByTypeName(typeName);
	}

	@Override
	public void insertDict(SysDict dict) {
		dictMapper.insertDict(dict);
	}

	@Override
	public void updateDict(SysDict dict) {
		dictMapper.updateDict(dict);
		
		// 删除缓存
		if ("0".equals(dict.getStatus())) {
			Set<String> keys = stringRedisTemplate.keys(cachePrefix + dictMapper.getDictById(dict.getId()).getTypeName() + "_*");
			stringRedisTemplate.delete(keys);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteDict(Long id) {
		SysDict dict = dictMapper.getDictById(id);
		dictMapper.deleteDict(id);
		dictDataMapper.deleteDictDataByDictId(id);
		
		// 删除缓存
		Set<String> keys = stringRedisTemplate.keys(cachePrefix + dict.getTypeName() + "_*");
		stringRedisTemplate.delete(keys);
	}

}