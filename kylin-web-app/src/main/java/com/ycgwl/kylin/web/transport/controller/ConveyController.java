package com.ycgwl.kylin.web.transport.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ycgwl.kylin.SystemKey;
import com.ycgwl.kylin.entity.PageInfo;
import com.ycgwl.kylin.entity.RequestJsonEntity;
import com.ycgwl.kylin.security.client.ContextHepler;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.security.entity.User;

import com.ycgwl.kylin.transport.entity.BatchTransportOrderEntity;
import com.ycgwl.kylin.transport.entity.FinanceCertify;
import com.ycgwl.kylin.transport.entity.FinanceStandardPrice;
import com.ycgwl.kylin.transport.entity.TransportEditResult;
import com.ycgwl.kylin.transport.entity.TransportOrder;
import com.ycgwl.kylin.transport.entity.TransportOrderCancelResult;
import com.ycgwl.kylin.transport.entity.TransportOrderDetail;
import com.ycgwl.kylin.transport.entity.TransportOrderEntry;
import com.ycgwl.kylin.transport.entity.TransportOrderResult;
import com.ycgwl.kylin.transport.entity.TransportOrderSearch;
import com.ycgwl.kylin.transport.entity.TransportSignRecordSearch;
import com.ycgwl.kylin.transport.entity.UserModel;
import com.ycgwl.kylin.transport.entity.adjunct.Company;
import com.ycgwl.kylin.transport.entity.adjunct.Employee;
import com.ycgwl.kylin.transport.entity.adjunct.ReleaseWaiting;
import com.ycgwl.kylin.transport.service.api.AdjunctSomethingService;
import com.ycgwl.kylin.transport.service.api.FinanceReceiveMoneyService;
import com.ycgwl.kylin.transport.service.api.ITransportOrderEditService;
import com.ycgwl.kylin.transport.service.api.ITransportOrderService;
import com.ycgwl.kylin.transport.service.api.TransportOrderDetailService;
import com.ycgwl.kylin.transport.service.api.TransportWaybillService;
import com.ycgwl.kylin.util.Assert;
import com.ycgwl.kylin.web.transport.BaseController;
import com.ycgwl.kylin.web.transport.entity.ExcelForm;
import com.ycgwl.kylin.web.transport.entity.TransportOrderSearchModel;
import com.ycgwl.kylin.web.transport.util.ConveyResult;
import com.ycgwl.kylin.web.transport.util.ConveyUtils;
import com.ycgwl.kylin.web.transport.util.DateRangeUtil;
import com.ycgwl.kylin.web.transport.util.ExcelReportUtils;
import com.ycgwl.kylin.web.transport.util.MessageUtil;

/**
 * 运单管理和新增运单controller
 * <p>
 * 
 * @developer Create by <a href="mailto:110686@ycgwl.com">yanxf</a> at 2017年6月2日
 */
@Controller
@RequestMapping("/transport/convey")
public class ConveyController extends BaseController {
	public static final String LASTFALSECONVEY = "lastFalseConvey";
	public static final String LASTSUCCESSCONVEY = "lastSuccessConvey";
	@Resource
	private ITransportOrderService orderService;
	@Resource
	private TransportOrderDetailService orderDetailService;
	@Resource
	private AdjunctSomethingService adjunctSomethingService;
	@Resource
	private FinanceReceiveMoneyService financeReceiveMoneyService;
	@Resource
	private ITransportOrderEditService transportOrderEditService;
	@Resource
	private TransportWaybillService transportWaybillService;
	

