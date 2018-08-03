package com.ycgwl.kylin.transport.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.ycgwl.kylin.AuthorticationConstant;
import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.entity.PageInfo;
import com.ycgwl.kylin.entity.RequestJsonEntity;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.dto.*;
import com.ycgwl.kylin.transport.entity.*;
import com.ycgwl.kylin.transport.entity.adjunct.*;
import com.ycgwl.kylin.transport.persistent.AdjunctSomethingMapper;
import com.ycgwl.kylin.transport.persistent.TransportOrderMapper;
import com.ycgwl.kylin.transport.persistent.TransportRightMapper;
import com.ycgwl.kylin.transport.service.api.ITransportSignRecordService;
import com.ycgwl.kylin.transport.vo.CustomerLabelVo;
import com.ycgwl.kylin.transport.vo.FreightRecordVo;
import com.ycgwl.kylin.transport.vo.LoadingListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Service("kylin.transport.dubbo.local.adjunctSomethingService")
public class AdjunctSomethingServiceImpl implements com.ycgwl.kylin.transport.service.api.AdjunctSomethingService {
	@Resource
	private AdjunctSomethingMapper somethingMapper;
	
	@Autowired
	TransportOrderMapper transportOrderMapper;  
	
	@Autowired
	TransportRightMapper transportRightMapper;
	
	@Autowired
	private ITransportSignRecordService iTransportSignRecordService;
	
//	@Autowired
//	private BundleReceiptService bundleReceiptService;
//	
//	@Autowired
//	private ITransportOrderService transportOrderService;
	
	@Resource
	public TransportRightMapper rightMapper;
	
	@Resource  
    private DataSourceTransactionManager transactionManager; 

	/**
	 *  模糊查询中转站
	 * @param transfer 中转站
	 * @return  匹配到的所有中转站
	 */
	@Override
	public List<String> listTransferstation(String transfer){
		return somethingMapper.listTransferstation(transfer);
	}
	/**
	 *  模糊查询中转站
	 * @param transfer 中转站
	 * @return  匹配到的所有中转站
	 */
	@Override
	public List<String> getAllDaozhan(String companyName){
		return somethingMapper.getAllDaozhan(companyName);
	}

	/**
	 * 模糊查询中转网点
	 * @param transfer
	 * @param transferpoint
	 * @return 匹配到的所有中转网点
	 */
	@Override
	public List<String>  listTransferPoint(String transfer,String transferpoint){
		return somethingMapper.listTransferPoint(transfer,transferpoint);
	}

	/**
	 * 批量查询运单是否已经查询
	 *      和上一个方法重复,回头把上面的方法给删掉,
	 * @param ydbhids
	 * @return
	 */
	@Override
	public Boolean isReceived(String[] ydbhids) {
		Integer received = somethingMapper.isReceived(ydbhids);
		if (received > 0 ){
			return true;
		}
		return false;
	}





	@Override
	public Collection<Company> listStationCompanys() {
		return somethingMapper.queryStationCompanys();
	}

	@Override
	public Company getCompanyByName(String companyName) {
		/*
		select shx from  gongsi where name = '天津'
		 */
		return somethingMapper.getCompanyByName(companyName);
	}

	@Override
	public Employee getEmployeeByNumber(String number) {
		return somethingMapper.getEmployeeByNumber(number);
	}


	@Override
	public Collection<Customer> listCustomerByCompanyName(String companyName, String something) {
		return somethingMapper.queryCustomerByCompanyName(companyName, something);
	}

	@Override
	public Collection<Industry> listIndustrys() {
		/*
		SELECT distinct dbo.T_Customer_categories.ID,   
        dbo.T_Customer_categories.Custname,   
        dbo.T_Customer_categories.remark,   
        dbo.T_Customer_categories.CustJM,   
        dbo.T_Customer_categories.Custsort,   
        dbo.T_Customer_categories.Custstate   
        FROM dbo.T_Customer_categories
		 */
		return somethingMapper.queryIndustrys();
	}

	@Override
	public Collection<ConveyWay> listConveyWays() {
		/*
		 * SELECT T_BASE_YSFS.ysfs FROM T_BASE_YSFS
		 */
		return somethingMapper.queryConveyWays();
	}

