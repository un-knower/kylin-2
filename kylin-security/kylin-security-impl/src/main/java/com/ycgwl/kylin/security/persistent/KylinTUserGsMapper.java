package com.ycgwl.kylin.security.persistent;

import com.ycgwl.kylin.security.entity.KylinTUserGsEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 
 * <p>Title: KylinTUserGsMapper.java</p> 
 *@date 2018年6月26日 
 *@author ltao 112656
 *@version 1.0
 *用户对用公司表的mapper接口
 */
@Mapper
public interface KylinTUserGsMapper extends BaseMapper<KylinTUserGsEntity> {

	List<KylinTUserGsEntity> queryList(String grid);
	
	
}
