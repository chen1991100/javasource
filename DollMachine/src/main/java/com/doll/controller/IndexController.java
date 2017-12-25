package com.doll.controller;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
/*@Scope("prototype")*/ //默认单例,线程不安全;如果定义非静态成员变量,需开启多例模式 
public class IndexController {
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		return "/adminPage/main";
	}
	
}
