package com.ycgwl.kylin.web.transport.controller;

import com.alibaba.fastjson.JSON;
import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.entity.RequestJsonEntity;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.security.client.ContextHepler;
import com.ycgwl.kylin.security.entity.User;
import com.ycgwl.kylin.transport.entity.*;
import com.ycgwl.kylin.transport.entity.adjunct.Company;
import com.ycgwl.kylin.transport.service.api.AdjunctSomethingService;
import com.ycgwl.kylin.transport.service.api.GeneralInfoService;
import com.ycgwl.kylin.transport.service.api.IBundleArriveService;
import com.ycgwl.kylin.transport.service.api.IDeliveryOperateService;
import com.ycgwl.kylin.util.SmsUtils;
import com.ycgwl.kylin.web.transport.BaseController;
import com.ycgwl.kylin.web.transport.util.ExcelReportForDelivery;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

@Controller
@RequestMapping("/transport/bundleArrive")
public class BundleArriveController extends BaseController {
	
	/**派车单查询导出钱的session名称*/
	private static final String DELIVERYDOCUMENTS = "DELIVERYDOCUMENTS";
	/**派车单查询(司机版)导出钱的session名称*/
	private static final String DELIVERYDOCUMENTS_DRIVER = "DELIVERYDOCUMENTS_DRIVER";
	/**到货装载清单导出的session名称*/
	private static final String BUNDLEARRIVE = "BUNDLEARRIVE";
	
	private static final String PICK_UP_FOR_PRINT = "PICK_UP";
	 
	@Autowired
	private IBundleArriveService bundleArriveService;

	@Autowired
	private IDeliveryOperateService deliveryOperateService;
	
	@Autowired
	private GeneralInfoService generalInfoService;
	
	@Resource
	private AdjunctSomethingService adjunctSomethingService;

	@RequestMapping("/arrive/sendSign")
	public String toSendSign() {
		return "/transport/arrive/sendSign";
	}
	
	@RequestMapping("/arrive/toPrintSendSign")
	public String toPrintSendSign() {
		return "/transport/arrive/printSendSign";
	}
	
	@RequestMapping("/convey/toCarrayPrint")
	public String toCarrayPrint() {
		return "/transport/convey/carrayPrint";
	}
	
	@RequestMapping("/arrive/toPrintReceiptSign")
	public String toPrintReceiptSign() {
		return "/transport/arrive/printReceiptSign";
	}

	@RequestMapping("/arrive/sendSignSettle")
	public String tosendSignSettle() {
		return "/transport/arrive/sendSignSettle";
	}

	@RequestMapping("/arrive/sendSignNodriver")
	public String tosendSignNodriver() {
		return "/transport/arrive/sendSignNodriver";
	}

	@RequestMapping("/documents/delivery/manage/dirver")
	public String todeliverydocumentsmanageForDriver() {
		return "/transport/arrive/driver";
	}

	@RequestMapping("/bill/delivery/manage")
	public String tobillOfdeliverymanage() {
		return "transport/arrive/receitCheck";
	}

	@RequestMapping("/arrive/receiptSign")
	public String toreceitSign() {
		return "transport/arrive/receiptSign";
	}

	@RequestMapping("/arrive/receipttSignSettle/{pcid}")
	public String toreceipttSignSettle(HttpServletRequest request,@PathVariable("pcid")Integer pcid) {
		request.setAttribute("pcid",pcid);
		User user = ContextHepler.getCurrentUser();
		request.setAttribute("CURR_USER_NAME", user.getUserName());
		request.setAttribute("CURR_COMPANY_NAME", user.getCompany());
		request.setAttribute("CURR_USER_GRID", user.getAccount());
		return "transport/arrive/receiptSignSettle";
	}

	/**
	 * 到货装载清单页面跳转
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/search")
	public String bundleArrivePage(HttpServletRequest request) {
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
			logger.debug("用户操作行为日志:操作->到货装载查询跳转，登录已过期，请重新登录！");
		}
		return "transport/arrive/search";
	}

	/**
	 * 录入上门取货指令跳转
	 */
	@RequestMapping(value = "/pickFormHome")
	public String pickFormHomeOrderPage(HttpServletRequest request) {
		User user = ContextHepler.getCurrentUser();
		request.setAttribute("CURR_USER_NAME", user.getUserName());
		request.setAttribute("CURR_COMPANY_NAME", user.getCompany());
		request.setAttribute("CURR_USER_GRID", user.getAccount());
		return "transport/arrive/pickformhome";
	}

