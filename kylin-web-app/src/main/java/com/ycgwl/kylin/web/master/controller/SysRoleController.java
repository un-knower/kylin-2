package com.ycgwl.kylin.web.master.controller;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ycgwl.kylin.annotation.SysLog;
import com.ycgwl.kylin.entity.R;
import com.ycgwl.kylin.security.service.api.SysRoleService;
import com.ycgwl.kylin.security.service.api.SysUserMenuService;
/**
 * @Descrption 角色管理
 * @email <a href="109668@ycgwl.com">lihuixia</a>
 * @date 2018年04月02日 上午08:27:15
 */
@Controller
@RequestMapping("/sys/role")
public class SysRoleController{
	
	
	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private SysUserMenuService sysUserMenuService;
	
	
	/**
	 * 跳转页面
	 */
	@RequestMapping("/roleJump")
	public String roleJump(){
		System.out.println("角色设置");
		return "sys/role";
	}
	
	
	
	/**
	 * 角色列表
	 *//*
	@RequestMapping("/list")
	@RequiresPermissions("sys:role:list")
	public R list(@RequestParam Map<String, Object> params){
		return null;
		
		//如果不是超级管理员，则只查询自己创建的角色列表
		if(getUserId() != Constant.SUPER_ADMIN){
			params.put("createUserId", getUserId());
		}
		
		//查询列表数据
		Query query = new Query(params);
		List<SysRoleEntity> list = sysRoleService.queryList(query);
		int total = sysRoleService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 角色列表
	 /*
	@RequestMapping("/select")
	@RequiresPermissions("sys:role:select")
	public R select(){
		return null;
		Map<String, Object> map = new HashMap<>();

		//如果不是超级管理员，则只查询自己所拥有的角色列表
		if(getUserId() != Constant.SUPER_ADMIN){
			map.put("createUserId", getUserId());
		}
		List<SysRoleEntity> list = sysRoleService.queryList(map);
		
		return R.ok().put("list", list);
	}
	
	/**
	 * 角色信息
	 /*
	@RequestMapping("/info/{roleId}")
	@RequiresPermissions("sys:role:info")
	public R info(@PathVariable("roleId") Long roleId){
		SysRoleEntity role = sysRoleService.queryObject(roleId);
		
		//查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);

		//查询角色对应的部门
		List<Long> deptIdList = sysRoleDeptService.queryDeptIdList(roleId);
		role.setDeptIdList(deptIdList);
		
		return R.ok().put("role", role);
	}
	
	/**
	 * 保存角色
	 /*
	@SysLog("保存角色")
	@RequestMapping("/save")
	//@RequiresPermissions("sys:role:save")
	public R save(@RequestBody User ygzl){
		ValidatorUtils.validateEntity(ygzl);
		
		sysRoleService.save(ygzl);
		
		return R.ok();
	}
	
	/**
	 * 修改角色
	 *//*
	@SysLog("修改角色")
	@RequestMapping("/update")
	//@RequiresPermissions("sys:role:update")
	public R update(@RequestBody User ygzl){
		ValidatorUtils.validateEntity(ygzl);
		
		sysRoleService.update(ygzl);
		
		return R.ok();
	}
	
	/**
	 * 删除角色
	 */
	@SysLog("删除角色")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:role:delete")
	public R delete(@RequestBody String[] grid){
		sysRoleService.deleteBatch(grid);
		
		return R.ok();
	}
}
