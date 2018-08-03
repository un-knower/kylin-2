package com.ycgwl.kylin.web.transport.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ycgwl.kylin.entity.Page;
import com.ycgwl.kylin.entity.PageInfo;
import com.ycgwl.kylin.security.client.ContextHepler;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.security.entity.User;
import com.ycgwl.kylin.transport.entity.Report;
import com.ycgwl.kylin.transport.entity.ReportConfig;
import com.ycgwl.kylin.transport.entity.ReportInfoDto;
import com.ycgwl.kylin.transport.entity.ReportQuery;
import com.ycgwl.kylin.transport.entity.ReportSubReport;
import com.ycgwl.kylin.transport.service.api.ReportService;
import com.ycgwl.kylin.web.transport.util.ExcelReportForDelivery;

/**
 * 报表列表
 * @author fusen.feng
 * @version 2018-03-29 
 */

@Controller
@RequestMapping("/report/")
public class ReportController {
	@Resource
	ReportService reportService;
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 跳转到报表页
	 */
	@RequestMapping("index")
	public String reportIndex(Model model,String reportKey) {
		String regEx="[^0-9]";  
		Pattern p = Pattern.compile(regEx);  
		Matcher m = p.matcher(reportKey);  
		model.addAttribute("reportKey", m.replaceAll("").trim());
		User user = ContextHepler.getCurrentUser();
		model.addAttribute("company", user.getCompany());
		return "transport/report/reportIndex";
	}
	
	/**
	 * 带查询条件的跳转
	 */
	@RequestMapping("indexWithParams")
	public String indexWithParams(Model model,HttpServletRequest request) {
		String regEx="[^0-9]";  
		Pattern p = Pattern.compile(regEx);  
		Matcher m = p.matcher(request.getParameter("reportKey"));  
		String subReportKey = m.replaceAll("").trim();
		model.addAttribute("reportKey", subReportKey);
		User user = ContextHepler.getCurrentUser();
		model.addAttribute("company", user.getCompany());
		String queryString = request.getQueryString();
		queryString = queryString.substring(queryString.indexOf("&")+1,queryString.length());
		String parentKey = request.getParameter("parentKey");
		ReportSubReport subReport = reportService.getSubReportByParentIdSubId(Integer.parseInt(parentKey),Integer.parseInt(subReportKey));
		if(subReport!=null) {
			String parentParam[] = subReport.getParams().split(",");
			String subParam[] = subReport.getSubParams().split(",");
			for (int i=0;i<parentParam.length;i++) {
				queryString = queryString.replace(parentParam[i], subParam[i]);
			}
			queryString += "&withPara=1";
			
			request.setAttribute("params",queryString);
		}else {
			request.setAttribute("SUB_REPORT_ERROR","没有配置子报表");
		}
		return "transport/report/reportIndexWithParams";
	}
	
	/**
	 * 跳转到报表的维护页
	 */
	@RequestMapping("report")
	public String report() {
		return "transport/report/report";
	}
	
	/**
	 * 查询所有报表
	 */
	@RequestMapping("report/data")
	@ResponseBody
	public JsonResult listConfig(){
		JsonResult jsonResult = new JsonResult();		
		try {			
			jsonResult.put("reportList",reportService.listConfig());
		} catch (Exception e) {
			logger.error("###ERROR 查询所有报表 ------", e);
			return JsonResult.getConveyResult("400", e.getMessage());
		}
		return jsonResult;
	}
		
	/**
	 * 查询报表配置
	 */
	@RequestMapping("query/config")
	@ResponseBody
	public JsonResult queryConfig(@RequestBody HashMap<String,String> map){
		JsonResult jsonResult = new JsonResult();
		User user = ContextHepler.getCurrentUser();
		
		try {
			int key = Integer.valueOf(map.get("reportKey").toString());
			jsonResult.put("reportConfig", reportService.findReportbyKey(key));
			jsonResult.put("reportQuery",reportService.listByReportKey(user.getAccount(), key));
		} catch (Exception e) {
			logger.error("###ERROR 查询报表配置 ------", e);
			return JsonResult.getConveyResult("400", e.getMessage());
		}
		return jsonResult;
	}
	
