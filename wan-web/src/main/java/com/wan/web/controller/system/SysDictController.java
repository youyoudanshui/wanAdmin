package com.wan.web.controller.system;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.wan.common.annotation.Log;
import com.wan.common.domain.Page;
import com.wan.common.domain.Result;
import com.wan.common.enumerate.OperationType;
import com.wan.common.util.ResultUtil;
import com.wan.system.domain.SysDict;
import com.wan.system.service.SysDictService;
import com.wan.web.controller.common.BaseController;

/**
 * 数字字典管理
 * @author wmj
 *
 */
@Controller
@RequestMapping("/system/dict")
public class SysDictController extends BaseController {
	
	@Autowired
	private SysDictService dictService;
	
	private String prefix = "system/dict";
	
	@GetMapping("")
	@PreAuthorize("hasAuthority('open:dict:manage')")
	public String dict() {
		return prefix + "/dict";
	}
	
	@GetMapping("/list")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:dict:manage')")
	public PageInfo<SysDict> list(SysDict dict, Page page) {
		startPage(page);
		List<SysDict> list = dictService.listDicts(dict);
		PageInfo<SysDict> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	@GetMapping("/add")
	@PreAuthorize("hasAuthority('open:dict:add')")
	public String add(ModelMap map) {
		return prefix + "/add";
	}

	@GetMapping("/edit/{id}")
	@PreAuthorize("hasAuthority('open:dict:edit')")
	public String edit(@PathVariable("id") Long id, ModelMap map) {
		SysDict dict = dictService.getDictById(id);
		map.put("dict", dict);
		return prefix + "/edit";
	}
	
	@PostMapping("/save")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:dict:add')")
	@Log(BusinessName = "数据字典管理", OperationType = OperationType.INSERT, Content = "新增类型")
	public Result save(@Valid SysDict dict, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtil.error(bindingResult.getFieldError().getDefaultMessage());
		}
		
		if (dictService.getDictByTypeName(dict.getTypeName()) != null) {
			return ResultUtil.error("该数据类型已经存在");
		}
		
		dictService.insertDict(dict);
		return ResultUtil.success();
	}
	
	@PostMapping("/update")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:dict:edit')")
	@Log(BusinessName = "数据字典管理", OperationType = OperationType.UPDATE, Content = "修改类型")
	public Result update(@Valid SysDict dict, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtil.error(bindingResult.getFieldError().getDefaultMessage());
		}
		
		dictService.updateDict(dict);
		return ResultUtil.success();
	}
	
	@PostMapping("/updateStatus")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:dict:edit')")
	@Log(BusinessName = "数据字典管理", OperationType = OperationType.UPDATE, Content = "修改类型状态")
	public Result updateStatus(SysDict dict) {
		dictService.updateDict(dict);
		return ResultUtil.success();
	}
	
	@PostMapping("/remove/{id}")
	@ResponseBody
	@PreAuthorize("hasAuthority('do:dict:remove')")
	@Log(BusinessName = "数据字典管理", OperationType = OperationType.DELETE, Content = "删除类型")
	public Result remove(@PathVariable("id") Long id) {
		dictService.deleteDict(id);
		return ResultUtil.success();
	}
	

	@GetMapping("/removeCache")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:dict:manage')")
	@CacheEvict(value="dictData", allEntries=true)
	public Result removeCache() {
		return ResultUtil.success();
	}
	
	@GetMapping("/checkTypeName")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:dict:add')")
	public boolean checkTypeName(String typeName) {
		if (dictService.getDictByTypeName(typeName) != null) {
			return false;
		}
		return true;
	}

}
