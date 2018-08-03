package com.ycgwl.kylin.transport.persistent;

import com.ycgwl.kylin.entity.RequestJsonEntity;
import com.ycgwl.kylin.transport.dto.*;
import com.ycgwl.kylin.transport.entity.*;
import com.ycgwl.kylin.transport.entity.adjunct.*;
import com.ycgwl.kylin.transport.vo.CustomerLabelVo;
import com.ycgwl.kylin.transport.vo.FreightRecordVo;
import com.ycgwl.kylin.transport.vo.LoadingListVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.*;
/**
 * TODO Add description
 * <p>
 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017-12-01 15:11:52
 */
@Mapper
public interface AdjunctSomethingMapper {
	
	@Select("SELECT DISTINCT DAOZHAN FROM XIANLU WHERE GS = #{companyName}")
	List<String> getAllDaozhan(@Param("companyName") String companyName);


	/**
	 * 模糊查询中转站
	 * @param transfer  中转站
	 * @return
	 */
	List<String> listTransferstation(String transfer);

	/**
	 * 模糊查询中转网点
	 * @param transfer 中转站
	 * @param transferpoint   中转网店
	 * @return
	 */
	List<String> listTransferPoint(@Param("transfer") String transfer,@Param("transferpoint") String transferpoint);

	/**
	 * 批量查询是否已经签收
	 * @param ydbhids
	 * @return
	 */
	Integer isReceived(@Param("ydbhids")String[] ydbhids);

	/**
	 * 生成成本号
	 * @param chxh
	 * @param zhchrq
	 * @param fazhan
	 * @return
	 */
	String getInsNo(@Param("chxh") String chxh, @Param("zhchrq") Date zhchrq, @Param("fazhan") String fazhan);

	/**
	 * 生成成本号
	 * @return
	 */
	void genincomecostno(Map<String,String> map);

	/**
	 * 插入成本表
	 * @param bundleReceipt
	 * @param insNo
	 */
	void insertIncomeCost(@Param("bundleReceipt")BundleReceipt bundleReceipt,@Param("yshhm")String yshhm ,@Param("insNo") String insNo);

	/**
	 * 根据成本号查询成本序号
	 * @param insNo
	 */
	Integer queryInsXh(String insNo);

	/**
	 *  网成本D表中插入数据
	 * @param incomeEntities
	 */
	void insertTIncomeCostD(@Param("incomeEntities") List<IncomeEntity> incomeEntities);

	/**
	 *
	 * @param ydbhids
	 * @return
	 */
	List<IncomeEntity> getvIncomeCost(Map<String,Object> map);


	/**
	 * 查询到站公司信息
	 * @return
	 */
	public Collection<Company> queryStationCompanys(); 
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
	 * 根据公司名称查询客户信息
	 * @param companyName
	 * @param something 客户名称或者客户编号
	 * @return
	 */
	public Collection<Customer> queryCustomerByCompanyName(@Param("companyName") String companyName, @Param("something") String something);

	/**
	 * 根据公司名称、客户名称及客户编码查询客户信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年10月26日
	 * @param companyName
	 * @param something
	 * @return
	 */
	public List<Customer> queryCustomerByKhmcAndKhbm(@Param("companyName") String companyName, @Param("khmc") String khmc,@Param("khbm") String khbm);

	/**
	 * 根据公司名称、客户名称查询客户信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年10月26日
	 * @param companyName
	 * @param khmc
	 * @return
	 */
	public List<Customer> queryCustomerByKhmc(@Param("companyName") String companyName, @Param("khmc") String khmc);

	/**
	 * 查询所有行业类别数据
	 * @return
	 */
	public Collection<Industry> queryIndustrys();
	/**
	 * 查询运输方式
	 * @return
	 */
	public Collection<ConveyWay> queryConveyWays();

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
	public Collection<ForeignRoute> queryForeignRouteByName(String foreignName);
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
	public Collection<VehicleInfo> queryVehicleInfoByNumber(String number);
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
	 * 通过运单编号来获取运单等待发货信息
	 * @param ydbhid
	 * @return
	 */
	public ReleaseWaiting getReleaseWaitingByYdbhid(String ydbhid);
	/**
	 * 自动生成车牌号
	 * @return
	 */
	public String buildVehicle();


	/**
	 * @Description: 模糊匹配到站列表
	 * @param companyname
	 * @return
	 * @exception
	 */
	public List<String> listDaoZhanByCompany(@Param("daozhan")String daozhan,@Param("gs")String gs);
	/**
	 * 
	 * @Description: 判断运单是否已经签收
	 * @param ydbhid
	 * @return
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
	public List<String> getLatticePoint(@Param("daozhan")String daozhan);
	@Select("SELECT DISTINCT mdd FROM sys_shlc_parm_pcdjf2")
	List<String> beginplaceNet();

	BigDecimal getElseCost(@Param("chxh")String chxh,@Param("zhchrq") Date zhchrq);

	/**
	 * 更新库存表
	 * @Description: 一句话描述做了什么
	 * @param ydbhids
	 * @exception
	 */
	void updateKucun(@Param("ydbhids")String[] ydbhids);