	/**
	 * 取货派车查询页面跳转
	 */
	@RequestMapping(value = "/pickGoods/search")
	public String dispatchPage(HttpServletRequest request) {
		return "transport/arrive/pickgoods";
	}

	/**
	 * 提货单查询
	 */
	@RequestMapping("/bill/delivery/manage/search")
	@ResponseBody
	public JsonResult billOfdeliverymanage(@RequestBody BillOfdeliveryRequestEntity entity) {
		try {
			User user = ContextHepler.getCurrentUser();
			entity.setCompanyName(user.getCompany());
			return deliveryOperateService.billOfdeliverymanage(entity);
		} catch (BusinessException be) {
			return JsonResult.getConveyResult("400", be.getTipMessage());
		} catch (ParameterException pe) {
			return JsonResult.getConveyResult("400", pe.getTipMessage());
		} catch (Exception e) {
			return JsonResult.getConveyResult("400", "服务器异常,请重新操作");
		}
	}

	/**
	 * 单条提货单的查看
	 */
	@RequestMapping("/bill/delivery/search")
	@ResponseBody
	public JsonResult searchBillOfdelivery(@RequestBody RequestJsonEntity entity) {
		try {
			return deliveryOperateService.searchBillOfdelivery(entity);
		} catch (Exception e) {
			return JsonResult.getConveyResult("400", "服务器异常,请重新操作");
		}
	}

	

	@RequestMapping("/bill/delivery/update")
	@ResponseBody
	public JsonResult updateBillOfdelivery(@RequestBody RequestJsonEntity entity) {
		try {
			User user = ContextHepler.getCurrentUser();
			entity.put("company", user.getCompany());
			entity.put("account", user.getAccount());
			entity.put("username", user.getUserName());
			return deliveryOperateService.modifyBillOfdelivery(entity);
		} catch (Exception e) {
			return JsonResult.getConveyResult("400", "服务器异常,请重新操作");
		}
	}

	@RequestMapping("/documents/delivery/manage")
	public String todeliverydocumentsmanage() {
		return "transport/arrive/sendSignCheck";
	}

