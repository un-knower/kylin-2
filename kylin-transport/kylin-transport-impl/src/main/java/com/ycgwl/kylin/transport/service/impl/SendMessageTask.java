package com.ycgwl.kylin.transport.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ycgwl.kylin.transport.entity.SendMessageLogEntity;
import com.ycgwl.kylin.transport.persistent.SendMessageLogMapper;
import com.ycgwl.kylin.transport.service.api.ISendMessageLogService;
import com.ycgwl.kylin.util.SmsUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Properties;
@Component
public class SendMessageTask {
	protected static final Logger logger = LoggerFactory.getLogger(SendMessageTask.class);
	@Resource
	ISendMessageLogService sendMessageLogService; 
	@Resource
	SendMessageLogMapper sendMessageLogMapper;
	
	protected static Properties prop = null;

	@Scheduled(cron = "0 */15 * * * ?")  
	public void sendMessageTask() {
		//查询出发送未发送的运单
		List<SendMessageLogEntity> listSendMessageLogList = sendMessageLogService.getMessageInfo();
		// 获取到手机号和内容
		// 0:未发送    1：成功   2：失败
		int flag = 1;  
		while(listSendMessageLogList.size()==1) {
			SendMessageLogEntity entity = listSendMessageLogList.get(0);
			sendMessageLogMapper.updateByYdbhid(entity.getYdbhid(),3,"正在发送");
			JSONObject jsonObject = null;
			try{
				jsonObject = SmsUtils.sendMessage(entity.getSendphoneNb(),entity.getSendContent());
				if(StringUtils.isNotBlank(entity.getConfirmphone())) {
					SmsUtils.sendMessage(entity.getConfirmphone(), entity.getSendContent());
				}
				Thread.sleep(2000);
				logger.info("短信发送成功:发送人手机号:" + entity.getSendphoneNb() + ", 发送内容：" + entity.getSendContent());
			}
			catch(Exception e){
				logger.info("短信发送失败:发送人手机号:" + entity.getSendphoneNb() + ", 发送内容：" + entity.getSendContent());
				e.printStackTrace();
			}
			String errorMessage = jsonObject == null ? "Connection timed out" : jsonObject.getString("errorMessage");
			if (jsonObject == null || StringUtils.isNotEmpty(errorMessage)) {
				// 发送失败				
				flag = 2;
			} else {	
				flag = 1;
			}
			sendMessageLogMapper.updateByYdbhid(entity.getYdbhid(),flag,errorMessage);
			listSendMessageLogList = sendMessageLogService.getMessageInfo();
		}
	}


}
