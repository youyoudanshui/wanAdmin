package com.wan.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hello")
public class HelloWorldController {
	
	@RequestMapping("/test")
	public String testController(ModelMap map) {
		map.put("tip", "测试页");
		return "test";
		
	}
}
