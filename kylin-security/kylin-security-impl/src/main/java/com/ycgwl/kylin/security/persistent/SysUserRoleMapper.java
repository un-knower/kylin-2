package com.ycgwl.kylin.security.persistent;


import com.ycgwl.kylin.sys.entity.SysUserRoleEntity;

import java.util.List;


/**
 * 用户与角色对应关系
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRoleEntity> {
	
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);
}