	/**
	 * @Description: 生成一个默认的运单号
	 * @param map
	 * @return
	 * @exception
	 */
	void createYdbhid(Map<String, String> map);
	/**
	 * @Description: 查询公司附近的仓库
	 * @param company
	 * @return
	 * @exception
	 */
	List<StoreHouse> getStoreHouseBycompany(String company);
	
	/**
	 * @Description: 查询公司附近的仓库
	 * @param company
	 * @return
	 * @exception
	 */
	StoreHouse getStoreHouse(@Param("company")String company,@Param("storeHouse")String storeHouse);

	Integer queryFenliKucun(@Param("ydbhid")String ydbhid, @Param("gs")String gs,@Param("ydxzh")Short ydxzh);

	/**
	 * @Description: 更新分离库存表
	 * @param paramMap
	 * @exception
	 */
	void updateFenLiKuCun(Map<String, Object> paramMap);
	
	/**
	 * @Description: 更新分离库存表
	 * @param paramMap
	 * @exception
	 */
	void updateFenLiKuCunByXuhao(Map<String, Object> paramMap);
	
	/**
	 * @Description: 往分离库存表中插入数据
	 * @param map
	 * @exception
	 */
	void insertTFenLiKuCun(Map<String, Object> map);

	void updateTQSOK(String ydbhid);
	
	/**
	 * 运单录入的时候匹配到货网点
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月18日
	 * @param daozhan
	 * @param arriveNetWork
	 * @return
	 */
	List<String> getArriveNetWork(@Param("daozhan")String daozhan);

	/**
	 * 模糊匹配到货网点
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月18日
	 * @param daozhan
	 * @param arriveNetWork
	 * @return
	 */
	List<String> getArriveNetWorkEdit(@Param("daozhan")String daozhan,@Param("arriveNetWork")String arriveNetWork);

	@Select("SELECT cchfl.zhjxzyf,zhxfl.qhzhxfdj,zhzhxfdj.zhzhxfdj,bxfl.baolu as premiumRate,bxfl.gs FROM bxfl INNER JOIN zhzhxfdj ON bxfl.gs = zhzhxfdj.gs INNER JOIN zhxfl ON bxfl.gs = zhxfl.gs INNER JOIN cchfl ON bxfl.gs = cchfl.gs and cchfl.gs = #{gs}")
	FinanceStandardPrice financeStandardPrice(String gs);

	void checkuserdelrec(@SuppressWarnings("rawtypes") Map map);

	void deleteFenLiKucunByYdbhid(String ydbhid);

	/**
	 * 查询库存数据
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017-12-01 15:11:57
	 * @param ydbhid
	 * @param ydxzh
	 * @return
	 */
	KucunEntity queryKucun(@Param("ydbhid")String ydbhid, @Param("ydxzh")Integer ydxzh);
	/**
	 * 查找分离库存表数据
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017-12-01 15:26:35
	 * @param ydbhid
	 * @param ydxzh
	 * @param gs
	 * @return
	 */
	List<FenliKucunEntity> queryFenliKucunEntity(@Param("ydbhid")String ydbhid, @Param("ydxzh") Integer ydxzh,  @Param("gs")String gs);

	Collection<Company> queryStationCompany();
	/**更新库存表数据*/
	void updateKucunData(@SuppressWarnings("rawtypes") Map orderDetail);
	/**更新分离库存表数据*/
	void updateFenLiKucunData(@SuppressWarnings("rawtypes") Map orderDetail);
	/**新增一条装载的成本*/
	void insertIncome_HCostv2(@SuppressWarnings("rawtypes") HashMap map);

	void insertIncome_DCostv2(@SuppressWarnings("rawtypes") HashMap map);

	List<KucunEntity> queryKucunByYdbhid(String ydbhid);

	List<FenliKucunEntity> queryFenliKucunByYdbhid(@Param("ydbhid")String ydbhid, @Param("company")String company);
	/**
	 * 撤销到货时对分离库存表的还原
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017-12-18 13:17:16
	 * @param receipt
	 */
	void recoverGoodArriveFenLiKucun(@Param("receipt")BundleReceipt receipt);

	String getFinaceCertifyNoByYdbhid(String ydbhid);

	void plusKucun(@Param("receipt")BundleReceipt receipt);

