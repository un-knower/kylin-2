/**
 * kylin-transport-api
 */
package com.ycgwl.kylin.transport.service.api;

import com.alibaba.fastjson.JSONObject;
import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.entity.RequestJsonEntity;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.dto.*;
import com.ycgwl.kylin.transport.entity.*;
import com.ycgwl.kylin.transport.entity.adjunct.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 附加数据
 * @author <a href="mailto:110686@ycgwl.com">ding xuefeng</a>
 * @createDate 2017-05-03 17:28:03
 */
public interface AdjunctSomethingService {
	
	/**
	 * 查询到站公司信息
	 * @return
	 */
	public Collection<Company> listStationCompanys();
	/**
	 * 根据公司名称查询公司信息
	 * @param companyName 公司名称
	 * @return
	 */
	public Company getCompanyByName(String companyName);
	/**
	 * 根据员工编号获取员工基本信息
	 * @param number 员工编号
	 * @return
	 */
	public Employee getEmployeeByNumber(String number);
	
	/**
	 * 根据公司名称,客户名称或者客户编号模糊查询客户信息
	 * @param companyName
	 * @param something 客户编号 / 客户名称
	 * @return
	 */
	public Collection<Customer> listCustomerByCompanyName(String companyName, String something);
	
	/**
	 *  根据公司名称、客户名称和客户编号查询客户信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年10月26日
	 * @param companyName
	 * @param something
	 * @return
	 */
	public List<Customer> listCustomerByKhmcAndKhbm(String companyName, String khmc, String khbm);
	
	/**
	 * 根据公司名称、客户名称查询客户信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年10月26日
	 * @param companyName
	 * @param khmc
	 * @param khbm
	 * @return
	 */
	public List<Customer> listCustomerByKhmc(String companyName, String khmc);
	/**
	 * 查询所有行业类别数据
	 * @return
	 */
	public Collection<Industry> listIndustrys();
	/**
	 * 查询运输方式
	 * @return
	 */
	public Collection<ConveyWay> listConveyWays();
	
	/**
	 * 新增运单等待发货信息
	 * @param releaseWaiting
	 */
	public void insertReleaseWaiting(ReleaseWaiting releaseWaiting);
	/**
	 * 根据外线名称模糊查询外线信息
	 * @param foreignName
	 * @return
	 */
	public Collection<ForeignRoute> listForeignRouteByName(String foreignName);
	/**
	 * 根据外线名称查询外线信息
	 * @param foreignName
	 * @return
	 */
	public ForeignRoute getForeignRouteByName(String foreignName);
	/**
	 * 根据车牌号模糊车辆信息
	 * @param number
	 * @return
	 */
	public Collection<VehicleInfo> listVehicleInfoByNumber(String number);
	/**
	 * 根据车牌号获取车辆信息
	 * @param number
	 * @return
	 */
	public VehicleInfo getVehicleInfoByNumber(String number);
	/**
	 * 通过运单编号来获取货物的运输状态
	 * @param ydbhid
	 * @return
	 */
	public String getYsfsFromHwydByYdbhid(String ydbhid);
	
	/**
	 *  通过运单编号来获取运单等待发货信息
	 * @param ydbhid
	 * @return
	 */
	public ReleaseWaiting getReleaseWaitingByYdbhid(String ydbhid);
	
	
	
	/**
	  * @Description: 根据公司名称和输入的内容模糊查询站到列表
	  * @param companyname	公司名称
	  * @param something	输入框输入的内容
	  * @return
	  * @exception
	  */
	public List<String> listDaoZhanByCompany(String daozhan,String gs);
	/**
	  * @Description: 判断运单是否做过签收
	  * @param ydbhid
	  * @exception
	 */
	public Integer isReceivedByYdbhid(String ydbhid);
	
	/**
	 * 
	  * @Description: 模糊查询到站网点
	  * @param daozhan
	  * @param latticePoint
	  * @return
	  * @exception
	 */
	JsonResult getLatticePoint(String daozhan);
	/**
	 * 
	 * TODO 装载的发运网点
	 */
	JsonResult beginplaceNet();
	List<String> listTransferstation(String transfer);
	List<String> listTransferPoint(String transfer, String transferpoint);
	Boolean isReceived(String[] ydbhids);
	/**
	 * 
	  * @Description: 查找其他成本
	  * @param chxh
	  * @param zhchrq
	  * @return
	  * @exception
	 */
	BigDecimal getElseCost(String chxh, Date zhchrq);
	/**
	  * @Description: 生成默认运单号
	  * @param companyCode
	  * @param grid
	  * @return
	  * @exception
	 */
	String createYdbhid(String companyCode, String grid);
	/**
	  * @Description: 查询公司附近的仓库
	  * @param company
	  * @return
	  * @exception
	 */
	List<StoreHouse> getStoreHouseBycompany(String company);
	/**
	  * @Description: 运单录入的时候匹配到货网点
	  * @param daozhan
	  * @param arriveNetWork
	  * @return
	  * @exception
	  */
	public List<String> getArriveNetWork(String daozhan,String arriveNetWork);
	/**
	 * 查询客户的详细信息
	 * @param khbm
	 * @param fhdwmch
	 */
	Customer checkKhmessage(String khbm, String fhdwmch,String company)throws ParameterException;
	/**
	 * 查询叉车单价、轻货装卸费单价、重货装卸费单价、保率
	 * @param khbm
	 * @param fhdwmch
	 */
	public FinanceStandardPrice findFinanceStandardPriceByGs(String company);
	/**
	 * 查看运单是否存在提货单或送货派车单
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017年11月28日
	 * @param ydbhid
	 * @return
	 */
	public Integer checkuserdelrec(String ydbhid);
	/**
	 * 删除分离库存表的数据
	 * <p>
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017年11月29日
	 * @param ydbhid
	 */
	public void deleteFenLiKucunByYdbhid(String ydbhid);
	
