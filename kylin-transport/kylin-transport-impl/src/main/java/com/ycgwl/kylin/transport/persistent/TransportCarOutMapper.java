package com.ycgwl.kylin.transport.persistent;

import com.ycgwl.kylin.transport.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 派车签收单（送货）
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月26日
 */
public interface TransportCarOutMapper {

	/**
	 * 根据公司id和派车单号查询派车签收信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月26日
	 * @param gsid
	 * @param id
	 * @return
	 */
	TransportCarOut selectTransportCarOutByIdAndGs(@Param("gsid")String gsid,@Param("id")Integer id);
	
	/**
	 * 根据公司id和派车单号查询派车签收货物信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月27日
	 * @param gsid
	 * @param id
	 * @return
	 */
	List<TransportCarOutGoodsDetail> selectTransportCarOutGoodsDetailByIdAndGs(@Param("gsid")String gsid,@Param("id")Integer id);
	
	/**
	 * 根据公司id和派车单号查询送货派车单车辆明细信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月27日
	 * @param gsid
	 * @param id
	 * @return
	 */
	List<TransportCarOutVehicleDetail> selectTransportCarOutVehicleDetaillByIdAndGs(@Param("gsid")String gsid,@Param("id")Integer id);
	
	/**
	 * 批量新增送货派车单车辆明细信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月29日
	 * @param transportCarOutVehicleDetails
	 */
	void batchInsertTransportCarOutVehicleDetail(List<TransportCarOutVehicleDetail> transportCarOutVehicleDetails);
	
	/**
	 * 更新送货派车单信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月29日
	 * @param transportCarOut
	 */
	void updateTransportCarOutByIdAndGs(TransportCarOut transportCarOut);
	
	/**
	 * 更新送货派车单车辆明细信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月29日
	 * @param transportCarOutVehicleDetail
	 */
	void updateTransportCarOutByDetailId(TransportCarOutVehicleDetail transportCarOutVehicleDetail);
	
	/**
	 * 根据公司编号和派车单号查询运单号
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月29日
	 * @param gsid
	 * @param id
	 * @return
	 */
	String selectTransportCarOutYdbhidByIdAndGsid(@Param("gs")String gs,@Param("id")Integer id);
	
	/**
	 * 根据运单编号和公司查询到站网点
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月29日
	 * @param ydbhid
	 * @param gs
	 * @return
	 */
	String selectDzshhdByYdbhid(@Param("ydbhid")String ydbhid,@Param("gs")String gs);
	
	/**
	 * 获取公司分理标准价格信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月29日
	 * @param mdd
	 * @param gs
	 * @return
	 */
	Long selectShlcByYdGs(@Param("mdd")String mdd,@Param("gs")String gs);
	
	/**
	 * 删除送货派车单车辆明细信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月30日
	 * @param gsid
	 * @param id
	 */
	void deleteTransportCarOutVehicleDetailById(@Param("gsid")String gsid,@Param("id")Integer id);
	
	/**
	 * 批量新增派车签收单明细四
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月30日
	 * @param transportCarOutDetailFourths
	 */
	void batchInsertTransportCarOutDetailFourth(List<TransportCarOutDetailFourth> transportCarOutDetailFourths);
	
	/**
	 * 批量新增派车签收单明细五
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月30日
	 * @param transportCarOutDetailFifths
	 */
	void batchInsertTransportCarOutDetailFifth(List<TransportCarOutDetailFifth> transportCarOutDetailFifths);
	
	/**
	 * 删除送货派车单明细四
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月30日
	 * @param gsid
	 * @param id
	 */
	void deleteTransportCarOutDetailFourthById(@Param("gsid")String gsid,@Param("id")Integer id);
	
	/**
	 * 删除送货派车单明细五
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月30日
	 * @param gsid
	 * @param id
	 */
	void deleteTransportCarOutDetailFifthById(@Param("gsid")String gsid,@Param("id")Integer id);
	
	/**
	 * 根据公司id和派车单号查询派车单明细二
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月30日
	 * @param gsid
	 * @param id
	 * @return
	 */
	List<TransportCarOutDetailSecond> selectTransportCarOutDetailSecondByIdAndGs(@Param("gsid")String gsid,@Param("id")Integer id);
	
	/**
	 * 根据公司id和派车单号查询派车单明细四
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月30日
	 * @param gsid
	 * @param id
	 * @return
	 */
	List<TransportCarOutDetailFourth> selectTransportCarOutDetailFourthByIdAndGs(@Param("gsid")String gsid,@Param("id")Integer id);
	
	/**
	 * 根据公司id和派车单号查询派车单明细五
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月30日
	 * @param gsid
	 * @param id
	 * @return
	 */
	List<TransportCarOutDetailFifth> selectTransportCarOutDetailFifthByIdAndGs(@Param("gsid")String gsid,@Param("id")Integer id);
}
