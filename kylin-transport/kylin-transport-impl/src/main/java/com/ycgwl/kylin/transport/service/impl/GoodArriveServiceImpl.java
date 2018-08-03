package com.ycgwl.kylin.transport.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.entity.RequestJsonEntity;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.entity.BundleReceipt;
import com.ycgwl.kylin.transport.entity.CarOutResult;
import com.ycgwl.kylin.transport.entity.TransportOrder;
import com.ycgwl.kylin.transport.entity.adjunct.StoreHouse;
import com.ycgwl.kylin.transport.persistent.*;
import com.ycgwl.kylin.transport.service.api.AdjunctSomethingService;
import com.ycgwl.kylin.transport.service.api.ArriveStationService;
import com.ycgwl.kylin.transport.service.api.IGoodArriveService;
import com.ycgwl.kylin.transport.service.api.ITransportOrderService;
import com.ycgwl.kylin.util.Assert;
import com.ycgwl.kylin.util.CommonDateUtil;
import com.ycgwl.kylin.util.DateUtils;
import com.ycgwl.kylin.util.json.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;


@Service("kylin.transport.dubbo.local.goodArriveService")
public class GoodArriveServiceImpl implements IGoodArriveService {

	@Resource
	private BundleReceiptMapper bundleReceiptMapper;
	@Resource
	private AdjunctSomethingMapper adjunctSomethingMapper;
	@Resource
	private TransportOrderMapper transportOrderMapper;
	@Resource
	private AdjunctSomethingService adjunctSomethingService;
	@Resource
	private ITransportOrderService transportOrderService;
	@Resource
	private ArriveStationMapper arriveStationMapper;
	@Resource
	private ArriveStationService arriveStationService;
	
	@Autowired
	private TransportSignRecordMapper transportSignRecordMapper;
	
	
	@Override
	public JsonResult searchGoodArriveByYdbhid(String ydbhid, String company,String account) {
		JsonResult jsonResult = new JsonResult();
		TransportOrder transportOrder = transportOrderService.getTransportOrderByYdbhid(ydbhid);
		if (transportOrder == null) {
			return JsonResult.getConveyResult("400", "运单号不存在");
		}
		List<BundleReceipt> bundleReceiptByYDBHID = bundleReceiptMapper.getBundleReceiptByYDBHID(new String[] { ydbhid });
		if (CollectionUtils.isEmpty(bundleReceiptByYDBHID)) 
			return JsonResult.getConveyResult("400", "该运单未进行过装载");
		List<BundleReceipt> currentBundleReceipt = bundleReceiptByYDBHID.stream().
			filter(receipt->company.equals(receipt.getDaozhan())).collect(Collectors.toList());
		if(CollectionUtils.isEmpty(currentBundleReceipt)) 
			return JsonResult.getConveyResult("400", "不存在有装载到本公司的装载信息");
		
		List<StoreHouse> storeHouseBycompany = adjunctSomethingMapper.getStoreHouseBycompany(company);
		jsonResult.put("storeHouse", storeHouseBycompany);
		jsonResult.put("resultCode", 200);
		// 存在临时的车辆是自动生成的,所以车牌号是可能查处为空的情况
		jsonResult.put("reason", "查询成功");
		jsonResult.put("message", createGoodArriveResult( currentBundleReceipt,transportOrder));
		return jsonResult;
	}
	
