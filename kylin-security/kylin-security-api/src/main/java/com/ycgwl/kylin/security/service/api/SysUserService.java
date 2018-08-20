package com.ycgwl.kylin.security.service.api;


import com.ycgwl.kylin.exception.RRException;
import com.ycgwl.kylin.security.entity.KylinGongSiEntity;
import com.ycgwl.kylin.security.entity.User;

import java.util.List;
import java.util.Map;


/**
 * 系统用户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:43:39
 */
public interface SysUserService {

    /**
     * 根据用户的grid获取到用户信息
     *
     * @param grid
     * @return
     */
    User queryByGrid(String grid) throws RRException;

    /**
     * 查询用户的所有权限
     *
     * @param  grid
     */
    List<String> queryAllPerms(String grid);

    /**
     * 查询用户的所有菜单ID
     */
    List<Integer> queryAllMenuId(String grid);

    /**
     * 根据用户名，查询系统用户
     */
    User queryByUserName(String account);

    /**
     * 根据用户ID，查询用户
     * @param userId
     * @return
     */
    //SysUserEntity queryObject(Long userId);

    /**
     * 查询用户列表
     */
    List<User> queryList(Map<String, Object> map);

    /**
     * 查询总数
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 保存用户
     */
    void save(User user);

    /**
     * 修改用户
     */
    void update(User user);

    /**
     * 删除用户
     */
    void deleteBatch(Long[] userIds);

    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param password    原密码
     * @param newPassword 新密码
     */
    int updatePassword(String grid, String password, String newPassword);
    
    /**
     *根据用户选择的公司查询公司的编号
     * @param companyName
     * @return
     */
    KylinGongSiEntity queryByName(String companyName);
    
    
}
