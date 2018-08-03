package com.ycgwl.kylin.web.transport.controller;

import com.alibaba.fastjson.JSON;
import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.security.client.ContextHepler;
import com.ycgwl.kylin.security.entity.User;
import com.ycgwl.kylin.transport.entity.FinanceReceiveMoney;
import com.ycgwl.kylin.transport.entity.FinanceReceiveMoneyForm;
import com.ycgwl.kylin.transport.entity.FinanceReceiveMoneyResult;
import com.ycgwl.kylin.transport.entity.adjunct.Company;
import com.ycgwl.kylin.transport.service.api.AdjunctSomethingService;
import com.ycgwl.kylin.transport.service.api.FinanceReceiveMoneyService;
import com.ycgwl.kylin.web.transport.BaseController;
import com.ycgwl.kylin.web.transport.entity.ExcelForm;
import com.ycgwl.kylin.web.transport.util.ExcelReportUtils;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 财凭相关controller
 * 
 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at
 *            2017年11月1日
 */
@Controller
@RequestMapping("/transport/finance")
public class FinanceTransportController extends BaseController {
	Logger logger = LoggerFactory.getLogger(FinanceTransportController.class);

	@Autowired
	private FinanceReceiveMoneyService financeReceiveMoneyService;
	@Resource
	private AdjunctSomethingService adjunctSomethingService;