	/**
	 * 运单管理，运单查询
	 * <p>
	 * 
	 * @developer Create by <a href="mailto:110686@ycgwl.com">yanxf</a> at
	 *            2017年6月2日
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/manage")
	public String manage(HttpServletRequest request, Model model) {
//		try {
//			TransportOrderSearchModel searchModel = new TransportOrderSearchModel();
//			searchModel.setStatus(2);
//			Calendar calendar = Calendar.getInstance();
//			calendar.add(Calendar.DAY_OF_MONTH, 1); // 默认是一个月的数据量
//			String edate = DateFormatUtils.format(calendar, DATE_FORMAT);
//			calendar.add(Calendar.MONTH, -1);
//			String sdate = DateFormatUtils.format(calendar, DATE_FORMAT);
//			searchModel.setSearchdate(sdate + " 至 " + edate);
//			model.addAttribute("searchModel", searchModel);
//			return "transport/convey/manage";
//		} catch (Exception e) {
//		}
		return "transport/convey/manage";
	}
	
	@RequestMapping(value = "/carrayPrint")
	public String carrayPrint(HttpServletRequest request) {
		return "transport/convey/carrayPrint";
	}
	@RequestMapping(value = "/carrayPrintA4")
	public String carrayPrintA4(HttpServletRequest request) {
		return "transport/convey/carrayPrintA4";
	}
	
	@RequestMapping("/print")
	public String toConveyPrint(){
		return "transport/convey/printManage";
	}
	@RequestMapping("/printA4")
	public String toConveyPrintA4(){
		return "transport/convey/printManageA4";
	}
	@RequestMapping("/printDetail")
	public String toConveyPrintDetail(){
		return "transport/convey/printDetail";
	}
	@RequestMapping("/printLabel")
	public String toConveyPrintLabel(){
		return "transport/convey/printLabel";
	}
	@RequestMapping("/printLabelBig")
	public String toConveyPrintLabelBig(){
		return "transport/convey/printLabelBig";
	}
	
	@RequestMapping("/toRecieptState")
	public String toRecieptState(){
		return "transport/reciept/recieptState";
	}

	/**
	 * 
	 * 查询运单
	 * <p>
	 * 
	 * @developer Create by <a href="mailto:110686@ycgwl.com">yanxf</a> at
	 *            2017年6月2日
	 * @param searchModel
	 *            查询参数
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/manage/search")
	public JsonResult searchTransportOrder(HttpServletRequest request,@RequestBody TransportOrderSearchModel searchModel) {
		User user = null;
		try {
			user = ContextHepler.getCurrentUser();
		} catch (Exception e) {
			logger.debug("用户操作行为日志:操作->废除运单查询");
			return JsonResult.getConveyResult("500", "登录已过期，请重新登录！");
		}
		logger.debug("用户操作行为日志:操作->运单信息查询;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		JsonResult result = new JsonResult();
		Integer pageNum = searchModel.getNum();
		if (pageNum == null) {
			pageNum = 1;
		}
		Integer pageSize = searchModel.getSize();
		if (pageSize == null) {
			pageSize = 10;
		}
		try{
			TransportOrderSearch transportOrderSearch = new TransportOrderSearch();
			transportOrderSearch.setYdbhid(searchModel.getYdbhid());
			transportOrderSearch.setBeginPlacename(searchModel.getBeginPlacename());
			transportOrderSearch.setDaozhan(searchModel.getDaozhan());
			transportOrderSearch.setKhbm(searchModel.getKhbm());
			transportOrderSearch.setFhdwmch(searchModel.getFhdwmch());
			transportOrderSearch.setYshhm(searchModel.getYshhm());
			transportOrderSearch.setFazhan(ContextHepler.getCurrentUser().getCompany());
			transportOrderSearch.setHyy(searchModel.getHyy());
			transportOrderSearch.setStatus(searchModel.getStatus());
			transportOrderSearch.setZhipiao(searchModel.getZhipiao());
			transportOrderSearch.setKhdh(searchModel.getKhdh());
			transportOrderSearch.setShhrmch(searchModel.getShhrmch());
			transportOrderSearch.setIsFinanceCertify(searchModel.getIsFinanceCertify());
			transportOrderSearch.setHasReceipt(searchModel.getHasReceipt());
			String[] dates = DateRangeUtil.finds(searchModel.getSearchdate(), 2);
			if (ArrayUtils.isEmpty(dates)) {
				Calendar calendar = Calendar.getInstance();
				String sdate = DateFormatUtils.format(calendar, DATE_FORMAT);
				calendar.add(Calendar.DAY_OF_MONTH, 1); // 默认是一天的数据量
				String edate = DateFormatUtils.format(calendar, DATE_FORMAT);
				searchModel.setSearchdate(sdate + " 至 " + edate);
			}
			/**
			 * 结束时间补到23:59:59,开始时间为零点，2018-02-05 13:53添加
			 */
			if (dates.length > 0) {
				transportOrderSearch.setFhshjStart(dates[0] + " 00:00");
			}
			if (dates.length > 1) {
				transportOrderSearch.setFhshjEnd(dates[1] + " 23:59");
			}
			PageInfo<TransportOrderResult> resultList = orderService.pageTransportOrderSecondRevision(transportOrderSearch, pageNum, pageSize);
			result.put("searchModel", searchModel);
			result.put("page", resultList);
			result.put("resultCode", 200);
			result.put("message", "查询成功");
		}catch(Exception e){
			logger.debug("用户操作行为日志:操作->运单信息查询;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
			logger.debug("ConveyController searchTransportOrder(param) param:" + JSON.toJSONString(searchModel) + " 报错："	+ e.getMessage());
			return JsonResult.getConveyResult("400", "查询失败");
		}
		return result;
	}

	/**
	 * 保存运单数据
	 * <p>
	 * @developer Create by <a href="mailto:110686@ycgwl.com">yanxf</a> at 2017年6月2日
	 * @param request
	 * @param orderEntry 运单基础数据和运单货物信息
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/insert/save", method = RequestMethod.POST)
	public String insertSave(HttpServletRequest request, TransportOrderEntry orderEntry, Model model) {
		try {
			User user = ContextHepler.getCurrentUser();
			logger.debug("用户操作行为日志:操作->录入运单;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
			model.addAttribute("transportOrderEntry", orderEntry);
			Boolean releaseWaiting = false;
			if (orderEntry.getReleaseWaiting() != null && orderEntry.getReleaseWaiting() == 1) {
				releaseWaiting = true;
			}
			Assert.notEmpty("orderEntry.details", orderEntry.getDetails(), "运单明细不能为空");
			// 清除一些无效的数据
			for (Iterator<TransportOrderDetail> iterator = orderEntry.getDetails().iterator(); iterator.hasNext();) {
				TransportOrderDetail transportOrderDetail = iterator.next();
				if (transportOrderDetail == null || StringUtils.isBlank(transportOrderDetail.getPinming())) {
					iterator.remove();
				}
			}
			orderEntry.getOrder().setGsbh(user.getCompanyCode());
			UserModel userModel = new UserModel();
			userModel.setAccount(user.getAccount());
			userModel.setUserName(user.getUserName());
			userModel.setPassword(user.getPassword());
			userModel.setCreateTime(user.getCreateTime());
			userModel.setEnable(user.getEnable());
			userModel.setCompany(user.getCompany());
			userModel.setCompanyCode(user.getCompanyCode());
			userModel.setSubCompany(user.getSubCompany());
			// 保存运单信息和货物详细信息
			transportWaybillService.saveOrderAndCertify(orderEntry.getOrder(), orderEntry.getDetails(), orderEntry.getCertify(),
					orderEntry.getPrice(), releaseWaiting,userModel);
			model.addAttribute(MessageUtil.SUCCESS_MESSAGE, "操作成功");
		} catch (BusinessException e) {
			model.addAttribute(MessageUtil.ERROR_MESSAGE, e.getTipMessage());
		} catch (ParameterException e) {
			model.addAttribute(MessageUtil.ERROR_MESSAGE, e.getTipMessage());
		} catch (Exception e) {
			exception(e, model);
		}
		return toInsertPage(request, model);
	}

	/**
	 * 跳转新增运单页面
	 * <p>
	 * 
	 * @developer Create by <a href="mailto:110686@ycgwl.com">yanxf</a> at 2017年6月2日
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/insert")
	public String insertPage(HttpServletRequest request, Model model) {
		try {
			// 获取用户信息
			User user = ContextHepler.getCurrentUser();
			String company = user.getCompany();

			TransportOrderEntry orderEntry = new TransportOrderEntry();
			TransportOrder order = new TransportOrder();
			order.setDaodatianshu(10);
			order.setIsfd(1);
			orderEntry.setOrder(order);
			// 查询该公司查询的单价
			FinanceStandardPrice price = (FinanceStandardPrice) request.getSession().getAttribute("transport_price");
			if (null == price) {
				price = adjunctSomethingService.findFinanceStandardPriceByGs(user.getCompany());
				request.getSession().setAttribute("transport_price", price);
			}
			orderEntry.setPrice(price);
			// 如果是四川远成这家分公司,那么就把品名的下拉框给他
			if (SystemKey.SEND_MESSAGE_COMPANY1.equals(company)) {
				model.addAttribute("pinmingList", MessageUtil.TUOPAIPINMINGLIST);
			}
			model.addAttribute("user", user);
			model.addAttribute("daozhanList", adjunctSomethingService.listStationCompanys());// 到站
			model.addAttribute("fhkhhyList", adjunctSomethingService.listIndustrys());// 行业类别
			model.addAttribute("ysfsList", adjunctSomethingService.listConveyWays());// 运输方式
			model.addAttribute("transportOrderEntry", orderEntry);
		} catch (Exception e) {
			exception(e, model);
		}
		return "transport/convey/insert";
	}

	/**
	 * 跳转新增装载清单页面
	 * <p>
	 * 
	 * @developer Create by <a href="mailto:110686@ycgwl.com">yanxf</a> at
	 *            2017年6月2日
	 * @param ydbhidList
	 *            运单编号
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/loading/insert")
	public String insertLoadingPage(TransportOrderSearchModel searchModel, String[] ydbhidList,
			HttpServletRequest request, Model model) {
		// 准备一些基础数据
		try {
			List<String> ydbhids = new ArrayList<String>();
			if (ArrayUtils.isNotEmpty(ydbhidList)) {
				for (String ydbhid : ydbhidList) {
					if (StringUtils.isNotBlank(ydbhid)) {
						ydbhids.add(ydbhid);
					}
				}
			}
			Assert.notEmpty("ydbhidList", ydbhids, "至少选择一项运单数据");

			// 遍历集合判断是否装载过
			for (String ydbhid : ydbhids) {

				TransportOrder transportOrder = new TransportOrder();
				transportOrder.setYdbhid(ydbhid);
				transportOrder.setFazhan(ContextHepler.getCurrentUser().getCompany());
			}
			/*
			 * 到站列表暂时不使用 User user = ContextHepler.getCurrentUser(); String
			 * company = user.getCompany(); List<String> listDaoZhan =
			 * adjunctSomethingService.listDaoZhanByCompany(company);
			 * model.addAttribute("listDaoZhan", listDaoZhan);
			 */
			searchModel.setPageType("convey"); // 跳转的起始标识
			Collection<TransportOrder> listTransportOrderByYdbhids = orderService.listTransportOrderByYdbhids(ydbhids);
			for (TransportOrder transOrder : listTransportOrderByYdbhids) {
				String recordPlayerId = transOrder.getZhipiao(); // 需求更改支票保存的是录单人的id
				Employee employee = adjunctSomethingService.getEmployeeByNumber(recordPlayerId);
				if (employee != null) {
					transOrder.setZhipiao(employee.getEmplyeeName());
				}
			}
			model.addAttribute("orderByYdbhidList", listTransportOrderByYdbhids);
			model.addAttribute("searchModel", searchModel);
			return "transport/bundle/insert";
		} catch (Exception e) {
			exception(e, model);
		}
		return insertPage(request, model);
	}

	/**
	 * 运单修改返回查询主界面
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月24日
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/return")
	@Deprecated
	public String returnManage(HttpServletRequest request,Model model) {

		//从session中获取数据
		TransportOrderSearchModel searchModel = (TransportOrderSearchModel) request.getSession().getAttribute("searchModel");

		User user = ContextHepler.getCurrentUser();
		logger.debug("用户操作行为日志:操作->装载清单查询;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());

		Integer pageNum = searchModel.getNum();
		if (pageNum == null) {
			pageNum = 1;
		}
		Integer pageSize = searchModel.getSize();
		if (pageSize == null) {
			pageSize = 10;
		}

		TransportOrderSearch transportOrderSearch = new TransportOrderSearch();
		transportOrderSearch.setYdbhid(searchModel.getYdbhid());
		transportOrderSearch.setBeginPlacename(searchModel.getBeginPlacename());
		transportOrderSearch.setDaozhan(searchModel.getDaozhan());
		transportOrderSearch.setKhbm(searchModel.getKhbm());
		transportOrderSearch.setFhdwmch(searchModel.getFhdwmch());
		transportOrderSearch.setYshhm(searchModel.getYshhm());
		transportOrderSearch.setFazhan(ContextHepler.getCurrentUser().getCompany());
		transportOrderSearch.setHyy(searchModel.getHyy());
		transportOrderSearch.setStatus(searchModel.getStatus());
		transportOrderSearch.setZhipiao(searchModel.getZhipiao());
		String[] dates = DateRangeUtil.finds(searchModel.getSearchdate(), 2);
		if (ArrayUtils.isEmpty(dates)) {
			Calendar calendar = Calendar.getInstance();
			String sdate = DateFormatUtils.format(calendar, DATE_FORMAT);
			calendar.add(Calendar.DAY_OF_MONTH, 1); // 默认是一天的数据量
			String edate = DateFormatUtils.format(calendar, DATE_FORMAT);

			searchModel.setSearchdate(sdate + " 至 " + edate);
		}
		if (dates.length > 0) {
			transportOrderSearch.setFhshjStart(dates[0]);
		}
		if (dates.length > 1) {
			transportOrderSearch.setFhshjEnd(dates[1]);
		}
		PageInfo<TransportOrderResult> result = orderService.pageTransportOrderSecondRevision(transportOrderSearch, pageNum, pageSize);
		model.addAttribute("searchModel", searchModel);
		model.addAttribute("page", result);
		request.getSession().removeAttribute("searchModel");
		request.getSession().setAttribute("searchModel", searchModel);

		return "transport/convey/manage";
	}

	/**
	 * 编辑运单数据
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月14日
	 * @param request
	 * @param orderEntry
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/save", method = RequestMethod.POST)
	public String editSave(HttpServletRequest request, TransportOrderSearchModel searchModel,TransportOrderEntry orderEntry, Model model) {
		model.addAttribute("searchModel", searchModel);
		User user = null;
		if(orderEntry==null){
			model.addAttribute(MessageUtil.ERROR_MESSAGE, "页面未提交任何数据");
		}else{
			try {
				user = ContextHepler.getCurrentUser();
				logger.debug("用户操作行为日志:操作->修改运单;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());

				UserModel userModel = new UserModel();
				userModel.setAccount(user.getAccount());
				userModel.setUserName(user.getUserName());
				userModel.setPassword(user.getPassword());
				userModel.setCreateTime(user.getCreateTime());
				userModel.setEnable(user.getEnable());
				userModel.setCompany(user.getCompany());
				userModel.setCompanyCode(user.getCompanyCode());
				userModel.setSubCompany(user.getSubCompany());
					
				// 保存运单信息和货物详细信息
				transportWaybillService.updateOrderAndCertify(orderEntry.getOrder(), orderEntry.getDetails(), orderEntry.getCertify(), 
						orderEntry.getPrice(),orderEntry.getModifyOrderIdentification(), orderEntry.getModifyCertifyIdentification(),userModel);
				model.addAttribute(MessageUtil.SUCCESS_MESSAGE, "操作成功");
			} catch (BusinessException e) {
				model.addAttribute(MessageUtil.ERROR_MESSAGE, e.getTipMessage());
			} catch (ParameterException e) {
				model.addAttribute(MessageUtil.ERROR_MESSAGE, e.getTipMessage());
			} catch (Exception e) {
				exception(e, model);
			}
			if(orderEntry.getModifyOrderIdentification() == 0){//查询运单信息
				TransportOrder transportOrder = orderService.getTransportOrderByYdbhid(orderEntry.getOrder().getYdbhid());
				orderEntry.setOrder(transportOrder);
				orderEntry.setDetails(orderDetailService.queryTransportOrderDetailByYdbhid(orderEntry.getOrder().getYdbhid()));	
			}else{
				// 清除一些无效的数据
				for (Iterator<TransportOrderDetail> iterator = orderEntry.getDetails().iterator(); iterator.hasNext();) {
					TransportOrderDetail transportOrderDetail = iterator.next();
					if (transportOrderDetail == null || StringUtils.isBlank(transportOrderDetail.getPinming())) {
						iterator.remove();
					}
				}
			}
			orderEntry.getOrder().setGsbh(user.getCompanyCode());
			if(orderEntry.getModifyCertifyIdentification() == 0){//0表示不修改财凭
				FinanceCertify certify = orderService.getFinanceCertifyByYdbhid(orderEntry.getOrder().getYdbhid());
				if(certify==null){//如果没有财凭信息，则从价格表拿
					orderEntry.setPrice(adjunctSomethingService.findFinanceStandardPriceByGs(user.getCompany()));
				}else{//如果有财凭的话，价格从财凭表FIWT拿
					orderEntry.setCertify(certify);
					FinanceStandardPrice price = new FinanceStandardPrice();
					price.setPremiumRate(certify.getPremiumRate());
					price.setQhzhxfdj(certify.getLightHandlingPrice());
					price.setZhjxzyf(certify.getForkliftFee());
					price.setZhzhxfdj(certify.getHeavyHandlingPrice());
					orderEntry.setPrice(price);
				}
			}
			model.addAttribute("transportOrderEntry",orderEntry);
			request.setAttribute("transportOrderEntry", orderEntry);
		}
		return toEditPage(request, model);
	}

	/**
	 * 保存后跳转至修改界面
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月19日
	 * @param request
	 * @param model
	 * @return
	 */
	public String toEditPage(HttpServletRequest request,Model model) {

		try {
			// 获取用户信息
			User user = ContextHepler.getCurrentUser();
			String company = user.getCompany();
			TransportOrderEntry orderEntry = (TransportOrderEntry)request.getAttribute("transportOrderEntry");	
			TransportSignRecordSearch transportSignRecordSearch = new TransportSignRecordSearch();
			transportSignRecordSearch.setYdbhid(orderEntry.getOrder().getYdbhid());
			transportSignRecordSearch.setAccount(user.getAccount());
			transportSignRecordSearch.setUserName(user.getUserName());
			transportSignRecordSearch.setPassword(user.getPassword());
			transportSignRecordSearch.setCreateTime(user.getCreateTime());
			transportSignRecordSearch.setEnable(user.getEnable());
			transportSignRecordSearch.setCompany(user.getCompany());
			transportSignRecordSearch.setCompanyCode(user.getCompanyCode());
			transportSignRecordSearch.setSubCompany(user.getSubCompany());

			// 如果是四川远成这家分公司,那么就把品名的下拉框给他
			if (SystemKey.SEND_MESSAGE_COMPANY1.equals(company)) {
				model.addAttribute("pinmingList", MessageUtil.TUOPAIPINMINGLIST);
			}
			//查询是否可以做运单修改操作
			TransportEditResult transportOrderEditPermissions = transportOrderEditService.getTransportOrderEditPermissions(transportSignRecordSearch);
			//查询是否可以补录财凭
			transportSignRecordSearch.setTransportCompanyName(orderEntry.getOrder().getFazhan());
			TransportEditResult inputtingFinanceCertifyPermissions = transportOrderEditService.getInputtingFinanceCertifyPermissions(transportSignRecordSearch);

			model.addAttribute("user", user);
			model.addAttribute("daozhanList", adjunctSomethingService.listStationCompanys());// 到站
			model.addAttribute("fhkhhyList", adjunctSomethingService.listIndustrys());// 行业类别
			model.addAttribute("ysfsList", adjunctSomethingService.listConveyWays());// 运输方式
			model.addAttribute("dzshhdList", adjunctSomethingService.getArriveNetWorkList(orderEntry.getOrder().getDaozhan()));// 到站网点
			model.addAttribute("transportOrderEditPermissions", transportOrderEditPermissions);
			model.addAttribute("inputtingFinanceCertifyPermissions", inputtingFinanceCertifyPermissions);
		} catch (Exception e) {
			exception(e, model);
		}
		return "transport/convey/edit";
	}
	
	
	@RequestMapping(value = "/convey/printSearchBatch/{ydbhidArray}")
	@ResponseBody
	public JsonResult toConveyPrintSearchBatch(HttpServletRequest request,@PathVariable("ydbhidArray")String ydbhidArray, Model model) {
		JsonResult result = JsonResult.getConveyResult("200", "查询成功");
		List<HashMap<String, Object>> transportOrderList = new ArrayList<HashMap<String, Object>>();
		try {
			String ydbhidArrays[] = StringUtils.split(ydbhidArray,",");
			for (String ydbhid : ydbhidArrays) {
				TransportOrder transportOrder = orderService.getTransportOrderByYdbhid(ydbhid);
				Collection<TransportOrderDetail> detail = orderDetailService.queryTransportOrderDetailByYdbhid(ydbhid);
				Map<String, String> describe = BeanUtils.describe(transportOrder);
				for (Entry<String, String> entry : describe.entrySet()) {
					if(StringUtils.isEmpty(entry.getValue())) {
            entry.setValue("");
          }
				}
				Map<String,Object> finance = Maps.newHashMap();
				ReleaseWaiting releaseWaiting = adjunctSomethingService.getReleaseWaitingByYdbhid(ydbhidArray);
				if (releaseWaiting != null) {
          finance.put("releaseWaiting", releaseWaiting.getDdfhzt());
        } else {
          finance.put("releaseWaiting", 0);
        }
				
				FinanceCertify certify = orderService.getFinanceCertifyByYdbhid(ydbhid);
				if(certify == null){
					Map<String, String> financeStandardPrice = BeanUtils.describe(adjunctSomethingService.findFinanceStandardPriceByGs(ContextHepler.getCompanyName()));
					finance.putAll(financeStandardPrice);
					finance.put("receiveMoneyStatus", "");
				}else{		//如果没有财凭信息，则从价格表拿
					finance.put("premiumRate", certify.getPremiumRate());
					finance.put("qhzhxfdj", certify.getLightHandlingPrice());
					finance.put("zhjxzyf", certify.getForkliftFee());
					finance.put("zhzhxfdj", certify.getHeavyHandlingPrice());
					finance.putAll(BeanUtils.describe(certify));
				}
				HashMap<String, Object> resultObj = new HashMap<String, Object>();
				resultObj.put("orderEntry", finance);
				resultObj.put("order", describe);
				resultObj.put("detail", detail);
				transportOrderList.add(resultObj);
			}
			result.put("transportOrderInfo", transportOrderList);
			return result;
		} catch (ParameterException e) {
			return JsonResult.getConveyResult("400", e.getTipMessage()); 
		} catch (BusinessException e) {
			return JsonResult.getConveyResult("400",  e.getTipMessage());
		} catch (Exception e) {
			return JsonResult.getConveyResult("400", "查询有误，请检查请求数据");
		} 
	}

	@RequestMapping(value = "/convey/printSearch/{ydbhid}")
	@ResponseBody
	public JsonResult toConveyPrintSearch(HttpServletRequest request,@PathVariable("ydbhid")String ydbhid, Model model) {
		try {
			TransportOrder transportOrder = orderService.getTransportOrderByYdbhid(ydbhid);
			Collection<TransportOrderDetail> detail = orderDetailService.queryTransportOrderDetailByYdbhid(ydbhid);
			JsonResult result = JsonResult.getConveyResult("200", "查询成功");
			Map<String, String> describe = BeanUtils.describe(transportOrder);
			for (Entry<String, String> entry : describe.entrySet()) {
				if(StringUtils.isEmpty(entry.getValue())) {
          entry.setValue("");
        }
			}
			Map<String,Object> finance = Maps.newHashMap();
			ReleaseWaiting releaseWaiting = adjunctSomethingService.getReleaseWaitingByYdbhid(ydbhid);
			if (releaseWaiting != null) {
        finance.put("releaseWaiting", releaseWaiting.getDdfhzt());
      } else {
        finance.put("releaseWaiting", 0);
      }
			
			FinanceCertify certify = orderService.getFinanceCertifyByYdbhid(ydbhid);
			if(certify == null){
				Map<String, String> financeStandardPrice = BeanUtils.describe(adjunctSomethingService.findFinanceStandardPriceByGs(ContextHepler.getCompanyName()));
				finance.putAll(financeStandardPrice);
				finance.put("receiveMoneyStatus", "");
			}else{		//如果没有财凭信息，则从价格表拿
				finance.put("premiumRate", certify.getPremiumRate());
				finance.put("qhzhxfdj", certify.getLightHandlingPrice());
				finance.put("zhjxzyf", certify.getForkliftFee());
				finance.put("zhzhxfdj", certify.getHeavyHandlingPrice());
				finance.putAll(BeanUtils.describe(certify));
			}
			result.put("orderEntry", finance);
			result.put("order", describe);
			result.put("detail", detail);
			return result;
		} catch (ParameterException e) {
			return JsonResult.getConveyResult("400", e.getTipMessage()); 
		} catch (BusinessException e) {
			return JsonResult.getConveyResult("400",  e.getTipMessage());
		} catch (Exception e) {
			return JsonResult.getConveyResult("400", "查询有误，请检查请求数据");
		} 
	}
	

	/**
	 * 跳转编辑运单页面，从列表跳转详情（编辑）页面的数据展示
	 * @developer Create by <a href="mailto:110686@ycgwl.com">yanxf</a> at  2017年6月2日
	 * @param ydbhid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/manage/{ydbhid}")
	public String editPage(HttpServletRequest request,@PathVariable("ydbhid")String ydbhid, Model model) {

		try {
			// 获取用户信息
			User user = ContextHepler.getCurrentUser();
			String company = user.getCompany();

			TransportSignRecordSearch transportSignRecordSearch = new TransportSignRecordSearch();
			transportSignRecordSearch.setYdbhid(ydbhid);
			transportSignRecordSearch.setAccount(user.getAccount());
			transportSignRecordSearch.setUserName(user.getUserName());
			transportSignRecordSearch.setPassword(user.getPassword());
			transportSignRecordSearch.setCreateTime(user.getCreateTime());
			transportSignRecordSearch.setEnable(user.getEnable());
			transportSignRecordSearch.setCompany(user.getCompany());
			transportSignRecordSearch.setCompanyCode(user.getCompanyCode());
			transportSignRecordSearch.setSubCompany(user.getSubCompany());

			TransportOrderEntry orderEntry = new TransportOrderEntry();
			// 获取运单信息
			TransportOrder transportOrder = orderService.getTransportOrderByYdbhid(ydbhid);
			orderEntry.setOrder(transportOrder);
			orderEntry.setDetails(orderDetailService.queryTransportOrderDetailByYdbhid(ydbhid));
			// 查询运单等待信息
			ReleaseWaiting releaseWaiting = adjunctSomethingService.getReleaseWaitingByYdbhid(ydbhid);
			if (releaseWaiting != null) {
				orderEntry.setReleaseWaiting(releaseWaiting.getDdfhzt());
			} else {
				orderEntry.setReleaseWaiting(0);
			}
			// 获取员工信息
			Employee employee = adjunctSomethingService.getEmployeeByNumber(transportOrder.getZhipiao());
			
			
			// 查询该公司查询的单价
			FinanceCertify certify = orderService.getFinanceCertifyByYdbhid(orderEntry.getOrder().getYdbhid());
			if(certify == null){		//如果没有财凭信息，则从价格表拿
				orderEntry.setPrice(adjunctSomethingService.findFinanceStandardPriceByGs(user.getCompany()));
			}else{			//如果有财凭的话，价格从财凭表FIWT拿
				orderEntry.setCertify(certify);
				FinanceStandardPrice price = new FinanceStandardPrice();
				price.setPremiumRate(certify.getPremiumRate());
				price.setQhzhxfdj(certify.getLightHandlingPrice());
				price.setZhjxzyf(certify.getForkliftPrice());
				price.setZhzhxfdj(certify.getHeavyHandlingPrice());
				orderEntry.setPrice(price);
			}
			// 如果是四川远成这家分公司,那么就把品名的下拉框给他
			if (SystemKey.SEND_MESSAGE_COMPANY1.equals(company)) {
				model.addAttribute("pinmingList", MessageUtil.TUOPAIPINMINGLIST);
			}
			//查询是否可以做运单修改操作
			TransportEditResult transportOrderEditPermissions = transportOrderEditService.getTransportOrderEditPermissions(transportSignRecordSearch);
			//查询是否可以补录财凭
			transportSignRecordSearch.setTransportCompanyName(orderEntry.getOrder().getFazhan());
			TransportEditResult inputtingFinanceCertifyPermissions = transportOrderEditService.getInputtingFinanceCertifyPermissions(transportSignRecordSearch);

			model.addAttribute("user", user);
			model.addAttribute("daozhanList", adjunctSomethingService.listStationCompanys());// 到站
			model.addAttribute("fhkhhyList", adjunctSomethingService.listIndustrys());// 行业类别
			model.addAttribute("ysfsList", adjunctSomethingService.listConveyWays());// 运输方式
			model.addAttribute("dzshhdList", adjunctSomethingService.getArriveNetWorkList(orderEntry.getOrder().getDaozhan()));// 到站网点
			model.addAttribute("transportOrderEntry", orderEntry);
			model.addAttribute("employee", employee);
			model.addAttribute("transportOrderEditPermissions", transportOrderEditPermissions);
			model.addAttribute("inputtingFinanceCertifyPermissions", inputtingFinanceCertifyPermissions);
		} catch (Exception e) {
			exception(e, model);
		}

		return "transport/convey/edit";
	}

	/**
	 * 跳转查看运单详情页面
	 * <p>
	 * 
	 * @developer Create by <a href="mailto:110686@ycgwl.com">yanxf</a> at
	 *            2017年6月2日
	 * @param ydbhid
	 *            运单编号
	 * @param model
	 * @return
	 */
	@Deprecated
	@RequestMapping(value = "/check")
	public String checkPage(TransportOrderSearchModel searchModel, @RequestParam String ydbhId, Model model) {
		// 获取用户信息
		model.addAttribute("user", ContextHepler.getCurrentUser());
		TransportOrderEntry convey = new TransportOrderEntry();

		// 获取运单信息
		TransportOrder transportOrder = orderService.getTransportOrderByYdbhid(ydbhId);

		// 获取员工信息
		Employee employee = adjunctSomethingService.getEmployeeByNumber(transportOrder.getZhipiao());

		convey.setOrder(transportOrder);
		convey.setDetails(orderDetailService.queryTransportOrderDetailByYdbhid(ydbhId));
		convey.setCertify(orderService.getFinanceCertifyByYdbhid(ydbhId));
		// 到站
		model.addAttribute("daozhanList", adjunctSomethingService.listStationCompanys());
		// 行业类别
		model.addAttribute("fhkhhyList", adjunctSomethingService.listIndustrys());
		// 运输方式
		model.addAttribute("ysfsList", adjunctSomethingService.listConveyWays());
		// 查询运单等待信息
		ReleaseWaiting releaseWaiting = adjunctSomethingService.getReleaseWaitingByYdbhid(ydbhId);
		if (releaseWaiting != null) {
			convey.setReleaseWaiting(releaseWaiting.getDdfhzt());
		} else {
			convey.setReleaseWaiting(0);
		}
		model.addAttribute("convey", convey);
		model.addAttribute("employee", employee);
		model.addAttribute("searchModel", searchModel);

		return "transport/convey/check";
	}

	/**
	 * 跳转至新增运单界面
	 * <p>
	 * 
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at
	 *            2017年8月14日
	 * @param request
	 * @param model
	 * @return
	 */
	public String toInsertPage(HttpServletRequest request, Model model) {
		try {
			// 获取用户信息
			User user = ContextHepler.getCurrentUser();
			// 如果是四川远成这家分公司,那么就把品名的下拉框给他
			if (SystemKey.SEND_MESSAGE_COMPANY1.equals(user.getCompany())) {
				model.addAttribute("pinmingList", MessageUtil.TUOPAIPINMINGLIST);
			}

			model.addAttribute("user", user);
			model.addAttribute("daozhanList", adjunctSomethingService.listStationCompanys());// 到站
			model.addAttribute("fhkhhyList", adjunctSomethingService.listIndustrys());// 行业类别
			model.addAttribute("ysfsList", adjunctSomethingService.listConveyWays());// 运输方式
		} catch (Exception e) {
			exception(e, model);
		}
		return "transport/convey/insert";
	}

	/**
	 * 运单管理，运单查询 该方法是运单进入装载页面时候为了出现弹窗写的方法,与manage方法类似,勿删
	 * <p>
	 * 
	 * @developer Create by <a href="mailto:110686@ycgwl.com">yanxf</a> at
	 *            2017年6月2日
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/manage/success")
	public String managesuccess(HttpServletRequest request, Model model) {

		// 查询运单信息
		TransportOrderSearchModel searchModel = new TransportOrderSearchModel();
		searchModel.setNum(1);
		searchModel.setSize(10);
		searchModel.setStatus(2);
		// 设置默认查询时间
		Calendar calendar = Calendar.getInstance();
		String sdate = DateFormatUtils.format(calendar, DATE_FORMAT);
		calendar.add(Calendar.DAY_OF_MONTH, 1); // 默认是一天的数据量
		String edate = DateFormatUtils.format(calendar, DATE_FORMAT);

		searchModel.setSearchdate(sdate + " 至 " + edate);

		TransportOrderSearch transportOrderSearch = new TransportOrderSearch();
		transportOrderSearch.setYdbhid(searchModel.getYdbhid());
		transportOrderSearch.setBeginPlacename(searchModel.getBeginPlacename());
		transportOrderSearch.setDaozhan(searchModel.getDaozhan());
		transportOrderSearch.setKhbm(searchModel.getKhbm());
		transportOrderSearch.setFhdwmch(searchModel.getFhdwmch());
		transportOrderSearch.setYshhm(searchModel.getYshhm());
		transportOrderSearch.setHyy(searchModel.getHyy());
		transportOrderSearch.setStatus(searchModel.getStatus());

		transportOrderSearch.setFazhan(ContextHepler.getCurrentUser().getCompany());

		transportOrderSearch.setFhshjStart(sdate);
		transportOrderSearch.setFhshjEnd(edate);
		model.addAttribute(MessageUtil.SUCCESS_MESSAGE, "操作成功");
		model.addAttribute("searchModel", searchModel);
		model.addAttribute("page",
				orderService.pageTransportOrderSecondRevision(transportOrderSearch, searchModel.getNum(), searchModel.getSize()));
		return "transport/convey/manage";
	}

	/**
	 * 批量导入运单时候逐条进行运单的保存
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017-12-27 17:03:41
	 * @param request
	 * @param requestParam
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/insert/save/item")
	@ResponseBody
	public JsonResult saveConveyItemByItem(HttpServletRequest request,@RequestBody List<BatchTransportOrderEntity> list) {
		if(CollectionUtils.isEmpty(list)) {
			JsonResult result = JsonResult.getConveyResult("200", "失败,请检查数据后重新导入");
			result.put("success", false);
			return result; 
		}
		BatchTransportOrderEntity entity = list.get(0);
		Boolean flag = entity.getIsNewDoc();
		try {
			User user = ContextHepler.getCurrentUser();

			// 查询该公司查询的单价
			FinanceStandardPrice price = (FinanceStandardPrice) request.getSession().getAttribute("transport_price");
			if (price==null) {
				price = adjunctSomethingService.findFinanceStandardPriceByGs(user.getCompany());
				request.getSession().setAttribute("transport_price", price);
			}
			entity.setZhipiao(user.getUserName());
			
			// 批量导入运单的时候 当选择不录入财凭，其他费用默认显是汉字，前端没有校验  但后台校验通过后，解析失败  原因其他费用字段不能存储汉字
//			String withFinance = entity.getWithFinance();
//			if ("否".equals(withFinance)) {
//				entity.setOther(null);
//			}
			// 等拖放货
			String isReleaseWaiting = entity.getIsReleaseWaiting();
			if ("是".equals(isReleaseWaiting)) {
				entity.setReleaseWaiting(1);
			} else {
				entity.setReleaseWaiting(0);
			}
			
			orderService.saveConveyItemByItem(entity,price,user.getCompany(),user.getAccount(),user.getUserName());
			entity.setInsNo(adjunctSomethingService.getFinaceCertifyNoByYdbhid(entity.getYdbhid()));
			
			if(flag) {	//新数据
				request.getSession().setAttribute(LASTSUCCESSCONVEY, Lists.newArrayList(entity));
			}else {
				List<BatchTransportOrderEntity> sucessList = (List<BatchTransportOrderEntity>) request.getSession().getAttribute(LASTSUCCESSCONVEY);
				if(sucessList == null) {
          request.getSession().setAttribute(LASTSUCCESSCONVEY, Lists.newArrayList(entity));
        } else {
          sucessList.add(entity);
        }
			}
		} catch (Exception e) {
			if(e instanceof BusinessException) {
        entity.setErrorMsg(((BusinessException)e).getTipMessage());
      }
			if(e instanceof ParameterException) {
        entity.setErrorMsg(((ParameterException)e).getTipMessage());
      }
			if(flag) {	//新数据
				request.getSession().setAttribute(LASTFALSECONVEY, Lists.newArrayList(entity));
			}else {
				List<BatchTransportOrderEntity> falseList = (List<BatchTransportOrderEntity>) request.getSession().getAttribute(LASTFALSECONVEY);
				if(falseList == null) {
          request.getSession().setAttribute(LASTFALSECONVEY, Lists.newArrayList(entity));
        } else {
          falseList.add(entity);
        }
			}
			JsonResult result = JsonResult.getConveyResult("200", "失败,请检查数据后重新导入");
			result.put("success", false);
			return result; 
		}
		JsonResult result = JsonResult.getConveyResult("200", "新增运单成功");
		result.put("success", true);
		return result;
	}

	/**
	 * 校验运单的信息
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017-12-28 16:33:33
	 * @param map
	 * @return
	 */
	@RequestMapping("/batch/checkData")
	@ResponseBody
	public JsonResult checkOutConvey(@RequestBody RequestJsonEntity map) {
		try {
			User user = ContextHepler.getCurrentUser();
			map.put("company", user.getCompany());
			map.put("grid", user.getAccount());
			return orderService.checkOutConvey(map);
		} catch (Exception e) {
			return JsonResult.getConveyResult("400", "校验异常,请输入合理的数据!");
		}

	}
	/**
	 * 跳转到批量导入运单的页面 @return @exception
	 */
	@RequestMapping("/batch")
	public String toConveyBatch() {
		return "transport/convey/batch";
	}

	/**
	 * @Description: 跳转到运单批量导入页面,附带本次请求的成功失败列表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/batch/list")
	@ResponseBody
	public ConveyResult toConveyList(HttpServletRequest request) {
		ConveyResult conveyResult = new ConveyResult();
		List<BatchTransportOrderEntity> sucessList = (List<BatchTransportOrderEntity>) request.getSession()
				.getAttribute(LASTSUCCESSCONVEY);
		if (sucessList == null) {
			sucessList = Collections.emptyList();
		}
		conveyResult.setSuccessList(sucessList);
		List<BatchTransportOrderEntity> falseList = (List<BatchTransportOrderEntity>) request.getSession()
				.getAttribute(LASTFALSECONVEY);
		if (falseList == null) {
			falseList = Collections.emptyList();
		}
		conveyResult.setFalseList(falseList);
		return conveyResult;
	}
	
	/**
	 * 检查运单号是否已经存在
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkTransportCode")
	public JsonResult checkTransportCode(@RequestParam HashMap<String,Object> param) {
		String transportCode = (String) param.get("transportCode");
		boolean isExist = orderService.isTransportExistByCode(transportCode);
		return putJsonResult(null,isExist==true?"400":"200", isExist==true?"该运单号已存在":"该运单号不存在", "");
	}

	/**
	 * 导出失败的运单excel
	 */
	@RequestMapping("/exportExcel/falseConvey")
	public void downLoadExcel(Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			@SuppressWarnings("unchecked")
			List<BatchTransportOrderEntity> requestParam = (List<BatchTransportOrderEntity>) request.getSession().getAttribute(LASTFALSECONVEY);
			if (requestParam == null || requestParam.size() < 1) {
				throw new ParameterException("请求超时,失败的运单已经导出过");
			}
			String excelPath = request.getSession().getServletContext().getRealPath("/") + "static" + File.separator
					+ "upload" + File.separator + "template" + File.separator + "template.xls";
			logger.debug("开始读取excle模板,路径为" + excelPath);
			HSSFWorkbook workBook = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(excelPath)));
			HSSFSheet sheet = workBook.getSheetAt(0);

			HSSFRow head = sheet.getRow(0);
			HSSFCell cell = head.createCell(44);
			cell.setCellValue("错误信息");
			// 第二行开始输出
			for (int i = 0; i < requestParam.size(); i++) {
				BatchTransportOrderEntity entity = requestParam.get(i);
				HSSFRow row = sheet.createRow(i + 1);// 第二行开始输出
				ConveyUtils.buildRow(row, entity,false);
			}
			response.reset();
			response.setContentType("application/x-msdownload;charset=UTF-8");
			String fileName = java.net.URLEncoder.encode("失败的运单" + new Date().getTime(), "UTF-8");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ServletOutputStream out = response.getOutputStream();
			workBook.write(out);
			out.close();
			workBook.close();
			request.getSession().removeAttribute(LASTFALSECONVEY);
		} catch (Exception e) {
			exception(e, model);
		}

	}

	/**
	 * 导出成功的运单excel
	 */
	@RequestMapping("/exportExcel/successConvey")
	public void downLoadSuccessExcel(Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			@SuppressWarnings("unchecked")
			List<BatchTransportOrderEntity> requestParam = (List<BatchTransportOrderEntity>) request.getSession()
			.getAttribute(LASTSUCCESSCONVEY);
			if (requestParam == null || requestParam.size() < 1) {
				throw new ParameterException("请求超时,成功的运单已经导出过");
			}
			String excelPath = request.getSession().getServletContext().getRealPath("/") + "static" + File.separator
					+ "upload" + File.separator + "template" + File.separator + "template.xls";
			logger.debug("开始读取excle模板,路径为" + excelPath);
			HSSFWorkbook workBook = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(excelPath)));
			HSSFSheet sheet = workBook.getSheetAt(0);
			HSSFRow head = sheet.getRow(0);
			head.createCell(45).setCellValue("财凭号");
			for (int i = 0; i < requestParam.size(); i++) {
				BatchTransportOrderEntity entity = requestParam.get(i);
				HSSFRow row = sheet.createRow(i + 1);// 第二行开始输出
				ConveyUtils.buildRow(row, entity,true);
			}
			response.reset();
			response.setContentType("application/x-msdownload;charset=UTF-8");
			String fileName = java.net.URLEncoder.encode("成功的运单" + new Date().getTime(), "UTF-8");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ServletOutputStream out = response.getOutputStream();
			workBook.write(out);
			out.close();
			workBook.close();
			request.getSession().removeAttribute(LASTSUCCESSCONVEY);
		} catch (Exception e) {
			exception(e, model);
		}
	}

	/**
	 * 跳转运单作废界面
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked"})
	@RequestMapping("/cancel")
	public String toCancel(HttpServletRequest request) {
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
			logger.debug("用户操作行为日志:操作->废除页面跳转，登录已过期，请重新登录！");
		}
		return "transport/convey/cancel";
	}

	/**
	 * 查询运单作废页面
	 * 
	 * @return
	 */
	@RequestMapping("/searchCancelWaybill")
	@ResponseBody
	public JsonResult searchCancelWaybill(@RequestBody TransportOrderCancelResult conveyCancel) {
		String grid = null;
		try {
			User user = ContextHepler.getCurrentUser();
			grid = user.getAccount();
			logger.debug("用户操作行为日志:操作->废除运单查询;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		} catch (Exception e) {
			logger.debug("ConveyController searchCancelWaybill(param) param:" + JSON.toJSONString(conveyCancel) + " 报错："
					+ e.getMessage());
			logger.debug("用户操作行为日志:操作->废除运单查询");
			return JsonResult.getConveyResult("500", "登录已过期，请重新登录！");
		}
		try {

			return  orderService.findCancelTransportOrder(conveyCancel, grid);
		} catch (Exception e) {
			logger.debug("ConveyController searchCancelWaybill(param) param:" + JSON.toJSONString(conveyCancel) + " 报错："
					+ e.getMessage());
			return JsonResult.getConveyResult("400", "查询请求报错，请联系系统维护人员");
		}
	}

	/**
	 * 保存作废（作废或取消作废）
	 * 
	 * @return
	 */
	@RequestMapping("/cancelWaybill")
	@ResponseBody
	public JsonResult cancelWaybill(@RequestBody TransportOrderCancelResult conveyCancel) {
		String grid = "";
		String userName = "";
		try {
			User user = ContextHepler.getCurrentUser();
			grid = user.getAccount();
			userName = user.getUserName();
			logger.debug("用户操作行为日志:操作->保存废除运单;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
			conveyCancel.setCompanyName(user.getCompany());
		} catch (Exception e) {
			logger.debug("ConveyController cancelWaybill(param) param:" + JSON.toJSONString(conveyCancel) + " 报错："
					+ e.getMessage());
			logger.debug("用户操作行为日志:操作->保存废除运单");
			JsonResult jsonResult = new JsonResult();
			jsonResult.put("resultCode", 500);
			jsonResult.put("reason", "登录已过期，请重新登录！");
			return jsonResult;
		}
		try {
			JsonResult jsonResult = orderService.saveCancelWaybill(conveyCancel, grid,userName);
			return jsonResult;
		} catch (Exception e) {
			logger.debug("ConveyController cancelWaybill(param) param:" + JSON.toJSONString(conveyCancel) + " 报错："
					+ e.getMessage());
			JsonResult jsonResult = new JsonResult();
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "保存请求报错，请联系系统维护人员");
			return jsonResult;
		}
	}
	
	/**
	 * 运单导出
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年3月22日
	 * @param request
	 * @param searchModel
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/exportTransportOrder")
	public void exportTransportOrder(HttpServletRequest request,HttpServletResponse response,Model model) {
		
		TransportOrderSearch transportOrderSearch = new TransportOrderSearch();
		transportOrderSearch.setYdbhid(request.getParameter("ydbhid"));
		transportOrderSearch.setDaozhan(request.getParameter("daozhan"));
		transportOrderSearch.setKhbm(request.getParameter("khbm"));
		transportOrderSearch.setFhdwmch(request.getParameter("fhdwmch"));
		transportOrderSearch.setZhipiao(request.getParameter("zhipiao"));
		transportOrderSearch.setKhdh(request.getParameter("khdh"));
		transportOrderSearch.setShhrmch(request.getParameter("shhrmch"));
		transportOrderSearch.setIsFinanceCertify(request.getParameter("isFinanceCertify"));
		String[] dates = DateRangeUtil.finds(request.getParameter("searchdate"), 2);
		if (dates.length > 0) {
			transportOrderSearch.setFhshjStart(dates[0] + " 00:00");
		}
		if (dates.length > 1) {
			transportOrderSearch.setFhshjEnd(dates[1] + " 23:59");
		}
		
		List<Map<String, Object>> exportTransportOrderList = new ArrayList<Map<String,Object>>();
		try {
			exportTransportOrderList = orderService.exportTransportOrderList(transportOrderSearch);
			logger.debug("TransportSignRecordController exportTransportOrder(param) param:" + JSON.toJSONString(transportOrderSearch));
		} catch (ParameterException e) {
			exception(e,model);
			logger.debug("TransportSignRecordController exportTransportOrder(param) param:" + JSON.toJSONString(transportOrderSearch) +" 报错："+e.getCause());
		}catch(BusinessException e){
			exception(e,model);
			logger.debug("TransportSignRecordController exportTransportOrder(param) param:" + JSON.toJSONString(transportOrderSearch) +" 报错："+e.getCause());
		}	
		
		// 设置excel信息
		String[] showColumnName = { "结算公司", "快运标识", "快运客户性质", "绿色通道", "运单编号", "客户单号", "线路", "发货时间", "是否生成财凭", "始发地", "目的地",
				 "始发站", "发站网点", "到站", "到站网点", "品名", "件数", "重量", "体积", "投保金额", "客户编码", "销售代码", "发货单位", "行业类别", "发货单位电话",
				 "收货单位", "收货单位电话", "收货人手机", "运输号码", "制票员", "货运员", "装卸班组", "托运人签名", "收货人地址", "业务类型", "运行状态", "计费方式", 
				 "计费重量", "计费体积", "派送指示", "运到天数", "作废状态", "型号", "服务方式", "付款方式", "代收货款", "等托指放货" };// 列名
		List<String[]> exportContentList = new ArrayList<String[]>();
		for (Map<String, Object> result : exportTransportOrderList) {
			String content[] = { result.get("jsgs") == null ? "" : result.get("jsgs").toString(), result.get("is_kaiyun") == null ? "" : result.get("is_kaiyun").toString(), result.get("ky_kind") == null ? "" : result.get("ky_kind").toString(), 
				result.get("is_greenchannel") == null ? "" : result.get("is_greenchannel").toString(), result.get("ydbhid") == null ? "" : result.get("ydbhid").toString(), result.get("khdh") == null ? "" : result.get("khdh").toString(), result.get("xianlu") == null ? "" : result.get("xianlu").toString(), 
				result.get("fhshj") == null ? "" : result.get("fhshj").toString(), result.get("cwpzhy") == null ? "" : result.get("cwpzhy").toString(), result.get("begin_placename") == null ? "" : result.get("begin_placename").toString(), result.get("end_placename") == null ? "" : result.get("end_placename").toString(),
				result.get("fazhan") == null ? "" : result.get("fazhan").toString(), result.get("shhd") == null ? "" : result.get("shhd").toString(), result.get("daozhan") == null ? "" : result.get("daozhan").toString(), result.get("dzshhd") == null ? "" : result.get("dzshhd").toString(), 
				result.get("pinming") == null ? "" : result.get("pinming").toString(), Double.valueOf(result.get("jianshu") == null ?  "0": result.get("jianshu").toString()).toString(), Double.valueOf(result.get("zhl") == null ?  "0": result.get("zhl").toString()).toString(), 
				Double.valueOf(result.get("tiji") == null ?  "0": result.get("tiji").toString()).toString(), Double.valueOf(result.get("tbje") == null ?  "0": result.get("tbje").toString()).toString(),result.get("khbm") == null ? "" : result.get("khbm").toString(),
				result.get("xiaoshoucode") == null ? "" : result.get("xiaoshoucode").toString(), result.get("FHDWMCH") == null ? "" : result.get("FHDWMCH").toString(), result.get("FHKHHY") == null ? "" : result.get("FHKHHY").toString(), result.get("fhdwlxdh") == null ? "" : result.get("fhdwlxdh").toString(),
				result.get("shhrmch") == null ? "" : result.get("shhrmch").toString(), result.get("shhrlxdh") == null ? "" : result.get("shhrlxdh").toString(), result.get("shhryb") == null ? "" : result.get("shhryb").toString(), result.get("yshhm") == null ? "" : result.get("yshhm").toString(),
				result.get("zhipiao") == null ? "" : result.get("zhipiao").toString(), result.get("hyy") == null ? "" : result.get("hyy").toString(), result.get("zhxbz") == null ? "" : result.get("zhxbz").toString(), result.get("tyrqm") == null ? "" : result.get("tyrqm").toString(), result.get("shhrdzh") == null ? "" : result.get("shhrdzh").toString(),
				result.get("ywlx") == null ? "" : result.get("ywlx").toString(), result.get("yxzt") == null ? "" : result.get("yxzt").toString(), result.get("jffs") == null ? "" : result.get("jffs").toString(), result.get("jfzl") == null ? "" : result.get("jfzl").toString(), result.get("jftj") == null ? "" : result.get("jftj").toString(),
				result.get("pszhsh") == null ? "" : result.get("pszhsh").toString(), result.get("ydts") == null ? "" : result.get("ydts").toString(), result.get("zuofei") == null ? "" : result.get("zuofei").toString(), result.get("xh") == null ? "" : result.get("xh").toString(), result.get("fwfs") == null ? "" : result.get("fwfs").toString(),
				result.get("fkfs") == null ? "" : result.get("fkfs").toString(), Double.valueOf(result.get("dashoukuan_yuan") == null ?  "0": result.get("dashoukuan_yuan").toString()).toString(), result.get("tzfhzt") == null ? "" : result.get("tzfhzt").toString() };
			exportContentList.add(content);
		}
		String sheetName = "运单记录信息";
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
			exception(e,model);
			logger.debug("运单记录导出异常！报错内容：" + e.getMessage());
		}
	}
}
