package com.ycgwl.kylin.security.service.impl;


import com.ycgwl.kylin.security.entity.KylinSysMenuEntity;
import com.ycgwl.kylin.security.persistent.KylinSysMenuMapper;
import com.ycgwl.kylin.security.service.api.SysMenuService;
import com.ycgwl.kylin.security.service.api.SysUserService;
import com.ycgwl.kylin.sys.entity.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("kylin.security.dubbo.local.sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {
	@Autowired
	private KylinSysMenuMapper kylinSysMenuMapper;
	@Resource
	private SysUserService sysUserService;
	
	@Override
	public List<KylinSysMenuEntity> queryListParentId(Integer parentId, List<Integer> menuIdList) {
		List<KylinSysMenuEntity> menuList = queryListParentId(parentId);
		if(menuIdList == null){
			return menuList;
		}
		
		List<KylinSysMenuEntity> userMenuList = new ArrayList<>();
		for(KylinSysMenuEntity menu : menuList){
			if(menuIdList.contains(menu.getMenuId())){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}
	

	@Override
	public List<KylinSysMenuEntity> queryListParentId(Integer parentId) {
		return kylinSysMenuMapper.queryListParentId(parentId);
	}

	@Override
	public List<KylinSysMenuEntity> queryNotButtonList() {
		return kylinSysMenuMapper.queryNotButtonList();
	}

	@Override
	public List<KylinSysMenuEntity> getUserMenuList(String grid) {
	
		//用户菜单列表
		List<Integer> menuIdList = sysUserService.queryAllMenuId(grid);
		
		return getAllMenuList(menuIdList);
	}
	
	@Override
	public KylinSysMenuEntity queryObject(Integer menuId) {
		
		return kylinSysMenuMapper.queryObject(menuId);
	}

	@Override
	public List<KylinSysMenuEntity> queryList(Map<String, Object> map) {
		
		 return kylinSysMenuMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return kylinSysMenuMapper.queryTotal(map);
	}

	@Override
	public void save(KylinSysMenuEntity menu) {
		kylinSysMenuMapper.save(menu);
	}

	@Override
	public void update(KylinSysMenuEntity menu) {
		kylinSysMenuMapper.update(menu);
	}

	@Override
	@Transactional
	public void deleteBatch(Integer[] menuIds) {
		kylinSysMenuMapper.deleteBatch(menuIds);
	}
	
	@Override
	public List<KylinSysMenuEntity> queryUserList(String grid) {
		return kylinSysMenuMapper.queryUserList(grid);
	}

	/**
	 * 获取所有菜单列表//1
	 */
	private List<KylinSysMenuEntity> getAllMenuList(List<Integer> menuIdList){
	
		//查询根菜单列表
		List<KylinSysMenuEntity> menuList = queryListParentId((int) 0L, menuIdList);
	
		//递归获取子菜单
		getMenuTreeList(menuList, menuIdList);
		
		return menuList;
	}

	/**
	 * 递归
	 */
	private List<KylinSysMenuEntity> getMenuTreeList(List<KylinSysMenuEntity> menuList, List<Integer> menuIdList){
		List<KylinSysMenuEntity> subMenuList = new ArrayList<KylinSysMenuEntity>();
		
		for(KylinSysMenuEntity entity : menuList){
			if(entity.getType() == Constant.MenuType.CATALOG.getValue()){//目录
				entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
			}
			subMenuList.add(entity);
		}
		
		return subMenuList;
	}

}
