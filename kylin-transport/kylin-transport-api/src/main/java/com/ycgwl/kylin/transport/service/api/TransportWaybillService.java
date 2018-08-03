package com.ycgwl.kylin.transport.service.api;

import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.entity.*;

import java.util.Collection;

/**
 * 运单和财凭保存和修改逻辑
 *<p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月28日
 */
public interface TransportWaybillService {

	/**
	 * 运单和财凭保存逻辑（修改）
	 */
	String updateOrderAndCertify(TransportOrder transportOrder, Collection<TransportOrderDetail> orderDetails,
			FinanceCertify financeCertify, FinanceStandardPrice financeStandardPrice, Integer modifyOrderIdentification,
			Integer modifyCertifyIdentification, UserModel userModel) throws ParameterException, BusinessException;

	/**
	 * 运单和财凭保存逻辑（新增）
	 */
	String saveOrderAndCertify(TransportOrder transportOrder, Collection<TransportOrderDetail> orderDetails,
			FinanceCertify financeCertify, FinanceStandardPrice price, Boolean releaseWaitingFlag, UserModel userModel)
					throws ParameterException, BusinessException;

}
