package com.ycgwl.kylin.transport.service.impl;

import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.entity.*;
import com.ycgwl.kylin.transport.entity.adjunct.ReleaseWaiting;
import com.ycgwl.kylin.transport.persistent.AdjunctSomethingMapper;
import com.ycgwl.kylin.transport.persistent.FinanceCertifyMapper;
import com.ycgwl.kylin.transport.persistent.TransportOrderMapper;
import com.ycgwl.kylin.transport.service.api.IExceptionLogService;
import com.ycgwl.kylin.transport.service.api.ISendMessageLogService;
import com.ycgwl.kylin.transport.service.api.TransportOrderDetailService;
import com.ycgwl.kylin.transport.service.api.TransportWaybillService;
import com.ycgwl.kylin.util.Assert;
import com.ycgwl.kylin.util.IPUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service("kylin.transport.dubbo.local.transportWaybillService")
public class TransportWaybillServiceImpl implements TransportWaybillService {
	private final static String OPERATING_MENU = "运单修改";
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	TransportOrderMapper transportOrderMapper;
	@Autowired
	AdjunctSomethingMapper adjunctSomethingMapper;
	@Autowired
	TransportOrderDetailService orderDetailService;
	@Autowired
	FinanceCertifyMapper financeCertifyMapper;
	@Autowired
	IExceptionLogService exceptionLogService;
	@Autowired
	ISendMessageLogService sendMessageLogService;

	@Override
	@Transactional(rollbackFor=Exception.class)
	public String saveOrderAndCertify(TransportOrder transportOrder, Collection<TransportOrderDetail> orderDetails,
			FinanceCertify financeCertify, FinanceStandardPrice price, Boolean releaseWaitingFlag, UserModel userModel)
					throws ParameterException, BusinessException {
		boolean saveFinance = financeCertify.getHasFinance() == null || financeCertify.getHasFinance() == 0 ? false: true;
		List<TransportOrder> list = transportOrderMapper.get(transportOrder.getYdbhid());
		Assert.trueIsWrong("transportOrder not exist", list != null && !list.isEmpty(),"运单号[" + transportOrder.getYdbhid() + "]已经存在");
		//1）运单信息准备和校验
		try {
			if(!saveFinance){
				transportOrder.setCwpzhy(0);
			}
			transportOrder = prepareForTransportOrder(transportOrder, orderDetails);
		} catch (ParameterException e) {
			throw new ParameterException(e.getTipMessage());
		} catch (BusinessException e) {
			throw new BusinessException(e.getTipMessage());
		}
		//2）运单明细准备
		orderDetails = prepareForOrderDetails(transportOrder, orderDetails);// 运单明细
		if (saveFinance) {
			transportOrder.setDashoukuanYuan(financeCertify.getReceipt());
			transportOrder.setZhuangxiefei(financeCertify.getZhuangxiefei());
		}
		if(saveFinance){
			try {
				transportOrder.setCwpzhy(1);
				financeCertify = prepareForFinance(financeCertify, price, transportOrder,userModel,orderDetails);
			} catch (ParameterException e) {
				throw new ParameterException(e.getTipMessage());
			} catch (BusinessException e) {
				throw new BusinessException(e.getTipMessage());
			}
		}
		try {
			// 持久化运单信息
			transportOrder.setYxzt("货物入库");
			// 查询发站到站的简称
			String xianluOfFazhan = transportOrderMapper.selectXianLuByStation(transportOrder.getFazhan());
			String xianluOfDaozhan = transportOrderMapper.selectXianLuByStation(transportOrder.getDaozhan());
			xianluOfFazhan = xianluOfFazhan == null ? "" : xianluOfFazhan;
			xianluOfDaozhan = xianluOfDaozhan == null ? "" : xianluOfDaozhan;
			transportOrder.setXianlu(xianluOfFazhan + xianluOfDaozhan);
			transportOrderMapper.insert(transportOrder);
			saveReleaseWaiting(transportOrder, releaseWaitingFlag);//保存等待发货通知
			
			// 持久化运单明细信息
			orderDetailService.batchAddTransportOrderDetail(orderDetails);
			// 持久化财凭信息
			if (saveFinance) {
				financeCertifyMapper.callProcedureInsert(financeCertify);
				if (StringUtils.isNotBlank(financeCertify.getMessage())) {
					throw new BusinessException(financeCertify.getMessage());
				}
			}
		} catch (Exception e) {
			logger.error("录单业务处理异常  运单:{}, 运单明细:{}, 财凭:{} , errorMsg:{}", transportOrder, orderDetails, financeCertify,e.getMessage(), e);
			throw new BusinessException("业务处理异常", e);
		}
		transportOrder.setDetailList(orderDetails);
		sendMessageLogService.savePhoneSms(transportOrder, userModel.getCompany());
		return transportOrder.getYdbhid();
	}
	
	
	/**
	 * 保存等待发货通知
	 * 
	 * @param transportOrder
	 */
	public void saveReleaseWaiting(TransportOrder transportOrder, Boolean releaseWaitingFlag) {
		if (releaseWaitingFlag) {
			ReleaseWaiting releaseWaiting = new ReleaseWaiting();
			releaseWaiting.setYdbhid(transportOrder.getYdbhid());
			releaseWaiting.setFazhan(transportOrder.getFazhan());
			releaseWaiting.setDdfhczshijian(new Date());
			releaseWaiting.setDdfhczygh(transportOrder.getZhipiao());
			if (releaseWaitingFlag) {
				releaseWaiting.setDdfhzt(1);// 等待托运人放货通知
				releaseWaiting.setTzfhzt(1);// 通知状态0表示不通知
			} else {
				releaseWaiting.setDdfhzt(0);// 不等待托运人放货通知
				releaseWaiting.setTzfhzt(0);// 通知状态0表示不通知
			}
			// 持久化等待发货通知
			adjunctSomethingMapper.insertReleaseWaiting(releaseWaiting);
		}
	}

