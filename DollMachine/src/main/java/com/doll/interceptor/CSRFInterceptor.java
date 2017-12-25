package com.doll.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.doll.utils.CsrfTokenManager;
/**
 * CSRF攻击提交处理拦截器
 * @author cq
 *
 */
public class CSRFInterceptor implements HandlerInterceptor {
	private static Logger logger=Logger.getLogger(CSRFInterceptor.class);
	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Object handler, Exception exception)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object handler, ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object handler) throws Exception {
		if ("POST".equalsIgnoreCase(httpServletRequest.getMethod())) {
			String CsrfToken = CsrfTokenManager.getTokenFromRequest(httpServletRequest);
			if (CsrfToken == null || !CsrfToken.equals(httpServletRequest.getSession().getAttribute(
                            CsrfTokenManager.CSRF_TOKEN_FOR_SESSION_ATTR_NAME))) {
				logger.info("CSRF验证不通过");
                String reLoginUrl = "";
                httpServletResponse.sendRedirect(reLoginUrl);
                return false;
            }
        }
        return true;
	}

}