	/**
	 * @Description:收钱默认加载页面
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/receivemoney")
	public String searchReceiveMoney(HttpServletRequest request) {
		Collection<Company> sessionList = (Collection<Company>) request.getSession().getAttribute("COMPANY_LIST");
		if (sessionList == null) {
			Collection<Company> list = adjunctSomethingService.queryStationCompany();
			request.getSession().setAttribute("COMPANY_LIST", list);
			request.getSession().setAttribute("CURR_COMPANY_NAME", ContextHepler.getCurrentUser().getCompany());
			request.getSession().setAttribute("CURR_COMPANY_CODE", ContextHepler.getCurrentUser().getCompanyCode());
		}
		return "/transport/convey/receivemoney";
	}

	/**
	 * @Description:收钱查询按钮
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/searchReceiveMoney")
	@ResponseBody
	public JsonResult searchReceiveMoney(HttpServletRequest request, @RequestBody FinanceReceiveMoneyForm searchForm) {
		try {
			User user = ContextHepler.getCurrentUser();
			logger.debug("用户操作行为日志:操作->收钱查询;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
			searchForm.setCompanyName(user.getCompany());
		} catch (Exception e) {
			logger.debug("FinanceTransportController searchReceiveMoney(param) param:" + JSON.toJSONString(searchForm)
					+ " 报错：" + e.getMessage());
			logger.debug("用户操作行为日志:操作->收钱查询");
			JsonResult jsonResult = new JsonResult();
			jsonResult.put("resultCode", 500);
			jsonResult.put("reason", "登录已过期，请重新登录！");
			return jsonResult;
		}
		try {
			JsonResult jsonResult = new JsonResult();
			jsonResult.put("resultCode", 200);
			FinanceReceiveMoneyResult financeResult = financeReceiveMoneyService.searchBeforeReceiveMoney(searchForm);
			// 记录在session用于保存

			request.getSession().setAttribute("FINA_REC_MONEY", financeResult.getSessionMoney());
			jsonResult.put("message", financeResult);
			jsonResult.put("reason", "查询请求成功");
			logger.debug("FinanceTransportController searchBeforeReceiveMoney(param) param:"
					+ JSON.toJSONString(jsonResult));
			return jsonResult;
		} catch (Exception e) {
			logger.debug("FinanceTransportController searchBeforeReceiveMoney(param) param:"
					+ JSON.toJSONString(searchForm) + " 报错：" + e.getMessage());
			JsonResult jsonResult = new JsonResult();
			jsonResult.put("resultCode", 500);
			jsonResult.put("reason", "查询请求报错，请联系系统维护人员");
			return jsonResult;
		}
	}

	/**
	 * @Description:收钱保存按钮
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveReceiveMoney")
	@ResponseBody
	public JsonResult saveReceiveMoney(HttpServletRequest request, @RequestBody FinanceReceiveMoneyForm saveForm) {
		try {
			User user = ContextHepler.getCurrentUser();
			logger.debug("用户操作行为日志:操作->收钱保存;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
			saveForm.setGrid(user.getAccount());
			saveForm.setChuna(user.getUserName());
			saveForm.setCompanyName(user.getCompany());
		} catch (Exception e) {
			logger.debug("FinanceTransportController searchReceiveMoney(param) param:" + JSON.toJSONString(saveForm)
					+ " 报错：" + e.getMessage());
			logger.debug("用户操作行为日志:操作->收钱保存");
			JsonResult jsonResult = new JsonResult();
			jsonResult.put("resultCode", 500);
			jsonResult.put("reason", "登录已过期，请重新登录！");
			return jsonResult;
		}
		// 页面接收内容展示
		try {
			JsonResult jsonResult = new JsonResult();
			jsonResult.put("resultCode", 200);
			FinanceReceiveMoney sessionMoney = (FinanceReceiveMoney) request.getSession()
					.getAttribute("FINA_REC_MONEY");
			logger.debug("保存方法sessionMoney=" + JSON.toJSONString(sessionMoney));
			FinanceReceiveMoneyResult financeResult = financeReceiveMoneyService.saveReceviceMoney(saveForm,
					sessionMoney);
			jsonResult.put("message", financeResult);
			jsonResult.put("reason", "保存请求成功");
			logger.debug("FinanceTransportController saveReceiveMoney() return json:" + JSON.toJSONString(jsonResult));
			return jsonResult;
		} catch (ParameterException e) {
			logger.debug("FinanceTransportController searchReceiveMoney(param) param:" + JSON.toJSONString(saveForm)
					+ " 报错：" + e.getMessage());
			JsonResult jsonResult = new JsonResult();
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "保存请求报错，" + e.getTipMessage());
			return jsonResult;
		} catch (BusinessException e) {
			logger.debug("FinanceTransportController searchReceiveMoney(param) param:" + JSON.toJSONString(saveForm)
					+ " 报错：" + e.getMessage());
			JsonResult jsonResult = new JsonResult();
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "保存请求报错，" + e.getTipMessage());
			return jsonResult;
		} catch (Exception e) {
			JsonResult jsonResult = new JsonResult();
			jsonResult.put("resultCode", 400);
			if (e.getMessage().indexOf("Transaction timed out") > 1) {
				jsonResult.put("reason", "因服务器繁忙收钱失败（已取消该操作），请重试");
			} else {
				jsonResult.put("reason", "收钱失败报错，其他异常情况");
			}
			logger.debug("FinanceTransportController searchReceiveMoney(param) param:" + JSON.toJSONString(saveForm)
					+ " 报错：" + e.getMessage());
			return jsonResult;
		}
	}

	/**
	 * 跳转财凭冲红页面
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping("/redMoney")
	public String offsetWealthPage(HttpServletRequest request) {
		try {
			User user = ContextHepler.getCurrentUser();
			Collection<Company> sessionList = (Collection<Company>) request.getSession().getAttribute("COMPANY_LIST");
			if (sessionList == null) {
				Collection<Company> list = adjunctSomethingService.queryStationCompany();
				request.getSession().setAttribute("COMPANY_LIST", list);
				request.getSession().setAttribute("CURR_COMPANY_NAME", user.getCompany());
				request.getSession().setAttribute("CURR_COMPANY_CODE", user.getCompanyCode());
			}
		} catch (Exception e) {
			logger.debug("用户操作行为日志:操作->财凭冲红页面跳转，登录已过期，请重新登录！");
		}
		return "transport/convey/redMoney";
	}

	/**
	 * 查询财凭冲红(没有权限)按钮
	 */
	@RequestMapping("/searchOffsetWealthInfo")
	@RequiresPermissions("undo:finance:search:noright")
	@ResponseBody
	public JsonResult searchOffsetWealthInfo(@RequestBody FinanceReceiveMoneyForm formFkfsh) {
		try {
			User user = ContextHepler.getCurrentUser();
			formFkfsh.setGrid(user.getAccount());
			formFkfsh.setLoginCompanyName(user.getCompany());
			formFkfsh.setUserName(user.getUserName());
			logger.debug("用户操作行为日志:操作->财凭冲红查询;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		} catch (Exception e) {
			logger.debug("ConveyController searchOffsetWealthInfo(param) param:" + JSON.toJSONString(formFkfsh) + " 报错："
					+ e.getMessage());
			logger.debug("用户操作行为日志:操作->财凭冲红查询");
			JsonResult jsonResult = new JsonResult();
			jsonResult.put("resultCode", 500);
			jsonResult.put("reason", "登录已过期，请重新登录！");
			return jsonResult;
		}
		try {
			JsonResult jsonResult = financeReceiveMoneyService.searchOffsetWealthInfo(formFkfsh,0);
			return jsonResult;
		} catch (Exception e) {
			logger.debug("ConveyController searchOffsetWealthInfo(param) param:" + JSON.toJSONString(formFkfsh) + " 报错："
					+ e.getMessage());
			JsonResult jsonResult = new JsonResult();
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "查询请求报错，请联系系统维护人员");
			return jsonResult;
		}
	}
	
