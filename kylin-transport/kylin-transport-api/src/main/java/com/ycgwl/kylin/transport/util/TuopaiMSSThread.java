package com.ycgwl.kylin.transport.util;

import com.ycgwl.kylin.transport.entity.TransportOrder;
import com.ycgwl.kylin.util.SmsUtils;

/**
  * @Description: 沱牌发送短信的线程
  * 	因为一条短信的发送需要一秒左右,所以启动新线程以加速
  * @author <a href="mailto:工号@ycgwl.com">姓名全拼</a>
  * @date 2017年9月25日 上午11:06:49
  * @version 需求对应版本号
  *
 */
public class TuopaiMSSThread extends Thread {

	private TransportOrder transportOrder;
	
	public TransportOrder getTransportOrder() {
		return transportOrder;
	}

	public void setTransportOrder(TransportOrder transportOrder) {
		this.transportOrder = transportOrder;
	}

	@Override
	public void run() {
		//短信内容
		String content = SMSSubUtils.splitJoin(transportOrder);
		//发送短信
		SmsUtils.sendMessage(transportOrder.getShhryb(), content);
	}

}
