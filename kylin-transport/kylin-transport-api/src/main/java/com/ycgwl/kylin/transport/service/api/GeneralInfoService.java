package com.ycgwl.kylin.transport.service.api;

import com.ycgwl.kylin.transport.entity.*;

import java.util.List;

/**
 * 通用接口逻辑层
 * @author <a href="mailto:109668@ycgwl.com">lihuixia</a>
 */
public interface GeneralInfoService {
	public List<BundleArriveMileage> pickGoodsMileage();

	public List<BundleArriveMileage> pickGoodsAddress(String companyName);
	
	public List<TransportBasicData> queryBasicInfoByName(String cnName);
	
	/**
	 * 查询司机信息（根据状态公司和编码）
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月26日
	 * @param zt
	 * @param gs
	 * @param bm
	 * @return
	 */
	List<Driver> queryDriverByGsAndBm(Integer zt,String gs,String bm);
	
	/**
	 * 查询派车提成趟次
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月26日
	 * @return
	 */
	List<CarOutBaseTc> queryCarOutBaseTc();
	
	/**
	 * 查询派车考核对象
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月26日
	 * @return
	 */
	List<CarOutBaseKh> querCarOutBaseKh();
	
	public List<TransportBasicData> queryBasicInfoByParentName(String parentname);

	public List<TransportBasicData> queryAllBasicInfo();

	public List<CompanyInfo> destinationStation();

	List<DestinationNetPoint> destinationPointStation(String companyCode);

	public List<DestinationNetPoint> destinationPointStationByConpanyName(String conpanyName);
}