	/**
	 * 添加和修改报表
	 */
	@RequestMapping("save/reportDeploy")
	@ResponseBody
	public JsonResult saveDeploy(@RequestBody ReportConfig rConfig) {
		JsonResult jsonResult = new JsonResult();		
		try {			
			/** 添加报表基本数据 和配置*/
			reportService.saveOrUptReport(rConfig);
			jsonResult.put("status", 200);
		} catch (Exception e) {
			jsonResult.put("message", "系统异常!");
			logger.error("###ERROR 添加报表配置------", e);
		}
		return jsonResult;
	}	
	
	/**
	 * 查询报表数据
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("query/data")
	@ResponseBody
	public JsonResult queryData(@RequestBody HashMap<String,String> map,HttpServletRequest request){
		JsonResult jsonResult = new JsonResult();
		User user = ContextHepler.getCurrentUser();
		String company = user.getCompany();
		String selectCompany=map.get("company");
		try {
			if (StringUtils.isNotEmpty(selectCompany) && !company.equals(selectCompany)){
				company = selectCompany;
			}
			jsonResult.put("status", 200);
			request.getSession().setAttribute("OUTPUT_COMPANY_NAME", company);
			Report report = reportService.loadReportByKey(company, Integer.valueOf(map.get("reportKey")), map);
			if (null != report){
			    // 设置分页返回信息
		 		int startRow = 0;
		 		int endRow = 0;
		 		int pages = 0;
		 		int pageNum = Integer.valueOf(map.get("pageNum"));
		 		int pageSize = Integer.valueOf(map.get("pageSize"));
		 		int total = report.getCount();
		 		if (pageNum > 0 && pageSize > 0) {
		 			startRow = (pageNum - 1) * pageSize;
		 			pages = (int) Math.ceil((double) total / pageSize);
		 		}
		 		endRow = startRow + pageSize;				
			  jsonResult.put("headers",report.getHeaders());
			  //jsonResult.put("subReportList",report.getSubList());
			  Page page = new Page(pageNum, pageSize, startRow, endRow, total,
						pages, report.getDataCollection());
			  jsonResult.put("page",new PageInfo(page));
			}
		} catch (Exception e) {
			logger.error("###ERROR 查询报表数据 ------", e);
			return JsonResult.getConveyResult("400", e.getMessage());
		}                                     
		return jsonResult;
	}
	
	/**
	 * 查询报表需要合计金额
	 */	
	@RequestMapping("query/sumReport")
	@ResponseBody
	public JsonResult sumReport(@RequestBody HashMap<String,String> map) {
		JsonResult jsonResult = new JsonResult();
		User user = ContextHepler.getCurrentUser();
		String company = user.getCompany();
		String selectCompany=map.get("company");
		try {
			if (StringUtils.isNotEmpty(selectCompany) && !company.equals(selectCompany)){
				company = selectCompany;
			}					
			jsonResult.put("sumReport",reportService.sumReportByKey(company, map));
			jsonResult.put("status", 200);
			
		} catch (Exception e) {
			logger.error("###ERROR 查询报表合计数据 ------", e);
			return JsonResult.getConveyResult("400", "系统异常!");
		}
		return jsonResult;
	}
	
	/**
	 * 导出报表数据
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping("export/data")
	public void exportData(@RequestBody HashMap<String,String> map,HttpServletRequest request,HttpServletResponse response){
		User user = ContextHepler.getCurrentUser();
		HSSFWorkbook wb = null ;
		FileOutputStream fout = null;
		String company = (String) request.getSession().getAttribute("OUTPUT_COMPANY_NAME");
		try {
			map.put("export", "true");
			/** 实施进行导出*/
			Report report = reportService.loadReportByKey(company, Integer.valueOf(map.get("reportKey")), map);
			
			/** 返回类型*/
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setContentType("application/octet-stream;charset=UTF-8");         
            response.addHeader("Content-Disposition", "attachment;filename=" + "report.xls");            
            
            /** 定义表格*/
			wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet(report.getReportName());
            HSSFRow row = sheet.createRow(0);
            HSSFFont font = wb.createFont();
            font.setFontName("宋体");
            font.setFontHeightInPoints((short) 7); 
            
