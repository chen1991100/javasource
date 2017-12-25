package com.doll.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
/*@Scope("prototype")*/ //默认单例,线程不安全;如果定义非静态成员变量,需开启多例模式 
@RequestMapping("/form")
public class FormController {
	@RequestMapping("/show")
	public String appList(HttpServletRequest request,HttpServletResponse response) {
		return "/form/test";
	}
	
	@RequestMapping("/json")
	@ResponseBody
	public Object jsonData(HttpServletRequest request,HttpServletResponse response) {
		Object object = null;
		return object;
	}
}
