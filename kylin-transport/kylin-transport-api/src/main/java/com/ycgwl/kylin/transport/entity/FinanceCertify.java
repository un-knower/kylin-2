/**
 * com.ycgwl.kylin.transport.entity.FinanceCertify.java
 *
 * @Description: 财凭实体类, 财凭数据的载体
 * @author <a href="mailto:110686@ycgwl.com">ding xuefeng</a>
 * @date 2017年5月11日 下午1:01:36
 */
package com.ycgwl.kylin.transport.entity;

import com.ycgwl.kylin.entity.BaseEntity;

import java.util.Collection;
import java.util.Optional;

/**
 * FinanceCertify
 * @Description: 财凭实体类
 * @author <a href="mailto:110686@ycgwl.com">ding xuefeng</a>
 * @date 2017年5月11日 下午1:01:36
 */
public class FinanceCertify extends BaseEntity {

    private static final long serialVersionUID = 5614047806285317153L;

    /**
     * @Description: 运单号
     * @date 2017年5月11日 下午1:06:05
     */
    private String conveyKey;

    /**
     * @Description: 财凭号
     * @date 2018年1月12日 下午2:03:29
     */
    private Long cwpzhbh;


    /**
     * @Description: 运费合计
     * @date 2018年01月10日 下午1:06:05
     */
    private Double transportTotalFee;

    /**
     * @Description: 合计费用
     * @date 2017年5月11日 下午2:28:05
     */
    private Double cost;
    /**
     * @Description: 轻货运费单价
     * @date 2017年5月11日 下午1:06:10
     */
    private Double lightprice;
    /**
     * @Description: 重货运费单价
     * @date 2017年5月11日 下午1:06:13
     */
    private Double weightprice;
    /**
     * @Description: 计件运费单价
     * @date 2017年5月11日 下午1:06:16
     */
    private Double piecework;
    /**
     * @Description: 送货上门费
     * @date 2017年5月11日 下午1:06:19
     */
    private Double delivery = 0.0;
    /**
     * @Description: 上门收货费
     * @date 2017年5月11日 下午1:06:38
     */
    private Double tohome = 0.0;
    /**
     * @Description: 办单费
     * @date 2017年5月11日 下午1:06:42
     */
    private Double invoice;
    /**
     * @Description: 代收款
     * @date 2017年5月11日 下午1:06:45
     */
    private Double receipt;

    /**
     * @Description: 代收款状态
     * @date 2017年11月09日 下午1:06:45
     */
    private Integer receiptStatus;

    /**
     * @Description: 其它费用
     * @date 2017年5月11日 下午1:06:48
     */
    private Double other;

    /**
     * @Description: 其它费用名称
     * @date 2017年5月11日 下午1:06:48
     */
    private String otherFeeName;

    /**
     * @Description: 货到付款金额
     * @date 2017年11月9日 下午1:06:48
     */
    private Double deliveryCash;

    /**
     * @Description: 货到付款状态
     * @date 2017年11月9日 下午1:06:48
     */
    private Integer deliveryCashStatus;

    /**
     * @Description: 发货付款金额
     * @date 2017年11月9日 下午1:06:48
     */
    private Double sendCash;

    /**
     * @Description 业务字段, 与数据库无关
     * @date 2017年5月11日 下午4:15:48
     */
    private String message;

    /**
     * @Description 出纳员姓名
     * @date 2017年8月10日上午8:59:23
     */
    private String chuna;

    /**
     * @Description 是否已收钱（款清，若到站付款默认可签收，若发站付款收钱才能签收）
     * @date 2017年10月9日 下午18:40
     */
    private Integer receiveMoneyStatus;

    /**
     * @Description: 款未付金额
     * @date 2017年11月9日 下午1:06:48
     */
    private Double receiveLackMoney;

    /**
     * @Description: 保险费率
     * @date 2017年11月28日 下午1:06:48
     */
    private Double premiumRate;

    /**
     * @Description: 保险费
     * @date 2017年11月28日 下午1:06:48
     */
    private Double premiumFee;

    /**
     * @Description: 叉车费
     * @date 2017年11月28日 下午1:06:48
     */
    private Double forkliftFee;


