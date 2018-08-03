package com.ycgwl.kylin.web.master.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.ycgwl.kylin.annotation.SysLog;
import com.ycgwl.kylin.entity.R;
import com.ycgwl.kylin.exception.RRException;
import com.ycgwl.kylin.security.client.ContextHepler;
import com.ycgwl.kylin.sys.entity.Constant;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ycgwl.kylin.security.entity.KylinSysMenuEntity;
import com.ycgwl.kylin.security.entity.User;
import com.ycgwl.kylin.security.service.api.SysMenuService;


/**
 * 
 * <p>Title: SysMenuController.java</p> 
 *@date 2018年6月13日 
 *@author ltao 112656
 *@version 1.0
 */
@Controller
@RequestMapping("/sys/menu")
public class SysMenuController{
	
	@Resource
	private SysMenuService sysMenuService;

	/**
	 * 导航菜单
	 */
	@RequestMapping("/nav")
	@ResponseBody
	public R nav(){

		User user=	ContextHepler.getCurrentUser();
		//user = getUser();
	    String grid=user.getAccount();
		List<KylinSysMenuEntity> menuList = sysMenuService.getUserMenuList(grid);
		R put = R.ok().put("menuList", menuList);
		return put;
	}
	
	/**
	 *菜单管理的跳转
	 */
	@RequestMapping("/menuPage")
	public String menuPage(){
		
		return "sys/menu";
	}
	
	
	/**
	 * 权限设置的跳转
	 */
	@RequestMapping("/adminJump")
	public String adminJump(){
		
		return "sys/permissionSettings";
	}
	
	
	/**
	 * 所有菜单列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:menu:list")
	@ResponseBody
	public List<KylinSysMenuEntity> list(){
		List<KylinSysMenuEntity> menuList = sysMenuService.queryList(new HashMap<String, Object>());
		return menuList;
	}
	
	/**
	 * 选择菜单(添加、修改菜单)
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:menu:select")
	@ResponseBody
	public R select(){
		//查询列表数据
		List<KylinSysMenuEntity> menuList = sysMenuService.queryNotButtonList();
		
		//添加顶级菜单
		KylinSysMenuEntity root = new KylinSysMenuEntity();
		root.setMenuId((int) 0L);
		root.setName("一级菜单");
		root.setParentId((int) -1L);
		root.setOpen(true);
		menuList.add(root);
		
		return R.ok().put("menuList", menuList);
	}
	
	/**
	 * 菜单信息
	 */
	@RequestMapping("/info/{menuId}")
	@RequiresPermissions("sys:menu:info")
	@ResponseBody
	public R info(@PathVariable("menuId") Integer menuId){
		KylinSysMenuEntity menu = sysMenuService.queryObject(menuId);
		return R.ok().put("menu", menu);
	}
	
	
	/**
	 * 保存
	 */
	@SysLog("保存菜单")
	@RequestMapping("/save")
	@RequiresPermissions("sys:menu:save")
	@ResponseBody
	public R save(@RequestBody KylinSysMenuEntity menu){
		//数据校验
		verifyForm(menu);
		
		sysMenuService.save(menu);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@SysLog("修改菜单")
	@RequestMapping("/update")
	@RequiresPermissions("sys:menu:update")
	@ResponseBody
	public R update(@RequestBody KylinSysMenuEntity menu){
		//数据校验
		verifyForm(menu);
				
		sysMenuService.update(menu);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@SysLog("删除菜单")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:menu:delete")
	@ResponseBody
	public R delete(Integer menuId){
		//判断是否有子菜单或按钮
		List<KylinSysMenuEntity> menuList = sysMenuService.queryListParentId(menuId);
		if(menuList.size() > 0){
			return R.error("请先删除子菜单或按钮");
		}

		sysMenuService.deleteBatch(new Integer[]{menuId});
		
		return R.ok();
	}
	
	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(KylinSysMenuEntity menu){
		if(StringUtils.isBlank(menu.getName())){
			throw new RRException("菜单名称不能为空");
		}
		
		if(menu.getParentId() == null){
			throw new RRException("上级菜单不能为空");
		}
		
		//菜单
		if(menu.getType() == Constant.MenuType.MENU.getValue()){
			if(StringUtils.isBlank(menu.getUrl())){
				throw new RRException("菜单URL不能为空");
			}
		}
		
		//上级菜单类型
		int parentType = Constant.MenuType.CATALOG.getValue();
		if(menu.getParentId() != 0){
			KylinSysMenuEntity parentMenu = sysMenuService.queryObject(menu.getParentId());
			parentType = parentMenu.getType();
		}
		
		//目录、菜单
		if(menu.getType() == Constant.MenuType.CATALOG.getValue() ||
				menu.getType() == Constant.MenuType.MENU.getValue()){
			if(parentType != Constant.MenuType.CATALOG.getValue()){
				throw new RRException("上级菜单只能为目录类型");
			}
			return ;
		}
		
		//按钮
		if(menu.getType() == Constant.MenuType.BUTTON.getValue()){
			if(parentType != Constant.MenuType.MENU.getValue()){
				throw new RRException("上级菜单只能为菜单类型");
			}
			return ;
		}
	}
}
