package com.ycgwl.kylin.transport.entity.adjunct;

import com.ycgwl.kylin.entity.BaseEntity;

/**
 * 客户行业类型
 * T_Customer_categories
 * @author <a href="mailto:110686@ycgwl.com">ding xuefeng</a>
 * @createDate 2017-05-03 17:30:43
 */
public class Industry extends BaseEntity {

	private static final long serialVersionUID = 7337626612824791184L;

	private String industryKey;//行业编号 id
	private String industryName;//行业名称  Custname
	private String industryRemark;//行业标签  remark 
	private String industryShortCode;//行业简码   custJM
	private String industrySort;// 排序  Custsort
	private String industryState;//状态 Custstate
	private String industryOldName;//前行业名称  oldname
	
	public String getIndustryKey() {
		return industryKey;
	}
	public void setIndustryKey(String industryKey) {
		this.industryKey = industryKey;
	}
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	public String getIndustryRemark() {
		return industryRemark;
	}
	public void setIndustryRemark(String industryRemark) {
		this.industryRemark = industryRemark;
	}
	public String getIndustryShortCode() {
		return industryShortCode;
	}
	public void setIndustryShortCode(String industryShortCode) {
		this.industryShortCode = industryShortCode;
	}
	public String getIndustrySort() {
		return industrySort;
	}
	public void setIndustrySort(String industrySort) {
		this.industrySort = industrySort;
	}
	public String getIndustryState() {
		return industryState;
	}
	public void setIndustryState(String industryState) {
		this.industryState = industryState;
	}
	public String getIndustryOldName() {
		return industryOldName;
	}
	public void setIndustryOldName(String industryOldName) {
		this.industryOldName = industryOldName;
	}
}
