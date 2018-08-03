package com.ycgwl.kylin.transport.dto;

import java.io.Serializable;

public class UpdateFreightRecordDto implements Serializable {

	private static final long serialVersionUID = 5197141918149179508L;
	
	public long id;
	
	public String remark;
	
	public String currentAccount;
	
	/**
	 * 操作类型   1：装车到站对事故描述及原因分析
	 * 		  2：装车到站运作责任人处理意见
	 * 	 	  3：装车到站单位负责人处理意见
	 * 		  4：装车发站运作部意见
	 * 		  5：装车发站运作责任人处理意见
	 * 		  6：装车发站单位负责人处理意见
	 * 		  7：营运中心运作管理部意见
	 */
	public String type;
	
	/**
	 * 到站运作负责人已确认  1
	 */
//	public int dzyzlrck = 0;
	
	/**
	 * 到站单位负责人已确认 1
	 */
//	public int dzdwlrck = 0;
	
	/**
	 * 发站运作负责人已确认    1
	 */
//	public int fzyzlrck = 0;
	
	/**
	 * 发展单位负责人已确认     1
	 */
//	public int fzdwlrck = 0;
	
	/**
	 * 到站确认  1
	 */
//	public int dzlrck = 0;
	
	/**
	 * 运营中心确认  1
	 */
//	public int yylrck = 0;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCurrentAccount() {
		return currentAccount;
	}

	public void setCurrentAccount(String currentAccount) {
		this.currentAccount = currentAccount;
	}

//	public int getDzyzlrck() {
//		return dzyzlrck;
//	}
//
//	public void setDzyzlrck(int dzyzlrck) {
//		this.dzyzlrck = dzyzlrck;
//	}
//
//	public int getDzdwlrck() {
//		return dzdwlrck;
//	}
//
//	public void setDzdwlrck(int dzdwlrck) {
//		this.dzdwlrck = dzdwlrck;
//	}
//
//	public int getFzyzlrck() {
//		return fzyzlrck;
//	}
//
//	public void setFzyzlrck(int fzyzlrck) {
//		this.fzyzlrck = fzyzlrck;
//	}
//
//	public int getFzdwlrck() {
//		return fzdwlrck;
//	}
//
//	public void setFzdwlrck(int fzdwlrck) {
//		this.fzdwlrck = fzdwlrck;
//	}
//
//	public int getDzlrck() {
//		return dzlrck;
//	}
//
//	public void setDzlrck(int dzlrck) {
//		this.dzlrck = dzlrck;
//	}
//
//	public int getYylrck() {
//		return yylrck;
//	}
//
//	public void setYylrck(int yylrck) {
//		this.yylrck = yylrck;
//	}
//
//	public String getType() {
//		return type;
//	}
//
//	public void setType(String type) {
//		this.type = type;
//	}
//
//	
//	
}