	@Override
	public JsonResult searchGoodArriveByChxh(String fchrq, String chxh, String company,String account) {
		// 查询这一辆车拉的所有货物
		String nextDay = DateUtils.fromDays(fchrq,1);
		if(StringUtils.isEmpty(nextDay))
			return JsonResult.getConveyResult("400", "日期格式有误");
		List<BundleReceipt> bundleReceiptList = bundleReceiptMapper.getBundleReceiptByChxh(fchrq, nextDay, chxh);
		if (bundleReceiptList == null || bundleReceiptList.isEmpty()) {
			return JsonResult.getConveyResult("400", "未查询到装载记录");
		}
		List<BundleReceipt> currentBundleReceiptList = new ArrayList<>(); 
		for (BundleReceipt bundleReceipt : bundleReceiptList) {
			if(company.equals(bundleReceipt.getDaozhan())) {
				currentBundleReceiptList.add(bundleReceipt);
			}
		}
		if(CollectionUtils.isEmpty(currentBundleReceiptList)) {
			return JsonResult.getConveyResult("400", "该车辆没有到本公司的装载清单");
		}
		// 多个运单,将运单分开
		List<StoreHouse> storeHouseBycompany = adjunctSomethingMapper.getStoreHouseBycompany(company);
		JsonResult jsonResult = JsonResult.getConveyResult("200", "查询成功");
		jsonResult.put("storeHouse", storeHouseBycompany);
		jsonResult.put("message", createGoodArriveResult(currentBundleReceiptList, null));
		return jsonResult;
	}

	
	
	/** 到货的返回封装 */
	private List<Map<String,Object>> createGoodArriveResult(List<BundleReceipt> receiptList,TransportOrder order) {
		List<Map<String,Object>> result = new ArrayList<>();
		Map<String,List<BundleReceipt>> map = new HashMap<>();
		for (BundleReceipt bundleReceipt : receiptList) {
			String chxh = bundleReceipt.getChxh();
			if(map.containsKey(chxh)) {
				map.get(chxh).add(bundleReceipt);
			}else {
				List<BundleReceipt> list = new ArrayList<>();
				list.add(bundleReceipt);
				map.put(chxh, list);
			}
		}
		Set<Entry<String, List<BundleReceipt>>> entrySet = map.entrySet();
		for (Entry<String, List<BundleReceipt>> entry : entrySet) {
			Map<String,Object> subMap = new HashMap<>();
			subMap.put("chxh", entry.getKey());
			List<BundleReceipt> value = entry.getValue();
			List<Map<String, Object>> conveyLevel = createConveyLevel(value, order);
			subMap.put("conveys", conveyLevel);
			result.add(subMap);
		}
		return result;
	}

	public List<Map<String,Object>> createConveyLevel(List<BundleReceipt> receiptList,TransportOrder order){
		List<Map<String,Object>> result = new ArrayList<>();
		Map<String,List<BundleReceipt>> conveyMap = new HashMap<>();
		for (BundleReceipt bundleReceipt : receiptList) {
			if(conveyMap.containsKey(bundleReceipt.getYdbhid())) {
				conveyMap.get(bundleReceipt.getYdbhid()).add(bundleReceipt);
			}else {
				List<BundleReceipt> list = new ArrayList<>();
				list.add(bundleReceipt);
				conveyMap.put(bundleReceipt.getYdbhid(), list);
			}
		}
		Set<Entry<String, List<BundleReceipt>>> entrySet = conveyMap.entrySet();
		for (Entry<String, List<BundleReceipt>> entry : entrySet) {
			Map<String,Object> subMap = new HashMap<>();
			List<BundleReceipt> value = entry.getValue();
			List<Map<String, Object>> detailLevel = createDetailLevel(value);
			subMap.put("detail", detailLevel);
			BundleReceipt bundleReceipt = value.get(0);
			String ydbhid = bundleReceipt.getYdbhid();
			if(order==null) {	//既然该运单有装载,那么一定有该运单的信息
				order = transportOrderMapper.getTransportOrderByYdbhid(ydbhid);
			}
			subMap.put("ydbhid", ydbhid);
			subMap.put("driverName", bundleReceipt.getDriverName());
			subMap.put("driverTel", bundleReceipt.getDriverTel());
			subMap.put("beginPlace", order.getFazhan());
			subMap.put("endPlace", order.getDaozhan());
			subMap.put("fazhan", bundleReceipt.getFazhan());
			subMap.put("daozhan", bundleReceipt.getDaozhan());
			subMap.put("iType", bundleReceipt.getiType());
			subMap.put("isArrived",bundleReceipt.getYdzh()== 1 ? "已到站" : "未到站");
			Date dhsj = bundleReceipt.getDhsj();
			String dateToString = "";
			if(dhsj!=null) {
				dateToString = CommonDateUtil.DateToString(dhsj, CommonDateUtil.PATTERN1);
			}
			subMap.put("dhsj", dateToString);
			result.add(subMap);
		}
		return result;
	}
	public List<Map<String,Object>> createDetailLevel(List<BundleReceipt> receiptList){
		List<Map<String,Object>> list = new ArrayList<>();
		for (BundleReceipt bundleReceipt : receiptList) {
			Map<String,Object> map = new HashMap<>();
			map.put("pinming", bundleReceipt.getPinming());
			map.put("jianshu", bundleReceipt.getJianshu());
			map.put("tiji", bundleReceipt.getTiji());
			map.put("zhl", bundleReceipt.getZhl());
			map.put("xuhao", bundleReceipt.getXuhao());
			list.add(map);
		}
		return list;
	}

