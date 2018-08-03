package com.ycgwl.kylin.transport.service.api;

import com.ycgwl.kylin.entity.RequestJsonEntity;
import com.ycgwl.kylin.transport.entity.TransportOrderGoodExceptionResult;

import java.util.List;

public interface ITransportOrderGoodExceptionService {
	
	List<TransportOrderGoodExceptionResult> getExceptionInformation(TransportOrderGoodExceptionResult transportOrderGoodExceptionResult);
	void saveExceptionInformation(RequestJsonEntity requestJsonEntity);

}
