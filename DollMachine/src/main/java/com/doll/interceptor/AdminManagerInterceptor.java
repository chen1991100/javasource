package com.doll.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.doll.utils.StaticUtils;

public class AdminManagerInterceptor implements HandlerInterceptor {
	private static Logger logger=Logger.getLogger(AdminManagerInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Object object, Exception exception)
			throws Exception {
		logger.info("当request处理完成后被调用");
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object object, ModelAndView exception) throws Exception {
		 logger.info("在handler被执行后被调用");
	}

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object object) throws Exception {
		 HttpSession httpSession = httpServletRequest.getSession();
		 if(httpSession.getAttribute(StaticUtils.ADMIN_LOGIN_SESSION)==null){
			 httpServletResponse.sendRedirect("admin/login");
			 //httpServletRequest.getRequestDispatcher("adminPage/login.html").forward(httpServletRequest, httpServletResponse);
			 return false;
		 }
	     return true;
	}

}
