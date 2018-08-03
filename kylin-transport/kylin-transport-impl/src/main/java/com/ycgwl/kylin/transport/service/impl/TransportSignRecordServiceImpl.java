package com.ycgwl.kylin.transport.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.ycgwl.kylin.AuthorticationConstant;
import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.entity.RequestJsonEntity;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.dto.ReorderDto;
import com.ycgwl.kylin.transport.entity.*;
import com.ycgwl.kylin.transport.persistent.*;
import com.ycgwl.kylin.transport.service.api.*;
import com.ycgwl.kylin.transport.vo.ReorderVo;
import com.ycgwl.kylin.util.Assert;
import com.ycgwl.kylin.util.DateUtils;
import com.ycgwl.kylin.util.IPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("kylin.transport.dubbo.local.transportSignRecordService")
public class TransportSignRecordServiceImpl implements ITransportSignRecordService,AuthorticationConstant {

	private final static String REVOCATION_SIGN_IN = "已撤销签收";

	private final static String TRANSFER_YES = "中转";

	private final static String TRANSFER_NO = "不中转";

	private final static String COMPANY_HEADQUARTER = "总公司";

	private final static String OPERATING_MENU = "撤销签收";

	@Resource
	private DeliveryOperateServiceImpl deliveryOperateService;

	@Autowired
	private TransportSignRecordMapper transportSignRecordMapper;

	@Autowired
	private BundleReceiptService bundleReceiptService;

	@Autowired
	private ITransportOrderService transportOrderService;

	@Autowired
	private AdjunctSomethingMapper adjunctSomethingMapper;

	@Autowired
	private TransportOrderMapper transportOrderMapper;

	@Autowired
	private IExceptionLogService exceptionLogService; 

	@Autowired
	private TransportRightMapper transportRightMapper;

	@Autowired
	private BundleReceiptMapper bundleReceiptMapper;

	@Resource
	private ITransportSignRecordService signRecordService;
	@Resource
	private DeliveryOperateMapper deliveryOperateMapper;

	@Resource
	private AdjunctSomethingService adjunctSomethingService;

	@Resource
	private BundleReceiptService receiptService;

	@Resource
	private ArriveStationService arriveStationService;

