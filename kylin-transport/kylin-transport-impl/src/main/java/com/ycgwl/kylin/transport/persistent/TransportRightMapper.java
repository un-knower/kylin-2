package com.ycgwl.kylin.transport.persistent;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TransportRightMapper {
	
	/**
	 * 查询某个菜单的权限
	 * @param menuId
	 * @param grid
	 * @param gs
	 * @return (0或Null没有 1 普通   3最高)
	 */
	@Select("select max(a.limit) as rightNum from (select limit from T_USERRIGHT where GKH = #{grid} and LIMIT>0 and menu_id = 'QL_${menuId}' union SELECT limit FROM V_USER_GROUP_RIGHT WHERE grid = #{grid} and menu_id = 'QL_${menuId}') a")
	public Integer getRightNum(@Param(value = "menuId")String menuId,@Param(value = "grid")String grid);
}