	/**
	 * 送货单查询
	 */
	@RequestMapping("/documents/delivery/manage/search")
	@ResponseBody
	public JsonResult deliverydocumentsmanage(@RequestBody RequestJsonEntity entity , HttpServletRequest request) {
		User user = ContextHepler.getCurrentUser();
		try {
			logger.debug("用户操作行为日志:操作->送货单查询;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
			entity.put("account", user.getAccount());
			entity.put("company", user.getCompany());
			List<HashMap<String, Object>> list = deliveryOperateService.deliverydocumentsmanage(entity);
			JsonResult result = JsonResult.getConveyResult("200", "查询成功");
			result.put("collection",list);
			request.getSession().setAttribute(DELIVERYDOCUMENTS, list);
			return result;
		} catch (BusinessException be) {
			return JsonResult.getConveyResult("400", be.getTipMessage());
		} catch (ParameterException pe) {
			return JsonResult.getConveyResult("400", pe.getTipMessage());
		} catch (Exception e) {
			return JsonResult.getConveyResult("400", "服务器异常,请重新操作");
		}
	}
	
	/**
	 * 送货单导出
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/exportExcel/delivery")
	public void downLoadDeliveryVersion(HttpServletRequest request,HttpServletResponse response,Model model) {
		try {
			//先拿到要导出的数据
			List<Map<String,Object>> list = (List<Map<String,Object>>)request.getSession().getAttribute(DELIVERYDOCUMENTS);
			String excelPath = request.getSession().getServletContext().getRealPath("/") + "static" + File.separator
					+ "upload" + File.separator + "template" + File.separator + "送货签收单.xls";
			logger.debug("开始读取excle模板,路径为" + excelPath);
			HSSFWorkbook wookBook = ExcelReportForDelivery.BuildDeliveryWorkSheet(list, excelPath);
			response.reset();
			response.setContentType("application/x-msdownload;charset=UTF-8");
			String fileName = java.net.URLEncoder.encode("送货签收单" + new Date().getTime(), "UTF-8");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ServletOutputStream out = response.getOutputStream();
			wookBook.write(out);
			out.close();
			wookBook.close();
		}catch(Exception e) {
			exception(e, model);
		}
		
	}
	/**
	 * 送货单查询 -司机版
	 */
	@RequestMapping("/documents/delivery/manage/dirver/search")
	@ResponseBody
	public JsonResult deliverydocumentsmanageForDriver(@RequestBody RequestJsonEntity entity, HttpServletRequest request) {
		try {
			User user = ContextHepler.getCurrentUser();
			entity.put("account", user.getAccount());
			entity.put("company", user.getCompany());
			logger.debug("用户操作行为日志:操作->送货单查询(司机);工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
			entity.put("account", user.getAccount());
			List<HashMap<String,Object>> page = deliveryOperateService.deliverydocumentsmanageForDriver(entity);
			JsonResult result = JsonResult.getConveyResult("200", "查询成功");
			result.put("collection", page);
			request.getSession().setAttribute(DELIVERYDOCUMENTS_DRIVER, page);
			return result;
		} catch (BusinessException be) {
			return JsonResult.getConveyResult("400", be.getTipMessage());
		} catch (ParameterException pe) {
			return JsonResult.getConveyResult("400", pe.getTipMessage());
		} catch (Exception e) {
			return JsonResult.getConveyResult("400", "服务器异常,请重新操作");
		}
	}

	/**
	 * 送货单
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/exportExcel/driverdelivery")
	public void downLoadDeliveryDriverVersion(HttpServletRequest request,HttpServletResponse response,Model model) {
		try {
			//先拿到要导出的数据
			List<Map<String,Object>> list = (List<Map<String,Object>>)request.getSession().getAttribute(DELIVERYDOCUMENTS_DRIVER);
			String excelPath = request.getSession().getServletContext().getRealPath("/") + "static" + File.separator
					+ "upload" + File.separator + "template" + File.separator + "送货派车单司机.xls";
			logger.debug("开始读取excle模板,路径为" + excelPath);
			HSSFWorkbook wookBook = ExcelReportForDelivery.BuildDeliveryDriverWorkSheet(list, excelPath);
			response.reset();
			response.setContentType("application/x-msdownload;charset=UTF-8");
			String fileName = java.net.URLEncoder.encode("送货派车单司机" + new Date().getTime(), "UTF-8");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ServletOutputStream out = response.getOutputStream();
			wookBook.write(out);
			out.close();
			wookBook.close();
		}catch(Exception e) {
			exception(e, model);
		}
		
	}
	
	

	
	/**
	 * 到货装载查询
	 * 
	 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at
	 *            2018-01-18
	 * @return
	 */
	@RequestMapping("/searchBundleArrive")
	@ResponseBody
	public JsonResult findBundleArriveList(@RequestBody Map<String, Object> param, HttpServletRequest request) {
		try {
			User user = ContextHepler.getCurrentUser();
			logger.debug("用户操作行为日志:操作->查询到货装载;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		} catch (Exception e) {
			logger.debug("BundleArriveController findBundleArriveList(map) map:" + JSON.toJSONString(param) + " 报错："
					+ e.getMessage());
			logger.debug("用户操作行为日志:操作->查询到货装载");
			return JsonResult.getConveyResult("500", "登录已过期，请重新登录！");
		}
		JsonResult jsonResult = new JsonResult();
		try {
			List<BundleArriveEntity> resultList = bundleArriveService.findBundleArriveList(param);
			request.getSession().setAttribute(BUNDLEARRIVE, resultList);
			jsonResult.put("resultCode", "200");
			jsonResult.put("message", resultList);
		} catch (Exception e) {
			logger.error("##Error Exception--------到货装载查询失败---------", e);
			jsonResult.put("resultCode", "400");
			jsonResult.put("reason", "到货装载查询失败");
		}
		return jsonResult;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/download/bundleArrive")
	public void downLoadBundleArrive(HttpServletRequest request,HttpServletResponse response,Model model) {
		//要打印的数据
		try {
			String menu= "到货装载清单";
			//先拿到要导出的数据
			List<BundleArriveEntity> list = (List<BundleArriveEntity>)request.getSession().getAttribute(BUNDLEARRIVE);
			String excelPath = request.getSession().getServletContext().getRealPath("/") + "static" + File.separator
					+ "upload" + File.separator + "template" + File.separator + menu + ".xls";
			logger.debug("开始读取excle模板,路径为" + excelPath);

			List<TransportBasicData> basicList = generalInfoService.queryAllBasicInfo();
			HSSFWorkbook wookBook = ExcelReportForDelivery.BuildBundleArriveWorkSheet(list, excelPath,basicList);
			response.reset();
			response.setContentType("application/x-msdownload;charset=UTF-8");
			String fileName = java.net.URLEncoder.encode( menu + new Date().getTime(), "UTF-8");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ServletOutputStream out = response.getOutputStream();
			wookBook.write(out);
			out.close();
			wookBook.close();
		}catch(Exception e) {
			exception(e, model);
		}
	}
	
	
	/**
	 * 发送短信通知
	 * 
	 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at
	 *            2018-01-18
	 * @return
	 */
	@RequestMapping("/sendMessageByPhone")
	@ResponseBody
	public JsonResult sendMessageByPhone(@RequestParam Map<String, Object> param) {
		try {
			User user = ContextHepler.getCurrentUser();
			logger.debug("用户操作行为日志:操作->发送短信通知;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		} catch (Exception e) {
			logger.debug("BundleArriveController sendMessageByPhone(map) map:" + JSON.toJSONString(param) + " 报错："
					+ e.getMessage());
			logger.debug("用户操作行为日志:操作->发送短信通知");
			JsonResult jsonResult = new JsonResult();
			jsonResult.put("resultCode", 500);
			jsonResult.put("reason", "登录已过期，请重新登录！");
			return jsonResult;
		}
		JsonResult jsonResult = null;
		try {
			Map<String, Object> sendInfoMap = bundleArriveService.checkSendMessage(param);
			StringBuffer buff = new StringBuffer();
			for (Map.Entry<String, Object> entry : sendInfoMap.entrySet()) {
				buff.append(",").append(entry.getValue());
			}
			String mobile = (String) sendInfoMap.get("mobile");
			SmsUtils.sendMessage(mobile, buff.toString());
			jsonResult = new JsonResult();
			jsonResult.put("resultCode", "200");
		} catch (Exception e) {
			jsonResult.put("resultCode", "400");
			jsonResult.put("reason", "短信发送失败");
		}
		return jsonResult;
	}

	
	
	/**
	 * 查询录入上门取货指令
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping("/searchPickFormHomeOrder")
	@ResponseBody
	public JsonResult searchPickFormHomeOrder(@RequestBody Map<String, Object> param) {
		try {
			User user = ContextHepler.getCurrentUser();
			logger.debug("用户操作行为日志:操作->查询录入上门取货;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		} catch (Exception e) {
			logger.debug("BundleArriveController searchPickFormHomeOrder(pcid) pcid:" + JSON.toJSONString(param.get("pcid")) + " 报错："
					+ e.getMessage());
			logger.debug("用户操作行为日志:操作->查询录入上门取货;");
			JsonResult jsonResult = new JsonResult();
			jsonResult.put("resultCode", 500);
			jsonResult.put("reason", "登录已过期，请重新登录！");
			return jsonResult;
		}
		User user = ContextHepler.getCurrentUser();
		param.put("userName", user.getUserName());
		param.put("grid", user.getAccount());
		param.put("gs",user.getCompany());
		param.put("gsid", user.getCompanyCode());
		//Integer openType = (Integer) param.get("openType");
		if(param.get("pcid")==null){
			return JsonResult.getConveyResult("400", "派车单号不能为空");
		}
		param.put("pcid", param.get("pcid").toString());
		Integer openType = (Integer) param.get("openType");
		try {
			return bundleArriveService.searchPickFormHomeOrder(param,openType);
		} catch (BusinessException e) {
			e.printStackTrace();
			return JsonResult.getConveyResult("400", e.getTipMessage());
		} catch (ParameterException e) {
			e.printStackTrace();
			return JsonResult.getConveyResult("400", e.getTipMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.getConveyResult("400", "查询失败");
		}
	}

	/**
	 * 录入上门取货指令跳转
	 */
	@RequestMapping(value = "/picktohome")
	public String pickFormHomeOrderPage1(HttpServletRequest request) {
		UserModel userModel = new UserModel();
		User user = ContextHepler.getCurrentUser();
		userModel.setAccount(user.getAccount());
		userModel.setUserName(user.getUserName());
		userModel.setPassword(user.getPassword());
		userModel.setCreateTime(user.getCreateTime());
		userModel.setEnable(user.getEnable());
		userModel.setCompany(user.getCompanyString());
		userModel.setCompanyCode(user.getCompanyCode());
		userModel.setSubCompany(user.getSubCompany());
		
		
		request.setAttribute("CURR_USER_NAME", user.getUserName());
		request.setAttribute("CURR_COMPANY_NAME", user.getCompany());
		request.setAttribute("CURR_USER_GRID", user.getAccount());
		request.setAttribute("CURR_NET_POINT", user.getNetPoint());
		
		logger.debug("用户操作行为日志:操作->保存录入上门取货;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		return "transport/arrive/picktohome";
	}
	
	/**
	 * 调度派车跳转
	 */
	@RequestMapping(value = "/picktohomeEdit/{pcid}/{openType}")
	public String picktohomeEdit(HttpServletRequest request,@PathVariable("pcid")Integer pcid,@PathVariable("openType")Integer openType) {
		UserModel userModel = new UserModel();
		User user = ContextHepler.getCurrentUser();
		userModel.setAccount(user.getAccount());
		userModel.setUserName(user.getUserName());
		userModel.setPassword(user.getPassword());
		userModel.setCreateTime(user.getCreateTime());
		userModel.setEnable(user.getEnable());
		userModel.setCompany(user.getCompany());
		userModel.setCompanyCode(user.getCompanyCode());
		userModel.setSubCompany(user.getSubCompany());
		request.setAttribute("pcid",pcid);
		request.setAttribute("openType",openType);
		logger.debug("用户操作行为日志:操作->保存录入上门取货;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		return "transport/arrive/picktohome";
	}

	/**
	 * 保存录入上门取货指令
	 *
	 * @return
	 */
	@RequestMapping("/savePickFormHomeOrder")
	@ResponseBody
	public JsonResult savePickFormHomeOrder(@RequestBody BundlePickHome bundlePickHome) {
		UserModel userModel = new UserModel();
		try {
			User user = ContextHepler.getCurrentUser();
			userModel.setAccount(user.getAccount());
			userModel.setUserName(user.getUserName());
			userModel.setPassword(user.getPassword());
			userModel.setCreateTime(user.getCreateTime());
			userModel.setEnable(user.getEnable());
			userModel.setCompany(user.getCompany());
			userModel.setCompanyCode(user.getCompanyCode());
			userModel.setSubCompany(user.getSubCompany());
			logger.debug("用户操作行为日志:操作->保存录入上门取货;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		} catch (Exception e) {
			logger.debug("BundleArriveController savePickFormHomeOrder(map) map:" + JSON.toJSONString(bundlePickHome)
					+ " 报错：" + e.getMessage());
			logger.debug("用户操作行为日志:操作->保存录入上门取货;");
			JsonResult jsonResult = new JsonResult();
			jsonResult.put("resultCode", 500);
			jsonResult.put("reason", "登录已过期，请重新登录！");
			return jsonResult;
		}
		JsonResult jsonResult = new JsonResult();
		try {
			jsonResult = bundleArriveService.savePickFormHomeOrder(bundlePickHome, userModel);
		} catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
		} catch (BusinessException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
		} catch (Exception e) {
			jsonResult.put("resultCode", 400);
			if (e.getMessage().indexOf("Transaction timed out") > 1) {
				jsonResult.put("reason", "保存失败，服务器处理超时，请重试");
			} else {
				jsonResult.put("reason", "保存失败，其他原因");
			}
			e.printStackTrace();
			return jsonResult;
		}
		return jsonResult;
	}

	/**
	 * 取货派车查询导出
	 * @author fusen.feng
	 * @version[2018.02.07]
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/exptVehicleDispatching")
	@ResponseBody
	public JsonResult exptVehicleDispatching(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> param){
		JsonResult jsonResult = this.getJsonResult();
		try {
			String excelPath = request.getSession().getServletContext().getRealPath("/") + "static" + File.separator
					+ "upload" + File.separator ;
			logger.debug("开始读取excle模板,路径为" + excelPath);
			List<DispatchCarPickGoods> resultList = (List<DispatchCarPickGoods>) request.getSession().getAttribute(PICK_UP_FOR_PRINT);
			HSSFWorkbook wookBook = ExcelReportForDelivery.VehicleDispatching(resultList,excelPath+ "template" + File.separator+ "exptVehicle.xls");		            			
			// 输出到根目录
			FileOutputStream fout = new FileOutputStream(excelPath+ "exptVehicle.xls");
			wookBook.write(fout);			
			fout.close();			
			wookBook.close();
		} catch (Exception e) {
			logger.error("##ERROR Exception ---取货派车查询异常---", e);
			return this.putJsonResult(jsonResult, "500", "服务器异常", null);
		}
		
		return jsonResult;
	}
	
	/**
	 * 取货派送处理查询
	 * 
	 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at
	 *            2018-01-24
	 * @return
	 */
	@RequestMapping("/searchDeliveryCarHandling")
	@ResponseBody
	public JsonResult searchDeliveryCarHandling(@RequestBody Map<String, Object> param, HttpServletRequest request) {
		try {
			User user = ContextHepler.getCurrentUser();
			logger.debug("用户操作行为日志:操作->取货派送处理查询;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		} catch (Exception e) {
			logger.debug("BundleArriveController searchDeliveryCarHandling(map) map:" + JSON.toJSONString(param)
					+ " 报错：" + e.getMessage());
			logger.debug("用户操作行为日志:操作->取货派送处理查询");
			JsonResult jsonResult = new JsonResult();
			jsonResult.put("resultCode", 500);
			jsonResult.put("reason", "登录已过期，请重新登录！");
			return jsonResult;
		}
		JsonResult jsonResult = new JsonResult();
		try {
			List<DispatchCarPickGoods> resultList = bundleArriveService.searchDeliveryCarHandling(param);
			request.getSession().setAttribute(PICK_UP_FOR_PRINT, resultList);
			jsonResult.put("resultCode", "200");
			jsonResult.put("message", resultList);
		} catch (Exception e) {
			jsonResult.put("resultCode", "400");
			jsonResult.put("reason", "到货装载查询失败");
		}
		return jsonResult;
	}

	/**
	 * 取货派车签收核算查询
	 * 
	 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at
	 *            2018-01-24
	 * @return
	 */
	@RequestMapping("/searchPickGoodsAccount")
	@ResponseBody
	public JsonResult searchPickGoodsAccount(@RequestBody Map<String, Object> param) {
		try {
			User user = ContextHepler.getCurrentUser();
			param.put("grid",user.getAccount());
			param.put("companyCode",user.getCompanyCode());
			logger.debug("用户操作行为日志:操作->取货派车签收核算查询;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		} catch (Exception e) {
			logger.debug("BundleArriveController searchDeliveryCarHandling(map) map:" + JSON.toJSONString(param)
					+ " 报错：" + e.getMessage());
			logger.debug("用户操作行为日志:操作->取货派车签收核算查询");
			JsonResult jsonResult = new JsonResult();
			jsonResult.put("resultCode", 500);
			jsonResult.put("reason", "登录已过期，请重新登录！");
			return jsonResult;
		}
		JsonResult jsonResult = new JsonResult();
		try {
			jsonResult = bundleArriveService.searchPickGoodsAccount(param);
			jsonResult.put("resultCode", "200");
			jsonResult.put("message", "查询成功");
		} catch (BusinessException e) {
			e.printStackTrace();
			return JsonResult.getConveyResult("400", e.getTipMessage());
		} catch (ParameterException e) {
			e.printStackTrace();
			return JsonResult.getConveyResult("400", e.getTipMessage());
		}catch (Exception e) {
			jsonResult.put("T_CAR_IN_DETAIL_THREE_LIST_CAN_MODIFY", false);
			jsonResult.put("T_CAR_IN_INFO_CAN_MODIFY", false);
			jsonResult.put("T_CAR_IN_DETAIL_FIVE_LIST_CAN_MODIFY", false);
			jsonResult.put("saveButtonCanMondify", false);
			jsonResult.put("resultCode", "400");
			jsonResult.put("reason", "取货派车签收核算查询失败");
		}
		return jsonResult;
	}

	/**
	 * 保存取货派车签收核算
	 * 
	 * @developer Create by <a href="mailto:109668@ycgwl.com">lihuixia</a> at
	 *            2018-01-24
	 * @return
	 */
	@RequestMapping("/savePickGoodsAccount")
	@ResponseBody
	public JsonResult savePickGoodsAccount(@RequestBody BundleAccount account) {
		try {
			User user = ContextHepler.getCurrentUser();
			account.setGsid(user.getCompanyCode());
			logger.debug("用户操作行为日志:操作->取货派车签收核算保存;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		} catch (Exception e) {
			logger.debug("BundleArriveController savePickGoodsAccount(map) map:" + JSON.toJSONString(account) + " 报错："
					+ e.getMessage());
			logger.debug("用户操作行为日志:操作->取货派车签收核算保存");
			JsonResult jsonResult = new JsonResult();
			jsonResult.put("resultCode", 500);
			jsonResult.put("reason", "登录已过期，请重新登录！");
			return jsonResult;
		}
		JsonResult jsonResult = new JsonResult();
		try {
			bundleArriveService.savePickGoodsAccount(account);
			jsonResult.put("resultCode", "200");
			jsonResult.put("message", "保存成功");
		}  catch (BusinessException e) {
			e.printStackTrace();
			return JsonResult.getConveyResult("400", e.getTipMessage());
		} catch (ParameterException e) {
			e.printStackTrace();
			return JsonResult.getConveyResult("400", e.getTipMessage());
		} catch (Exception e) {
			logger.debug("FinanceTransportController savePickGoodsAccount(param) param:" + JSON.toJSONString(account)
					+ " 报错：" + e.getMessage());
			jsonResult.put("resultCode", 400);
			if (e.getMessage().indexOf("Transaction timed out") > 1) {
				jsonResult.put("reason", "因服务器繁忙保存失败，请重试");
			} else {
				jsonResult.put("reason", "保存失败");
			}
		}
		return jsonResult;
	}

	/**
	 * 取货单司机的单条查询
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2018-03-08 13:37:19
	 * @param entity
	 * @return
	 */
	@RequestMapping("/pickUp/query")
	@ResponseBody
	public JsonResult getT_Car_InForPrint(@RequestBody RequestJsonEntity entity) {
		try {
			return bundleArriveService.getT_Car_InForPrint(entity);
		} catch (BusinessException e) {
			return JsonResult.getConveyResult("500", e.getTipMessage());
		}catch (ParameterException e) {
			return JsonResult.getConveyResult("500", e.getTipMessage());
		}catch (Exception e) {
			return JsonResult.getConveyResult("500", "请刷新页面重新查询");
		}
	}
	

	/**
	 * 提货签收单的保存按钮
	 */
	@RequestMapping("/bill/delivery/save")
	//@RequiresPermissions("bundleArrive:pickdelivery:save")
	@ResponseBody
	public JsonResult saveBillOfdelivery(@RequestBody RequestJsonEntity entity) {
		User user = ContextHepler.getCurrentUser();
		entity.put("company", user.getCompany());
		entity.put("username", user.getUserName());
		entity.put("account", user.getAccount());
		entity.put("kpr", user.getAccount());
		try {
			return deliveryOperateService.saveBillOfdelivery(entity);
		} catch (BusinessException be) {
			return JsonResult.getConveyResult("400", be.getTipMessage());
		} catch (ParameterException pe) {
			return JsonResult.getConveyResult("400", pe.getTipMessage());
		} catch (Exception e) {
			return JsonResult.getConveyResult("400", "服务器异常,请重新操作");
		}
	}
	
	/**
	 * 送货签收单保存按钮
	 */
	@RequestMapping("/documents/delivery/save")
	//@RequiresPermissions("bundleArrive:senddelivery:save")
	@ResponseBody
	public JsonResult savedeliverydocuments(@RequestBody RequestJsonEntity entity) {
		User user = ContextHepler.getCurrentUser();
		entity.put("company", user.getCompany());
		entity.put("account", user.getAccount());
		entity.put("gsid", user.getCompanyCode());
		entity.put("grid", user.getUserName());
		try {
			return deliveryOperateService.savedeliverydocuments(entity);
		} catch (BusinessException be) {
			return JsonResult.getConveyResult("400", be.getTipMessage());
		} catch (ParameterException pe) {
			return JsonResult.getConveyResult("400", pe.getTipMessage());
		} catch (Exception e) {
			return JsonResult.getConveyResult("400", "服务器异常,请重新操作");
		}
	}

	
	
	/**
	 * 撤销送货签收单按钮
	 */
	@RequestMapping("/documents/delivery/cancel")
	@RequiresPermissions("bundleArrive:senddelivery:cancel")
	@ResponseBody
	public JsonResult canceldeliverydocuments(@RequestBody RequestJsonEntity entity) {
		User user = ContextHepler.getCurrentUser();
		logger.debug("用户操作行为日志:操作->撤销提货单;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());

		entity.put("company", user.getCompany());
		entity.put("account", user.getAccount());
		entity.put("username", user.getUserName());
		try {
			deliveryOperateService.canceldeliverydocuments(entity);
			return JsonResult.getConveyResult("200", "撤销成功");
		} catch (BusinessException be) {
			return JsonResult.getConveyResult("400", be.getTipMessage());
		} catch (ParameterException pe) {
			return JsonResult.getConveyResult("400", pe.getTipMessage());
		} catch (Exception e) {
			return JsonResult.getConveyResult("400", "服务器异常,请重新操作");
		}
	}

	
	
	/**
	 * 撤销提货签收单按钮
	 */
	@RequestMapping("/bill/delivery/cancel")
	@RequiresPermissions("bundleArrive:pickdelivery:cancel")
	@ResponseBody
	public JsonResult cancelBillOfdelivery(@RequestBody RequestJsonEntity entity) {
		try {
			User user = ContextHepler.getCurrentUser();
			entity.put("company", user.getCompany());
			entity.put("account", user.getAccount());
			entity.put("username", user.getUserName());
			deliveryOperateService.cancelBillOfdelivery(entity);
			return JsonResult.getConveyResult("200", "撤销成功");
		} catch (BusinessException be) {
			return JsonResult.getConveyResult("400", be.getTipMessage());
		} catch (ParameterException pe) {
			return JsonResult.getConveyResult("400", pe.getTipMessage());
		} catch (Exception e) {
			return JsonResult.getConveyResult("400", "服务器异常,请重新操作");
		}
	}

	
	/**
	 * 生成提货签收单按钮-到货装载清单页面
	 */
	@RequestMapping("/bill/delivery")
	@RequiresPermissions("bundleArrive:pickdelivery:create")
	@ResponseBody
	public JsonResult billOfdelivery(@RequestBody List<String> xuhaos) {
		User user = ContextHepler.getCurrentUser();
		try {
			return deliveryOperateService.getBillOfdeliveryByYdbhid(xuhaos,user.getAccount(), user.getUserName(), user.getCompany());
		} catch (ParameterException e) {
			return JsonResult.getConveyResult("400", e.getTipMessage());
		} catch (Exception e) {
			return JsonResult.getConveyResult("400", "操作异常,请重试");
		}
	}
	
	/**
	 * 生成送货单按钮-到货装载清单页面
	 */
	@RequestMapping("/documents/delivery")
	@RequiresPermissions("bundleArrive:senddelivery:create")
	@ResponseBody
	public JsonResult deliverydocuments(@RequestBody List<String> xuhaos) {
		User user = ContextHepler.getCurrentUser();
		try {
			return deliveryOperateService.deliverydocuments(xuhaos, user.getAccount(), user.getCompany());
		} catch (BusinessException be) {
			return JsonResult.getConveyResult("400", be.getTipMessage());
		} catch (ParameterException pe) {
			return JsonResult.getConveyResult("400", pe.getTipMessage());
		} catch (Exception e) {
			return JsonResult.getConveyResult("400", "服务器异常,请重新操作");
		}
	}
}