	@Override
	public void insertReleaseWaiting(ReleaseWaiting releaseWaiting) {
		/*
		insert into hwyd_ddfh 
		(ydbhid,fazhan,ddfhzt,ddfhczygh,
		ddfhczshijian,tzfhzt) 
		values (:ydbhid,:ls_fazhan,1,
		:ycr_user.zh_czry,:ls_ddfhsj,1)
		 */
		somethingMapper.insertReleaseWaiting(releaseWaiting);
	}

	/* (non-Javadoc)
	 * @see com.ycgwl.kylin.transport.service.api.AdjunctSomethingService#getForeignRouteByName(java.lang.String)
	 */
	@Override
	public ForeignRoute getForeignRouteByName(String foreignName) {
		//select gys_lxr,gys_dhsj into :person_sj1xm,:person_sj1tel from t_waichexinxi_gysjbzl where gys_gs =
		return somethingMapper.getForeignRouteByName(foreignName);
	}

	/* (non-Javadoc)
	 * @see com.ycgwl.kylin.transport.service.api.AdjunctSomethingService#getVehicleInfoByNumber(java.lang.String)
	 */
	@Override
	public VehicleInfo getVehicleInfoByNumber(String number) {
		return somethingMapper.getVehicleInfoByNumber(number);
	}

	@Override
	public Collection<ForeignRoute> listForeignRouteByName(String foreignName) {
		return somethingMapper.queryForeignRouteByName(foreignName);
	}

	@Override
	public Collection<VehicleInfo> listVehicleInfoByNumber(String number) {
		return somethingMapper.queryVehicleInfoByNumber(number);
	}

	@Override
	public String getYsfsFromHwydByYdbhid(String ydbhid) {
		return somethingMapper.getYsfsFromHwydByYdbhid(ydbhid);
	}

	@Override
	public ReleaseWaiting getReleaseWaitingByYdbhid(String ydbhid) {

		return somethingMapper.getReleaseWaitingByYdbhid(ydbhid);
	}

	@Override
	public List<String> listDaoZhanByCompany(String daozhan,String gs) {

		return somethingMapper.listDaoZhanByCompany(daozhan,gs);
	}

	@Override
	public Integer isReceivedByYdbhid(String ydbhid) {
		return somethingMapper.isReceivedByYdbhid(ydbhid);
	}

	@Override
	public JsonResult getLatticePoint(String daozhan) {
		List<String> list = somethingMapper.getLatticePoint(daozhan);
		JsonResult conveyResult = JsonResult.getConveyResult("200", "查询成功");
		conveyResult.put("latticePoint", list);
		return  conveyResult;
	}
	@Override
	public JsonResult beginplaceNet() {
		JsonResult conveyResult = JsonResult.getConveyResult("200", "查询成功");
		conveyResult.put("latticePoint", somethingMapper.beginplaceNet());
		return  conveyResult;
	}
	@Override
	public BigDecimal getElseCost(String chxh, Date zhchrq) {

		return 	somethingMapper.getElseCost(chxh,zhchrq);
	}

	@Override
	public String createYdbhid(String companyCode, String grid) {
		Map<String, String> map = new HashMap<String, String >();
		map.put("gsbh", companyCode);
		map.put("grid", grid);
		map.put("o_YunDanHao", "");
		somethingMapper.createYdbhid(map);
		return map.get("o_YunDanHao");
	}

	@Override
	public List<StoreHouse> getStoreHouseBycompany(String company) {
		return somethingMapper.getStoreHouseBycompany(company);
	}

	@Override
	public List<Customer> listCustomerByKhmcAndKhbm(String companyName, String khmc, String khbm) {
		return somethingMapper.queryCustomerByKhmcAndKhbm(companyName, khmc, khbm);
	}

	@Override
	public List<Customer> listCustomerByKhmc(String companyName, String khmc) {
		return somethingMapper.queryCustomerByKhmc(companyName, khmc);
	}

	@Override
	public List<String> getArriveNetWork(String daozhan,String arriveNetWork) {
		//参数是编号,转化一下
		daozhan = somethingMapper.getCompanyByName(daozhan).getCompanyCode();
		List<String> arriveList = somethingMapper.getArriveNetWorkEdit(daozhan,arriveNetWork);
		return arriveList;
	}