            /** 合计数据*/
            if (StringUtils.isNotEmpty(report.getSumReportKeys())) {
            	String [] heads = report.getSumReportKeys().split(",");
            	List<Map<String, Object>> sumList =reportService.sumReportByKey(company, map);
            	if (CollectionUtils.isNotEmpty(sumList)) {
            		Map<String, Object> rstMap1 = sumList.get(1);
            		int y =0;
            		String value = "";
            		for (int i=0;heads.length>i;i++) {
            			if (y>0){
            				row.createCell(y+1).setCellValue(heads[i]);
            				++y;
            			} else {
            		    	row.createCell(i).setCellValue(heads[i]);
            		    }
            			++y;
            			if (null != rstMap1.get(heads[i].trim())){
            				value = rstMap1.get(heads[i].trim()).toString();
            			}
            			row.createCell(y).setCellValue(value.replaceAll("\"", ""));
            		}
            	} 
            }
                                  
            /** 定义表头*/
            String [] arrayHeads = report.getReportHeadNames().split(",");
            row = sheet.createRow(1);
            for(int i=0;arrayHeads.length>i ; i++){
            	row.createCell(i).setCellValue(arrayHeads[i]);
            }
                                 
            /** 查询数据*/
			List<Map<String, Object>> list =  report.getDataCollection();
            String [] array = report.getHeaders();
            if (CollectionUtils.isNotEmpty(list)) {
            	for(int i=0 ;i<list.size() ; i++){
            		row = sheet.createRow(i + 2);
                    Map maps = list.get(i);
                    for (int y=0 ;y<array.length ;y++){
                        row.createCell(y).setCellValue(ExcelReportForDelivery.checkValue(maps.get(array[y])));
                    }
            	}
            }
            
            /** 输出到根目录*/
            fout = new FileOutputStream(request.getSession().getServletContext().getRealPath("/") + "static" + File.separator
					+ "upload" + File.separator + "report.xls");
            wb.write(fout);
            
		} catch (Exception e) {
			logger.error("###ERROR 导出报表数据------", e);
		} finally {
			try {
				fout.close();
				wb.close();
			} catch (Exception e) {
				logger.error("###ERROR 导出报表数据关闭流异常！", e);
			}
		}		
	}
	
	 
	
	/**
	 * 新
	 * 保存/修改report_query: <br>
	 * @author zdl
	 * @version [V1.0, 2018年6月25日]
	 * @return
	 */
	@RequestMapping("/saveDeployNew")
	@ResponseBody
	public JsonResult saveDeployNew(@RequestBody ReportInfoDto reportInfoDto) {
		JsonResult jsonResult = new JsonResult();		
		try {			
			/** 添加报表基本数据 和配置*/
			jsonResult = reportService.saveOrUptReportNew(reportInfoDto.getReportConfig(),reportInfoDto.getReportQueryList());
		} catch (Exception e) {
			return JsonResult.getConveyResult("400", e.getMessage());
			 
		}
		return jsonResult;
	}
	

	
	/**
	 * 
	 * 删除 report_query: <br>
	 * @author zdl
	 * @version [V1.0, 2018年6月25日]
	 * @return
	 */
	@RequestMapping(value = "/deleteReportQuery")
	@ResponseBody
	public JsonResult deleteReportQuery(@RequestBody ReportQuery reportQuery ) {
		JsonResult jsonResult = new JsonResult();
		try {
			
			/** 添加报表基本数据 和配置*/
			jsonResult = reportService.deleteReportQuery(reportQuery);
			
		} catch (Exception e) {
			logger.error("###ERROR 删除report_query ------", e);
		}
	        return jsonResult;
		
	}	  
	
	
	/**
	 * 
	 * 添加锁定功能: <br>
	 * @author zdl
	 * @version [V1.0, 2018年6月28日]
	 * @return
	 */
	@RequestMapping(value = "/reportLock")
	@ResponseBody
	public JsonResult reportLock(@RequestBody ReportConfig rConfig) {
		JsonResult jsonResult = new JsonResult();
		try {		
			/**设置报表锁定状态**/
			jsonResult = reportService.lockReport(rConfig);
		} catch (Exception e) {
			logger.error("###ERROR 锁定reportLock ------", e);
		}
	        return jsonResult;
		
	}
	
	
	
	
	
	
}
