package com.ycgwl.kylin.transport.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.ycgwl.kylin.AuthorticationConstant;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.entity.*;
import com.ycgwl.kylin.transport.entity.adjunct.Employee;
import com.ycgwl.kylin.transport.persistent.*;
import com.ycgwl.kylin.transport.service.api.AdjunctSomethingService;
import com.ycgwl.kylin.transport.service.api.BundleReceiptService;
import com.ycgwl.kylin.transport.service.api.IFinancialReceiptsService;
import com.ycgwl.kylin.transport.service.api.ITransportSignRecordService;
import com.ycgwl.kylin.util.Assert;
import com.ycgwl.kylin.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service("kylin.transport.dubbo.local.financialReceiptsService")
public class FinancialReceiptsServiceImpl implements IFinancialReceiptsService{
	
	private final static String INPUT_ID = "麒麟";
	
	@Autowired
	FinancialReceiptsMapper financialReceiptsMapper;
	
	@Autowired
	TransportRightMapper transportRightMapper;
	
	@Autowired
	FinanceCertifyMapper financeCertifyMapper;
	
	@Autowired
	TransportOrderMapper transportOrderMapper;
	
	@Autowired
	ITransportSignRecordService transportSignRecordService;
	
	@Autowired
	DeliveryOperateMapper deliveryOperateMapper;
	
	@Autowired
	AdjunctSomethingService adjunctSomethingService;
	
	@Autowired
	BundleReceiptService bundleReceiptService;

	@Override
	public FinancialReceiptsMaster queryFinancialReceiptsMasterByYdbhidAndGs(Integer id, String gs, String ydbhid) throws ParameterException, BusinessException{
		
		if(id == null){
			throw new ParameterException("id", id, "收据号不能为空");
		}
		if(StringUtils.isEmpty(gs)){
			throw new ParameterException("gs", gs, "公司不能为空");
		}
		if(StringUtils.isEmpty(ydbhid)){
			throw new ParameterException("ydbhid", ydbhid, "运单编号不能为空");
		}
		FinancialReceiptsMaster result = new FinancialReceiptsMaster();
		try {
			result = financialReceiptsMapper.selectFinancialReceiptsMasterByYdbhidAndGs(id, gs, ydbhid);
		} catch (Exception e) {
			throw new BusinessException("查询分理收据主表信息失败", e);
		}
		return result;
	}

	@Override
	public List<FinancialReceiptsDetail> queryFinancialReceiptsDetailByYdbhidAndGs(Integer sjid, String gs, String ydbhid)
			throws ParameterException, BusinessException {
		if(sjid == null){
			throw new ParameterException("sjid", sjid, "收据号不能为空");
		}
		if(StringUtils.isEmpty(gs)){
			throw new ParameterException("gs", gs, "公司不能为空");
		}
		if(StringUtils.isEmpty(ydbhid)){
			throw new ParameterException("ydbhid", ydbhid, "运单编号不能为空");
		}
		List<FinancialReceiptsDetail> result = new ArrayList<FinancialReceiptsDetail>();
		try {
			result = financialReceiptsMapper.selectFinancialReceiptsDetailByYdbhidAndGs(sjid, gs, ydbhid);
		} catch (Exception e) {
			throw new BusinessException("查询分理收据明细信息失败", e);
		}
		return result;
	}

	@Override
	public FinancialReceiptsMaster queryFinancialReceiptsMasterByYdbhid(String ydbhid)
			throws ParameterException, BusinessException {
		if(StringUtils.isEmpty(ydbhid)){
			throw new ParameterException("ydbhid", ydbhid, "运单编号不能为空");
		}
		FinancialReceiptsMaster result = new FinancialReceiptsMaster();
		try {
			result = financialReceiptsMapper.selectFinancialReceiptsMasterByYdbhid(ydbhid);
		} catch (Exception e) {
			throw new BusinessException("查询分理收据主表信息失败", e);
		}
		return result;
	}

