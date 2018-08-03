package com.ycgwl.kylin.security.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ycgwl.kylin.exception.RRException;
import com.ycgwl.kylin.security.entity.User;
import com.ycgwl.kylin.security.persistent.KylinSysUserMenuMapper;
import com.ycgwl.kylin.security.service.api.SysUserMenuService;
import com.ycgwl.kylin.util.Assert;

/**
 * 角色与菜单对应关系
 */
@Service("kylin.security.dubbo.local.sysUserMenuService")
public class SysUserMenuServiceImpl implements SysUserMenuService {
	@Resource
	private KylinSysUserMenuMapper kylinSysUserMenuMapper;

	@Override
	@Transactional
	public void saveOrUpdate(String grid, List<Integer> menuIdList) {
		//先删除员工与菜单关系
		kylinSysUserMenuMapper.delete(grid);

		if(menuIdList.size() == 0){
			return ;
		}

		//保存员工与菜单关系
		Map<String, Object> map = new HashMap<>();
		map.put("grid", grid);
		map.put("menuIdList", menuIdList);
		kylinSysUserMenuMapper.save(map);
	}

	@Override
	public void save(User ygzl) {
		this.saveOrUpdate(ygzl.getAccount(), ygzl.getMenuIdList());
		
	}

	@Override
	public void update(User ygzl) throws RRException {
		Assert.isBlank(ygzl.getAccount(), "请先查询员工的信息，再进行提交");
		
		//Assert.isNull(ygzl, "请先输入员工的工号");
		
		
		
		this.saveOrUpdate(ygzl.getAccount(), ygzl.getMenuIdList());
		
	}


}