	void plusFenliKucun(@Param("receipt")BundleReceipt receipt);
	
	void plusFenliKucunByXuhao(@Param("receipt")BundleReceipt receipt,@Param("xuhao")Integer xuhao);
	
	/**
	 * 根据到站查询到站网点
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月19日
	 * @param daozhan
	 * @return
	 */
	List<String> getArriveNetWorkList(@Param("daozhan")String daozhan);

	@Select("select xuhao from hczzqd_source where ydbhid=#{ydbhid} and ydxzh = #{ydxzh} "
			+ " and chxh=#{chxh} and fchrq=#{fchrq}")
	int getXuhaoByTransportCode(@SuppressWarnings("rawtypes") Map map);

	void updateIncome_HByInsNo(RequestJsonEntity entity);
	
	/**
	 * 查询收费项目
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月20日
	 * @return
	 */
	List<ChargingProjects> selectChargingProjects();
	
	/**
	 * 根据公司查询客户信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月20日
	 * @param gs
	 * @return
	 */
	List<Customer> selectCustomerByGs(String gs);
	
	/**
	 * 撤销到货时对分离库存表的还原（提货）
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年3月19日
	 * @param receipt
	 */
	void recoverGoodArriveFenLiKucuns(@Param("receipt")BundleReceipt receipt);
	
	void recoverGoodArriveFenLiKucunsByXuhao(@Param("xuhao")Integer xuhao);
	
	void batchRepealGoodArriveList(List<BundleReceipt> list);

	@Delete("delete t_fenlikucun where gs =#{receipt.daozhan} and ydbhid = #{receipt.ydbhid} and ydxzh = #{receipt.ydxzh}")
	void deleteGoodArriveFenLiKucun(@Param("receipt")BundleReceipt receipt);

	/**
	 * 查询客户标签
	 * @param wayBillNum 运单号
	 * @return List<CustomerLabelVo>
	 */
	List<CustomerLabelVo> findCustomerLabel(@Param("wayBillNum")String wayBillNum);

	/**
	 * 货云记录 查询
	 * @param freightRecordDto
	 * @return
	 */
	List<FreightRecordVo> findFreightRecord(FreightRecordDto freightRecordDto);

	/**
	 * 货云记录 录入
	 * @param freightRecordInputDto
	 */
	int saveFreightRecord(FreightRecordInputDto freightRecordInputDto);

	/**
	 * 货云记录详情 录入
	 * @param freightRecordDetailDto
	 */
	int saveFreightRecordDetail(FreightRecordDetailDto freightRecordDetailDto);

	/**
	 * 修改货云记录详情
	 * @param updateFreightRecordDto
	 * @return 
	 */
	int updateFreightRecord(UpdateFreightRecordDto updateFreightRecordDto);

	FiwtResult findFiwtResultByYdid(String wayBillNum);

	List<String> findFkfsh(FiwtResult fiwtResult);

	/**
	 * 插入日志
	 * @param account
	 * @param id
	 */
	void saveHyjlLog(@Param("account")String account, @Param("id")int id);

	/**
	 * 添加货运记录
	 * @param reorderDto 
	 * @return
	 */
	int saveOrdertrace(ReorderDto reorderDto);

	/**
	 * 查询等拖放货表是否有数据
	 * @param waybillNum
	 * @return
	 */
	ReleaseWaiting selectDdfhByYdbhid(String waybillNum);

	Integer countFenlikucunByXuhao(Integer xuhao);

	@Delete("delete t_fenlikucun where xuhao=#{xuhao}")
	void deleteGoodArriveFenLiKucunByXuhao(@Param("xuhao")Integer xuhao);

	Integer countFenliKucunEntity(@Param("ydbhid")String ydbhid, @Param("ydxzh")int ydxzh, @Param("gs")String gs);
   
	
	/**
	 * 根据运单号，公司
	 * 查询分理库存数据
	 * @param ydbhid
	 * @param company
	 */
	@Select("select count(1) from t_fenlikucun where ydbhid = #{ydbhid}  and gs=#{company}")
	public Integer queryFenliKucunCount(@Param("ydbhid") String ydbhid, @Param("company") String company);
	/**
	 * 货运记录 录入查询
	 * @param freightRecordSerachDto
	 * @return
	 */
	List<LoadingListVo> saveFreightRecordselect(FreightRecordSerachDto freightRecordSerachDto);

	/**
	 * 根据车牌号和运单号查询装载清单
	 * @param train
	 * @param fchrq 
	 * @param wayBillNum
	 * @return
	 */
	List<BundleReceipt> selectBundReceiptByCxhnAndYdh(@Param("waybillNum")String waybillNum, @Param("train")String train, @Param("startFchrq")String startFchrq,@Param("endFchrq")String endFchrq);

