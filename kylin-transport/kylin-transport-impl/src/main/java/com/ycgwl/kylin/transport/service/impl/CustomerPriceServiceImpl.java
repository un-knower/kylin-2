package com.ycgwl.kylin.transport.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.dto.CustomerPriceDto;
import com.ycgwl.kylin.transport.entity.CustomerPriceResult;
import com.ycgwl.kylin.transport.entity.CustomerResult;
import com.ycgwl.kylin.transport.entity.adjunct.CustomerPrice;
import com.ycgwl.kylin.transport.persistent.AdjunctSomethingMapper;
import com.ycgwl.kylin.transport.persistent.CustomerPriceMapper;
import com.ycgwl.kylin.transport.service.api.CustomerPriceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * 录入客户价格登记<br> 
 * 基础数据
 * @author zdl
 * @version [V1.0, 2018年5月16日]
 */
@Service("kylin.transport.dubbo.local.customerPriceService")
public class CustomerPriceServiceImpl implements CustomerPriceService {
	
	@Resource
	private CustomerPriceMapper customerPriceMapper;
	
	@Resource
	private AdjunctSomethingMapper adjunctSomethingMapper;

	@Override
	public JsonResult customerInfoByKhBMandGs(CustomerPriceDto customerPriceDto) throws ParameterException {
        //录入时查询录入权限
		Integer rigthNum = customerPriceMapper.RigthNum(customerPriceDto.getAccount());
		if(null==rigthNum || rigthNum == 0){
			return JsonResult.getConveyResult("400", "你没有权限使用这个功能，请与系统管理员联系!");
		}
		
		JsonResult jsonResult = new JsonResult();
		//客户编码效验
		if (StringUtils.isBlank(customerPriceDto.getKhbm())) {
			return JsonResult.getConveyResult("400", "客户编码不能为空");
		}
		CustomerPriceResult result = new CustomerPriceResult();
		List<CustomerPrice> customerPriceList = new ArrayList<CustomerPrice>();
	      
		//先查询客户信息
		List<CustomerResult> customerList = adjunctSomethingMapper.queryCustomerByGsandKhbm(customerPriceDto.getQizhan(),customerPriceDto.getKhbm());
		
		if(customerList.isEmpty() || customerList.size() == 0){
			return JsonResult.getConveyResult("400", "这个编码不是本公司的");
		}
  	
		CustomerResult customerResult = customerList.get(0);
		
		//再查询客户价格信息
		customerPriceList = customerPriceMapper.queryCustomerPriceByGsandKhbm(customerPriceDto.getQizhan(),customerPriceDto.getKhbm());
		
		if(!customerPriceList.isEmpty() && customerPriceList.size() != 0){
			result.setCustomerPriceList(customerPriceList);
		}
		
		result.setCustomerResult(customerResult);
		
		jsonResult.put("resultCode", 200);
		jsonResult.put("reason", "查询成功");
		jsonResult.put("resultInfo", result);
		
		return jsonResult;
	}


	@Override
	public JsonResult batchCustomerPriceInfo(CustomerPriceResult customerPriceResult) throws ParameterException {
	   
		JsonResult jsonResult = new JsonResult();
		
        //录入时查询录入权限
		Integer rigthNum = customerPriceMapper.RigthNum(customerPriceResult.getAccount());
		if(null==rigthNum || rigthNum == 0){
			return JsonResult.getConveyResult("400", "你没有权限使用这个功能，请与系统管理员联系!");
		}
		
		
		List<CustomerPrice> PriceList =new ArrayList<CustomerPrice>();
		//1.保存客户价格信息(客户价格表)
		//a.有客户基本信息，无价格信息，保存成功
		//b.录入成功
	    if(null!=customerPriceResult){
	    	PriceList = customerPriceResult.getCustomerPriceList();
	    	//必填项效验
	    	if(!PriceList.isEmpty() && PriceList.size() != 0){
 	    	 for (int i = 0; i < PriceList.size(); i++) {
 	    		//设置客户编码,到站不能为空
 	    		PriceList.get(i).setKhbm(PriceList.get(i).getKhbm()==null? "":PriceList.get(i).getKhbm());
 	    		PriceList.get(i).setDaozhan(PriceList.get(i).getDaozhan()==null? "":PriceList.get(i).getDaozhan());  
 	    		  
 			 }	 
	    	}
 	    }
	    
	    
	    //如果点击新增
	      if(null!=customerPriceResult){
	     //先把查询到的删除
	     customerPriceMapper.removePriceInfo(customerPriceResult.getCustomerResult().getCompany(),customerPriceResult.getCustomerResult().getKhbm());
          //再批量新增
		 int count = customerPriceMapper.batchCustomerPriceInfo(PriceList);
		 
		 if (count == 0) {
			 jsonResult.put("resultCode", 400);
			 jsonResult.put("reason", "录入价格信息失败");
		 }else {
			 jsonResult.put("resultCode", 200);
			 jsonResult.put("reason", "录入价格信息成功");
		 }
	    } 
		//2.保存操作要求(客户信息表)
	     //a.有客户价格信息，没有修改和录入信息不能保存
	     //b.没有客户价格信息，没有录入价格标准，提示价格信息不能为空，不能保存
	    // c.有客户价格信息，并录入操作要求，提示操作要求保存成功
	    // d.没有价格信息，并录入操作要求，提示操作要求保存成功
		//根据客户编码和登录公司查询客户基本
	    List<CustomerResult> customerList = adjunctSomethingMapper.queryCustomerByGsandKhbm(customerPriceResult.getCustomerResult().getCompany(),customerPriceResult.getCustomerResult().getKhbm());
	    if(customerList.isEmpty() || customerList.size() == 0){
	    	  jsonResult.put("resultCode", 400);
		      jsonResult.put("reason", "没有查询到对应的操作操作要求信息");
		}else {
			 //否则，更新操作要求到客户基本信息表
			customerPriceResult.getCustomerResult().setCzsm(customerPriceResult.getCustomerResult().getCzsm() == null ? "" : customerPriceResult.getCustomerResult().getCzsm());

			 int count2 = adjunctSomethingMapper.updateCustomerInfo(customerPriceResult.getCustomerResult());
			 if (count2!=1) {
				 jsonResult.put("resultCode", 400);
				 jsonResult.put("reason", "录入操作要求失败");
			 }else {
				 jsonResult.put("msg", 200);
				 jsonResult.put("message", "录入操作要求更新成功");
			 }
		}
  
		 return jsonResult;
	}


	@Override
	public JsonResult daozhanByCompany(CustomerPriceDto customerPriceDto) throws ParameterException {
		 JsonResult jsonResult = new JsonResult();
     	 List<String> daozhanList = customerPriceMapper.daozhanByCompany(customerPriceDto);
		 jsonResult.put("daozhanList", daozhanList);
		 jsonResult.put("resultCode", 200);
		 return jsonResult;
	}


	@Override
	public JsonResult deleteCustomerPriceInfo(CustomerPriceDto customerPriceDto) throws ParameterException {
		 JsonResult jsonResult = new JsonResult();
		 int  count = customerPriceMapper.deleteCustomerPriceInfo(customerPriceDto);
		 if(count == 0){
			 jsonResult.put("resultCode", 400);
			 jsonResult.put("reason", "删除价格信息失败");
		 }else {
			 jsonResult.put("resultCode", 200);
			 jsonResult.put("reason", "删除价格信息成功");
		 }
		return jsonResult;
		 
		  
		  
	}

}
