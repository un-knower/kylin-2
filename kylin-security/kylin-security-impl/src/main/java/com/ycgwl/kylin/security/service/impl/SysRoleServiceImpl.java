package com.ycgwl.kylin.security.service.impl;

import com.ycgwl.kylin.security.entity.User;
import com.ycgwl.kylin.security.service.api.SysRoleService;
import com.ycgwl.kylin.security.service.api.SysUserMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 角色
 */
@Service("kylin.security.dubbo.local.sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {
	/*@Resource
	private SysRoleMapper sysRoleMapper;*/
	@Resource
	private SysUserMenuService sysRoleMenuService;
	
/*
	@Override
	public SysRoleEntity queryObject(Long roleId) {
		return sysRoleMapper.queryObject(roleId);
	}

	@Override
	@DataFilter(tableAlias = "r", user = false)
	public List<SysRoleEntity> queryList(Map<String, Object> map) {
		return sysRoleMapper.queryList(map);
	}*/

	@Override
	public int queryTotal(Map<String, Object> map) {
		return 0;
	
	}

	@Override
	@Transactional
	public void save(User ygzl) {
		//保存角色与菜单关系
		sysRoleMenuService.saveOrUpdate(ygzl.getAccount(), ygzl.getMenuIdList());

	}
	@Override
	@Transactional
	public void update(User ygzl) {
		//更新角色与菜单关系
		sysRoleMenuService.saveOrUpdate(ygzl.getAccount(), ygzl.getMenuIdList());
	}

	@Override
	@Transactional
	public void deleteBatch(String[] grid) {
		/*sysRoleMapper.deleteBatch(grid);*/
	}

}