	@Override
	@Transactional(rollbackFor = { ParameterException.class, BusinessException.class })
	public String updateOrderAndCertify(TransportOrder transportOrder, Collection<TransportOrderDetail> orderDetails,
			FinanceCertify financeCertify, FinanceStandardPrice financeStandardPrice, Integer modifyOrderIdentification,
			Integer modifyCertifyIdentification, UserModel userModel) throws ParameterException, BusinessException {
		String ydbhid = transportOrder.getYdbhid();
		boolean hasSaveOrder = false;
		boolean hasSaveFinance = false;
		if (modifyOrderIdentification == 1) {// 需要更改订单
			List<TransportOrder> list = transportOrderMapper.get(transportOrder.getYdbhid());// 修改运单判断运单是否不存在
			Assert.trueIsWrong("transportOrder not exist", list == null || list.isEmpty(),
					"运单号[" + transportOrder.getYdbhid() + "]不存在");

			// 1）运单信息准备和校验
			try {
				transportOrder = prepareForTransportOrder(transportOrder, orderDetails);
			} catch (ParameterException e) {
				throw new ParameterException(e.getTipMessage());
			} catch (BusinessException e) {
				throw new BusinessException(e.getTipMessage());
			}
			// 2）运单明细准备
			orderDetails = prepareForOrderDetails(transportOrder, orderDetails);// 运单明细
		}

		// 3）若运单和财凭一起修改，那么财凭的校验要在<运单信息持久化>之前
		if (modifyCertifyIdentification == 1) {
			try {
				financeCertify = prepareForFinance(financeCertify, financeStandardPrice, transportOrder, userModel,orderDetails);
			} catch (ParameterException e) {
				throw new ParameterException(e.getTipMessage());
			} catch (BusinessException e) {
				throw new BusinessException(e.getTipMessage());
			}
		}

		if (modifyOrderIdentification == 1) {
			// 4）运单信息持久化
			try {
				// 2018-04-13 线上修改运单发付/到付的时候出现两个都勾选的问题
				if (transportOrder.getFzfk() == null) {
					transportOrder.setFzfk(0);
				}
				;
				if (transportOrder.getDzfk() == null) {
					transportOrder.setDzfk(0);
				}

				transportOrderMapper.updateTransportOrderByYdbhid(transportOrder);
				// 持久化运单明细信息
				orderDetailService.removeTransportOrderDetailByYdbhid(ydbhid);// 先删除数据库中原有明细信息
				orderDetailService.batchAddTransportOrderDetail(orderDetails);// 再新增明细
			} catch (Exception e) {
				logger.error("录单业务处理异常  运单:{}, 运单明细:{}, 财凭:{} , errorMsg:{}", transportOrder, orderDetails,
						financeCertify, e.getMessage(), e);
				throw new BusinessException(e.getMessage(), e);
			}
			hasSaveOrder = true;
		}

		// 5）补录财凭
		if (modifyCertifyIdentification == 1) {
			try {
				// transportOrder = transportOrderMapper.getTransportOrderByYdbhid(ydbhid);
				transportOrder.setCwpzhy(1);
				transportOrderMapper.updateTransportOrderByYdbhid(transportOrder);
				financeCertifyMapper.callProcedureInsert(financeCertify);
				if (StringUtils.isNotBlank(financeCertify.getMessage())) {
					throw new BusinessException(financeCertify.getMessage());
				}
			} catch (Exception e) {
				logger.error("录单业务处理异常  运单:{}, 运单明细:{}, 财凭:{} , errorMsg:{}", transportOrder, orderDetails,
						financeCertify, e.getMessage(), e);
				throw new BusinessException("新增财凭失败", e);
			}
			hasSaveFinance = true;
		}
		if (hasSaveFinance == false && hasSaveOrder == false) {
			throw new ParameterException("由于未标记保存，运单和财凭都未保存");
		}
		// 6）记录异常操作日志
		saveExceptionLog(userModel, transportOrder);
		return transportOrder.getYdbhid();
	}

