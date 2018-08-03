package com.ycgwl.kylin.transport.service.impl;

import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.entity.BundleReceipt;
import com.ycgwl.kylin.transport.entity.FenliKucunEntity;
import com.ycgwl.kylin.transport.entity.TransportOrder;
import com.ycgwl.kylin.transport.entity.TransportOrderDetail;
import com.ycgwl.kylin.transport.entity.adjunct.StoreHouse;
import com.ycgwl.kylin.transport.persistent.*;
import com.ycgwl.kylin.transport.service.api.ArriveStationService;
import com.ycgwl.kylin.transport.service.api.BundleReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
@Service("kylin.transport.dubbo.local.arriveStationService")
public class ArriveStationServiceImpl implements ArriveStationService{
	
	@Autowired
	private BundleReceiptService bundleReceiptService;
	
	@Resource
	private ArriveStationMapper arriveStationMapper;
	
	@Autowired
	private AdjunctSomethingMapper adjunctSomethingMapper;
	
	@Autowired
	private TransportOrderMapper transportOrderMapper;
	
	@Autowired
	private TransportSignRecordMapper transportSignRecordMapper;
	
	@Autowired
	private BundleReceiptMapper bundleReceiptMapper;
	
	public void checkArriveTime(BundleReceipt bundle,Date newDhsjDate) throws ParameterException{
		boolean isSameCity = false;//是否同城
		int sameCityTime = 2 * 1000 * 3600;//2小时
		int differentCityTime = 8 * 1000 * 3600;//8小时
		if(bundle.getFazhan().equals(bundle.getDaozhan())) {
			isSameCity = true;
		}else {
			isSameCity = false;
		}
		if(isSameCity) {
			if(newDhsjDate.getTime()-bundle.getZhchrq().getTime() < sameCityTime) {
				throw new ParameterException("同城到货时间要发生在装车时间2小时以后，装车时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(bundle.getZhchrq()));
			}
		}else {
			if(newDhsjDate.getTime()-bundle.getZhchrq().getTime() < differentCityTime) {
				throw new ParameterException("不同城到货时间要发生在装车时间8小时以后，装车时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(bundle.getZhchrq()));
			}
		}
		//校验到货时间
		if(new Date().getTime() < newDhsjDate.getTime()) {
			throw new ParameterException("到货时间不能大于等于系统时间");
		}
		
	}
	
	/**
	 * 检查是否完成装载（该运单的货物明细的重量、体积、件数和装载清单里相同细则号的合计的重量、体积、件数是否一样）
	 */
	@Override
	public boolean hasBundleFinish(String ydbhid) {
		//按装车时间倒序获取装载信息,相同的装载类型
		List<BundleReceipt> bundleList = bundleReceiptMapper.queryLastBundleReceiptByYdbhid(ydbhid);
		List<TransportOrderDetail> detailList = transportOrderMapper.getTransportOrderDetail(ydbhid);
		//先看最后一次装载是否是全装
		List<BundleReceipt> lastBundleList = bundleReceiptService.getLastBundleReceiptByYdbhid(ydbhid);
		boolean isSplitBundle = true;//是否是分装
		for (BundleReceipt last : lastBundleList) {
			for (TransportOrderDetail detail : detailList) {
				if(last.getYdxzh().equals(detail.getYdxzh())) {
					BigDecimal detailZhl = new BigDecimal(detail.getZhl().toString());
					Integer detailJianshu = detail.getJianshu();
					BigDecimal detailTiji = new BigDecimal(detail.getTiji().toString());
					if(detailZhl.compareTo(last.getZhl())>0 || detailJianshu>last.getJianshu() || detailTiji.compareTo(last.getTiji())>0) {
						isSplitBundle = false;//如果某次装载的量比货物明细的少，就是分装
						break;
					}
				}
			}
		}
		
		//如果不是全装，则检查是否已经分次装完
		int count = 0;
		if(!isSplitBundle) {
			for (TransportOrderDetail detail : detailList) {
				boolean isFinish = false;
				BigDecimal detailZhl = new BigDecimal(detail.getZhl().toString());
				Integer detailJianshu = detail.getJianshu();
				BigDecimal detailTiji = new BigDecimal(detail.getTiji().toString());
				BigDecimal bundleZhl = new BigDecimal(0);
				Integer bundleJianshu = 0;
				BigDecimal bundleTiji = new BigDecimal(0);
				Integer ydxzh = detail.getYdxzh();
				for(int i=0;i<bundleList.size();i++) {
					BundleReceipt receipt = bundleList.get(i);
					if(receipt.getYdxzh().equals(ydxzh)) {
						bundleZhl = bundleZhl.add(receipt.getZhl());
						bundleJianshu += receipt.getJianshu();
						bundleTiji = bundleTiji.add(receipt.getTiji());
						if(bundleZhl.compareTo(detailZhl)==0 && bundleJianshu.equals(detailJianshu) && bundleTiji.compareTo(detailTiji)==0) {//相等
							//如果累计的重量、体积、件数和货物明细的符合
							isFinish = true;
							break;
						}else if(bundleZhl.compareTo(detailZhl)==1 || bundleJianshu>detailJianshu || bundleTiji.compareTo(detailTiji)==1) {
							break;
						}
					}
				}
				if(isFinish) {
					count+=1;
				}
			}
		}
		
		if(isSplitBundle) {
			return isSplitBundle;
		}else{
			return detailList.size()==count;
		}
	}
	
	
	/**
	 * 检查是否完成装载（该运单的货物明细的重量、体积、件数和装载清单里相同细则号的合计的重量、体积、件数是否一样）
	 */
	@Override
	public List<BundleReceipt> getLastBundleReceipt(String ydbhid) {
		//按装车时间倒序获取装载信息,相同的装载类型
		List<BundleReceipt> bundleList = bundleReceiptMapper.queryLastBundleReceiptByYdbhid(ydbhid);
		List<TransportOrderDetail> detailList = transportOrderMapper.getTransportOrderDetail(ydbhid);
		//先看最后一次装载是否是全装
		List<BundleReceipt> lastBundleList = bundleReceiptService.getLastBundleReceiptByYdbhid(ydbhid);
		List<BundleReceipt> getLastBundleReceipt = new ArrayList<BundleReceipt>();
		
		boolean isSplitBundle = true;//是否是分装
		for (BundleReceipt last : lastBundleList) {
			for (TransportOrderDetail detail : detailList) {
				if(last.getYdxzh().equals(detail.getYdxzh())) {
					BigDecimal detailZhl = new BigDecimal(detail.getZhl().toString());
					Integer detailJianshu = detail.getJianshu();
					BigDecimal detailTiji = new BigDecimal(detail.getTiji().toString());
					if(detailZhl.compareTo(last.getZhl())>0 || detailJianshu>last.getJianshu() || detailTiji.compareTo(last.getTiji())>0) {
						isSplitBundle = false;//如果某次装载的量比货物明细的少，就是分装
						break;
					}else if(detailZhl.compareTo(last.getZhl())==0 && detailJianshu.equals(last.getJianshu()) &&  detailTiji.compareTo(last.getTiji())==0){
						getLastBundleReceipt.add(last);
					}
				}
			}
		}
		
		//收集最后一次装载记录
		if(detailList.size()==getLastBundleReceipt.size()) {
			return getLastBundleReceipt;
		}
		
		//如果不是全装，则检查是否已经分次装完
		List<BundleReceipt> getLastBundle= new ArrayList<BundleReceipt>();
		int count = 0;
		if(!isSplitBundle) {
			for (TransportOrderDetail detail : detailList) {
				BigDecimal detailZhl = new BigDecimal(detail.getZhl().toString());
				Integer detailJianshu = detail.getJianshu();
				BigDecimal detailTiji = new BigDecimal(detail.getTiji().toString());
				BigDecimal bundleZhl = new BigDecimal(0);
				Integer bundleJianshu = 0;
				BigDecimal bundleTiji = new BigDecimal(0);
				Integer ydxzh = detail.getYdxzh();
				List<BundleReceipt> list = new ArrayList<BundleReceipt>();
				for(int i=0;i<bundleList.size();i++) {
					BundleReceipt receipt = bundleList.get(i);
					if(receipt.getYdxzh().equals(ydxzh)) {
						list.add(receipt);
						bundleZhl = bundleZhl.add(receipt.getZhl());
						bundleJianshu += receipt.getJianshu();
						bundleTiji = bundleTiji.add(receipt.getTiji());
						if(bundleZhl.compareTo(detailZhl)==0 && bundleJianshu.equals(detailJianshu) && bundleTiji.compareTo(detailTiji)==0) {//相等
							count+=1;
							//如果累计的重量、体积、件数和货物明细的符合
							for (BundleReceipt bund : list) {
								getLastBundle.add(bund);
							}
							list.clear();
							break;
						}else if(bundleZhl.compareTo(detailZhl)==1 || bundleJianshu>detailJianshu || bundleTiji.compareTo(detailTiji)==1) {
							list.clear();
							break;
						}
					}
				}
			}
		}
		
		if(detailList.size()==count) {
			return getLastBundle;
		}else {
			return new ArrayList<BundleReceipt>();
		}
	}

	/**
	 * 自动到货
	 * @param saveMap：gs（公司）,ydbhid（运单号）,grid（工号） 
	 */
	@Override
	@Transactional
	public void autoArriveStation(HashMap<String,Object> saveMap) throws ParameterException{
		String dhsj = (String) saveMap.get("dhsj");
		List<BundleReceipt> lastBundleList = bundleReceiptService.queryBundleReceiptByYdbhidDesc(saveMap.get("ydbhid").toString());
		Date dhsjDate = null;
		try {
			dhsjDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dhsj);
		} catch (ParseException e1) {
			throw new ParameterException("到货时间转换错误");
		}
		//未做过到货需要校验到货时间
		for(BundleReceipt bundle:lastBundleList) {
			if(bundle.getYdzh()==0) {
				try {
					checkArriveTime(bundle,dhsjDate);
				}catch (ParameterException e) {
					throw new ParameterException(e.getTipMessage());
				}
			}
		}
		
//		saveMap.put("dhsj",dhsj);//到货时间
		List<StoreHouse> storeHouseList = adjunctSomethingMapper.getStoreHouseBycompany(saveMap.get("gs").toString());
		if (storeHouseList == null || storeHouseList.isEmpty()) {
			saveMap.put("storeHouse", "");
			saveMap.put("ckbh", "");
		} else {
			saveMap.put("storeHouse", storeHouseList.get(0));
			saveMap.put("ckbh", storeHouseList.get(0).getCkbh());
		}
		//自动到货操作 
		TransportOrder order = transportOrderMapper.getTransportOrderByYdbhid(saveMap.get("ydbhid").toString());
		for(BundleReceipt bundle:lastBundleList) {
			if(bundle.getYdzh()==0) {
				bundle.setAutoArrive(1);//自动到货
				saveMap.put("bundleReceipt", bundle);
				saveMap.put("order", order);
				arriveStationMapper.updateArriveStateByXuhao(saveMap.get("grid").toString(),dhsj,1,bundle.getXuhao(),bundle.getAutoArrive());
				if(bundle.getNewbill()!=null && bundle.getNewbill()==1) {
					Integer count = arriveStationMapper.countFenliKucunByXuhao(bundle.getXuhao());
					if(count>0) {
						arriveStationMapper.updateFenLiKuCunByXuhao(saveMap);
					}else {
						arriveStationMapper.insertTFenLiKuCun(saveMap);
					}
				}else {
					Integer num = arriveStationMapper.countFenliKucun(saveMap.get("ydbhid").toString(),saveMap.get("gs").toString(),bundle.getYdxzh());
					if (num > 0) {
						try {
							arriveStationMapper.updateFenLiKuCun(saveMap);
						} catch (Exception e) {
							throw new ParameterException("更新分理库存失败");
						}
						
					}else {
						saveMap.put("gs", order.getDaozhan());
						arriveStationMapper.insertTFenLiKuCun(saveMap);
					}
				}
			}
		}
	}

	@Override
	public void undoSignArriveStation(Integer deliveryNumber,String ydbhid) throws ParameterException,BusinessException{
		List<BundleReceipt> bundleList = getLastBundleReceipt(ydbhid);//获取最后一次装载的记录
		Map<String,String> fazhanDaozhan = transportOrderMapper.getTransportFazhanDaozhanByYdbhid(ydbhid);
		String fazhan = fazhanDaozhan.get("fazhan");
		String daozhan = fazhanDaozhan.get("daozhan");
		if(bundleList.isEmpty()) {
			throw new BusinessException("查询不到该运单的最后一次装载，无法撤销到货");
		}
		for(BundleReceipt bundle:bundleList) {
			if(bundle.getYdzh()==1) {//已到站状态的才需要撤销到货
				boolean isTransfer = bundle.getiType() == 0 && "中转".equals(bundle.getTransferFlag());//干线且中转
				if(isTransfer) {
					continue;
				}
				String bundleDaozhan = bundle.getDaozhan();//装载的到站是运单的发站或到站
				boolean isWaybillFazhanOrDaozhan = (fazhan.equals(bundleDaozhan) || daozhan.equals(bundleDaozhan))?true:false;
				if(isWaybillFazhanOrDaozhan) {
					if(bundle.getNewbill()!=null && bundle.getNewbill()==1) {
						if(bundle.getAutoArrive()==1) {//已到货，自动到货的，不是干线中转的
							arriveStationMapper.deleteGoodArriveStatesByXuhao(bundle.getXuhao());
							arriveStationMapper.deleteGoodArriveFenLiKucunByXuhao(bundle.getXuhao());
						}
					}else {
						arriveStationMapper.deleteGoodArriveStatesByXuhao(bundle.getXuhao());
						//查询到站
						List<FenliKucunEntity> fenlikucunList = adjunctSomethingMapper.queryFenliKucunEntity(ydbhid, bundle.getYdxzh(), bundle.getDaozhan());
						String company =  bundle.getDaozhan();
						if(fenlikucunList.isEmpty()) {
							fenlikucunList = adjunctSomethingMapper.queryFenliKucunEntity(ydbhid, bundle.getYdxzh(), fazhan);
							company = fazhan;
						}
						String zhchrq = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(bundle.getZhchrq());
						if(!fenlikucunList.isEmpty()) {
							arriveStationMapper.recoverFenlikucunByZhchrq(bundle,company,zhchrq);
						}
						Integer fenliCount = arriveStationMapper.countFenliKucunIsNotZero(ydbhid, company, bundle.getYdxzh());
						//查看分理库存是否为0，为0就删除
						if(fenliCount==0) {
							bundle.setDaozhan(company);
							adjunctSomethingMapper.deleteGoodArriveFenLiKucunByZhchrq(bundle,zhchrq);
						}
					}
				}
			}
		}
	}
}
