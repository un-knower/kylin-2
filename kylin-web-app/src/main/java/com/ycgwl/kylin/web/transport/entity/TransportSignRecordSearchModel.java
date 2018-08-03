package com.ycgwl.kylin.web.transport.entity;

/**
 * 保存签收参数实体
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月7日
 */
public class TransportSignRecordSearchModel extends BatchTransportSignResultException {

	private static final long serialVersionUID = 3600242285061487614L;

	/**
	 * 签收状态(默认是0)
	 */
	private Integer qszt;
	
	private String qsr;
	
	private Double psjianshu;
	
	private Double dsjianshu;
	
	private String qsTime;
	
	/**
	 * 运单编码
	 */
	private String ydbhid;
	
	private String comm;
	
	private String qsrphone;
	
	private String lrTime;

	public Integer getQszt() {
		return qszt;
	}

	public void setQszt(Integer qszt) {
		this.qszt = qszt;
	}

	public String getQsr() {
		return qsr;
	}

	public void setQsr(String qsr) {
		this.qsr = qsr;
	}

	public Double getPsjianshu() {
		return psjianshu;
	}

	public void setPsjianshu(Double psjianshu) {
		this.psjianshu = psjianshu;
	}

	public Double getDsjianshu() {
		return dsjianshu;
	}

	public void setDsjianshu(Double dsjianshu) {
		this.dsjianshu = dsjianshu;
	}

	public String getQsTime() {
		return qsTime;
	}

	public void setQsTime(String qsTime) {
		this.qsTime = qsTime;
	}

	public String getYdbhid() {
		return ydbhid;
	}

	public void setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
	}

	public String getComm() {
		return comm;
	}

	public void setComm(String comm) {
		this.comm = comm;
	}

	public String getQsrphone() {
		return qsrphone;
	}

	public void setQsrphone(String qsrphone) {
		this.qsrphone = qsrphone;
	}

	public String getLrTime() {
		return lrTime;
	}

	public void setLrTime(String lrTime) {
		this.lrTime = lrTime;
	}
	
}