	/**
	 * 新增异常日志信息
	 */
	public void saveExceptionLog(UserModel userModel, TransportOrder transportOrder) throws BusinessException {
		ExceptionLog exceptionLog = new ExceptionLog();
		exceptionLog.setOperatorName(userModel.getUserName());
		exceptionLog.setOperatorAccount(userModel.getAccount());
		exceptionLog.setOperatorCompany(userModel.getCompany());
		exceptionLog.setIpAddress(IPUtil.getLocalIP());
		exceptionLog.setYdbhid(transportOrder.getYdbhid());
		exceptionLog.setOperatingMenu(OPERATING_MENU);
		String operatingContent = "运单号为：" + transportOrder.getYdbhid() + "的运单记录修改成功！";
		exceptionLog.setOperatingContent(operatingContent);
		exceptionLog.setOperatingTime(new Date());
		exceptionLog.setCreateName(userModel.getUserName());
		exceptionLog.setCreateTime(new Date());
		exceptionLog.setUpdateName(userModel.getUserName());
		exceptionLog.setUpdateTime(new Date());
		try {
			exceptionLogService.addExceptionLog(exceptionLog);
		} catch (Exception e) {
			throw new BusinessException("新增异常日志信息失败", e);
		}
	}

	/**
	 * 运单明细校验和设置
	 */
	public Collection<TransportOrderDetail> prepareForOrderDetails(TransportOrder transportOrder,
			Collection<TransportOrderDetail> orderDetails) {
		// 运单明细信息
		for (Iterator<TransportOrderDetail> iterator = orderDetails.iterator(); iterator.hasNext();) {// 清除一些无效的数据
			TransportOrderDetail transportOrderDetail = iterator.next();
			if (transportOrderDetail == null || StringUtils.isBlank(transportOrderDetail.getPinming())) {
				iterator.remove();
			}
		}
		// 统计件数、体积、重量
		Double tijiHj = new Double(0);
		Double zhlHj = 0.0;
		for (TransportOrderDetail orderDetail : orderDetails) {
			Assert.notNull("orderDetail.pinming", orderDetail.getPinming(), "运单明细品名不能为空");
			// 要求  重量 体积 件数全部必填
			boolean flag = null == orderDetail.getZhl() || null == orderDetail.getTiji() || null == orderDetail.getJianshu();
			Assert.trueIsWrong("orderDetail.info", flag, "每个货物信息的重量体积件数不能为空");
//			if (orderDetail.getJffs() == 0) {
//				Assert.notNull("orderDetail.zhl", orderDetail.getZhl(), "重货计费时重量不能为空");
//				zhlHj = zhlHj + orderDetail.getZhl();
//			}
//			if (orderDetail.getJffs() == 1) {
//				Assert.notNull("orderDetail.tiji", orderDetail.getTiji(), "轻货计费时体积不能为空");
//				tijiHj = (orderDetail.getTiji().doubleValue() * 10000 + tijiHj * 10000) / 10000;
//			}
//			if (orderDetail.getJffs() == 2) {
//				Assert.notNull("orderDetail.jianshu", orderDetail.getJianshu(), "按件计费时件数不能为空");
//			}
			orderDetail.setYdbhid(transportOrder.getYdbhid());
			orderDetail.setYfh(false);
			orderDetail.setIsKaiyun(transportOrder.getIsKaiyun());
		}
		transportOrder.setTijihj(tijiHj);
		transportOrder.setZhlhj(zhlHj.intValue());
		return orderDetails;
	}

