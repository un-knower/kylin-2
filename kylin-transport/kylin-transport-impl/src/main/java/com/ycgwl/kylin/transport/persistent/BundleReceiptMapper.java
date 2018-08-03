/**
 * kylin-transport-impl
 * com.ycgwl.kylin.transport.persistent
 */
package com.ycgwl.kylin.transport.persistent;

import com.ycgwl.kylin.entity.RequestJsonEntity;
import com.ycgwl.kylin.transport.entity.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.*;

/**
 * @author <a href="mailto:110686@ycgwl.com">ding xuefeng</a>
 * @createDate 2017-04-27 09:21:30
 */
public interface BundleReceiptMapper {

	/**
	 * <b>Description</b> 查询最后一单装载信息
	 * <p>
	 * <b>Author</b> <a href="mailto:108252@ycgwl.com">wanyujie</a><br>
	 * <b>Date</b> 2017-09-12 21:26:03
	 * <p>
	 */
	List<BundleReceipt> queryLastBundleReceiptByYdbhid(String ydbhid);

	/**
	 * <b>Description</b> 根据运单号将所有运单细则查询出来
	 * <p>
	 * <b>Author</b> <a href="mailto:108252@ycgwl.com">wanyujie</a><br>
	 * <b>Date</b> 2017-09-12 15:02:00
	 * <p>
	 */
	Collection<TransportOrderDetail> queryTransportOrderDetailByYdbhid(String ydbhid);


	/**
	 * <b>Description</b> 
	 * <p>
	 * <b>Author</b> <a href="mailto:108252@ycgwl.com">wanyujie</a><br>
	 * <b>Date</b> 2017-09-12 15:02:00
	 * <p>
	 */
	void insertBundleReceiptList(@Param("bundleReceipts") List<BundleReceipt> bundleReceipts);

	/**
	 * <b>Description</b> 
	 * <p>
	 * <b>Author</b> <a href="mailto:108252@ycgwl.com">wanyujie</a><br>
	 * <b>Date</b> 2017-09-12 15:02:00
	 * <p>
	 */
	void inserthczzqdbeizhu(@Param("bundleReceipts")List<BundleReceipt> bundleReceiptList);
	
	@SuppressWarnings("rawtypes")
	void insertHczzqd_beizhu(Map map);

	/**
	 * countBundleReceiptByConveyKey @Description: 根据运单编号查询其是否装载 @param
	 * conveyKey 运单编号 @return 大于0表示已经装载，反之没有装载 @exception
	 */
	public Long countBundleReceiptByConveyKey(String conveyKey);


	/**
	 * 分页查询装载清单条数
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年9月19日
	 * @param bundleReceipt
	 * @return
	 */
	public Integer listBundleReceiptCounts(BundleReceiptSearch bundleReceipt);

	/**
	 * 使用存储过程来保存装载清单中的数据
	 * 
	 * @param bundleProcedure
	 */
	public void callProcedureInsert(BundleProcedure bundleProcedure);

	public List<BundleReceipt> getBundleReceipt(BundleReceipt receipt);

	/**
	 * 
	 * @Description: 按照发车时间降序排列运单的装载信息 @param ydbhid @return @exception
	 */
	public List<BundleReceipt> queryBundleReceiptByYdbhidDesc(String ydbhid);

	/**
	 * @Description:通过运单号来进行查询装载清单 
	 * @param ydbhids
	 * @return
	 * @exception
	 */
	List<BundleReceipt> getBundleReceiptByYDBHID(@Param("ydbhids")String[] ydbhids);

	/**
	 * @Description: 更新状态成到货的状态
	 * @param map
	 * @exception
	 */
	@SuppressWarnings("rawtypes")
	void updateStateArrive(Map map);

	List<BundleReceipt> getBundleReceiptByChxh(@Param("fchrq")String fchrq,@Param("nextDay") String nextDay,@Param("chxh")String chxh);

	/**
	 * 装载的保存
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017-12-04 11:16:26
	 * @param map
	 */
	@SuppressWarnings("rawtypes")
	void insertBundleReceipt(HashMap map);
	/**
	 * 更新装载的提货标志
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017-12-05 14:45:58
	 * @param ydbhid
	 * @param ydxzh
	 * @param fazhan
	 */
	void updateBundleReceiptyiti(@Param("ydbhid")String ydbhid, @Param("ydxzh")Integer ydxzh,@Param("fazhan") String fazhan);

	/**
	 * 
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017-12-11 10:21:27
	 * @param chxh
	 * @param ydbhids
	 * @param company
	 */
	List<BundleReceipt> getDaozhanBundleReceiptByChxhAndYdbhids(@Param("chxh")String chxh,@Param("ydbhids") List<String> ydbhids, @Param("company")String company);
	/** 撤销到货--恢复到货状态 */
	void recoverGoodArriveState(@Param("chxh")String chxh,@Param("ydbhids") String[] ydbhids);

