package com.ycgwl.kylin.transport.persistent;

import com.ycgwl.kylin.transport.entity.TransportOrderGoodException;
import com.ycgwl.kylin.transport.entity.TransportOrderGoodExceptionResult;

import java.util.List;

public interface TransportOrderExceptionMapper {
	
	List<TransportOrderGoodExceptionResult> getExceptionInformation(TransportOrderGoodExceptionResult transportOrderGoodExceptionResult);
	void saveExceptionInformation(List<TransportOrderGoodException> transportOrderGoodExceptionList);

}
