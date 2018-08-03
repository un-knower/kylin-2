package com.ycgwl.kylin.transport.service.api;

import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.entity.FiwtResult;
import com.ycgwl.kylin.transport.entity.TransportEditResult;
import com.ycgwl.kylin.transport.entity.TransportOrder;
import com.ycgwl.kylin.transport.entity.TransportSignRecordSearch;

import java.util.List;

/**
 * 运单修改sevice
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月13日
 */
public interface ITransportOrderEditService {
	
	/**
	 * 查询是否可以做运单修改操作
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月13日
	 * @param transportSignRecordSearch
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	TransportEditResult getTransportOrderEditPermissions(TransportSignRecordSearch transportSignRecordSearch) throws ParameterException, BusinessException;
	
	/**
	 * 查询是否可以做补录财凭操作
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月13日
	 * @param transportSignRecordSearch
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	TransportEditResult getInputtingFinanceCertifyPermissions(TransportSignRecordSearch transportSignRecordSearch) throws ParameterException, BusinessException;
	
	/**
	 * 查询是否做过冲红操作
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月14日
	 * @param transportSignRecordSearch
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	List<FiwtResult> getFinanceReceiveMoney(String ydbhid) throws ParameterException, BusinessException;
	
	/**
	 * 更新运单信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月14日
	 * @param transportOrder
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	void modifyTransportOrderByYdbhid(TransportOrder transportOrder) throws ParameterException, BusinessException;
}
