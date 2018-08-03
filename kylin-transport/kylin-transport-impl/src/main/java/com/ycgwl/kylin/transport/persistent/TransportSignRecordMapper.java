package com.ycgwl.kylin.transport.persistent;


import com.ycgwl.kylin.transport.dto.ReorderDto;
import com.ycgwl.kylin.transport.entity.*;
import com.ycgwl.kylin.transport.vo.ReorderVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Description 签收数据
 * @author <a href="mailto:109668@ycgwl.com">lihuixia</a>
 * @createDate 2017-04-27 09:21:30
 */
@Mapper
public interface TransportSignRecordMapper {
	
	
	/**
	 * @Note 去运单作废表查询，检查是否已经作废.
	 * @param ydbhid
	 * @return
	 */
	Long isOrderCanceled(String ydbhid);
	
	/**
	 * @Note 该运单是否到站不是本公司或还未录入到货.
	 * @param ydbhid 运单号
	 * @param fazhan 发站
	 * @return
	 */
	Long isTransportOrderReachRight(String ydbhid, String fazhan);
	
	/**
	 * 根据运单号查询出运单信息
	 * @param ydbhid 运单号
	 * @return yshhm 运输号码
	 * @return fazhan 发站
	 * @return ysfs 运输方式
	 */
	TransportOrder getTransportOrderByYdbhid(String ydbhid);
	
	/**
	 * 获取判断是否到付的信息（合计大于0则是到付）
	 * @param ydbhid
	 * @return
	 */
	ShqsdResult getReachAfterPayInfo(@Param("ydbhid") String ydbhid);
	
	/**
	 * 查询是否做过成本和财凭
	 * @param ydbhid
	 * @return
	 */
	Integer countIncomeCost(@Param("ydbhid") String ydbhid);
	
	/**
	 * 新增签收日志表
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年10月27日
	 * @param transportSignRecordLog
	 */
	void insertTransportSignRecordLog(TransportSignRecordLog transportSignRecordLog);
	
	/**
	 * 根据运单编号更新签收表
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年10月30日
	 * @param transportSignRecord
	 */
	void updateTransportSignRecordByYdbhid(TransportSignRecord transportSignRecord);
	
	/**
	 * 根据运单编号更新签收日志表
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年10月30日
	 * @param transportSignRecordLog
	 */
	void updateTransportSignRecordLogByYdbhid(TransportSignRecordLog transportSignRecordLog);
	
	/**
	 * 根据订单编号删除在途跟踪表里面的已撤销签收信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年10月30日
	 * @param ydbhid
	 * @param cztype
	 */
	void deleteHwydRouteByYdbhid(@Param("ydbhid") String ydbhid,@Param("cztype") String cztype);
	
	/**
	 * 根据运单号查询签收信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年10月30日
	 * @param ydbhid
	 * @return
	 */
	TransportSignRecord selectTransportSignRecordByYdbhid(String ydbhid);
	
	/**
	 * 根据公司和运单号更新分理库存表
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年10月31日
	 * @param transportProcessStock
	 */
	void updatetransportProcessStockByGsAndYdbhid(TransportProcessStock transportProcessStock);
	
	/**
	 * 新增T_WL_ORDERTRACE信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年10月31日
	 * @param transportOrderTrace
	 */
	void insertTransportOrderTrace(TransportOrderTrace transportOrderTrace);
	
	/**
	 * 查询线路
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月2日
	 * @param ydbhid
	 * @return
	 */
	FiwtResult selectXianluByYdbhid(String ydbhid);
	
	/**
	 * 查询财凭
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月2日
	 * @param ydbhid
	 * @return
	 */
	Boolean selectFkfsh(FiwtResult fiwtResult);
	
	/**
	 * 查询放货通知
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月2日
	 * @param ydbhid
	 * @return
	 */
	Integer selectFhtzd(String ydbhid);
	
	/**
	 * 查询派车单信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月2日
	 * @param ydbhid
	 * @return
	 */
	CarOutResult selectCarOutByYdbhid(String ydbhid);
	
	/**
	 * 查询提货单信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月2日
	 * @param ydbhid
	 * @return
	 */
	String selectThqsdByYdbhid(String ydbhid);
	
	/**
	 * 查询收据是否打印
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月2日
	 * @param ydbhid
	 * @return
	 */
	List<String> selectCwsjDetailByYshm(String yshm);
	