    /**
     * @Description: 重货装卸费单价
     * @date 2017年11月28日 下午1:06:48
     */
    private Double heavyHandlingPrice;

    /**
     * @Description: 轻货装卸费单价
     * @date 2017年11月28日 下午1:06:48
     */
    private Double lightHandlingPrice;

    /**
     * @Description: 叉车费单价
     * @date 2017年11月28日 下午1:06:48
     */
    private Double forkliftPrice;

    /**
     * @Description: 装卸费
     * @date 2017年11月28日 下午1:06:48
     */
    private Double zhuangxiefei;

    /**
     * @Description: 包装费
     * @date 2017年11月28日 下午1:06:48
     */
    private Double baozhuangfei;

    /**
     * 送货上门或上门取货费
     */
    private Double dtf;

    /**
     * 是否取整
     */
    private int jshhj = 1;

    private String sysName = "麒麟";

    /**
     * 是否有财凭
     */
    private Integer hasFinance;

    public Integer getHasFinance() {
        return hasFinance;
    }

    public void setHasFinance(Integer hasFinance) {
        this.hasFinance = hasFinance;
    }

    public int getJshhj() {
        return jshhj;
    }

    public void setJshhj(int jshhj) {
        this.jshhj = jshhj;
    }

    public Double getZhuangxiefei() {
        return zhuangxiefei;
    }

    public void setZhuangxiefei(Double zhuangxiefei) {
        this.zhuangxiefei = zhuangxiefei;
    }

    public Double getBaozhuangfei() {
        return baozhuangfei;
    }

    public void setBaozhuangfei(Double baozhuangfei) {
        this.baozhuangfei = baozhuangfei;
    }

    public Double getPremiumFee() {
        return premiumFee;
    }

    public void setPremiumFee(Double premiumFee) {
        this.premiumFee = premiumFee;
    }

    public Double getForkliftFee() {
        return forkliftFee;
    }

    public void setForkliftFee(Double forkliftFee) {
        this.forkliftFee = forkliftFee;
    }

    public Double getTransportTotalFee() {
        return transportTotalFee;
    }

    public void setTransportTotalFee(Double transportTotalFee) {
        this.transportTotalFee = transportTotalFee;
    }

    public Double getDtf() {
        return dtf;
    }

    public void setDtf(Double dtf) {
        this.dtf = dtf;
    }

    public Double getHeavyHandlingPrice() {
        return heavyHandlingPrice;
    }

    public void setHeavyHandlingPrice(Double heavyHandlingPrice) {
        this.heavyHandlingPrice = heavyHandlingPrice;
    }

    public Double getLightHandlingPrice() {
        return lightHandlingPrice;
    }

    public void setLightHandlingPrice(Double lightHandlingPrice) {
        this.lightHandlingPrice = lightHandlingPrice;
    }

    public Double getForkliftPrice() {
        return forkliftPrice;
    }

    public void setForkliftPrice(Double forkliftPrice) {
        this.forkliftPrice = forkliftPrice;
    }

    public Double getPremiumRate() {
        return premiumRate;
    }

    public void setPremiumRate(Double premiumRate) {
        this.premiumRate = premiumRate;
    }

    public Integer getReceiptStatus() {
        return receiptStatus;
    }

    public void setReceiptStatus(Integer receiptStatus) {
        this.receiptStatus = receiptStatus;
    }

    public Double getReceiveLackMoney() {
        return receiveLackMoney;
    }

    public void setReceiveLackMoney(Double receiveLackMoney) {
        this.receiveLackMoney = receiveLackMoney;
    }

    public Integer getDeliveryCashStatus() {
        return deliveryCashStatus;
    }

    public void setDeliveryCashStatus(Integer deliveryCashStatus) {
        this.deliveryCashStatus = deliveryCashStatus;
    }

    public Double getDeliveryCash() {
        return deliveryCash == null ? 0.00 : deliveryCash;
    }

    public void setDeliveryCash(Double deliveryCash) {
        this.deliveryCash = deliveryCash;
    }

    public Double getSendCash() {
        return sendCash;
    }

    public void setSendCash(Double sendCash) {
        this.sendCash = sendCash;
    }

    public Integer getReceiveMoneyStatus() {
        return receiveMoneyStatus;
    }

