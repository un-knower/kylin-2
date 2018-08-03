package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;
/**
 * 子报表关系和调整信息
 * <p>
 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at 2018/7/4
 */
public class ReportSubReport extends BaseEntity {
	
	/**
	 * 主报表关联ID
	 */
	private Integer reportConfigId;
	
	/**
	 * 子报表链接
	 */
	private String linkUrl;
	
	/**
	 * 多选还是单选
	 */
	private String checkType;
	
	/**
	 * 参数
	 */
	private String params;
	
	/**
	 * 按钮名称
	 */
	private String name;
	
	/**
	 * 子报表ID
	 */
	private Integer subReportId;
	
	/**
	 * 子报表查询条件
	 */
	private String subParams;

	public String getSubParams() {
		return subParams;
	}

	public void setSubParams(String subParams) {
		this.subParams = subParams;
	}

	public Integer getSubReportId() {
		return subReportId;
	}

	public void setSubReportId(Integer subReportId) {
		this.subReportId = subReportId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getReportConfigId() {
		return reportConfigId;
	}

	public void setReportConfigId(Integer reportConfigId) {
		this.reportConfigId = reportConfigId;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}
	
	
}
