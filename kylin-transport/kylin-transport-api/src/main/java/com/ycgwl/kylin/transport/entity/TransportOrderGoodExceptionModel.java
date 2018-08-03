package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.util.List;

public class TransportOrderGoodExceptionModel extends BaseEntity{

	private static final long serialVersionUID = 1524217469972246762L;

	private TransportOrderGoodException transportOrderGoodException;
	
	List<TransportOrderGoodException> transportOrderGoodExceptionDetails;

	public TransportOrderGoodException getTransportOrderGoodException() {
		return transportOrderGoodException;
	}

	public void setTransportOrderGoodException(TransportOrderGoodException transportOrderGoodException) {
		this.transportOrderGoodException = transportOrderGoodException;
	}

	public List<TransportOrderGoodException> getTransportOrderGoodExceptionDetails() {
		return transportOrderGoodExceptionDetails;
	}

	public void setTransportOrderGoodExceptionDetails(
			List<TransportOrderGoodException> transportOrderGoodExceptionDetails) {
		this.transportOrderGoodExceptionDetails = transportOrderGoodExceptionDetails;
	}

}
