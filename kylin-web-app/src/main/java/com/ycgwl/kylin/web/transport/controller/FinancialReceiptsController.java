package com.ycgwl.kylin.web.transport.controller;

import com.alibaba.fastjson.JSON;
import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.security.client.ContextHepler;
import com.ycgwl.kylin.security.entity.User;
import com.ycgwl.kylin.transport.entity.FinancialReceiptsQueryEntity;
import com.ycgwl.kylin.transport.entity.FinancialReceiptsResult;
import com.ycgwl.kylin.transport.entity.OffsetFinancialReceiptsQueryEntity;
import com.ycgwl.kylin.transport.service.api.IFinancialReceiptsService;
import com.ycgwl.kylin.web.transport.BaseController;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 财务收据控制层
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月5日
 */
@Controller
@RequestMapping("/transport/financialReceipts")
public class FinancialReceiptsController extends BaseController {
	
	@Autowired
    IFinancialReceiptsService financialReceiptsService;
	
	/**
	 * 跳转至财凭收钱页面
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月20日
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/collectMoney")
	public String toCollectMoney(HttpServletRequest request, Model model) {
		String ydbhid = request.getParameter("ydbhid");
		String ydzh = request.getParameter("ydzh");
		String ydxzh = request.getParameter("ydxzh");
		String xuhao = request.getParameter("xuhao");
		String sessionYdbhid = (String) request.getSession().getAttribute("ydbhid");
		if(ydbhid!=null && ydzh==null && ydxzh==null && xuhao==null) {	//返回使用
			if(sessionYdbhid != null && sessionYdbhid.equals(ydbhid)) {
				request.setAttribute("ydbhid", request.getSession().getAttribute("ydbhid"));
				request.setAttribute("ydzh", request.getSession().getAttribute("ydzh"));
				request.setAttribute("ydxzh", request.getSession().getAttribute("ydxzh"));
				request.setAttribute("hdfk", request.getSession().getAttribute("hdfk"));
				request.setAttribute("dsk", request.getSession().getAttribute("dsk"));
				request.setAttribute("xuhao", request.getSession().getAttribute("xuhao"));
			}
		}else {
			request.getSession().setAttribute("ydbhid", request.getParameter("ydbhid"));
			request.getSession().setAttribute("ydzh", request.getParameter("ydzh"));
			request.getSession().setAttribute("ydxzh", request.getParameter("ydxzh"));
			request.getSession().setAttribute("hdfk", request.getParameter("hdfk"));
			request.getSession().setAttribute("dsk", request.getParameter("dsk"));
			request.getSession().setAttribute("xuhao", request.getParameter("xuhao"));
		}
		return "transport/financialreceipts/collectmoneyreceipts";
	}

	/**
	 * 判断是否生成分理收据
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年2月9日
	 * @param financialReceiptsQueryEntity
	 * @return
	 */
	@Deprecated
	@ResponseBody
	@RequestMapping(value = "/collectMoney/isGenerate")
	public JsonResult isGenerateFinancialReceipts(@RequestBody FinancialReceiptsQueryEntity financialReceiptsQueryEntity) {
		JsonResult jsonResult = new JsonResult();
		
		User user = new User();
		try {
			//获取用户信息
			user = ContextHepler.getCurrentUser();
			logger.debug("用户操作行为日志:操作->查询分理收据;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		} catch (Exception e) {
			logger.debug("FinancialReceiptsController toCollectMoney(param) param:" + JSON.toJSONString(financialReceiptsQueryEntity) + " 报错：" + e.getMessage());
			logger.debug("用户操作行为日志:操作->查询分理收据");
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "登录已过期，请重新登录！");
		}
		
		try {
			FinancialReceiptsResult result = financialReceiptsService.isGenerateFinancialReceipts(financialReceiptsQueryEntity);
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "查询成功");
			jsonResult.put("resultInfo", result);
			logger.debug("FinancialReceiptsController isGenerateFinancialReceipts(param) param:" + JSON.toJSONString(financialReceiptsQueryEntity));
		} catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("FinancialReceiptsController isGenerateFinancialReceipts(param) param:" + JSON.toJSONString(financialReceiptsQueryEntity) +" 报错：" + e.getCause());
		}catch(BusinessException e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("FinancialReceiptsController isGenerateFinancialReceipts(param) param:" + JSON.toJSONString(financialReceiptsQueryEntity) +" 报错：" + e.getCause());
		}	
		return jsonResult;		
	}
	
	/**
	 *  分理收钱查询按钮（到货装载清单页面跳转到分理收钱的按钮）
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月5日
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/collectMoney/search")
	@RequiresPermissions("bundleArrive:collectMoney:search")
	public JsonResult collectMoneySearch(@RequestBody FinancialReceiptsQueryEntity financialReceiptsQueryEntity) {
		JsonResult jsonResult = new JsonResult();
		User user = new User();
		
		try {
			//获取用户信息
			user = ContextHepler.getCurrentUser();
			logger.debug("用户操作行为日志:操作->查询分理收据;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		} catch (Exception e) {
			logger.debug("FinancialReceiptsController toCollectMoney(param) param:" + JSON.toJSONString(financialReceiptsQueryEntity) + " 报错：" + e.getMessage());
			logger.debug("用户操作行为日志:操作->查询分理收据");
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "登录已过期，请重新登录！");
		}
		
		financialReceiptsQueryEntity.setAccount(user.getAccount());
		financialReceiptsQueryEntity.setUserName(user.getUserName());
		financialReceiptsQueryEntity.setPassword(user.getPassword());
		financialReceiptsQueryEntity.setCreateTime(user.getCreateTime());
		financialReceiptsQueryEntity.setEnable(user.getEnable());
		financialReceiptsQueryEntity.setCompany(user.getCompany());
		financialReceiptsQueryEntity.setCompanyCode(user.getCompanyCode());
		financialReceiptsQueryEntity.setSubCompany(user.getSubCompany());
		
		try {
			FinancialReceiptsResult result = financialReceiptsService.financialReceiptsInformation(financialReceiptsQueryEntity);
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "查询成功");
			jsonResult.put("resultInfo", result);
			logger.debug("FinancialReceiptsController toCollectMoney(param) param:" + JSON.toJSONString(financialReceiptsQueryEntity));
		} catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("FinancialReceiptsController toCollectMoney(param) param:" + JSON.toJSONString(financialReceiptsQueryEntity) +" 报错：" + e.getCause());
		}catch(BusinessException e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("FinancialReceiptsController toCollectMoney(param) param:" + JSON.toJSONString(financialReceiptsQueryEntity) +" 报错：" + e.getCause());
		}	
		return jsonResult;		
	}
	
	/**
	 * 跳转至财凭交钱页面
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月5日
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/payMoney")
	public String toPayMoney(HttpServletRequest request, Model model) {
		return "transport/financialreceipts/paymoneyreceipts";
	}
	
	
	/**
	 * 分理收据交钱查询（跳转到分理收据交钱页面的按钮）
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月20日
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/payMoney/search")
	@RequiresPermissions("bundleArrive:payMoney:search")
	public JsonResult payMoneySearch(@RequestBody FinancialReceiptsQueryEntity financialReceiptsQueryEntity) {
		JsonResult jsonResult = new JsonResult();
		User user = new User();
		
		try {
			//获取用户信息
			user = ContextHepler.getCurrentUser();
			logger.debug("用户操作行为日志:操作->保存分理收据;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		} catch (Exception e) {
			logger.debug("FinancialReceiptsController search(param) param:" + JSON.toJSONString(financialReceiptsQueryEntity) + " 报错：" + e.getMessage());
			logger.debug("用户操作行为日志:操作->保存分理收据");
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "登录已过期，请重新登录！");
		}
		
		financialReceiptsQueryEntity.setAccount(user.getAccount());
		financialReceiptsQueryEntity.setUserName(user.getUserName());
		financialReceiptsQueryEntity.setPassword(user.getPassword());
		financialReceiptsQueryEntity.setCreateTime(user.getCreateTime());
		financialReceiptsQueryEntity.setEnable(user.getEnable());
		financialReceiptsQueryEntity.setCompany(user.getCompany());
		financialReceiptsQueryEntity.setCompanyCode(user.getCompanyCode());
		financialReceiptsQueryEntity.setSubCompany(user.getSubCompany());
		
		try {
			FinancialReceiptsResult result = financialReceiptsService.financialReceiptsPayMoney(financialReceiptsQueryEntity);
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "查询成功");
			jsonResult.put("resultInfo", result);
			logger.debug("FinancialReceiptsController search(param) param:" + JSON.toJSONString(financialReceiptsQueryEntity));
		} catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("FinancialReceiptsController search(param) param:" + JSON.toJSONString(financialReceiptsQueryEntity) +" 报错：" + e.getCause());
		}catch(BusinessException e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("FinancialReceiptsController search(param) param:" + JSON.toJSONString(financialReceiptsQueryEntity) +" 报错：" + e.getCause());
		}	
		return jsonResult;	
	}
	
	/**
	 * 保存分理收据
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月20日
	 * @param financialReceiptsResult
	 * @return
	 */
	@RequestMapping(value = "/saveFinancialReceipts")
	@RequiresPermissions("bundleArrive:payMoney:save")
	@ResponseBody
	public JsonResult generateFinancialReceipts(@RequestBody FinancialReceiptsResult financialReceiptsResult){
		JsonResult jsonResult = new JsonResult();
		User user = new User();
		
		try {
			//获取用户信息
			user = ContextHepler.getCurrentUser();
			logger.debug("用户操作行为日志:操作->保存分理收据;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		} catch (Exception e) {
			logger.debug("FinancialReceiptsController generateFinancialReceipts(param) param:" + JSON.toJSONString(financialReceiptsResult) + " 报错：" + e.getMessage());
			logger.debug("用户操作行为日志:操作->保存分理收据");
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "登录已过期，请重新登录！");
		}
		
		FinancialReceiptsQueryEntity financialReceiptsQueryEntity = new FinancialReceiptsQueryEntity();
		financialReceiptsQueryEntity.setAccount(user.getAccount());
		financialReceiptsQueryEntity.setUserName(user.getUserName());
		financialReceiptsQueryEntity.setPassword(user.getPassword());
		financialReceiptsQueryEntity.setCreateTime(user.getCreateTime());
		financialReceiptsQueryEntity.setEnable(user.getEnable());
		financialReceiptsQueryEntity.setCompany(user.getCompany());
		financialReceiptsQueryEntity.setCompanyCode(user.getCompanyCode());
		financialReceiptsQueryEntity.setSubCompany(user.getSubCompany());
		
		try {
			financialReceiptsService.saveFinancialReceipts(financialReceiptsResult.getFinancialReceiptsMaster(),financialReceiptsResult.getFinancialReceiptsDetailList(),financialReceiptsQueryEntity);
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "保存成功");
			jsonResult.put("resultInfo", financialReceiptsResult);
			logger.debug("FinancialReceiptsController generateFinancialReceipts(param) param:" + JSON.toJSONString(financialReceiptsResult));
		} catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("FinancialReceiptsController generateFinancialReceipts(param) param:" + JSON.toJSONString(financialReceiptsResult) +" 报错：" + e.getCause());
		}catch(BusinessException e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("FinancialReceiptsController generateFinancialReceipts(param) param:" + JSON.toJSONString(financialReceiptsResult) +" 报错：" + e.getCause());
		}	
		return jsonResult;		
	}
	
	/**
	 * 分理收据交钱
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月20日
	 * @param financialReceiptsResult
	 * @return
	 */
	@RequestMapping(value = "/financialReceiptsPayMoney")
	@ResponseBody
	public JsonResult financialReceiptsPayMoney(@RequestBody FinancialReceiptsResult financialReceiptsResult){
		JsonResult jsonResult = new JsonResult();
		User user = new User();
		
		try {
			//获取用户信息
			user = ContextHepler.getCurrentUser();
			logger.debug("用户操作行为日志:操作->保存分理收据;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		} catch (Exception e) {
			logger.debug("FinancialReceiptsController financialReceiptsPayMoney(param) param:" + JSON.toJSONString(financialReceiptsResult) + " 报错：" + e.getMessage());
			logger.debug("用户操作行为日志:操作->保存分理收据");
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "登录已过期，请重新登录！");
		}
		
		FinancialReceiptsQueryEntity financialReceiptsQueryEntity = new FinancialReceiptsQueryEntity();
		financialReceiptsQueryEntity.setAccount(user.getAccount());
		financialReceiptsQueryEntity.setUserName(user.getUserName());
		financialReceiptsQueryEntity.setPassword(user.getPassword());
		financialReceiptsQueryEntity.setCreateTime(user.getCreateTime());
		financialReceiptsQueryEntity.setEnable(user.getEnable());
		financialReceiptsQueryEntity.setCompany(user.getCompany());
		financialReceiptsQueryEntity.setCompanyCode(user.getCompanyCode());
		financialReceiptsQueryEntity.setSubCompany(user.getSubCompany());
		
		try {
			financialReceiptsService.savefinancialReceiptsPayMoney(financialReceiptsResult.getFinancialReceiptsMaster(),financialReceiptsResult.getFinancialReceiptsDetailList(),financialReceiptsQueryEntity);
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "保存成功");
			jsonResult.put("resultInfo", financialReceiptsResult);
			logger.debug("FinancialReceiptsController financialReceiptsPayMoney(param) param:" + JSON.toJSONString(financialReceiptsResult));
		} catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("FinancialReceiptsController financialReceiptsPayMoney(param) param:" + JSON.toJSONString(financialReceiptsResult) +" 报错：" + e.getCause());
		}catch(BusinessException e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("FinancialReceiptsController financialReceiptsPayMoney(param) param:" + JSON.toJSONString(financialReceiptsResult) +" 报错：" + e.getCause());
		}	
		return jsonResult;		
	}
	
	/**
	 * 跳转至分理收据冲红界面
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月24日
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toOffsetFinancialReceipts")
	public String toOffsetFinancialReceipts(HttpServletRequest request, Model model) {
		return "transport/financialreceipts/offsetfinancialreceipts";
	}
	
	/**
	 * 分理收据冲红查询按钮(无权限)
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月24日
	 * @param financialReceiptsQueryEntity
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/offsetFinancialReceipts/searchByNoRight")
	@RequiresPermissions("undo:financeReceipt:search:noright")
	public JsonResult offsetFinancialReceiptsSearchNoRight(@RequestBody FinancialReceiptsQueryEntity financialReceiptsQueryEntity) {
		JsonResult jsonResult = new JsonResult();
		User user = new User();
		
		try {
			//获取用户信息
			user = ContextHepler.getCurrentUser();
			logger.debug("用户操作行为日志:操作->查询分理收据;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		} catch (Exception e) {
			logger.debug("FinancialReceiptsController offsetFinancialReceiptsSearch(param) param:" + JSON.toJSONString(financialReceiptsQueryEntity) + " 报错：" + e.getMessage());
			logger.debug("用户操作行为日志:操作->查询分理收据");
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "登录已过期，请重新登录！");
		}
		
		financialReceiptsQueryEntity.setAccount(user.getAccount());
		financialReceiptsQueryEntity.setUserName(user.getUserName());
		financialReceiptsQueryEntity.setPassword(user.getPassword());
		financialReceiptsQueryEntity.setCreateTime(user.getCreateTime());
		financialReceiptsQueryEntity.setEnable(user.getEnable());
		financialReceiptsQueryEntity.setCompany(user.getCompany());
		financialReceiptsQueryEntity.setCompanyCode(user.getCompanyCode());
		financialReceiptsQueryEntity.setSubCompany(user.getSubCompany());
		
		try {
			FinancialReceiptsResult result = financialReceiptsService.searchOffsetFinancialReceipts(financialReceiptsQueryEntity,0);
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "查询成功");
			jsonResult.put("resultInfo", result);
			logger.debug("FinancialReceiptsController offsetFinancialReceiptsSearch(param) param:" + JSON.toJSONString(financialReceiptsQueryEntity));
		} catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("FinancialReceiptsController offsetFinancialReceiptsSearch(param) param:" + JSON.toJSONString(financialReceiptsQueryEntity) +" 报错：" + e.getCause());
		}catch(BusinessException e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("FinancialReceiptsController offsetFinancialReceiptsSearch(param) param:" + JSON.toJSONString(financialReceiptsQueryEntity) +" 报错：" + e.getCause());
		}	
		return jsonResult;		
	}
	
	/**
	 * 分理收据冲红查询按钮（普通权限）
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月24日
	 * @param financialReceiptsQueryEntity
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/offsetFinancialReceipts/searchByCommon")
	@RequiresPermissions("undo:financeReceipt:search:common")
	public JsonResult offsetFinancialReceiptsSearch(@RequestBody FinancialReceiptsQueryEntity financialReceiptsQueryEntity) {
		JsonResult jsonResult = new JsonResult();
		User user = new User();
		
		try {
			//获取用户信息
			user = ContextHepler.getCurrentUser();
			logger.debug("用户操作行为日志:操作->查询分理收据;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		} catch (Exception e) {
			logger.debug("FinancialReceiptsController offsetFinancialReceiptsSearch(param) param:" + JSON.toJSONString(financialReceiptsQueryEntity) + " 报错：" + e.getMessage());
			logger.debug("用户操作行为日志:操作->查询分理收据");
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "登录已过期，请重新登录！");
		}
		
		financialReceiptsQueryEntity.setAccount(user.getAccount());
		financialReceiptsQueryEntity.setUserName(user.getUserName());
		financialReceiptsQueryEntity.setPassword(user.getPassword());
		financialReceiptsQueryEntity.setCreateTime(user.getCreateTime());
		financialReceiptsQueryEntity.setEnable(user.getEnable());
		financialReceiptsQueryEntity.setCompany(user.getCompany());
		financialReceiptsQueryEntity.setCompanyCode(user.getCompanyCode());
		financialReceiptsQueryEntity.setSubCompany(user.getSubCompany());
		
		try {
			FinancialReceiptsResult result = financialReceiptsService.searchOffsetFinancialReceipts(financialReceiptsQueryEntity,1);
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "查询成功");
			jsonResult.put("resultInfo", result);
			logger.debug("FinancialReceiptsController offsetFinancialReceiptsSearch(param) param:" + JSON.toJSONString(financialReceiptsQueryEntity));
		} catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("FinancialReceiptsController offsetFinancialReceiptsSearch(param) param:" + JSON.toJSONString(financialReceiptsQueryEntity) +" 报错：" + e.getCause());
		}catch(BusinessException e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("FinancialReceiptsController offsetFinancialReceiptsSearch(param) param:" + JSON.toJSONString(financialReceiptsQueryEntity) +" 报错：" + e.getCause());
		}	
		return jsonResult;		
	}
	
	
	/**
	 * 分理收据冲红查询按钮(财务负责人权限）
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月24日
	 * @param financialReceiptsQueryEntity
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/offsetFinancialReceipts/searchByManager")
	@RequiresPermissions("undo:financeReceipt:search:manager")
	public JsonResult offsetFinancialReceiptsSearchManager(@RequestBody FinancialReceiptsQueryEntity financialReceiptsQueryEntity) {
		JsonResult jsonResult = new JsonResult();
		User user = new User();
		
		try {
			//获取用户信息
			user = ContextHepler.getCurrentUser();
			logger.debug("用户操作行为日志:操作->查询分理收据;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		} catch (Exception e) {
			logger.debug("FinancialReceiptsController offsetFinancialReceiptsSearch(param) param:" + JSON.toJSONString(financialReceiptsQueryEntity) + " 报错：" + e.getMessage());
			logger.debug("用户操作行为日志:操作->查询分理收据");
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "登录已过期，请重新登录！");
		}
		
		financialReceiptsQueryEntity.setAccount(user.getAccount());
		financialReceiptsQueryEntity.setUserName(user.getUserName());
		financialReceiptsQueryEntity.setPassword(user.getPassword());
		financialReceiptsQueryEntity.setCreateTime(user.getCreateTime());
		financialReceiptsQueryEntity.setEnable(user.getEnable());
		financialReceiptsQueryEntity.setCompany(user.getCompany());
		financialReceiptsQueryEntity.setCompanyCode(user.getCompanyCode());
		financialReceiptsQueryEntity.setSubCompany(user.getSubCompany());
		
		try {
			FinancialReceiptsResult result = financialReceiptsService.searchOffsetFinancialReceipts(financialReceiptsQueryEntity,2);
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "查询成功");
			jsonResult.put("resultInfo", result);
			logger.debug("FinancialReceiptsController offsetFinancialReceiptsSearch(param) param:" + JSON.toJSONString(financialReceiptsQueryEntity));
		} catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("FinancialReceiptsController offsetFinancialReceiptsSearch(param) param:" + JSON.toJSONString(financialReceiptsQueryEntity) +" 报错：" + e.getCause());
		}catch(BusinessException e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("FinancialReceiptsController offsetFinancialReceiptsSearch(param) param:" + JSON.toJSONString(financialReceiptsQueryEntity) +" 报错：" + e.getCause());
		}	
		return jsonResult;		
	}
	
	/**
	 * 分理收据冲红
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月24日
	 * @param offsetFinancialReceiptsQueryEntity
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/offsetFinancialReceipts/offset")
	public JsonResult offsetFinancialReceipts(@RequestBody OffsetFinancialReceiptsQueryEntity offsetFinancialReceiptsQueryEntity) {
		JsonResult jsonResult = new JsonResult();
		User user = new User();
		
		try {
			//获取用户信息
			user = ContextHepler.getCurrentUser();
			logger.debug("用户操作行为日志:操作->查询分理收据;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		} catch (Exception e) {
			logger.debug("FinancialReceiptsController offsetFinancialReceiptsSearch(param) param:" + JSON.toJSONString(offsetFinancialReceiptsQueryEntity) + " 报错：" + e.getMessage());
			logger.debug("用户操作行为日志:操作->查询分理收据");
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "登录已过期，请重新登录！");
		}
		
		offsetFinancialReceiptsQueryEntity.setAccount(user.getAccount());
		offsetFinancialReceiptsQueryEntity.setUserName(user.getUserName());
		offsetFinancialReceiptsQueryEntity.setPassword(user.getPassword());
		offsetFinancialReceiptsQueryEntity.setCreateTime(user.getCreateTime());
		offsetFinancialReceiptsQueryEntity.setEnable(user.getEnable());
		offsetFinancialReceiptsQueryEntity.setCompany(user.getCompany());
		offsetFinancialReceiptsQueryEntity.setCompanyCode(user.getCompanyCode());
		offsetFinancialReceiptsQueryEntity.setSubCompany(user.getSubCompany());
		
		try {
			Integer result = financialReceiptsService.offsetFinancialReceipts(offsetFinancialReceiptsQueryEntity);
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "查询成功");
			jsonResult.put("resultInfo", result);
			logger.debug("FinancialReceiptsController offsetFinancialReceipts(param) param:" + JSON.toJSONString(offsetFinancialReceiptsQueryEntity));
		} catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("FinancialReceiptsController offsetFinancialReceipts(param) param:" + JSON.toJSONString(offsetFinancialReceiptsQueryEntity) +" 报错：" + e.getCause());
		}catch(BusinessException e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("FinancialReceiptsController offsetFinancialReceipts(param) param:" + JSON.toJSONString(offsetFinancialReceiptsQueryEntity) +" 报错：" + e.getCause());
		}	
		return jsonResult;		
	}
}
