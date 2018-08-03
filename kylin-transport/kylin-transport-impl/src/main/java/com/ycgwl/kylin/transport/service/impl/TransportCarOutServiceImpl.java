package com.ycgwl.kylin.transport.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.ycgwl.kylin.AuthorticationConstant;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.entity.*;
import com.ycgwl.kylin.transport.persistent.TransportCarOutMapper;
import com.ycgwl.kylin.transport.persistent.TransportRightMapper;
import com.ycgwl.kylin.transport.service.api.IExceptionLogService;
import com.ycgwl.kylin.transport.service.api.IFinancialReceiptsService;
import com.ycgwl.kylin.transport.service.api.ITransportCarOutService;
import com.ycgwl.kylin.util.Assert;
import com.ycgwl.kylin.util.IPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("kylin.transport.dubbo.local.transportCarOutService")
public class TransportCarOutServiceImpl implements ITransportCarOutService{

	private final static String PCQSD_TEMP = "本部";
	
	private final static String OPERATING_MENU = "送货派车修改";
	
	@Autowired
	TransportCarOutMapper transportCarOutMapper;
	
	@Autowired
	TransportRightMapper transportRightMapper;
	
	@Autowired
	IFinancialReceiptsService financialReceiptsService;
	
	@Autowired
	private IExceptionLogService exceptionLogService; 
	
	@Override
	public TransportCarOut queryTransportCarOutByIdAndGs(String gsid, Integer id)
			throws ParameterException, BusinessException {
		if(id == null){
			throw new ParameterException("id", id, "派车单号不能为空");
		}
		if(StringUtils.isEmpty(gsid)){
			throw new ParameterException("gsid", gsid, "公司编号不能为空");
		}
		TransportCarOut result = new TransportCarOut();
		try {
			result = transportCarOutMapper.selectTransportCarOutByIdAndGs(gsid, id);
		} catch (Exception e) {
			throw new BusinessException("查询派车信息失败", e);
		}
		return result;
	}

	@Override
	public List<TransportCarOutGoodsDetail> queryTransportCarOutGoodsDetailByIdAndGs(String gsid, Integer id)
			throws ParameterException, BusinessException {
		if(id == null){
			throw new ParameterException("id", id, "派车单号不能为空");
		}
		if(StringUtils.isEmpty(gsid)){
			throw new ParameterException("gsid", gsid, "公司编号不能为空");
		}
		List<TransportCarOutGoodsDetail> result = new ArrayList<TransportCarOutGoodsDetail>();
		try {
			result = transportCarOutMapper.selectTransportCarOutGoodsDetailByIdAndGs(gsid, id);
		} catch (Exception e) {
			throw new BusinessException("查询派车签收货物信息失败", e);
		}
		return result;
	}

	@Override
	public List<TransportCarOutVehicleDetail> queryTransportCarOutVehicleDetaillByIdAndGs(String gsid, Integer id)
			throws ParameterException, BusinessException {
		if(id == null){
			throw new ParameterException("id", id, "派车单号不能为空");
		}
		if(StringUtils.isEmpty(gsid)){
			throw new ParameterException("gsid", gsid, "公司编号不能为空");
		}
		List<TransportCarOutVehicleDetail> result = new ArrayList<TransportCarOutVehicleDetail>();
		try {
			result = transportCarOutMapper.selectTransportCarOutVehicleDetaillByIdAndGs(gsid, id);
		} catch (Exception e) {
			throw new BusinessException("查询派车签收车辆信息失败", e);
		}
		return result;
	}

