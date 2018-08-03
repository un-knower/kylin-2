package com.ycgwl.kylin.security.service.impl;


import com.ycgwl.kylin.exception.RRException;
import com.ycgwl.kylin.security.entity.KylinGongSiEntity;
import com.ycgwl.kylin.security.entity.User;
import com.ycgwl.kylin.security.persistent.KylinGongSiMapper;
import com.ycgwl.kylin.security.persistent.KylinSysUserMenuMapper;
import com.ycgwl.kylin.security.persistent.UserMapper;
import com.ycgwl.kylin.security.service.api.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 */
@Service("kylin.security.dubbo.local.sysUserService")
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private UserMapper kylinSysYgzlMapper;
    @Autowired
    private KylinSysUserMenuMapper kylinSysGsMenuMapper;
    @Autowired
    private KylinGongSiMapper kylinGongSiMapper;

    @Override
    public List<String> queryAllPerms(String grid) {
        return kylinSysYgzlMapper.queryAllPerms(grid);
    }

    @Override
    public List<Integer> queryAllMenuId(String grid) {
        return kylinSysYgzlMapper.queryAllMenuId(grid);
    }

    @Override
    public User queryByUserName(String account) {
        System.out.println("根据姓名来获取user的全部信息");
        return kylinSysYgzlMapper.queryByUserName(account);
    }

    @Override
    public List<User> queryList(Map<String, Object> map) {

        List<User> list = kylinSysYgzlMapper.queryList(map);

        return list;
    }

    @Override
    /*@DataFilter(tableAlias = "u", user = false)*/
    public int queryTotal(Map<String, Object> map) {
        return kylinSysYgzlMapper.queryTotal(map);
    }

    @Override
    @Transactional
    public void save(User user) {
    }

    @Override
    @Transactional
    public void update(User user) {
	/*	if(StringUtils.isBlank(user.getPassword())){
			user.setPassword(null);
		}else{
			user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
		}
		kylinSysYgzlMapper.update(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());*/
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] userId) {
        kylinSysYgzlMapper.deleteBatch(userId);
    }

    @Override
    public int updatePassword(String grid, String password, String newPassword) {
        return 0;
		/*Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("password", password);
		map.put("newPassword", newPassword);
		return kylinSysYgzlMapper.updatePassword(map);*/
    }

    @Override
    public User queryByGrid(String grid) throws RRException {
        User returnSysYgzl = new User();
        try {
            returnSysYgzl = kylinSysYgzlMapper.queryByGrid(grid);
            if (returnSysYgzl == null) {
                throw new RRException("没有员工的信息");
            }

        } catch (Exception e) {
            throw new RRException("没有员工的信息");
        }
        return returnSysYgzl;
    }

	@Override
	public KylinGongSiEntity queryByName(String companyName) {
		
		
		return kylinGongSiMapper.queryByName(companyName);
	}

}
