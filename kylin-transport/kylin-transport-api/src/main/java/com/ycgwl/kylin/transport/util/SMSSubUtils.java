package com.ycgwl.kylin.transport.util;

import com.ycgwl.kylin.SystemKey;
import com.ycgwl.kylin.transport.entity.TransportOrder;
import com.ycgwl.kylin.transport.entity.TransportOrderDetail;
import com.ycgwl.kylin.util.CommonDateUtil;

import java.util.Collection;
import java.util.Date;

/**
 * 
  * @Description: 发送短信的工具类;
  * 		目前只针对沱牌项目组的
  * @author <a href="mailto:108252@ycgwl.com">wyj</a>
  * @date 2017年9月22日 下午4:06:32
  * @version 需求对应版本号
  *
 */
public class SMSSubUtils {	
	private static final String PATTERN = "MM-dd";
	/**
	  * @Description: 判断该公司是否需要发送短信的公司
	  * @param company	公司名称
	  * @return
	  * @exception
	 */
	public static Boolean RequireSendMessageCompany(String company){
		if(SystemKey.SEND_MESSAGE_COMPANY1.equals(company)){
			return true;
		}else{
			return false;
		}
	}
	/**
	  * @Description: 判断该项目组是否需要发送短信
	  * @param project
	  * @return
	  * @exception
	 */
	public static Boolean RequiredSendMessage(String project){
		if(SystemKey.KHBM.equals(project)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 拼接成需要使用的短信内容
	  * @return
	  * @exception
	  * 尊敬的客户:
	  * 		您好!您在四川沱牌舍得营销有效公司订购 + 品名 +件数,
	  * 	已于 + 时间 + 从射洪发出,运输单号: + 运单 +,货物在途情况
	  * 	请致电 0825-6767851,感谢您的支持
	  * 			远成物流
	 */
	public static String splitJoin(TransportOrder transportOrder){
		Collection<TransportOrderDetail> detailList = transportOrder.getDetailList();
		StringBuilder detail = new StringBuilder("");
		for (TransportOrderDetail transportOrderDetail : detailList) {
			detail.append(transportOrderDetail.getPinming()+"共"+transportOrderDetail.getJianshu()+"件、" );
		}
		String substring = detail.substring(0,detail.length()-1);
		
		return "尊敬的客户: "+
				"您好!您在四川沱牌舍得营销有限公司订购的 "+substring+"已于"+
				CommonDateUtil.DateToString(new Date(), PATTERN)+
				"从射洪发出,运输单号:"+transportOrder.getYdbhid()+",预计7日内送达"+
				" ,货物在途情况请致电 0825-6879919,感谢您的支持!"+
				//增加,预计7日内送达，更换座机号码2018-06-07 change by cxj
				//" ,货物在途情况请致电 0825-6767851,感谢您的支持!"+
				"【远成物流】";
	}
	
	
	
}
