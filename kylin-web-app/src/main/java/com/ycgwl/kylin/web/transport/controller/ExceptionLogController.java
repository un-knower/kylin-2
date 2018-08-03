package com.ycgwl.kylin.web.transport.controller;

import com.alibaba.fastjson.JSON;
import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.entity.PageInfo;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.security.client.ContextHepler;
import com.ycgwl.kylin.security.entity.User;
import com.ycgwl.kylin.transport.entity.ExceptionLogResult;
import com.ycgwl.kylin.transport.entity.ExceptionLogSearch;
import com.ycgwl.kylin.transport.service.api.IExceptionLogService;
import com.ycgwl.kylin.web.transport.BaseController;
import com.ycgwl.kylin.web.transport.entity.ExcelForm;
import com.ycgwl.kylin.web.transport.entity.ExceptionLogSearchModel;
import com.ycgwl.kylin.web.transport.util.ExcelReportUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * 异常日志controller
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月4日
 */
@Controller  
@RequestMapping("/transport/exceptionLog")
public class ExceptionLogController extends BaseController{

	@Resource
	private IExceptionLogService exceptionLogService;
	
	/**
	 * 跳转至分页查询异常日志界面
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月5日
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toOperationLog")
	public String toOperationLog(Model model) {
		return "transport/convey/operationlog";
	}
	
	/**
	 * 分页查询异常日志
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月4日
	 * @param searchModel
	 * @return
	 */
	@RequestMapping(value = "/search")
	@ResponseBody
	public JsonResult searchExceptionLog(@RequestBody ExceptionLogSearchModel searchModel){
		JsonResult jsonResult = new JsonResult();
		
		User user = new User();
		try {
			//获取用户信息
			user = ContextHepler.getCurrentUser();
			logger.debug("用户操作行为日志:操作->签收查询;工号->"+user.getAccount()+";分公司->"+user.getCompanyString());
		} catch (Exception e) {
			logger.debug("TransportSignRecordController searchExceptionLog(param) param:"+JSON.toJSONString(searchModel) +" 报错："+e.getMessage());
			logger.debug("用户操作行为日志:操作->签收查询");
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "登录已过期，请重新登录！");
		}
		
		Integer pageNum = searchModel.getNum();
		if(pageNum == null){
			pageNum = 1;
		}
		Integer pageSize = searchModel.getSize();
		if(pageSize == null){
			pageSize = 10;
		}

		ExceptionLogSearch exceptionLogSearch = new ExceptionLogSearch();
		exceptionLogSearch.setOperatingMenu(searchModel.getOperatingMenu());
		exceptionLogSearch.setYdbhid(searchModel.getYdbhid());
		if(StringUtils.isNotEmpty(searchModel.getCwpzhbh())){
			exceptionLogSearch.setCwpzhbh(new BigDecimal(searchModel.getCwpzhbh()));
		}		
		exceptionLogSearch.setOperatingTimeBegin(searchModel.getOperatingTimeBegin());
		exceptionLogSearch.setOperatingTimeEnd(searchModel.getOperatingTimeEnd());	
		exceptionLogSearch.setOperatorCompany(user.getCompany());
		
