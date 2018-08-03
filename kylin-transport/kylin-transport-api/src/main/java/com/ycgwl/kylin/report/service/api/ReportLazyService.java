package com.ycgwl.kylin.report.service.api;

import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.entity.Report;
import com.ycgwl.kylin.transport.entity.ReportConfig;
import com.ycgwl.kylin.transport.entity.ReportQuerySomething;
import com.ycgwl.kylin.transport.entity.ReportSubReport;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 报表服务
 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2018/3/28
 */
public interface ReportLazyService {

    Collection<ReportQuerySomething> listByReportKey(String account, Integer reportKey) throws ParameterException, BusinessException;

    Report loadReportByKey(String account, Integer reportKey, Map<String, String> parmars) throws ParameterException, BusinessException;

    /** 根据报表id查询配置*/
 	public ReportConfig findReportbyKey(Integer reportKey);

 	/** 合计报表金额*/
 	public List<Map<String,Object>> sumReportByKey(String company, Map<String, String> parmars);

 	/**根据父报表和子报表id查询子报表 **/
	ReportSubReport getSubReportByParentIdSubId(Integer parentKey, Integer subReportKey);
}