	@Override
	public FinancialReceiptsResult financialReceiptsInformation(
			FinancialReceiptsQueryEntity financialReceiptsQueryEntity) throws ParameterException, BusinessException {
		FinancialReceiptsResult result = new FinancialReceiptsResult();
		
		//先判断该用户是否有权限生成分理收据
		Integer limit = 0;
		try {
			limit = transportRightMapper.getRightNum(AuthorticationConstant.CWSJ_FENLI, financialReceiptsQueryEntity.getAccount());
		} catch (Exception e) {	
			throw new BusinessException("获取用户生成分理收据权限失败", e);
		}
		
		if(limit == null || limit == 0){
			throw new BusinessException("您没有权限使用这个功能，请与系统管理员联系！");
		}
		
		//查询运单信息
		TransportOrder transportOrder = new TransportOrder();
		try {
			transportOrder = transportOrderMapper.getTransportOrderByYdbhid(financialReceiptsQueryEntity.getYdbhid());
		} catch (Exception e) {
			throw new BusinessException("查询运单信息失败", e);
		}
		
		if(transportOrder == null){
			throw new BusinessException("运单不存在,请确认");
		}
		
//		if(transportOrder.getDaozhan() != null && !transportOrder.getDaozhan().equals(financialReceiptsQueryEntity.getCompany())){
//			throw new BusinessException("无法进行分理收据操作，请确认！");
//		}
		
		int count = 0;
		if(transportOrder.getDaozhan()!=null && !transportOrder.getDaozhan().equals(financialReceiptsQueryEntity.getCompany())) {
			count+=1;
		}
		if(transportOrder.getFazhan()!=null && !transportOrder.getFazhan().equals(financialReceiptsQueryEntity.getCompany())) {
			count+=1;
		}
		if(count==2) {
			throw new BusinessException("无法操作分理收据，只有发站和到站公司才能操作！");
		}
		
		//判断分理收据有没有生成过
		FinancialReceiptsMaster financialReceiptsMaster = this.queryFinancialReceiptsMasterByYdbhid(financialReceiptsQueryEntity.getYdbhid());
		if(financialReceiptsMaster != null && financialReceiptsMaster.getId() > 0){// 收据号大于0,代表收据号已存在,只是查询相关数据
			if(!financialReceiptsMaster.getGs().equals(financialReceiptsQueryEntity.getCompany())){
				throw new BusinessException("此运单已生成分理收据'" + financialReceiptsMaster.getId() + "',所属公司为'"
				+ financialReceiptsMaster.getGs() + "'！");
			}else{
				
				if(StringUtils.isNotEmpty(financialReceiptsMaster.getZhiPiao())){
					//查询制单员
					Employee zhidan = new Employee();
					try {
						zhidan = adjunctSomethingService.getEmployeeByNumber(financialReceiptsMaster.getZhiPiao());
					} catch (Exception e) {
						throw new BusinessException("查询制单员信息失败", e);
					}
					financialReceiptsMaster.setZhiPiao(zhidan.getEmplyeeName());
				}
				
				if(StringUtils.isNotEmpty(financialReceiptsMaster.getChuNa())){
					//查询出纳员
					Employee chuna = new Employee();
					try {
						chuna = adjunctSomethingService.getEmployeeByNumber(financialReceiptsMaster.getChuNa());
					} catch (Exception e) {
						throw new BusinessException("查询出纳员信息失败", e);
					}
					financialReceiptsMaster.setChuNa(chuna.getEmplyeeName());
				}
				
				//查询明细信息
				List<FinancialReceiptsDetail> financialReceiptsDetailList = this.queryFinancialReceiptsDetailByYdbhidAndGs(financialReceiptsMaster.getId(), financialReceiptsMaster.getGs(), 
						financialReceiptsQueryEntity.getYdbhid());
				result.setFinancialReceiptsMaster(financialReceiptsMaster);
				result.setFinancialReceiptsDetailList(financialReceiptsDetailList);		
				result.setIsGenerate(1);
			}
			
		}else{// 代表收据号不存在,查询相关权限，带出相关数据，页面相关操作后保存
			FinancialReceiptsMaster entity = new FinancialReceiptsMaster();
			//检查是否生成受理单
			FiwtResult fiwtResult = transportSignRecordService.getXianluByYdbhid(financialReceiptsQueryEntity.getYdbhid());
			if(fiwtResult == null || StringUtils.isEmpty(fiwtResult.getXianlu()) || fiwtResult.getCwpzhbh() ==null || fiwtResult.getCwpzhbh() == 0){
				throw new BusinessException("该运单的货物发站方还未生成受理单，不能生成分理收据！");
			}
			
			if(financialReceiptsQueryEntity.getHdfk() == null || financialReceiptsQueryEntity.getDsk() == null){
				throw new BusinessException("该运单在您查询到货之前未生成财凭,请重新查询到货信息");
			}
			
			Map<String,Object> map = new HashMap<>();
			map.put("ydbhid", financialReceiptsQueryEntity.getYdbhid());
			map.put("grid", financialReceiptsQueryEntity.getAccount());
			try {
				deliveryOperateMapper.excuteCheckfwfsandcar(map);
			} catch (Exception e) {
				throw new BusinessException("查询自提改送货检测信息失败", e);
			}
			if(!(financialReceiptsQueryEntity.getHdfk().compareTo(new BigDecimal(0)) > 0 || (financialReceiptsQueryEntity.getDsk().compareTo(new BigDecimal(0)) > 0)
					|| Integer.parseInt(String.valueOf(map.get("flag"))) > 0)){
				throw new BusinessException("该运单不是到站付款的或没有代收款信息,请确认！");
			}
			
			//查询装载信息
			BundleReceipt receipt = new BundleReceipt();
			try {
				receipt = bundleReceiptService.getBundleReceiptByXuhao(financialReceiptsQueryEntity.getXuhao());
			} catch (Exception e) {
				throw new BusinessException("查询运单装载信息失败", e);
			}
			
			if(receipt.getYdzh() != 1){
				throw new BusinessException("该运单还未做到站处理,细则号为'" + financialReceiptsQueryEntity.getYdxzh()
				+ "'！");
			}
			
			//查询财凭信息
			FinanceCertify financeCertify = new FinanceCertify();
			try {
				financeCertify = financeCertifyMapper.getFinanceCertifyByYdbhid(financialReceiptsQueryEntity.getYdbhid());
			} catch (Exception e) {
				throw new BusinessException("查询财凭信息失败", e);
			}
			
			if(financeCertify == null){
				throw new BusinessException("该运单发站财凭无效,请确认！");
			}

			entity.setJkdw(transportOrder.getShhrmch());
			entity.setJksj(new Date());
			entity.setFiwtYdbhid(financialReceiptsQueryEntity.getYdbhid());
			entity.setFiwtCwpzhbh(financeCertify.getCwpzhbh());
			entity.setType(0);
			entity.setVer(1);
			result.setFinancialReceiptsMaster(entity);

			//添加明细信息
			List<FinancialReceiptsDetail> financialReceiptsDetailList = new ArrayList<FinancialReceiptsDetail>();
			if(financialReceiptsQueryEntity.getHdfk() != null && financialReceiptsQueryEntity.getHdfk().compareTo(new BigDecimal(0)) > 0){
				FinancialReceiptsDetail financialReceiptsDetailHdfk = new FinancialReceiptsDetail();
				String str = "0000000" + financeCertify.getCwpzhbh();
				financialReceiptsDetailHdfk.setShfxm("货到付款" + fiwtResult.getXianlu() + str.substring(str.length()-7,str.length()));
				financialReceiptsDetailHdfk.setShfje(financialReceiptsQueryEntity.getHdfk().doubleValue());
				financialReceiptsDetailHdfk.setYshm(transportOrder.getYshhm());
				financialReceiptsDetailHdfk.setBeizhu(transportOrder.getShhrmch());
				financialReceiptsDetailHdfk.setFiwtYdbhid(financialReceiptsQueryEntity.getYdbhid());
				financialReceiptsDetailHdfk.setFiwtCwpzhbh(financeCertify.getCwpzhbh());
				financialReceiptsDetailHdfk.setType(0);
				financialReceiptsDetailHdfk.setKhbm(transportOrder.getKhbm());
				financialReceiptsDetailHdfk.setKhmc(transportOrder.getFhdwmch());
				financialReceiptsDetailList.add(financialReceiptsDetailHdfk);
			}
			if(financialReceiptsQueryEntity.getDsk() != null && financialReceiptsQueryEntity.getDsk().compareTo(new BigDecimal(0)) > 0){
				FinancialReceiptsDetail financialReceiptsDetailDsk = new FinancialReceiptsDetail();
				String str = "0000000" + financeCertify.getCwpzhbh();
				financialReceiptsDetailDsk.setShfxm("代收款" + fiwtResult.getXianlu() + str.substring(str.length()-7,str.length()));
				financialReceiptsDetailDsk.setShfje(financialReceiptsQueryEntity.getDsk().doubleValue());
				financialReceiptsDetailDsk.setYshm(transportOrder.getYshhm());
				financialReceiptsDetailDsk.setBeizhu(transportOrder.getShhrmch());
				financialReceiptsDetailDsk.setFiwtYdbhid(financialReceiptsQueryEntity.getYdbhid());
				financialReceiptsDetailDsk.setFiwtCwpzhbh(financeCertify.getCwpzhbh());
				financialReceiptsDetailDsk.setType(0);
				financialReceiptsDetailDsk.setKhbm(transportOrder.getKhbm());
				financialReceiptsDetailDsk.setKhmc(transportOrder.getFhdwmch());
				financialReceiptsDetailList.add(financialReceiptsDetailDsk);
			}
			result.setFinancialReceiptsDetailList(financialReceiptsDetailList);
			
			result.setIsGenerate(0);
		}
		result.setYdbhid(financialReceiptsQueryEntity.getYdbhid());
		return result;
	}