	/**查找库存表的数据 */
	KucunEntity queryKucun(String ydbhid,Integer ydxzh);
	/** 查找分离库存表的数据 
	 * @param orderDetails */
	List<FenliKucunEntity> queryFenliKucunEntity(String ydbhid,Integer ydxzh,String gs);
	
	Collection<Company> queryStationCompany();
	/**
	 * 更新库存表数据
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017-12-05 13:27:13
	 * @param orderDetail
	 */
	@SuppressWarnings("rawtypes")
	void updateKucun(Map orderDetail);
	@SuppressWarnings("rawtypes")
	void updateFenLiKucun(Map orderDetail);
	
	List<KucunEntity> queryKucunByYdbhid(String ydbhid);
	List<FenliKucunEntity> queryFenliKucunByYdbhid(String ydbhid,String company);
	/**
	 * 撤销到货操作恢复库存
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017-12-18 13:16:34
	 * @param receipt
	 */
	void recoverGoodArriveFenLiKucun(BundleReceipt receipt);
	/**获取成本号*/
	String getFinaceCertifyNoByYdbhid(String ydbhid);
	/** 撤销装载的时候恢复原有的库存数量 */
	void plusKucun(BundleReceipt bundleReceipt);
	/** 撤销装载的时候恢复原有的分离库存数量 */
	void plusFenliKucun(BundleReceipt bundleReceipt);
	
    void plusFenliKucunByXuhao(BundleReceipt receipt, Integer xuhao);
	
	/**
	 * 根据到站查询到站网点
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月19日
	 * @param daozhan
	 * @return
	 */
	List<String> getArriveNetWorkList(String daozhan);
	
	void updateIncome_HByInsNo(RequestJsonEntity entity);
	
	/**
	 * 运单录入的时候匹配到货网点
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月18日
	 * @param daozhan
	 * @param arriveNetWork
	 * @return
	 */
	public JsonResult getArriveNetWork(String daozhan);
	
	/**
	 * 查询收费项目
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月20日
	 * @return
	 */
	List<ChargingProjects> listChargingProjects();
	
	/**
	 * 根据公司查询客户信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月20日
	 * @param gs
	 * @return
	 */
	List<Customer> listCustomerByGs(String gs);
	
	public void deleteGoodArriveFenLiKucun(BundleReceipt receipt);
	/**
	 * 查询客户标签
	 * @param wayBillNum 运单号
	 * @return List<CustomerLabelVo>
	 */
	public JSONObject findCustomerLabel(String wayBillNum);
	/**
	 * 是否是超级权限
	 * @return
	 */
	public boolean isSuperAuthentor(String account);

	
	/**
	 * 货云记录 查询
	 * @param account
	 * @param freightRecordDto
	 * @return
	 */
	public JsonResult findFreightRecord(String account, FreightRecordDto freightRecordDto);
	
	/**
	 * 货云记录 录入
	 * @param account
	 * @param freightRecordInputDto
	 * @return
	 */
	public JsonResult saveFreightRecord(String account, FreightRecordInputDto freightRecordInputDto);
	
	/**
	 * 添加意见
	 * @param account
	 * @param updateFreightRecordDto
	 * @return
	 */
	public JsonResult updateFreightRecord(String account, UpdateFreightRecordDto updateFreightRecordDto);
	

	/**
	 * 查询等托运人指令发货是否勾选
	 * @return
	 */
	public boolean isReleaseWaiting(String ydbhid);
	
	/**
	 * 通过序号来删除分理库存
	 * @param xuhao
	 */
	public void deleteGoodArriveFenLiKucunByXuhao(Integer xuhao);
	
	/**
	 * 货运记录 录入查询
	 * @param account
	 * @param string 
	 * @param freightRecordSerachDto
	 * @return
	 */
	public JsonResult saveFreightRecordselect(String username,String account, String company, FreightRecordSerachDto freightRecordSerachDto);
	
	/**
	 * 货运记录查询详情
	 * @param freightRecordDetailSerachDto
	 * @return
	 */
	public JsonResult selectFreightRecordDetail(FreightRecordDetailSerachDto freightRecordDetailSerachDto);
	
	/**
	 * 添加意见
	 * @param freightRecordInputDto
	 * @return
	 */
	public JsonResult addComments(FreightRecordInputDto freightRecordInputDto);
	List<String> getAllDaozhan(String companyName);
	
}
