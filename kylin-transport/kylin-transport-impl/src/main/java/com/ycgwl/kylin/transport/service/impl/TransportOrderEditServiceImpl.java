package com.ycgwl.kylin.transport.service.impl;

import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.entity.*;
import com.ycgwl.kylin.transport.persistent.AdjunctSomethingMapper;
import com.ycgwl.kylin.transport.persistent.FinanceCertifyMapper;
import com.ycgwl.kylin.transport.persistent.TransportOrderMapper;
import com.ycgwl.kylin.transport.persistent.TransportRightMapper;
import com.ycgwl.kylin.transport.service.api.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("kylin.transport.dubbo.local.transportOrderEditService")
public class TransportOrderEditServiceImpl implements ITransportOrderEditService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	TransportRightMapper transportRightMapper;

	@Autowired
	BundleReceiptService bundleReceiptService;

	@Autowired
	TransportSignRecordServiceImpl transportSignRecordService;

	@Autowired
	TransportOrderMapper transportOrderMapper;

	@Autowired
	AdjunctSomethingService adjunctSomethingService;

	@Autowired
	AdjunctSomethingMapper adjunctSomethingMapper;

	@Autowired
	TransportOrderDetailService orderDetailService;

	@Autowired
	FinanceCertifyMapper financeCertifyMapper;

	@Autowired
	IExceptionLogService exceptionLogService;

	/**
	 * 判断是否为本人录入的订单
	 * <p>
	 * 
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at
	 *            2017年12月22日
	 * @param transportSignRecordSearch
	 * @return
	 */
	public boolean isOneselfEdit(TransportSignRecordSearch transportSignRecordSearch) {
		boolean flag = false;
		TransportOrder order = transportOrderMapper.getTransportOrderByYdbhid(transportSignRecordSearch.getYdbhid());
		if (order != null) {
			if (transportSignRecordSearch.getAccount().equals(order.getZhipiao())) {// 本人录入，可以编辑
				flag = true;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	@Override
	public TransportEditResult getTransportOrderEditPermissions(TransportSignRecordSearch transportSignRecordSearch)
			throws ParameterException, BusinessException {
		TransportEditResult transportEditResult = new TransportEditResult();

		// 查询用户运单修改权限
//		Integer limit = 0;
//		try {
//			limit = transportRightMapper.getRightNum(AuthorticationConstant.MODIFY_HWYD, transportSignRecordSearch.getAccount());
//		} catch (Exception e) {
//			throw new BusinessException("获取用户运单修改权限失败", e);
//		}

//		if (limit == 0) {
//			transportEditResult.setIsEdit(false);
//			transportEditResult.setMessage("您没有权限使用这个功能，请与系统管理员联系！");
//			return transportEditResult;
//		}

		 //查询运单是否为本人录入的
		if (!isOneselfEdit(transportSignRecordSearch)) {
			transportEditResult.setIsEdit(false);
			transportEditResult.setMessage("该运单不是您录入的，不能做修改操作！");
			return transportEditResult;
		}
		
		// 查询该运单是否做过装载
		List<BundleReceipt> bundleReceiptList = new ArrayList<BundleReceipt>();
		try {
			bundleReceiptList = bundleReceiptService
					.getLastBundleReceiptByYdbhid(transportSignRecordSearch.getYdbhid());
		} catch (Exception e) {
			throw new BusinessException("获取装载信息失败", e);
		}
		if (bundleReceiptList != null && bundleReceiptList.size() > 0) {
			transportEditResult.setIsEdit(false);
			transportEditResult.setMessage("该运单已做装载操作，不能修改！");
			return transportEditResult;
		}

		// 查询运单是否做过财凭
		FiwtResult fiwtResult = new FiwtResult();
		try {
			fiwtResult = transportSignRecordService.getXianluByYdbhid(transportSignRecordSearch.getYdbhid());
		} catch (Exception e) {
			throw new BusinessException("获取财凭信息失败", e);
		}

		if (fiwtResult != null) {// 已做财凭操作
			transportEditResult.setIsEdit(false);
			transportEditResult.setMessage("该运单已做财凭操作，不能修改！");
			return transportEditResult;
		}
		transportEditResult.setIsEdit(true);
		transportEditResult.setMessage("该运单可以修改！");
		return transportEditResult;
	}

	@Override
	public TransportEditResult getInputtingFinanceCertifyPermissions(
			TransportSignRecordSearch transportSignRecordSearch) throws ParameterException, BusinessException {
		TransportEditResult transportEditResult = new TransportEditResult();

		// 查询运单是否为本人录入的
//		if (!isOneselfEdit(transportSignRecordSearch)) {
//			transportEditResult.setIsEdit(false);
//			transportEditResult.setMessage("该运单不是您录入的，不能做补录财凭！");
//			return transportEditResult;
//		}
		
		if(!transportSignRecordSearch.getTransportCompanyName().equals(transportSignRecordSearch.getCompany())) {
			transportEditResult.setIsEdit(false);
			transportEditResult.setMessage("当前公司不是"+transportSignRecordSearch.getTransportCompanyName()+"，不能补录财凭！");
			return transportEditResult;
		}

		// 查询运单是否做过财凭
		FiwtResult fiwtResult = new FiwtResult();
		try {
			fiwtResult = transportSignRecordService.getXianluByYdbhid(transportSignRecordSearch.getYdbhid());
		} catch (Exception e) {
			throw new BusinessException("获取财凭信息失败", e);
		}
		
		if (fiwtResult != null) {// 已做财凭操作
			transportEditResult.setIsEdit(false);
			transportEditResult.setMessage("该运单已做财凭操作！");
			return transportEditResult;
		}
		
		transportEditResult.setIsEdit(true);
		transportEditResult.setMessage("可以补录财凭！");
		return transportEditResult;
	}

	@Override
	public List<FiwtResult> getFinanceReceiveMoney(String ydbhid) throws ParameterException, BusinessException {
		if (StringUtils.isEmpty(ydbhid)) {
			throw new ParameterException("ydbhid", ydbhid, "运单编号不能为空");
		}
		return transportOrderMapper.selectFinanceReceiveMoneyByYdbhid(ydbhid);
	}

	@Override
	public void modifyTransportOrderByYdbhid(TransportOrder transportOrder)
			throws ParameterException, BusinessException {
		if (transportOrder == null) {
			throw new ParameterException("transportOrder", transportOrder, "运单信息不能为空");
		}
		if (StringUtils.isEmpty(transportOrder.getYdbhid())) {
			throw new ParameterException("ydbhid", transportOrder.getYdbhid(), "运单编号不能为空");
		}
		transportOrderMapper.updateTransportOrderByYdbhid(transportOrder);
	}
}