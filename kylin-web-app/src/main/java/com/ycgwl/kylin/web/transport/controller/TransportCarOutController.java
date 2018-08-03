package com.ycgwl.kylin.web.transport.controller;

import com.alibaba.fastjson.JSON;
import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.security.client.ContextHepler;
import com.ycgwl.kylin.security.entity.User;
import com.ycgwl.kylin.transport.entity.DeliveryAccountingResult;
import com.ycgwl.kylin.transport.entity.DeliveryQueryEntity;
import com.ycgwl.kylin.transport.entity.DeliverySignResult;
import com.ycgwl.kylin.transport.entity.UserModel;
import com.ycgwl.kylin.transport.service.api.ITransportCarOutService;
import com.ycgwl.kylin.web.transport.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 送货派车控制层
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月30日
 */
@Controller
@RequestMapping("/transport/carOut")
public class TransportCarOutController extends BaseController{

	@Autowired
    ITransportCarOutService transportCarOutService;
	
	/**
	 * 跳转至送货派车页面
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月30日
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toCheckDelivery")
	public String toCheckDelivery(HttpServletRequest request, Model model) {
		return "transport/arrive/sendSign";
	}
	
	/**
	 * 送货派车信息查询
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月30日
	 * @param deliveryQueryEntity
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delivery/search")
	public JsonResult checkDeliverySearch(@RequestBody DeliveryQueryEntity deliveryQueryEntity) {
		JsonResult jsonResult = new JsonResult();
		User user = new User();
		
		try {
			//获取用户信息
			user = ContextHepler.getCurrentUser();
			logger.debug("用户操作行为日志:操作->查询分理收据;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		} catch (Exception e) {
			logger.debug("TransportCarOutController checkDeliverySearch(param) param:" + JSON.toJSONString(deliveryQueryEntity) + " 报错：" + e.getMessage());
			logger.debug("用户操作行为日志:操作->查询分理收据");
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "登录已过期，请重新登录！");
		}
		
		deliveryQueryEntity.setAccount(user.getAccount());
		deliveryQueryEntity.setUserName(user.getUserName());
		deliveryQueryEntity.setPassword(user.getPassword());
		deliveryQueryEntity.setCreateTime(user.getCreateTime());
		deliveryQueryEntity.setEnable(user.getEnable());
		deliveryQueryEntity.setCompany(user.getCompany());
		deliveryQueryEntity.setCompanyCode(user.getCompanyCode());
		deliveryQueryEntity.setSubCompany(user.getSubCompany());
		
		try {
			DeliverySignResult result = transportCarOutService.checkDelivery(deliveryQueryEntity);
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "查询成功");
			jsonResult.put("resultInfo", result);
			logger.debug("TransportCarOutController checkDeliverySearch(param) param:" + JSON.toJSONString(deliveryQueryEntity));
		} catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("TransportCarOutController checkDeliverySearch(param) param:" + JSON.toJSONString(deliveryQueryEntity) +" 报错：" + e.getCause());
		}catch(BusinessException e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("TransportCarOutController checkDeliverySearch(param) param:" + JSON.toJSONString(deliveryQueryEntity) +" 报错：" + e.getCause());
		}	
		return jsonResult;		
	}
	
	/**
	 * 保存/编辑送货派车信息
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月30日
	 * @param deliverySignResult
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delivery/save")
	public JsonResult checkDeliverySave(@RequestBody DeliverySignResult deliverySignResult) {
		JsonResult jsonResult = new JsonResult();
		User user = new User();
		DeliverySignResult result = new DeliverySignResult();
		try {
			//获取用户信息
			user = ContextHepler.getCurrentUser();
			logger.debug("用户操作行为日志:操作->查询分理收据;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		} catch (Exception e) {
			logger.debug("TransportCarOutController checkDeliverySave(param) param:" + JSON.toJSONString(deliverySignResult) + " 报错：" + e.getMessage());
			logger.debug("用户操作行为日志:操作->查询分理收据");
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "登录已过期，请重新登录！");
		}
		
		UserModel userModel = new UserModel();
		userModel.setAccount(user.getAccount());
		userModel.setUserName(user.getUserName());
		userModel.setPassword(user.getPassword());
		userModel.setCreateTime(user.getCreateTime());
		userModel.setEnable(user.getEnable());
		userModel.setCompany(user.getCompany());
		userModel.setCompanyCode(user.getCompanyCode());
		userModel.setSubCompany(user.getSubCompany());
		
		try {
			result = transportCarOutService.deliverySeconded(deliverySignResult,userModel);
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "保存成功");
			jsonResult.put("resultInfo", result);
			logger.debug("TransportCarOutController checkDeliverySave(param) param:" + JSON.toJSONString(deliverySignResult));
		} catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("TransportCarOutController checkDeliverySave(param) param:" + JSON.toJSONString(deliverySignResult) +" 报错：" + e.getCause());
		}catch(BusinessException e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("TransportCarOutController checkDeliverySave(param) param:" + JSON.toJSONString(deliverySignResult) +" 报错：" + e.getCause());
		}	
		return jsonResult;		
	}
	
	/**
	 * 跳转至送货签收单核算页面
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月31日
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toDeliveryAccounting")
	public String toDeliveryAccounting(HttpServletRequest request, Model model) {
		return "transport/arrive/sendSignSettle";
	}
	
	/**
	 * 派车签收核算查询
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年2月1日
	 * @param deliveryQueryEntity
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deliveryAccounting/search")
	public JsonResult deliveryAccountingSearch(@RequestBody DeliveryQueryEntity deliveryQueryEntity) {
		JsonResult jsonResult = new JsonResult();
		User user = new User();
		
		try {
			//获取用户信息
			user = ContextHepler.getCurrentUser();
			logger.debug("用户操作行为日志:操作->查询分理收据;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		} catch (Exception e) {
			logger.debug("TransportCarOutController deliveryAccountingSearch(param) param:" + JSON.toJSONString(deliveryQueryEntity) + " 报错：" + e.getMessage());
			logger.debug("用户操作行为日志:操作->查询分理收据");
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "登录已过期，请重新登录！");
		}
		
		deliveryQueryEntity.setAccount(user.getAccount());
		deliveryQueryEntity.setUserName(user.getUserName());
		deliveryQueryEntity.setPassword(user.getPassword());
		deliveryQueryEntity.setCreateTime(user.getCreateTime());
		deliveryQueryEntity.setEnable(user.getEnable());
		deliveryQueryEntity.setCompany(user.getCompany());
		deliveryQueryEntity.setCompanyCode(user.getCompanyCode());
		deliveryQueryEntity.setSubCompany(user.getSubCompany());
		
		try {
			DeliveryAccountingResult result = transportCarOutService.deliveryAccountingSearch(deliveryQueryEntity);
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "查询成功");
			jsonResult.put("resultInfo", result);
			logger.debug("TransportCarOutController deliveryAccountingSearch(param) param:" + JSON.toJSONString(deliveryQueryEntity));
		} catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("TransportCarOutController deliveryAccountingSearch(param) param:" + JSON.toJSONString(deliveryQueryEntity) +" 报错：" + e.getCause());
		}catch(BusinessException e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("TransportCarOutController deliveryAccountingSearch(param) param:" + JSON.toJSONString(deliveryQueryEntity) +" 报错：" + e.getCause());
		}	
		return jsonResult;		
	}
	
	
	/**
	 * 派车签收核算保存
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年2月1日
	 * @param deliveryAccountingResult
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deliveryAccounting/save")
	public JsonResult deliveryAccountingSave(@RequestBody DeliveryAccountingResult deliveryAccountingResult) {
		JsonResult jsonResult = new JsonResult();
		User user = new User();
		
		try {
			//获取用户信息
			user = ContextHepler.getCurrentUser();
			logger.debug("用户操作行为日志:操作->查询分理收据;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		} catch (Exception e) {
			logger.debug("TransportCarOutController deliveryAccountingSave(param) param:" + JSON.toJSONString(deliveryAccountingResult) + " 报错：" + e.getMessage());
			logger.debug("用户操作行为日志:操作->查询分理收据");
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "登录已过期，请重新登录！");
		}
		
		UserModel userModel = new UserModel();
		userModel.setAccount(user.getAccount());
		userModel.setUserName(user.getUserName());
		userModel.setPassword(user.getPassword());
		userModel.setCreateTime(user.getCreateTime());
		userModel.setEnable(user.getEnable());
		userModel.setCompany(user.getCompany());
		userModel.setCompanyCode(user.getCompanyCode());
		userModel.setSubCompany(user.getSubCompany());
		
		try {
			DeliveryAccountingResult result = transportCarOutService.deliveryAccountingSave(deliveryAccountingResult, userModel);
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "保存成功");
			jsonResult.put("resultInfo", result);
			logger.debug("TransportCarOutController deliveryAccountingSave(param) param:" + JSON.toJSONString(deliveryAccountingResult));
		} catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("TransportCarOutController deliveryAccountingSave(param) param:" + JSON.toJSONString(deliveryAccountingResult) +" 报错：" + e.getCause());
		}catch(BusinessException e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("TransportCarOutController deliveryAccountingSave(param) param:" + JSON.toJSONString(deliveryAccountingResult) +" 报错：" + e.getCause());
		}	
		return jsonResult;		
	}
}
