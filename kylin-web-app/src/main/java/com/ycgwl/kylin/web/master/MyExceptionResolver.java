package com.ycgwl.kylin.web.master;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.json.JSON;
import com.ycgwl.kylin.entity.R;

import net.minidev.json.JSONUtil;

public class MyExceptionResolver implements HandlerExceptionResolver{

	@Override
	public ModelAndView resolveException(HttpServletRequest request, 
			                             HttpServletResponse response, 
			                             Object handler,Exception ex) {

		System.out.println("==============异常开始=============");
		if(ex instanceof UnauthorizedException){
			String detail=((UnauthorizedException)ex).getMessage();
			int startIndex=detail.indexOf("[");
			String permissionStr=detail.substring(startIndex,detail.length());
			String tips="你没有该功能的权限"+permissionStr+"请联系管理员";
	
			try {
				response.setHeader("Content-Type", "application/json");
				response.setHeader("Content-Encoding", "utf-8");
				response.getWriter().write(JSON.json(R.error(tips)));
			} catch (Exception e) { 
				e.printStackTrace();
			}
		}

		return null;
	}
}

