package com.ycgwl.kylin.transport.service.impl;

import com.github.pagehelper.util.StringUtil;
import com.google.common.base.Joiner;
import com.microsoft.sqlserver.jdbc.StringUtils;
import com.ycgwl.kylin.AuthorticationConstant;
import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.entity.RequestJsonEntity;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.entity.*;
import com.ycgwl.kylin.transport.persistent.BundleArriveMapper;
import com.ycgwl.kylin.transport.persistent.TransportRightMapper;
import com.ycgwl.kylin.transport.service.api.*;
import com.ycgwl.kylin.util.Assert;
import com.ycgwl.kylin.util.CommonDateUtil;
import com.ycgwl.kylin.util.IPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("kylin.transport.dubbo.local.bundleArriveService")
public class BundleArriveServiceImpl implements IBundleArriveService {

	private final static String OPERATING_MENU = "取货派车修改";
	
	@Resource
	private BundleReceiptService receiptService;

	@Resource
	private ITransportOrderService orderService;

	@Resource
	private BundleArriveMapper bundleArriveMapper;

	@Resource
	private AdjunctSomethingService adjunctSomethingService;

	@Resource
	private ITransportSignRecordService signRecordService;

	@Resource
	private TransportRightMapper rightMapper;
	
	@Autowired
	private IExceptionLogService exceptionLogService; 

	@Override
	public List<BundleArriveEntity> findBundleArriveList(Map<String, Object> map) {
		if(ObjectUtils.isEmpty(map.get("ziti"))){
			map.remove("ziti");
		}
		if(ObjectUtils.isEmpty(map.get("yiti"))){
			map.remove("yiti");
		}
		String loadingDateBegin = (String) map.get("loadingDateBegin");
		String loadingDateEnd = (String) map.get("loadingDateEnd");
		if(!StringUtils.isEmpty(loadingDateBegin)){
			loadingDateBegin = loadingDateBegin + " 00:00:00";
			map.put("loadingDateBegin", loadingDateBegin);
		}
		if(!StringUtils.isEmpty(loadingDateEnd)){
			loadingDateEnd = loadingDateEnd + " 23:59:59";
			map.put("loadingDateEnd", loadingDateEnd);
		}
		
		List<BundleArriveEntity> resultList = new ArrayList<BundleArriveEntity>();
		if(ObjectUtils.isEmpty(map.get("ydbhid"))){
			resultList = bundleArriveMapper.findBundleArriveList(map);
		}else{
			resultList = bundleArriveMapper.findBundleArriveListByTransportCode(map);
		}
		
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < resultList.size(); i++) {
			list.add(resultList.get(i).getYdbhid());
		}
		String transportCodes = Joiner.on("','").join(list);
		List<BundleArriveEntity> findTzfhztList = bundleArriveMapper.findTzfhztByTransportCodes("'"+transportCodes+"'");
		for (BundleArriveEntity entity:resultList) {
			for (int i = 0; i < findTzfhztList.size(); i++) {
				BundleArriveEntity tzfgztEntity = findTzfhztList.get(i);
				if(tzfgztEntity.getYdbhid().equals(entity.getYdbhid())) {
					entity.setTzfhzt(tzfgztEntity.getTzfhzt());
				}
			}
		}
		