	/** 某运单从装载列表中去除最后一次的装载信息 */
	public List<BundleReceipt> geLatelyBundleReceipt(List<BundleReceipt> bundleReceiptByYDBHID) {
		Date date = bundleReceiptByYDBHID.get(0).getFchrq();
		for (BundleReceipt bundleReceipt : bundleReceiptByYDBHID) {
			Date fchrq = bundleReceipt.getFchrq();
			if (date.getTime() <= fchrq.getTime()) {
				date = fchrq;
			}
		}
		List<BundleReceipt> receiptList = new ArrayList<>();
		for (BundleReceipt bundleReceipt : bundleReceiptByYDBHID) {
			if (bundleReceipt.getFchrq().getTime() == date.getTime()) {
				receiptList.add(bundleReceipt);
			}
		}
		return receiptList;
	}
	
	
	@SuppressWarnings({"unchecked","rawtypes"})
	@Override
	@Transactional(propagation= Propagation.REQUIRED)
	public JsonResult saveGoodArrive(RequestJsonEntity map) throws ParseException,ParameterException {
		Assert.notNull("dhsj", map.get("dhsj"), "必须填写到货时间");
		map.put("dhsj", map.getDate("dhsj"));
		String company = map.getString("gs");
		List<StoreHouse> storeHouseList = adjunctSomethingMapper.getStoreHouseBycompany(company);
		if (storeHouseList == null || storeHouseList.isEmpty()) {
			map.put("storeHouse", "");
			map.put("ckbh", "");
		} else {
			String house = map.getString("storeHouse");
			Assert.notNull("storeHouse", map.get("storeHouse"), "该到货目的地有仓库,请选择仓库");
			for (StoreHouse storeHouse : storeHouseList) {
				if(house.equals(storeHouse.getCkmc())) {
					map.put("ckbh", storeHouse.getCkbh());
					break;
				}
			}
		}
		String valueOf = String.valueOf(map.get("parama"));
		List<JSONObject> param =JsonUtils.converList(valueOf);
		for (Map chxhLevel : param) {
			chxhLevel.put("company", map.get("gs"));
			chxhLevel.put("grid", String.valueOf(map.get("grid")));
			chxhLevel.put("dhsj", map.get("dhsj"));
			
			String chxh = String.valueOf(chxhLevel.get("chxh"));
			List<String> ydbhids = Lists.newArrayList(String.valueOf(chxhLevel.get("ydbhids")));
			chxhLevel.put("ydbhids", ydbhids);
			//这些就是要进行到货的数据
			List<BundleReceipt> receiptList = null;
			if(StringUtils.isEmpty(chxh)) {		//外线零担没有车牌号
				throw new ParameterException("装载信息中没有车牌号数据,请核对该运单的装载情况");
			}else {
				//检查运单是否存在没有装载完的情况，比如分装未装载完就做到货
				for(String ydbhid :ydbhids) {
					boolean isBundleFinish = arriveStationService.hasBundleFinish(ydbhid);
					if(!isBundleFinish) {
						throw new ParameterException("运单号:"+ydbhid+"分装还未装载完毕，请装完后做到货");
					}
				}
				receiptList = bundleReceiptMapper.getDaozhanBundleReceiptByChxhAndYdbhids(chxh,ydbhids,company);
				for(BundleReceipt bundleReceipt:receiptList) {
					if(bundleReceipt.getYdzh()==0) {
						try {
							arriveStationService.checkArriveTime(bundleReceipt, map.getDate("dhsj"));
						}catch (ParameterException e) {
							throw new ParameterException(e.getTipMessage());
						}
					}
				}
				if(CollectionUtils.isEmpty(receiptList)) {
					throw new ParameterException("车牌号:"+chxh+"选中的运单号在本公司下未查询到未到货的装载清单");
				}
				bundleReceiptMapper.updateStateArrive(chxhLevel);
			}
			
			//已经按照到站公司查询的数据,有数据就进行到货操作吧
			for(BundleReceipt bundleReceipt:receiptList) {
				bundleReceipt.setAutoArrive(0);//标记为手动到货
				map.put("bundleReceipt", bundleReceipt);
				map.put("order", transportOrderMapper.getTransportOrderByYdbhid(bundleReceipt.getYdbhid()));
				
				if(bundleReceipt.getNewbill()!=null&&bundleReceipt.getNewbill()==1) {
					Integer count = arriveStationMapper.countFenliKucunByXuhao(bundleReceipt.getXuhao());
					if(count>0) {
						arriveStationMapper.updateFenLiKuCunByXuhao(map);
					}else {
						arriveStationMapper.insertTFenLiKuCun(map);
					}
				}else {
					Integer num = arriveStationMapper.countFenliKucun(bundleReceipt.getYdbhid(), company,  bundleReceipt.getYdxzh());
					if (num>0) {	//如果分离库存表中有数据那么就更改库存数据,如果没有就新插入一条
						arriveStationMapper.updateFenLiKuCun(map);
					}else {
						arriveStationMapper.insertTFenLiKuCun(map);
					}
				}
			}
		}
		return JsonResult.getConveyResult("200", "到货成功");
	}
	
