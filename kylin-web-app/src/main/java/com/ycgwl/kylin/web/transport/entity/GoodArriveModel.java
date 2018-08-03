package com.ycgwl.kylin.web.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

/**
  * @Description: 到货请求的实体
  * @author <a href="mailto:108252@ycgwl.com">万玉杰</a>
  * @date 2017年10月26日 下午1:21:35
  * @version 需求对应版本号
  *
 */
public class GoodArriveModel extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/** 运单号 */
	private String ydbhid;
	/** 车牌号 */
	private String chxh;
	/** 发车时间 */
	private String fchrq;
	/** 查询方式  1.安装车牌号和发车日期   2.根据运单号*/
	private String type;
	public String getYdbhid() {
		return ydbhid;
	}
	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}
	public String getChxh() {
		return chxh;
	}
	public void setChxh(String chxh) {
		this.chxh = chxh;
	}
	public String getFchrq() {
		return fchrq;
	}
	public void setFchrq(String fchrq) {
		this.fchrq = fchrq;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	

}