	/**
	 * 查询财凭冲红（普通权限）
	 */
	@RequestMapping("/searchOffsetWealthInfoRightIsCommon")
	@RequiresPermissions("undo:finance:search:common")
	@ResponseBody
	public JsonResult searchOffsetWealthInfoRightWithCommon(@RequestBody FinanceReceiveMoneyForm formFkfsh) {
		try {
			User user = ContextHepler.getCurrentUser();
			formFkfsh.setGrid(user.getAccount());
			formFkfsh.setLoginCompanyName(user.getCompany());
			formFkfsh.setUserName(user.getUserName());
			logger.debug("用户操作行为日志:操作->财凭冲红查询;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		} catch (Exception e) {
			logger.debug("ConveyController searchOffsetWealthInfoRightWithCommon(param) param:" + JSON.toJSONString(formFkfsh) + " 报错：" + e.getMessage());
			logger.debug("用户操作行为日志:操作->财凭冲红查询");
			JsonResult jsonResult = new JsonResult();
			jsonResult.put("resultCode", 500);
			jsonResult.put("reason", "登录已过期，请重新登录！");
			return jsonResult;
		}
		try {
			JsonResult jsonResult = financeReceiveMoneyService.searchOffsetWealthInfo(formFkfsh,1);
			return jsonResult;
		} catch (Exception e) {
			logger.debug("ConveyController searchOffsetWealthInfoRightWithCommon(param) param:" + JSON.toJSONString(formFkfsh) + " 报错：" + e.getMessage());
			JsonResult jsonResult = new JsonResult();
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "查询请求报错，请联系系统维护人员");
			return jsonResult;
		}
	}
	
	
	/**
	 * 查询财凭冲红（单证组长）
	 */
	@RequestMapping("/searchOffsetWealthInfoRightWithGrouper")
	@RequiresPermissions("undo:finance:search:grouper")
	@ResponseBody
	public JsonResult searchOffsetWealthInfoRightWithGrouper(@RequestBody FinanceReceiveMoneyForm formFkfsh) {
		try {
			User user = ContextHepler.getCurrentUser();
			formFkfsh.setGrid(user.getAccount());
			formFkfsh.setLoginCompanyName(user.getCompany());
			formFkfsh.setUserName(user.getUserName());
			logger.debug("用户操作行为日志:操作->财凭冲红查询;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		} catch (Exception e) {
			logger.debug("ConveyController searchOffsetWealthInfoRightWithCommon(param) param:" + JSON.toJSONString(formFkfsh) + " 报错：" + e.getMessage());
			logger.debug("用户操作行为日志:操作->财凭冲红查询");
			JsonResult jsonResult = new JsonResult();
			jsonResult.put("resultCode", 500);
			jsonResult.put("reason", "登录已过期，请重新登录！");
			return jsonResult;
		}
		try {
			JsonResult jsonResult = financeReceiveMoneyService.searchOffsetWealthInfo(formFkfsh,2);
			return jsonResult;
		} catch (Exception e) {
			logger.debug("ConveyController searchOffsetWealthInfoRightWithCommon(param) param:" + JSON.toJSONString(formFkfsh) + " 报错：" + e.getMessage());
			JsonResult jsonResult = new JsonResult();
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "查询请求报错，请联系系统维护人员");
			return jsonResult;
		}
	}
	
