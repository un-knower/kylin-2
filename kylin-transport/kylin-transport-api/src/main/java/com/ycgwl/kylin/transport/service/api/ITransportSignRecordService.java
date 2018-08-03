package com.ycgwl.kylin.transport.service.api;

import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.dto.ReorderDto;
import com.ycgwl.kylin.transport.entity.*;
import com.ycgwl.kylin.transport.vo.ReorderVo;

import java.util.List;

/**
 * 签收逻辑.
 * <p>
 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at 2017-05-25 09:21:30
 */
public interface ITransportSignRecordService {
	
	/**
	 * 校验运单是否可以签收.
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月1日
	 * @param transportSignRecordSearch
	 * @return 签收的提示编码
	 * @throws ParameterException 参数有误时
	 * @throws BusinessException 业务逻辑处理有误时
	 */
	TransportSignRecordResult canTransportOrderSign(TransportSignRecordSearch transportSignRecordSearch) throws ParameterException, BusinessException;
	
	/**
	 * 整票签收
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年10月25日
	 * @param signRecord
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	String fullTicketReceipt(TransportSignRecordEntity transportSignRecordEntity) throws ParameterException, BusinessException;
	
	/**
	 * 新增签收日志表
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年10月27日
	 * @param transportSignRecordLog
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	void addTransportSignRecordLog(TransportSignRecordLog transportSignRecordLog) throws ParameterException, BusinessException;
	
	/**
	 * 根据运单编号更新签收表
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年10月30日
	 * @param transportSignRecord
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	void modifyTransportSignRecordByYdbhid(TransportSignRecord transportSignRecord) throws ParameterException, BusinessException;
	
	/**
	 * 根据运单编号更新签收日志表
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年10月30日
	 * @param transportSignRecordLog
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	void modifyTransportSignRecordLogByYdbhid(TransportSignRecordLog transportSignRecordLog) throws ParameterException, BusinessException;
	
	/**
	 * 根据订单编号删除在途跟踪表里面的已撤销签收信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年10月30日
	 * @param ydbhid
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	void removeHwydRouteByYdbhid(String ydbhid) throws ParameterException, BusinessException;
	
	/**
	 * 根据运单号查询签收信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年10月30日
	 * @param ydbhid
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	TransportSignRecord getTransportSignRecordByYdbhid(String ydbhid) throws ParameterException, BusinessException;
	
	/**
	 * 根据公司和运单号更新分理库存表
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年10月31日
	 * @param transportProcessStock
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	void modifyTransportProcessStockByGsAndYdbhid(TransportProcessStock transportProcessStock) throws ParameterException, BusinessException;
	
	/**
	 *  新增T_WL_ORDERTRACE信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年10月31日
	 * @param transportOrderTrace
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	void addTransportOrderTrace(TransportOrderTrace transportOrderTrace) throws ParameterException, BusinessException;
	
	/**
	 * 查询线路
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月2日
	 * @param ydbhid
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	FiwtResult getXianluByYdbhid(String ydbhid) throws ParameterException, BusinessException;
	
	
	/**
	 * 查询是否做过成本和财凭
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月2日
	 * @param ydbhid
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	Integer countIncomeCost(String ydbhid) throws ParameterException, BusinessException;
	
	/**
	 * 根据运单号查询出运单信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月2日
	 * @param ydbhid
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	TransportOrder getTransportOrderByYdbhid(String ydbhid) throws ParameterException, BusinessException;
	
	/**
	 * 查询财凭
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月2日
	 * @param ydbhid
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	Boolean getFkfsh(FiwtResult fiwtResult) throws ParameterException, BusinessException;
	
	/**
	 * 获取判断是否到付的信息（合计大于0则是到付）
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月2日
	 * @param ydbhid
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	ShqsdResult getReachAfterPayInfo(String ydbhid) throws ParameterException, BusinessException;
	
	/**
	 * 查询派车单信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月2日
	 * @param ydbhid
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	CarOutResult getCarOutByYdbhid(String ydbhid) throws ParameterException, BusinessException;
	
	/**
	 * 查询提货单信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月2日
	 * @param ydbhid
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	String getThqsdByYdbhid(String ydbhid) throws ParameterException, BusinessException;
	
	/**
	 * 查询收据是否打印
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月2日
	 * @param ydbhid
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	List<String> getCwsjDetailByYshm(String yshm) throws ParameterException, BusinessException;
	
	/**
	 * 查询派车单信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月2日
	 * @param ydbhid
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	String getCarOutShjhmByYdbhid(String ydbhid) throws ParameterException, BusinessException;
	
	/**
	 * 查询提货单信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月2日
	 * @param ydbhid
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	String getThqsdShjhmByYdbhid(String ydbhid) throws ParameterException, BusinessException;
	
	/**
	 * 查询签收状态
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月3日
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	List<TransportQsztBase> getQsztBase() throws ParameterException, BusinessException;
	
	
	/**
	 * 查询绿色通道信息 
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月3日
	 * @param ydbhid
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	Integer getGreenWayByYdbhid(String ydbhid) throws ParameterException, BusinessException;
	
	/**
	 * 查询撤销签收信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月27日
	 * @param transportSignRecordSearch
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	TransportSignRecordResult searchUndoSign(TransportSignRecordSearch transportSignRecordSearch) throws ParameterException, BusinessException;

	/**
	 * 查询破损短少责任划分信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月28日
	 * @param ydbhid
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	String getFlagByYdbhid(String ydbhid) throws ParameterException, BusinessException;
	
	/**
	 * 查询理赔信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月28日
	 * @param ydbhid
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	Integer getLdqppassByYdbhid(String ydbhid) throws ParameterException, BusinessException;
	
	/**
	 * 新增撤销日志信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月28日
	 * @param ydbhid
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	void addUndoSignLog(UndoSignLog undoSignLog) throws ParameterException, BusinessException;
	
	/**
	 * 新增在途信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月28日
	 * @param hwydRoute
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	void addHwydRoute(HwydRoute hwydRoute) throws ParameterException, BusinessException;
	
	/**
	 * 删除轨迹信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月29日
	 * @param ydbhid
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	void removeTransportOrderTraceByYdbhid(String ydbhid) throws ParameterException, BusinessException;
	
	/**
	 * 撤销签收
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月29日
	 * @param ydbhid
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	String undoSign(TransportSignRecordSearch transportSignRecordSearch) throws ParameterException, BusinessException;

	FkfshResult getFkfshResult(FiwtResult xianlu);
	
	/**
	 * 批量签收
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月21日
	 * @param transportSignRecordEntity
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	@Deprecated
	boolean batchTransportSign(TransportSignRecordEntity transportSignRecordEntity) throws ParameterException, BusinessException;
	
	/**
	 * 批量签收（未校验）
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月28日
	 * @param transportSignRecordEntity
	 * @return
	 * @throws ParameterException
	 * @throws BusinessException
	 */
	boolean batchTransportSignNotCheck(TransportSignRecordEntity transportSignRecordEntity) throws ParameterException, BusinessException;

