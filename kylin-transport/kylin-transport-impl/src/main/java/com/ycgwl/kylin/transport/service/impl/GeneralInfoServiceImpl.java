package com.ycgwl.kylin.transport.service.impl;

import com.ycgwl.kylin.transport.entity.*;
import com.ycgwl.kylin.transport.persistent.GeneralInfoMapper;
import com.ycgwl.kylin.transport.service.api.GeneralInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("kylin.transport.dubbo.local.generalInfoService")
public class GeneralInfoServiceImpl implements GeneralInfoService{
	@Resource
	private GeneralInfoMapper generalInfoMapper;

	@Override
	public List<BundleArriveMileage> pickGoodsMileage() {
		return generalInfoMapper.pickGoodsMileage();
	}

	@Override
	public List<BundleArriveMileage> pickGoodsAddress(String companyName) {
		return generalInfoMapper.pickGoodsAddress(companyName);
	}

	@Override
	public List<TransportBasicData> queryBasicInfoByName(String cnName) {
		return generalInfoMapper.queryBasicInfoByName(cnName);
	}

	@Override
	public List<Driver> queryDriverByGsAndBm(Integer zt, String gs, String bm) {
		return generalInfoMapper.queryDriverByGsAndBm(zt, gs, bm);
	}

	@Override
	public List<CarOutBaseTc> queryCarOutBaseTc() {
		return generalInfoMapper.queryCarOutBaseTc();
	}

	@Override
	public List<CarOutBaseKh> querCarOutBaseKh() {
		return generalInfoMapper.querCarOutBaseKh();
	}

	@Override
	public List<TransportBasicData> queryBasicInfoByParentName(String parentname) {
		return generalInfoMapper.queryBasicInfoByParentName(parentname);
	}

	@Override
	public List<TransportBasicData> queryAllBasicInfo() {
		return generalInfoMapper.queryAllBasicInfo();
	}

	@Override
	public List<CompanyInfo> destinationStation() {
		return generalInfoMapper.destinationStation();
	}

	@Override
	public List<DestinationNetPoint> destinationPointStation(String companyCode) {
		return generalInfoMapper.destinationPointStation(companyCode);
	}

	@Override
	public List<DestinationNetPoint> destinationPointStationByConpanyName(String conpanyName) {
		return generalInfoMapper.destinationPointStationByConpanyName(conpanyName);
	}
	
}