	@Override
	public DeliverySignResult checkDelivery(DeliveryQueryEntity deliveryQueryEntity)
			throws ParameterException, BusinessException {
		
		// 查询用户权限信息
		Integer limit = 0;
		try {
			limit = transportRightMapper.getRightNum(AuthorticationConstant.PC_DDPC, deliveryQueryEntity.getAccount());
		} catch (Exception e) {	
			throw new BusinessException("获取用户调度派车权限失败", e);
		}
		
		if(limit == null || limit == 0){
			throw new BusinessException("您没有权限使用这个功能，请与系统管理员联系！");
		}
		
		//返回结果
		DeliverySignResult deliverySignResult = new DeliverySignResult();
		
		//查询派车信息
		TransportCarOut transportCarOut = this.queryTransportCarOutByIdAndGs(deliveryQueryEntity.getCompanyCode(), deliveryQueryEntity.getPcdid());
		if(transportCarOut.getPcdd() == null){
			transportCarOut.setPcdd(deliveryQueryEntity.getUserName());
		}
		//查询派车签收货物信息
		List<TransportCarOutGoodsDetail> transportCarOutGoodsDetailList = this.queryTransportCarOutGoodsDetailByIdAndGs(deliveryQueryEntity.getCompanyCode(),
				deliveryQueryEntity.getPcdid());
	
		//判断是否生成过派车签收单
		if(transportCarOut != null && transportCarOut.getPcyes() == 1){//生成过派车签收单
			//查询派车签收车辆信息
			List<TransportCarOutVehicleDetail> transportCarOutVehicleDetailList = this.queryTransportCarOutVehicleDetaillByIdAndGs(deliveryQueryEntity.getCompanyCode(),
					deliveryQueryEntity.getPcdid());
			if(transportCarOutVehicleDetailList.size() != 0 && !transportCarOutVehicleDetailList.isEmpty()){
				deliverySignResult.setTransportCarOutVehicleDetailList(transportCarOutVehicleDetailList);
			}
			deliverySignResult.setIsDelivery(1);
		}else{
			deliverySignResult.setIsDelivery(0);
		}
		
		//判断是否生成过派车核算
		if(transportCarOut.getTjhsrGrid() != null && !transportCarOut.getTjhsrGrid().equals(deliveryQueryEntity.getUserName())){//已生成，查看
			deliverySignResult.setIsAccounting(1);
		}else{
			deliverySignResult.setIsAccounting(0);
		}
		
		if(transportCarOutGoodsDetailList.size() != 0 && !transportCarOutGoodsDetailList.isEmpty()){
			deliverySignResult.setTransportCarOutGoodsDetailList(transportCarOutGoodsDetailList);
		}
		deliverySignResult.setTransportCarOut(transportCarOut);
		
		return deliverySignResult;
	}

