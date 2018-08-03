package com.ycgwl.kylin.security.entity;

import java.io.Serializable;

/**
 * 
 * <p>Title: KylinSysGrczqx.java</p> 
 *@date 2018年5月16日 
 *@author ltao 112656
 *密码
 *@version 1.0
 */
public class KylinSysGrczqx implements Serializable {

	private static final long serialVersionUID = 1259763851432987214L;
	/**
	 * 工号
	 */
	private String HmcGkh;
	/**
	 * 口令
	 */
	private String KouLing;
	public String getHmcGkh() {
		return HmcGkh;
	}
	public void setHmcGkh(String hmcGkh) {
		HmcGkh = hmcGkh;
	}
	public String getKouLing() {
		return KouLing;
	}
	public void setKouLing(String kouLing) {
		KouLing = kouLing;
	}
	
}
