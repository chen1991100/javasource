package com.doll.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doll.entity.AdminUser;
import com.doll.service.AdminUserService;
import com.doll.utils.RequestUtil;
import com.doll.utils.StaticUtils;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminUserService adminUserService;
	@RequestMapping("/login")
	public void toLogin(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		request.getRequestDispatcher("/adminPage/login.html").forward(request, response);
	}
	@RequestMapping("/validate")
	@ResponseBody
	public String validate(HttpServletRequest request,HttpServletResponse response){
		String userName = RequestUtil.getStringXss(request, "userName");
		String password = RequestUtil.getStringXss(request, "password");
		if(!StringUtils.isEmpty(userName)&&!StringUtils.isEmpty(password)){
			AdminUser adminUser = adminUserService.getAdminUserInfoByUserName(userName);
			if(adminUser!=null&&adminUser.getPassword().equals(password)){
				request.getSession().setAttribute(StaticUtils.ADMIN_LOGIN_SESSION, adminUser.getUsername());
				return "success";
			}
		}
		return "error";
	}
	  @RequestMapping({"/nav"})
	  public String nav(HttpServletRequest request) {
	    return "/adminPage/inc/nav";
	  }
	  @RequestMapping("/log_out")
	  public String log_out(HttpServletRequest request){
		  request.getSession().setAttribute(StaticUtils.ADMIN_LOGIN_SESSION, null);
		  return "redirect:/admin/login";
	  }
}
