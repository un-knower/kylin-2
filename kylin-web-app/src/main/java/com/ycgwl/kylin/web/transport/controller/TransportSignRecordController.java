package com.ycgwl.kylin.web.transport.controller;

import com.alibaba.fastjson.JSON;

import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.security.client.ContextHepler;
import com.ycgwl.kylin.security.entity.User;
import com.ycgwl.kylin.transport.entity.*;
import com.ycgwl.kylin.transport.service.api.ITransportOrderService;
import com.ycgwl.kylin.transport.service.api.ITransportSignRecordService;
import com.ycgwl.kylin.util.DateUtils;
import com.ycgwl.kylin.web.transport.BaseController;
import com.ycgwl.kylin.web.transport.entity.ExcelForm;
import com.ycgwl.kylin.web.transport.entity.TransportSignRecordSearchModel;
import com.ycgwl.kylin.web.transport.util.ConveyUtils;
import com.ycgwl.kylin.web.transport.util.ExcelReportUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 签收
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年10月31日
 */
@Controller  
@RequestMapping("/transport/sign")
public class TransportSignRecordController extends BaseController{
	
	public static final String LASTFALSECONVEY = "lastFalseSign";
	public static final String LASTSUCCESSCONVEY = "lastSuccessSign";
	
	@Resource
	private ITransportSignRecordService transportSignRecordService;
	
	@Resource
	private ITransportOrderService transportOrderService;
	
	/**
	 * 跳转主界面
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月3日
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/receivesign")
	public String receiveSign(Model model){  		
		try {
			List<TransportQsztBase> qsztList = transportSignRecordService.getQsztBase();
			model.addAttribute("qsztList", qsztList);
		} catch (ParameterException | BusinessException e) {
			e.printStackTrace();
		}
		return "/transport/convey/receivesign";
	}
	
	/**
	 * 查询运单并校验是否可以签收
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月3日
	 * @param ydbhid
	 * @return
	 */
	@RequestMapping(value = "/search", consumes="application/json")
	@ResponseBody
	public JsonResult searchSign(@RequestBody Map<String,Object> map){
		String ydbhid = map.get("ydbhid").toString();
		JsonResult jsonResult = new JsonResult();
		TransportSignRecordSearch transportSignRecordSearch = new TransportSignRecordSearch();
		User user = new User();
		try {
			//获取用户信息
			user = ContextHepler.getCurrentUser();
			logger.debug("用户操作行为日志:操作->签收查询;工号->"+user.getAccount()+";分公司->"+user.getCompanyString());
		} catch (Exception e) {
			logger.debug("TransportSignRecordController searchSign(param) param:"+JSON.toJSONString(ydbhid) +" 报错："+e.getMessage());
			logger.debug("用户操作行为日志:操作->签收查询");
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "登录已过期，请重新登录！");
		}
		
		transportSignRecordSearch.setYdbhid(ydbhid);
		transportSignRecordSearch.setAccount(user.getAccount());
		transportSignRecordSearch.setUserName(user.getUserName());
		transportSignRecordSearch.setPassword(user.getPassword());
		transportSignRecordSearch.setCreateTime(user.getCreateTime());
		transportSignRecordSearch.setEnable(user.getEnable());
		transportSignRecordSearch.setCompany(user.getCompany());
		transportSignRecordSearch.setCompanyCode(user.getCompanyCode());
		transportSignRecordSearch.setSubCompany(user.getSubCompany());
		