	/**
	 * 运单信息校验和设置
	 */
	private TransportOrder prepareForTransportOrder(TransportOrder transportOrder,
			Collection<TransportOrderDetail> orderDetails) {
		// 1）运单校验
		Assert.notNull("transportOrder", transportOrder, "运单信息不能为空");
		Assert.notNull("transportOrder.ydbhid", transportOrder.getYdbhid(), "运单编号不能为空");
		Assert.trueIsWrong("ydbhid length != 12 or 10",
				transportOrder.getYdbhid().length() != 10 && transportOrder.getYdbhid().length() != 12,
				"运单号长度不符合标准");
		// 立白客户到站网点必填
		// if("AHHFR1000".equals(transportOrder.getKhbm())&&StringUtils.isEmpty(transportOrder.getDzshhd())){
		// Assert.trueIsWrong("khbm is liby,so dzshhd canot empty",true,
		// "客户编码匹配为立白客户，到站网点必填");
		// }
		Assert.notNull("transportOrder.isfd", transportOrder.getIsfd(), "是否返单不能为空");
		Assert.notNull("transportOrder.fwfs", transportOrder.getFwfs(), "服务方式不能为空");
		Assert.notNull("transportOrder.ysfs", transportOrder.getYsfs(), "运输方式不能为空");
		Assert.notNull("transportOrder.shhrdzh", transportOrder.getShhrdzh(), "收货人地址不能为空");
		// Assert.trueIsWrong("transportOrder.shhryb focus rule",
		// !MobileRegex.regexMobile(transportOrder.getShhryb()),
		// "请输入正确的收货人手机号码");
		Assert.notNull("transportOrder.shhrmch", transportOrder.getShhrmch(), "收货人名称不能为空");
		Assert.notNull("transportOrder.fhdwdzh", transportOrder.getFhdwdzh(), "客户地址不能为空");
		Assert.notNull("transportOrder.fhdwmch", transportOrder.getFhdwmch(), "客户名称不能为空");
		Assert.notNull("transportOrder.daozhan", transportOrder.getDaozhan(), "到站不能为空");
		Assert.notNull("transportOrder.fazhan", transportOrder.getFazhan(), "始发站不能为空");
		Assert.notNull("transportOrder.fzfk and dzfk is null",
				(transportOrder.getFzfk() == null && transportOrder.getDzfk() == null), "付费方式必须选择一项");
		Assert.notEmpty("orderDetails", orderDetails, "运单明细不能为空");
		if (transportOrder.getFwfs() == 1 || transportOrder.getFwfs() == 2) {
			Assert.notNull("transportOrder.daozhan", transportOrder.getShhrlxdh(), "此票货物是送货上门，座机不能为空");
			Assert.notNull("transportOrder.daozhan", transportOrder.getShhrdzh(), "此票货物是送货上门，地址不能为空");
			Assert.notNull("transportOrder.daozhan",
					transportOrder.getShhrdzh() == null || transportOrder.getShhrdzh().length() < 12,
					"您输入收货人信息可能不完整，请录入详细");
		}
		Assert.trueIsWrong("transportOrder.daozhan",
				transportOrder.getFzfk() == null && transportOrder.getDzfk() == null, "付费方式必须选择一项");

		// 2）运单信息设置
		// 设置起始地和目的地
		/**
		 * 新需求，需要保持页面填写的数据值 2018-02-06 15:40 yanxf
		 */
		/**
		 * transportOrder.setBeginPlacename(transportOrder.getFhdwdzh()); if
		 * (transportOrder.getDhShengfen() != null && transportOrder.getDhChengsi() !=
		 * null) { transportOrder.setEndPlacename( transportOrder.getDhShengfen() +
		 * transportOrder.getDhChengsi() + transportOrder.getDhAddr()); } else if
		 * (transportOrder.getDhShengfen() == null && transportOrder.getDhChengsi() !=
		 * null) { transportOrder.setEndPlacename(transportOrder.getDhChengsi() +
		 * transportOrder.getDhAddr()); } else if (transportOrder.getDhShengfen() !=
		 * null && transportOrder.getDhChengsi() == null) {
		 * transportOrder.setEndPlacename(transportOrder.getDhShengfen() +
		 * transportOrder.getDhAddr()); } else {
		 * transportOrder.setEndPlacename(transportOrder.getDhAddr()); }
		 */
		if (transportOrder.getFhshj() == null) {
      transportOrder.setFhshj(new Date());
    }
		String date = DateFormatUtils.format(transportOrder.getFhshj(), "MMdd");// 取发货时间的月和日
		String fazhanShort = adjunctSomethingMapper.getCompanyByName(transportOrder.getFazhan()).getCompanyShort();// 获取发站简称
		String daozhanShort = adjunctSomethingMapper.getCompanyByName(transportOrder.getDaozhan()).getCompanyShort();// 获取到站简称
		transportOrder.setYshhm(fazhanShort + daozhanShort + date + "-" + transportOrder.getYdbhid());// 设置运输号码

		return transportOrder;
	}