	@Override
	public Map<String, Object> udtGetCwsjid(Map<String, Object> map) throws ParameterException, BusinessException{
		if(map.get("gs") == null){
			throw new ParameterException("公司不能为空");
		}
		if(map.get("nf") == null){
			throw new ParameterException("年份不能为空");
		}
		try {
			financialReceiptsMapper.udtGetCwsjid(map);
		} catch (Exception e) {
			throw new BusinessException("生成分理收据单号失败", e);
		}
		return map;
	}

	@Override
	public void addFinancialReceiptsMaster(FinancialReceiptsMaster financialReceiptsMaster) {
		if(financialReceiptsMaster == null){
			throw new ParameterException("分理收据信息不能为空");
		}
		try {
			financialReceiptsMapper.insertFinancialReceiptsMaster(financialReceiptsMaster);
		} catch (Exception e) {
			throw new BusinessException("新增分理收据信息失败", e);
		}
	}

	@Override
	public void batchAddFinancialReceiptsDetail(List<FinancialReceiptsDetail> financialReceiptsDetails) {
		if(financialReceiptsDetails.isEmpty() && financialReceiptsDetails.size() == 0){
			throw new ParameterException("分理收据明细信息不能为空");
		}
		try {
			financialReceiptsMapper.batchInsertFinancialReceiptsDetail(financialReceiptsDetails);
		} catch (Exception e) {
			throw new BusinessException("新增分理收据明细信息失败", e);
		}
	}

