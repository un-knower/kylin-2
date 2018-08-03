package com.ycgwl.kylin.web.transport.controller;

import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.security.client.ContextHepler;
import com.ycgwl.kylin.security.entity.User;
import com.ycgwl.kylin.transport.dto.CustomerDto;
import com.ycgwl.kylin.transport.dto.DaoZhanDto;
import com.ycgwl.kylin.transport.dto.RecurringCustomerDto;
import com.ycgwl.kylin.transport.service.api.CustomerService;
import com.ycgwl.kylin.web.transport.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController {
	
	@Resource
	private CustomerService customerService;
	
	@RequestMapping(value = "/toArriveBasic")
	public String toArriveBasic() {
		return "transport/basicData/arriveBasicData";
	}
	
	@RequestMapping(value = "/toCycleMoneyClient")
	public String toCycleMoneyClient() {
		return "transport/basicData/cycleMoneyClient";
	}
	
	
	@RequestMapping(value = "/toMaintainClientInfo")
	public String toMaintainClientInfo() {
		return "transport/basicData/maintainClientInfo";
	}
	
	
	/**
	 * 查询公司的到站  和  没有到站的信息
	 * @return
	 */
	@RequestMapping("selectDaoZhanBaseData")
	@ResponseBody
	public JsonResult selectDaoZhanBaseData(@RequestBody DaoZhanDto daoZhanDto) {
		User user = ContextHepler.getCurrentUser();
		if (null == user) {
			return JsonResult.getConveyResult("400", "请重新登录");
		}
		return customerService.selectDaoZhanBaseData(daoZhanDto.getCompany());
	}
	
	/**
	 * 更新公司的到站信息
	 * @return
	 */
	@RequestMapping("editDaoZhanBaseData")
	@ResponseBody
	public JsonResult editDaoZhanBaseData(@RequestBody DaoZhanDto daoZhanDto) {
		User user = ContextHepler.getCurrentUser();
		return customerService.editDaoZhanBaseData(user.getAccount(), daoZhanDto.getCompany(),daoZhanDto);
	}
	
	/**
	 * 查询周期性结款客户信息
	 * @param daoZhanDto
	 * @return
	 */
	@RequestMapping("findRecurringCustomers")
	@ResponseBody
	public JsonResult findRecurringCustomers(@RequestBody CustomerDto customerDto) {
		User user = ContextHepler.getCurrentUser();
		if (null == user) {
			return JsonResult.getConveyResult("400", "请重新登录");
		}
		if (StringUtils.isEmpty(customerDto.getKhbm()) && StringUtils.isEmpty(customerDto.getKhmc())) {
			return JsonResult.getConveyResult("400", "请输入查询条件");
		}
		
		customerDto.setCompany(user.getCompany());
		return customerService.findRecurringCustomers(user.getAccount(),customerDto);
	}
	
	/**
	 * 修改周期性结款客户信息
	 * @param daoZhanDto
	 * @return
	 */
	@RequestMapping("editRecurringCustomers")
	@ResponseBody
	public JsonResult editRecurringCustomers(@RequestBody RecurringCustomerDto recurringCustomerDto) {
		User user = ContextHepler.getCurrentUser();
		if (null == user) {
			return JsonResult.getConveyResult("400", "请重新登录");
		}
		if (StringUtils.isEmpty(recurringCustomerDto.getId())) {
			return JsonResult.getConveyResult("400", "请输入ID");
		}
		
		Date contractstartdate = null;
		Date contractenddate = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (!StringUtils.isEmpty(recurringCustomerDto.getContractstartdate())) {
			try {
				contractstartdate = simpleDateFormat.parse(recurringCustomerDto.getContractstartdate());
			} catch (ParseException e) {
				return JsonResult.getConveyResult("400", "请输入正确的日期格式");
			}
		}
		
		if (!StringUtils.isEmpty(recurringCustomerDto.getContractenddate())) {
			try {
				contractenddate = simpleDateFormat.parse(recurringCustomerDto.getContractenddate());
			} catch (ParseException e) {
				return JsonResult.getConveyResult("400", "请输入正确的日期格式");
			}
		}
		
		if (!StringUtils.isEmpty(recurringCustomerDto.getContractstartdate()) 
				&& !StringUtils.isEmpty(recurringCustomerDto.getContractenddate())) {
			boolean flag = contractstartdate.before(contractenddate);
			if(!flag) {
				// 不能早
				return JsonResult.getConveyResult("400", "合同结束日期不能早于开始日期");
			}
		}
		
		
		recurringCustomerDto.setCompany(user.getCompany());
		return customerService.editRecurringCustomers(user.getAccount(),recurringCustomerDto);
	}
	
}
