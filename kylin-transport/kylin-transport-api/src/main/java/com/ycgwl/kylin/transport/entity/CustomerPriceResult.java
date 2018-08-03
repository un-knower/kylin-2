package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.transport.entity.adjunct.CustomerPrice;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 录入客户价格登记<br> 
 * 查询客户基本信息和收费标准结果集
 * @author zdl
 * @version [V1.0, 2018年5月16日]
 */
public class CustomerPriceResult implements Serializable{
	
	private String Account;//账号

	/**
	 */
	private static final long serialVersionUID = 1L;
	
	/********客户基本信息*************/
	private CustomerResult customerResult;

	
	/*********客户价格信息************/
	private List<CustomerPrice> customerPriceList;
	

	public List<CustomerPrice> getCustomerPriceList() {
		return customerPriceList;
	}

   

	public void setCustomerPriceList(List<CustomerPrice> customerPriceList) {
		this.customerPriceList = customerPriceList;
	}



	public CustomerResult getCustomerResult() {
		return customerResult;
	}

	public void setCustomerResult(CustomerResult customerResult) {
		this.customerResult = customerResult;
	}



	public String getAccount() {
		return Account;
	}



	public void setAccount(String account) {
		Account = account;
	}
	
	
	
	/*	//客户名称
	private String khmc;
   
	//客户区域
	private String khchsh;
	
	//地址
	private String khtxdz;
	
	//联系人
	private String khlxr;
	
	//职务
	private String lxrzw;
	
	//电话
	private String khdh;
	
	//货物名称
	private String pinming;
	
	//客户编码
	private String khbm;
	
	//税号
	private String swzh;
	
	//客户类型
	private String khlxbh;*/
 

}
