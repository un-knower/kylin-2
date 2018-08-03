package com.ycgwl.kylin.transport.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import com.ycgwl.kylin.AuthorticationConstant;
import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.dto.CustomerDto;
import com.ycgwl.kylin.transport.dto.CustomerInfoDto;
import com.ycgwl.kylin.transport.dto.DaoZhanDto;
import com.ycgwl.kylin.transport.dto.RecurringCustomerDto;
import com.ycgwl.kylin.transport.entity.CustomerResult;
import com.ycgwl.kylin.transport.entity.adjunct.Company;
import com.ycgwl.kylin.transport.persistent.AdjunctSomethingMapper;
import com.ycgwl.kylin.transport.persistent.CustomerMapper;
import com.ycgwl.kylin.transport.persistent.TransportRightMapper;
import com.ycgwl.kylin.transport.service.api.CustomerService;
import com.ycgwl.kylin.transport.util.Constant;
import com.ycgwl.kylin.transport.vo.CustomerVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("kylin.transport.dubbo.local.customerServiceImpl")
public class CustomerServiceImpl implements CustomerService {
	
	@Resource
	private CustomerMapper customerMapper;
	
	@Resource
	private AdjunctSomethingMapper somethingMapper;
	
	@Resource
	private TransportRightMapper rightMapper;
	

	@Override
	public JsonResult selectDaoZhanBaseData(String company) {
		JSONObject json = new JSONObject();
		
		if (StringUtils.isEmpty(company)) {
			return JsonResult.getConveyResult("400", "请选择操作公司");
		}
		
		if (company.equals("总公司")) {
			// 查询所有公司
			Collection<Company> queryStationCompany = somethingMapper.queryStationCompany();
			if (CollectionUtils.isEmpty(queryStationCompany)) {
				json.put("msg", "未查询出该公司的到站");
			}
			json.put("allCompany", queryStationCompany);
		} else {
			json.put("allCompany", "");
		}
		
		// 查询到站
		List<String> list = customerMapper.selectDaoZhanBaseData(company);
		if (CollectionUtils.isEmpty(list)) {
			json.put("msg", "未查询出该公司的到站");
		}
		// 查询未到站
		List<String> list1 = customerMapper.selectNoDaoZhanBaseData(company);
		if (CollectionUtils.isEmpty(list1)) {
			json.put("msg", "未查询出该公司的未到站");
		}
		json.put("daoZhan", list);
		json.put("noDaoZhan", list1);
		return JsonResult.getConveyResult("200", "操作成功", json);
	}


	@Transactional
	@Override
	public JsonResult editDaoZhanBaseData(String account, String company, DaoZhanDto daoZhanDto) {
		
		Integer rightNum = rightMapper.getRightNum(AuthorticationConstant.BASE_DZ, account);
		if (rightNum == null || rightNum < 0) {
			return JsonResult.getConveyResult("400", "您没有此权限，请与系统管理员申请开通！");
		}
		
		if (StringUtils.isEmpty(company)) {
			return JsonResult.getConveyResult("400", "请选择操作公司");
		}
		
		// 删除到站信息
		customerMapper.removeDaoZhanBaseData(company);
		String daoZhan = daoZhanDto.getDaoZhan();
		String[] split = daoZhan.split(",");
		int length = split.length;
		Map<String, Object> map = new HashMap<>();
        map.put("daozhans", split);
        map.put("company", company);
		int count = customerMapper.saveDaoZhanBaseData(map);
		if (length == count) {
			return JsonResult.getConveyResult("200", "操作成功！");
		} else {
			return JsonResult.getConveyResult("400", "操作失败！");
		}
	}


	@Override
	public JsonResult findRecurringCustomers(String account, CustomerDto customerDto) {
		Integer rightNum = rightMapper.getRightNum(AuthorticationConstant.YSK_CUSFIND, account);
		if (rightNum == null || rightNum < 0) {
			return JsonResult.getConveyResult("400", "您没有此权限，请与系统管理员申请开通！");
		}
		CustomerVo customerVo=null;
		if(StringUtil.isEmpty(customerDto.getCompany())){
			 customerVo = customerMapper.findRecurringCustomerByKhbmAndKhmc(customerDto);
		}else {
            List<CustomerVo> customerVoList = customerMapper.findRecurringCustomers(customerDto);
            if(customerVoList.size()>1) {
                return JsonResult.getConveyResult("400", "此客户重复,请添加查询条件");
            }
		    if(customerVoList.size()==1) {
                customerVo = customerVoList.get(0);
            }
		}
		if (null == customerVo) {
			return JsonResult.getConveyResult("400", "未查询到此客户信息");
		}
		return JsonResult.getConveyResult("200", "查询成功！",customerVo);
	}