	@Override
	public Customer checkKhmessage(String khbm, String fhdwmch,String company) throws ParameterException {
		List<Customer> customerList = listCustomerByKhmc(company,fhdwmch);
		
		if(CollectionUtils.isEmpty(customerList))
			throw new ParameterException("当前登录公司下不存在该客户："+fhdwmch);
		
		if(StringUtils.isEmpty(khbm)){
			if(customerList.size()>=1) {
				return customerList.get(0);
			} else {
				throw new ParameterException(company+"公司未维护客户名称："+fhdwmch+"的信息");
			}
		}
		
		//用户录有客户编号
		for (Customer customer : customerList) {
			if(khbm.equals(customer.getCustomerKey())) {
				return customer;
			}
		}
		
		throw new ParameterException("客户编码与客户名称不一致");
	}

	@Override
	@Transactional
	public FinanceStandardPrice findFinanceStandardPriceByGs(String company) {
		return somethingMapper.financeStandardPrice(company);
	}

	@Override
	public Integer checkuserdelrec(String ydbhid) {
		Map<String,String> map = new HashMap<>();
		map.put("ydbhid", ydbhid);
		map.put("result", "");
		somethingMapper.checkuserdelrec(map);
		return Integer.parseInt(map.get("result"));
	}

	@Override
	public void deleteFenLiKucunByYdbhid(String ydbhid) {
		somethingMapper.deleteFenLiKucunByYdbhid(ydbhid);
	}

	@Override
	public KucunEntity queryKucun(String ydbhid, Integer ydxzh) {
		return somethingMapper.queryKucun(ydbhid,ydxzh);
	}
	@Override
	public List<FenliKucunEntity> queryFenliKucunEntity(String ydbhid, Integer ydxzh, String gs) {
		return somethingMapper.queryFenliKucunEntity(ydbhid,ydxzh,gs);
	}

