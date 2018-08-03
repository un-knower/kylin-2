package com.ycgwl.kylin.transport.service.impl;


import com.ycgwl.kylin.SystemKey;
import com.ycgwl.kylin.transport.entity.SendMessageLogEntity;
import com.ycgwl.kylin.transport.entity.TransportOrder;
import com.ycgwl.kylin.transport.entity.TransportOrderDetail;
import com.ycgwl.kylin.transport.persistent.SendMessageLogMapper;
import com.ycgwl.kylin.transport.service.api.ISendMessageLogService;
import com.ycgwl.kylin.transport.util.GuizhouMoutai;
import com.ycgwl.kylin.transport.util.SMSSubUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("kylin.transport.dubbo.local.sendMessageLogService")
public class SendMessageLogImpl implements ISendMessageLogService{
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	SendMessageLogMapper sendMessageLogMapper;
	
	@Override
	public void saveMessageLog(SendMessageLogEntity sendMessageLogEntity){
		sendMessageLogMapper.saveMessageLog(sendMessageLogEntity);
	}
	@Override
	public List<SendMessageLogEntity> getMessageInfo(){
		return sendMessageLogMapper.getMessageInfo();
	}
	
	@Override
	public void savePhoneSms(TransportOrder transportOrder,String company) {
		List<String> confirmPhoneList = sendMessageLogMapper.getMobileByCompanyName("贵州茅台");
		String confirmPhone = confirmPhoneList.get(0);
		//贵州茅台短信
		if(GuizhouMoutai.checkSendMessage(company, transportOrder.getKhbm()) ) {
			try {
				SendMessageLogEntity sendMessageLogEntity=new SendMessageLogEntity();
				Integer jianshuhj = 0;
				TransportOrderDetail detail = null;
				for (TransportOrderDetail orderdetail : transportOrder.getDetailList()) {
					if(detail == null)
						detail = orderdetail;
					jianshuhj += orderdetail.getJianshu();
				}
				detail.setJianshu(jianshuhj);
				String content = GuizhouMoutai.splitAndAdd(transportOrder,detail);// 内容
				String phone = transportOrder.getShhryb();// 手机号
				sendMessageLogEntity.setConfirmphone(confirmPhone);
				sendMessageLogEntity.setContext(content);
				sendMessageLogEntity.setSendphoneNb(phone);
				sendMessageLogEntity.setPhone(phone);
				sendMessageLogEntity.setYdbhid(transportOrder.getYdbhid());
				saveMessageLog(sendMessageLogEntity);		
			}catch(Exception  e) {
				logger.error("贵州茅台短信发送失败:{}", transportOrder.getYdbhid(), e);
				e.printStackTrace();
			}
		//四川远成沱牌短信	
		} else if("四川远成".equals(company) && SystemKey.KHBM.equals(transportOrder.getKhbm())) {//四川分公司沱牌客户（沱牌酒厂）
			try {
				String content = SMSSubUtils.splitJoin(transportOrder);
				SendMessageLogEntity sendMessageLogEntity = new SendMessageLogEntity();
				sendMessageLogEntity.setContext(content);
				sendMessageLogEntity.setSendphoneNb(transportOrder.getShhryb());
				sendMessageLogEntity.setYdbhid(transportOrder.getYdbhid());
				saveMessageLog(sendMessageLogEntity);			
			}catch(Exception  e) {
				logger.error("沱牌短信发送失败:{}", transportOrder.getYdbhid(), e);
				e.printStackTrace();
			}
		}
	}

}