		try{	
			TransportSignRecordResult result = transportSignRecordService.canTransportOrderSign(transportSignRecordSearch);
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "查询成功");
			jsonResult.put("resultInfo", result);
			logger.debug("TransportSignRecordController searchSign(param) param:"+JSON.toJSONString(result));
		}catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("TransportSignRecordController searchSign(param) param:"+JSON.toJSONString(ydbhid) +" 报错："+e.getCause());
		}catch(BusinessException e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("TransportSignRecordController searchSign(param) param:"+JSON.toJSONString(ydbhid) +" 报错："+e.getCause());
		}	
		return jsonResult;
	}
	
	/**
	 * 保存签收
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月3日
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public JsonResult saveSign(@RequestBody TransportSignRecordSearchModel transportSignRecordSearchModel){
		TransportSignRecordEntity transportSignRecordEntity = new TransportSignRecordEntity();
		TransportSignRecord transportSignRecord = new TransportSignRecord();
		transportSignRecord.setQszt(transportSignRecordSearchModel.getQszt());
		transportSignRecord.setQsr(transportSignRecordSearchModel.getQsr());
		transportSignRecord.setPsjianshu(transportSignRecordSearchModel.getPsjianshu());
		transportSignRecord.setDsjianshu(transportSignRecordSearchModel.getDsjianshu());
		transportSignRecord.setYdbhid(transportSignRecordSearchModel.getYdbhid());
		transportSignRecord.setComm(transportSignRecordSearchModel.getComm());
		transportSignRecord.setQsTime(DateUtils.getDetailDate(transportSignRecordSearchModel.getQsTime()));
		transportSignRecord.setQsrphone(transportSignRecordSearchModel.getQsrphone());
		transportSignRecord.setLrTime(DateUtils.getHourAndMinutesDate(transportSignRecordSearchModel.getLrTime()));
		
		JsonResult jsonResult = new JsonResult();
		User user = new User();
		
		try {
			//获取用户信息
			user = ContextHepler.getCurrentUser();
			logger.debug("用户操作行为日志:操作->保存签收;工号->"+user.getAccount()+";分公司->"+user.getCompanyString());
		} catch (Exception e) {
			logger.debug("TransportSignRecordController saveSign(param) param:"+JSON.toJSONString(transportSignRecord) +" 报错："+e.getMessage());
			logger.debug("用户操作行为日志:操作->保存签收");
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "登录已过期，请重新登录！");
			return jsonResult;
		}
					
		transportSignRecordEntity.setTransportSignRecord(transportSignRecord);
		transportSignRecordEntity.setAccount(user.getAccount());
		transportSignRecordEntity.setCompany(user.getCompany());
		transportSignRecordEntity.setCreateTime(user.getCreateTime());
		transportSignRecordEntity.setEnable(user.getEnable());
		transportSignRecordEntity.setPassword(user.getPassword());
		transportSignRecordEntity.setSubCompany(user.getSubCompany());
		transportSignRecordEntity.setUserName(user.getUserName());
		
		try {
			String ydbhid = transportSignRecordService.fullTicketReceipt(transportSignRecordEntity);
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "签收成功");
			jsonResult.put("ydbhid", ydbhid);
			logger.debug("TransportSignRecordController saveSign(param) param:"+JSON.toJSONString(ydbhid));
		} catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("TransportSignRecordController saveSign(param) param:"+JSON.toJSONString(transportSignRecord) +" 报错："+e.getCause());
		}catch(BusinessException e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("TransportSignRecordController saveSign(param) param:"+JSON.toJSONString(transportSignRecord) +" 报错："+e.getCause());
		}	
		return jsonResult;		
	}
	
	/**
	 * 跳转至撤销签收页面
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月29日
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toUndoSign")
	public String toUndoSign(Model model){  		
		try {
			List<TransportQsztBase> qsztList = transportSignRecordService.getQsztBase();
			model.addAttribute("qsztList", qsztList);
		} catch (ParameterException | BusinessException e) {
			e.printStackTrace();
		}
		return "/transport/convey/undoSign";
	}
	
	/**
	 * 查询撤销签收按钮
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月27日
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/searchUndoSign", consumes="application/json")
	@RequiresPermissions("undo:sign:search")
	@ResponseBody
	public JsonResult searchUndoSign(@RequestBody Map<String,Object> map){
		String ydbhid = map.get("ydbhid").toString();
		JsonResult jsonResult = new JsonResult();
		TransportSignRecordSearch transportSignRecordSearch = new TransportSignRecordSearch();
		User user = new User();
		try {
			//获取用户信息
			user = ContextHepler.getCurrentUser();
			logger.debug("用户操作行为日志:操作->签收查询;工号->"+user.getAccount()+";分公司->"+user.getCompanyString());
		} catch (Exception e) {
			logger.debug("TransportSignRecordController searchUndoSign(param) param:"+JSON.toJSONString(ydbhid) +" 报错："+e.getMessage());
			logger.debug("用户操作行为日志:操作->签收查询");
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "登录已过期，请重新登录！");
		}
		
		transportSignRecordSearch.setYdbhid(ydbhid);
		transportSignRecordSearch.setAccount(user.getAccount());
		transportSignRecordSearch.setUserName(user.getUserName());
		transportSignRecordSearch.setPassword(user.getPassword());
		transportSignRecordSearch.setCreateTime(user.getCreateTime());
		transportSignRecordSearch.setEnable(user.getEnable());
		transportSignRecordSearch.setCompany(user.getCompany());
		transportSignRecordSearch.setCompanyCode(user.getCompanyCode());
		transportSignRecordSearch.setSubCompany(user.getSubCompany());
		
		try{	
			TransportSignRecordResult result = transportSignRecordService.searchUndoSign(transportSignRecordSearch);
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "查询成功");
			jsonResult.put("resultInfo", result);
			logger.debug("TransportSignRecordController searchUndoSign(param) param:"+JSON.toJSONString(result));
		}catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("TransportSignRecordController searchUndoSign(param) param:"+JSON.toJSONString(ydbhid) +" 报错："+e.getCause());
		}catch(BusinessException e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("TransportSignRecordController searchUndoSign(param) param:"+JSON.toJSONString(ydbhid) +" 报错："+e.getCause());
		}	
		return jsonResult;
	}
	
	/**
	 * 撤销签收保存按钮
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月29日
	 * @return
	 */
	@RequestMapping(value = "/undoSign")
	@RequiresPermissions("undo:sign:save")
	@ResponseBody
	public JsonResult undoSign(@RequestBody Map<String,Object> map){
		String ydbhid = map.get("ydbhid").toString();
		JsonResult jsonResult = new JsonResult();
		TransportSignRecordSearch transportSignRecordSearch = new TransportSignRecordSearch();
		User user = new User();
		try {
			//获取用户信息
			user = ContextHepler.getCurrentUser();
			logger.debug("用户操作行为日志:操作->签收查询;工号->"+user.getAccount()+";分公司->"+user.getCompanyString());
		} catch (Exception e) {
			logger.debug("TransportSignRecordController undoSign(param) param:"+JSON.toJSONString(ydbhid) +" 报错："+e.getMessage());
			logger.debug("用户操作行为日志:操作->签收查询");
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "登录已过期，请重新登录！");
		}
		
		transportSignRecordSearch.setYdbhid(ydbhid);
		transportSignRecordSearch.setAccount(user.getAccount());
		transportSignRecordSearch.setUserName(user.getUserName());
		transportSignRecordSearch.setPassword(user.getPassword());
		transportSignRecordSearch.setCreateTime(user.getCreateTime());
		transportSignRecordSearch.setEnable(user.getEnable());
		transportSignRecordSearch.setCompany(user.getCompany());
		transportSignRecordSearch.setCompanyCode(user.getCompanyCode());
		transportSignRecordSearch.setSubCompany(user.getSubCompany());
		
		try {
			String result = transportSignRecordService.undoSign(transportSignRecordSearch);
			if(StringUtils.isEmpty(result)){
				jsonResult.put("resultCode", 300);
				jsonResult.put("reason", "没有权限,撤销失败!");
			}
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "撤销签收成功");
			jsonResult.put("ydbhid", result);
			logger.debug("TransportSignRecordController undoSign(param) param:"+JSON.toJSONString(ydbhid));
		} catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("TransportSignRecordController undoSign(param) param:"+JSON.toJSONString(ydbhid) +" 报错："+e.getCause());
		}catch(BusinessException e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("TransportSignRecordController undoSign(param) param:"+JSON.toJSONString(ydbhid) +" 报错："+e.getCause());
		}	
		return jsonResult;		
	}
	
	/**
	 * 跳转至批量签收页面
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月19日
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toBatchsign")
	public String toBatchsign(Model model){  		
		try {
		
		} catch (ParameterException | BusinessException e) {
			e.printStackTrace();
		}
		return "/transport/convey/batchsign";
	}
	
	/**
	 * 批量签收
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月21日
	 * @param request
	 * @param model
	 * @param requestParam
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/save/batch")
	@ResponseBody
	public JsonResult saveSignBatch(HttpServletRequest request, Model model,
                                    @RequestBody List<TransportSignRecordSearchModel> requestParam) {
//		List<TransportSignRecordSearchModel> requestParam = new ArrayList<TransportSignRecordSearchModel>();
//		TransportSignRecordSearchModel a = new TransportSignRecordSearchModel();
//		a.setQsr("墨");
//		a.setQsTime("2017-12-21");
//		a.setQszt(11);
//		a.setYdbhid("H000003245");
//		requestParam.add(a);
		try {
			
			//新文档第一条标记
			Boolean isNewDoc = true;
			// 失败的运单
			List<TransportSignRecordSearchModel> falseList = new ArrayList<TransportSignRecordSearchModel>();
			List<TransportSignRecordSearchModel> sucessList = new ArrayList<TransportSignRecordSearchModel>();
			
			if(requestParam.size() > 0 && requestParam != null){	
					
				List<TransportSignRecordEntity> transportSignRecordEntityLsit = new ArrayList<TransportSignRecordEntity>();
			
				//获取用户信息
				User user = ContextHepler.getCurrentUser();
			
//				User user = new User();
//				user.setAccount("93333");
//				user.setCompany("北京");
//				user.setCreateTime(new Date());
//				user.setEnable(true);
//				user.setPassword("123456");
//				user.setSubCompany("北京");
//				user.setUserName("测试账号");
				logger.debug("用户操作行为日志:操作->批量签收;工号->"+user.getAccount()+";分公司->"+user.getCompanyString());
					
				for (int i = 0; i < requestParam.size(); i++) {
					TransportSignRecordSearchModel saveEntity = requestParam.get(i);
					isNewDoc = saveEntity.getIsNewDoc();
					saveEntity.setLrr(user.getUserName());
					saveEntity.setLrrGrid(user.getAccount());
					saveEntity.setGs(user.getCompany());
					TransportSignRecordEntity transportSignRecordEntity = new TransportSignRecordEntity();
					TransportSignRecord transportSignRecord = new TransportSignRecord();
					transportSignRecord.setQszt(requestParam.get(i).getQszt());
					transportSignRecord.setQsr(requestParam.get(i).getQsr());
					transportSignRecord.setPsjianshu(requestParam.get(i).getPsjianshu());
					transportSignRecord.setDsjianshu(requestParam.get(i).getDsjianshu());
					transportSignRecord.setYdbhid(requestParam.get(i).getYdbhid());
					transportSignRecord.setComm(requestParam.get(i).getComm());
					transportSignRecord.setQsTime(DateUtils.getHourAndMinutesDate2(requestParam.get(i).getQsTime()));
					transportSignRecord.setQsrphone(requestParam.get(i).getQsrphone());
					transportSignRecord.setLrTime(new Date());
					
					transportSignRecordEntity.setTransportSignRecord(transportSignRecord);
					transportSignRecordEntity.setAccount(user.getAccount());
					transportSignRecordEntity.setCompany(user.getCompany());
					transportSignRecordEntity.setCreateTime(user.getCreateTime());
					transportSignRecordEntity.setEnable(user.getEnable());
					transportSignRecordEntity.setPassword(user.getPassword());
					transportSignRecordEntity.setSubCompany(user.getSubCompany());
					transportSignRecordEntity.setUserName(user.getUserName());
					transportSignRecordEntityLsit.add(transportSignRecordEntity);
					
					try {
						//校验并执行签收操作
						boolean flag = transportSignRecordService.batchTransportSignNotCheck(transportSignRecordEntity);
						if(flag){//做过撤销签收的运单，需要查询签收时间
							TransportSignRecord record =  transportSignRecordService.getTransportSignRecordByYdbhid(saveEntity.getYdbhid());
							saveEntity.setQsTime(DateUtils.getCurrentHourAndMinutesTime2(record.getQsTime()));
							TransportOrder transportOrder = transportOrderService.getTransportOrderByYdbhid(saveEntity.getYdbhid());//查询运单
							Integer isfd = transportOrder.getIsfd();
							// 判断是否要求返单
							if (isfd == 1 || isfd == 2) {
								// 是   检查是否返单
								Integer hasReceipt = transportOrder.getHasReceipt();
								if (hasReceipt != 1 ) {
									throw new BusinessException(saveEntity.getYdbhid() + "此运单需要返单，请上传返单");
								}
							}
						}
						sucessList.add(saveEntity);	
					} catch (Exception e) {
						// 失败的签收信息返回到页面上去
						try {
							saveEntity.setErrorField(ConveyUtils.getErrorField(e));
							if (e instanceof BusinessException) {
								saveEntity.setErrorMsg(((BusinessException) e).getTipMessage());
							} else if (e instanceof ParameterException) {
								saveEntity.setErrorMsg(((ParameterException) e).getTipMessage());
							} else {
								saveEntity.setErrorMsg("请详细对照各个字段");
							}
						} catch (Exception e1) {
							exception(e, model);
						}
						falseList.add(saveEntity);
					}
				}
		
			}		
			
			if(isNewDoc){
				request.getSession().setAttribute(LASTSUCCESSCONVEY, sucessList);
				request.getSession().setAttribute(LASTFALSECONVEY, falseList);
			}else{
				
				//获取所有失败的运单
				List<TransportSignRecordSearchModel> oldFailList = (List<TransportSignRecordSearchModel>) request.getSession().getAttribute(LASTFALSECONVEY);
				if(falseList !=null && !falseList.isEmpty()){
					oldFailList.add(falseList.get(0));
				}
				
				//获取所有成功的运单
				List<TransportSignRecordSearchModel> oldSuccList = (List<TransportSignRecordSearchModel>) request.getSession().getAttribute(LASTSUCCESSCONVEY);
				if(sucessList != null && !sucessList.isEmpty()){
					oldSuccList.add(sucessList.get(0));
				}
				request.getSession().setAttribute(LASTSUCCESSCONVEY, oldSuccList);
				request.getSession().setAttribute(LASTFALSECONVEY, oldFailList);
			}
			
			JsonResult jsonResult = JsonResult.getConveyResult("200", "操作成功");
			jsonResult.put("successList", sucessList);
			jsonResult.put("falseList", falseList);
			return jsonResult;
		} catch (Exception e) {
			return JsonResult.getConveyResult("400", "导入失败");
		}
	}
	
	/**
	 * 导出签收成功的订单
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月22日
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downloadSuccBatchSign")
	public void downLoadSuccessExcel(HttpServletRequest request, HttpServletResponse response) {
		List<TransportSignRecordSearchModel> succList = (List<TransportSignRecordSearchModel>) request.getSession().getAttribute(LASTSUCCESSCONVEY);
		if (succList == null || succList.isEmpty()) {
			throw new ParameterException("请求超时,签收成功的记录已经导出过");
		}
		// 设置excel信息
		String[] showColumnName = {  "运单编号", "签收时间", "签收状态", "签收人", "导入人", "导入人工号", "导入人公司"};// 列名
		List<String[]> exportContentList = new ArrayList<String[]>();
		for (TransportSignRecordSearchModel result : succList) {
			String content[] = { result.getYdbhid(), result.getQsTime(), result.getQszt().toString(), result.getQsr(),
					result.getLrr(), result.getLrrGrid(), result.getGs() };
			exportContentList.add(content);
		}
		String sheetName = "签收成功记录";
		String fileName = sheetName;
		Integer showColumnWidth[] = { 15, 15, 15, 15, 15, 15, 15 };// 列宽
		ExcelForm excelForm = new ExcelForm();
		excelForm.setFileName(fileName);// excel文件名称（不包含后缀)
		excelForm.setList(exportContentList);// 导出的内容
		excelForm.setSheetName(sheetName);
		excelForm.setShowColumnName(showColumnName);
		excelForm.setShowColumnWidth(showColumnWidth);
		try{
			ExcelReportUtils.exportExcel(excelForm, response);
		}catch(Exception e){
			logger.debug("签收成功记录导出异常！报错内容：" + e.getMessage());
		}
	}
	
	/**
	 * 导出签收失败的订单
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月22日
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downloadFalseBatchSign")
	public void downLoadFalseExcel(HttpServletRequest request, HttpServletResponse response) {
		List<TransportSignRecordSearchModel> falseList = (List<TransportSignRecordSearchModel>) request.getSession().getAttribute(LASTFALSECONVEY);
		if (falseList == null || falseList.isEmpty()) {
			throw new ParameterException("请求超时,签收失败的记录已经导出过");
		}
		// 设置excel信息
		String[] showColumnName = { "运单编号", "签收时间", "签收状态", "签收人" };// 列名
		List<String[]> exportContentList = new ArrayList<String[]>();
		for (TransportSignRecordSearchModel result : falseList) {
			String content[] = { result.getYdbhid(), result.getQsTime(), result.getQszt().toString(), result.getQsr() };
			exportContentList.add(content);
		}
		String sheetName = "签收失败记录";
		String fileName = sheetName;
		Integer showColumnWidth[] = { 15, 15, 15, 15 };// 列宽
		ExcelForm excelForm = new ExcelForm();
		excelForm.setFileName(fileName);// excel文件名称（不包含后缀)
		excelForm.setList(exportContentList);// 导出的内容
		excelForm.setSheetName(sheetName);
		excelForm.setShowColumnName(showColumnName);
		excelForm.setShowColumnWidth(showColumnWidth);
		try{
			ExcelReportUtils.exportExcel(excelForm, response);
		}catch(Exception e){
			logger.debug("签收失败记录导出异常！报错内容：" + e.getMessage());
		}
	}
}