	/**
	 * 查询财凭冲红（财务经理）
	 */
	@RequestMapping("/searchOffsetWealthInfoRightWithManager")
	@RequiresPermissions("undo:finance:search:manager")
	@ResponseBody
	public JsonResult searchOffsetWealthInfoRightWithManager(@RequestBody FinanceReceiveMoneyForm formFkfsh) {
		try {
			User user = ContextHepler.getCurrentUser();
			formFkfsh.setGrid(user.getAccount());
			formFkfsh.setLoginCompanyName(user.getCompany());
			formFkfsh.setUserName(user.getUserName());
			logger.debug("用户操作行为日志:操作->财凭冲红查询;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		} catch (Exception e) {
			logger.debug("ConveyController searchOffsetWealthInfoRightWithCommon(param) param:" + JSON.toJSONString(formFkfsh) + " 报错：" + e.getMessage());
			logger.debug("用户操作行为日志:操作->财凭冲红查询");
			JsonResult jsonResult = new JsonResult();
			jsonResult.put("resultCode", 500);
			jsonResult.put("reason", "登录已过期，请重新登录！");
			return jsonResult;
		}
		try {
			JsonResult jsonResult = financeReceiveMoneyService.searchOffsetWealthInfo(formFkfsh,3);
			return jsonResult;
		} catch (Exception e) {
			logger.debug("ConveyController searchOffsetWealthInfoRightWithCommon(param) param:" + JSON.toJSONString(formFkfsh) + " 报错：" + e.getMessage());
			JsonResult jsonResult = new JsonResult();
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "查询请求报错，请联系系统维护人员");
			return jsonResult;
		}
	}
	
	/**
	 * 查询财凭打印信息
	 * 
	 * @return
	 */
	@RequestMapping("/searchWealthPrint")
	@ResponseBody
	public JsonResult searchWealthPrint(@RequestBody FinanceReceiveMoneyForm formFkfsh) {
		try {
			User user = ContextHepler.getCurrentUser();
			formFkfsh.setGrid(user.getAccount());
			formFkfsh.setLoginCompanyName(user.getCompany());
			formFkfsh.setUserName(user.getUserName());
			logger.debug("用户操作行为日志:操作->财凭打印查询;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		} catch (Exception e) {
			logger.debug("ConveyController searchWealthPrint(param) param:" + JSON.toJSONString(formFkfsh) + " 报错："
					+ e.getMessage());
			logger.debug("用户操作行为日志:操作->财凭打印查询");
			JsonResult jsonResult = new JsonResult();
			jsonResult.put("resultCode", 500);
			jsonResult.put("reason", "登录已过期，请重新登录！");
			return jsonResult;
		}
		try {
			JsonResult jsonResult = financeReceiveMoneyService.searchWealthPrint(formFkfsh);
			return jsonResult;
		} catch (Exception e) {
			logger.debug("ConveyController searchWealthPrint(param) param:" + JSON.toJSONString(formFkfsh) + " 报错："
					+ e.getMessage());
			JsonResult jsonResult = new JsonResult();
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "查询请求报错，请联系系统维护人员");
			return jsonResult;
		}
	}

