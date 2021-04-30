package com.wan.web.controller.system;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.wan.common.annotation.Log;
import com.wan.common.domain.Page;
import com.wan.common.domain.Result;
import com.wan.common.enumerate.OperationType;
import com.wan.common.util.ReqUtil;
import com.wan.common.util.ResultUtil;
import com.wan.security.util.SecurityUtil;
import com.wan.system.domain.SysLog;
import com.wan.system.domain.SysRole;
import com.wan.system.domain.SysUser;
import com.wan.system.service.SysRoleService;
import com.wan.system.service.SysUserService;
import com.wan.web.controller.common.BaseController;

/**
 * 用户管理
 * @author wmj
 *
 */
@Controller
@RequestMapping("/system/user")
public class SysUserController extends BaseController {
	
	@Autowired
	private SysUserService userService;
	
	@Autowired
	private SysRoleService roleService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private String prefix = "system/user";
	
	@GetMapping("")
	@PreAuthorize("hasAuthority('open:user:manage')")
	public String user() {
		return prefix + "/user";
	}
	
	@GetMapping("/list")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:user:manage')")
	public PageInfo<SysUser> list(SysUser user, Page page) {
		startPage(page);
		List<SysUser> list = userService.listUsers(user);
		PageInfo<SysUser> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	@GetMapping("/add")
	@PreAuthorize("hasAuthority('open:user:add')")
	public String add(ModelMap map) {
		List<SysRole> roleList = roleService.listAllRoles();
		map.put("roleList", roleList);
		
		return prefix + "/add";
	}

	@GetMapping("/edit/{id}")
	@PreAuthorize("hasAuthority('open:user:edit')")
	public String edit(@PathVariable("id") Long id, ModelMap map) {
		SysUser user = userService.getUserAndRolesById(id);
		map.put("user", user);
		
		List<SysRole> roleList = roleService.listAllRoles();
		map.put("roleList", roleList);
		
		return prefix + "/edit";
	}
	
	@PostMapping("/save")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:user:add')")
	@Log(BusinessName = "用户管理", OperationType = OperationType.INSERT, Content = "新增用户")
	public Result save(@Valid SysUser user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtil.error(bindingResult.getFieldError().getDefaultMessage());
		}
		
		if (userService.getUserByLoginname(user.getLoginname()) != null) {
			return ResultUtil.error("该登录名已经存在");
		}
		
		userService.insertUser(user);
		return ResultUtil.success();
	}
	
	@PostMapping("/update")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:user:edit')")
	@Log(BusinessName = "用户管理", OperationType = OperationType.UPDATE, Content = "修改用户")
	public Result update(@Valid SysUser user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtil.error(bindingResult.getFieldError().getDefaultMessage());
		}
		
		userService.updateUserAndRoles(user);
		return ResultUtil.success();
	}
	
	@PostMapping("/updateStatus")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:user:edit')")
	@Log(BusinessName = "用户管理", OperationType = OperationType.UPDATE, Content = "修改用户状态")
	public Result updateStatus(SysUser user) {
		userService.updateUser(user);
		return ResultUtil.success();
	}
	
	@PostMapping("/remove/{id}")
	@ResponseBody
	@PreAuthorize("hasAuthority('do:user:remove')")
	@Log(BusinessName = "用户管理", OperationType = OperationType.DELETE, Content = "删除用户")
	public Result remove(@PathVariable("id") Long id) {
		userService.deleteUser(id);
		return ResultUtil.success();
	}
	
	@GetMapping("/pwd")
	public String pwd() {
		return prefix + "/pwd";
	}
	
	@PostMapping("/updatePwd")
	@ResponseBody
	public Result updatePwd(String oldpwd, String newpwd, String confirmpwd, HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		
		if (StringUtils.isBlank(oldpwd)) {
			return ResultUtil.error("请输入账号的原登录密码");
		}
		if (StringUtils.isBlank(newpwd)) {
			return ResultUtil.error("请输入新的密码");
		}
		if (StringUtils.isBlank(confirmpwd)) {
			return ResultUtil.error("请确认新密码");
		}
		if (!newpwd.equals(confirmpwd)) {
			return ResultUtil.error("两次输入的新密码不同");
		}
		
		// 用户信息
		SysUser sessionUser = SecurityUtil.getAuthUser();
		
		if (!passwordEncoder.matches(oldpwd, sessionUser.getPassword())) {
			return ResultUtil.error("原登录密码错误");
		}
		
		SysUser user = new SysUser();
		user.setId(sessionUser.getId());
		user.setPassword(newpwd);
		
		// 日志信息
		SysLog sysLog = new SysLog();
		sysLog.setBusinessName("我的设置");
		sysLog.setOperationType(OperationType.UPDATE.toString());
		sysLog.setContent("修改密码");
		sysLog.setMethod(this.getClass().getName() + "." + "updatePwd");
		
		// 请求的参数
		Map<String, String[]> parameterMap = request.getParameterMap();
		if (parameterMap.size() > 0) {
			sysLog.setRequestParam(objectMapper.writeValueAsString(ReqUtil.converRequestMap()));
		}
		
		// 请求地址
		String URI = request.getRequestURI();
		sysLog.setUrl(URI);
		
		// 用户信息
		sysLog.setOperator(sessionUser.getId());
		String IP = SecurityUtil.getAuthDetails().getRemoteAddress();
		sysLog.setIp(IP);
		
		userService.updateUserPwd(user, sysLog);
		
		return ResultUtil.success();
		
	}
	
	@GetMapping("/checkLoginname")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:user:add')")
	public boolean checkLoginname(String loginname) {
		if (userService.getUserByLoginname(loginname) != null) {
			return false;
		}
		return true;
	}
	
	@GetMapping("/profile")
	public String profile(ModelMap map) {
		// 用户信息
		SysUser sessionUser = SecurityUtil.getAuthUser();
		map.put("user", sessionUser);
		return prefix + "/profile";
	}
	
	@PostMapping("/updateProfile")
	@ResponseBody
	@Log(BusinessName = "我的设置", OperationType = OperationType.UPDATE, Content = "修改个人信息")
	public Result updateProfile(SysUser user) {
		// 用户信息
		SysUser sessionUser = SecurityUtil.getAuthUser();
		
		user.setId(sessionUser.getId());
		userService.updateUser(user);
		
		SysUser newUser = userService.getUserById(sessionUser.getId());
		SecurityUtil.updateAuthPrincipal(newUser);
		return ResultUtil.success();
	}

}
