package com.ycgwl.kylin.web.transport.controller;

import com.alibaba.fastjson.JSON;
import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.entity.RequestJsonEntity;
import com.ycgwl.kylin.security.client.ContextHepler;
import com.ycgwl.kylin.security.entity.User;

import com.ycgwl.kylin.transport.entity.TransportOrderGoodExceptionResult;
import com.ycgwl.kylin.transport.service.api.ITransportOrderGoodExceptionService;
import com.ycgwl.kylin.web.transport.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/transport/goods")
public class ExceptionInputController extends BaseController {

	@Resource
	private ITransportOrderGoodExceptionService transportOrderGoodExceptionService;
	
	/**
	 * 跳转至主界面
	 * @param model
	 * @return
	 */
	@RequestMapping("/excptinput")
	public String excptinput(Model model) {
		return "transport/goods/excptinput";
	}
		
	/**
	 * 查询货物异常录入
	 * 
	 * @return
	 */
	@RequestMapping("/searchException")
	@ResponseBody
	public JsonResult searchException(@RequestBody Map<String, String> map) {
		String ydbhid = map.get("transportCode");
		TransportOrderGoodExceptionResult transportOrderGoodExceptionResult = new TransportOrderGoodExceptionResult();
		User user = new User();
		JsonResult jsonResult = new JsonResult();
		try {
			user = ContextHepler.getCurrentUser();
			logger.debug("用户操作行为日志:操作->货物异常录入查询按钮;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		} catch (Exception e) {
			logger.debug("ConveyController searchException(param) param:" + JSON.toJSONString(ydbhid) + " 报错："+ e.getMessage());
			logger.debug("用户操作行为日志:操作->货物异常录入查询");
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "登录已过期，请重新登录！");
			
		}
		
		transportOrderGoodExceptionResult.setTransportCode(ydbhid);
		transportOrderGoodExceptionResult.setUserCompany(user.getCompany());
		
		try {
			List<TransportOrderGoodExceptionResult> resultList = transportOrderGoodExceptionService.getExceptionInformation(transportOrderGoodExceptionResult);
			if(resultList.size() > 0 && resultList != null){
				for (TransportOrderGoodExceptionResult result : resultList) {
					result.setHandOver(user.getAccount());
				}
			}
			jsonResult.put("resultCode", 200);
			jsonResult.put("message", resultList);	
			jsonResult.put("reason", "查询成功");	
		} catch (Exception e) {
			logger.debug("ConveyController searchException(param) param:" + JSON.toJSONString(ydbhid) + " 报错："+ e.getMessage());
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "查询请求报错，请联系系统维护人员");
			
		}	
		return jsonResult;
	}
	
	@RequestMapping("/saveException")
	@ResponseBody
	public JsonResult saveException(@RequestBody RequestJsonEntity requestJsonEntity){
		User user = new User();
		JsonResult jsonResult = new JsonResult();
		try {
			user = ContextHepler.getCurrentUser();
			logger.debug("用户操作行为日志:操作->货物异常录入保存按钮;工号->" + user.getAccount() + ";分公司->" + user.getCompanyString());
		} catch (Exception e) {
			logger.debug("ConveyController searchException(param) param:"  + " 报错："+ e.getMessage());
			logger.debug("用户操作行为日志:操作->货物异常录入查询");
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "登录已过期，请重新登录！");	
		}
		
		try {
			requestJsonEntity.put("company", user.getCompany());
			transportOrderGoodExceptionService.saveExceptionInformation(requestJsonEntity);
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "保存成功");
			jsonResult.put("resultInfo", requestJsonEntity);	
		} catch (Exception e) {
			logger.debug("ConveyController searchException(param) param:" + JSON.toJSONString(requestJsonEntity) + " 报错："+ e.getMessage());
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "保存请求报错，请联系系统维护人员");
			jsonResult.put("resultInfo", requestJsonEntity);	
		}
		return jsonResult;	
	}
	
}
