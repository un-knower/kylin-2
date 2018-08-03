package com.ycgwl.kylin.transport.entity.adjunct;

import com.ycgwl.kylin.entity.BaseEntity;
/**
  * @Description: 仓库实体
  * @author <a href="mailto:108252@ycgwl.com">万玉杰</a>
  * @date 2017年10月25日 上午9:56:07
  * @version 需求对应版本号
  *
 */
public class StoreHouse extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	/** 编号 */
	private Integer id;
	/** 公司 */
	private String gs;
	/**  */
	private String yyb;
	/** 仓库名称 */
	private String ckmc;
	/** 仓库编号 */
	private String ckbh;
	/** 备注 */
	private String beizhu;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGs() {
		return gs;
	}
	public void setGs(String gs) {
		this.gs = gs;
	}
	public String getYyb() {
		return yyb;
	}
	public void setYyb(String yyb) {
		this.yyb = yyb;
	}
	public String getCkmc() {
		return ckmc;
	}
	public void setCkmc(String ckmc) {
		this.ckmc = ckmc;
	}
	public String getCkbh() {
		return ckbh;
	}
	public void setCkbh(String ckbh) {
		this.ckbh = ckbh;
	}
	public String getBeizhu() {
		return beizhu;
	}
	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}
	

}