		try {
			PageInfo<ExceptionLogResult> resultPage = exceptionLogService.pageTransportOrder(exceptionLogSearch, pageNum, pageSize);
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "查询成功");
			jsonResult.put("resultInfo", resultPage);
			logger.debug("TransportSignRecordController searchExceptionLog(param) param:" + JSON.toJSONString(resultPage));
		} catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("TransportSignRecordController searchExceptionLog(param) param:" + JSON.toJSONString(searchModel) + " 报错："+e.getCause());
		}catch(BusinessException e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("TransportSignRecordController searchExceptionLog(param) param:" + JSON.toJSONString(searchModel) + " 报错："+e.getCause());
		}	
				
		return jsonResult; 		
	}
	
	/**
	 * 导出异常日志列表
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月6日
	 * @param searchModel
	 */
	@RequestMapping(value = "/exportExceptionLog",method = RequestMethod.GET)
	public void exportExceptionLog(HttpServletResponse response,String operatingMenu,String ydbhid, 
			String cwpzhbh,String operatingTimeBegin,String operatingTimeEnd) {
		List<String[]> list = new ArrayList<String[]>();
		
		User user = new User();
		try {
			//获取用户信息
			user = ContextHepler.getCurrentUser();
			logger.debug("用户操作行为日志:操作->签收查询;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		} catch (Exception e) {
			logger.debug("TransportSignRecordController exportExceptionLog(param) param:" + " 报错："+e.getMessage());
			logger.debug("用户操作行为日志:操作->签收查询");
		}
		
		ExceptionLogSearchModel searchModel = new ExceptionLogSearchModel();
		searchModel.setOperatingMenu(operatingMenu);
		searchModel.setOperatingTimeBegin(operatingTimeBegin);
		searchModel.setOperatingTimeEnd(operatingTimeEnd);
		searchModel.setYdbhid(ydbhid);
		searchModel.setCwpzhbh(cwpzhbh);
		
		Integer	pageNum = 1;
		Integer	pageSize = 2000000000;
	
		ExceptionLogSearch exceptionLogSearch = new ExceptionLogSearch();
		exceptionLogSearch.setOperatingMenu(searchModel.getOperatingMenu());
		exceptionLogSearch.setYdbhid(searchModel.getYdbhid());
		exceptionLogSearch.setOperatorCompany(user.getCompany());
		if(StringUtils.isNotEmpty(searchModel.getCwpzhbh())){
			exceptionLogSearch.setCwpzhbh(new BigDecimal(searchModel.getCwpzhbh()));
		}
	
		if(StringUtils.isNotEmpty(searchModel.getOperatingTimeBegin()) && StringUtils.isNotEmpty(searchModel.getOperatingTimeEnd())){
			
			exceptionLogSearch.setOperatingTimeBegin(searchModel.getOperatingTimeBegin());
			exceptionLogSearch.setOperatingTimeEnd(searchModel.getOperatingTimeEnd());
		}

		try {
			PageInfo<ExceptionLogResult> resultPage = exceptionLogService.pageTransportOrder(exceptionLogSearch, pageNum, pageSize);
			Collection<ExceptionLogResult> resultList = resultPage.getCollection();
			if (resultPage.getCollection().size() > 0) {
				int i = 0;
				for (ExceptionLogResult exceptionLog : resultList) {
					if(StringUtils.isNotEmpty(exceptionLog.getYdbhid()) && exceptionLog.getCwpzhbh() == null){
						String[] exceptionLogForOutArray = { i + 1 + "", exceptionLog.getOperatorName(),
								exceptionLog.getOperatorAccount(),exceptionLog.getOperatorCompany(),
								exceptionLog.getIpAddress(),exceptionLog.getYdbhid(),
								exceptionLog.getOperatingMenu(),exceptionLog.getOperatingContent(),
								exceptionLog.getOperatingTime()};
						list.add(exceptionLogForOutArray);
					}
					if(StringUtils.isEmpty(exceptionLog.getYdbhid()) && exceptionLog.getCwpzhbh() != null){
						String[] exceptionLogForOutArray = { i + 1 + "", exceptionLog.getOperatorName(),
								exceptionLog.getOperatorAccount(),exceptionLog.getOperatorCompany(),
								exceptionLog.getIpAddress(),exceptionLog.getCwpzhbh().toString(),
								exceptionLog.getOperatingMenu(),exceptionLog.getOperatingContent(),
								exceptionLog.getOperatingTime()};
						list.add(exceptionLogForOutArray);
					}	
					i++;
				}
			}
			
			// 设置excel信息
			String[] showColumnName = {"序号", "操作人名称", "操作人账号", "操作人公司", "IP地址", "运单号/财凭号",
					"操作菜单", "操作内容", "操作时间" };// 列名
			String sheetName = "异常日志查询";
			Integer showColumnWidth[] = {8, 15, 15, 15, 15, 15, 15, 45, 20 };// 列宽
			String fileName = "异常日志";
			String total = "异常日志";
			
			String implParam = "";
			String search = "";
			String menu = "按";		
			if(StringUtils.isNotEmpty(exceptionLogSearch.getOperatingTimeBegin()) && StringUtils.isNotEmpty(exceptionLogSearch.getOperatingTimeEnd())){
				search += "操作时间：" + exceptionLogSearch.getOperatingTimeBegin() + "至 " + exceptionLogSearch.getOperatingTimeEnd();
				menu += "操作时间";
			} 
			if(StringUtils.isNotEmpty(exceptionLogSearch.getOperatingMenu())){
				search += "  操作菜单：" + exceptionLogSearch.getOperatingMenu();
				menu += "、操作菜单";
			}	
			if(StringUtils.isNotEmpty(exceptionLogSearch.getYdbhid())){
				search += "  运单编号：" + exceptionLogSearch.getYdbhid();
				menu += "、运单编号";
			}
			if(exceptionLogSearch.getCwpzhbh() != null){
				search += "  财凭号：" + exceptionLogSearch.getCwpzhbh();
				menu += "、财凭号";
			}
			if(StringUtils.isEmpty(exceptionLogSearch.getOperatingTimeBegin()) && StringUtils.isEmpty(exceptionLogSearch.getOperatingTimeEnd())
					&& StringUtils.isEmpty(exceptionLogSearch.getOperatingMenu()) && StringUtils.isEmpty(exceptionLogSearch.getYdbhid()) 
					&& exceptionLogSearch.getCwpzhbh() == null){
				implParam = "查询全部";
			}else{
				implParam = search + "            " + menu + "查询";
			}
			
			ExcelForm excelForm = new ExcelForm();
			excelForm.setFileName(fileName);// excel文件名称（不包含后缀)
			excelForm.setTotal(total);
			excelForm.setImplParam(implParam);
			excelForm.setList(list);// 导出的内容
			excelForm.setSheetName(sheetName);
			excelForm.setShowColumnName(showColumnName);
			excelForm.setShowColumnWidth(showColumnWidth);
			ExcelReportUtils.exportExcel(excelForm, response);
			
			logger.debug("TransportSignRecordController exportExceptionLog(param) param:"+JSON.toJSONString(resultPage));
		} catch (ParameterException e) {
			logger.debug("TransportSignRecordController exportExceptionLog(param) param:"+JSON.toJSONString(exceptionLogSearch) +" 报错："+e.getCause());
		}catch(BusinessException e){
			logger.debug("TransportSignRecordController exportExceptionLog(param) param:"+JSON.toJSONString(exceptionLogSearch) +" 报错："+e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("TransportSignRecordController exportExceptionLog(param) param:"+JSON.toJSONString(exceptionLogSearch) +" 报错："+e.getCause());
		}
	}
}
