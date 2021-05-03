package com.wan.web.controller.system;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.wan.system.domain.SysDictData;
import com.wan.system.service.SysDictDataService;
import com.wan.system.service.SysDictService;
import com.wan.web.controller.common.BaseController;

/**
 * 数字字典管理
 * @author wmj
 *
 */
@Controller
@RequestMapping("/system/dict/data")
public class SysDictDataController extends BaseController {
	
	@Autowired
	private SysDictDataService dictDataService;
	
	@Autowired
	private SysDictService dictService;
	
	private String prefix = "system/dict/data";
	
	@GetMapping("/{dictId}")
	@PreAuthorize("hasAuthority('open:dict:manage')")
	public String data(@PathVariable("dictId") Long dictId, ModelMap map) {
		SysDict dict = dictService.getDictById(dictId);
		map.put("dict", dict);
		return prefix + "/data";
	}
	
	@GetMapping("/list")
	@ResponseBody
	@PreAuthorize("hasAuthority('open:dict:manage')")
	public PageInfo<SysDictData> list(SysDictData data, Page page) {
		startPage(page);
		List<SysDictData> list = dictDataService.listDictDatas(data);
		PageInfo<SysDictData> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	@GetMapping("/add/{dictId}")
	@PreAuthorize("hasAuthority('open:dict:manage')")
	public String add(@PathVariable("dictId") Long dictId, ModelMap map) {
		SysDict dict = dictService.getDictById(dictId);
		map.put("dict", dict);
		return prefix + "/add";
	}

	@GetMapping("/edit/{id}")
	@PreAuthorize("hasAuthority('open:dict:manage')")
	public String edit(@PathVariable("id") Long id, ModelMap map) {
		SysDictData data = dictDataService.getDictDataById(id);
		map.put("data", data);
		return prefix + "/edit";
	}
	
	@PostMapping("/save")
	@ResponseBody
	@PreAuthorize("hasAuthority('do:dict:add')")
	@Log(BusinessName = "数据字典管理", OperationType = OperationType.INSERT, Content = "新增类型数据")
	public Result save(@Valid SysDictData data, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtil.error(bindingResult.getFieldError().getDefaultMessage());
		}
		
		dictDataService.insertDictData(data);
		return ResultUtil.success();
	}
	
	@PostMapping("/update")
	@ResponseBody
	@PreAuthorize("hasAuthority('do:dict:edit')")
	@Log(BusinessName = "数据字典管理", OperationType = OperationType.UPDATE, Content = "修改类型数据")
	public Result update(@Valid SysDictData data, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResultUtil.error(bindingResult.getFieldError().getDefaultMessage());
		}
		
		dictDataService.updateDictData(data);
		return ResultUtil.success();
	}
	
	@PostMapping("/updateStatus")
	@ResponseBody
	@PreAuthorize("hasAuthority('do:dict:edit')")
	@Log(BusinessName = "数据字典管理", OperationType = OperationType.UPDATE, Content = "修改类型数据状态")
	public Result updateStatus(SysDictData data) {
		SysDictData o_data = dictDataService.getDictDataById(data.getId());
		data.setTypeName(o_data.getTypeName());
		data.setKey(o_data.getKey());
		dictDataService.updateDictData(data);
		return ResultUtil.success();
	}
	
	@PostMapping("/remove/{id}")
	@ResponseBody
	@PreAuthorize("hasAuthority('do:dict:remove')")
	@Log(BusinessName = "数据字典管理", OperationType = OperationType.DELETE, Content = "删除类型数据")
	public Result remove(@PathVariable("id") Long id) {
		SysDictData data = dictDataService.getDictDataById(id);
		dictDataService.deleteDictData(data);
		return ResultUtil.success();
	}
	
	@GetMapping("/getValue")
	@ResponseBody
	public Result getValue(SysDictData data) {
		String dataValue = dictDataService.getDictDataValue(data);
		return ResultUtil.success(dataValue);
	}
	
	@GetMapping("/getList")
	@ResponseBody
	public Result getList(SysDictData data) {
		List<SysDictData> dataList = dictDataService.getDictDataList(data);
		return ResultUtil.success(dataList);
	}

}