	@Transactional
	@Override
	public void saveFinancialReceipts(FinancialReceiptsMaster financialReceiptsMaster,List<FinancialReceiptsDetail> financialReceiptsDetailList,
			FinancialReceiptsQueryEntity financialReceiptsQueryEntity) throws ParameterException, BusinessException {
	
		if(financialReceiptsMaster == null){
			throw new ParameterException("分理收据信息不能为空,请确认！");
		}
		
		if(financialReceiptsDetailList.isEmpty() && financialReceiptsDetailList.size() == 0){
			throw new ParameterException("分理收据明细信息不能为空,请确认！");
		}
		
		// 判断交款人不能为空
		if(StringUtils.isEmpty(financialReceiptsMaster.getJkdw())){
			throw new ParameterException("交款人不能为空,请确认！");
		}
		
		//生成收据号
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("gs", financialReceiptsQueryEntity.getCompany());
		map.put("nf", DateUtils.getNowYear());
		map = this.udtGetCwsjid(map);
		Integer sjid = (Integer)map.get("cwsjid");
		financialReceiptsMaster.setId(sjid);
		financialReceiptsMaster.setGs(financialReceiptsQueryEntity.getCompany());
		financialReceiptsMaster.setZhiPiao(financialReceiptsQueryEntity.getAccount());
		financialReceiptsMaster.setSysName(FinancialReceiptsServiceImpl.INPUT_ID);
		
		//持久化分理收据信息
		this.addFinancialReceiptsMaster(financialReceiptsMaster);
		
		//遍历明细信息添加默认值
		for (int i = 0;i < financialReceiptsDetailList.size(); i++) {
			FinancialReceiptsDetail financialReceiptsDetail = financialReceiptsDetailList.get(i);
			financialReceiptsDetail.setSjid(sjid);
			financialReceiptsDetail.setXzh(i + 1);
			financialReceiptsDetail.setGs(financialReceiptsMaster.getGs());
			financialReceiptsDetail.setFiwtYdbhid(financialReceiptsMaster.getFiwtYdbhid());
			financialReceiptsDetail.setFiwtCwpzhbh(financialReceiptsMaster.getFiwtCwpzhbh());
			financialReceiptsDetail.setType(0);
		}
		
		//持久化分理收据明细信息
		this.batchAddFinancialReceiptsDetail(financialReceiptsDetailList);
	}