	/**
	 * 保存财凭冲红
	 * 
	 * @return
	 */
	@RequestMapping("/offsetWealthInfo")
	@ResponseBody
	public JsonResult offsetWealthInfo(@RequestBody FinanceReceiveMoneyForm formFkfsh) {
		String grid = null;
		String userName = null;
		try {
			User user = ContextHepler.getCurrentUser();
			grid = user.getAccount();
			userName = user.getUserName();
			logger.debug("用户操作行为日志:操作->财凭冲红保存;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		} catch (Exception e) {
			logger.debug("ConveyController offsetWealthInfo(param) param:" + JSON.toJSONString(formFkfsh) + " 报错："
					+ e.getMessage());
			logger.debug("用户操作行为日志:操作->财凭冲红保存");
			JsonResult jsonResult = new JsonResult();
			jsonResult.put("resultCode", 500);
			jsonResult.put("reason", "登录已过期，请重新登录！");
			return jsonResult;
		}
		JsonResult jsonResult = new JsonResult();
		try {
			formFkfsh.setGrid(grid);
			formFkfsh.setUserName(userName);
			String result = financeReceiveMoneyService.offsetWealthInfo(formFkfsh);
			jsonResult.put("resultCode", 200);
			jsonResult.put("redWealthCode", result);
			jsonResult.put("reason", "冲红成功，生成红票财凭号为：" + result + "，请重新生成正确的财务凭证！");
			return jsonResult;
		} catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			return jsonResult;
		} catch (BusinessException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			return jsonResult;
		} catch (Exception e) {
			logger.debug("ConveyController offsetWealthInfo(param) param:" + JSON.toJSONString(formFkfsh) + " 报错："
					+ e.getMessage());
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "查询请求报错，请联系系统维护人员");
			return jsonResult;
		}
	}

	/**
	 * @Description:批量收钱
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveBatchReceiveMoney")
	@ResponseBody
	public JsonResult saveBatchReceiveMoney(HttpServletRequest request,
                                            @RequestBody List<FinanceReceiveMoneyForm> saveFormList) {
		String grid = null;
		String chuna = null;
		String companyName = null;
		String companyCode = null;
		try {
			User user = ContextHepler.getCurrentUser();
			logger.debug("用户操作行为日志:操作->批量收钱;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
			grid = user.getAccount();
			chuna = user.getUserName();
			companyName = user.getCompany();
			companyCode = user.getCompanyCode();
		} catch (Exception e) {
			logger.debug("FinanceTransportController saveBatchReceiveMoney(param) param:"
					+ JSON.toJSONString(saveFormList) + " 报错：" + e.getMessage());
			logger.debug("用户操作行为日志:操作->批量收钱");
			JsonResult jsonResult = new JsonResult();
			jsonResult.put("resultCode", 500);
			jsonResult.put("reason", "登录已过期，请重新登录！");
			return jsonResult;
		}
		// 页面接收内容展示
		List<FinanceReceiveMoneyResult> succList = new ArrayList<FinanceReceiveMoneyResult>();
		List<FinanceReceiveMoneyResult> failList = new ArrayList<FinanceReceiveMoneyResult>();
		for (FinanceReceiveMoneyForm formFkfsh : saveFormList) {
			FinanceReceiveMoneyResult result = new FinanceReceiveMoneyResult();
			result.setCompanyCode(companyCode);
			result.setCompanyName(companyName);
			result.setYear(formFkfsh.getYear());
			result.setWealthNo(formFkfsh.getWealthNo());
			formFkfsh.setChuna(chuna);
			formFkfsh.setCompanyCode(companyCode);
			formFkfsh.setCompanyName(companyName);
			formFkfsh.setGrid(grid);
			try {
				result = financeReceiveMoneyService.saveMonthReceviceMoney(formFkfsh);
			} catch (ParameterException e) {
				result.setResultCode("400");
				result.setResultMsg("收钱失败，提交的信息不能正常收钱，请检查格式");
				logger.debug("FinanceTransportController saveBatchReceiveMoney(param) param:"
						+ JSON.toJSONString(saveFormList) + " 报错：" + e.getMessage());
				JsonResult jsonResult = new JsonResult();
				jsonResult.put("resultCode", 400);
				jsonResult.put("reason", "收钱失败请重试，" + e.getTipMessage());
				return jsonResult;
			} catch (BusinessException e) {
				result.setResultCode("400");
				result.setResultMsg("收钱失败，提交的信息不能正常收钱，请检查格式");
				logger.debug("FinanceTransportController saveBatchReceiveMoney(param) param:"
						+ JSON.toJSONString(saveFormList) + " 报错：" + e.getMessage());
				JsonResult jsonResult = new JsonResult();
				jsonResult.put("resultCode", 400);
				jsonResult.put("reason", "收钱失败请重试，" + e.getTipMessage());
				return jsonResult;
			} catch (Exception e) {
				logger.debug("FinanceTransportController saveBatchReceiveMoney(param) param:"
						+ JSON.toJSONString(saveFormList) + " 报错：" + e.getMessage());
				JsonResult jsonResult = new JsonResult();
				jsonResult.put("resultCode", 400);
				if (e.getMessage().indexOf("Transaction timed out") > 1) {
					result.setResultCode("400");
					result.setResultMsg("因服务器繁忙收钱失败（已取消该操作），请重试");
					jsonResult.put("reason", "因服务器繁忙收钱失败（已取消该操作），请重试");
				} else {
					jsonResult.put("reason", "收钱失败，其他异常情况");
				}
				return jsonResult;
			}
			if (result != null && "200".equals(result.getResultCode())) {
				succList.add(result);
			} else {
				failList.add(result);
			}
		}
		JsonResult jsonResult = new JsonResult();
		jsonResult.put("resultCode", 200);
		jsonResult.put("failMessage", failList);
		jsonResult.put("succMessage", succList);
		if (saveFormList.get(0).getIsNewDoc()) {
			request.getSession().setAttribute("SUCC_BATCH_RECEIVE_LIST", succList);
			request.getSession().setAttribute("FAIL_BATCH_RECEIVE_LIST", failList);
		} else {
			List<FinanceReceiveMoneyResult> oldSuccList = (List<FinanceReceiveMoneyResult>) request.getSession()
					.getAttribute("SUCC_BATCH_RECEIVE_LIST");
			List<FinanceReceiveMoneyResult> oldFailList = (List<FinanceReceiveMoneyResult>) request.getSession()
					.getAttribute("FAIL_BATCH_RECEIVE_LIST");
			for (FinanceReceiveMoneyResult succ : succList) {
				oldSuccList.add(succ);
			}
			for (FinanceReceiveMoneyResult fail : failList) {
				oldFailList.add(fail);
			}
			request.getSession().setAttribute("SUCC_BATCH_RECEIVE_LIST", oldSuccList);
			request.getSession().setAttribute("FAIL_BATCH_RECEIVE_LIST", oldFailList);
		}
		jsonResult.put("reason", "批量月结收钱请求成功");
		return jsonResult;
	}

	/**
	 * @Description:下载批量收钱成功的列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downloadSuccBatchReceiveMoney")
	public void downloadSuccBatchReceiveMoney(HttpServletRequest request, HttpServletResponse response) {
		List<FinanceReceiveMoneyResult> succList = (List<FinanceReceiveMoneyResult>) request.getSession()
				.getAttribute("SUCC_BATCH_RECEIVE_LIST");
		if (succList == null || succList.isEmpty()) {
			throw new ParameterException("请求超时,收钱成功的记录已经导出过");
		}
		// 设置excel信息
		String[] showColumnName = { "公司", "年份", "财凭号", "月结金额", "导入人", "导入人工号" };// 列名
		List<String[]> exportContentList = new ArrayList<String[]>();
		for (FinanceReceiveMoneyResult result : succList) {
			String content[] = { result.getCompanyName(), result.getYear(), result.getWealthNo() + "",
					result.getYshk() + "", result.getUserName(), result.getGrid() };
			exportContentList.add(content);
		}
		String sheetName = "月结收钱成功记录";
		String fileName = sheetName;
		Integer showColumnWidth[] = { 15, 15, 15, 15, 15, 15 };// 列宽
		ExcelForm excelForm = new ExcelForm();
		excelForm.setFileName(fileName);// excel文件名称（不包含后缀)
		excelForm.setList(exportContentList);// 导出的内容
		excelForm.setSheetName(sheetName);
		excelForm.setShowColumnName(showColumnName);
		excelForm.setShowColumnWidth(showColumnWidth);
		try {
			ExcelReportUtils.exportExcel(excelForm, response);
		} catch (Exception e) {
			logger.debug("月结收钱成功记录导出异常！报错内容：" + e.getMessage());
		}
	}

	/**
	 * @Description:下载批量收钱失败的列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downloadFailBatchReceiveMoney")
	public void downloadFailBatchReceiveMoney(HttpServletRequest request, HttpServletResponse response) {
		List<FinanceReceiveMoneyResult> failList = (List<FinanceReceiveMoneyResult>) request.getSession()
				.getAttribute("FAIL_BATCH_RECEIVE_LIST");
		if (failList == null || failList.isEmpty()) {
			throw new ParameterException("请求超时,收钱失败的记录已经导出过");
		}
		// 设置excel信息
		String[] showColumnName = { "公司", "年份", "财凭号", "月结金额", "导入人", "导入人工号", "失败原因" };// 列名
		List<String[]> exportContentList = new ArrayList<String[]>();
		for (FinanceReceiveMoneyResult result : failList) {
			String content[] = { result.getCompanyName(), result.getYear(), result.getWealthNo() + "",
					result.getYshk() + "", result.getUserName(), result.getGrid(), result.getResultMsg() };
			exportContentList.add(content);
		}
		String sheetName = "月结收钱失败记录";
		String fileName = sheetName;
		Integer showColumnWidth[] = { 15, 15, 15, 15, 15, 15, 60 };// 列宽
		ExcelForm excelForm = new ExcelForm();
		excelForm.setFileName(fileName);// excel文件名称（不包含后缀)
		excelForm.setList(exportContentList);// 导出的内容
		excelForm.setSheetName(sheetName);
		excelForm.setShowColumnName(showColumnName);
		excelForm.setShowColumnWidth(showColumnWidth);
		try {
			ExcelReportUtils.exportExcel(excelForm, response);
		} catch (Exception e) {
			logger.debug("月结收钱失败记录导出异常！报错内容：" + e.getMessage());
		}
	}

	/**
	 * @Description:批量收钱页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/batchrecemoney")
	public String batchReceiveMoney(HttpServletRequest request) {
		return "/transport/convey/batchrecemoney";
	}

	/**
	 * 记录打印次数和最后打印时间 
	 */
	@RequestMapping(value = "/updatePrintCountAndDate")
	@ResponseBody
	public JsonResult updatePrintCountAndDate(HttpServletRequest request) {
		String waybillNum = request.getParameter("waybillNum");
		return financeReceiveMoneyService.updatePrintCountAndDate(waybillNum);
	}
	
}
