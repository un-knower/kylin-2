package com.ycgwl.kylin.transport.entity.adjunct;

import com.ycgwl.kylin.entity.BaseEntity;

import java.util.Date;
/**
 * 等待发货通知
 * @author <a href="mailto:110686@ycgwl.com">ding xuefeng</a>
 * @createDate 2017-05-03 17:30:43
 */
public class ReleaseWaiting extends BaseEntity {

	private static final long serialVersionUID = 7755710573940773950L;
	
	private String ydbhid;
	private String ddfhbeizhu;
	private Date ddfhczshijian;// 插入默认当前系统时间
	private String ddfhczygh;// 录单人
	private Integer ddfhzt;//1为等待托运人放货指令，0为不等待
	private String fazhan;
	private String tzfhbeizhu;
	private Date tzfhczshijian;
	private String tzfhczygh;
	private Integer tzfhzt;//默认1
	private String xgrgonghao;
	private String xgrjiqi;
	/**
	 * @return the ydbhid
	 */
	public String getYdbhid() {
		return ydbhid;
	}
	/**
	 * @param ydbhid the ydbhid to set
	 */
	public ReleaseWaiting setYdbhid(String ydbhid) {
		this.ydbhid = ydbhid;
		return this;
	}
	/**
	 * @return the ddfhbeizhu
	 */
	public String getDdfhbeizhu() {
		return ddfhbeizhu;
	}
	/**
	 * @param ddfhbeizhu the ddfhbeizhu to set
	 */
	public ReleaseWaiting setDdfhbeizhu(String ddfhbeizhu) {
		this.ddfhbeizhu = ddfhbeizhu;
		return this;
	}
	/**
	 * @return the ddfhczshijian
	 */
	public Date getDdfhczshijian() {
		return ddfhczshijian;
	}
	/**
	 * @param ddfhczshijian the ddfhczshijian to set
	 */
	public ReleaseWaiting setDdfhczshijian(Date ddfhczshijian) {
		this.ddfhczshijian = ddfhczshijian;
		return this;
	}
	/**
	 * @return the ddfhczygh
	 */
	public String getDdfhczygh() {
		return ddfhczygh;
	}
	/**
	 * @param ddfhczygh the ddfhczygh to set
	 */
	public ReleaseWaiting setDdfhczygh(String ddfhczygh) {
		this.ddfhczygh = ddfhczygh;
		return this;
	}
	/**
	 * @return the ddfhzt
	 */
	public Integer getDdfhzt() {
		return ddfhzt;
	}
	/**
	 * @param ddfhzt the ddfhzt to set
	 */
	public ReleaseWaiting setDdfhzt(Integer ddfhzt) {
		this.ddfhzt = ddfhzt;
		return this;
	}
	/**
	 * @return the fazhan
	 */
	public String getFazhan() {
		return fazhan;
	}
	/**
	 * @param fazhan the fazhan to set
	 */
	public ReleaseWaiting setFazhan(String fazhan) {
		this.fazhan = fazhan;
		return this;
	}
	/**
	 * @return the tzfhbeizhu
	 */
	public String getTzfhbeizhu() {
		return tzfhbeizhu;
	}
	/**
	 * @param tzfhbeizhu the tzfhbeizhu to set
	 */
	public ReleaseWaiting setTzfhbeizhu(String tzfhbeizhu) {
		this.tzfhbeizhu = tzfhbeizhu;
		return this;
	}
	/**
	 * @return the tzfhczshijian
	 */
	public Date getTzfhczshijian() {
		return tzfhczshijian;
	}
	/**
	 * @param tzfhczshijian the tzfhczshijian to set
	 */
	public ReleaseWaiting setTzfhczshijian(Date tzfhczshijian) {
		this.tzfhczshijian = tzfhczshijian;
		return this;
	}
	/**
	 * @return the tzfhczygh
	 */
	public String getTzfhczygh() {
		return tzfhczygh;
	}
	/**
	 * @param tzfhczygh the tzfhczygh to set
	 */
	public ReleaseWaiting setTzfhczygh(String tzfhczygh) {
		this.tzfhczygh = tzfhczygh;
		return this;
	}
	/**
	 * @return the tzfhzt
	 */
	public Integer getTzfhzt() {
		return tzfhzt;
	}
	/**
	 * @param tzfhzt the tzfhzt to set
	 */
	public ReleaseWaiting setTzfhzt(Integer tzfhzt) {
		this.tzfhzt = tzfhzt;
		return this;
	}
	/**
	 * @return the xgrgonghao
	 */
	public String getXgrgonghao() {
		return xgrgonghao;
	}
	/**
	 * @param xgrgonghao the xgrgonghao to set
	 */
	public ReleaseWaiting setXgrgonghao(String xgrgonghao) {
		this.xgrgonghao = xgrgonghao;
		return this;
	}
	
	/**
	 * @return the xgrjiqi
	 */
	public String getXgrjiqi() {
		return xgrjiqi;
	}
	/**
	 * @param xgrjiqi the xgrjiqi to set
	 */
	public ReleaseWaiting setXgrjiqi(String xgrjiqi) {
		this.xgrjiqi = xgrjiqi;
		return this;
	}
}
