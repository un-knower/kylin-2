package com.ycgwl.kylin.security.persistent;


import com.ycgwl.kylin.security.entity.KylinSysUserMenuEntity;

import java.util.List;


/**
 * 
 * <p>Title: KylinSysGrczqxMapper.java</p> 
 *@date 2018年5月17日 
 *@author ltao 112656
 *@version 1.0
 */
public interface KylinSysUserMenuMapper extends BaseMapper<KylinSysUserMenuEntity> {


	/**
	 * 根据员工的工号，获取菜单ID列表
	 */
	List<String> queryMenuIdList(String grid);
	
	
}
