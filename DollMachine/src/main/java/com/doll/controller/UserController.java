package com.doll.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doll.pagebean.DataTableBean;
import com.doll.pagebean.ParamListBean;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@RequestMapping("/list")
	@ResponseBody
	public DataTableBean getUserInfo(HttpServletRequest request,HttpServletResponse response){
	    return null;
	}
}
