package com.wan.system.domain;

import java.io.Serializable;
import java.util.Collection;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wan.common.domain.BaseDTO;

/**
 * 用户类
 * @author wmj
 *
 */
public class SysUser extends BaseDTO implements Serializable, UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 登录名
	 */
	@NotBlank(message = "登录名是必填字段！")
	String loginname;
	
	/**
	 * 用户名
	 */
	@NotBlank(message = "用户名是必填字段！")
	String username;
	
	/**
	 * 密码
	 */
	@NotBlank(message = "密码是必填字段！")
	@Length(min = 5, message = "密码最少要输入 5 个字符！")
	String password;
	
	/**
	 * 头像
	 */
	String avatar;
	
	/**
	 * 手机
	 */
	String telephone;
	
	/**
	 * 邮箱
	 */
	@Email(message = "请输入有效的电子邮件地址！")
	String email;
	
	/**
	 * 性别
	 */
	String sex;
	
	/**
	 * 状态
	 */
	@NotBlank(message = "状态是必填字段！")
	String status;
	
	@NotNull(message = "角色是必填字段！")
	String[] roles;
	
	/**
	 * 登录密码输入错误次数
	 */
	int errorTimes;
	
	/**
	 * 账号是否被锁
	 */
	String isLocked;
	
	/**
	 * 锁过期时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	String gmtLocked;
	
	Collection<GrantedAuthority> authorities;
	
	String ip;

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}
	
	public int getErrorTimes() {
		return errorTimes;
	}

	public void setErrorTimes(int errorTimes) {
		this.errorTimes = errorTimes;
	}

	public String getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(String isLocked) {
		this.isLocked = isLocked;
	}

	public String getGmtLocked() {
		return gmtLocked;
	}

	public void setGmtLocked(String gmtLocked) {
		this.gmtLocked = gmtLocked;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getIp() {
		return ip;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return loginname;
	}
	
	@Override
	public int hashCode() {
		return loginname.hashCode();
	}

	@Override
    public boolean equals(Object obj) {
        return this.toString().equals(obj.toString());
    }
	
}