	@Override
	public FinancialReceiptsResult financialReceiptsPayMoney(FinancialReceiptsQueryEntity financialReceiptsQueryEntity)
			throws ParameterException, BusinessException {
		FinancialReceiptsResult result = new FinancialReceiptsResult();
		
		//先判断该用户是否有权限生成分理收据
		Integer limit = 0;
		try {
			limit = transportRightMapper.getRightNum(AuthorticationConstant.FLSJSQ, financialReceiptsQueryEntity.getAccount());
		} catch (Exception e) {	
			throw new BusinessException("获取用户分理收据收钱权限失败", e);
		}
		if(limit == null || limit == 0){
			throw new BusinessException("您没有权限使用这个功能，请与系统管理员联系！");
		}
		FinancialReceiptsMaster financialReceiptsMaster = new FinancialReceiptsMaster();
		
		// 判断是否生成分理收据
		if(StringUtils.isNotEmpty(financialReceiptsQueryEntity.getYdbhid()) && financialReceiptsQueryEntity.getSjid() == null){	
			financialReceiptsMaster = this.queryFinancialReceiptsMasterByYdbhid(financialReceiptsQueryEntity.getYdbhid());
			if(financialReceiptsMaster == null){// 未生成分理收据
				throw new BusinessException("此运单还未生成分理收据,请确认！");
			}
		}else{
			Integer id = null;
			try {
				id = Integer.parseInt(financialReceiptsQueryEntity.getSjid());
			}catch (Exception e) {
				throw new BusinessException("收据号编码错误！");
			}
			financialReceiptsMaster = this.queryFinancialReceiptsMasterById(id,financialReceiptsQueryEntity.getCompany());
			if(financialReceiptsMaster == null){// 未生成分理收据
				throw new  BusinessException("此收据号'" + financialReceiptsQueryEntity.getSjid() + "'还未生成,请确认！");
			}
		}
		
		if(StringUtils.isNotEmpty(financialReceiptsMaster.getZhiPiao())){
			//查询制单员
			Employee zhidan = new Employee();
			try {
				zhidan = adjunctSomethingService.getEmployeeByNumber(financialReceiptsMaster.getZhiPiao());
			} catch (Exception e) {
				throw new BusinessException("查询制单员信息失败", e);
			}
			financialReceiptsMaster.setZhiPiao(zhidan.getEmplyeeName());
		}
		
		if(StringUtils.isNotEmpty(financialReceiptsMaster.getChuNa())){
			//查询出纳员
			Employee chuna = new Employee();
			try {
				chuna = adjunctSomethingService.getEmployeeByNumber(financialReceiptsMaster.getChuNa());
			} catch (Exception e) {
				throw new BusinessException("查询出纳员信息失败", e);
			}
			financialReceiptsMaster.setChuNa(chuna.getEmplyeeName());
		}
		
		//判断登录人公司
		if(!financialReceiptsMaster.getGs().equals(financialReceiptsQueryEntity.getCompany())){
			throw new BusinessException("当前公司不能收钱，该运单生成的收据为" 
					+ financialReceiptsMaster.getFiwtCwpzhbh() + ",所属公司为" + financialReceiptsMaster.getGs() + "！");
		}
		
		//判断是否收过钱
		if(financialReceiptsMaster.getIsShq() == 1){//收过钱
			result.setIsPayMoney(1);
		}else{
			result.setIsPayMoney(0);
		}
		
		//查询明细信息
		List<FinancialReceiptsDetail> financialReceiptsDetailList = this.queryFinancialReceiptsDetailByYdbhidAndGs(financialReceiptsMaster.getId(), financialReceiptsMaster.getGs(), 
				financialReceiptsMaster.getFiwtYdbhid());
		result.setFinancialReceiptsMaster(financialReceiptsMaster);
		result.setFinancialReceiptsDetailList(financialReceiptsDetailList);	
		result.setYdbhid(financialReceiptsQueryEntity.getYdbhid());
		return result;
	}