    public void setReceiveMoneyStatus(Integer receiveMoneyStatus) {
        this.receiveMoneyStatus = receiveMoneyStatus;
    }

    /**
     * getter method
     * @return the conveyKey
     */
    public String getConveyKey() {
        return conveyKey;
    }

    /**
     * setter method
     * <pre>
     * setter method
     *  </pre>
     * @param conveyKey the conveyKey to set
     */
    public void setConveyKey(String conveyKey) {
        this.conveyKey = conveyKey;
    }

    /**
     * getter method
     * @return the cost
     */
    public Double getCost() {
        return cost;
    }

    /**
     * setter method
     * @param cost the cost to set
     */
    public void setCost(Double cost) {
        this.cost = cost;
    }

    /**
     * getter method
     * @return the lightprice
     */
    public Double getLightprice() {
        return lightprice;
    }

    /**
     * setter method
     * @param lightprice the lightprice to set
     */
    public void setLightprice(Double lightprice) {
        this.lightprice = lightprice;
    }

    /**
     * getter method
     * @return the weightprice
     */
    public Double getWeightprice() {
        return weightprice;
    }

    /**
     * setter method
     * @param weightprice the weightprice to set
     */
    public void setWeightprice(Double weightprice) {
        this.weightprice = weightprice;
    }

    /**
     * getter method
     * @return the piecework
     */
    public Double getPiecework() {
        return piecework;
    }

    /**
     * setter method
     * @param piecework the piecework to set
     */
    public void setPiecework(Double piecework) {
        this.piecework = piecework;
    }

    /**
     * getter method
     * @return the delivery
     */
    public Double getDelivery() {
        return delivery;
    }

    /**
     * setter method
     * @param delivery the delivery to set
     */
    public void setDelivery(Double delivery) {
        this.delivery = delivery;
    }

    /**
     * getter method
     * @return the tohome
     */
    public Double getTohome() {
        return tohome;
    }

    /**
     * setter method
     * @param tohome the tohome to set
     */
    public void setTohome(Double tohome) {
        this.tohome = tohome;
    }

    /**
     * getter method
     * @return the invoice
     */
    public Double getInvoice() {
        return invoice;
    }

    /**
     * setter method
     * @param invoice the invoice to set
     */
    public void setInvoice(Double invoice) {
        this.invoice = invoice;
    }

    /**
     * getter method
     * @return the receipt
     */
    public Double getReceipt() {
        return receipt == null ? 0.0 : receipt;
    }

    /**
     * setter method
     * @param receipt the receipt to set
     */
    public void setReceipt(Double receipt) {
        this.receipt = receipt;
    }

    /**
     * getter method
     * @return the other
     */
    public Double getOther() {
        return other;
    }

    /**
     * setter method
     * @param other the other to set
     */
    public void setOther(Double other) {
        this.other = other;
    }