	/**
	 * 在途跟踪查询
	 * @param wayBillNum  运单号
	 */
	List<OnTheWay> selectTrackInfo(String wayBillNum);

	/**
	 * 返单录入查询
	 * @param account  登录账户
	 * @return
	 */
	JsonResult selectReorder(String account, ReorderDto reorderDto);

	/**
	 * 返单查询
	 * @param waybillNum  运单编号
	 * @param rightNum  权限大小
	 * @return
	 */
	List<ReorderVo> selectReorder(String waybillNum, Integer rightNum);

	/**
	 * 返单录入
	 * @param account
	 * @param reorderDto
	 * @return
	 */
	JsonResult saveReorder(String account, ReorderDto reorderDto);

	/**
	 * 查询返单配置
	 * @param account
	 * @return
	 */
	JsonResult selectReorderConfigure(String account);

	/**
	 * 修改返单配置
	 * @param reorderConfigure
	 * @return
	 */
	JsonResult updateReorderConfigure(ReorderConfigure reorderConfigure);

	/**
	 * 返单确认查询
	 * @param account
	 * @param company
	 * @param waybillNum
	 * @return
	 */
	JsonResult selectReceiveReorder(String account, String company, ReorderDto reorderDto);

	/**
	 * 返单确认录入
	 * @param reorderDto
	 * @return
	 */
	JsonResult saveReceiveReorder(ReorderDto reorderDto);
}