	/**
	 * 查询派车单信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月2日
	 * @param ydbhid
	 * @return
	 */
	String selectCarOutShjhmByYdbhid(String ydbhid);
	
	/**
	 * 查询提货单信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月2日
	 * @param ydbhid
	 * @return
	 */
	String selectThqsdShjhmByYdbhid(String ydbhid);
	
	/**
	 * 查询签收状态
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月3日
	 * @return
	 */
	@Select("SELECT * FROM T_QSZT_BASE")
	List<TransportQsztBase> selectQsztBase();
	
	/**
	 * 根据运单号查询签收状态
	 * @param transportCode
	 * @return
	 */
	Integer selectSignStatusByYdbhid(String transportCode);
	
	/**
	 * 查询是否有送货派车单 
	 * @param transportCode
	 * @return
	 */
	Long countSendCarBill(String transportCode);
	
	/**
	 * 查询是否有提货签收单
	 * @param transportCode
	 * @return
	 */
	Long countLadingSignBill(String transportCode);
	
	/**
	 * 查询绿色通道信息 
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月3日
	 * @param ydbhid
	 * @return
	 */
	Integer selectGreenWayByYdbhid(String ydbhid);
	
	/**
	 * 查询破损短少责任划分信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月28日
	 * @param ydbhid
	 * @return
	 */
	String selectFlagByYdbhid(String ydbhid);
	
	/**
	 * 查询理赔信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月28日
	 * @param ydbhid
	 * @return
	 */
	Integer selectLdqppassByYdbhid(String ydbhid);
	
	/**
	 * 新增撤销日志信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月28日
	 * @param undoSignLog
	 */
	void insertUndoSignLog(UndoSignLog undoSignLog);
	
	/**
	 * 新增在途信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月28日
	 * @param hwydRoute
	 */
	void insertHwydRoute(HwydRoute hwydRoute);
	
	/**
	 * 删除轨迹信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月29日
	 * @param ydbhid
	 */
	void deleteTransportOrderTraceByYdbhid(String ydbhid);
	
	/**
	 * 根据运单编号更新签收表（撤销签收）
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月5日
	 * @param transportSignRecord
	 */
	void undoSignByYdbhid(TransportSignRecord transportSignRecord);

	FkfshResult selectFkfshResult(FiwtResult xianlu);
	
	/**
	 * 查询装载信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月15日
	 * @param chxh
	 * @param ydbhids
	 * @param company
	 * @return
	 */
	List<BundleReceipt> selectBundleReceiptByChxhAndYdbhids(@Param("chxh")String chxh,@Param("ydbhids") List<String> ydbhids, @Param("company")String company);
	
	List<String> findTransportBillByCompanyName(@Param("ydbhids") List<String> ydbhids, @Param("company")String company);

	List<BundleReceipt> selectBundleReceiptByChxhAndYdbhidsWithoutCompany(@Param("chxh")String chxh,@Param("ydbhids") List<String> ydbhids, @Param("company")String company);
	
	/**
	 * 更新状态成到货的状态（提货）
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年3月21日
	 * @param map
	 */
	void arriveBundleReceiptByXuhao(Map<String,Object> map);

	/**
	 * waybillNum
	 * @param waybillNum  运单编号
	 * @param rightNum  权限大小
	 * @return
	 */
	List<ReorderVo> selectReorder(@Param("waybillNum")String waybillNum, @Param("rightNum")Integer rightNum);

	/**
	 * 添加返单录入日志
	 * @param transportSignRecord
	 * @return
	 */
	int saveReorderLog(TransportSignRecord transportSignRecord);

	/**
	 * 返单录入
	 * @param reorderDto
	 * @return
	 */
	int saveReorder(ReorderDto reorderDto);

	/**
	 * 查询返单配置
	 * @return
	 */
	ReorderConfigure selectReorderConfigure();

	/**
	 * 修改返单配置
	 * @param reorderConfigure
	 * @return
	 */
	int updateReorderConfigure(ReorderConfigure reorderConfigure);

	/**
	 * 查询返单发送人是否为空
	 * @param waybillNum
	 * @return
	 */
	String selectfdfsrByydbhid(String waybillNum);
	
	/**
	 * 返单确认查询
	 * @param waybillNum
	 * @param company
	 * @return
	 */
	ReorderVo selectReceiveReorder(@Param("waybillNum")String waybillNum, @Param("company")String company);

	/**
	 * 跟新签收信息
	 * @param ydbhid    运单编号
	 */
    void updateSignRecordByYdbhid(String ydbhid);
}
