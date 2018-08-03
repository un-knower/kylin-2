package com.ycgwl.kylin.web.master;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommonInterceptor extends HandlerInterceptorAdapter {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		/*
		host = localhost
		connection = keep-alive
		content-length = 56
		accept = application/json, text/javascript
		origin = http://localhost
		user-agent = Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36
		content-type = application/json; charset=UTF-8
		referer = http://localhost/report/operate/company
		accept-encoding = gzip, deflate, br
		accept-language = zh-CN,zh;q=0.8
		cookie = JSESSIONID=3128D72420674D09A3EFE287D3B3A8E4
		*/
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String url = request.getServletPath();
		String params = request.getQueryString();
		if(params == null){
			logger.debug("url:{}", url);
		}else{
			logger.debug("url:{}, params:{}", url, params);
		}
		return true;
	}
}