	/**
	 * 手动撤销到货
	 */
	@Override
	@Transactional
	public JsonResult deleteGoodArrive(RequestJsonEntity map) throws ParameterException {
		String company = map.getString("company");
		String valueOf = map.getString("parama");
		String xuhaostr = map.getString("xuhaos").replace("[", "").replace("]", "");
		List<JSONObject> param =JsonUtils.converList(valueOf);
		List<String> xuhaos = Arrays.asList(xuhaostr.split(","));
		boolean useXuhao = true;
		if(!param.isEmpty()) {
			for(JSONObject chxhLevel:param) {
				String ydbhid = chxhLevel.getString("ydbhids");
				List<BundleReceipt> bundleList = bundleReceiptMapper.getBundleReceiptByYDBHID(new String[] {ydbhid});
				for (BundleReceipt bundleReceipt : bundleList) {
					if(bundleReceipt.getNewbill() == null || 0 == bundleReceipt.getNewbill()) {
						useXuhao = false;
						break;
					}
				}
				CarOutResult carOutResult = transportSignRecordMapper.selectCarOutByYdbhid(ydbhid);//获取派车信息
				String thqshdid = transportSignRecordMapper.selectThqsdByYdbhid(ydbhid);
				Assert.trueIsWrong("carOutResult != null && thqshdid != null", carOutResult != null && thqshdid != null, "请先撤销提货单和送货派车单，再撤销到货");
				Assert.trueIsWrong("carOutResult != null", carOutResult != null, "请先撤销送货派车单，再撤销到货");
				Assert.trueIsWrong("thqshdid != null", thqshdid != null, "请先撤销提货单，再撤销到货");
			}
		}
		
		if(useXuhao) {	//新流程走这里，使用xuhao撤销到货和分理库存（老数据的分理库存里没有记录xuhao，都是0）
			for(String xuhao:xuhaos) {
				BundleReceipt rc = bundleReceiptMapper.findBundleReceiptByXuhao(Integer.parseInt(xuhao.trim()));
				
				Integer isReceived = adjunctSomethingService.isReceivedByYdbhid(rc.getYdbhid());
				
				if(isReceived !=null && isReceived > 0){	
					throw new ParameterException("已签收运单:"+rc.getYdbhid()+"无法撤销到货");	
				}
				
				Integer count = bundleReceiptMapper.countBundleReceiptByParentXuhao(xuhao.trim(),rc.getYdbhid());
				if(count>0) {
					throw new ParameterException("请从最后一站到站开始撤销到站，然后可以撤销相应的装载");	
				}
				adjunctSomethingService.deleteGoodArriveFenLiKucunByXuhao(Integer.parseInt(xuhao.trim()));
				arriveStationMapper.deleteGoodArriveStatesByXuhao(Integer.parseInt(xuhao.trim()));
			}
		}else {//老数据走这里
			for(JSONObject chxhLevel:param) {
				String chxh = chxhLevel.getString("chxh");
				String ydbhid = chxhLevel.getString("ydbhids");
				String xuhao = chxhLevel.getString("xuhao");
				Integer isReceived = adjunctSomethingService.isReceivedByYdbhid(ydbhid);//查看签收状态
				
				if(isReceived !=null && isReceived > 0){	
					throw new ParameterException("已签收运单无法撤销到货");	
				}
				
				//通过序号撤销
				if(xuhao==null) {
					//撤销这个车牌号下的这几个运单到站是该公司下的到站情况
					List<BundleReceipt> bundleReceiptByYDBHID = bundleReceiptMapper.getBundleReceiptByYDBHID(new String[] {ydbhid});
					List<BundleReceipt> collect = bundleReceiptByYDBHID.stream()
										 .filter(receipt -> (company.equals(receipt.getDaozhan()) || (receipt.getiType() == 2 && company.equals(receipt.getFazhan()))) && chxh.equals(receipt.getChxh()) && receipt.getYdzh() == 1)
										 .collect(Collectors.toList());
					//将collect代表的装载进行撤销到站操作
					bundleReceiptMapper.recoverGoodArriveState(chxh, new String[] {ydbhid});
					//仓库的数据撤销,将到货装载更新为未到站
					collect.forEach(receipt->{
						boolean candel = false;
						for (String xh:xuhaos) {
							if((Integer.parseInt(xh.trim()) == receipt.getXuhao())) {
								candel = true;
								break;
							}	
						}
						if(candel == true) {
							if(receipt.getNewbill()!=null&&receipt.getNewbill()==1) {
								adjunctSomethingService.deleteGoodArriveFenLiKucunByXuhao(receipt.getXuhao());
							}else {
								adjunctSomethingMapper.recoverGoodArriveFenLiKucun(receipt);
								Integer fenliCount = arriveStationMapper.countFenliKucunIsNotZero(ydbhid, receipt.getDaozhan(), receipt.getYdxzh());
								//查看分理库存是否为0，为0就删除
								if(fenliCount==0) {
									adjunctSomethingMapper.deleteGoodArriveFenLiKucun(receipt);
								}
							}
						}
					});
				} else {
					Integer count = bundleReceiptMapper.countBundleReceiptByParentXuhao(xuhao,ydbhid);
					if(count>0) {
						throw new ParameterException("请从最后一次到货开始撤销");	
					}
					arriveStationMapper.deleteGoodArriveStatesByXuhao(Integer.parseInt(xuhao));
					adjunctSomethingService.deleteGoodArriveFenLiKucunByXuhao(Integer.parseInt(xuhao));
				}
			}
		}
		return JsonResult.getConveyResult("200", "操作成功");
	}
	
