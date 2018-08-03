package com.ycgwl.kylin.transport.service.api;

import com.ycgwl.kylin.transport.entity.TransportOrderDetail;

import java.util.Collection;

public interface TransportOrderDetailService {
	
	public void addTransportOrderDetail(TransportOrderDetail transportOrderDetail);
	
	public void updateTransportOrderDetail(TransportOrderDetail transportOrderDetail);
	
	public Collection<TransportOrderDetail> queryTransportOrderDetailByYdbhid(String ydbhid);

	public void deleteTransportOrderDetail(String ydbhid, Integer ydxzh);
	
	public void batchAddTransportOrderDetail(Collection<TransportOrderDetail> transportOrderDetails);

	public TransportOrderDetail getWayBillByYdbhid(String ydbhid);

	TransportOrderDetail getOrderDetailByYdbhidAndYdxzh(String ydbhid, Integer ydxzh);
	
	/**
	 * 删除订单明细
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月14日
	 * @param ydbhid
	 */
	void removeTransportOrderDetailByYdbhid(String ydbhid);
}
