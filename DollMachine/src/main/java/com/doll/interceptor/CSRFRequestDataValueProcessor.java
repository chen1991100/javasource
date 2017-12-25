package com.doll.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.RequestDataValueProcessor;

import com.doll.utils.CsrfTokenManager;

@Component("requestDataValueProcessor")
public class CSRFRequestDataValueProcessor implements RequestDataValueProcessor {

	@Override
	public Map<String, String> getExtraHiddenFields(HttpServletRequest request) {
		Map<String, String> hiddenFields = new HashMap<String, String>();
        hiddenFields.put(CsrfTokenManager.CSRF_PARAM_NAME,CsrfTokenManager.createTokenForSession(request.getSession()));
        return hiddenFields;
	}

	@Override
	public String processAction(HttpServletRequest request, String action,
			String arg2) {
		return action;
	}

	@Override
    public String processFormFieldValue(HttpServletRequest request, String name, String value,
            String type)
	{
		return value;
	}

	@Override
	public String processUrl(HttpServletRequest request, String url) {
		// TODO Auto-generated method stub
		return url;
	}

}