	/**
	 * 批量确认到货
	 * @author fusen.feng 
	 */
	@Override
	public HashMap<String, Object> batchGoodArrive(RequestJsonEntity map) throws ParameterException{
		HashMap<String, Object> mapResult = new HashMap<String, Object>();
		if (null != map ) {
			String company = map.getString("company");
			// 查询所属仓库
			StoreHouse house = adjunctSomethingMapper.getStoreHouse(company,map.getString("storeHouse"));
			if (null != house) {
				map.put("ckbh", house.getCkbh());
			} else {
				// 查询公司是否有仓库
				List<StoreHouse> storeHouseList = adjunctSomethingMapper.getStoreHouseBycompany(company);
				if (CollectionUtils.isEmpty(storeHouseList)){
					Assert.notNull("storeHouse", map.get("storeHouse"), "该到货目的地有仓库,请选择仓库");
				}else {
					map.put("ckbh", "");
				}				
			}
			if (null == map.get("dhsj")) {
				mapResult.put("resultCode", "400");
				mapResult.put("reason", "请填写到货时间");
				return mapResult;
			}
	
			//String chxh = String.valueOf(map.get("chxh"));
			//List<String> ydbhids = java.util.Arrays.asList(map.getString("ydbhids").split(","));
			String xuhaos = map.getString("xuhaos");
			String xuhaoArray[] = xuhaos.split(",");
			List<String> ydbhidList = new ArrayList<String>();
			for(String xuhao:xuhaoArray) {
				BundleReceipt receipt = bundleReceiptMapper.getBundleReceiptByXuhao(xuhao);
				if(receipt.getYdzh()==0) {
					//校验到货时间
					Date dhsj = null;
					try {
						dhsj = map.getDate("dhsj");
					} catch (ParseException e) {
						e.printStackTrace();
					}
					try {
						arriveStationService.checkArriveTime(receipt, dhsj);
					}catch (ParameterException e) {
						throw new ParameterException(e.getTipMessage());
					}
					
					if(!ydbhidList.contains(receipt.getYdbhid())) {
						ydbhidList.add(receipt.getYdbhid());
						boolean isBundleFinish = arriveStationService.hasBundleFinish(receipt.getYdbhid());
						if(!isBundleFinish) {
							throw new ParameterException("运单号:"+receipt.getYdbhid()+"分装还未装载完毕，请装完后做到货");
						}
					}
					if(StringUtils.isEmpty(receipt.getChxh())) {		//外线零担没有车牌号
						mapResult.put("resultCode", "400");
						mapResult.put("reason", "装载信息中没有车牌号数据,请核对该运单的装载情况");
						return mapResult;
					}
					map.put("gs", company);
					receipt.setAutoArrive(0);
					arriveStationMapper.updateArriveStateByXuhao(map.getString("grid"), map.getString("dhsj"), new Integer(1), Integer.parseInt(xuhao.trim()), new Integer(0));
					//新方式
					if(receipt.getNewbill()!=null && receipt.getNewbill()==1) {
						Integer count = arriveStationMapper.countFenliKucunByXuhao(receipt.getXuhao());
						map.put("bundleReceipt", receipt);
						map.put("order", transportOrderMapper.getTransportOrderByYdbhid(receipt.getYdbhid()));
						if(count>0) {
							arriveStationMapper.updateFenLiKuCunByXuhao(map);
						}else {
							arriveStationMapper.insertTFenLiKuCun(map);
						}
					}else {
						Integer num = arriveStationMapper.countFenliKucun(receipt.getYdbhid(), company,  receipt.getYdxzh());
						map.put("bundleReceipt", receipt);
						map.put("order", transportOrderMapper.getTransportOrderByYdbhid(receipt.getYdbhid()));
						if (num>0) {	//如果分离库存表中有数据那么就更改库存数据,如果没有就新插入一条
							arriveStationMapper.updateFenLiKuCun(map);
						}else {
							arriveStationMapper.insertTFenLiKuCun(map);
						}
					}
				}
			}
			
			mapResult.put("resultCode", "200");
			mapResult.put("reason", "操作成功!");
			
//			for(String ydbhid :ydbhids) {
//				if(StringUtils.isNotBlank(ydbhid)) {
//					boolean isBundleFinish = arriveStationService.hasBundleFinish(ydbhid);
//					if(!isBundleFinish) {
//						throw new ParameterException("运单号:"+ydbhid+"分装还未装载完毕，请装完后做到货");
//					}
//				}
//			}
			
//			map.put("ydbhids", ydbhids);
//			//这些就是要进行到货的数据
//			List<BundleReceipt> receiptList = null;
//			if(StringUtils.isEmpty(chxh)) {		//外线零担没有车牌号
//				mapResult.put("resultCode", "400");
//				mapResult.put("reason", "装载信息中没有车牌号数据,请核对该运单的装载情况");
//				return mapResult;
//			}else {
//				//检查运单是否存在没有装载完的情况，比如分装未装载完就做到货
//				for(String ydbhid :ydbhids) {
//					boolean isBundleFinish = arriveStationService.hasBundleFinish(ydbhid);
//					if(!isBundleFinish) {
//						throw new ParameterException("运单号:"+ydbhid+"分装还未装载完毕，请装完后做到货");
//					}
//				}
//				receiptList = bundleReceiptMapper.getDaozhanBundleReceiptByChxhAndYdbhids(chxh,ydbhids,company);
//				if(!CollectionUtils.isEmpty(receiptList)) {
//					bundleReceiptMapper.updateStateArrive(map);
//				}else {
////					mapResult.put("resultCode", "400");
////					mapResult.put("reason", "您选择的车牌号:"+chxh+"在"+company+"下已经装载到货，请选择没有到货的装载进行操作");
////					return mapResult;
//				}
//			}
//			//已经按照到站公司查询的数据,有数据就进行到货操作吧
//			for(BundleReceipt bundleReceipt:receiptList) {
//				map.put("gs", company);
//				bundleReceipt.setAutoArrive(0);
//				//新方式
//				if(bundleReceipt.getNewbill()!=null && bundleReceipt.getNewbill()==1) {
//					Integer count = arriveStationMapper.countFenliKucunByXuhao(bundleReceipt.getXuhao());
//					map.put("bundleReceipt", bundleReceipt);
//					map.put("order", transportOrderMapper.getTransportOrderByYdbhid(bundleReceipt.getYdbhid()));
//					if(count>0) {
//						arriveStationMapper.updateFenLiKuCunByXuhao(map);
//					}else {
//						arriveStationMapper.insertTFenLiKuCun(map);
//					}
//				}else {
//					Integer num = arriveStationMapper.countFenliKucun(bundleReceipt.getYdbhid(), company,  bundleReceipt.getYdxzh());
//					map.put("bundleReceipt", bundleReceipt);
//					map.put("order", transportOrderMapper.getTransportOrderByYdbhid(bundleReceipt.getYdbhid()));
//					if (num>0) {	//如果分离库存表中有数据那么就更改库存数据,如果没有就新插入一条
//						arriveStationMapper.updateFenLiKuCun(map);
//					}else {
//						arriveStationMapper.insertTFenLiKuCun(map);
//					}
//				}
//			}
			
		}
		return mapResult;
	}
	
