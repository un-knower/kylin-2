package com.ycgwl.kylin.transport.service.api;

import com.ycgwl.kylin.transport.entity.SendMessageLogEntity;
import com.ycgwl.kylin.transport.entity.TransportOrder;

import java.util.List;

public interface ISendMessageLogService {
	void saveMessageLog(SendMessageLogEntity sendMessageLogEntity);
	List<SendMessageLogEntity> getMessageInfo();
	

	/**
	 * 保存短信内容，定时发送短信
	 */
	void savePhoneSms(TransportOrder transportOrder, String company);
}
