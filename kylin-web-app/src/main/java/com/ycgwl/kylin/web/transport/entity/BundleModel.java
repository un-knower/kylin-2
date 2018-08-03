/**
 * kylin-admin-webapp
 * com.ycgwl.kylin.admin.transport.entity
 */
package com.ycgwl.kylin.web.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;
import com.ycgwl.kylin.transport.entity.BundleReceipt;

/**
 * @author <a href="mailto:110686@ycgwl.com">ding xuefeng</a>
 * @createDate 2017-04-26 14:15:33
 */
public class BundleModel extends BaseEntity {

	private static final long serialVersionUID = -1698406941165436484L;

	/**运单列表*/
	private String[] ydbhids;		
	
	/**装载基本信息*/
	private BundleReceipt bundle;	

	/**运输方式    整车零担*/
	private String yslx;
	
	/**是否中转    1: 是     0:不是*/
	private Integer istransfer;
	
	/**中转站*/
	private String freightStation;
	

	public Integer getIstransfer() {
		return istransfer;
	}
	public void setIstransfer(Integer istransfer) {
		this.istransfer = istransfer;
	}
	public String getFreightStation() {
		return freightStation;
	}
	public void setFreightStation(String freightStation) {
		this.freightStation = freightStation;
	}
	public String[] getYdbhids() {
		return ydbhids;
	}
	public void setYdbhids(String[] ydbhids) {
		this.ydbhids = ydbhids;
	}
	public BundleReceipt getBundle() {
		return bundle;
	}
	public void setBundle(BundleReceipt bundle) {
		this.bundle = bundle;
	}
	public String getYslx() {
		return yslx;
	}
	public void setYslx(String yslx) {
		this.yslx = yslx;
	}
	
}
