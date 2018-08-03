package com.ycgwl.kylin.security.util;

public interface Constant {

	int ADMIN_ROLE = 1;//管理员角色
	int DEFAULT_ROLE = 2;//默认员工角色
	
	String IS_AUTHENTICATED_FULLY = "IS_AUTHENTICATED_FULLY";//登录才能访问
	String IS_AUTHENTICATED_DISABLED = "IS_AUTHENTICATED_DISABLED";//不能访问任何资源
	
	
	int RES_TYPE_MENU = 1;//菜单
	int RES_TYPE_ELEMENT = 2;//按钮
}