	BundleReceipt getBundleReceiptByXuhao(@Param("xuhao")String xuhao);
	
	Collection<BundleReceiptHomePageEntity> searchTop10BundleReceiptList(BundleReceiptSearch receiptSearch);


	List<BundleReceiptHomePageEntity> getBundleReceiptHomePageEntityByMap(@Param("chxh")String chxh, @Param("fchrq")Date fchrq);
	/**修改装载清单的成本信息*/
	void modifyHczzqd_sourceIncome(RequestJsonEntity entity);

	String getInsNoByFchrqAndChxh(RequestJsonEntity entity);
	/**查询某车的装载信息*/
	List<BundleReceipt> getBundleReceiptByChxhAndFchrq(@Param("chxh")String chxh,
			@Param("before")String before,
			@Param("after")String after);

	void insertIntoIncome_D(RequestJsonEntity entity);

	void insertIntoIncome_H(RequestJsonEntity entity);
	/**
	 * TMS中fchrq和zhchrq是一个时间
	 */
	@Select("SELECT count(*) FROM T_INCOME_COST_H WHERE CXH = #{chxh} AND ZHCHRQ = #{fchrq}")
	Integer countIncome(BundleReceiptHomePageEntity entity);

	List<String> getInsNoFromIncome(@Param("before")String before,@Param("chxh")String chxh);
	
	@Select("SELECT max(ins_xh) FROM T_INCOME_COST_D WHERE ins_no = #{insNo}")
	Integer getInsXhFromIncome(String insNo);
	
	/**
	 * 撤销到货--恢复到货状态
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年3月19日
	 * @param company
	 * @param ydbhids
	 */
	void recoverGoodArriveStates(@Param("ydbhid")String ydbhid);
	
	/**
	 * 撤销到货--恢复到货状态
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年3月19日
	 * @param company
	 * @param ydbhids
	 */
	void deleteGoodArriveStatesByXuhao(@Param("xuhao")Integer xuhao);
	
	List<HashMap<String,Object>> searchBundleReceiptForPrint(BundleReceiptSearch receiptsearch);

	@Select("SELECT * FROM HCZZQD_source WHERE YDBHID =#{ydbhid} and convert(varchar(20),zhchrq,120) = #{zhchrq} and chxh = #{chxh}")
	List<BundleReceipt> queryBundleReceiptByYdbhidTime(@Param("ydbhid")String ydbhid,@Param("zhchrq") String zhchrq,@Param("chxh") String chxh);
	
	/**
	 * 通过运单号来进行查询装载清单
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年3月26日
	 * @param ydbhid
	 * @return
	 */
	BundleReceipt getBundleReceiptByYdbhid(@Param("ydbhid")String ydbhid);

	/**
	 * 删除货运记录表
	 * @param ydbhid
	 * @param cztype  操作类型  到站库存  到站卸车  运输途中
	 */
	void removeRouteByCztype(@Param("ydbhid")String ydbhid, @Param("cztype")String cztype);

	BundleReceipt findBundleReceiptByXuhao(Integer xuhao);

	@Select("SELECT count(*) FROM HCZZQD_source WHERE ydbhid=#{ydbhid} and parent_xuhao like '%${xuhao}%'")
	Integer countBundleReceiptByParentXuhao(@Param("xuhao")String xuhao, @Param("ydbhid")String ydbhid);
   
	/**
	 * 
	 * 修改成本: <br>
	 * @version [V1.0, 2018年4月17日]
	 * @param bundlerReceipt
	 */
	@Update("update  HCZZQD_source set qd_Cost = #{qdCost},else_Cost =#{elseCost} where YDBHID =#{ydbhid} ")
	void modifyCost(BundleReceipt bundlerReceipt);

	@Select("select count(*) from HCZZQD_source where chxh = #{chxh} and convert(varchar(20),zhchrq,120)=#{zhchrq}")
	int countBundleByChxhAndZhchrq(@Param("chxh")String chxh, @Param("zhchrq")String zhchrq);

	@Select("select ydbhid from HCZZQD_source where ydbhid in (${ydbhids}) and convert(varchar(20),zhchrq,120) > #{zhchrq}")
	String[] getBiggerBundleReceiptTimeYdbhid(@Param("ydbhids")String ydbhids,@Param("zhchrq")String zhchrq);

	/**
	 * 检查是否存在派车单或提货单存在需要先撤销才能撤销到货物入库（调用一个存储过程）
	 * @param map   参数map
	 * @return
	 */
	Map<String, Object> callProcedureOfUndoCargoStorage(Map<String, Object> map);

	/**
	 * 删除装载清单
	 * @param ydbhid    运单编号
	 */
	void deleteHczzqdSourceByYdbhid(String ydbhid);

	/**
	 * 删除装载清单备注
	 * @param ydbhid    运单编号
	 */
	void deleteHczzqdSourceBeiZhuByYdbhid(String ydbhid);
}