	/**
	 * 财凭信息校验和设置
	 * 
	 * @param userModel
	 */
	public FinanceCertify prepareForFinance(FinanceCertify financeCertify, FinanceStandardPrice price,
											TransportOrder transportOrder, UserModel userModel, Collection<TransportOrderDetail> orderDetails) throws ParameterException, BusinessException {
		// 1）财凭校验
		Assert.notNull("financeCertify", financeCertify, "财凭不能为空");
		//Assert.notNull("financeCertify.cost", financeCertify.getCost(), "合计费用不能为空");
		// Assert.trueIsWrong("financeCertify.getOther() is null",
		// financeCertify.getOther() == null || financeCertify.getOther() == 0,
		// "其他费用不能为空");
		// 仓到站=0，仓到仓=1，上门取货费必填
		Assert.trueIsWrong("fwfs=0 or 1,tohome is null or zero",
				((transportOrder.getFwfs() == 0 || transportOrder.getFwfs() == 1)
						&& (financeCertify.getTohome() == null)),
				"请输入上门取货费！");
		// 仓到仓=1，站到仓=2，送货上门费必填
		Assert.trueIsWrong("fwfs=1 or 2,delivery is null or zero",
				((transportOrder.getFwfs() == 1 || transportOrder.getFwfs() == 2)
						&& (financeCertify.getDelivery() == null)),
				"请输入送货上门费！");
		FinanceCertify.Builder builder = FinanceCertify.newBuilder();
		builder.setFinanceCertify(financeCertify)
				.setTransportOrder(transportOrder)
				.setFinanceStandardPrice(price)
				.setOrderDetails(orderDetails)
				.setChuNa(userModel.getUserName());
		return builder.build();
	}


}
