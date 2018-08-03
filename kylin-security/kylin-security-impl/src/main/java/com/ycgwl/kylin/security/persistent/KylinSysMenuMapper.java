package com.ycgwl.kylin.security.persistent;


import com.ycgwl.kylin.security.entity.KylinSysMenuEntity;

import java.util.List;

/**
 * 
 * <p>Title: KylinSysMenuMapper.java</p> 
 *@date 2018年5月17日 
 *@author ltao 112656
 *@version 1.0
 */
public interface KylinSysMenuMapper extends BaseMapper<KylinSysMenuEntity> {

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
	 * 查询用户的权限列表
	 */
	List<KylinSysMenuEntity> queryUserList(String grid);
}