	@Override
	@Transactional
	public DeliverySignResult deliverySeconded(DeliverySignResult deliverySignResult, UserModel userModel)
			throws ParameterException, BusinessException {
		TransportCarOut transportCarOut = deliverySignResult.getTransportCarOut();
		List<TransportCarOutVehicleDetail> transportCarOutVehicleDetailList = deliverySignResult.getTransportCarOutVehicleDetailList();
		
		//判断必填项不能为空
		Assert.notNull("transportCarOut.getPcqsd()", transportCarOut.getPcqsd(), "请选择起始地！");
		Assert.notNull("transportCarOut.getPcshd()", transportCarOut.getPcshd(), "请选择送货地！");
		if(transportCarOut.getMin() == null){
			throw new ParameterException("请选择送货里程！");
		}
		if(transportCarOut.getMax() == null){
			throw new ParameterException("请选择送货里程！");
		}
		if(transportCarOut.getPcshlc() == null){
			throw new ParameterException("请选择送货里程！");
		}
		Assert.notNull("transportCarOut.getKhObject()", transportCarOut.getKhObject(), "请选择考核对象！");
		
		if(TransportCarOutServiceImpl.PCQSD_TEMP.equals(transportCarOut.getPcqsd())){
			if(checkqsd(userModel.getCompany(),transportCarOut.getId()) == 0){
				throw new BusinessException("该运单已通过装载清单从“本部”中转出去,起始地不能再选择“本部”，请按货物所在地作为送货“起始地”！");
			}
			if(checkshd(transportCarOut.getPcshd(),userModel.getCompany()) == 0){
				throw new BusinessException("送货地: " + transportCarOut.getPcshd() + " 不在" + userModel.getCompany() + "公司分理标准价格表中，不能保存，请核实!");
			}
		}
		
		//判断派车签收单车辆明细是否为空
		Assert.notEmpty("transportCarOutVehicleDetailList", transportCarOutVehicleDetailList, "车辆情况不能为空");
		
		//进行保存，TMS ue_update方法
		transportCarOut.setPcdd(userModel.getUserName());
		transportCarOut.setPcddGrid(userModel.getAccount());
		transportCarOut.setPcpcTime(new Date());
		transportCarOut.setPcyes(1);
		
		transportCarOutVehicleDetailList.forEach((final TransportCarOutVehicleDetail l) -> {
			l.setId(transportCarOut.getId());
			l.setGsid(userModel.getCompanyCode());
		});
		
		/**更新分理收据表jkr为第一个司机（TMS逻辑，需求不明）
		FinancialReceiptsMaster financialReceiptsMaster = new FinancialReceiptsMaster();
		financialReceiptsMaster.setGs(userModel.getCompany());
		financialReceiptsMaster.setId(transportCarOut.getShjhm());
		financialReceiptsMaster.setJkr(transportCarOutVehicleDetailList.get(0).getSj());
		financialReceiptsService.modifyFinancialReceiptsMasterByIdAndGs(financialReceiptsMaster);*/
		
		//更新派车签收信息表
		this.modifyTransportCarOutByIdAndGs(transportCarOut);
		
		//先删除派车签收车辆信息
		this.removeTransportCarOutVehicleDetailById(userModel.getCompanyCode(), transportCarOut.getId());
		
		//新增车辆明细信息
		this.batchAddTransportCarOutVehicleDetail(transportCarOutVehicleDetailList);
		
		/**
		 * 新增异常日志信息记录
		 * yanxf
		 * 2018-03-13 16:09:07
		 */
		if(deliverySignResult.getIsDelivery() == 1){//已做过派车签收单，修改操作，添加异常日志信息
			ExceptionLog exceptionLog = new ExceptionLog();
			exceptionLog.setOperatorName(userModel.getUserName());
			exceptionLog.setOperatorAccount(userModel.getAccount());
			exceptionLog.setOperatorCompany(userModel.getCompany());
			exceptionLog.setIpAddress(IPUtil.getLocalIP());
			exceptionLog.setYdbhid(transportCarOut.getId().toString());
			exceptionLog.setOperatingMenu(OPERATING_MENU);
			exceptionLog.setOperatingContent("派车单号为：" + transportCarOut.getId().toString() + "的取货派车记录修改成功！");
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
		DeliverySignResult result = new DeliverySignResult();
		result.setTransportCarOut(this.queryTransportCarOutByIdAndGs(userModel.getCompanyCode(), transportCarOut.getId()));
		result.setTransportCarOutGoodsDetailList(this.queryTransportCarOutGoodsDetailByIdAndGs(userModel.getCompanyCode(),
				 transportCarOut.getId()));
		result.setTransportCarOutVehicleDetailList(this.queryTransportCarOutVehicleDetaillByIdAndGs(userModel.getCompanyCode(),
				transportCarOut.getId()));
		return result;
	}

	@Override
	public void batchAddTransportCarOutVehicleDetail(List<TransportCarOutVehicleDetail> transportCarOutVehicleDetails)
			throws ParameterException, BusinessException {
		if(transportCarOutVehicleDetails.isEmpty() && transportCarOutVehicleDetails.size() == 0){
			throw new ParameterException("transportCarOutVehicleDetails", transportCarOutVehicleDetails, "派车单车辆信息不能为空");
		}
		try {
			transportCarOutMapper.batchInsertTransportCarOutVehicleDetail(transportCarOutVehicleDetails);
		} catch (Exception e) {
			throw new BusinessException("新增派车单车辆信息失败", e);
		}
		
	}

	@Override
	public void modifyTransportCarOutByIdAndGs(TransportCarOut transportCarOut)
			throws ParameterException, BusinessException {
		if(transportCarOut == null){
			throw new ParameterException("transportCarOut", transportCarOut, "派车单信息不能为空");
		}
		try {
			transportCarOutMapper.updateTransportCarOutByIdAndGs(transportCarOut);
		} catch (Exception e) {
			throw new BusinessException("更新派车单信息失败", e);
		}
	}

	@Override
	public void modifyTransportCarOutByDetailId(TransportCarOutVehicleDetail transportCarOutVehicleDetail)
			throws ParameterException, BusinessException {
		if(transportCarOutVehicleDetail == null){
			throw new ParameterException("transportCarOutVehicleDetail", transportCarOutVehicleDetail, "派车单车辆信息不能为空");
		}
		try {
			transportCarOutMapper.updateTransportCarOutByDetailId(transportCarOutVehicleDetail);
		} catch (Exception e) {
			throw new BusinessException("更新派车单车辆信息失败", e);
		}
	}

	@Override
	public Integer checkqsd(String gs, Integer id) {
		
		Integer result = 0;
		//查询运单编号
		String ydbhid = "";
		try {
			ydbhid = transportCarOutMapper.selectTransportCarOutYdbhidByIdAndGsid(gs, id);
		} catch (Exception e) {
			throw new BusinessException("查询运单编号失败", e);
		}
		
		//判断是否存在分理费计算装载清单
		String dzshhd = "";
		try {
			dzshhd = transportCarOutMapper.selectDzshhdByYdbhid(ydbhid, gs);
		} catch (Exception e) {
			throw new BusinessException("查询装载信息失败", e);
		}
		
		if(StringUtils.isNotEmpty(dzshhd)){
			result = 1;
		}
		return result;
	}

	@Override
	public Integer checkshd(String mdd, String gs) {
		Integer result = 0;
		Long shlc = 0l;
		try {
			shlc = transportCarOutMapper.selectShlcByYdGs(mdd, gs);
		} catch (Exception e) {
			throw new BusinessException("查询公司分理标准价格信息失败", e);
		}
		if(shlc > 0l){
			result = 1;
		}
		return result;
	}

	@Override
	public void removeTransportCarOutVehicleDetailById(String gsid, Integer id) throws ParameterException, BusinessException{
		if(id == null){
			throw new ParameterException("id", id, "派车单号不能为空");
		}
		if(StringUtils.isEmpty(gsid)){
			throw new ParameterException("gsid", gsid, "公司编号不能为空");
		}
		try {
			transportCarOutMapper.deleteTransportCarOutVehicleDetailById(gsid, id);
		} catch (Exception e) {
			throw new BusinessException("删除派车单车辆信息失败", e);
		}
	}

	@Override
	public void batchAddTransportCarOutDetailFourth(List<TransportCarOutDetailFourth> transportCarOutDetailFourths)
			throws ParameterException, BusinessException {
		if(transportCarOutDetailFourths.isEmpty() && transportCarOutDetailFourths.size() == 0){
			throw new ParameterException("transportCarOutDetailFourths", transportCarOutDetailFourths, "派车单明细信息不能为空");
		}
		try {
			transportCarOutMapper.batchInsertTransportCarOutDetailFourth(transportCarOutDetailFourths);
		} catch (Exception e) {
			throw new BusinessException("新增派车单明细信息失败", e);
		}	
	}

	@Override
	public void batchAddTransportCarOutDetailFifth(List<TransportCarOutDetailFifth> transportCarOutDetailFifths)
			throws ParameterException, BusinessException {
		if(transportCarOutDetailFifths.isEmpty() && transportCarOutDetailFifths.size() == 0){
			throw new ParameterException("transportCarOutDetailFifths", transportCarOutDetailFifths, "派车单明细信息不能为空");
		}
		try {
			transportCarOutMapper.batchInsertTransportCarOutDetailFifth(transportCarOutDetailFifths);
		} catch (Exception e) {
			throw new BusinessException("新增派车单明细信息失败", e);
		}	
	}

	@Override
	public void removeTransportCarOutDetailFourthById(String gsid, Integer id)
			throws ParameterException, BusinessException {
		if(id == null){
			throw new ParameterException("id", id, "派车单号不能为空");
		}
		if(StringUtils.isEmpty(gsid)){
			throw new ParameterException("gsid", gsid, "公司编号不能为空");
		}
		try {
			transportCarOutMapper.deleteTransportCarOutDetailFourthById(gsid, id);
		} catch (Exception e) {
			throw new BusinessException("删除派车单明细信息失败", e);
		}	
	}

	@Override
	public void removeTransportCarOutDetailFifthById(String gsid, Integer id)
			throws ParameterException, BusinessException {
		if(id == null){
			throw new ParameterException("id", id, "派车单号不能为空");
		}
		if(StringUtils.isEmpty(gsid)){
			throw new ParameterException("gsid", gsid, "公司编号不能为空");
		}
		try {
			transportCarOutMapper.deleteTransportCarOutDetailFifthById(gsid, id);
		} catch (Exception e) {
			throw new BusinessException("删除派车单明细信息失败", e);
		}	
	}

	@Override
	public List<TransportCarOutDetailSecond> queryTtransportCarOutDetailSecondByIdAndGs(String gsid, Integer id)
			throws ParameterException, BusinessException {
		if(id == null){
			throw new ParameterException("id", id, "派车单号不能为空");
		}
		if(StringUtils.isEmpty(gsid)){
			throw new ParameterException("gsid", gsid, "公司编号不能为空");
		}
		List<TransportCarOutDetailSecond> result = new ArrayList<TransportCarOutDetailSecond>();
		try {
			result = transportCarOutMapper.selectTransportCarOutDetailSecondByIdAndGs(gsid, id);
		} catch (Exception e) {
			throw new BusinessException("查询派车签收明细信息失败", e);
		}
		return result;
	}

	@Override
	public List<TransportCarOutDetailFourth> queryTransportCarOutDetailFourthByIdAndGs(String gsid, Integer id)
			throws ParameterException, BusinessException {
		if(id == null){
			throw new ParameterException("id", id, "派车单号不能为空");
		}
		if(StringUtils.isEmpty(gsid)){
			throw new ParameterException("gsid", gsid, "公司编号不能为空");
		}
		List<TransportCarOutDetailFourth> result = new ArrayList<TransportCarOutDetailFourth>();
		try {
			result = transportCarOutMapper.selectTransportCarOutDetailFourthByIdAndGs(gsid, id);
		} catch (Exception e) {
			throw new BusinessException("查询派车签收明细信息失败", e);
		}
		return result;
	}

	@Override
	public List<TransportCarOutDetailFifth> queryTransportCarOutDetailFifthByIdAndGs(String gsid, Integer id)
			throws ParameterException, BusinessException {
		if(id == null){
			throw new ParameterException("id", id, "派车单号不能为空");
		}
		if(StringUtils.isEmpty(gsid)){
			throw new ParameterException("gsid", gsid, "公司编号不能为空");
		}
		List<TransportCarOutDetailFifth> result = new ArrayList<TransportCarOutDetailFifth>();
		try {
			result = transportCarOutMapper.selectTransportCarOutDetailFifthByIdAndGs(gsid, id);
		} catch (Exception e) {
			throw new BusinessException("查询派车签收明细信息失败", e);
		}
		return result;
	}

	@Override
	public DeliveryAccountingResult deliveryAccountingSearch(DeliveryQueryEntity deliveryQueryEntity)
			throws ParameterException, BusinessException {
		
		//查询用户签收派车核算权限
		Integer limit = 0;
		try {
			limit = transportRightMapper.getRightNum(AuthorticationConstant.TJYHS, deliveryQueryEntity.getAccount());
		} catch (Exception e) {	
			throw new BusinessException("获取用户派车签收权限失败", e);
		}
		if(limit == null || limit == 0){
			throw new BusinessException("您没有权限使用这个功能，请与系统管理员联系！");
		}
		
		DeliveryAccountingResult deliveryAccountingResult = new DeliveryAccountingResult();
		
		//查询派车签收信息
		TransportCarOut transportCarOut = this.queryTransportCarOutByIdAndGs(deliveryQueryEntity.getCompanyCode(), deliveryQueryEntity.getPcdid());
		if(transportCarOut == null){
			throw new BusinessException("没有这个派车单号！");
		}
		if(transportCarOut.getPcyes() == 0){
			throw new BusinessException("该派车单还未派车！");
		}
		
		//查询签收派车单明细二
		List<TransportCarOutDetailSecond> transportCarOutDetailSecondList = this.queryTtransportCarOutDetailSecondByIdAndGs(deliveryQueryEntity.getCompanyCode(), 
				deliveryQueryEntity.getPcdid());
		
		//判断是否生成过派车核算
		if(transportCarOut.getTjhsrGrid() != null && !transportCarOut.getTjhsrGrid().equals(deliveryQueryEntity.getUserName())){//已生成，查看
			//查询签收派车明细三、明细四
			List<TransportCarOutDetailFourth> transportCarOutDetailFourthList = this.queryTransportCarOutDetailFourthByIdAndGs(deliveryQueryEntity.getCompanyCode(), 
					deliveryQueryEntity.getPcdid());
			List<TransportCarOutDetailFifth> transportCarOutDetailFifthlList = this.queryTransportCarOutDetailFifthByIdAndGs(deliveryQueryEntity.getCompanyCode(), 
					deliveryQueryEntity.getPcdid());
			deliveryAccountingResult.setTransportCarOutDetailFourthList(transportCarOutDetailFourthList);
			deliveryAccountingResult.setTransportCarOutDetailFifthlList(transportCarOutDetailFifthlList);
		}
		
		//添加签收派车信息
		deliveryAccountingResult.setTransportCarOut(transportCarOut);
		deliveryAccountingResult.setTransportCarOutDetailSecondList(transportCarOutDetailSecondList);
		
		return deliveryAccountingResult;
	}

	@Override
	@Transactional
	public DeliveryAccountingResult deliveryAccountingSave(DeliveryAccountingResult deliveryAccountingResult,UserModel userModel)
			throws ParameterException, BusinessException {
		TransportCarOut transportCarOut = deliveryAccountingResult.getTransportCarOut();
		List<TransportCarOutDetailFourth> transportCarOutDetailFourthList = deliveryAccountingResult.getTransportCarOutDetailFourthList();
		List<TransportCarOutDetailFifth> transportCarOutDetailFifthlList = deliveryAccountingResult.getTransportCarOutDetailFifthlList();
		
		if(transportCarOutDetailFourthList.isEmpty() && transportCarOutDetailFourthList.size() == 0){
			throw new BusinessException("请输入司机明细情况！");
		}
		if(transportCarOutDetailFifthlList.isEmpty() && transportCarOutDetailFifthlList.size() == 0){
			throw new BusinessException("请输入统计员结算明细情况！");
		}
		
		//给派车单添加默认值
		transportCarOut.setGs(userModel.getCompany());
		transportCarOut.setGsid(userModel.getCompanyCode());
		transportCarOut.setTjhsr(userModel.getUserName());
		transportCarOut.setTjhsrGrid(userModel.getAccount());
		transportCarOut.setTjTime(new Date());
	
		transportCarOutDetailFourthList.forEach((final TransportCarOutDetailFourth l) -> {
			l.setId(transportCarOut.getId());
			l.setGsid(transportCarOut.getGsid());		
		});
		
		transportCarOutDetailFifthlList.forEach((final TransportCarOutDetailFifth l) -> {
			l.setId(transportCarOut.getId());
			l.setGsid(transportCarOut.getGsid());
		});
		
		//更新派车单信息
		this.modifyTransportCarOutByIdAndGs(transportCarOut);
		
		//删除派车单明细四、明细五
		this.removeTransportCarOutDetailFourthById(userModel.getCompanyCode(), transportCarOut.getId());
		this.removeTransportCarOutDetailFifthById(userModel.getCompanyCode(), transportCarOut.getId());
		
		//新增派车单明细四、明细五
		this.batchAddTransportCarOutDetailFourth(transportCarOutDetailFourthList);
		this.batchAddTransportCarOutDetailFifth(transportCarOutDetailFifthlList);
		return deliveryAccountingResult;
	}


	
}
