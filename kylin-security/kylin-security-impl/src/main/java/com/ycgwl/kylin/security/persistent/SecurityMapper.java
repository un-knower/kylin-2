package com.ycgwl.kylin.security.persistent;

import com.ycgwl.kylin.entity.Page;
import com.ycgwl.kylin.security.entity.Resource;
import com.ycgwl.kylin.security.entity.Role;
import com.ycgwl.kylin.security.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface SecurityMapper {
	/**
	 * 根据用户编号获取用户信息
	 * @param userId
	 * @return
	 */
	public User getUserByAccount(String account);
	
	public List<User> getUserListByAccount(String account);
	
	/**
	 * 查询用户信息
	 * @param user
	 * @return
	 */
	public Collection<User> queryUserBySomething(User user);
	
	/**
	 * 分页查询用户信息
	 * @param user
	 * @param bounds
	 * @return
	 */
	public Page<User> queryUserBySomething(User user, RowBounds bounds);
	/**
	 * 插入用户信息
	 * @param user
	 */
	public Integer insertUser(User user);
	/**
	 * 更新用户信息
	 * @param user
	 */
	public void updateUser(User user);
	
	/**
	 * 启用/禁用指定用户
	 * @param userId
	 * @param enable
	 */
	public void enableUser(@Param("account") String account, @Param("enable") Boolean enable);
	
	/**
	 * 查询指定用户的角色信息
	 * @param userId
	 * @return
	 */
	public Collection<Role> queryRolesByUid(String account);

	/**
	 * 根据角色编号查询资源信息
	 * @param roleId
	 * @return
	 */
	public Collection<Resource> queryResourceByRoleId(Integer roleId); 
	/**
	 * 查询资源信息，如果该角色拥有该资源则带上角色ID
	 * @param parentId
	 * @param roleId
	 * @return
	 */
	public Collection<Resource> queryResourceWhitRoleIDByRoleID(@Param("parentId") Integer parentId, @Param("roleId") Integer roleId);
	
	/**
	 * 查询资源信息
	 * @param userName
	 * @param parentId
	 * @return
	 */
	public Collection<Resource> queryResourceRootByUserName(@Param("account") String account, @Param("parentId") Integer parentId);

	/**
	 * 查询资源信息
	 * @param userName
	 * @param parentId
	 * @return
	 */
	public Collection<Resource> queryResourceByUserName(@Param("account") String account, @Param("parentId") Integer parentId);
	
	
	/**
	 * 分页查询资源信息
	 * @param resource
	 * @return
	 */
	public Page<Resource> queryResourceBySomething(Resource resource, RowBounds bounds);
	public Collection<Resource> queryResourceBySomething(Resource resource);
	/**
	 * 根据父编号查询资源信息
	 * @param parentId  父编号
	 * @return
	 */
	public Collection<Resource> queryResourceByParentId(Integer parentId);
	/**
	 * 根据资源编号查询资源信息
	 * @param resourceId
	 * @return
	 */
	public Resource getResourceByID(Integer resourceId);
	/**
	 * 插入资源信息
	 * @param resource 资源信息
	 */
	public Integer insertResource(Resource resource);
	/**
	 * 更新资源信息
	 * @param resource 资源信息，资源ID不能为空
	 */
	public void updateResource(Resource resource);
	/**
	 * 更新资源状态
	 * @param resourceId
	 * @param enable
	 */
	public void enableResource(@Param("resourceId") Integer resourceId, @Param("enable") Boolean enable);
	/**
	 * 根据角色编号查询角色信息
	 * @param roleId
	 * @return
	 */
	public Role getRoleByID(Integer roleId);
	/**
	 * 查询角色信息
	 * @return
	 */
	public Collection<Role> queryRole();
	/**
	 * 分页查询角色信息
	 * @param resource
	 * @return
	 */
	public Page<Role> queryRoleBySomething(Role role, RowBounds bounds);
	/**
	 * 查询角色信息
	 * @param role
	 * @return
	 */
	public Collection<Role> queryRoleBySomething(Role role);
	/**
	 * 查询角色信息
	 * @param enable 是否启用
	 * @return
	 */
	public Collection<Role> queryRoleByEnable(Boolean enable);
	/**
	 * 保存角色信息
	 * @param role
	 */
	public Integer insertRole(Role role);
	/**
	 * 修改角色信息
	 * @param role
	 */
	public void updateRole(Role role);
	/**
	 * 修改角色状态
	 * @param roleId
	 * @param enable 是否启用
	 */
	public void enableRole(@Param("roleId") Integer roleId, @Param("enable") Boolean enable);
	/**
	 * 关联用户和角色信息
	 * @param associates key=角色编号 value=用户编号
	 */
	public void associateUserRole(@Param("associates") Map<Integer, String> associates);
	/**
	 * 关联角色和资源信息
	 * @param associates key=资源编号 value=角色编号
	 */
	public void associateRoleResource(@Param("associates") Map<Integer, Integer> associates);
	/**
	 * 查询用户关联的所有角色编号
	 * @param account 用户编号
	 */
	public Collection<Integer> queryAssociateUserRole(String account);
	/**
	 * 查询角色关联的所有资源编号
	 * @param roleId 角色编号
	 */
	public Collection<Integer> queryAssociateRoleResource(Integer roleId);
	/**
	 * 删除用户关联的所有角色编号
	 * @param account 用户编号
	 */
	public void deleteAssociateUserRole(String account);
	/**
	 * 删除角色关联的所有资源编号
	 * @param roleId 角色编号
	 */
	public void deleteAssociateRoleResource(Integer roleId);
	
	/**
	  * 验证是否有该角色
	  */
	public Integer validateRole(@Param("account") String account, @Param("roleId") Integer roleId);
	public Integer mapperUserRole(@Param("account") String account, @Param("roleId") Integer roleId);

	@Select("select bh FROM gongsi WHERE name = #{company} ")
	public String getCompanyCodeBycompany(String company);

	@Select("SELECT gs FROM T_USER_GS WHERE grid = #{account} and nf= '2009'")
	public List<String> getCompanyListByAccount(String account);

}
