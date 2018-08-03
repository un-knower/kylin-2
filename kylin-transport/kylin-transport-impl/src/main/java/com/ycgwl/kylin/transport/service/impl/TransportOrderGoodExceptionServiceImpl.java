package com.ycgwl.kylin.transport.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ycgwl.kylin.entity.RequestJsonEntity;
import com.ycgwl.kylin.transport.entity.TransportOrderGoodException;
import com.ycgwl.kylin.transport.entity.TransportOrderGoodExceptionResult;
import com.ycgwl.kylin.transport.persistent.TransportOrderExceptionMapper;
import com.ycgwl.kylin.transport.service.api.ITransportOrderGoodExceptionService;
import com.ycgwl.kylin.util.Assert;
import com.ycgwl.kylin.util.DateUtils;
import com.ycgwl.kylin.util.json.JsonUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("kylin.transport.dubbo.local.transportOrderGoodExceptionService")
public class TransportOrderGoodExceptionServiceImpl implements ITransportOrderGoodExceptionService {
	
	@Resource
	TransportOrderExceptionMapper transportOrderExceptionMapper;

	@Override
	public List<TransportOrderGoodExceptionResult> getExceptionInformation(TransportOrderGoodExceptionResult transportOrderGoodExceptionResult) {
		List<TransportOrderGoodExceptionResult> resultList = transportOrderExceptionMapper.getExceptionInformation(transportOrderGoodExceptionResult);	
		return resultList;
	}

	@Override
	public void saveExceptionInformation(RequestJsonEntity requestJsonEntity) {
		
		List<TransportOrderGoodException> detailsList = new ArrayList<TransportOrderGoodException>();
		
		//获取map中运单信息
		String ydbhid = requestJsonEntity.getString("ydbhid");
		String company = requestJsonEntity.getString("company");
		String jiaoJie = requestJsonEntity.getString("jiaoJie");
		String jieShou = requestJsonEntity.getString("jieShou");
		String faZhan = requestJsonEntity.getString("faZhan");
		String daoZhan = requestJsonEntity.getString("daoZhan");
		String customerNo = requestJsonEntity.getString("customerNo");
		String chxh = requestJsonEntity.getString("chxh");
		String driveName = requestJsonEntity.getString("driveName");
		String driveTele = requestJsonEntity.getString("driveTele");
		String hjbh = requestJsonEntity.getString("hjbh");
		String scene = requestJsonEntity.getString("scene");
		String happenDate = requestJsonEntity.getString("happenDate");
		
		Assert.notNull("hjbh", hjbh, "环节不能为空");
		Assert.notNull("scene", scene, "发生地点不能为空");
		Assert.notNull("happenDate", happenDate, "发生日期不能为空");
		
		String resultInfo = requestJsonEntity.getString("resultInfo");
		
		//遍历明细集合
		List<JSONObject> conver = JsonUtils.converList(resultInfo);
		for (JSONObject jsonObject : conver) {
			TransportOrderGoodException transportOrderGoodException = new TransportOrderGoodException();
			transportOrderGoodException.setYdbhid(ydbhid);
			transportOrderGoodException.setCompany(company);
			transportOrderGoodException.setJiaoJie(jiaoJie);
			transportOrderGoodException.setJieShou(jieShou);
			transportOrderGoodException.setFaZhan(faZhan);
			transportOrderGoodException.setDaoZhan(daoZhan);
			transportOrderGoodException.setCustomerNo(customerNo);
			transportOrderGoodException.setChxh(chxh);
			transportOrderGoodException.setDriveName(driveName);
			transportOrderGoodException.setDriveTele(driveTele);
			transportOrderGoodException.setHjbh(hjbh);
			transportOrderGoodException.setScene(scene);
			transportOrderGoodException.setHappenDate(DateUtils.getDate(happenDate));
			
			//添加明细
			transportOrderGoodException.setPinMing(jsonObject.getString("pinMing"));
			transportOrderGoodException.setPacking(jsonObject.getString("packing"));
			if(jsonObject.getString("jianShu") == null){
				transportOrderGoodException.setJianShu(0);
			}else{
				transportOrderGoodException.setJianShu(Integer.parseInt(jsonObject.getString("jianShu")));
			}	
			if(jsonObject.getString("ds") == null){
				transportOrderGoodException.setDs(0);
			}else{
				transportOrderGoodException.setDs(Integer.parseInt(jsonObject.getString("ds")));
			}
			if(jsonObject.getString("pl") == null){
				transportOrderGoodException.setPl(0);
			}else{
				transportOrderGoodException.setPl(Integer.parseInt(jsonObject.getString("pl")));
			}
			if(jsonObject.getString("ssh") == null){
				transportOrderGoodException.setSsh(0);
			}else{
				transportOrderGoodException.setSsh(Integer.parseInt(jsonObject.getString("ssh")));
			}
			if(jsonObject.getString("wr") == null){
				transportOrderGoodException.setWr(0);
			}else{
				transportOrderGoodException.setWr(Integer.parseInt(jsonObject.getString("wr")));
			}
			if(jsonObject.getString("frozenhurt") == null){
				transportOrderGoodException.setFrozenhurt(0);
			}else{
				transportOrderGoodException.setFrozenhurt(Integer.parseInt(jsonObject.getString("frozenhurt")));
			}
			if(jsonObject.getString("flmb") == null){
				transportOrderGoodException.setFlmb(0);
			}else{
				transportOrderGoodException.setFlmb(Integer.parseInt(jsonObject.getString("flmb")));
			}
			if(jsonObject.getString("delayed") == null){
				transportOrderGoodException.setDelayed(0);
			}else{
				transportOrderGoodException.setDelayed(Integer.parseInt(jsonObject.getString("delayed")));
			}
			if(jsonObject.getString("chuanhuo") == null){
				transportOrderGoodException.setChuanhuo(0);
			}else{
				transportOrderGoodException.setChuanhuo(Integer.parseInt(jsonObject.getString("chuanhuo")));
			}
			if(jsonObject.getString("other") == null){
				transportOrderGoodException.setOther(0);
			}else{
				transportOrderGoodException.setOther(Integer.parseInt(jsonObject.getString("other")));
			}
			if(jsonObject.getString("excepquantity") == null){
				transportOrderGoodException.setExcepquantity(0);
			}else{
				transportOrderGoodException.setExcepquantity(Integer.parseInt(jsonObject.getString("excepquantity")));
			}
			if(jsonObject.getString("salvage") == null){
				transportOrderGoodException.setSalvage(0);
			}else{
				transportOrderGoodException.setSalvage(Integer.parseInt(jsonObject.getString("salvage")));
			}
			Assert.notNull("bz", jsonObject.getString("bz"), "异常描述不能为空");
			transportOrderGoodException.setBz(jsonObject.getString("bz"));
			
			//添加到集合中
			detailsList.add(transportOrderGoodException);
		}
		
		//持久化货物异常日志信息
		transportOrderExceptionMapper.saveExceptionInformation(detailsList);
	}

	
	

}
