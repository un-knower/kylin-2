package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.util.List;

/**
 * 
 * 报表维护<br> 
 * 保存/修改入参
 * @author zdl
 * @version [V1.0, 2018年6月25日]
 */
public class ReportInfoDto  extends BaseEntity{
     
	/**
	 */
	private static final long serialVersionUID = 1L;

	private ReportConfig reportConfig;
	
	List<ReportQuery> reportQueryList;

	public ReportConfig getReportConfig() {
		return reportConfig;
	}

	public void setReportConfig(ReportConfig reportConfig) {
		this.reportConfig = reportConfig;
	}

	public List<ReportQuery> getReportQueryList() {
		return reportQueryList;
	}

	public void setReportQueryList(List<ReportQuery> reportQueryList) {
		this.reportQueryList = reportQueryList;
	}
   
	
	
	
	
}
