package com.ycgwl.kylin.security.service.api;


import com.ycgwl.kylin.security.entity.KylinSysMenuEntity;

import java.util.List;
import java.util.Map;


/**
 * 菜单管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:42:16
 */

public interface SysMenuService {
	
	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 * @param menuIdList  用户菜单ID
	 */
	List<KylinSysMenuEntity> queryListParentId(Integer parentId, List<Integer> menuIdList);

	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	List<KylinSysMenuEntity> queryListParentId(Integer parentId);
	
	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<KylinSysMenuEntity> queryNotButtonList();
	
	/**
	 * 获取用户菜单列表
	 */
	List<KylinSysMenuEntity> getUserMenuList(String grid);
	
	/**
	 * 查询菜单
	 */
	KylinSysMenuEntity queryObject(Integer menuId);
	
	/**
	 * 查询菜单列表
	 */
	List<KylinSysMenuEntity> queryList(Map<String, Object> map);
	
	/**
	 * 查询总数
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * 保存菜单
	 */
	void save(KylinSysMenuEntity menu);
	
	/**
	 * 修改
	 */
	void update(KylinSysMenuEntity menu);
	
	/**
	 * 删除
	 */
	void deleteBatch(Integer[] menuIds);
	
	/**
	 * 查询用户的权限列表
	 */
	List<KylinSysMenuEntity> queryUserList(String grid);
}