	@Override
	@Transactional(rollbackFor = {ParameterException.class, BusinessException.class} )
	public String fullTicketReceipt(TransportSignRecordEntity transportSignRecordEntity) throws ParameterException, BusinessException {
		TransportSignRecord signRecord = transportSignRecordEntity.getTransportSignRecord();
		// 解决 没有成本的运单也可以签收 的问题
		String ydbhid = transportSignRecordEntity.getTransportSignRecord().getYdbhid();
		FinanceCertify certify = transportOrderService.getFinanceCertifyByYdbhid(ydbhid);
		if(certify == null)
			throw new BusinessException("该运单未生成财凭信息,请录完财凭做完收钱再进行签收!");
		//解决到付款的运单没有生成收据也可以签收 的问题
//		ShqsdResult payInfo = signRecordService.getReachAfterPayInfo(ydbhid);
//		if(payInfo.getDsk() > 0 || payInfo.getHdfk() > 0 ) {
//			Integer id = deliveryOperateMapper.getT_CWSJ_MASTERId(ydbhid);
//			if(id == null || id <= 0)
//				throw new ParameterException("此运单号为"+ydbhid+",要求到站付款或代收款,签收前请生成分理收据");
//		}

		//等托指令放货勾选不能签收
		if(adjunctSomethingService.isReleaseWaiting(ydbhid)) 
			throw new ParameterException("该运单是等托指令放货，没有放货通知，不能签收");

		Integer qszt = signRecord.getQszt();//获取签收状态
		if(qszt==null||qszt == 0){
			throw new ParameterException("请选择签收状态，不能没有签收状态");
		}else if(qszt == 21){
			throw new ParameterException("系统禁止使用【客户要求延时(21)】签收类型");
		}else if(qszt > 0 && qszt <= 10){
			Double sum = signRecord.getPsjianshu() + signRecord.getDsjianshu();
			if(sum <= 0){
				throw new ParameterException("当前签收状态，请录入破损件数或者短少件数！");
			}
			String common = signRecord.getComm();
			if(StringUtils.isEmpty(common)){
				throw new ParameterException("请录入备注信息!");
			}
		}
		if(null == signRecord.getQsTime()){
			throw new ParameterException("请录入签收时间!");
		}
		if(StringUtils.isEmpty(signRecord.getQsr())){
			throw new ParameterException("请录入签收人!");
		}
		
		/**
		 * 新增需求：第一次签收默认系统时间，当月内撤销后可以更改签收时间和签收状态，跨月后显示当月内最后一次签收的时间和状态
		 * 2018-02-07 13:40
		 * yanxif
		 */
		/**Date firstDayOfMonth = DateUtils.getFirstDayOfMonth();//当月1号
		if(signRecord.getQsTime().getTime() <= fchrqTime.getTime() || signRecord.getQsTime().getTime() <= firstDayOfMonth.getTime()){
			throw new ParameterException("签收时间不能小于等于发车时间,也不能小于当月1号!");
		}*/

		TransportOrder order = transportOrderService.getTransportOrderByYdbhid(ydbhid);

		//		if(qszt > 0 && (null == signRecord.getShlc() || signRecord.getShlc() <= 0)){
		//			throw new ParameterException("请选择送货里程！");
		//		}
		//客户要求延时签收判断（需求确定下是否要加）
		if(signRecord.getQszt() > 0){
			signRecord.setLrr(transportSignRecordEntity.getUserName());
			signRecord.setLrTime(signRecord.getLrTime());
			signRecord.setLrrGrid(transportSignRecordEntity.getAccount());
			signRecord.setGs(transportSignRecordEntity.getCompany());
			signRecord.setSfxgbs(0);
			signRecord.setCxFlag("0");
			signRecord.setRecordQsTime(signRecord.getQsTime());
			signRecord.setSignType(1); // 手动签收标识
			TransportSignRecord transportSignRecordOld = null;
			/**
			 * 保存为已签收，先判断是否撤销过签收
			 */
			try {
				transportSignRecordOld = this.getTransportSignRecordByYdbhid(signRecord.getYdbhid());
				if(transportSignRecordOld != null && "1".equals(transportSignRecordOld.getCxFlag())){//撤销过签收，先删除撤销信息，再修改签收时间
//					TransportSignRecord transportSignRecord = new TransportSignRecord();
//					transportSignRecord.setCxFlag("0");
//					transportSignRecord.setYdbhid(signRecord.getYdbhid());
//
//					//更新签收信息的撤销状态
//					this.modifyTransportSignRecordByYdbhid(transportSignRecord);
					//删除已撤销签收信息
					this.removeHwydRouteByYdbhid(signRecord.getYdbhid());
					//更新签收时间为原来的签收时间
					//signRecord.setQsTime(transportSignRecordOld.getOldQsTime());

					//jira:944 	签收时么有old_time的需要增加old_time
					if(transportSignRecordOld.getOldQsTime() ==null) 
						signRecord.setOldQsTime(signRecord.getQsTime());
				}else{
					signRecord.setOldQsTime(signRecord.getQsTime());
				}
			} catch (Exception e) {
				throw new BusinessException("当前是已撤销过签收的运单，删除撤销签收在途跟踪记录失败", e);
			}

			//签收日志信息
			TransportSignRecordLog log = new TransportSignRecordLog();
			transportSignRecordLogConversions(signRecord, log);

			//分理库存信息
			TransportProcessStock transportProcessStock = new TransportProcessStock();
			transportProcessStock.setGs(signRecord.getGs());
			transportProcessStock.setYdbhid(signRecord.getYdbhid());
			transportProcessStock.setYiti(5);

			//订单轨迹信息
			TransportOrderTrace transportOrderTrace = new TransportOrderTrace();
			transportOrderTrace.setUpdateDate(new Date());
			transportOrderTrace.setUpdateUser(transportSignRecordEntity.getUserName());
			transportOrderTrace.setYdbhid(signRecord.getYdbhid());
			transportOrderTrace.setOtDtime(signRecord.getOldQsTime());
			transportOrderTrace.setStatus(3);
			transportOrderTrace.setRemark(signRecord.getComm());
			if(signRecord.getQszt() == 11){
				transportOrderTrace.setRecstatus(0);
			}else{
				transportOrderTrace.setRecstatus(1);
			}

			List<BundleReceipt> bundleReceiptList = bundleReceiptMapper.queryBundleReceiptByYdbhidDesc(ydbhid);
			if(bundleReceiptList == null || bundleReceiptList.size() == 0){
				throw new ParameterException("该运单没有做装载操作！");
			}
			for (BundleReceipt bundleReceipt : bundleReceiptList) {
				if (bundleReceipt.getiType() ==  0 && bundleReceipt.getYdzh() == 0 && "中转".equals(bundleReceipt.getTransferFlag())){
					throw new ParameterException("未到货的装载清单中有干线中转必须先到货");
				}
			}

			//检查该运单如果没有装载完，不允许签收，因为没有装载完不准到货
			boolean isBundleFinish = arriveStationService.hasBundleFinish(ydbhid);
			if(!isBundleFinish) {
				throw new ParameterException("该运单进行了分装，请装载完毕后再签收");
			}
			BundleReceipt bundle = bundleReceiptService.getLastBundleReceiptByYdbhid(signRecord.getYdbhid()).get(0);
			Boolean isArrive = bundle.getYdzh()==1?true:false;
			Date fchrqTime = bundle.getFchrq();//发车时间
			Date dhsj = bundle.getDhsj();
//			Date fanghuoshj = transportSignRecordOld.getYxfhsj();
//			if(fanghuoshj!=null && signRecord.getQsTime().getTime() - fanghuoshj.getTime() < 1000*3600*2) {
//				throw new ParameterException("签收时间要在放货时间2个小时之后");
//			}
			if(signRecord.getQsTime().getTime() > new Date().getTime()) {
				throw new ParameterException("签收时间不能大于当前系统时间");
			}
//			if(signRecord.getQsTime().getTime() >= signRecord.getLrTime().getTime()) {
//				throw new ParameterException("签收时间要小于录入时间");
//			}
			if(signRecord.getQsTime().getTime() <= fchrqTime.getTime()) {
				throw new ParameterException("签收时间不能小于等于装载发车时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fchrqTime));
			}
			if(order.getFhshj().getTime() >= signRecord.getQsTime().getTime()) {
				throw new ParameterException("签收时间不能小于等于运单托运时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getFhshj()));
			}
			if(isArrive && dhsj!=null && signRecord.getQsTime().getTime()<=dhsj.getTime()) {
				throw new ParameterException("签收时间要大于到站时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dhsj));
			}
			
			//到货时间要比签收时间少1秒
			long dhtimes = signRecord.getQsTime().getTime()-1000;
			Date newDhsjDate = new Date(dhtimes);
			String newDhsj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(newDhsjDate);
			
			try {
				//自动到货
				HashMap<String,Object> autoArriveParam = new HashMap<String,Object>();
				autoArriveParam.put("grid", transportSignRecordEntity.getAccount());
				autoArriveParam.put("gs", transportSignRecordEntity.getCompany());
				autoArriveParam.put("ydbhid", ydbhid);
				autoArriveParam.put("dhsj",newDhsj);
				arriveStationService.autoArriveStation(autoArriveParam);
				
				/**
				 * 新增需求：第一次签收默认系统时间，当月内撤销后可以更改签收时间和签收状态，跨月后显示当月内最后一次签收的时间和状态
				 * 2018-02-07 13:40
				 * yanxif
				 */
				/**Date firstDayOfMonth = DateUtils.getFirstDayOfMonth();//当月1号
				if(signRecord.getQsTime().getTime() <= fchrqTime.getTime() || signRecord.getQsTime().getTime() <= firstDayOfMonth.getTime()){
					throw new ParameterException("签收时间不能小于等于发车时间,也不能小于当月1号!");
				}*/
				
				//更新签收信息
				this.modifyTransportSignRecordByYdbhid(signRecord);

				// 新增签收日志信息
				this.addTransportSignRecordLog(log);

				//更新分理库存表
				//this.modifyTransportProcessStockByGsAndYdbhid(transportProcessStock);

				//新增运单轨迹信息
				this.addTransportOrderTrace(transportOrderTrace);
			} catch (BusinessException e) {	
				throw new BusinessException(e.getTipMessage(), e);
			}

		}
		return signRecord.getYdbhid();
	}

	/**
	 * 查询签收信息，签收校验逻辑 2018.4.17重新修改
	 */
	@Override
	@Transactional
	public TransportSignRecordResult canTransportOrderSign(TransportSignRecordSearch transportSignRecordSearch) throws ParameterException, BusinessException {
		String ydbhid = transportSignRecordSearch.getYdbhid();
		TransportSignRecordResult transportSignRecordResult = new TransportSignRecordResult();
		Assert.trueIsWrong("运单号不能为空", StringUtils.isEmpty(ydbhid), "请输入正确的运单编号！");
		Assert.trueIsWrong("运单号不是10位或12位是不正确的", !(ydbhid.length() == 10 ||  ydbhid.length()==12), "请输入正确的运单编号！");

		TransportOrder transportOrder = transportOrderService.getTransportOrderByYdbhid(ydbhid);//查询运单
		Assert.trueIsWrong("运单号不存在", transportOrder == null, "该运单号不存在！");
//		Integer isfd = transportOrder.getIsfd();
//		// 判断是否要求返单
//		if (isfd == 1 || isfd == 2) {
//			// 是   检查是否返单
//			Integer hasReceipt = transportOrder.getHasReceipt();
//			if (hasReceipt != 1 ) {
//				throw new BusinessException("此运单需要返单，请上传返单");
//			}
//		}
		
		List<BundleReceipt> bundleReceiptList = bundleReceiptService.getLastBundleReceiptByYdbhid(ydbhid);//根据运单号查询装载清单（装车时间倒序排）
		Assert.trueIsWrong("没有做过装载清单", bundleReceiptList == null || bundleReceiptList.size() == 0, "该运单没有做装载操作！");

		BundleReceipt bundleReceipt = bundleReceiptList.get(0);
		
		ShqsdResult payInfo = signRecordService.getReachAfterPayInfo(ydbhid);

		//检查运单号是否已经作废
		Long isOrderCanceled = transportSignRecordMapper.isOrderCanceled(ydbhid);
		Assert.trueIsWrong("isOrderCanceled > 0", isOrderCanceled > 0, "该票货物运单已作废！请确认！");
			
		//检查登录公司签收权限
		//判断1：当前登录公司是运单的发站，那就判断装载清单的到站和运单的到站一样，一样就允许签收
		//判断2：当前登录公司是运单的到站，那就判断装载清单的到站和运单的到站一样，一样就允许签收
		if(transportOrder.getFazhan().equals(transportSignRecordSearch.getCompany()) 
				|| transportOrder.getDaozhan().equals(transportSignRecordSearch.getCompany())) {
			//if(!transportOrder.getDaozhan().equals(bundleReceipt.getDaozhan())) {
			//	throw new ParameterException("装载清单到站和运单到站一致才能签收！");
			//}
			//	for(BundleReceipt br:bundleReceiptList) {
			//	if(br.getYdzh()==0) {
			//		if(!transportOrder.getDaozhan().equals(br.getDaozhan())) {
			//			throw new ParameterException("装载清单到站和运单到站一致才能签收，存在不一致的到站：“"+br.getDaozhan()+"”！");
			//		}
			//	}
			//}
		}else {
			throw new ParameterException("当前公司不是运单的发站或到站公司，不能签收！");
		}

		//检查是否做过成本
		Integer countIncomeCost = this.countIncomeCost(ydbhid);
		Assert.trueIsWrong("countIncomeCost <= 0", countIncomeCost <= 0, "当前运单没有做成本，不能签收！");

		//检查是否有财凭
		FiwtResult fiwtResult = this.getXianluByYdbhid(ydbhid);
		Assert.trueIsWrong("fiwtResult == null ", fiwtResult == null || StringUtils.isEmpty(fiwtResult.getXianlu()), "当前运单没有做财凭，不能签收！");

		//检查是否款清且发放货通知
		Boolean yshzhk = transportSignRecordMapper.selectFkfsh(fiwtResult);
		if(yshzhk != null && yshzhk == true){
			Integer fhtzd = transportSignRecordMapper.selectFhtzd(ydbhid);
			Assert.trueIsWrong("fhtzd == 0", fhtzd == 0, "当前运单状态是款未付且还未发放货通知，不能签收！");
		}

		//检查是否已签收
		TransportSignRecord transportSignRecord = this.getTransportSignRecordByYdbhid(ydbhid);
		transportSignRecordResult.setSignType(transportSignRecord.getSignType());

		//检查干线中转不能做自动到货，要求手动到货
		boolean isTransfer = bundleReceipt.getiType() == 0 && 
				TransportSignRecordServiceImpl.TRANSFER_YES.equals(bundleReceipt.getTransferFlag());//干线且中转
		boolean arriveStatus =  bundleReceipt.getYdzh() == 0;//未到货
		Assert.trueIsWrong("isTransferAndNotArrive is true", isTransfer && arriveStatus, "干线中转的装载请先做到货，才能签收！");

		//干线中转清单在做签收时有代收款或者到付款的，必须生成提货单或者送货派车才能签收
		CarOutResult carOutResult = transportSignRecordMapper.selectCarOutByYdbhid(ydbhid);//获取派车信息
		String thqshdid = this.getThqsdByYdbhid(ydbhid);
		
		if(!transportSignRecordSearch.getCompany().equals(transportOrder.getFazhan()) && 
				transportSignRecordSearch.getCompany().equals(transportOrder.getDaozhan())){	//到站签收
			if(payInfo.getDsk() + payInfo.getHdfk() > 0) {	//有代收款或到付款
				if(carOutResult == null && thqshdid == null) {
					throw new ParameterException("到站签收有代收款或者到付款的，必须生成提货单或送货派车单才能签收");
				}
			}
//			Integer id = deliveryOperateMapper.getT_CWSJ_MASTERId(ydbhid);
//			if(id == null || id <= 0)
//				throw new ParameterException("此运单号要求到站付款或代收款,签收前请生成分理收据");
			
		}

		//到货装载相关：检查货到付款检测
		if(!transportSignRecordSearch.getCompany().equals(transportOrder.getFazhan()) &&
				transportSignRecordSearch.getCompany().equals(transportOrder.getDaozhan())){	//到站签收
			if(transportOrder.getDzfk() != null && transportOrder.getDzfk() == 1 ){	//判断是否是到站付款
				//查询是否生成提货单和派车单
				if(carOutResult != null || thqshdid != null || "1".equals(transportSignRecord.getDaoZhanOk())){ //已生成派车单或者提货单，装载清单是否到站
					if(payInfo.getDsk() + payInfo.getHdfk() > 0){//到付，判断是否打印收据
						List<String> sjidList = this.getCwsjDetailByYshm(transportOrder.getYshhm());
						if(sjidList.isEmpty() || sjidList.size() == 0){//未打印收据，判断是否生成收据
							if(StringUtils.isEmpty(this.getCarOutShjhmByYdbhid(ydbhid)) && StringUtils.isEmpty(this.getThqsdShjhmByYdbhid(ydbhid))){
								throw new ParameterException("该票货需要生成收据，请先生成收据！");
							}
						}
					}
				}else{
					throw new ParameterException("请先打印生成派车单或者提货单！");
				}
			}else{//发付	
				//判断是否送货上门
//				if(bundleReceipt.getZiti()){//1.送货上门 0.自提
//					//查询是否生成提货单和派车单
//					String thqshdid = this.getThqsdByYdbhid(ydbhid);
//					if(carOutResult == null && thqshdid == null ){
//						throw new ParameterException("请先打印生成派车单或者提货单！");
//					}	
//				}	
			}
		}
		 
		//返回页面数据
		if(transportSignRecord != null && transportSignRecord.getQszt() != null && transportSignRecord.getQszt() != 0){//已签收
			transportSignRecordResult.setIsSign(true);
			transportSignRecordResult.setQszt(transportSignRecord.getQszt());
			transportSignRecordResult.setQsr(transportSignRecord.getQsr());
			transportSignRecordResult.setPsjianshu(transportSignRecord.getPsjianshu());
			transportSignRecordResult.setDsjianshu(transportSignRecord.getDsjianshu());
			transportSignRecordResult.setQsTime(DateUtils.getCurrentHourAndMinutesTime(transportSignRecord.getQsTime()));
			transportSignRecordResult.setComm(transportSignRecord.getComm());
			transportSignRecordResult.setQsrphone(transportSignRecord.getQsrphone());
			transportSignRecordResult.setLrr(transportSignRecord.getLrr());
			transportSignRecordResult.setLrTime(transportSignRecord.getLrTime() == null? "" : DateUtils.getCurrentHourAndMinutesTime(transportSignRecord.getLrTime()));
		}else if(transportSignRecord != null && transportSignRecord.getOldQsTime() != null){//未签收，但做过撤销签收
			/**
			 * 新增需求：第一次签收默认系统时间，当月内撤销后可以更改签收时间和签收状态，跨月后显示当月内最后一次签收的时间和状态
			 * 2018-02-07 11:36
			 * yanxif
			 */
			//判断当前签收时间和第一次签收时间是否为同一个月,old_qstime时间为空，TMS做的提货签收操作，可以直接签收
			if(transportSignRecord.getOldQsTime() == null || DateUtils.isTimeInterval(transportSignRecord.getOldQsTime(), new Date())){//可以更改签收时间
				transportSignRecordResult.setIsSign(false);
				transportSignRecordResult.setLrr(transportSignRecordSearch.getUserName());
				transportSignRecordResult.setLrTime(DateUtils.getNowDateHourAndMinutesStr());
				transportSignRecordResult.setIsUndo(false);
				transportSignRecordResult.setQsTime(DateUtils.getNowDateHourAndMinutesStr());
			}else{//更改签收时间为上月最后一次签收时间
				transportSignRecordResult.setIsSign(false);
				transportSignRecordResult.setLrr(transportSignRecordSearch.getUserName());
				transportSignRecordResult.setLrTime(DateUtils.getNowDateHourAndMinutesStr());
				transportSignRecordResult.setIsUndo(true);
				transportSignRecordResult.setQsTime(DateUtils.getCurrentTimeStr(transportSignRecord.getOldQsTime()));
				if(transportSignRecord.getRecordQsTime() != null){
					transportSignRecordResult.setQsTime(DateUtils.getCurrentHourAndMinutesTime(transportSignRecord.getRecordQsTime()));	
				}		
			}
		}else{//未签收，且未做过撤销签收
			transportSignRecordResult.setIsSign(false);
			transportSignRecordResult.setLrr(transportSignRecordSearch.getUserName());
			transportSignRecordResult.setLrTime(DateUtils.getNowDateHourAndMinutesStr());
			transportSignRecordResult.setIsUndo(false);
			transportSignRecordResult.setQsTime(DateUtils.getNowDateHourAndMinutesStr());
		}

		transportSignRecordResult.setYdbhid(ydbhid);
		transportSignRecordResult.setFazhan(transportOrder.getFazhan());
		transportSignRecordResult.setDaozhan(transportOrder.getDaozhan());
		transportSignRecordResult.setFhdwmch(transportOrder.getFhdwmch());
		transportSignRecordResult.setShhrmch(transportOrder.getShhrmch());
		transportSignRecordResult.setCxNumber(transportSignRecord.getCxNumber());

		if(transportOrder.getFhdwlxdh() != null){
			transportSignRecordResult.setFhdwlxdh(transportOrder.getFhdwlxdh());
		}else if(transportOrder.getFhdwyb() != null){
			transportSignRecordResult.setFhdwlxdh(transportOrder.getFhdwyb());
		}else{
			transportSignRecordResult.setFhdwlxdh("");
		}

		if(transportOrder.getShhrlxdh() != null){
			transportSignRecordResult.setShhrlxdh(transportOrder.getShhrlxdh());
		}else if(transportOrder.getShhryb() != null){
			transportSignRecordResult.setShhrlxdh(transportOrder.getShhryb());
		}else{
			transportSignRecordResult.setShhrlxdh("");
		}

		if(carOutResult != null && carOutResult.getPcshlc() > 0){
			transportSignRecordResult.setShlc(carOutResult.getPcshlc());
			transportSignRecordResult.setMax(carOutResult.getMax());
			transportSignRecordResult.setMin(carOutResult.getMin());
			transportSignRecordResult.setLc(carOutResult.getMin().toString() + '-' + carOutResult.getMax().toString());
		}else{
			transportSignRecordResult.setShlc(0);
			transportSignRecordResult.setMax(0);
			transportSignRecordResult.setMin(0);
			transportSignRecordResult.setLc("");
		}

		//查询绿色通道信息
		Integer greenWay = 0;
		try {
			greenWay = this.getGreenWayByYdbhid(ydbhid);
		} catch (Exception e) {
			throw new BusinessException("获取绿色通道客户信息失败", e);
		}
		if(greenWay == 1){
			transportSignRecordResult.setIsGreenWay(true);
		}else{
			transportSignRecordResult.setIsGreenWay(false);
		}
		return transportSignRecordResult;
	}

	/**
	 * 签收日志数据转换
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年10月31日
	 * @param transportSignRecord
	 * @param transportSignRecordLog
	 */
	public void transportSignRecordLogConversions(TransportSignRecord transportSignRecord,TransportSignRecordLog transportSignRecordLog){
		transportSignRecordLog.setYdbhid(transportSignRecord.getYdbhid());
		transportSignRecordLog.setQsr(transportSignRecord.getQsr());
		transportSignRecordLog.setQsTime(transportSignRecord.getQsTime());
		transportSignRecordLog.setLrr(transportSignRecord.getLrr());
		transportSignRecordLog.setLrTime(transportSignRecord.getLrTime());
		transportSignRecordLog.setQszt(transportSignRecord.getQszt());
		transportSignRecordLog.setComm(transportSignRecord.getComm());
		transportSignRecordLog.setLrrGrid(transportSignRecord.getLrrGrid());
		transportSignRecordLog.setGs(transportSignRecord.getGs());
		transportSignRecordLog.setYxfhsj(transportSignRecord.getYxfhsj());
		transportSignRecordLog.setJianshu(transportSignRecord.getJianshu());
		transportSignRecordLog.setQrr(transportSignRecord.getQrr());
		transportSignRecordLog.setQrrGrid(transportSignRecord.getQrrGrid());
		transportSignRecordLog.setQrTime(transportSignRecord.getQrTime());
		transportSignRecordLog.setFdqsTime(transportSignRecord.getFdqsTime());
		transportSignRecordLog.setXgsj(new Date());
		transportSignRecordLog.setStr1(transportSignRecord.getStr1());
		transportSignRecordLog.setStr2(transportSignRecord.getStr2());
		transportSignRecordLog.setStr3(transportSignRecord.getStr3());
	}

	@Override
	public void addTransportSignRecordLog(TransportSignRecordLog transportSignRecordLog)
			throws ParameterException, BusinessException {
		if(transportSignRecordLog == null){
			throw new ParameterException("transportSignRecordLog", transportSignRecordLog, "签收日志表信息不能为空");
		}
		try {
			transportSignRecordMapper.insertTransportSignRecordLog(transportSignRecordLog);
		} catch (Exception e) {
			throw new BusinessException("更新签收日志信息失败", e);
		}
	}

	@Override
	public void modifyTransportSignRecordByYdbhid(TransportSignRecord transportSignRecord)
			throws ParameterException, BusinessException {	
		if(transportSignRecord == null){
			throw new ParameterException("transportSignRecord", transportSignRecord, "签收表信息不能为空");
		}	
		try {
			transportSignRecordMapper.updateTransportSignRecordByYdbhid(transportSignRecord);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("更新签收信息失败", e);
		}
	}

	@Override
	public void modifyTransportSignRecordLogByYdbhid(TransportSignRecordLog transportSignRecordLog)
			throws ParameterException, BusinessException {
		if(transportSignRecordLog == null){
			throw new ParameterException("transportSignRecordLog", transportSignRecordLog, "签收日志表信息不能为空");
		}
		try {
			transportSignRecordMapper.updateTransportSignRecordLogByYdbhid(transportSignRecordLog);
		} catch (Exception e) {
			throw new BusinessException("更新签收日志信息失败", e);
		}
	}

	@Override
	public void removeHwydRouteByYdbhid(String ydbhid) throws ParameterException, BusinessException {
		if(StringUtils.isEmpty(ydbhid)){
			throw new ParameterException("ydbhid", ydbhid, "运单编号不能为空");
		}
		try {
			transportSignRecordMapper.deleteHwydRouteByYdbhid(ydbhid, TransportSignRecordServiceImpl.REVOCATION_SIGN_IN);
		} catch (Exception e) {
			throw new BusinessException("删除在途跟踪已撤销信息失败", e);
		}
	}

	@Override
	public TransportSignRecord getTransportSignRecordByYdbhid(String ydbhid)
			throws ParameterException, BusinessException {
		if(StringUtils.isEmpty(ydbhid)){
			throw new ParameterException("ydbhid", ydbhid, "运单编号不能为空");
		}
		TransportSignRecord result = new TransportSignRecord();
		try {
			result = transportSignRecordMapper.selectTransportSignRecordByYdbhid(ydbhid);
		} catch (Exception e) {
			throw new BusinessException("获取签收信息失败", e);
		}	
		return result;
	}

	@Override
	public void modifyTransportProcessStockByGsAndYdbhid(TransportProcessStock transportProcessStock)
			throws ParameterException, BusinessException {
		if(transportProcessStock == null){
			throw new ParameterException("transportProcessStock", transportProcessStock, "分理库存信息不能为空");
		}
		try {
			transportSignRecordMapper.updatetransportProcessStockByGsAndYdbhid(transportProcessStock);
		} catch (Exception e) {
			throw new BusinessException("更新分离库存信息失败", e);
		}
	}

	@Override
	public void addTransportOrderTrace(TransportOrderTrace transportOrderTrace)
			throws ParameterException, BusinessException {
		if(transportOrderTrace == null){
			throw new ParameterException("transportOrderTrace", transportOrderTrace, "订单轨迹信息不能为空");
		}
		try {
			transportSignRecordMapper.insertTransportOrderTrace(transportOrderTrace);
		} catch (Exception e) {
			throw new BusinessException("新增分离库存信息失败", e);
		}
	}

	@Override
	public FiwtResult getXianluByYdbhid(String ydbhid) throws ParameterException, BusinessException {
		if(StringUtils.isEmpty(ydbhid)){
			throw new ParameterException("ydbhid", ydbhid, "运单编号不能为空");
		}
		FiwtResult result = new FiwtResult();
		try {
			result = transportSignRecordMapper.selectXianluByYdbhid(ydbhid);
		} catch (Exception e) {		
			throw new BusinessException("获取财凭信息失败", e);
		}
		return result;
	}

	@Override
	public Integer countIncomeCost(String ydbhid) throws ParameterException, BusinessException {
		if(StringUtils.isEmpty(ydbhid)){
			throw new ParameterException("ydbhid", ydbhid, "运单编号不能为空");
		}
		Integer result = 0;
		try {
			result =  transportSignRecordMapper.countIncomeCost(ydbhid);
		} catch (Exception e) {
			throw new BusinessException("获取成本和财凭信息失败", e);
		}
		return result;
	}

	@Override
	public TransportOrder getTransportOrderByYdbhid(String ydbhid) throws ParameterException, BusinessException {
		if(StringUtils.isEmpty(ydbhid)){
			throw new ParameterException("ydbhid", ydbhid, "运单编号不能为空");
		}
		TransportOrder result = new TransportOrder();
		try {
			result = transportSignRecordMapper.getTransportOrderByYdbhid(ydbhid);
		} catch (Exception e) {
			throw new BusinessException("获取运单信息失败", e);
		}
		return result;
	}

	@Override
	public Boolean getFkfsh(FiwtResult fiwtResult) throws ParameterException, BusinessException {
		if(fiwtResult == null){
			throw new ParameterException("fiwtResult", fiwtResult, "财凭信息不能为空");
		}
		Boolean result = false;
		try {
			result = transportSignRecordMapper.selectFkfsh(fiwtResult);
		} catch (Exception e) {
			throw new BusinessException("获取财凭信息失败", e);
		}
		return result;
	}



	@Override
	public ShqsdResult getReachAfterPayInfo(String ydbhid) throws ParameterException, BusinessException {
		if(StringUtils.isEmpty(ydbhid)){
			throw new ParameterException("ydbhid", ydbhid, "运单编号不能为空");
		}
		ShqsdResult result = new ShqsdResult();
		try {
			result = transportSignRecordMapper.getReachAfterPayInfo(ydbhid);
		} catch (Exception e) {
			throw new BusinessException("获取是否到付信息失败", e);
		}
		return result;
	}

	@Override
	public CarOutResult getCarOutByYdbhid(String ydbhid) throws ParameterException, BusinessException {
		if(StringUtils.isEmpty(ydbhid)){
			throw new ParameterException("ydbhid", ydbhid, "运单编号不能为空");
		}
		CarOutResult result = new CarOutResult();
		try {
			result = transportSignRecordMapper.selectCarOutByYdbhid(ydbhid);
		} catch (Exception e) {
			throw new BusinessException("获取派车单信息失败", e);
		}
		return result;
	}

	@Override
	public String getThqsdByYdbhid(String ydbhid) throws ParameterException, BusinessException {
		if(StringUtils.isEmpty(ydbhid)){
			throw new ParameterException("ydbhid", ydbhid, "运单编号不能为空");
		}
		String result = "";
		try {
			result = transportSignRecordMapper.selectThqsdByYdbhid(ydbhid);
		} catch (Exception e) {
			throw new BusinessException("获取提货单信息失败", e);
		}
		return result;
	}

	@Override
	public List<String> getCwsjDetailByYshm(String yshm) throws ParameterException, BusinessException {
		List<String> result = new ArrayList<String>();
		try {
			result = transportSignRecordMapper.selectCwsjDetailByYshm(yshm);
		} catch (Exception e) {
			throw new BusinessException("获取收据打印信息失败", e);
		}
		return result;
	}

	@Override
	public String getCarOutShjhmByYdbhid(String ydbhid) throws ParameterException, BusinessException {
		if(StringUtils.isEmpty(ydbhid)){
			throw new ParameterException("ydbhid", ydbhid, "运单编号不能为空");
		}
		String result = "";
		try {
			result = transportSignRecordMapper.selectCarOutShjhmByYdbhid(ydbhid);
		} catch (Exception e) {
			throw new BusinessException("获取派车单信息失败", e);
		}
		return result;
	}

	@Override
	public String getThqsdShjhmByYdbhid(String ydbhid) throws ParameterException, BusinessException {
		if(StringUtils.isEmpty(ydbhid)){
			throw new ParameterException("ydbhid", ydbhid, "运单编号不能为空");
		}
		String result = "";
		try {
			result = transportSignRecordMapper.selectThqsdShjhmByYdbhid(ydbhid);
		} catch (Exception e) {
			throw new BusinessException("获取提货单信息失败", e);
		}
		return result;
	}

	@Override
	public List<TransportQsztBase> getQsztBase() throws ParameterException, BusinessException {
		List<TransportQsztBase> result = new ArrayList<TransportQsztBase>();
		try {
			result = transportSignRecordMapper.selectQsztBase();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("获取签收状态信息失败", e);
		}
		return result;
	}

	@Override
	public Integer getGreenWayByYdbhid(String ydbhid) throws ParameterException, BusinessException {
		if(StringUtils.isEmpty(ydbhid)){
			throw new ParameterException("ydbhid", ydbhid, "运单编号不能为空");
		}
		Integer result = 0;
		try {
			result = transportSignRecordMapper.selectGreenWayByYdbhid(ydbhid);
		} catch (Exception e) {
			throw new BusinessException("获取绿色通道信息失败", e);
		}
		return result;
	}

	@Override
	public TransportSignRecordResult searchUndoSign(TransportSignRecordSearch transportSignRecordSearch)
			throws ParameterException, BusinessException {

		String ydbhid = transportSignRecordSearch.getYdbhid();
		TransportSignRecordResult transportSignRecordResult = new TransportSignRecordResult();

		//运单号不能为空
		if(StringUtils.isEmpty(ydbhid) || (ydbhid.length() != 10 && ydbhid.length() != 12)){
			throw new ParameterException("请输入正确的运单编号！");
		}

		TransportOrder transportOrder = new TransportOrder();
		try {
			transportOrder = transportOrderService.getTransportOrderByYdbhid(ydbhid);
		} catch (Exception e) {
			throw new BusinessException("获取运单信息失败", e);
		}
		if(transportOrder == null){
			throw new ParameterException("该运单号不存在！");
		}

		//判断用户单据撤销超级权限
		Integer permission = transportRightMapper.getRightNum(AuthorticationConstant.SUP_CANCEL_RIGHT, transportSignRecordSearch.getAccount());

		if ((null == permission) || (0 == permission)){
			//判断是否是本公司用户撤销签收
			if(!(transportSignRecordSearch.getCompany().equals(transportOrder.getFazhan()) 
					|| transportSignRecordSearch.getCompany().equals(transportOrder.getDaozhan()) 
					|| transportSignRecordSearch.getCompany().equals(TransportSignRecordServiceImpl.COMPANY_HEADQUARTER))){
				throw new ParameterException("本票货当前到站是“" + transportOrder.getDaozhan() + "”不是本公司,禁止撤销签收！");
			}
		}

		CarOutResult carOutResult = new CarOutResult();
		try {
			carOutResult = this.getCarOutByYdbhid(ydbhid);
		} catch (Exception e) {
			throw new BusinessException("获取派车信息失败", e);
		}

		//返回页面数据
		//查询签收信息并判断是否已签收
		TransportSignRecord transportSignRecord = new TransportSignRecord();
		try {
			transportSignRecord = this.getTransportSignRecordByYdbhid(ydbhid);
		} catch (Exception e) {
			throw new BusinessException("获取签收信息失败", e);
		}

		if(transportSignRecord != null && transportSignRecord.getQszt() != null && transportSignRecord.getQszt() != 0){//已签收
			transportSignRecordResult.setIsSign(true);
			transportSignRecordResult.setQszt(transportSignRecord.getQszt());
			transportSignRecordResult.setQsr(transportSignRecord.getQsr());
			transportSignRecordResult.setPsjianshu(transportSignRecord.getPsjianshu());
			transportSignRecordResult.setDsjianshu(transportSignRecord.getDsjianshu());
			transportSignRecordResult.setQsTime(DateUtils.getCurrentHourAndMinutesTime(transportSignRecord.getQsTime()));
			transportSignRecordResult.setComm(transportSignRecord.getComm());
			transportSignRecordResult.setQsrphone(transportSignRecord.getQsrphone());
			transportSignRecordResult.setLrr(transportSignRecord.getLrr());
			transportSignRecordResult.setLrTime(transportSignRecord.getLrTime() == null? "" : DateUtils.getCurrentHourAndMinutesTime(transportSignRecord.getLrTime()));
		}else{//未签收
			throw new ParameterException("运单号：“" + ydbhid + "”的运单未签收，不能做撤销签收操作！");
		}

		transportSignRecordResult.setYdbhid(ydbhid);
		transportSignRecordResult.setFazhan(transportOrder.getFazhan());
		transportSignRecordResult.setDaozhan(transportOrder.getDaozhan());
		transportSignRecordResult.setFhdwmch(transportOrder.getFhdwmch());
		transportSignRecordResult.setShhrmch(transportOrder.getShhrmch());

		if(transportOrder.getFhdwlxdh() != null){
			transportSignRecordResult.setFhdwlxdh(transportOrder.getFhdwlxdh());
		}else if(transportOrder.getFhdwyb() != null){
			transportSignRecordResult.setFhdwlxdh(transportOrder.getFhdwyb());
		}else{
			transportSignRecordResult.setFhdwlxdh("");
		}

		if(transportOrder.getShhrlxdh() != null){
			transportSignRecordResult.setShhrlxdh(transportOrder.getShhrlxdh());
		}else if(transportOrder.getShhryb() != null){
			transportSignRecordResult.setShhrlxdh(transportOrder.getShhryb());
		}else{
			transportSignRecordResult.setShhrlxdh("");
		}

		if(carOutResult != null && carOutResult.getPcshlc()!=null && carOutResult.getPcshlc() > 0){
			transportSignRecordResult.setShlc(carOutResult.getPcshlc());
			transportSignRecordResult.setMax(carOutResult.getMax());
			transportSignRecordResult.setMin(carOutResult.getMin());
			transportSignRecordResult.setLc(carOutResult.getMin().toString() + '-' + carOutResult.getMax().toString());
		}else{
			transportSignRecordResult.setShlc(0);
			transportSignRecordResult.setMax(0);
			transportSignRecordResult.setMin(0);
			transportSignRecordResult.setLc("");
		}

		//查询绿色通道信息
		Integer greenWay = 0;
		try {
			greenWay = this.getGreenWayByYdbhid(ydbhid);
		} catch (Exception e) {
			throw new BusinessException("获取绿色通道客户信息失败", e);
		}
		if(greenWay == 1){
			transportSignRecordResult.setIsGreenWay(true);
		}else{
			transportSignRecordResult.setIsGreenWay(false);
		}

		return transportSignRecordResult;
	}

	@Override
	public String getFlagByYdbhid(String ydbhid) throws ParameterException, BusinessException {
		if(StringUtils.isEmpty(ydbhid)){
			throw new ParameterException("ydbhid", ydbhid, "运单编号不能为空");
		}
		return transportSignRecordMapper.selectFlagByYdbhid(ydbhid);
	}

	@Override
	public Integer getLdqppassByYdbhid(String ydbhid) throws ParameterException, BusinessException {
		if(StringUtils.isEmpty(ydbhid)){
			throw new ParameterException("ydbhid", ydbhid, "运单编号不能为空");
		}
		return transportSignRecordMapper.selectLdqppassByYdbhid(ydbhid);
	}

	@Override
	public void addUndoSignLog(UndoSignLog undoSignLog) throws ParameterException, BusinessException {
		if(undoSignLog == null){
			throw new ParameterException("undoSignLog", undoSignLog, "撤销日志信息不能为空");
		}
		transportSignRecordMapper.insertUndoSignLog(undoSignLog);

	}

	@Override
	public void addHwydRoute(HwydRoute hwydRoute) throws ParameterException, BusinessException {
		if(hwydRoute == null){
			throw new ParameterException("hwydRoute", hwydRoute, "在途信息不能为空");
		}
		transportSignRecordMapper.insertHwydRoute(hwydRoute);	
	}

	@Override
	public void removeTransportOrderTraceByYdbhid(String ydbhid) throws ParameterException, BusinessException {
		if(StringUtils.isEmpty(ydbhid)){
			throw new ParameterException("ydbhid", ydbhid, "运单编号不能为空");
		}
		transportSignRecordMapper.deleteTransportOrderTraceByYdbhid(ydbhid);
	}

	@Override
	@Transactional(rollbackFor = {ParameterException.class, BusinessException.class})
	public String undoSign(TransportSignRecordSearch transportSignRecordSearch) throws ParameterException, BusinessException {
		// 查询用户权限
		Integer permission = transportRightMapper.getRightNum(AuthorticationConstant.HCZZQD_CANCEL, transportSignRecordSearch.getAccount());
		if ((null == permission) || (0 == permission)){
			return null;
		}

		String ydbhid = transportSignRecordSearch.getYdbhid();
		//查询签收信息
		TransportSignRecord transportSignRecord = new TransportSignRecord();
		try {
			transportSignRecord = this.getTransportSignRecordByYdbhid(ydbhid);
		} catch (Exception e) {
			throw new BusinessException("获取签收信息失败", e);
		}

		Integer qszt = transportSignRecord.getQszt();

		//若客户签收状态为 破损、短少、综合事故、客户要求延时、其它，则检查是否有破损短少责任划分生成
		if(transportSignRecord != null && (qszt == 1 || qszt == 2 || qszt == 3 || qszt == 20 || qszt == 21)){
			String flag = "";
			try {
				flag = this.getFlagByYdbhid(ydbhid);
			} catch (Exception e) {			
				throw new BusinessException("获取运单破损短少责任划分信息失败", e);
			}

			if("1".equals(flag)){
				throw new ParameterException("运单号：“" + ydbhid + "”的运单破损短少责任划分已经完成，请先撤销破损短少责任划分!");
			}
		}

		//查询理赔信息
		Integer ldqppass = 0;
		try {
			ldqppass = this.getLdqppassByYdbhid(ydbhid);
		} catch (Exception e) {			
			throw new BusinessException("获取理赔信息失败", e);
		}
		if(ldqppass != null && ldqppass == 1){
			throw new ParameterException("运单号：“" + ydbhid + "”的运单已经提交理赔报告，并且已经签批同意！请先撤销理赔报告，再撤销客户签收信息！");
		}
		if(ldqppass != null && ldqppass == 100){
			throw new ParameterException("运单号：“" + ydbhid + "”的运单已经提交理赔报告，请先撤销理赔报告，再撤销客户签收信息！");
		}

		//新增撤销信息记录
		UndoSignLog undoSignLog = new UndoSignLog();
		undoSignLog.setYdbhid(ydbhid);
		undoSignLog.setOpeGrid(transportSignRecordSearch.getAccount());
		undoSignLog.setOpeXm(transportSignRecordSearch.getUserName());
		undoSignLog.setOpeType("分理管理--客户签收撤销-删除");
		try {
			this.addUndoSignLog(undoSignLog);
		} catch (Exception e) {			
			throw new BusinessException("新增撤销日志失败", e);
		}

		//更新签收信息
		TransportSignRecord undoTransportSignRecord = new TransportSignRecord();
		if(transportSignRecord.getOldQsTime() == null) 
			transportSignRecord.setOldQsTime(transportSignRecord.getQsTime());
		if(transportSignRecord.getRecordQsTime() == null) 
			transportSignRecord.setRecordQsTime(transportSignRecord.getOldQsTime());
		undoTransportSignRecord.setOldQsTime(transportSignRecord.getOldQsTime());
		undoTransportSignRecord.setRecordQsTime(transportSignRecord.getRecordQsTime());

		undoTransportSignRecord.setSfxgbs(1);
		undoTransportSignRecord.setQszt(0);
		undoTransportSignRecord.setPsjianshu(0.0);
		undoTransportSignRecord.setDsjianshu(0.0);
		undoTransportSignRecord.setQsr("");
		undoTransportSignRecord.setQsTime(null);
		undoTransportSignRecord.setLrr("");
		undoTransportSignRecord.setLrTime(null);
		undoTransportSignRecord.setComm("");
		undoTransportSignRecord.setCxFlag("1");
		undoTransportSignRecord.setYdbhid(ydbhid);
		undoTransportSignRecord.setCxNumber(transportSignRecord.getCxNumber()==null?1:transportSignRecord.getCxNumber() + 1);
		try {
			transportSignRecordMapper.undoSignByYdbhid(undoTransportSignRecord);
		} catch (Exception e) {			
			throw new BusinessException("更新签收信息失败", e);
		}

		//在途表新增一条已撤销签收 的信息
		HwydRoute hwydRoute = new HwydRoute();
		hwydRoute.setYdbhid(ydbhid);
		hwydRoute.setShiJian(new Date());
		hwydRoute.setCztype("已撤销签收");
		hwydRoute.setGrid(transportSignRecordSearch.getAccount());
		hwydRoute.setPushFlag(0);
		try {
			this.addHwydRoute(hwydRoute);
		} catch (Exception e) {			
			throw new BusinessException("新增在途信息失败", e);
		}

		//删除轨迹信息
		try {
			this.removeTransportOrderTraceByYdbhid(ydbhid);
		} catch (Exception e) {			
			throw new BusinessException("删除轨迹信息失败", e);
		}	

		//新增异常日志信息
		ExceptionLog exceptionLog = new ExceptionLog();
		exceptionLog.setOperatorName(transportSignRecordSearch.getUserName());
		exceptionLog.setOperatorAccount(transportSignRecordSearch.getAccount());
		exceptionLog.setOperatorCompany(transportSignRecordSearch.getCompany());
		exceptionLog.setIpAddress(IPUtil.getLocalIP());
		exceptionLog.setYdbhid(ydbhid);
		exceptionLog.setOperatingMenu(OPERATING_MENU);
		String operatingContent = "";
		if(transportSignRecord.getOldQsTime() != null){
			operatingContent = "运单编号为：" + ydbhid + "的签收记录撤销，第一次签收时间为：" + DateUtils.getCurrentTimeStr(transportSignRecord.getOldQsTime()) + ",再次签收时将不可更改！";
		}else{
			operatingContent = "运单编号为：" + ydbhid + "的签收记录撤销成功！";
		}
		exceptionLog.setOperatingContent(operatingContent);
		exceptionLog.setOperatingTime(new Date());
		exceptionLog.setCreateName(transportSignRecordSearch.getUserName());
		exceptionLog.setCreateTime(new Date());
		exceptionLog.setUpdateName(transportSignRecordSearch.getUserName());
		exceptionLog.setUpdateTime(new Date());

		try {
			exceptionLogService.addExceptionLog(exceptionLog);
		} catch (Exception e) {			
			throw new BusinessException("新增异常日志信息失败", e);
		}

		Integer deliveryNumber = deliveryOperateService.deliveryNumber(ydbhid);
		try {
			arriveStationService.undoSignArriveStation(deliveryNumber,ydbhid);
		}catch (BusinessException e) {			
			throw new BusinessException(e.getTipMessage());
		}catch (ParameterException e) {			
			throw new ParameterException(e.getTipMessage());
		}

		return ydbhid;
	}

	@Deprecated
	@Override
	public boolean batchTransportSign(TransportSignRecordEntity transportSignRecordEntity)
			throws ParameterException, BusinessException {
		boolean flag = false;
		String ydbhid = transportSignRecordEntity.getTransportSignRecord().getYdbhid();
		/*************************************************是否可以做签收权限校验***********************************************************************/
		//1）运单号不能为空
		if(ydbhid.length() != 10 || StringUtils.isEmpty(ydbhid)){
			throw new ParameterException("请输入正确的运单编号！");
		}

		TransportOrder transportOrder = new TransportOrder();
		try {
			transportOrder = transportOrderService.getTransportOrderByYdbhid(ydbhid);
		} catch (Exception e) {
			throw new BusinessException("获取运单信息失败", e);
		}
		if(transportOrder == null){
			throw new ParameterException("该运单号不存在！");
		}

		List<BundleReceipt> bundleReceiptList = new ArrayList<BundleReceipt>();
		try {
			bundleReceiptList = bundleReceiptService.getLastBundleReceiptByYdbhid(ydbhid);
		} catch (Exception e) {
			throw new BusinessException("获取装载信息失败", e);
		}
		if(bundleReceiptList == null || bundleReceiptList.size() == 0){
			throw new ParameterException("该运单没有做装载操作！");
		}
		BundleReceipt bundleReceipt = bundleReceiptList.get(0);

		//2）检查运单号是否已经作废
		Long isOrderCanceled = transportSignRecordMapper.isOrderCanceled(ydbhid);
		Assert.trueIsWrong("isOrderCanceled > 0", isOrderCanceled > 0, "该票货物运单已作废！请确认！");

		//查询签收信息并判断是否已签收
		TransportSignRecord transportSignRecord = new TransportSignRecord();
		try {
			transportSignRecord = this.getTransportSignRecordByYdbhid(ydbhid);
		} catch (Exception e) {
			throw new BusinessException("获取签收信息失败", e);
		}

		if(transportSignRecord != null && transportSignRecord.getQszt() != null && transportSignRecord.getQszt() != 0){//已签收
			throw new ParameterException("该运单已签收，不能再次签收！");
		}

		//3）检查是否做过成本
		Integer countIncomeCost = 0;
		try {
			countIncomeCost = this.countIncomeCost(ydbhid);
		} catch (Exception e) {
			throw new BusinessException("获取成本信息失败", e);
		}
		if(countIncomeCost <= 0){
			throw new ParameterException("该运单未做成本，请先做成本再签收！");
		}

		//4）判断是否收款
		FiwtResult fiwtResult = new FiwtResult();
		try {
			fiwtResult = this.getXianluByYdbhid(ydbhid);
		} catch (Exception e) {
			throw new BusinessException("获取财凭信息失败", e);
		}
		if(fiwtResult == null || StringUtils.isEmpty(fiwtResult.getXianlu())){
			throw new ParameterException("运单号为 " + ydbhid + "的货物还未做财凭录入，不能签收！");
		}else{
			Boolean yshzhk = transportSignRecordMapper.selectFkfsh(fiwtResult);
			if(yshzhk != null && yshzhk == true){
				Integer fhtzd = transportSignRecordMapper.selectFhtzd(ydbhid);
				Assert.trueIsWrong("fhtzd == 0", fhtzd == 0, "当前运单状态是款未付且还未发放货通知，不能签收！");
			}
		}

		//5）判断是否是本公司用户签收
		if(bundleReceipt.getFazhan().equals(transportOrder.getFazhan()) || bundleReceipt.getDaozhan().equals(transportOrder.getDaozhan())){
			if(!(transportSignRecordEntity.getCompany().equals(transportOrder.getFazhan()) || transportSignRecordEntity.getCompany().equals(transportOrder.getDaozhan()))){
				throw new ParameterException("本票货当前到站是“" + transportOrder.getDaozhan() + "”不是本公司,禁止录入客户签收！");
			}
		}else{
			throw new ParameterException("运单号 " + ydbhid + "的运单发站或者到站和装载不一致，不能签收！");
		}

		//判断是否到货
		if(bundleReceipt.getiType() == 2 && bundleReceipt.getYdzh() == 0){//提货未到货
			throw new ParameterException("该运单需要先做到站处理才能签收！");
		}

		if(bundleReceipt.getiType() == 0 && TransportSignRecordServiceImpl.TRANSFER_YES.equals(bundleReceipt.getTransferFlag()) && bundleReceipt.getYdzh() == 0){
			//干线中转且未到货
			throw new ParameterException("该运单需要先做到站处理才能签收！");
		}

		if((bundleReceipt.getiType() == 0 && TransportSignRecordServiceImpl.TRANSFER_NO.equals(bundleReceipt.getTransferFlag()) && bundleReceipt.getYdzh() == 0) ||
				(bundleReceipt.getiType() == 1 && bundleReceipt.getYdzh() == 0) || 
				(bundleReceipt.getiType() == 0 && (bundleReceipt.getTransferFlag() == null || bundleReceipt.getTransferFlag() == "") && bundleReceipt.getYdzh() == 0)){
			//干线非中转未到货、配送未到货、干线未到货（TMS录入无法获取其中转还是未中转，只要未到货直接做到货处理并签收）
			//调用方法去做到站处理
			List<String> ydbhidList = new ArrayList<String>();
			ydbhidList.add(ydbhid);

			RequestJsonEntity requestJsonEntity = new RequestJsonEntity();
			Map<String, Object> map = new HashMap<String, Object>();
			List<Map<String, Object>> param = new ArrayList<Map<String, Object>>();

			requestJsonEntity.put("grid", transportSignRecordEntity.getAccount());
			requestJsonEntity.put("gs", transportSignRecordEntity.getCompany());
			map.put("ydbhids", ydbhidList);
			map.put("chxh", bundleReceipt.getChxh());
			param.add(map);
			requestJsonEntity.put("parama", param);
			requestJsonEntity.put("dhsj", DateUtils.getNowDateStr());
			try {
				requestJsonEntity.put("storeHouse", adjunctSomethingMapper.getStoreHouseBycompany(transportSignRecordEntity.getCompany()).get(0));
				/**
				 * 提货未到货，做提货到站处理（不用检验装载清单发站和到站）
				 * yanxf
				 * 2018-03-21 
				 */
				Integer extract = 0;
				if(bundleReceipt.getiType() == 2 && bundleReceipt.getYdzh() == 0){
					extract = 1;
				}
				//this.saveSignRecordGoodArrive(requestJsonEntity,extract,bundleReceipt);		
			} catch (ParameterException e) {
				throw new ParameterException(e.getTipMessage());
			}
			//catch (ParseException e) {
			//	e.printStackTrace();
			//}	
		}

		/*************************************************校验通过，做签收操作***********************************************************************/
		TransportSignRecord signRecord = transportSignRecordEntity.getTransportSignRecord();

		int qszt = signRecord.getQszt();//获取签收状态
		if(qszt == 0){
			throw new ParameterException("请选择签收状态!");
		}else if(qszt == 21){
			throw new ParameterException("系统禁止使用【客户要求延时(21)】签收类型！");
		}else if(qszt > 0 && qszt <= 10){
			Double sum = signRecord.getPsjianshu() + signRecord.getDsjianshu();
			if(sum <= 0){
				throw new ParameterException("请录入破损件数或者短少件数！");
			}
			String common = signRecord.getComm();
			if(StringUtils.isEmpty(common)){
				throw new ParameterException("请录入备注信息!");
			}
		}
		if(null == signRecord.getQsTime()){
			throw new ParameterException("请录入签收时间!");
		}
		if(StringUtils.isEmpty(signRecord.getQsr())){
			throw new ParameterException("请录入签收人!");
		}

		//先判断是否撤销过签收	
		TransportSignRecord transportSignRecordOld = this.getTransportSignRecordByYdbhid(signRecord.getYdbhid());
		if(transportSignRecordOld != null && "1".equals(transportSignRecordOld.getCxFlag())){
			//更新签收时间为原来的签收时间
			signRecord.setQsTime(transportSignRecordOld.getOldQsTime());
			flag = true;
		}

		Date fchrqTime;
		try {
			fchrqTime = bundleReceiptService.getLastBundleReceiptByYdbhid(signRecord.getYdbhid()).get(0).getFchrq();
		} catch (Exception e) {
			throw new BusinessException("获取发车时间失败", e);
		}
		Date firstDayOfMonth = DateUtils.getFirstDayOfMonth();//当月1号
		if(signRecord.getQsTime().getTime() <= fchrqTime.getTime() || signRecord.getQsTime().getTime() <= firstDayOfMonth.getTime()){
			throw new ParameterException("签收时间不能小于等于发车时间,也不能小于当月1号!");
		}

		//客户要求延时签收判断（需求确定下是否要加）
		if(signRecord.getQszt() > 0){
			signRecord.setLrr(transportSignRecordEntity.getUserName());
			signRecord.setLrTime(signRecord.getLrTime());
			signRecord.setLrrGrid(transportSignRecordEntity.getAccount());
			signRecord.setGs(transportSignRecordEntity.getCompany());
			signRecord.setSfxgbs(0);
			signRecord.setCxFlag("0");

			/**
			 * 保存为已签收，先判断是否撤销过签收
			 */
			try {
	
				if(transportSignRecordOld != null && "1".equals(transportSignRecordOld.getCxFlag())){//撤销过签收，先删除撤销信息，再修改签收时间
//					TransportSignRecord transportSignRecordNew = new TransportSignRecord();
//					transportSignRecordNew.setCxFlag("0");
//					transportSignRecordNew.setYdbhid(signRecord.getYdbhid());
//
//					//更新签收信息的撤销状态
//					this.modifyTransportSignRecordByYdbhid(transportSignRecordNew);
					//删除已撤销签收信息
					this.removeHwydRouteByYdbhid(signRecord.getYdbhid());
				}else{
					signRecord.setOldQsTime(signRecord.getQsTime());
				}
			} catch (Exception e) {
				throw new BusinessException("更新撤销签收信息失败", e);
			}

			//签收日志信息
			TransportSignRecordLog log = new TransportSignRecordLog();
			transportSignRecordLogConversions(signRecord, log);

			//分理库存信息
			TransportProcessStock transportProcessStock = new TransportProcessStock();
			transportProcessStock.setGs(signRecord.getGs());
			transportProcessStock.setYdbhid(signRecord.getYdbhid());
			transportProcessStock.setYiti(5);

			//订单轨迹信息
			TransportOrderTrace transportOrderTrace = new TransportOrderTrace();
			transportOrderTrace.setUpdateDate(new Date());
			transportOrderTrace.setUpdateUser(transportSignRecordEntity.getUserName());
			transportOrderTrace.setYdbhid(signRecord.getYdbhid());
			transportOrderTrace.setOtDtime(signRecord.getOldQsTime());
			transportOrderTrace.setStatus(3);
			transportOrderTrace.setRemark(signRecord.getComm());
			if(signRecord.getQszt() == 11){
				transportOrderTrace.setRecstatus(0);
			}else{
				transportOrderTrace.setRecstatus(1);
			}

			try {
				//更新签收信息
				this.modifyTransportSignRecordByYdbhid(signRecord);
				// 新增签收日志信息
				this.addTransportSignRecordLog(log);
				//更新分理库存表
				this.modifyTransportProcessStockByGsAndYdbhid(transportProcessStock);
				//新增运单轨迹信息
				this.addTransportOrderTrace(transportOrderTrace);
			} catch (Exception e) {	
				throw new BusinessException(e.getMessage(), e);
			}

		}
		return flag;
	}

	@Override
	public FkfshResult getFkfshResult(FiwtResult xianlu) {
		return transportSignRecordMapper.selectFkfshResult(xianlu);
	}

	@Override
	@Transactional(rollbackFor = {ParameterException.class, BusinessException.class,ParseException.class})
	public boolean batchTransportSignNotCheck(TransportSignRecordEntity transportSignRecordEntity)
			throws ParameterException, BusinessException {		
		boolean flag = false;
		String ydbhid = transportSignRecordEntity.getTransportSignRecord().getYdbhid();
		//1）运单号不能为空
		if(ydbhid.length() != 10 || StringUtils.isEmpty(ydbhid)){
			throw new ParameterException("请输入正确的运单编号！");
		}

		//查询签收信息并判断是否已签收
		TransportSignRecord transportSignRecord = new TransportSignRecord();
		transportSignRecord = this.getTransportSignRecordByYdbhid(ydbhid);
	
		if(transportSignRecord != null && transportSignRecord.getQszt() != null && transportSignRecord.getQszt() != 0){//已签收
			throw new ParameterException("该运单已签收，不能再次签收！");
		}

		List<BundleReceipt> bundleReceiptList = new ArrayList<BundleReceipt>();
		try {
			bundleReceiptList = bundleReceiptService.getLastBundleReceiptByYdbhid(ydbhid);
		} catch (Exception e) {
			throw new BusinessException("获取装载信息失败", e);
		}
		if(bundleReceiptList == null || bundleReceiptList.size() == 0){
			throw new ParameterException("该运单没有做装载操作！");
		}
		BundleReceipt bundleReceipt = bundleReceiptList.get(0);

		//判断是否到货
//		if(bundleReceipt.getiType() == 2 && bundleReceipt.getYdzh() == 0){//提货未到货
//			throw new ParameterException("该运单需要先做到站处理才能签收！");
//		}

		if(bundleReceipt.getiType() == 0 && TransportSignRecordServiceImpl.TRANSFER_YES.equals(bundleReceipt.getTransferFlag()) && bundleReceipt.getYdzh() == 0){
			//干线中转且未到货
			throw new ParameterException("干线中转的运单需要先做到站处理才能签收！");
		}


		//检查该运单如果没有装载完，不允许签收，因为没有装载完不准到货
		boolean isBundleFinish = arriveStationService.hasBundleFinish(ydbhid);
		if(!isBundleFinish) {
			throw new ParameterException("该运单进行了分装，还未装载完毕，不能签收");
		}

		TransportSignRecord signRecord = transportSignRecordEntity.getTransportSignRecord();
		int qszt = signRecord.getQszt();//获取签收状态
		if(qszt == 0){
			throw new ParameterException("请选择签收状态!");
		}else if(qszt == 21){
			throw new ParameterException("系统禁止使用【客户要求延时(21)】签收类型！");
		}else if(qszt > 0 && qszt <= 10){
			Double sum = signRecord.getPsjianshu() + signRecord.getDsjianshu();
			if(sum <= 0){
				throw new ParameterException("请录入破损件数或者短少件数！");
			}
			String common = signRecord.getComm();
			if(StringUtils.isEmpty(common)){
				throw new ParameterException("请录入备注信息!");
			}
		}
		if(null == signRecord.getQsTime()){
			throw new ParameterException("请录入签收时间!");
		}
		if(StringUtils.isEmpty(signRecord.getQsr())){
			throw new ParameterException("请录入签收人!");
		}
		
		BundleReceipt bundle = bundleReceiptService.getLastBundleReceiptByYdbhid(signRecord.getYdbhid()).get(0);
		boolean isArrive = bundle.getYdzh()==1;//是否已经到站
		Date dhsj = bundle.getDhsj();//到货时间
		Date fchrqTime = bundle.getFchrq();//发车时间
		TransportSignRecord transportSignRecordOld = this.getTransportSignRecordByYdbhid(signRecord.getYdbhid());
		TransportOrder order = transportOrderService.getTransportOrderByYdbhid(ydbhid);
//		Date fanghuoshj = transportSignRecordOld.getYxfhsj();
//		if(fanghuoshj!=null && signRecord.getQsTime().getTime() - fanghuoshj.getTime() < 1000*3600*2) {
//			throw new ParameterException("签收时间要在放货时间2个小时之后");
//		}
		if(isArrive && dhsj!=null && signRecord.getQsTime().getTime()<dhsj.getTime()) {
			throw new ParameterException("签收时间要不能小于到站时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dhsj));
		}
		if(signRecord.getQsTime().getTime() > new Date().getTime()) {
			throw new ParameterException("签收时间不能大于当前系统时间");
		}
		if(signRecord.getQsTime().getTime() > signRecord.getLrTime().getTime()) {
			throw new ParameterException("签收时间要小于等于录入时间");
		}
		if(signRecord.getQsTime().getTime() <= fchrqTime.getTime()) {
			throw new ParameterException("签收时间不能小于等于装载发车时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fchrqTime));
		}
		if(signRecord.getQsTime().getTime() <= order.getFhshj().getTime()) {
			throw new ParameterException("签收时间要大于运单托运时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getFhshj()));
		}
		
		
		//自动到货
		HashMap<String,Object> autoArriveParam = new HashMap<String,Object>();
		autoArriveParam.put("grid", transportSignRecordEntity.getAccount());
		autoArriveParam.put("gs", transportSignRecordEntity.getCompany());
		autoArriveParam.put("ydbhid", ydbhid);
		//把签收时间作为到货时间
		String signTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(signRecord.getQsTime());
		autoArriveParam.put("dhsj",signTime);
		arriveStationService.autoArriveStation(autoArriveParam);
		

		//先判断是否撤销过签收	
		if(transportSignRecordOld != null && "1".equals(transportSignRecordOld.getCxFlag())){
			//更新签收时间为原来的签收时间
			signRecord.setQsTime(transportSignRecordOld.getOldQsTime());
			flag = true;
		}
		
		if(signRecord.getQszt() > 0){
			signRecord.setLrr(transportSignRecordEntity.getUserName());
			signRecord.setLrTime(signRecord.getLrTime());
			signRecord.setLrrGrid(transportSignRecordEntity.getAccount());
			signRecord.setGs(transportSignRecordEntity.getCompany());
			signRecord.setSfxgbs(0);
			signRecord.setCxFlag("0");
			signRecord.setRecordQsTime(signRecord.getQsTime());
			signRecord.setSignType(1); // 手动签收标识

			/**
			 * 保存为已签收，先判断是否撤销过签收
			 */	
			if(transportSignRecordOld != null && "1".equals(transportSignRecordOld.getCxFlag())){//撤销过签收，先删除撤销信息，再修改签收时间
//				TransportSignRecord transportSignRecordNew = new TransportSignRecord();
//				transportSignRecordNew.setCxFlag("0");
//				transportSignRecordNew.setYdbhid(signRecord.getYdbhid());
//				//更新签收信息的撤销状态
//				this.modifyTransportSignRecordByYdbhid(transportSignRecordNew);
				//删除已撤销签收信息
				this.removeHwydRouteByYdbhid(signRecord.getYdbhid());
			}else{
				signRecord.setOldQsTime(signRecord.getQsTime());
			}

			//签收日志信息
			TransportSignRecordLog log = new TransportSignRecordLog();
			transportSignRecordLogConversions(signRecord, log);

			//分理库存信息
			TransportProcessStock transportProcessStock = new TransportProcessStock();
			transportProcessStock.setGs(signRecord.getGs());
			transportProcessStock.setYdbhid(signRecord.getYdbhid());
			transportProcessStock.setYiti(5);

			//订单轨迹信息
			TransportOrderTrace transportOrderTrace = new TransportOrderTrace();
			transportOrderTrace.setUpdateDate(new Date());
			transportOrderTrace.setUpdateUser(transportSignRecordEntity.getUserName());
			transportOrderTrace.setYdbhid(signRecord.getYdbhid());
			transportOrderTrace.setOtDtime(signRecord.getOldQsTime());
			transportOrderTrace.setStatus(3);
			transportOrderTrace.setRemark(signRecord.getComm());
			if(signRecord.getQszt() == 11){
				transportOrderTrace.setRecstatus(0);
			}else{
				transportOrderTrace.setRecstatus(1);
			}
		
			//更新签收信息
			this.modifyTransportSignRecordByYdbhid(signRecord);
			// 新增签收日志信息
			this.addTransportSignRecordLog(log);
			//更新分理库存表
			//this.modifyTransportProcessStockByGsAndYdbhid(transportProcessStock);
			//新增运单轨迹信息
			this.addTransportOrderTrace(transportOrderTrace);		
		}
		return flag;
	}

	@Override
	public List<OnTheWay> selectTrackInfo(String wayBillNum) {
        List<OnTheWay> onTheWayList = transportOrderMapper.selectTrackInfo(wayBillNum);
		return onTheWayList;
	}

	/* (non-Javadoc)
	 * @see com.ycgwl.kylin.transport.service.api.ITransportSignRecordService#selectReorder(java.lang.String, com.ycgwl.kylin.transport.dto.ReorderDto)
	 */
	@Override
	public JsonResult selectReorder(String account, ReorderDto reorderDto) {
		// 0. 是否有权限
		// 1.判断是否有返单录入权限
		Integer rightNum = transportRightMapper.getRightNum(AuthorticationConstant.FDQK_IN, account);
		if (rightNum == null || rightNum < 1) {
			return JsonResult.getConveyResult("400", "你没有权限使用这个功能，请与系统管理员联系！");
		}
		// 1.查询是否是【电子返单管理系统】  ： 2  
		// 只有运单返单标记为1的运单才能做发送   该运单为无返单，不需要进行返单发送！"
		String podType =  transportOrderService.selectIsfdByYdbhid(reorderDto.getWaybillNum());
		if ("2".equals(podType)) {
			return JsonResult.getConveyResult("400", "该票要求电子返单，请到【电子返单管理系统】中进行处理！");
		}
		if (!"1".equals(podType)) {
			return JsonResult.getConveyResult("400", "该运单为无返单，不需要进行返单发送！");
		}

		// 2.查询作废状态
		int invalidatedState = transportOrderMapper.findCancelState(reorderDto.getWaybillNum());
		if (invalidatedState == 1) {
			return JsonResult.getConveyResult("400", reorderDto.getWaybillNum() + "该票货已作废，不能设置等待发货！");
		}

//		只有存在提货单或者送货单的运单且是普通返单的运单才能进行返单发送
		// 3.查询签收     
		// 查询提货签收单表此运单的数量
//		ConsignorDeliveryInstructDto cdiDto = new ConsignorDeliveryInstructDto();
//		cdiDto.setWaybillNum(reorderDto.getWaybillNum());
//		int deliveryCount = transportOrderMapper.findDeliveryCount(cdiDto);
//		// 查询送货签收单表此运单的数量
//		int count1 = transportOrderMapper.findSendCount(cdiDto);
//		if (!(deliveryCount > 0 || count1 > 0)) {
//			return JsonResult.getConveyResult("400", "只有存在提货单或者送货单的运单才能进行返单发送");
//		}
		
		// 4.返单查询  
		List<ReorderVo> recordVoList = this.selectReorder(reorderDto.getWaybillNum(), rightNum);
		int size = recordVoList.size();
		if (size > 0) {
			ReorderVo reorderVo = recordVoList.get(0);
			if (Integer.parseInt(reorderVo.getFdJinZhiLuRu()) == 1) {
				return JsonResult.getConveyResult("400", "返单已处理");
			}
			if (null == reorderVo.getFdfsr() || "".equals(reorderVo.getFdfsr())) {
				reorderVo.setFdfsr(reorderDto.getFdfsr());
			}
			return JsonResult.getConveyResult("200", "查询成功", recordVoList);
		} else {
			return JsonResult.getConveyResult("400", "未查询到数据");
		}

	}

	@Override
	public List<ReorderVo> selectReorder(String waybillNum, Integer rightNum) {
		return transportSignRecordMapper.selectReorder(waybillNum, rightNum);
	}

	@Override
	public JsonResult saveReorder(String account, ReorderDto reorderDto) {
		TransportSignRecord transportSignRecord = transportSignRecordMapper.selectTransportSignRecordByYdbhid(reorderDto.getWaybillNum());

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		// 签收时间
		Date bt;
		Date et;
		try {
			bt = transportSignRecord.getQsTime();
			// 返单录入时间
			et=sdf.parse(reorderDto.getFdjctime()); 
			if (!bt.before(et)){ 
				return JsonResult.getConveyResult("400", "返单寄出时间要大于运单签收时间");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} 

		// 添加返单录入日志
//		transportSignRecord.setAccount(account);
//		int count = transportSignRecordMapper.saveReorderLog(transportSignRecord);
//		if (count < 1) {
//			return JsonResult.getConveyResult("400", "添加返单录入日志失败");
//		}
		reorderDto.setAccount(account);
		int count1 = transportSignRecordMapper.saveReorder(reorderDto);
		if (count1 < 1) {
			return JsonResult.getConveyResult("400", "添加返单录入失败");
		} else {
			return JsonResult.getConveyResult("200", "添加返单录入成功");
		}
	}

	@Override
	public JsonResult selectReorderConfigure(String account) {
		// 0. 是否有权限
		// 1.判断是否返单参数管理权限
		Integer rightNum = transportRightMapper.getRightNum(AuthorticationConstant.FD_YJ_CANSHU,account);
		if (rightNum == null || rightNum < 1) {
			return JsonResult.getConveyResult("400", "你没有权限使用这个功能，请与系统管理员联系！");
		}

		ReorderConfigure reorderConfigure = transportSignRecordMapper.selectReorderConfigure();
		if (null == reorderConfigure) {
			return JsonResult.getConveyResult("400", "查询失败");
		} else {
			return JsonResult.getConveyResult("200", "查询成功", reorderConfigure);
		}
	}

	@Override
	public JsonResult updateReorderConfigure(ReorderConfigure reorderConfigure) {
		int count = transportSignRecordMapper.updateReorderConfigure(reorderConfigure);
		if (1 == count) {
			return JsonResult.getConveyResult("200", "操作成功");
		} else {
			return JsonResult.getConveyResult("400", "操作失败");
		}
	}

	@Override
	public JsonResult selectReceiveReorder(String account, String company, ReorderDto reorderDto) {
		// 1.判断是否有返单录入权限
		Integer rightNum = transportRightMapper.getRightNum(AuthorticationConstant.FDQK_IN,account);
		if (rightNum == null || rightNum < 1) {
			return JsonResult.getConveyResult("400", "你没有权限使用这个功能，请与系统管理员联系！");
		}

		// 2.查询返单发送人是否为空
		TransportSignRecord transportSignRecord = transportSignRecordMapper.selectTransportSignRecordByYdbhid(reorderDto.getWaybillNum());
		if (null == transportSignRecord) {
			return JsonResult.getConveyResult("400", "没有查到此运单");
		}
		if (null == transportSignRecord.getFdfsr() || "".equals(transportSignRecord.getFdfsr())) {
			return JsonResult.getConveyResult("400", "普通返单未发送!");
		}
		if (null == transportSignRecord.getLrrGrid() || "".equals(transportSignRecord.getLrrGrid())) {
			return JsonResult.getConveyResult("400", "该票货还没有签收!");
		}

		// 3.查询作废状态
		int invalidatedState = transportOrderMapper.findCancelState(reorderDto.getWaybillNum());
		if (invalidatedState == 1) {
			return JsonResult.getConveyResult("400", reorderDto.getWaybillNum() + "该票货已作废，不能设置等待发货！");
		}

		// 4. 查询
		ReorderVo reorderVo = transportSignRecordMapper.selectReceiveReorder(reorderDto.getWaybillNum(), company);
		if (null == reorderVo) {
			return JsonResult.getConveyResult("400", "返单已处理或者未查询到数据");
		} else {
			if (null == reorderVo.getQrr() || "".equals(reorderVo.getQrr())) {
				reorderVo.setQrr(reorderDto.getQrr());
			}
			return JsonResult.getConveyResult("200", "操作成功", reorderVo);
		}
	}

	@Transactional
	@Override
	public JsonResult saveReceiveReorder(ReorderDto reorderDto) {
		
		TransportSignRecord transportSignRecord = transportSignRecordMapper.selectTransportSignRecordByYdbhid(reorderDto.getWaybillNum());

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		// 返单寄出时间
		Date bt;
		Date et;
		try {
			bt = transportSignRecord.getFdjcTime();
			// 返单收到时间
			et=sdf.parse(reorderDto.getFdqstime()); 
			if (!bt.before(et)){ 
				return JsonResult.getConveyResult("400", "返单收到时间要大于返单寄出时间");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		
		// 获取返单状态
		String fdzt = reorderDto.getFdzt();
		// 返单不合格  状态为2
		if ("2".equals(fdzt)) {
			if (null == reorderDto.getFdjscomm() || "".equals(reorderDto.getFdjscomm())) {
				return JsonResult.getConveyResult("400", "请输入返单不合格原因！");
			}
			// 如果返单不合格，允许到站方重新修改返单发送信息
			reorderDto.setFdJinZhiLuRu(0);
		} else {
			reorderDto.setFdJinZhiLuRu(1);
		}

		int count = transportSignRecordMapper.saveReorder(reorderDto);

		// 添加货运记录
		int count1= adjunctSomethingMapper.saveOrdertrace(reorderDto);
		if (count1 != 1) {
			return JsonResult.getConveyResult("400", "签收状态更新错误");
		}

		if (count < 1) {
			return JsonResult.getConveyResult("400", "添加返单确认录入失败");
		} else {
			return JsonResult.getConveyResult("200", "添加返单确认录入成功");
		}
	}

}