	@Override
	public JsonResult editRecurringCustomers(String account, RecurringCustomerDto recurringCustomerDto) {
		Integer rightNum = rightMapper.getRightNum(AuthorticationConstant.YSK_CUSEDIT, account);
		if (rightNum == null || rightNum < 0) {
			return JsonResult.getConveyResult("400", "您没有此权限，请与系统管理员申请开通！");
		}
		int count = customerMapper.editRecurringCustomers(recurringCustomerDto);
		if (1 == count) {
			return JsonResult.getConveyResult("200", "修改成功！");
		} else {
			return JsonResult.getConveyResult("400", "修改失败！");
		}
	}
	
	
	@Override
	public JsonResult customerInfoByYwdhIdandGs(CustomerInfoDto customerInfoDto) throws ParameterException {
		JsonResult jsonResult = new JsonResult();
		
        //录入时查询录入权限
		Integer rigthNum = somethingMapper.RigthNum(customerInfoDto);
		if(null==rigthNum || rigthNum == 0){
			return JsonResult.getConveyResult("400", "你没有权限使用这个功能，请与系统管理员联系!");
		}
		
		//验证业务接单号是否为空
		if (StringUtils.isBlank(customerInfoDto.getYwjdId())) {
			return JsonResult.getConveyResult("400", "请输入业务接单号");
		}
		
		//查询输入的业务接单号是否在所属公司
		CustomerResult result = somethingMapper.customerInfoByYwdhIdandGs(customerInfoDto.getCompany(), Integer.valueOf(customerInfoDto.getYwjdId()));
		if (null==result) {
			 return JsonResult.getConveyResult("400", "请输入正确的业务接单号");
		}
		
		jsonResult.put("resultCode", 200);
		jsonResult.put("reason", "查询成功");
		jsonResult.put("resultInfo", result);
	
		return jsonResult;
	}


	@Override
	public JsonResult saveCustomerInfo(CustomerInfoDto customerInfoDto) throws ParameterException {

        //录入时查询录入权限
		Integer rigthNum = somethingMapper.RigthNum(customerInfoDto);
		if(null==rigthNum || rigthNum == 0){
			return JsonResult.getConveyResult("400", "你没有权限使用这个功能，请与系统管理员联系!");
		}
		
		//必填项效验
		if(StringUtil.isEmpty(customerInfoDto.getCustomerName())){
 			return JsonResult.getConveyResult("400", "请输入客户名称!");
 		
		}
		
		if(StringUtil.isEmpty(customerInfoDto.getAddress())){
 			return JsonResult.getConveyResult("400", "请输入通讯地址!");
 		
		}
		
		if(StringUtil.isEmpty(customerInfoDto.getSellCode())){
 			return JsonResult.getConveyResult("400", "请输入销售代码!");
 		
		}
				
		if(StringUtil.isEmpty(customerInfoDto.getCustomerKey())){
 			return JsonResult.getConveyResult("400", "请输入客户编码!");
 		
		}
		
		if(StringUtil.isEmpty(customerInfoDto.getContactPerson())){
 			return JsonResult.getConveyResult("400", "请输入联系人!");
 		
		}
		
		if(StringUtil.isEmpty(customerInfoDto.getPhone())){
 			return JsonResult.getConveyResult("400", "请输入电话!");
 		
		}
		
		if(StringUtil.isEmpty(customerInfoDto.getClearingcode())){
 			return JsonResult.getConveyResult("400", "请输入结款方式!");
 		
		}
		
		if(StringUtil.isEmpty(customerInfoDto.getGuestType())){
 			return JsonResult.getConveyResult("400", "请输入行业类别!");
 		
		}
		
		if(StringUtil.isEmpty(customerInfoDto.getConsterType())){
 			return JsonResult.getConveyResult("400", "请输入客户类型!");
 		
		}
		
		if(StringUtil.isEmpty(customerInfoDto.getProduct())){
 			return JsonResult.getConveyResult("400", "请输入主营产品!");
 		
		}
		
		if(StringUtil.isEmpty(customerInfoDto.getSalesDeputy())){
 			return JsonResult.getConveyResult("400", "请输入销售代表!");
 		
		}
		
        //根据公司和输入的业务编码查询客户信息
		List<CustomerResult> list1 = somethingMapper.queryCustomerByGsandKhbm(customerInfoDto.getCompany(),customerInfoDto.getCustomerKey());
		if(null!=list1 && list1.size()>0){
		    //a.判断客户名称是否与客户表一样，如果一样，提示客户名称已存在
            return JsonResult.getConveyResult("400", "录入的客户编码已在系统中存在!");
		}
		
		//根据公司和输入的客户名称查询客户信息
		List<CustomerResult> list2 = somethingMapper.queryCustomerByGsandKhMC(customerInfoDto.getCompany(),customerInfoDto.getCustomerName());
        
		if(null!=list2 && list2.size()>0 ){
			//b.判断客户编码是否存在客户表，如果有，则不能录入客户资料，提示客户编码已存在
		   return JsonResult.getConveyResult("400", "录入的客户名称已在系统中存在!");
		}
		
	   // c.录入成功
		//获取customer表的最大id
		int customerId= somethingMapper.getMaxId(customerInfoDto);
		String id = Constant.getId(customerId);
		customerInfoDto.setId(id);
	 	customerInfoDto.setKhjj(customerInfoDto.getKhjj() == null ? "" : customerInfoDto.getKhjj());
		int count = somethingMapper.insertCustomerInfo(customerInfoDto);
		if (count != 1) {
			return JsonResult.getConveyResult("400", "客户基本信息录入失败");
		}else {
			return JsonResult.getConveyResult("200", "客户基本信息录入成功");
		}
	}

}
