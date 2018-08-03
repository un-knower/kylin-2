package com.ycgwl.kylin.security.persistent;

import com.ycgwl.kylin.security.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 
 * <p>Title: UserMapper.java</p> 
 *@date 2018年6月28日 
 *@author ltao 112656
 *@version 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

	/**
	 * 根据用户名(前端传过来是员工的工号)，查询系统用户
	 */
	User queryByUserName(String account);
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Integer> queryAllMenuId(String grid);
	

	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(String grid);
	
	/**
	 * 根据用户的grid获取到用户的信息
	 * @param grid
	 * @return
	 */
	User	queryByGrid(String grid);
	
}