	/**
	 * 查询货运记录头信息
	 * @param id
	 * @return
	 */
	FreightRecord selectFreightRecordById(String id);

	/**
	 * 查询货运记录详细信息
	 * @param id
	 * @return
	 */
	List<FreightRecordDetail> selectFreightRecordDetailById(@Param("id")String id);
	
	@Delete("delete t_fenlikucun where gs =#{receipt.daozhan} and ydbhid = #{receipt.ydbhid} and ydxzh = #{receipt.ydxzh} and convert(varchar(20),dhsj,120) > #{zhchrq}")
	void deleteGoodArriveFenLiKucunByZhchrq(@Param("receipt")BundleReceipt receipt,@Param("zhchrq")String zhchrq);

	/**
	 * 查询详细信息和头信息
	 * @param waybillNum
	 * @return
	 */
	FreightRecordDetail selectFreightRecordDetailByWaybillNum(@Param("waybillNum")String waybillNum);

	/**
	 * 根据车牌号查询头信息
	 * @param train
	 * @return
	 */
	FreightRecord selectFreightRecordByChxh(@Param("train")String train);

	/**
	 * 删除异常信息头表
	 * @param id
	 */
	void deleteFreightRecord(int id);

	/**
	 * 删除异常详细信息表
	 * @param id
	 */
	void deleteFreightRecordDetail(int id);

	/**
	 * 添加意见
	 * @param freightRecordInputDto
	 * @return
	 */
	int addComments(FreightRecordInputDto freightRecordInputDto);

	
	/**
	 * 
	 * 查询客户基本信息: <br>
	 * 根据登录公司,业务接单号
	 * @author zdl
	 * @version [V1.0, 2018年5月16日]
	 * @param Gs
	 * @param ywjdhId
	 * @return
	 */
	CustomerResult customerInfoByYwdhIdandGs(@Param("company") String company, @Param("ywjdhId") int ywjdhId);
	
	/**
	 * 
	 * 根据客户的登录公司,客户编码: <br>
	 * 查询客户表
	 * @author zdl
	 * @version [V1.0, 2018年5月16日]
	 * @param Gs
	 * @return
	 */
//	Customer queryCustomerByGs(CustomerInfoDto customerInfoDto);
	
    /**
     * 
     * 录入客户基本信息: <br>
     * @author zdl
     * @version [V1.0, 2018年5月16日]
     * @param 传入参数 --customerInfoDto
     * @return int
     */
	int insertCustomerInfo(CustomerInfoDto customerInfoDto);

    
	/**
	 * 
	 * 查询客户基本信息: <br>
	 * 根据登录公司,客户编码
	 * @author zdl
	 * @version [V1.0, 2018年5月16日]
	 * @param Gs
	 * @param Khbm
	 * @return
	 */
	List<CustomerResult>  queryCustomerByGsandKhbm(@Param("Gs") String Gs, @Param("Khbm") String Khbm);
	
	
	/**
	 * 
	 * 查询客户基本信息: <br>
	 * 根据登录公司,客户名称
	 * @author zdl
	 * @version [V1.0, 2018年5月16日]
	 * @param Gs
	 * @param khmc
	 * @return
	 */
	List<CustomerResult>  queryCustomerByGsandKhMC(@Param("Gs") String Gs, @Param("khmc") String khmc);
	

	
	   /**
     * 
     * 更新客户信息: <br>
     * @author zdl
     * @version [V1.0, 2018年5月16日]
     * @param 传入参数 --customerResult
     * @return int
     */
	int updateCustomerInfo(CustomerResult customerResult);
    
	/**
	 * 获取customer表的max(id)
	 * @author zdl
     * @version [V1.0, 2018年5月16日]
     * @param
	 */
	int getMaxId(CustomerInfoDto customerPriceDto);
	
	
	CustomerResult queryCustomerByGs(String company);
	
	/**
	 * 查询公司权限
	 * 根据公司
	 * @author zdl
     * @version [V1.0, 2018年5月16日]
     * @param
	 */
    Integer RigthNum(CustomerInfoDto customerPriceDto);

	/**
	 * 通过运单编号和操作类型为货物入库  删除货云记录
	 * @param ydbhid    运单编号
	 * @param czType    操作类型
	 */
    void deleteFreightRecordByYdbhid(@Param("ydbhid") String ydbhid, @Param("czType")String czType);

	/**
	 * 通过运单号更新库存
	 * @param ydbhid   运单编号
	 */
	void updateKucunByYdbhid(String ydbhid);
	
	/**
	 * 通过运单号删除成本明细
	 * @param ydbhid   运单编号
	 */
	void deleteIncomeCost(@Param("ydbhid") String ydbhid);
	
	
	
	
}
