package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 成本表的一些非运单信息
 * Created by hp001 on 2017/9/11.
 */
public class IncomeEntity extends BaseEntity{
	
	private static final long serialVersionUID = 2229529010557642278L;
	private String insNo;
    private Integer insXh;
    private String ydbhid;
    private Short ydxzh;

    private String pingming;
    private Integer jianshu;
    private BigDecimal tiji;
    private BigDecimal zhl;

    private String ysfs;
    private String fazhan;
    private String daozhan;
    private Date zhchrq;

    private String yshm;
    private Integer xuhao;					//序号		xuhao


    public String getYdbhid() {
        return ydbhid;
    }

    public void setYdbhid(String ydbhid) {
        this.ydbhid = ydbhid;
    }

    public Short getYdxzh() {
        return ydxzh;
    }

    public void setYdxzh(Short ydxzh) {
        this.ydxzh = ydxzh;
    }

    public String getPingming() {
        return pingming;
    }

    public void setPingming(String pingming) {
        this.pingming = pingming;
    }

    public Integer getJianshu() {
        return jianshu;
    }

    public void setJianshu(Integer jianshu) {
        this.jianshu = jianshu;
    }

    public BigDecimal getTiji() {
        return tiji;
    }

    public void setTiji(BigDecimal tiji) {
        this.tiji = tiji;
    }

    public BigDecimal getZhl() {
        return zhl;
    }

    public void setZhl(BigDecimal zhl) {
        this.zhl = zhl;
    }

    public String getYsfs() {
        return ysfs;
    }

    public void setYsfs(String ysfs) {
        this.ysfs = ysfs;
    }

    public String getFazhan() {
        return fazhan;
    }

    public void setFazhan(String fazhan) {
        this.fazhan = fazhan;
    }

    public String getDaozhan() {
        return daozhan;
    }

    public void setDaozhan(String daozhan) {
        this.daozhan = daozhan;
    }

    public String getYshm() {
        return yshm;
    }

    public void setYshm(String yshm) {
        this.yshm = yshm;
    }

    public Integer getXuhao() {
        return xuhao;
    }

    public void setXuhao(Integer xuhao) {
        this.xuhao = xuhao;
    }

    public String getInsNo() {
        return insNo;
    }

    public void setInsNo(String insNo) {
        this.insNo = insNo;
    }

    public Integer getInsXh() {
        return insXh;
    }

    public void setInsXh(Integer insXh) {
        this.insXh = insXh;
    }

    public Date getZhchrq() {
        return zhchrq;
    }

    public void setZhchrq(Date zhchrq) {
        this.zhchrq = zhchrq;
    }

}
