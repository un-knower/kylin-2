package com.ycgwl.kylin.security.service.api;


import com.ycgwl.kylin.security.entity.User;

import java.util.Map;


/**
 * 角色
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:42:52
 */
public interface SysRoleService {
	
	/*SysRoleEntity queryObject(Long roleId);
	
	List<SysRoleEntity> queryList(Map<String, Object> map);*/
	
	int queryTotal(Map<String, Object> map);
	
	void save(User ygzl);
	
	void update(User ygzl);
	
	void deleteBatch(String[] grid);

}
