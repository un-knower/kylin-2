package com.ycgwl.kylin.transport.persistent;

import com.ycgwl.kylin.transport.entity.TransportOrderDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Collection;
/**
 * 运单明细
 * TODO Add description
 * <p>
 * @developer Create by <a href="mailto:110686@ycgwl.com">wanyj</a> at 2017年5月31日
 */
public interface TransportOrderDetailMapper {
	/**
	 * 根据运单编号进行查找
	 * @return
	 */
	public Collection<TransportOrderDetail> queryTransportOrderDetailByYdbhid(String ydbhid);

	/**
	 * 将运单明细添加到数据库中
	 */
	public void InsertIntoHWYDXZ(TransportOrderDetail transportOrderDetail);
	
	/**
	 * 修改运单明细	更改是通过   运单编号+序列号   来确定一条数据
	 * @param transportOrderDetail
	 */
	public void updateHWYDXZ(TransportOrderDetail transportOrderDetail);

	/**
	 * 根据运单标号和序列号,对数据库中的数据进行移除
	 * @param ydbhid
	 * @param ydxzh
	 */
	void deleteTransportOrderDetail(@Param("ydbhid") String ydbhid,@Param("ydxzh") Integer ydxzh);

	TransportOrderDetail getWayBillByYdbhid(String ydbhid);
	
	/**
	 * 批量插入装载清单信息
	 * @param transportOrderDetails
	 */
	void batchInsertIntoHWYDXZ(Collection<TransportOrderDetail> transportOrderDetails);

	TransportOrderDetail getOrderDetailByYdbhidAndYdxzh(@Param("ydbhid")String ydbhid, @Param("ydxzh")Integer ydxzh);
	
	/**
	 * 删除订单明细
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月14日
	 * @param ydbhid
	 */
	void deleteTransportOrderDetailByYdbhid(String ydbhid);
	
	/**
	 * 
	 * 根据运单号查询运单细则: <br>
	 * @version [V1.0, 2018年4月20日]
	 * @param ydbhid
	 * @return
	 */
	@Select("select count(1) from HWYDXZ where YDBHID = #{ydbhid}")
	Integer  queryOrderDetailCountByYdbhid(@Param("ydbhid") String ydbhid);
	
}
