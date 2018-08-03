package com.ycgwl.kylin.util.http;

import javax.servlet.http.HttpServletRequest;

public class RequestUitl {

	
	public static boolean isAjax(HttpServletRequest request){
		boolean ajax = "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
		String request_type = request.getHeader("Request-Type");
		if(request_type != null && "ajax".equalsIgnoreCase(request_type)){
			ajax = true;
		}
		return ajax;
	}
}