	@Override
	public Collection<Company> queryStationCompany() {
		return somethingMapper.queryStationCompany();
	}
	@SuppressWarnings("rawtypes")
	@Override
	public void updateKucun(Map orderDetail) {
		somethingMapper.updateKucunData(orderDetail);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public void updateFenLiKucun(Map orderDetail) {
		somethingMapper.updateFenLiKucunData(orderDetail);
	}
	@Override
	public List<KucunEntity> queryKucunByYdbhid(String ydbhid) {
		return somethingMapper.queryKucunByYdbhid(ydbhid);
	}
	@Override
	public List<FenliKucunEntity> queryFenliKucunByYdbhid(String ydbhid,String company) {
		return somethingMapper.queryFenliKucunByYdbhid(ydbhid,company);
	}
	@Override
	public void recoverGoodArriveFenLiKucun(BundleReceipt receipt) {
		somethingMapper.recoverGoodArriveFenLiKucun(receipt);
	}
	@Override
	public String getFinaceCertifyNoByYdbhid(String ydbhid) {
		return somethingMapper.getFinaceCertifyNoByYdbhid(ydbhid);
	}
	@Override
	@Transactional
	public void plusKucun(BundleReceipt bundleReceipt) {
		TransportSignRecord transportSignRecordOld = iTransportSignRecordService.getTransportSignRecordByYdbhid(bundleReceipt.getYdbhid());			
	
		if (null != transportSignRecordOld && transportSignRecordOld.getSignType() > 0)	{
			transportSignRecordOld.setSignType(0);
		iTransportSignRecordService.modifyTransportSignRecordByYdbhid(transportSignRecordOld);
		}
			
	    somethingMapper.plusKucun(bundleReceipt);
	}
	@Override
	@Transactional
	public void plusFenliKucun(BundleReceipt bundleReceipt) {
		TransportSignRecord transportSignRecordOld = iTransportSignRecordService.getTransportSignRecordByYdbhid(bundleReceipt.getYdbhid());			
		if (null != transportSignRecordOld && transportSignRecordOld.getSignType() > 0)	{
			transportSignRecordOld.setSignType(0);
		iTransportSignRecordService.modifyTransportSignRecordByYdbhid(transportSignRecordOld);
		}
		somethingMapper.plusFenliKucun(bundleReceipt);
	}
	
	@Override
	@Transactional
	public void plusFenliKucunByXuhao(BundleReceipt bundleReceipt,Integer xuhao) {
		TransportSignRecord transportSignRecordOld = iTransportSignRecordService.getTransportSignRecordByYdbhid(bundleReceipt.getYdbhid());			
		if (null != transportSignRecordOld && transportSignRecordOld.getSignType() > 0)	{
			transportSignRecordOld.setSignType(0);
		iTransportSignRecordService.modifyTransportSignRecordByYdbhid(transportSignRecordOld);
		}
		somethingMapper.plusFenliKucunByXuhao(bundleReceipt,xuhao);
	}

	@Override
	public List<String> getArriveNetWorkList(String daozhan) {
		//参数是编号,转化一下
		Company companyByName = somethingMapper.getCompanyByName(daozhan);
		if(companyByName != null)
			return somethingMapper.getArriveNetWorkList(companyByName.getCompanyCode());
		return Collections.emptyList();
	}

	@Override
	public void updateIncome_HByInsNo(RequestJsonEntity entity) {
		somethingMapper.updateIncome_HByInsNo(entity);
	}

	@Override
	public JsonResult getArriveNetWork(String daozhan) {
		//参数是编号,转化一下
		daozhan = somethingMapper.getCompanyByName(daozhan).getCompanyCode();
		List<String> arriveList = somethingMapper.getArriveNetWork(daozhan);
		JsonResult result = JsonResult.getConveyResult("200", "查询成功");
		result.put("arriveNetWork", arriveList);
		return result;
	}

	@Override
	public List<ChargingProjects> listChargingProjects() {	
		List<ChargingProjects> result = new ArrayList<ChargingProjects>();
		try {
			result = somethingMapper.selectChargingProjects();
		} catch (Exception e) {
			throw new BusinessException("获取收费项目信息失败", e);
		}
		return result;
	}

	@Override
	public List<Customer> listCustomerByGs(String gs) {
		if(StringUtils.isEmpty(gs)){
			throw new ParameterException("gs", gs, "公司不能为空");
		}
		List<Customer> result = new ArrayList<Customer>();
		try {
			result = somethingMapper.selectCustomerByGs(gs);
		} catch (Exception e) {
			throw new BusinessException("获取客户名称和客户编码信息失败", e);
		}
		return result;
	}

	@Override
	public void deleteGoodArriveFenLiKucun(BundleReceipt receipt) {
		somethingMapper.deleteGoodArriveFenLiKucun(receipt);
	}

	@Override
	public JSONObject findCustomerLabel(String wayBillNum) {
		JSONObject json = new JSONObject();
		List<CustomerLabelVo> customerLabelVoList = somethingMapper.findCustomerLabel(wayBillNum);
		int totalJianShu = 0;
		for (CustomerLabelVo customerLabelVo : customerLabelVoList) {
			totalJianShu += customerLabelVo.getJianshu();
		}
		json.put("list", customerLabelVoList);
		json.put("totalJianShu", totalJianShu);
		return json;
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@Override
	public JsonResult findFreightRecord(String account, FreightRecordDto freightRecordDto) {
		
		// 判断货运记录查询权限    QL_FREIGHT_RECORD_PERMISSIONS
		Integer hyjlIn = transportRightMapper.getRightNum(AuthorticationConstant.GOOD_RECORD_FIND,account);
		
		if (null == hyjlIn || hyjlIn < 1) {
			return JsonResult.getConveyResult("400", "你没有权限使用这个功能，请与系统管理员联系！");
		}
		
		// 货云记录 查询
        com.github.pagehelper.Page<Object> page = PageHelper.startPage(freightRecordDto.getNum(), freightRecordDto.getSize(), true);
		List<FreightRecordVo> freightRecordVoList = somethingMapper.findFreightRecord(freightRecordDto);
		PageInfo pageInfo = new PageInfo(freightRecordVoList);
		if (null == pageInfo.getCollection() || pageInfo.getCollection().size() <=0) {
			return JsonResult.getConveyResult("400", "没有符合条件的数据");
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", pageInfo);
		return JsonResult.getConveyResult("200", "查询成功", jsonObject);
	}

	@Override
	public JsonResult saveFreightRecord(String account, FreightRecordInputDto freightRecordInputDto) {
		// 判断货运记录录入权限
		Integer rightNum = transportRightMapper.getRightNum(AuthorticationConstant.HYJL_IN,account);
		if (rightNum == null || rightNum < 1) {
			return JsonResult.getConveyResult("400", "你没有权限使用这个功能，请与系统管理员联系！");
		}

		String fchrq2 = freightRecordInputDto.getFchrq();
		String happenedTime = freightRecordInputDto.getHappenedTime();
		int compareTo = fchrq2.compareTo(happenedTime);
		if (compareTo >= 0) {
			return JsonResult.getConveyResult("400", "发生日期要小于装车日期！");
		}
		DefaultTransactionDefinition def = new DefaultTransactionDefinition(); 
		// 事物隔离级别，开启新事务，这样会比较安全些。  
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		// 获得事务状态
		TransactionStatus status = transactionManager.getTransaction(def); 
		try {
			// 货云记录 录入
			somethingMapper.saveFreightRecord(freightRecordInputDto);
			int id = freightRecordInputDto.getId();

			if (null == freightRecordInputDto.getFreightRecordDetailDto()
					|| freightRecordInputDto.getFreightRecordDetailDto().size() <= 0) {
				return JsonResult.getConveyResult("400", "请录入货物运单详情");
			}
			// 满足异常详情表的id测略
			int i = 1;
			for (FreightRecordDetailDto freightRecordDetailDto : freightRecordInputDto.getFreightRecordDetailDto()) {

				// 车牌号和运单号是否匹配
				String fchrq = freightRecordInputDto.getFchrq();
				if (null == fchrq) {
					return JsonResult.getConveyResult("400", "装车时间不能为空");
				}
				String substring = fchrq.substring(0, 10);
				String startFchrq = substring + " 00:00:00.0"; 
				String endFchrq = substring + " 23:59:59.999"; 
				List<BundleReceipt> bundleReceiptList = somethingMapper.selectBundReceiptByCxhnAndYdh(freightRecordDetailDto.getWayBillNum(),freightRecordInputDto.getTrain(), startFchrq,endFchrq);
				if (bundleReceiptList.size() == 0) {
					return JsonResult.getConveyResult("400", freightRecordDetailDto.getWayBillNum() + "与" + freightRecordInputDto.getTrain() +"不匹配");
				}
				freightRecordDetailDto.setId(id);
				String wayBillNum = freightRecordDetailDto.getWayBillNum();
				TransportOrder transportOrder = transportOrderMapper.getTransportOrderByYdbhid(wayBillNum);
				if (null == transportOrder) {
					return JsonResult.getConveyResult("400", wayBillNum + "此运单不存在");
				}
				// 货云记录详情 录入
				freightRecordDetailDto.setXzh(i);
				int count = somethingMapper.saveFreightRecordDetail(freightRecordDetailDto);
				if (count != 1) {
					 throw new BusinessException("异常详情录入失败!");
				}
				i++;
			}
			transactionManager.commit(status);
		} catch (Exception e) {
			transactionManager.rollback(status); 
			return JsonResult.getConveyResult("400", "操作失败");
		}
		return JsonResult.getConveyResult("200", "操作成功");

	}
	
	@Override
	public boolean isSuperAuthentor(String account) {
		Integer rightNum = rightMapper.getRightNum(AuthorticationConstant.SUP_CANCEL_RIGHT,account);
		if(rightNum != null && rightNum >= 1) 
			return true;
		return false;
	}

	// 添加意见
	@Override
	public JsonResult updateFreightRecord(String account, UpdateFreightRecordDto updateFreightRecordDto) {
		updateFreightRecordDto.setCurrentAccount(account);
		// 修改货云记录详情
		int count = somethingMapper.updateFreightRecord(updateFreightRecordDto);
		if (1 == count) {
			return JsonResult.getConveyResult("200", "操作成功");
		}
		return JsonResult.getConveyResult("400", "操作失败");
	}
	@Override
	public boolean isReleaseWaiting(String ydbdhid) {
		ReleaseWaiting releaseWaiting = this.getReleaseWaitingByYdbhid(ydbdhid);
		if(releaseWaiting == null) 
			return false;
		return releaseWaiting.getDdfhzt() == 1;
	}

	@Override
	public void deleteGoodArriveFenLiKucunByXuhao(Integer xuhao) {
		somethingMapper.deleteGoodArriveFenLiKucunByXuhao(xuhao);
	}

	@Override
	public JsonResult saveFreightRecordselect(String username,String account, String company,FreightRecordSerachDto freightRecordSerachDto) {
//		业务逻辑：
//		1.可以一次录入一车多票的货物异常信息，也可以根据运单号录入单票货物异常信息
//		2.异常环节分为提货、干线、配送
//		3.根据对应的查询条件，查出车牌号、装车日期、装车发站、装车到站、供应商名称、异常环节信息
//		4.录单员将异常信息录入之后，保存后将提交给相关负责人审批
//		5.如果这个货发站与到站一致，不再需要发站负责人填写处理意见，只需到站负责人填写处理意见，否则到站和站负责人的处理意见都要填写，
//		     只有到站负责人给出处理意见后发站负责人方可填写处理意见
//		6.责任方需要到站负责人去确认填写
		
		// 判断货运记录录入权限
		Integer rightNum = transportRightMapper.getRightNum(AuthorticationConstant.HYJL_IN,account);
		if (rightNum == null || rightNum < 1) {
			return JsonResult.getConveyResult("400", "你没有权限使用这个功能，请与系统管理员联系！");
		}
		
		JSONObject json = new JSONObject();
		List<LoadingListVo> loadingListVoList = somethingMapper.saveFreightRecordselect(freightRecordSerachDto);
		if (null == loadingListVoList || loadingListVoList.size() <= 0) {
			return JsonResult.getConveyResult("400", "此运单没有装载清单记录");
		}
		
		LoadingListVo loadingListVo = loadingListVoList.get(0);
		json.put("data", loadingListVo);
		return JsonResult.getConveyResult("200", "操作成功", json);
	}

	@Override
	public JsonResult selectFreightRecordDetail(FreightRecordDetailSerachDto freightRecordDetailSerachDto) {
		JSONObject json = new JSONObject();
		
		FreightRecord freightRecord = somethingMapper.selectFreightRecordById(freightRecordDetailSerachDto.getId());
		List<FreightRecordDetail> freightRecordDetail = somethingMapper.selectFreightRecordDetailById(freightRecordDetailSerachDto.getId());
		if (null == freightRecord || null == freightRecordDetail || freightRecordDetail.size() < 0 ) {
			return JsonResult.getConveyResult("400", "没有符合条件的数据");
		}
		json.put("freightRecord", freightRecord);
		json.put("freightRecordDetailList", freightRecordDetail);
		return JsonResult.getConveyResult("200", "查询成功", json);
	}

	@Override
	public JsonResult addComments(FreightRecordInputDto freightRecordInputDto) {
		// 判断装车到站/发站单位负责人意见  
		Integer opinionJurisdiction = transportRightMapper.getRightNum(AuthorticationConstant.HYJLDWYJ,freightRecordInputDto.getCurrAccount());
		if (opinionJurisdiction == null || opinionJurisdiction < 1) {
			return JsonResult.getConveyResult("400", "你没有权限使用这个功能，请与系统管理员联系！");
		}
		
		String daozhan = freightRecordInputDto.getDaozhan();
		String fazhan = freightRecordInputDto.getFazhan();
		if (StringUtil.isNotEmpty(freightRecordInputDto.getFzdwOpinion()) &&  (!fazhan.equals(daozhan))) {
			
		}
		if ((fazhan.equals(daozhan) && StringUtil.isNotEmpty(freightRecordInputDto.getDzdwOpinion()))
				|| (StringUtil.isNotEmpty(freightRecordInputDto.getFzdwOpinion()) && (!fazhan.equals(daozhan)))) {
			freightRecordInputDto.setIsHandle("1");
		} else {
			freightRecordInputDto.setIsHandle("0");
		}
		
		int count = somethingMapper.addComments(freightRecordInputDto);
		if (count == 1) {
			return JsonResult.getConveyResult("200", "操作成功");
		} else {
			return JsonResult.getConveyResult("400", "操作失败");
		}
	}
}
