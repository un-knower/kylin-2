package com.ycgwl.kylin.security.service.impl;

import com.ycgwl.kylin.exception.RRException;
import com.ycgwl.kylin.security.entity.KylinTUserGsEntity;
import com.ycgwl.kylin.security.persistent.KylinTUserGsMapper;
import com.ycgwl.kylin.security.service.api.IKylinTUserGsService;
import com.ycgwl.kylin.util.Assert;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <p>Title: KylinTUserGsServiceImpl.java</p> 
 *@date 2018年6月26日 
 *@author ltao 112656
 *@version 1.0
 *用户对照公司serviceImpl
 */

@Service("kylin.security.dubbo.local.kylinTUserGsService")
public class KylinTUserGsServiceImpl implements IKylinTUserGsService  {
	@Resource
	private KylinTUserGsMapper kylinTUserGsMapper;
	
	
	@Override
	public List<KylinTUserGsEntity> queryList(String grid) throws RRException {
		Assert.isBlank(grid, "暂未获取工号");
		
     List<KylinTUserGsEntity> returnGsList= new ArrayList<KylinTUserGsEntity>();
	try {
		returnGsList = kylinTUserGsMapper.queryList(grid);
	} catch (Exception e) {
		
		new RRException("查询失败");
	}
		
		return returnGsList;
	}

}
