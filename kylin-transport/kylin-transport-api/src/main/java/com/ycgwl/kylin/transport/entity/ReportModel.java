package com.ycgwl.kylin.transport.entity;

import java.io.Serializable;
import java.util.List;

public class ReportModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private ReportConfig reportConfig;
	
	private List<ReportQuery> list;

	public ReportConfig getReportConfig() {
		return reportConfig;
	}

	public void setReportConfig(ReportConfig report) {
		this.reportConfig = report;
	}

	public List<ReportQuery> getList() {
		return list;
	}

	public void setList(List<ReportQuery> list) {
		this.list = list;
	}
}
