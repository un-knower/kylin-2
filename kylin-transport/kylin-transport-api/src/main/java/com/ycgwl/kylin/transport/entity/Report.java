package com.ycgwl.kylin.transport.entity;/**
 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2018/3/28
 */

import com.ycgwl.kylin.entity.BaseEntity;

import java.util.List;
import java.util.Map;

/**
 * 报表
 * <p>
 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2018/3/28
 */
public class Report extends BaseEntity {
	private static final long serialVersionUID = -465421L;
	private String[] headers;
    private List<Map<String, Object>> dataCollection;
    private Integer count;
    private String reportName;
    private String reportHeadNames;
    private String sumReportKeys;
    private List<ReportSubReport> subList;
    
    public List<ReportSubReport> getSubList() {
		return subList;
	}

	public void setSubList(List<ReportSubReport> subList) {
		this.subList = subList;
	}

	public String getSumReportKeys() {
		return sumReportKeys;
	}

	public void setSumReportKeys(String sumReportKeys) {
		this.sumReportKeys = sumReportKeys;
	}

	public String getReportHeadNames() {
		return reportHeadNames;
	}

	public void setReportHeadNames(String reportHeadNames) {
		this.reportHeadNames = reportHeadNames;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String[] getHeaders() {
        return headers;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }

    public List<Map<String, Object>> getDataCollection() {
        return dataCollection;
    }

    public void setDataCollection(List<Map<String, Object>> dataCollection) {
        this.dataCollection = dataCollection;
    }
}