    /**
     * getter method
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * setter method
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public String getChuna() {
        return chuna;
    }

    public void setChuna(String chuna) {
        this.chuna = chuna;
    }

    public Long getCwpzhbh() {
        return cwpzhbh;
    }

    public void setCwpzhbh(Long cwpzhbh) {
        this.cwpzhbh = cwpzhbh;
    }

    public String getOtherFeeName() {
        return otherFeeName;
    }

    public void setOtherFeeName(String otherFeeName) {
        this.otherFeeName = otherFeeName;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public static Builder newBuilder() {

        return new Builder();
    }

    public static class Builder {

        private FinanceCertify financeCertify;

        private FinanceStandardPrice financeStandardPrice;
        private TransportOrder transportOrder;
        private Collection<TransportOrderDetail> orderDetails;
        private String chuNa;

        public FinanceStandardPrice getFinanceStandardPrice() {
            return financeStandardPrice;
        }

        public Builder setFinanceStandardPrice(FinanceStandardPrice financeStandardPrice) {
            this.financeStandardPrice = financeStandardPrice;
            return this;
        }

        public TransportOrder getTransportOrder() {
            return transportOrder;
        }

        public Builder setTransportOrder(TransportOrder transportOrder) {
            this.transportOrder = transportOrder;
            return this;
        }

        public Collection<TransportOrderDetail> getOrderDetails() {
            return orderDetails;
        }

        public Builder setOrderDetails(Collection<TransportOrderDetail> orderDetails) {
            this.orderDetails = orderDetails;
            return this;
        }

        public String getChuNa() {
            return chuNa;
        }

        public Builder setChuNa(String chuNa) {
            this.chuNa = chuNa;
            return this;
        }

        public FinanceCertify getFinanceCertify() {
            return financeCertify;
        }

        public Builder setFinanceCertify(FinanceCertify financeCertify) {
            this.financeCertify = financeCertify;
            return this;
        }

        public FinanceCertify build(){
            FinanceCertify financeCertify=getFinanceCertify();
            if(financeCertify==null){
                financeCertify=new FinanceCertify();
            }

            initDefaultValue(financeCertify);

            TransportOrder transportOrder=getTransportOrder();

            // 站到仓=2，站到站=3 ，上门收货费=0
            if (transportOrder.getFwfs() == 2 || transportOrder.getFwfs() == 3) {
                financeCertify.setTohome(0.00);
            }
            // 仓到站=0，站到站=3， 送货上门费=0
            if (transportOrder.getFwfs() == 0 || transportOrder.getFwfs() == 3) {
                financeCertify.setDelivery(0.00);
            }
            if (transportOrder.getFkfsh() == 0) {
                financeCertify.setDtf(financeCertify.getDelivery() + financeCertify.getTohome());
            } else {
                financeCertify.setDtf(financeCertify.getTohome());
                financeCertify.setCost(financeCertify.getCost() - financeCertify.getDelivery());
            }
            financeCertify.setConveyKey(transportOrder.getYdbhid());

            if (transportOrder.getJshhj() == 0) {
                financeCertify.setCost(Math.round(financeCertify.getCost()) * 1.0);
            }


            //使用对象中的公式获取合计金额
            FinanceReceiveMoneyPrint print = new FinanceReceiveMoneyPrint();

            print.setBaolu(financeCertify.getPremiumRate());//保率
            print.setJshhj("取整".equals(financeCertify.getJshhj())?0:1);//是否取整
            print.setZhhyj(financeCertify.getWeightprice());//重货运价
            print.setQhyj(financeCertify.getLightprice());//轻货运价
            print.setAjyj(financeCertify.getPiecework());//按件运价
            print.setBdf(financeCertify.getInvoice());//办单费
            print.setDsk(financeCertify.getReceipt());//代收款
            print.setQtfy(financeCertify.getOther());//其他费用
            print.setShshmf(financeCertify.getDelivery());//送货上门费
            print.setShmshhf(financeCertify.getTohome());//上门取货费
            print.setZhjxzyf(financeCertify.getForkliftPrice());//叉车费单价
            print.setZhzhxfdj(financeCertify.getHeavyHandlingPrice());//重装卸费单价
            print.setQhzhxfdj(financeCertify.getLightHandlingPrice());//轻装卸费单价

             Collection<TransportOrderDetail> orderDetails=getOrderDetails();
            //
            double baoJiaJine=0;
            double transCost=0;
            double lightLoadingFee=0;
            double heavyLoadingFee=0;
            double forklift=0;

            for(TransportOrderDetail detail:orderDetails){

                baoJiaJine+=Optional.ofNullable(detail.getTbje()).orElse(0.0d).doubleValue();

                print.setJianshu(detail.getJianshu());//件数
                print.setTiji(detail.getTiji());//体积
                print.setZhl(detail.getZhl());//重量
                print.setJffs("重货".equals(detail.getJffs())?0:("轻货".equals(detail.getJffs())?1:("按件".equals(detail.getJffs())?2:0)));//计费方式
                print.setQchl(detail.getQchl());
                print.setZchl(detail.getZchl());
                print.setQzxl(detail.getQzxl());
                print.setZzxl(detail.getZzxl());

                transCost+=print.calcTotalTransport();
                lightLoadingFee+=print.calcLightLoadingFee();
                heavyLoadingFee+=print.calcHeavyLoadingFee();
                forklift+=print.calcForkliftCharge();
            }

            print.setTbje(baoJiaJine);//投保金额
            Double insuranceExpense = print.calcInsuranceExpense();
            Double totalCost = (print.getDsk()==null?0:print.getDsk()) + print.calcSumTransportFees(forklift,lightLoadingFee,insuranceExpense,heavyLoadingFee,transCost);
            financeCertify.setCost(totalCost);//合计费用

            String chuNa=getChuNa();
            // 到站付款需设置 还是发站付
            if (financeCertify.getReceipt() == 0) {// 如果代收款=0
                // 到站付款
                if ((transportOrder.getDzfk() != null && transportOrder.getDzfk() == 1)
                        && (transportOrder.getFzfk() == null || transportOrder.getFzfk() == 0)) {// 只勾选到付
                    financeCertify.setDeliveryCash(financeCertify.getCost());// 货到付款
                    financeCertify.setDeliveryCashStatus(1);// 货到付款状态
                    financeCertify.setReceiveLackMoney(0.00);
                    financeCertify.setReceiveMoneyStatus(0);// 是否款清
                    financeCertify.setChuna(chuNa);
                } else {// 匹配只勾选了发付 或者 发付到付都勾选了
                    // 发站付款
                    financeCertify.setDeliveryCash(0.00);// 货到付款
                    financeCertify.setDeliveryCashStatus(0);// 货到付款状态
                    financeCertify.setReceiveLackMoney(financeCertify.getCost());
                    financeCertify.setReceiveMoneyStatus(1);// 是否款清
                    financeCertify.setChuna("");
                }
                financeCertify.setReceiptStatus(0);
            } else {
                if ((transportOrder.getDzfk() != null && transportOrder.getDzfk() == 1)
                        && (transportOrder.getFzfk() == null || transportOrder.getFzfk() == 0)) {
                    financeCertify.setDeliveryCash(financeCertify.getCost() - financeCertify.getReceipt());// 合计金额-代收款
                    financeCertify.setDeliveryCashStatus(1);// 货到付款状态
                    financeCertify.setReceiveLackMoney(0.00);
                    financeCertify.setReceiveMoneyStatus(0);// 是否款清
                    financeCertify.setChuna(chuNa);
                } else {
                    financeCertify.setDeliveryCash(0.00);// 货到付款
                    financeCertify.setDeliveryCashStatus(0);// 货到付款状态
                    financeCertify.setReceiveLackMoney(financeCertify.getCost() - financeCertify.getReceipt());// 合计金额-代收款
                    financeCertify.setReceiveMoneyStatus(1);// 是否款清
                    financeCertify.setChuna("");
                }
                financeCertify.setReceiptStatus(1);// 代收款状态
            }

            FinanceStandardPrice financeStandardPrice=getFinanceStandardPrice();
            // 持久化财凭
            if (financeStandardPrice != null) {
                financeCertify.setPremiumRate(financeStandardPrice.getPremiumRate());
                financeCertify.setLightHandlingPrice(financeStandardPrice.getQhzhxfdj());
                financeCertify.setHeavyHandlingPrice(financeStandardPrice.getZhzhxfdj());
                financeCertify.setForkliftPrice(financeStandardPrice.getZhjxzyf());
            }
            financeCertify.setPremiumFee(transportOrder.getBaoxianfei());// 保险费
            financeCertify.setReceiveLackMoney(
                    financeCertify.getCost() - financeCertify.getReceipt() - financeCertify.getDeliveryCash());
            return financeCertify;
        }

        private void initDefaultValue(FinanceCertify financeCertify) {
            if (financeCertify.getCost() == null) {
                financeCertify.setCost(0.00);
            }
            if (financeCertify.getLightprice() == null) {
                financeCertify.setLightprice(0.00);
            }
            if (financeCertify.getWeightprice() == null) {
                financeCertify.setWeightprice(0.00);
            }
            if (financeCertify.getPiecework() == null) {
                financeCertify.setPiecework(0.00);
            }
            if (financeCertify.getDelivery() == null) {
                financeCertify.setDelivery(0.00);
            }
            if (financeCertify.getTohome() == null) {
                financeCertify.setTohome(0.00);
            }
            if (financeCertify.getInvoice() == null) {
                financeCertify.setInvoice(0.00);
            }
            if (financeCertify.getReceipt() == null) {
                financeCertify.setReceipt(0.00);
            }
            if (financeCertify.getOther() == null) {
                financeCertify.setOther(0.00);
            }
        }
    }

}