		for (int i = 0; i < resultList.size(); i++) {
			resultList.get(i).setTijiCalc(resultList.get(i).tijiCalc());
			resultList.get(i).setZhlCalc(resultList.get(i).zhlCalc());
		}
		return resultList;
	}

	/**
	 * 查询录入上门取货指令
	 */
	@SuppressWarnings("unused")
	@Override
	public JsonResult searchPickFormHomeOrder(Map<String, Object> map, int openType)
			throws ParameterException, BusinessException {
		JsonResult jsonResult = new JsonResult();
		boolean canModifyGoodsDetail = true;
		boolean canModifyDispatchDetail = true;
		boolean canModifyOrderEntity = true;
		boolean canModifySaveButton = true;
		boolean canModifyAddButton = true;
		boolean canModifyPcidInput = true;
		List<DispatchCarPickGoodsDetail> goodsList = null;
		List<DispatchCarPickGoodsDetailSecond> dispatchList = null;
		DispatchCarPickGoods orderEntity = null;
		String modifyReason = null;
		String gsid = (String) map.get("gsid");
		String pcidStr = (String) map.get("pcid");
		Integer pcid = Integer.parseInt(pcidStr);
		String grid = (String) map.get("grid");
		Integer rightNum = 0;
		if (pcid == null) {
			rightNum = rightMapper.getRightNum(AuthorticationConstant.PC_SHIN, grid);//收货派车
			Assert.trueIsWrong("rightNum", rightNum==null || rightNum == 0, "您没有收货派车的权限，请与系统管理员申请开通！");
			if (rightNum == 1) {
				canModifyOrderEntity = false;
				canModifyGoodsDetail = false;
				canModifyDispatchDetail = false;
				canModifySaveButton = false;
				canModifyAddButton = false;
			} else if (rightNum == 2 || rightNum == 3) {
				canModifyOrderEntity = true;
				canModifyGoodsDetail = true;
				canModifyDispatchDetail = false;
			}
		} else {
			rightNum = rightMapper.getRightNum(AuthorticationConstant.PC_DDPC, grid);//调度派车
			Assert.trueIsWrong("rightNum", rightNum==null || rightNum == 0, "你没有调度派车的权限，请与系统管理员申请开通！");
			if (rightNum == 1) {
				canModifyOrderEntity = false;
				canModifyGoodsDetail = false;
				canModifyDispatchDetail = false;
				canModifySaveButton = false;
			} else if (rightNum == 2 || rightNum == 3) {
				canModifyOrderEntity = true;
				canModifyGoodsDetail = false;
				canModifyDispatchDetail = true;
			}
			//canModifyAddButton = false;
			//canModifyGoodsDetail = false;
			//canModifyPcidInput = false;
			orderEntity = bundleArriveMapper.findTcarinEntity(gsid, pcid);// 取货派车表信息查询（主键查询只有一条）
			Assert.trueIsWrong("bundleArriveMapper.countTcarinById(map)", orderEntity == null,
					"未查询到派车单号：" + map.get("pcid") + "，公司编号：" + map.get("gsid") + "对应的派车信息");
			// dw_1.object.orderno_t.visible = true
			// dw_1.object.orderno.visible = true
			goodsList = bundleArriveMapper.findTcarinGoodsList(gsid, pcid);// 对应的货物明细查询
			Assert.trueIsWrong("goodsList", goodsList == null, "条件出错，无法查询到货物明细");
			
			//检测派车单是否操作员已经签收，尚未签收的派车单可以修改
			if (orderEntity.getPcyes()!=null && orderEntity.getPcyes() > 0) {
				dispatchList = bundleArriveMapper.findTCarInDetail2(gsid, pcid);
				Assert.trueIsWrong("dispatchList", dispatchList == null, "条件出错，无法查询到调度明细");
				String tjyhsr = orderEntity.getTjyhsrgrid();
				if (!StringUtils.isEmpty(tjyhsr)) {
					if (openType == 0) {
						canModifyOrderEntity = false;
						canModifyGoodsDetail = false;
						canModifyDispatchDetail = false;
						//canModifySaveButton = false;
						//canModifyAddButton = false;
						//canModifyPcidInput = false;
					} else if (openType == 1) {
						canModifyOrderEntity = false;
						canModifyGoodsDetail = false;
						canModifyDispatchDetail = false;
					}
					modifyReason = "已经签收，不能修改！";
				} else {// 只允许修改调度派车的内容，不允许修改其它与派车无关的内容
					//orderEntity除了ddqsd、ddqhd、ddcom都不能编辑
					if (openType == 1) {
						canModifyOrderEntity = true;
						canModifyGoodsDetail = false;
						canModifyDispatchDetail = true;
						jsonResult.put("canModifyColumnDdpctime", false);
						jsonResult.put("canModifyColumnDdpcdd", false);
						jsonResult.put("canModifyColumnDdqsd", true);
						jsonResult.put("canModifyColumnDdqhd", true);
						jsonResult.put("canModifyColumnDdcomm", true);
						jsonResult.put("canModifyColumnPcmdd", true);
						jsonResult.put("canModifyColumnTctc", true);
						//modifyReason = "不能修改与派车无关的内容";
					}else if(openType == 0) {
						canModifyOrderEntity = false;
						canModifyGoodsDetail = false;
						canModifyDispatchDetail = true;
						jsonResult.put("canModifyColumnDdpctime", false);
						jsonResult.put("canModifyColumnDdpcdd", false);
						jsonResult.put("canModifyColumnDdqsd", true);
						jsonResult.put("canModifyColumnDdqhd", true);
						jsonResult.put("canModifyColumnDdcomm", true);
						jsonResult.put("canModifyColumnPcmdd", false);
					}
				}
			} else if (openType == 1) {
				canModifyOrderEntity = false;
				canModifyGoodsDetail = false;
				canModifyDispatchDetail = true;
				jsonResult.put("canModifyColumnDdpcdd", false);
				jsonResult.put("canModifyColumnDdpctime", false);
				jsonResult.put("canModifyColumnTctc", true);
				jsonResult.put("canModifyColumnDdqsd", true);
				jsonResult.put("canModifyColumnDdqhd", true);
				jsonResult.put("canModifyColumnDdcomm", true);
			} else if (openType == 0) {
				canModifyOrderEntity = true;
				canModifyGoodsDetail = true;
				canModifyDispatchDetail = false;
				jsonResult.put("canModifyColumnDdpcdd", false);
				jsonResult.put("canModifyColumnDdpctime", false);
				jsonResult.put("canModifyColumnDdqsd", false);
				jsonResult.put("canModifyColumnDdqhd", false);
				jsonResult.put("canModifyColumnDdcomm", false);
			}
		}
		if(canModifyDispatchDetail) {
			orderEntity.setDdpcdd(map.get("userName").toString());
		}
		jsonResult.put("resultCode", "200");
		if(modifyReason!=null) {
			jsonResult.put("resultCode", "300");
		}
		jsonResult.put("reason", "查询成功");
		jsonResult.put("orderEntity", orderEntity);
		jsonResult.put("goodsList", goodsList);
		jsonResult.put("dispatchList", dispatchList);
		jsonResult.put("canModifyOrderEntity", canModifyOrderEntity);
		jsonResult.put("canModifyGoodsDetail", canModifyGoodsDetail);
		jsonResult.put("canModifyDispatchDetail", canModifyDispatchDetail);
		jsonResult.put("canModifySaveButton", canModifySaveButton);
		jsonResult.put("canModifyAddButton", canModifyAddButton);
		jsonResult.put("canModifyPcidInput", canModifyPcidInput);
		jsonResult.put("modifyReason", modifyReason);
		return jsonResult;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, timeout = 300)
	@Override
	public JsonResult savePickFormHomeOrder(BundlePickHome pickHome,UserModel userModel) throws ParameterException, BusinessException {
		boolean isSaveSucc = false;
		JsonResult jsonResult = new JsonResult();
		DispatchCarPickGoods orderEntity =  pickHome.getOrderEntity();
		String gsid = userModel.getCompanyCode();
		Integer pcid = orderEntity.getId();
		orderEntity.setGsid(gsid);
		SimpleDateFormat sbf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(!StringUtils.isEmpty(orderEntity.getDdpctime())){
			try {
				orderEntity.setDdpctimeDate(sbf.parse(orderEntity.getDdpctime()+":00"));
			} catch (ParseException e) {
				throw new ParameterException("调度派车时间转换字符串为时间格式错误，ddpctime="+orderEntity.getDdpctime());
			}
		}else {
			orderEntity.setDdpctimeDate(null);
		}
		if(!StringUtils.isEmpty(orderEntity.getJhqhtime())){
			try {
				orderEntity.setJhqhtimeDate(sbf.parse(orderEntity.getJhqhtime()+":00"));
			} catch (ParseException e) {
				throw new ParameterException("预计取货时间转换字符串为时间格式错误，jhqhtime="+orderEntity.getJhqhtime());
			}
		}else {
			orderEntity.setJhqhtimeDate(null);
		}	
		List<DispatchCarPickGoodsDetail> goodsList = pickHome.getGoodsList();
		// 新增
		if (null==pcid) {
			checkSaveData(orderEntity);// 若校验不通过直接抛出到上层
			int idnew = bundleArriveMapper.findNewIdForTcarIn(orderEntity.getGsid());
			Assert.trueIsWrong("DispatchCarPickGoodsDetail", goodsList==null || goodsList.isEmpty(), "请输入托运货物明细！");
			for (int i = 0; i < goodsList.size(); i++) {
				String pinming = goodsList.get(i).getHwmc();
				Integer jianshu = goodsList.get(i).getJianshu();
				Double tiji = goodsList.get(i).getTj();
				Double zhongliang = goodsList.get(i).getZl();
				Assert.trueIsWrong("DispatchCarPickGoodsDetail", StringUtil.isEmpty(pinming),"货物明细中品名必填！");
				//重量，体积，件数任意一个不为空
				if((ObjectUtils.isEmpty(jianshu) || jianshu == 0) && ObjectUtils.isEmpty(tiji) && ObjectUtils.isEmpty(zhongliang)){
					Assert.trueIsWrong("DispatchCarPickGoodsDetail",true,"货物明细中的件数、体积、重量至少填一个 ！");
				}
				if(ObjectUtils.isEmpty(jianshu) || jianshu == 0) {
          goodsList.get(i).setJianshu(0);
        }
				if(ObjectUtils.isEmpty(tiji)) {
          goodsList.get(i).setTj(0.00);
        }
				if(ObjectUtils.isEmpty(zhongliang)) {
          goodsList.get(i).setZl(0.00);
        }
				goodsList.get(i).setGsid(gsid);
			}
			pickHome.setVer("2.0");
			pickHome.getOrderEntity().setXdtime(sbf.format(new Date()));
			pickHome.setIdnew(idnew);
			orderEntity.setId(idnew);
			orderEntity.setPcyes(0);
			bundleArriveMapper.saveTCarInEntity(orderEntity);
			for (int i = 0; i < goodsList.size(); i++) {
				goodsList.get(i).setGsid(gsid);
				goodsList.get(i).setId(idnew);
				bundleArriveMapper.saveGoodsDetail(goodsList.get(i));
			}
			isSaveSucc = true;
		} else {// 修改下单或派车、修改派车
			Boolean canModifyGoodsDetail = pickHome.getCanModifyGoodsDetail();
			Boolean canModifyDispatchDetail = pickHome.getCanModifyDispatchDetail();
			// 界面上可修改的范围
			if (canModifyGoodsDetail && !canModifyDispatchDetail) { // 修改下单
				Assert.trueIsWrong("DispatchCarPickGoodsDetail", goodsList==null || goodsList.isEmpty(), "请输入托运货物明细！");
				checkSaveData(orderEntity);// 若校验不通过直接抛出到上层
				bundleArriveMapper.updateTCarInEntity(orderEntity);
				for (int i = 0; i < goodsList.size(); i++) {
					String pinming = goodsList.get(i).getHwmc();
					Integer jianshu = goodsList.get(i).getJianshu();
					Double tiji = goodsList.get(i).getTj();
					Double zhongliang = goodsList.get(i).getZl();
					Integer detailId = goodsList.get(i).getDetailId();
					Assert.trueIsWrong("DispatchCarPickGoodsDetail", StringUtil.isEmpty(pinming),"货物明细中品名必填！");
					//重量，体积，件数任意一个不为空
					if((ObjectUtils.isEmpty(jianshu) || jianshu == 0) && ObjectUtils.isEmpty(tiji) && ObjectUtils.isEmpty(zhongliang)){
						Assert.trueIsWrong("DispatchCarPickGoodsDetail",true,"货物明细中的件数、体积、重量至少填一个 ！");
					}
					if(ObjectUtils.isEmpty(jianshu) || jianshu == 0) {
            goodsList.get(i).setJianshu(0);
          }
					if(ObjectUtils.isEmpty(tiji)) {
            goodsList.get(i).setTj(0.00);
          }
					if(ObjectUtils.isEmpty(zhongliang)) {
            goodsList.get(i).setZl(0.00);
          }
					goodsList.get(i).setGsid(gsid);
					goodsList.get(i).setId(pcid);
					if(detailId!=null) {
						bundleArriveMapper.updateGoodsDetail(goodsList.get(i));
					}else {
						bundleArriveMapper.saveGoodsDetail(goodsList.get(i));
					}
				}
				isSaveSucc = true;
			} else if (!canModifyGoodsDetail && canModifyDispatchDetail) {
				// 调度派车、修改派车
				Assert.trueIsWrong("ddqsd", StringUtil.isEmpty(orderEntity.getDdqsd()), "请输入调度派车的起始地！");
				Assert.trueIsWrong("ddqhd", StringUtil.isEmpty(orderEntity.getDdqhd()), "请输入调度派车的取货地！");
				Assert.trueIsWrong("pcshlc", orderEntity.getPcshlc() == null || orderEntity.getPcshlc() <= 0,
						"请选择调度派车的取货里程！");
				List<DispatchCarPickGoodsDetailSecond> detailSecondList = pickHome.getDispatchList();
				Assert.trueIsWrong("DispatchCarPickGoodsDetailSecond",
						null == detailSecondList || detailSecondList.isEmpty(), "请输入调度派车的取货司机明细！");
				//bundleArriveMapper.deleteGoodsDetailTwo(orderEntity.getGsid(),orderEntity.getId());
				for (int i = 0; i < detailSecondList.size(); i++) {
					DispatchCarPickGoodsDetailSecond dispatch = detailSecondList.get(i);
					dispatch.setGsid(orderEntity.getGsid());
					dispatch.setId(orderEntity.getId());
					if(dispatch.getDetailid()!=null){
						bundleArriveMapper.updateDispatchDetailTwo(dispatch);
					}else{
						bundleArriveMapper.saveDispatchDetailTwo(dispatch);
					}
				}
				if (orderEntity.getPcyes()== null || orderEntity.getPcyes() < 1) {
          orderEntity.setPcyes(1);// 派车
        }
				bundleArriveMapper.updateTCarIn(orderEntity);
				isSaveSucc = true;
				
				/**
				 * 添加记录异常日志信息
				 * yanxf
				 * 2018-03-13 14:26:09
				 */
				ExceptionLog exceptionLog = new ExceptionLog();
				exceptionLog.setOperatorName(userModel.getUserName());
				exceptionLog.setOperatorAccount(userModel.getAccount());
				exceptionLog.setOperatorCompany(userModel.getCompany());
				exceptionLog.setIpAddress(IPUtil.getLocalIP());
				exceptionLog.setYdbhid(orderEntity.getId().toString());
				exceptionLog.setOperatingMenu(OPERATING_MENU);
				exceptionLog.setOperatingContent("派车单号为：" + orderEntity.getId().toString() + "的取货派车记录修改成功！");
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
		}
		if (isSaveSucc) {
			jsonResult.put("reason", "保存成功");
			jsonResult.put("resultCode", "200");
			jsonResult.put("orderEntity", orderEntity);
		} else {
			jsonResult.put("reason", "不符合修改的条件！");
			jsonResult.put("resultCode", "400");
		}
		return jsonResult;
	}

	/**
	 * 校验数据是否符合业务所需条件
	 * @param canModifyDispath 
	 * @param canModifyGoods 
	 */
	public void checkSaveData(DispatchCarPickGoods transportInfo) throws ParameterException, BusinessException {
		if (StringUtils.isEmpty(transportInfo.getShhrlxdh())) {
      transportInfo.setShhrlxdh(""); // 收货人[座机电话]
    }
		if (StringUtils.isEmpty(transportInfo.getShhrdzh())) {
      transportInfo.setShhrdzh("");//// 收货人[收货地址]
    }
		//Assert.trueIsWrong("hwdaozhanWangDian is null", StringUtils.isEmpty(transportInfo.getHwdaozhanWangDian()),
		//		"请填写到站网点！");
		Assert.trueIsWrong("shhryb is null", StringUtils.isEmpty(transportInfo.getShhryb()), "请填写手机号！");
		Assert.trueIsWrong("shhryb length is not 11", transportInfo.getShhryb().length() != 11, "您输入收货人手机号位数不正确！");
		Assert.trueIsWrong("fhShengfen is null", StringUtils.isEmpty(transportInfo.getFhShengFen()), "请填写托运人省市！");// 托运人省市
		Assert.trueIsWrong("fhChengshi is null", StringUtils.isEmpty(transportInfo.getFhChengShi()), "请填写托运人市区县！");// 托运人城市
		Assert.trueIsWrong("fhr is null", StringUtils.isEmpty(transportInfo.getFhr()), "请填写托运人名称！");// 托运人名称
		Assert.trueIsWrong("shhrmch is null", StringUtils.isEmpty(transportInfo.getShhrmch()), "请填写收货人名称！");// 收货人名称
		Assert.trueIsWrong("ysfs is null", StringUtils.isEmpty(transportInfo.getYsfs()), "请选择运输方式！");// 运输方式
		Assert.trueIsWrong("hwdaozhan is null", StringUtils.isEmpty(transportInfo.getHwdaozhan()), "请填写目的地！");// 目的站
		Assert.trueIsWrong("isfd is null", ObjectUtils.isEmpty(transportInfo.getIsfd()), "请选择是否返单！");// 是否返单
		if(transportInfo.getIsfd()==1){
			Assert.trueIsWrong("fdlc is null", StringUtils.isEmpty(transportInfo.getFdlc()), "请输入返单联次！");// 返单联次
			Assert.trueIsWrong("fdsl is null", StringUtils.isEmpty(transportInfo.getFdsl()), "请输入返单数量！");// 返单数量
			Assert.trueIsWrong("fdnr is null", StringUtils.isEmpty(transportInfo.getFdnr()), "请输入返单内容！");// 返单内容
		}
		Assert.trueIsWrong("fwfs is null", transportInfo.getFwfs() == null, "请选择服务方式！");// 返单内容
		Assert.trueIsWrong("fukuanfangshi is null", null == transportInfo.getFuKuanFangShi(), "请选择付款方式！");// 付款方式
		Assert.trueIsWrong("fwfs check rules is unpass",
				(transportInfo.getFwfs() == 1 || transportInfo.getFwfs() == 2)
						&& StringUtils.isEmpty(transportInfo.getShhrdzh()),
				"此票货物是送货上门，请输入完整的收货人信息(包括电话和地址)！");//&& (StringUtils.isEmpty(transportInfo.getShhrlxdh())
		//Assert.trueIsWrong("shhrlxdh is too short", ((transportInfo.getFwfs() == 1 || transportInfo.getFwfs() == 2)
		//		&& transportInfo.getShhrlxdh().length() < 12), "此票货物是送货上门，您输入收货人座机电话小于12位，请补充完整！");

//		Double dsk = transportInfo.getDashoukuanYuan();
//		Integer fukuanfangshi = transportInfo.getFuKuanFangShi();
//		Integer isfd = transportInfo.getIsfd();
		//if (isfd==1 || fukuanfangshi >= 1 || dsk > 0) {
			Assert.trueIsWrong("dhShengfen is null", StringUtils.isEmpty(transportInfo.getDhShengfen()), "收货人的省市不能为空！");
			Assert.trueIsWrong("dhChengsi is null", StringUtils.isEmpty(transportInfo.getDhChengsi()),
					"收货人的市县区不能为空,请确认！");
			Assert.trueIsWrong("dhAddr is null", StringUtils.isEmpty(transportInfo.getDhAddr()), "收货人的街道城镇不能为空,请确认！");
		//}
//		Map<String, Object> arriveCityMap = bundleArriveMapper.findTDhuoChengsi(transportInfo.getHwdaozhan(),
//				transportInfo.getDhShengfen(), transportInfo.getDhChengsi());
//		Assert.trueIsWrong("arriveCityMap is null", arriveCityMap == null, "到货城市信息请及时维护，目的地："+transportInfo.getHwdaozhan()+",到货省份："+transportInfo.getDhShengfen()+",到货城市："+transportInfo.getDhChengsi()+"条件下无法找到对应的到货城市信息");
//		if (isfd==1)
//			Assert.trueIsWrong("is_fandan not focus receiver addr", "0".equals(arriveCityMap.get("is_fandan")),
//					"收货人的省市县区不能做返单处理,请确认！");
//		if (fukuanfangshi >= 1)
//			Assert.trueIsWrong("is_daofukuan not focus receiver addr", "0".equals(arriveCityMap.get("is_daofukuan")),
//					"收货人的省市县区不能做到站付款处理,请确认！");
//		if (dsk != null && dsk >= 1)
//			Assert.trueIsWrong("is_daisoukuan", "0".equals(arriveCityMap.get("is_daisoukuan")),
//					"收货人的省市县区不能做到代收款处理,请确认！");
	}

	@Override
	public Map<String, Object> checkSendMessage(Map<String, Object> param)
			throws ParameterException, BusinessException {
		Map<String, Object> networkPointInfo = bundleArriveMapper.findNetworkPointInfo(param);
		Assert.isNull("bundleArriveMapper.findNetworkPointInfo(param)", networkPointInfo, "没有该网点的提货地址和电话，请进行人工维护");
		Map<String, Object> hasFiwt = bundleArriveMapper.hasFiwt(param);
		Assert.trueIsWrong("bundleArriveMapper.hasFiwt(param)", hasFiwt == null || hasFiwt.get("cwpzhbh") == null,
				"没有生成财凭");
		Map<String, Object> hasReceiveMoney = bundleArriveMapper.hasReceiveMoney(hasFiwt);
		Assert.trueIsWrong("bundleArriveMapper.hasReceiveMoney(param)",
				hasReceiveMoney.get("yshzhk_b") == null || hasReceiveMoney.get("yshzhk_b").equals("1"),
				"该财凭还未款清，请继续收钱");
		Map<String, Object> waitOrderStatusMap = bundleArriveMapper.checkWaitOrderStatus(param);// 等托运人指令
		Assert.trueIsWrong("bundleArriveMapper.checkWaitOrderStatus(param)",
				waitOrderStatusMap.get("tzfhzt") != null && waitOrderStatusMap.get("tzfhzt").equals("1"),
				"等托运人指令未允许放货");
		return param;
	}

	@Override
	public List<DispatchCarPickGoods> searchDeliveryCarHandling(Map<String, Object> map) {
		String gsid = (String) map.get("gsid");
		String pcyes = (String) map.get("pcyes");
		String idObj = (String)map.get("carNum");
		String xdtimeStart = (String) map.get("xdtimeStart") + " 00:00:00";
		String xdtimeEnd = (String) map.get("xdtimeEnd") + " 23:59:59";;
		Integer pcyesInt = StringUtils.isEmpty(pcyes)?null:"1".equals(pcyes)?1:0;
		Integer id = StringUtil.isNotEmpty(idObj) ? Integer.valueOf(idObj) : null;
		List<DispatchCarPickGoods> list = bundleArriveMapper.searchDeliveryCarHandling(gsid, pcyesInt, xdtimeStart, xdtimeEnd,id);
		Collections.sort(list, new Comparator<DispatchCarPickGoods>() {
             @Override
             public int compare(DispatchCarPickGoods o1, DispatchCarPickGoods o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                try {
                     Date dt1 = format.parse(o1.getXdtime());
                     Date dt2 = format.parse(o2.getXdtime());
                    if (dt1.getTime() < dt2.getTime()) {
                       return 1;
                    } else if (dt1.getTime() > dt2.getTime()) {
                         return -1;
                    } else {
                         return 0;
                     }
                 } catch (Exception e) {
                 	e.printStackTrace();
                 }
                 return 0;
             }	
	   });
	   return list;
	}
	
	
	@Override
	public JsonResult searchPickGoodsAccount(Map<String, Object> map) throws ParameterException, BusinessException {
		JsonResult jsonResult = new JsonResult();
		jsonResult.put("T_CAR_IN_DETAIL_THREE_LIST_CAN_MODIFY", true);
		jsonResult.put("T_CAR_IN_INFO_CAN_MODIFY", true);
		jsonResult.put("T_CAR_IN_DETAIL_FIVE_LIST_CAN_MODIFY", true);
		// 检查权限
		Boolean saveButtonCanMondify = false;
		String grid = (String) map.get("grid");
		String pcidStr = (String) map.get("pcid");
		Integer pcid = Integer.parseInt(pcidStr);
		String companyCode = (String) map.get("companyCode");
		Integer rightNum = rightMapper.getRightNum(AuthorticationConstant.TJYHS, grid);
		Assert.trueIsWrong("canot access menu", rightNum == null || rightNum == 0, "你没有权限使用这个功能，请与系统管理员联系！");
		if (rightNum == 1) {
      saveButtonCanMondify = false;
    }
		// dwc_1
		// dw_1.getchild("ckbh",dwc_1)
		// dwc_1.SetSQLSelect("SELECT gs,yyb,ckmc,ckbh,beizhu,id FROM T_CKBH
		// where gs='" + "" +"'")
		// dwc_1.settransobject(gtr_SQLCB)
		// dwc_1.retrieve()
		DispatchCarPickGoods tcarPick = bundleArriveMapper.findTcarinEntity(companyCode, pcid);
		jsonResult.put("T_CAR_IN_INFO", tcarPick);
		Assert.trueIsWrong("DispatchCarPickGoods == null", tcarPick == null, "没有这个派车单号！");
		List<DispatchCarPickGoodsDetailThree> queryTCarDetailThreeList = bundleArriveMapper.queryTCarDetailThree(companyCode, pcid);
		List<DispatchCarPickGoodsDetailFive> queryTCarDetailFiveList = bundleArriveMapper.queryTCarDetailFive(companyCode, pcid);
		if (!queryTCarDetailThreeList.isEmpty() && !queryTCarDetailFiveList.isEmpty()) {
			jsonResult.put("T_CAR_IN_DETAIL_THREE_LIST", queryTCarDetailThreeList);
			jsonResult.put("T_CAR_IN_DETAIL_FIVE_LIST", queryTCarDetailFiveList);
			jsonResult.put("T_CAR_IN_DETAIL_THREE_LIST_CAN_MODIFY", true);
			jsonResult.put("T_CAR_IN_INFO_CAN_MODIFY", true);
			jsonResult.put("T_CAR_IN_DETAIL_FIVE_LIST_CAN_MODIFY", true);
		} 
//		else {
//			throw new ParameterException("未查询到符合条件的调度明细和结算明细");
//		}
		Integer pcyes = tcarPick.getPcyes();
		if (pcyes == 0) {
			throw new ParameterException("该派车单还未派车！");
		} else if (!grid.equals(tcarPick.getTjyhsrgrid()) && rightNum < 3) {
			jsonResult.put("T_CAR_IN_DETAIL_THREE_LIST_CAN_MODIFY", false);
			jsonResult.put("T_CAR_IN_INFO_CAN_MODIFY", false);
			jsonResult.put("T_CAR_IN_DETAIL_FIVE_LIST_CAN_MODIFY", false);
		}
		jsonResult.put("saveButtonCanMondify", saveButtonCanMondify);
		jsonResult.put("resultCode", "200");
		jsonResult.put("reason", "查询成功");
		return jsonResult;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={Exception.class},timeout=300)
	@Override
	public void savePickGoodsAccount(BundleAccount bundleAccount) throws ParameterException, BusinessException {
		String gsid = bundleAccount.getGsid();
		Integer pcid = bundleAccount.getTcarPick().getId();
		//String userName = bundleAccount.getUserName();
		
		List<DispatchCarPickGoodsDetailThree> queryTCarDetailThreeList = bundleAccount.getQueryTCarDetailThreeList();
		List<DispatchCarPickGoodsDetailFive> queryTCarDetailFiveList = bundleAccount.getQueryTCarDetailFiveList();
		DispatchCarPickGoods tcarPick = bundleAccount.getTcarPick();
		//tcarPick.setTjyhsrgrid(gsid);
		//tcarPick.setTjyhsr(userName);
		tcarPick.setTjyjstime(new Date());
		tcarPick.setGsid(gsid);
		for(int i=0;i<queryTCarDetailThreeList.size();i++){
			queryTCarDetailThreeList.get(i).setGsid(gsid);
			queryTCarDetailThreeList.get(i).setId(pcid);
		}
		Assert.trueIsWrong("List<DispatchCarPickGoodsDetailThree> is empty", queryTCarDetailThreeList.isEmpty(), "请输入司机明细情况！");
		Assert.trueIsWrong("List<DispatchCarPickGoodsDetailFive> is empty", queryTCarDetailFiveList.isEmpty(), "请输入统计员结算明细情况！");
		bundleArriveMapper.updateTCarInAboutAccount(tcarPick);
		bundleArriveMapper.deleteGoodsDetailThree(pcid,gsid);
		for(int i=0;i<queryTCarDetailThreeList.size();i++){
			queryTCarDetailThreeList.get(i).setId(pcid);
			queryTCarDetailThreeList.get(i).setGsid(gsid);
			bundleArriveMapper.saveGoodsDetailThree(queryTCarDetailThreeList.get(i));
		}
		bundleArriveMapper.deleteGoodsDetailFive(pcid,gsid);
		for(int i=0;i<queryTCarDetailFiveList.size();i++){
			queryTCarDetailFiveList.get(i).setId(pcid);
			queryTCarDetailFiveList.get(i).setGsid(gsid);
			bundleArriveMapper.saveGoodsDetailFive(queryTCarDetailFiveList.get(i));
		}
	}

	@Override
	public JsonResult getT_Car_InForPrint(RequestJsonEntity entity) throws BusinessException,ParameterException{
		String id = entity.getString("id");
		String gsid = entity.getString("gsid");
		Assert.notNull("id", id, "派车单号不能为空");
		Assert.notNull("gsid", gsid, "公司的id不能为空");
		List<HashMap<String, Object>> list = bundleArriveMapper.getT_Car_InByIdAndGsId(id,gsid);
		JsonResult result = JsonResult.getConveyResult("200", "查询成功");
		result.put("list", CommonDateUtil.FormatDate(list));
		return result;
	}
}