	/**
	 * 批量撤销到货
	 * @author fusen.feng 
	 */
	@Override
	@Deprecated
	public JsonResult batchRepealGoodArrive(RequestJsonEntity map) {
		String company = map.getString("company");
		String  valueOf  = map.getString("ydbhids");
		String chxh = map.getString("chxh");
		Assert.hasText("chxh", chxh, "车牌号传递失败");
		Assert.hasText("ydbhid", valueOf, "运单号传递失败");
				
		//查看签收状态
		Integer isReceived = adjunctSomethingMapper.isReceived(valueOf.split(","));				
		if(isReceived ==null || isReceived > 0)				
			return JsonResult.getConveyResult("400", "存在已签收的运单,请核实后再操作");
		
		//撤销这个车牌号下的这几个运单到站是该公司下的到站情况
		List<BundleReceipt> bundleReceiptList = bundleReceiptMapper
				.getBundleReceiptByYDBHID(map.getString("ydbhids").split(","))
				.stream()
				.filter(receipt->receipt.getYdzh() == 1 && company.equals(receipt.getDaozhan()))
				.collect(Collectors.toList());
		
		if(CollectionUtils.isEmpty(bundleReceiptList))
			return JsonResult.getConveyResult("400", "未查询到需要进行到货的装载清单");
			bundleReceiptMapper.recoverGoodArriveState(chxh,map.getString("ydbhids").split(","));
			adjunctSomethingMapper.batchRepealGoodArriveList(bundleReceiptList);
		return JsonResult.getConveyResult("400", "操作成功!");
	}
	
}
