package com.ycgwl.kylin.security.service.api;


import com.ycgwl.kylin.exception.RRException;
import com.ycgwl.kylin.security.entity.KylinTUserGsEntity;



import java.util.List;

/**
 * 
 * <p>Title: IKylinTUserGsService.java</p> 
 *@date 2018年6月26日 
 *@author ltao 112656
 *@version 1.0
 *用户对照公司service接口
 */
public interface IKylinTUserGsService {
	/**
	 * 根据工号查询工号下面的所有的公司
	 * @param grid
	 * @return
	 */
	List<KylinTUserGsEntity> queryList(String grid) throws RRException;
	


}
