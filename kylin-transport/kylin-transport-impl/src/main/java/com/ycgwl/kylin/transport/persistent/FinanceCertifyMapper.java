/**
 * @Title: FinanceCertifyMapper.java
 * @Package com.ycgwl.kylin.transport.persistent
 * @Description: 财凭持久化
 * 
 * @author <a href="mailto:110686@ycgwl.com">ding xuefeng</a>
 * @date 2017年5月11日 下午3:36:17
 */
package com.ycgwl.kylin.transport.persistent;

import com.ycgwl.kylin.transport.entity.FinanceCertify;
import com.ycgwl.kylin.transport.entity.FinanceCertifyResult;

/**
 * <b>Description</b> 财凭持久化
 * <p>
 * <b>Author</b> <a href="mailto:110686@ycgwl.com">dingxf</a><br>
 * <b>Date</b> 2017-05-24 21:55:12<br> 
 * <p>
 */
public interface FinanceCertifyMapper {


	/**
	 * <b>Description</b> 调用存储过程持久化财凭信息
	 * <p>
	 * <b>Author</b> <a href="mailto:110686@ycgwl.com">dingxf</a><br>
	 * <b>Date</b> 2017-05-24 21:55:26
	 * <p>
	 * @param financeCertify 财凭信息
	 * @return 
	 * <p>
	 */
	public String callProcedureInsert(FinanceCertify financeCertify);
	

	/**
	 * <b>Description</b> 根据运单号查询财凭信息
	 * <p>
	 * <b>Author</b> <a href="mailto:110686@ycgwl.com">dingxf</a><br>
	 * <b>Date</b> 2017-05-24 21:55:37
	 * <p>
	 * @param ydbhid 韵达编号
	 * @return 财凭信息
	 * <p>
	 */
	public FinanceCertify getFinanceCertifyByYdbhid(String ydbhid);
	
	/**
	 * 根据运单号查询财凭合计金额和财凭号
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月29日
	 * @param ydbhid
	 * @return
	 */
	public FinanceCertifyResult getCwpzhbhAndHjjeByYdbhid(String ydbhid);
}
