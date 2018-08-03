package com.ycgwl.kylin.transport.dto;

import com.ycgwl.kylin.entity.RequestEntity;

/**
 *  等托运人发货指令 查询参数dto
 * @author chenxm
 *
 */
public class ConsignorDeliveryInstructDto extends RequestEntity{
	
	private static final long serialVersionUID = 3049732001758993887L;
	
	/** 1:等待放货  2:所有 */
	public int type;
	
	/** 时间范围  */
	public int timeFrame = 3;
	
	/** 公司 */
	public String companyName;
	
	/** 运单编号 */
	public String waybillNum;
	
	/** 托运人指令操作类型  1:设置放货  3:取消放货   2:通知放货  4：取消通知放货*/
	public int waitType;
	
	/** 当前登录账户 */
	public String currentAcount;
	
	/** 备注 */
	public String remark;
	
	/** 不知道什么意思 */
	public String ddfhczygh;
	
	/** 操作 ipAddr */
	public String ipAddr;
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}


	public int getTimeFrame() {
		return timeFrame;
	}

	public void setTimeFrame(int timeFrame) {
		this.timeFrame = timeFrame;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getWaybillNum() {
		return waybillNum;
	}

	public void setWaybillNum(String waybillNum) {
		this.waybillNum = waybillNum;
	}

	public Integer getWaitType() {
		return waitType;
	}

	public void setWaitType(Integer waitType) {
		this.waitType = waitType;
	}

	public String getCurrentAcount() {
		return currentAcount;
	}

	public void setCurrentAcount(String currentAcount) {
		this.currentAcount = currentAcount;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setWaitType(int waitType) {
		this.waitType = waitType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDdfhczygh() {
		return ddfhczygh;
	}

	public void setDdfhczygh(String ddfhczygh) {
		this.ddfhczygh = ddfhczygh;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	
	

}
