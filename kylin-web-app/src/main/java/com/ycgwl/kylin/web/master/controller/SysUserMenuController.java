package com.ycgwl.kylin.web.master.controller;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ycgwl.kylin.entity.R;
import com.ycgwl.kylin.exception.RRException;
import com.ycgwl.kylin.security.entity.User;
import com.ycgwl.kylin.security.service.api.SysUserMenuService;
import com.ycgwl.kylin.util.ValidatorUtils;

/**
 * 
 * <p>Title: SysUserMenuController.java</p> 
 *@date 2018年7月2日 
 *@author ltao 112656
 *@version 1.0
 */
@RestController
@RequestMapping("/sys/userMenu")
public class SysUserMenuController {

	
	@Resource
	private SysUserMenuService sysUserMenuService;
	/**
	 * 添加用户的权限
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:userMenu:save")
	public R save(@RequestBody User ygzl){
		ValidatorUtils.validateEntity(ygzl);
		
		sysUserMenuService.save(ygzl);
		
		return R.ok();
	}
	
	/**
	 * 修改用户对应的权限
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:userMenu:update")
	public R update(@RequestBody User ygzl){
	ValidatorUtils.validateEntity(ygzl);
	R r= new R();
	
		try {
			sysUserMenuService.update(ygzl);
			return R.ok();
		} catch (RRException e) {
			r.put("code", 400);
			r.put("msg", e.getMsg());
		}
		return r;
		
	}
	

}
