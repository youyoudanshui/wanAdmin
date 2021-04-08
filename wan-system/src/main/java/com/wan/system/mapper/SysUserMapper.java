package com.wan.system.mapper;

import java.util.List;

import com.wan.system.domain.SysUser;

public interface SysUserMapper {
	
	/**
	 * 获取用户列表
	 * @return
	 */
	List<SysUser> listUsers(SysUser user);
	
	/**
	 * 根据id获取用户
	 * @param id
	 * @return
	 */
	SysUser getUserById(Long id);
	
	/**
	 * 根据登录名获取用户
	 * @param loginname
	 * @return
	 */
	SysUser getUserByLoginname(String loginname);
	
	/**
	 * 新增用户
	 * @param user
	 */
	void insertUser(SysUser user);
	
	/**
	 * 修改用户
	 * @param user
	 */
	void updateUser(SysUser user);
	
	/**
	 * 删除用户
	 * @param id
	 */
	void deleteUser(Long id);
	
}
