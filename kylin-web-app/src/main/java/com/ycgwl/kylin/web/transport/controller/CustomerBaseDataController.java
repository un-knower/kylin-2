package com.ycgwl.kylin.web.transport.controller;


import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.security.client.ContextHepler;
import com.ycgwl.kylin.security.entity.User;
import com.ycgwl.kylin.transport.dto.CustomerInfoDto;
import com.ycgwl.kylin.transport.dto.CustomerPriceDto;
import com.ycgwl.kylin.transport.entity.CustomerPriceResult;
import com.ycgwl.kylin.transport.entity.adjunct.CustomerPrice;
import com.ycgwl.kylin.transport.service.api.CustomerPriceService;
import com.ycgwl.kylin.transport.service.api.CustomerService;
import com.ycgwl.kylin.web.transport.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 
 * 客户基础数据<br> 
 * 录入客户基本资料
 * 录入客户价格登记
 * @author zdl
 * @version [V1.0, 2018年5月14日]
 */
@Controller
@RequestMapping("/transport/customer")
public class CustomerBaseDataController extends BaseController {
    
	@Resource
	private CustomerService customerService;
	
	@Resource
	private CustomerPriceService customerPriceService;
	
	
	@RequestMapping(value = "/toClientBaseInfo")
	public String toClientBaseInfo() {
		return "transport/basicData/clientBaseInfo";
	}
	
	@RequestMapping(value = "/toClientPriceRegister")
	public String toClientPriceRegister() {
		return "transport/basicData/clientPriceRegister";
	}
	
	
	/**
	 * 
	 * 查询客户基本信息: <br>
	 * @author zdl
	 * @version [V1.0, 2018年5月15日]
	 * @return
	 */
	@RequestMapping("/customerByYwdhId")
	@ResponseBody
	public JsonResult customerByYwdhId(@RequestBody CustomerInfoDto customerInfoDto){
		JsonResult jsonResult = new JsonResult();
		User user = new User();
		try {
			//获取用户信息
			user = ContextHepler.getCurrentUser();
		 	logger.debug("用户操作行为日志:操作->客户基本信息查询;工号->"+user.getAccount()+";分公司->"+user.getCompanyString());
		} catch (Exception e) {
			logger.debug("CustomerBaseDataController customerByYwdhId() 报错："+e.getMessage());
		 	logger.debug("用户操作行为日志:操作->客户基本信息查询");
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "登录已过期，请重新登录！");
		}
		
		try {
			//设置公司
			customerInfoDto.setCompany(user.getCompany());
			//设置账号
			customerInfoDto.setAccount(user.getAccount());
			jsonResult = customerService.customerInfoByYwdhIdandGs(customerInfoDto);
			 
		} catch (Exception e) {
			logger.debug("CustomerBaseDataController customerByYwdhId() 报错：" + e.getMessage());
			return JsonResult.getConveyResult("400", "查询失败");
		} 
		
