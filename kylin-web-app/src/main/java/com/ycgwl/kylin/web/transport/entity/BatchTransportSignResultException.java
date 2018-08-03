package com.ycgwl.kylin.web.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

/**
 * 批量签收异常返回信息
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月21日
 */
public class BatchTransportSignResultException extends BaseEntity {

	private static final long serialVersionUID = 6445340062295856900L;
	
	/** 异常的字段 */
	private String errorField;
	/** 错误信息 */
	private String errorMsg;
	
	private String lrr;
	
	private String lrrGrid;
	
	private String gs;
	
	private Boolean isNewDoc;
	
	public String getErrorField() {
		return errorField;
	}
	public void setErrorField(String errorField) {
		this.errorField = errorField;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getLrr() {
		return lrr;
	}
	public void setLrr(String lrr) {
		this.lrr = lrr;
	}
	public String getLrrGrid() {
		return lrrGrid;
	}
	public void setLrrGrid(String lrrGrid) {
		this.lrrGrid = lrrGrid;
	}
	public String getGs() {
		return gs;
	}
	public void setGs(String gs) {
		this.gs = gs;
	}
	public Boolean getIsNewDoc() {
		return isNewDoc;
	}
	public void setIsNewDoc(Boolean isNewDoc) {
		this.isNewDoc = isNewDoc;
	}

}
