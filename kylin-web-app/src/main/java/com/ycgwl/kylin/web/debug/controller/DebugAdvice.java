package com.ycgwl.kylin.web.debug.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

//@ControllerAdvice
public class DebugAdvice {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@ModelAttribute
	public String beforeSomething(HttpServletRequest request) {
		System.out.println("服务器名称:"+request.getServerName());
		System.out.println("端口号:"+request.getServerPort());
		System.out.println("项目名"+request.getContextPath());
		System.out.println("servlet路径"+request.getServletPath());
		System.out.println("参数"+request.getQueryString());
		System.out.println("请求uri"+request.getRequestURI());
		System.out.println("请求url"+request.getRequestURL());
		logger.info("before"+System.currentTimeMillis());
		return "current";
	}
}
