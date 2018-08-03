package com.ycgwl.kylin.security.service.api;

import java.util.List;

import com.ycgwl.kylin.exception.RRException;
import com.ycgwl.kylin.security.entity.User;


/**
 * 用户与菜单对应关系
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:42:30
 */
public interface SysUserMenuService {
	
	//void saveOrUpdate(Long roleId, List<Long> menuIdList);
	
	//List<Long> queryMenuIdList(Long roleId);

	void saveOrUpdate(String grid, List<Integer> menuIdList);
	 void save(User ygzl);
		
		void update(User ygzl) throws RRException;
	
}