		return jsonResult;
	}

	
	
	/**
	 * 
	 * 保存客户基本信息: <br>
	 * @author zdl
	 * @version [V1.0, 2018年5月15日]
	 * @return
	 */
	@RequestMapping(value = "/saveCustomerInfo")
	@ResponseBody
	public JsonResult saveCustomerInfo(@RequestBody CustomerInfoDto customerInfoDto) {
		JsonResult jsonResult = new JsonResult();
		// 获取当前用户
		User user = ContextHepler.getCurrentUser();
		try {
			//获取用户信息
			user = ContextHepler.getCurrentUser();
		 	logger.debug("用户操作行为日志:操作->客户基本信息录入;工号->"+user.getAccount()+";分公司->"+user.getCompanyString());
		} catch (Exception e) {
		 	logger.debug("CustomerBaseDataController saveCustomerInfo() 报错："+e.getMessage());
		 	logger.debug("用户操作行为日志:操作->客户基本信息录入");
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "登录已过期，请重新登录！");
		}
		    
		    customerInfoDto.setGKH(user.getUserName());
		    customerInfoDto.setAccount(user.getAccount());
		    customerInfoDto.setCompany(user.getCompany());
	        jsonResult= customerService.saveCustomerInfo(customerInfoDto);
	        return jsonResult;
		
	}
	
	
	/**
	 * 录入价格登记
	 * 查询客户基本信息和收费标准: <br>
	 * @author zdl
	 * @version [V1.0, 2018年5月16日]
	 * @return
	 */
	@RequestMapping("/customerByKhbm")
	@ResponseBody
	public JsonResult customerByKhbm(@RequestBody CustomerPriceDto customerPriceDto){
		JsonResult jsonResult = new JsonResult();
		User user = new User();
		try {
			//获取用户信息
			user = ContextHepler.getCurrentUser();
		 	logger.debug("用户操作行为日志:操作->客户基本信息和收费标准查询;工号->"+user.getAccount()+";分公司->"+user.getCompanyString());
		} catch (Exception e) {
		 	logger.debug("CustomerBaseDataController customerByKhbm() 报错："+e.getMessage());
		 	logger.debug("用户操作行为日志:操作->客户基本信息和收费标准查询");
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "登录已过期，请重新登录！");
		}
		
		try {
			//设置账号
			customerPriceDto.setAccount(user.getAccount());
			jsonResult  = customerPriceService.customerInfoByKhBMandGs(customerPriceDto);
	
		} catch (Exception e) {
			logger.debug("CustomerBaseDataController customerByKhbm() 报错：" + e.getMessage());
			return JsonResult.getConveyResult("400", "查询失败");
		}
		return jsonResult;
	}
	
  
	
	/**
	 * 录入客户价格
	 * 保存客户价格和操作要求: <br>
	 * @author zdl
	 * @version [V1.0, 2018年5月16日]
	 * @return
	 */
	@RequestMapping(value="/batchCustomerPrice")
	@ResponseBody
	public JsonResult batchCustomerPrice(@RequestBody CustomerPriceResult customerPriceResult) {
		JsonResult jsonResult = new JsonResult();
		CustomerPrice customerPrice =new CustomerPrice();
		
		// 获取当前用户
		User user = ContextHepler.getCurrentUser();
		try {
			//获取用户信息
			user = ContextHepler.getCurrentUser();
		 	logger.debug("用户操作行为日志:操作->客户价格信息录入;工号->"+user.getAccount()+";分公司->"+user.getCompanyString());
		} catch (Exception e) {
		 	logger.debug("batchCustomerPrice batchCustomerPrice() 报错："+e.getMessage());
		 	logger.debug("用户操作行为日志:操作->客户价格信息录入");
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "登录已过期，请重新登录！");
		}
		
		//设置账号
		customerPriceResult.setAccount(user.getAccount());
		customerPriceResult.getCustomerResult().setCompany(user.getCompany());
		customerPriceResult.getCustomerPriceList().add(customerPrice);
		//保存客户价格信息
		return customerPriceService.batchCustomerPriceInfo(customerPriceResult);
	
	}
    
	
	/**
	 *
	 * 根据登录公司查询到站: <br>
	 * @author zdl
	 * @version [V1.0, 2018年5月16日]
	 * @return
	 * 
	 */
	@RequestMapping("/daozhanByCompany")
	@ResponseBody
	public JsonResult daozhanByCompany(@RequestBody CustomerPriceDto customerPriceDto){
		JsonResult jsonResult = new JsonResult();
		User user = new User();
		try {
			//获取用户信息
			user = ContextHepler.getCurrentUser();
		} catch (Exception e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "登录已过期，请重新登录！");
		}
		
		try {
			
			jsonResult  = customerPriceService.daozhanByCompany(customerPriceDto);
	
		} catch (Exception e) {
			return JsonResult.getConveyResult("400", "查询失败");
		}
		return jsonResult;
	}
	
	
	/**
	 * 删除客户价格 : <br>
	 * @author zdl
	 * @version [V1.0, 2018年5月16日]
	 * @return
	 */
	@RequestMapping("/deleteCustomerPriceInfo")
	@ResponseBody
	public JsonResult deleteCustomerPriceInfo(@RequestBody CustomerPriceDto customerPriceDto){
		JsonResult jsonResult = new JsonResult();
		User user = new User();
		try {
			//获取用户信息
			user = ContextHepler.getCurrentUser();
		} catch (Exception e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "登录已过期，请重新登录！");
		}
		
		try {
			//设置公司
			customerPriceDto.setQizhan(user.getCompany());
			jsonResult  = customerPriceService.deleteCustomerPriceInfo(customerPriceDto);
	
		} catch (Exception e) {
			return JsonResult.getConveyResult("400", "删除失败");
		}
		return jsonResult;
	}
	
	
	
	
	
	
}
