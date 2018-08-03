package com.ycgwl.kylin.transport.service.api;

import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;

import java.util.List;

public interface UndoLoadingService {

	/**
	 * 查询要撤销的运单号的详细信息
	 * <p>
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017年11月28日
	 * @param ydbhid
	 * @return
	 */
	JsonResult queryBundle(String ydbhid)throws Exception;

	/**
	 * 撤销操作
	 * <p>
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017年11月28日
	 * @param array
	 * @return
	 */
	JsonResult deleteBundle(String company,String account,String grid,List<String> xuhaos)throws ParameterException,BusinessException,Exception;

}
