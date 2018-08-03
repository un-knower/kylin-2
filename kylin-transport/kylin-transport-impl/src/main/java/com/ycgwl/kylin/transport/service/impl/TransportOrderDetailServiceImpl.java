package com.ycgwl.kylin.transport.service.impl;

import com.ycgwl.kylin.transport.entity.TransportOrderDetail;
import com.ycgwl.kylin.transport.persistent.TransportOrderDetailMapper;
import com.ycgwl.kylin.transport.service.api.TransportOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service("kylin.transport.dubbo.local.transportOrderDetailService")
public class TransportOrderDetailServiceImpl implements TransportOrderDetailService {

	@Autowired
	private TransportOrderDetailMapper mapper;

	public void addTransportOrderDetail(TransportOrderDetail transportOrderDetail) {
		mapper.InsertIntoHWYDXZ(transportOrderDetail);

	}

	public void updateTransportOrderDetail(TransportOrderDetail transportOrderDetail) {
		mapper.updateHWYDXZ(transportOrderDetail);
	}
	
	public Collection<TransportOrderDetail> queryTransportOrderDetailByYdbhid(String ydbhid) {
		return mapper.queryTransportOrderDetailByYdbhid(ydbhid);
	}

	@Override
	public void deleteTransportOrderDetail(String ydbhid, Integer ydxzh) {
		mapper.deleteTransportOrderDetail(ydbhid,ydxzh);
	}
	@Override
	public TransportOrderDetail getWayBillByYdbhid(String ydbhid){
		return mapper.getWayBillByYdbhid(ydbhid);
	}
	@Override
	public void batchAddTransportOrderDetail(Collection<TransportOrderDetail> transportOrderDetails) {
		//单条保存解决货物明细表的库存触发器无法识别问题
		for (TransportOrderDetail detail : transportOrderDetails) {
			mapper.InsertIntoHWYDXZ(detail);
		}
		//mapper.batchInsertIntoHWYDXZ(transportOrderDetails);
	}
	@Override
	public TransportOrderDetail getOrderDetailByYdbhidAndYdxzh(String ydbhid, Integer ydxzh) {
		return mapper.getOrderDetailByYdbhidAndYdxzh(ydbhid,ydxzh);
	}

	@Override
	public void removeTransportOrderDetailByYdbhid(String ydbhid) {	
		mapper.deleteTransportOrderDetailByYdbhid(ydbhid);	
	}
}
