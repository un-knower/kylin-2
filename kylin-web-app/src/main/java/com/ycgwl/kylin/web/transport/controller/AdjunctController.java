package com.ycgwl.kylin.web.transport.controller;

import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.security.client.ContextHepler;
import com.ycgwl.kylin.security.entity.User;
import com.ycgwl.kylin.transport.entity.ChargingProjects;
import com.ycgwl.kylin.transport.entity.adjunct.Customer;
import com.ycgwl.kylin.transport.service.api.AdjunctSomethingService;
import com.ycgwl.kylin.web.transport.BaseController;
import com.ycgwl.kylin.web.transport.util.MessageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller  
@RequestMapping("/transport/adjunct")
public class AdjunctController extends BaseController {

	@Resource
	private AdjunctSomethingService adjunctSomethingService;
	/**
	 * 根据客户名称或者客户编号模糊查询客户相关信息
	 * @param something
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/customer")
	public Object customer(String something, Model model){
		if(StringUtils.isNotBlank(something)){
			try {
				String some=java.net.URLDecoder.decode(something,"utf-8");
				User user = ContextHepler.getCurrentUser();
				return adjunctSomethingService.listCustomerByCompanyName(user.getCompany() , some);
			} catch (Exception e) {
				exception(e, model);
			}
		}
		return Collections.emptyList();
	}
	/**
	 * 模糊查询到站信息//根据发站找到站
	  * @param daozhan
	  * @return
	  * @exception
	 */
	@ResponseBody
	@RequestMapping("/daozhan/{daozhan}")
	public Object daoZhan(@PathVariable String daozhan){
		try{
			daozhan =java.net.URLDecoder.decode(daozhan,"utf-8");
			return adjunctSomethingService.listDaoZhanByCompany(daozhan,ContextHepler.getCompanyName());
		}catch(Exception e){
			return 	Collections.emptyList();
		}
	}
	/**
	  * @Description: 发站网点
	 */
	@ResponseBody
	@RequestMapping("/latticePoint/{daozhan}")
	public JsonResult latticePoint(@PathVariable String daozhan){
		try {
			daozhan =java.net.URLDecoder.decode(daozhan,"utf-8");
			return adjunctSomethingService.getLatticePoint(daozhan);
		} catch (Exception e) {
			return JsonResult.getConveyResult("400", "查询异常,请重新操作");
		}
	}
	/**
	 * @Description: 根据中转站模糊查询中转网点
	 */
	@ResponseBody
	@RequestMapping("/beginplaceNet")
	public JsonResult getBeginplaceNet(){
		try {
			return adjunctSomethingService.beginplaceNet();
		} catch (Exception e) {
			return JsonResult.getConveyResult("400", "查询异常,请重新操作");
		}
	}
	
	@ResponseBody
	@RequestMapping("/pinming")
	public Object pinmingList(@RequestParam("something")String something,Model model){
		try{
			String some=java.net.URLDecoder.decode(something,"utf-8");
			List<String> tuopaipinminglist = MessageUtil.TUOPAIPINMINGLIST;
			List<String> resultList = new ArrayList<String>();
			for (String pinming : tuopaipinminglist) {
				if(pinming.indexOf(some) != -1){
					resultList.add(pinming);
				}
			}
			return resultList;
		}catch(Exception e){
			exception(e,model);
		}
		return Collections.emptyList();
	}
	@RequestMapping("/arriveNetWorkEdit")
	@ResponseBody
	public Object arriveNetWork(@RequestParam("daozhan") String daozhan,@RequestParam("arriveNetWork") String arriveNetWork, Model model){
		if(StringUtils.isNotBlank(daozhan) && StringUtils.isNotBlank(arriveNetWork)){
			try {
				daozhan=java.net.URLDecoder.decode(daozhan,"utf-8");
				arriveNetWork=java.net.URLDecoder.decode(arriveNetWork,"utf-8");
				return adjunctSomethingService.getArriveNetWork(daozhan,arriveNetWork);
			} catch (Exception e) {
				exception(e, model);
			}
		}
		return Collections.emptyList();
	}
	
	@RequestMapping("/arriveNetWork")
	@ResponseBody
	public JsonResult arriveNetWork(@RequestParam("daozhan") String daozhan){
		try {
			daozhan=java.net.URLDecoder.decode(daozhan,"utf-8");
			return adjunctSomethingService.getArriveNetWork(daozhan);
		} catch (UnsupportedEncodingException e) {
			return JsonResult.getConveyResult("400", "查询失败");
		}
	}
	
	/**
	 * 获取收费项目
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月20日
	 * @return
	 */
	@RequestMapping("/listChargingProjects")
	@ResponseBody
	public JsonResult listChargingProjects(){
		JsonResult jsonResult = new JsonResult();
		try {
			List<ChargingProjects> result = adjunctSomethingService.listChargingProjects();
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "签收成功");
			jsonResult.put("resultInfo", result);
		} catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("AdjunctController listChargingProjects() 报错：" + e.getCause());
		}catch(BusinessException e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("AdjunctController listChargingProjects() 报错：" + e.getCause());
		}	
		return jsonResult;	
	}
	
	/**
	 * 获取客户名称和客户编码
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月20日
	 * @return
	 */
	@RequestMapping("/listCustomerByGs")
	@ResponseBody
	public JsonResult listCustomerByGs(){
		JsonResult jsonResult = new JsonResult();
		User user = new User();
		try {
			//获取用户信息
			user = ContextHepler.getCurrentUser();
			logger.debug("用户操作行为日志:操作->签收查询;工号->"+user.getAccount()+";分公司->"+user.getCompanyString());
		} catch (Exception e) {
			logger.debug("AdjunctController listCustomerByGs() 报错："+e.getMessage());
			logger.debug("用户操作行为日志:操作->签收查询");
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", "登录已过期，请重新登录！");
		}
		
		try {
			List<Customer> result = adjunctSomethingService.listCustomerByGs(user.getCompany());
			jsonResult.put("resultCode", 200);
			jsonResult.put("reason", "签收成功");
			jsonResult.put("resultInfo", result);
		} catch (ParameterException e) {
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("AdjunctController listCustomerByGs() 报错：" + e.getCause());
		}catch(BusinessException e){
			jsonResult.put("resultCode", 400);
			jsonResult.put("reason", e.getTipMessage());
			logger.debug("AdjunctController listCustomerByGs() 报错：" + e.getCause());
		}
		return jsonResult;
	}
}