	@Override
	public FinancialReceiptsMaster queryFinancialReceiptsMasterById(Integer id,String gs)
			throws ParameterException, BusinessException {
		if(id == null){
			throw new ParameterException("id", id, "收据号不能为空");
		}
		FinancialReceiptsMaster result = new FinancialReceiptsMaster();
		try {
			result = financialReceiptsMapper.selectFinancialReceiptsMasterById(id,gs);
		} catch (Exception e) {
			throw new BusinessException("查询分理收据主表信息失败", e);
		}
		return result;
	}

	@Override
	public void modifyFinancialReceiptsMasterByIdAndGs(FinancialReceiptsMaster financialReceiptsMaster)
			throws ParameterException, BusinessException {
		if(financialReceiptsMaster == null){
			throw new ParameterException("financialReceiptsMaster", financialReceiptsMaster, "分理收据信息不能为空");
		}
		try {
			financialReceiptsMapper.updateFinancialReceiptsMasterByIdAndGs(financialReceiptsMaster);
		} catch (Exception e) {
			throw new BusinessException("更新分理收据信息失败", e);
		}
	}

	@Override
	public void modifyFinancialReceiptsDetailBySjidAndGsAndXzh(FinancialReceiptsDetail financialReceiptsDetail)
			throws ParameterException, BusinessException {
		if(financialReceiptsDetail == null){
			throw new ParameterException("financialReceiptsDetail", financialReceiptsDetail, "分理收据明细信息不能为空");
		}
		try {
			financialReceiptsMapper.updateFinancialReceiptsDetailBySjidAndGsAndXzh(financialReceiptsDetail);
		} catch (Exception e) {
			throw new BusinessException("更新分理收据明细信息失败", e);
		}	
	}

	@Transactional
	@Override
	public void savefinancialReceiptsPayMoney(FinancialReceiptsMaster financialReceiptsMaster,List<FinancialReceiptsDetail> financialReceiptsDetailList,
			FinancialReceiptsQueryEntity financialReceiptsQueryEntity) throws ParameterException, BusinessException {
		
		Assert.notNull("financialReceiptsMaster", financialReceiptsMaster, "分理收据信息不能为空");
		Assert.notEmpty("financialReceiptsDetailList", financialReceiptsDetailList, "分理收据明细信息不能为空");
		
		//循环更新明细信息
		for (FinancialReceiptsDetail financialReceiptsDetail : financialReceiptsDetailList) {
			//判断应收金额和实收金额是否相等
			if(!financialReceiptsDetail.getShfje().equals(financialReceiptsDetail.getShsje()) ){
				throw new ParameterException("实收金额不等于应收金额，请确认！");
			}
			financialReceiptsDetail.setGs(financialReceiptsQueryEntity.getCompany());
			financialReceiptsDetail.setVer(2);
			financialReceiptsDetail.setSjid(financialReceiptsMaster.getId());
			this.modifyFinancialReceiptsDetailBySjidAndGsAndXzh(financialReceiptsDetail);
		}
		
		//先更新分理收据主表信息
		financialReceiptsMaster.setGs(financialReceiptsQueryEntity.getCompany());
		financialReceiptsMaster.setChuNa(financialReceiptsQueryEntity.getAccount());
		financialReceiptsMaster.setIsShq(1);
		financialReceiptsMaster.setSqsj(new Date());
		this.modifyFinancialReceiptsMasterByIdAndGs(financialReceiptsMaster);	
	}

	@Override
	public List<FinancialReceiptsDetail> queryFinancialReceiptsDetailByYdbhidOrGs(Integer sjid, String gs,
			String ydbhid) throws ParameterException, BusinessException {
		List<FinancialReceiptsDetail> result = new ArrayList<FinancialReceiptsDetail>();
		if(StringUtils.isEmpty(gs)){
			throw new ParameterException("gs", gs, "公司不能为空");
		}
		if(sjid == null && StringUtils.isEmpty(ydbhid)){
			throw new ParameterException("请输入收据号或者运单号");
		}
		if(sjid != null && StringUtils.isEmpty(ydbhid)){
			try {
				result = financialReceiptsMapper.selectFinancialReceiptsDetailByYdbhid(gs, ydbhid);
			} catch (Exception e) {
				throw new BusinessException("查询分理收据明细信息失败", e);
			}
		}
		if(sjid == null && StringUtils.isNotEmpty(ydbhid)){
			try {
				result = financialReceiptsMapper.selectFinancialReceiptsDetailBySjid(gs, sjid);
			} catch (Exception e) {
				throw new BusinessException("查询分理收据明细信息失败", e);
			}
		}
		return result;
	}
	
