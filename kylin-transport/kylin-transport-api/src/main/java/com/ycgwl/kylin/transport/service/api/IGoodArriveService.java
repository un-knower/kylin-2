package com.ycgwl.kylin.transport.service.api;

import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.entity.RequestJsonEntity;
import com.ycgwl.kylin.exception.ParameterException;

import java.text.ParseException;
import java.util.HashMap;

/**
 * 到货的业务处理层
 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017-12-15 16:33:55
 */
public interface IGoodArriveService {

	/**
	  * @Description: 运单号查询到货信息
	  * @param ydbhid
	  * @exception
	 */
	public JsonResult searchGoodArriveByYdbhid(String ydbhid,String company,String account);

	/**
	  * @Description: 整车查询到货信息
	  * @param fchrq
	  * @param chxh
	  * @param company
	  * @return
	  * @exception
	 */
	public JsonResult searchGoodArriveByChxh(String fchrq, String chxh, String company,String account);

	/**
	  * 保存到货
	 */
	public JsonResult saveGoodArrive(RequestJsonEntity map) throws ParseException,ParameterException ;
	/**
	 * 
	 * 进行撤销到货
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017-12-15 16:03:18
	 * @param map
	 * @return
	 */
	JsonResult deleteGoodArrive(RequestJsonEntity map) throws ParameterException;
	
	/**
	 * 批量确认到货
	 * @author fusen.feng 
	 */
	HashMap<String, Object> batchGoodArrive(RequestJsonEntity map) throws ParameterException;
	
	/**
	 * 批量撤销到货
	 * @author fusen.feng 
	 */
	JsonResult batchRepealGoodArrive(RequestJsonEntity map);

}
