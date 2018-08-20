package com.ycgwl.kylin.transport.util;

import com.ycgwl.kylin.transport.entity.TransportOrder;
import com.ycgwl.kylin.transport.entity.TransportOrderDetail;
import com.ycgwl.kylin.util.SmsUtils;

/**
 * 给贵州茅台客户发送短信
 * @author NameX
 *
 */
public class GuizhouMoutai implements Runnable {
	
	
	public static GuizhouMoutai createGuizhouMoutai() {
		return new GuizhouMoutai();
	}
	
	private static final String GUIZHOU = "贵阳";
	private static final String KHBM = "GYYC20160831";
	
	/**
	 * 判断是否需要发送短信
	 * @return
	 */
	public static boolean checkSendMessage(String company,String khbm) {
		if(GUIZHOU.equals(company) && KHBM.equals(khbm)) {
      return true;
    }
		return false;
	}
	
	/**
	 * 【远成物流】您好！关于贵司在贵州茅台酱香酒营销有限公司订单单号*******货物，
	 * 已由我司承运配送，运单号为:*********.品名:**酒，件数:**，请做好收货准备，
	 * 在签收货物的同时，请仔细核对货物是否完好，物流码是否一致，准备好公章或身份证复印件，
	 * 进行签字盖章确认，谢谢！如需查询详情可致电:15585285102（茅台售后服务）
	 * @return
	 */
	public static String splitAndAdd(TransportOrder order ,TransportOrderDetail detail) {
		StringBuilder content = new StringBuilder("【远成物流】您好！关于贵司在贵州茅台酱香酒营销有限公司订单单号");
		content.append(order.getKhdh() == null ? "" : order.getKhdh()).append("货物，已由我司承运配送，运单号为:")
			.append(order.getYdbhid()).append(".品名:").append(detail.getPinming())
			.append("酒，件数:").append(detail.getJianshu())
			.append("，请做好收货准备，在签收货物的同时，请仔细核对货物是否完好，物流码是否一致，准备好公章或身份证复印件，")
			.append("进行签字盖章确认，谢谢！如需查询详情可致电:15585285102（茅台售后服务）");
		return content.toString();
	}
	private String content;
	private String tel;
	public String getContent() {
		return content;
	}
	public GuizhouMoutai setContent(String content) {
		this.content = content;
		return this;
	}
	public String getTel() {
		return tel;
	}
	public GuizhouMoutai setTel(String tel) {
		this.tel = tel;
		return this;
	}
	@Override
	public void run() {
		SmsUtils.sendMessage(tel, content);
		SmsUtils.sendMessage(SmsUtils.YC_TEL, content);
	}

}