	@Override
	public FinancialReceiptsResult searchOffsetFinancialReceipts(
			FinancialReceiptsQueryEntity financialReceiptsQueryEntity) throws ParameterException, BusinessException {
	
		//先判断该用户是否有权限进行分理收据冲红
		Integer limit = 0;
		try {
			limit = transportRightMapper.getRightNum(AuthorticationConstant.CWSJ_CH, financialReceiptsQueryEntity.getAccount());
		} catch (Exception e) {	
			throw new BusinessException("获取用户生成分理收据权限失败", e);
		}
		
		if(limit == null || limit == 0){
			throw new BusinessException("您没有权限使用这个功能，请与系统管理员联系！");
		}
		
		if(StringUtils.isEmpty(financialReceiptsQueryEntity.getYdbhid()) && financialReceiptsQueryEntity.getSjid() == null){
			throw new BusinessException("请输入运单编号或者收据号！");
		}
		
		//查询分理收据信息
		FinancialReceiptsMaster financialReceiptsMaster = new FinancialReceiptsMaster();
		if(StringUtils.isNotEmpty(financialReceiptsQueryEntity.getYdbhid()) && financialReceiptsQueryEntity.getSjid() == null){	
			financialReceiptsMaster = this.queryFinancialReceiptsMasterByYdbhid(financialReceiptsQueryEntity.getYdbhid());
			if(financialReceiptsMaster == null){// 未生成分理收据
				throw new BusinessException("此运单号'" + financialReceiptsQueryEntity.getYdbhid() + "'还未生成分理收据,请确认！");
			}
		}else{
			Integer id = null;
			try {
				id = Integer.parseInt(financialReceiptsQueryEntity.getSjid());
			}catch (Exception e) {
				throw new BusinessException("收据号编号不是数字或太长！");
			}
			financialReceiptsMaster = this.queryFinancialReceiptsMasterById(id,financialReceiptsQueryEntity.getCompany());
			if(financialReceiptsMaster == null){// 未生成分理收据
				throw new BusinessException("此收据号'" + financialReceiptsQueryEntity.getSjid() + "'已不存在或无效,请确认！");
			}
		}
		
		//判断登录人公司
		if(!financialReceiptsMaster.getGs().equals(financialReceiptsQueryEntity.getCompany())){
			throw new BusinessException("此运单号:'" + financialReceiptsMaster.getFiwtYdbhid() + "',生成的收据为" 
					+ financialReceiptsMaster.getFiwtCwpzhbh() + ",所属公司为" + financialReceiptsMaster.getGs() + ",当前公司不能进行分理收据冲红操作,请确认！");
		}
		
		//判断权限
		switch(limit){
			case 1 :
				if(financialReceiptsMaster.getIsShq() == 1){
					if(financialReceiptsMaster.getIsReport() ==1){	
						throw new BusinessException("本收据'" + financialReceiptsQueryEntity.getSjid() + "',已经做了收钱处理,并且生成了出纳日报表,必须有单位财务负责人权限才能冲红,请确认!");
					}else{
						throw new BusinessException("本收据'" + financialReceiptsQueryEntity.getSjid() + "',已经做了收钱处理,必须有足够权限才能冲红,请确认!");
					}
				}
				break;
			case 2 :
				if(financialReceiptsMaster.getIsReport() ==1){	
					throw new BusinessException("本收据'" + financialReceiptsQueryEntity.getSjid() + "',已经生成出纳日报表,必须有单位财务负责人权限才能冲红,请确认!");
				}
		}
		
		if(StringUtils.isNotEmpty(financialReceiptsMaster.getZhiPiao())){
			//查询制单员
			Employee zhidan = new Employee();
			try {
				zhidan = adjunctSomethingService.getEmployeeByNumber(financialReceiptsMaster.getZhiPiao());
			} catch (Exception e) {
				throw new BusinessException("查询制单员信息失败", e);
			}
			financialReceiptsMaster.setZhiPiao(zhidan.getEmplyeeName());
		}
		
		if(StringUtils.isNotEmpty(financialReceiptsMaster.getChuNa())){
			//查询出纳员
			Employee chuna = new Employee();
			try {
				chuna = adjunctSomethingService.getEmployeeByNumber(financialReceiptsMaster.getChuNa());
			} catch (Exception e) {
				throw new BusinessException("查询出纳员信息失败", e);
			}
			financialReceiptsMaster.setChuNa(chuna.getEmplyeeName());
		}
		//查询明细信息
		List<FinancialReceiptsDetail> financialReceiptsDetailList = this.queryFinancialReceiptsDetailByYdbhidAndGs(financialReceiptsMaster.getId(), financialReceiptsMaster.getGs(), 
				financialReceiptsMaster.getFiwtYdbhid());
		
		if(financialReceiptsDetailList.size() == 0 && financialReceiptsDetailList.isEmpty()){
			throw new BusinessException("本收据'" + financialReceiptsQueryEntity.getSjid() + "',无收费项目,请确认!");
		}
		
		for (FinancialReceiptsDetail financialReceiptsDetail : financialReceiptsDetailList) {
			if(financialReceiptsDetail.getShsje() == null || financialReceiptsDetail.getShsje() == 0){
				financialReceiptsDetail.setShsje(0.0);
			}
		}
		
		FinancialReceiptsResult result = new FinancialReceiptsResult();
		result.setFinancialReceiptsMaster(financialReceiptsMaster);
		result.setFinancialReceiptsDetailList(financialReceiptsDetailList);	
		result.setYdbhid(financialReceiptsQueryEntity.getYdbhid());
		return result;	
	}

