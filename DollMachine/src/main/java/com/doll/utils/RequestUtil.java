package com.doll.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;


public class RequestUtil {
	
	public static String getStringXss(HttpServletRequest request, String key) {
		String str = "";
		str = getString(request, key);
		if(!StringUtils.isEmpty(str)){
			str = Jsoup.clean(str, Whitelist.basicWithImages());
		}
		return str;
	}
	
	public static String getString(HttpServletRequest request, String key) {
		return request.getParameter(key);
	}
	public static int getInt(HttpServletRequest request, String key) {
		int i = 0;
		try {
			 String temp = getString(request,key);
			 if (temp.equals(""))
				 return i;
			 else
				 i = Integer.parseInt(temp);
		} catch (NumberFormatException e) {}
		return i;
	}
	public static long getLong(HttpServletRequest request,String key) {
		long l = 0;
		try {
			 String temp = getString(request,key);
			 if (temp.equals(""))
				 return l;
			 else
				 l = Long.parseLong(temp);
		} catch (NumberFormatException e) {}
		return l;
	}

	public static float getFloat(HttpServletRequest request,String key) {
		float f = 0.0f;
		try {
			 String temp = getString(request,key);
			 if (temp.equals(""))
				 return f;
			 else
				 f = Float.parseFloat(temp);
		} catch (NumberFormatException e) {}
		return f;
	}
	
	public static double getDouble(HttpServletRequest request,String key) {
		double d = 0;
		try {
			 String temp = getString(request,key);
			 if (temp.equals(""))
				 return d;
			 else
				 d = Double.parseDouble(temp);
		} catch (NumberFormatException e) {}
		return d;
	}
	public static String[] getValues(HttpServletRequest request,String key) {
	    return request.getParameterValues(key);
	}
}