	@Override
	@Transactional
	public Integer offsetFinancialReceipts(OffsetFinancialReceiptsQueryEntity offsetFinancialReceiptsQueryEntity)
			throws ParameterException, BusinessException {
		Assert.notNull("offsetReason", offsetFinancialReceiptsQueryEntity.getOffsetReason(), "分理收据冲红原因不能为空");
		
		//生成红票收据号
		Map<String,Object> sjidMap = new HashMap<String, Object>();
		sjidMap.put("gs", offsetFinancialReceiptsQueryEntity.getCompany());
		sjidMap.put("nf", DateUtils.getNowYear());
		sjidMap = this.udtGetCwsjid(sjidMap);
		Integer offsetSjid = (Integer)sjidMap.get("cwsjid");
		
		//调用存储过程进行分理收据冲红操作
		Map<String,Object> offsetMap = new HashMap<String, Object>();
		offsetMap.put("sjhao", offsetFinancialReceiptsQueryEntity.getSjid());
		offsetMap.put("gs", offsetFinancialReceiptsQueryEntity.getCompany());
		offsetMap.put("SJch_why", offsetFinancialReceiptsQueryEntity.getOffsetReason());
		offsetMap.put("chuna", offsetFinancialReceiptsQueryEntity.getAccount());
		offsetMap.put("sjhao2", offsetSjid);
		offsetMap = this.offsetFinancialReceipts(offsetMap);
		if((Integer)offsetMap.get("retn") != 1){
			throw new BusinessException("冲红存储过程执行失败！");
		}
		return offsetSjid;
	}

	@Override
	@Transactional
	public Map<String, Object> offsetFinancialReceipts(Map<String, Object> map)
			throws ParameterException, BusinessException {
		if(map.get("sjhao") == null){
			throw new ParameterException("收据号不能为空");
		}
		if(map.get("gs") == null){
			throw new ParameterException("公司不能为空");
		}
		if(map.get("SJch_why") == null){
			throw new ParameterException("冲红原因不能为空");
		}
		if(map.get("chuna") == null){
			throw new ParameterException("出纳员不能为空");
		}
		if(map.get("sjhao2") == null){
			throw new ParameterException("红票收据号不能为空");
		}
		try {
			financialReceiptsMapper.offsetFinancialReceipts(map);
		} catch (Exception e) {
			throw new BusinessException("分理收据冲红失败", e);
		}
		return map;
	}

	@Deprecated
	@Override
	public FinancialReceiptsResult isGenerateFinancialReceipts(
			FinancialReceiptsQueryEntity financialReceiptsQueryEntity) throws ParameterException, BusinessException {
		FinancialReceiptsResult result = new FinancialReceiptsResult();
		//判断分理收据有没有生成过
		FinancialReceiptsMaster financialReceiptsMaster = this.queryFinancialReceiptsMasterByYdbhid(financialReceiptsQueryEntity.getYdbhid());
		if(financialReceiptsMaster != null && financialReceiptsMaster.getId() > 0){// 收据号大于0,代表收据号已存在
			result.setIsGenerate(1);
		}else{// 代表收据号不存在
			result.setIsGenerate(0);
		}
		return result;
	}

	
	
}
