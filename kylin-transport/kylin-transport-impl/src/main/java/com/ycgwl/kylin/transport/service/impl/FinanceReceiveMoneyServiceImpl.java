package com.ycgwl.kylin.transport.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.ycgwl.kylin.AuthorticationConstant;
import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.entity.*;
import com.ycgwl.kylin.transport.entity.adjunct.PrintData;
import com.ycgwl.kylin.transport.persistent.FinanceReceiveMoneyMapper;
import com.ycgwl.kylin.transport.persistent.TransportOrderCancelMapper;
import com.ycgwl.kylin.transport.persistent.TransportOrderMapper;
import com.ycgwl.kylin.transport.persistent.TransportRightMapper;
import com.ycgwl.kylin.transport.service.api.FinanceReceiveMoneyService;
import com.ycgwl.kylin.transport.service.api.IExceptionLogService;
import com.ycgwl.kylin.util.Assert;
import com.ycgwl.kylin.util.IPUtil;
import org.apache.commons.lang.math.NumberUtils;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;


@Service("kylin.transport.dubbo.local.financeReceiveMoneyService")
public class FinanceReceiveMoneyServiceImpl implements FinanceReceiveMoneyService {
	
	private final static String OPERATING_MENU = "财凭冲红";

	@Autowired
	private FinanceReceiveMoneyMapper financeReceiveMoneyMapper;
	
	@Resource
	TransportOrderCancelMapper transportOrderCancelMapper;
	
	@Resource
	TransportRightMapper transportRightMapper;
	
	@Resource
	IExceptionLogService exceptionLogService; 
	
	@Autowired
	TransportOrderMapper transportOrderMapper;
	
	public FinanceReceiveMoneyResult getResult(FinanceReceiveMoneyResult result,String code,String msg){
		result.setResultCode(code);
		result.setResultMsg(msg);
		return result;
	}
	
	public FinanceReceiveMoneyResult getModify(FinanceReceiveMoneyResult result,boolean canModify,String reason){
		result.setCanModify(canModify);
		result.setCanotModifyReason(reason);
		return result;
	}
	
	public Double setNullToZero(Double value){
		return value==null?0.00:value;
	}
	
	
	public FinanceReceiveMoneyResult searchBeforeReceiveMoney(FinanceReceiveMoneyForm formFkfsh) throws ParameterException, BusinessException {
		FinanceReceiveMoney sessionMoney = new FinanceReceiveMoney();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		FinanceReceiveMoneyResult result = new FinanceReceiveMoneyResult();
		// 输入框注释： sle_1=公司编码，sle_2=受理单编号，sle_4=年度，sle_3=客户余额
		//界面查询3个参数
		// 校验 3）校验财凭号是否是正常的
		FinanceTransport fiwt = null;
		if(StringUtils.isEmpty(formFkfsh.getTransportCode()) && ObjectUtils.isEmpty(formFkfsh.getWealthNo())){
			return getResult(result, "1002", "运单号和财凭号请任意输入一个！");
		}else if(!ObjectUtils.isEmpty(formFkfsh.getTransportCode())) {//运单号不为空
			fiwt  = financeReceiveMoneyMapper.findTransportFinanceByTransportCode(formFkfsh.getTransportCode(), 0);
		}else {//财务凭证号
			if (!NumberUtils.isNumber(formFkfsh.getWealthNo().toString())) {
				return getResult(result, "1002", "输入的财务凭证编号无效，不是数字");
			}
			fiwt = financeReceiveMoneyMapper.findTransportFinance(formFkfsh.getCompanyCode(), formFkfsh.getWealthNo(), formFkfsh.getYear(),0);
		}

		if(fiwt!=null){
			formFkfsh.setCompanyCode(fiwt.getXianlu());
		}
		if(fiwt==null){
			return getResult(result, "1003", "该财凭不存在");
		}
		if (fiwt.getType() != 0) {
			//返回校验处理：清空页面 上的款未付、货到付款、月结、现金收入、银行收入、代收款
			return getResult(result, "1004", "该财凭类型不是正常财凭，请重新输入");
		}
		if(!StringUtils.isEmpty(formFkfsh.getTransportCode()) && ObjectUtils.isEmpty(fiwt.getCwpzhbh())) {
			return getResult(result, "1003", "您查询的财凭号没有对应财务凭证");
		}else if(StringUtils.isEmpty(formFkfsh.getTransportCode()) && ObjectUtils.isEmpty(fiwt.getCwpzhbh())) {
			return getResult(result, "1003", "您查询的运单没有生成财务凭证");
		}
		result.setCompanyCode(fiwt.getXianlu());
		result.setYear(formFkfsh.getYear());
		result.setCanModify(false);
		result.setWealthNo(fiwt.getCwpzhbh());
		formFkfsh.setWealthNo(fiwt.getCwpzhbh());
		List <FinanceReceiveMoneyPrint> printList = null;
		try {
			printList = financeReceiveMoneyMapper.findReceiveMoneyPrint(fiwt.getXianlu(),result.getWealthNo(), formFkfsh.getYear(), 0);
		} catch (MyBatisSystemException e) {
			return getResult(result, "1009", "该财凭查询有误，请与系统管理员联系");
		}
		//System.out.println(JSON.toJSONString(printList));
		
		Double lr_hj,ir_bdf,ir_chcf=0.00,lr_yshzhk;
		Double ir_dtf,ir_hj;
		Integer ii_round;
		Date ldt_scsj = fiwt.getScsj();//生成时间
		Integer li_round;
		Integer fkfsh = null;
		Double forkliftCharge = 0.00;//叉车费
		Double heavyLoadingFee = 0.00;//装卸费-重
		Double lightLoadingFee = 0.00;//装卸费-轻
		Double insuranceExpense = 0.00;//保险费
		Double baojiajine = 0.00;
		Double baolu = 0.00;
		Double transportSum=0.00;//运费合计
		String khbm = null;
		if (printList.size() > 0) {
			for (int i = 0; printList.size()>=1&&i < printList.size(); i++) {
				baojiajine += printList.get(i).getTbje();
				baolu = printList.get(i).getBaolu();
				transportSum += printList.get(i).calcTotalTransport();	//运费合计（财凭细则累加）
				forkliftCharge += printList.get(i).calcForkliftCharge(); //叉车费（财凭细则累加）
				heavyLoadingFee += printList.get(i).calcHeavyLoadingFee(); //装卸费-重（财凭细则累加）
				lightLoadingFee += printList.get(i).calcLightLoadingFee();//装卸费-轻（财凭细则累加）
			}
			FinanceReceiveMoneyPrint print = printList.get(0);
			insuranceExpense += print.calcInsuranceExpense(baojiajine, baolu);	// 保险费（财凭细则累加）
			//校验 4）校验财务凭号是否属于当前公司
			if (!print.getFazhan().equals(formFkfsh.getCompanyName())) {
				return getResult(result, "1005", "输入的财凭号不是本公司的");
			}
			//校验 5）校验是否已经生成报表，若生成不能再修改
			if (fiwt.getIsreport() > 0) {// 已生成报表的不能再修改
				result.setCanModify(false);
				getModify(result, false, "已经生成财务日报表，不能进行收钱");
			} else {
				result.setCanModify(true);
			}
			//数据计算
			//lr_hj,ir_bdf,insuranceExpense,ir_chcf,transportSum,ir_dtf
			transportSum = transportSum - print.getYouhuijine();//运费合计
			ir_bdf = print.getBdf();// 办单费
			li_round = print.getJshhj();
			lr_hj = print.calcTransportationAcceptanceSheet(forkliftCharge,lightLoadingFee,insuranceExpense,heavyLoadingFee,transportSum);// 运输受理单，总金额大写，合计金额
			
			
			HashMap<String, Object> hwydMap = financeReceiveMoneyMapper.findHwydByYdbhid(fiwt.getYdbh());// 运单编号
			if(hwydMap!=null){
				if(hwydMap.get("fkfsh")!=null){
					fkfsh = (Integer)hwydMap.get("fkfsh");
				}
				if(hwydMap.get("khbm")!=null){
					khbm = (String) hwydMap.get("khbm");
				}
			}
			
			//if(StringUtils.isEmpty(khbm)){
			//	return getResult(result, "1007", "根据运单号："+fiwt.getYdbh()+"查询货物运单信息，得到客户编码为空");
			//}
			//li_round = print.getJshhj();
			if (fkfsh!=null && fkfsh == 0) {
				ir_dtf = print.getShmshhf() + print.getShshmf();
			} else {
				ir_dtf = print.getShmshhf();
				lr_hj = lr_hj - print.getShshmf();
			}
			if (li_round == 1) {
				ir_hj = Math.round(lr_hj*100)/100.00;//四舍五入保留2位小数
			} else {
				ir_hj =  Math.round(lr_hj)*1.00;//四舍五入保留0位小数
			}
			ii_round = li_round;
		}else {
			lr_hj = 0.00;
			ir_hj = lr_hj;
			return getResult(result, "2000", "没有找到这条财务凭证");
		}
		ir_hj = lr_hj;
		Double lde_ye = financeReceiveMoneyMapper.findTCusRecordByKhbm(khbm);
		if (lde_ye == null) {
			lde_ye = 0.00;
		}
		//result.setCustomerBalance(lde_ye);//客户余额
		//查询fkfsh财凭金额表
		FinanceReceiveMoney receiveMoney = financeReceiveMoneyMapper.findReceiveMoney(fiwt.getXianlu(),formFkfsh.getWealthNo(), formFkfsh.getYear());
		Double lr_yhshr = receiveMoney.getYhshr();
		Double lr_hdfk = receiveMoney.getHdfk();
		Double lr_dsk = receiveMoney.getDsk().doubleValue();
		Double yshk = receiveMoney.getYshk().doubleValue();
		Double lr_xianjin = receiveMoney.getXianjin();
		Double lr_yshr = receiveMoney.getYhshr();
		lr_yshzhk = receiveMoney.getYshzhk();
		//用于保存，记录在session
		sessionMoney.setHjje(FinanceReceiveMoneyPrint.getSacleBigDecimal(lr_hj, 2));
		sessionMoney.setBdf(ir_bdf);
		sessionMoney.setChcf(ir_chcf);
		sessionMoney.setDtf(ir_dtf);
		sessionMoney.setBxf(insuranceExpense);
		sessionMoney.setYfhj(transportSum);
		
		//用于返回页面
		result.setCompanyCode(formFkfsh.getCompanyCode());
		//result.setWealthNo(formFkfsh.getWealthNo());
		result.setYshzhk(receiveMoney.getYshzhk());
		result.setXianjin(receiveMoney.getXianjin());
		result.setHdfk(receiveMoney.getHdfk());
		result.setDsk(receiveMoney.getDsk().doubleValue());
		result.setYshk(receiveMoney.getYshk().doubleValue());
		result.setYhshr(receiveMoney.getYhshr());
		
		//result.setChuna(formFkfsh.getChuna());
		
		if (li_round == 0) {
			if (setNullToZero(lr_yhshr) + setNullToZero(lr_yshzhk) + setNullToZero(lr_hdfk) + 
					setNullToZero(lr_xianjin) + setNullToZero(yshk) + setNullToZero(lr_dsk) != ir_hj) {
				lr_yshzhk = ir_hj - lr_yshr - lr_hdfk - lr_xianjin - yshk - setNullToZero(lr_dsk);
				result.setYshzhk(lr_yshzhk);//界面6个参数之一：款未付
			}
		} else {
			double a = setNullToZero(lr_yshr) + setNullToZero(lr_yshzhk) + 
					setNullToZero(lr_hdfk) + setNullToZero(lr_xianjin) +
					setNullToZero(yshk) + setNullToZero(lr_dsk);
			double b = setNullToZero(ir_hj);
			BigDecimal aD = new BigDecimal(String.valueOf(a));
			BigDecimal bD = new BigDecimal(String.valueOf(b));
			double aDecimal = aD.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			double bDeciaml = bD.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			if (aDecimal != bDeciaml) {
				Double c = setNullToZero(ir_hj) - setNullToZero(lr_yhshr) -
						setNullToZero(lr_hdfk) - setNullToZero(lr_xianjin) -
						setNullToZero(yshk) - setNullToZero(lr_dsk);
				BigDecimal cD = new BigDecimal(String.valueOf(c));
				lr_yshzhk = cD.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				result.setYshzhk(lr_yshzhk);//界面6个参数之一：款未付
			}
		}
		if (lr_yshzhk != 0) {
			sessionMoney.setYhshrB(1);
		} else {
			sessionMoney.setYhshrB(0);
		}
		//查找装载清单条件：某运单号和已提字段是1或2的
		int li_count = financeReceiveMoneyMapper.countHczzqdYiti(fiwt.getYdbh());
		if (li_count > 0 && fiwt.getIsreport() == 0) {
			if (setNullToZero(lr_yshzhk) != 0) {
				result.setCanModify(true);
			} else {
				getModify(result, false, "款未付在8小时外才能修改");
			}
		}
		// 受理单修改8小时限制
		// 做了报表不能修改
		if (fiwt.getIsreport() == 0) {
			// 没做报表的，款未付在8小时外才能修改
			if (lr_yshzhk != 0) {
				result.setCanModify(true);
			} else {						//款未付=0代表已经收钱结束，，8小时外不能改
				Date etime = new Date();
				Date btime = ldt_scsj;
				String overTime = sdf.format(ldt_scsj);//之前款结束的时间
				Double rtn = (new Double(etime.getTime() - btime.getTime()) / 3600000.00);
				if (rtn > 8) {
					result.setCanModify(false);
					getModify(result, false, "当前财凭曾在"+overTime+"已做款清，款清8小时后不能再做修改");
				}
			}
		} else {
			result.setCanModify(false);
			getModify(result, false, "已经生成财务日报表，不能进行收钱");
		}
		result.setSessionMoney(sessionMoney);
		if(ii_round==0){
			result.setDecimalPlace(0);
		}else{
			result.setDecimalPlace(2);
		}
		result.setTotalMoney(Math.round(ir_hj*100.00)/100.00);
		return getResult(result, "200", "查询收钱成功");
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={Exception.class},timeout=240)
	public FinanceReceiveMoneyResult saveReceviceMoney(FinanceReceiveMoneyForm formFkfsh,FinanceReceiveMoney sessionMoney) throws ParameterException, BusinessException,Exception {
		FinanceReceiveMoney dbFkfsh = financeReceiveMoneyMapper.findReceiveMoney(formFkfsh.getCompanyCode(),formFkfsh.getWealthNo(), formFkfsh.getYear());
		FinanceReceiveMoneyResult saveResult = new FinanceReceiveMoneyResult();
		// 增加月结结算管控
		Double myyshk = setNullToZero(formFkfsh.getYshk());// 表单填写的月结
		Double myyshkorignal = 0.00;// 数据库查询的月结
		if (!ObjectUtils.isEmpty(dbFkfsh.getYshk())) {
			myyshkorignal = dbFkfsh.getYshk().doubleValue();
		}
		Double plusvalue = myyshk - myyshkorignal;
		// 如果月结不为零则要判断此客户是否有月结权限
		if (myyshk != 0) {
			String customerCode = null;// 存储执行结果
			int result = -1;// 存储执行结果
			String msgstr = null;// 消息字符串

			// 调用存储过程 checkyuejie
			FinanceReceiveMoneyCheckProcedure checkProcedure = new FinanceReceiveMoneyCheckProcedure();
			checkProcedure.setCwpzhbh(formFkfsh.getWealthNo());
			checkProcedure.setGs(formFkfsh.getCompanyCode());
			checkProcedure.setNf(formFkfsh.getYear());
			checkProcedure.setYejieje(plusvalue);
			financeReceiveMoneyMapper.checkMonthReceive(checkProcedure);
			customerCode = checkProcedure.getMycustomercode();
			result = checkProcedure.getMyresult();

			String customerMsg = "";
			if (StringUtils.isNotEmpty(customerCode)) {
				customerMsg = "客户编号:" + customerCode +"，";
			}
			if (result == 0) {
				msgstr = "此客户可以月结";
			} else if (result == 1) {
				msgstr = "不能月结，人工管控无效";
			} else if (result == 2) {
				msgstr = "不能月结，人工管控标志位不为1";
			} else if (result == 3) {
				msgstr = "不能月结，系统月结无效";
			} else if (result == 4) {
				msgstr = "不能月结,月结标志位不为1";
			} else if (result == 5) {
				msgstr = "不能月结,合同结束日期无效";
			} else if (result == 6) {
				msgstr = "不能月结,合同有效期已过!";
			} else if (result == 7) {
				msgstr = "不能月结，可用信用额度无效";
			} else if (result == 8) {
				msgstr = "不能月结，可用信用额度不足";
			} else if (result == 9) {
				msgstr = "不能月结，合同执行开始日期还未到";
			} else {
				msgstr = "系统出错,请与系统管理员联系!";
			}
			if(result!=0){
				return getResult(saveResult,(2000+result)+"", customerMsg + msgstr);
			}
		}

		// UE_UPDATE
		String ls_nf = formFkfsh.getYear(); // 年份
		String xianlu = formFkfsh.getCompanyCode();// 公司
		Long cwpzhbh = formFkfsh.getWealthNo(); // 财凭号
		// 界面输入框6个填写项
		Double lr_yshzhk = setNullToZero(formFkfsh.getYshzhk()); // 款未付
		String ls_jishi = null;
		int li_type = dbFkfsh.getType(); // type!=0表示该财凭类型不是正常财凭
		// 防止冲红后继续收钱
		if (li_type != 0) {
			return getResult(saveResult, "2010", "该财凭类型不是正常财凭,请确认");
		}
		if (lr_yshzhk < 0) {
			return getResult(saveResult, "2011", "输入金额有误");
		}
		int li_yshzhk_b = dbFkfsh.getYshzhkB();
		int li_xianjin_b = dbFkfsh.getXianjinB();
		int li_yhshr_b = dbFkfsh.getYhshrB();
		int li_hdfk_b = dbFkfsh.getHdfkB();
		int li_yshk_b = dbFkfsh.getYshkB();
		//int li_dsk_b = dbFkfsh.getDskB();
		Double xianjin = setNullToZero(formFkfsh.getXianjin());//现金收入
		Double yhshr = setNullToZero(formFkfsh.getYhshr());//银行收入
		Double hdfk = setNullToZero(formFkfsh.getHdfk()); //货到付款
		Double yshk = setNullToZero(formFkfsh.getYshk());//月结
		//Double dsk =  setNullToZero(formFkfsh.getDsk());//代收款
		
		if(setNullToZero(xianjin)!=0){
			li_xianjin_b = 1;
		}else{
			li_xianjin_b = 0;
		}
		
		if(yhshr!=0){
			li_yhshr_b = 1;
		}else{
			li_yhshr_b = 0;
		}
		
		if(hdfk!=0){
			li_hdfk_b = 1;
		}else{
			li_hdfk_b = 0;
		}
		
		if(lr_yshzhk!=0){
			li_yshzhk_b = 1;
		}else{
			li_yshzhk_b = 0;
		}
		if(yshk!=0){
			li_yshk_b = 1;
		}else{
			li_yshk_b = 0;
		}
		
		//if(dsk!=0){
		//	li_dsk_b = 1;
		//}else{
		//	li_dsk_b = 0;
		//}
		
		if (li_xianjin_b == 1 || li_yhshr_b == 1) {
			ls_jishi = "款清";
		}
		if (li_yshk_b == 1 && li_hdfk_b == 0) {
			ls_jishi = "月结";
		}
		if (li_hdfk_b == 1) {
			ls_jishi = "货到付款";
		}
		if (li_yshzhk_b == 1) {
			ls_jishi = "款未付，等通知";
		}
		
		//保存财凭收钱
		sessionMoney.setXianjin(xianjin);//现金收入
		sessionMoney.setYhshr(yhshr);//银行收入
		sessionMoney.setHdfk(hdfk);//货到付款
		sessionMoney.setYshzhk(lr_yshzhk);//款未付
		sessionMoney.setYshk(yshk);//月结
		sessionMoney.setYshzhkB(li_yshzhk_b);
		sessionMoney.setDskB(dbFkfsh.getDskB());
		sessionMoney.setYshkB(li_yshk_b);
		sessionMoney.setXianjinB(li_xianjin_b);
		sessionMoney.setHdfkB(li_hdfk_b);
		sessionMoney.setYhshrB(li_yhshr_b);
		sessionMoney.setXianlu(xianlu);
		sessionMoney.setNf(ls_nf);
		sessionMoney.setCwpzhbh(cwpzhbh);
		sessionMoney.setType(0);
		sessionMoney.setChuna(formFkfsh.getChuna());
		financeReceiveMoneyMapper.updateFkfsh(sessionMoney);
		
		FinanceTransport fiwt = financeReceiveMoneyMapper.findTransportFinance(xianlu, cwpzhbh, ls_nf, 0);
		if (fiwt.getIsreport() == 1) {
			return getResult(saveResult, "2012", "该财凭已经生成日报,禁止再收款;如需要再收款,请冲红处理");
		} else {
			financeReceiveMoneyMapper.updateFiwtReceiveMoneyTime(xianlu, cwpzhbh, ls_nf);
		}
		if (StringUtils.isNotEmpty(formFkfsh.getChuna())) {
			FinanceReceiveMoney financeReceiveMoney = new FinanceReceiveMoney();
			financeReceiveMoney.setGrid(formFkfsh.getGrid());
			financeReceiveMoney.setXianlu(formFkfsh.getCompanyCode());
			financeReceiveMoney.setCwpzhbh(formFkfsh.getWealthNo());
			financeReceiveMoney.setYshzhk(lr_yshzhk);
			financeReceiveMoney.setXianjin(xianjin);
			financeReceiveMoney.setHdfk(hdfk);
			//financeReceiveMoney.setDsk(dbFkfsh.getDsk());
			financeReceiveMoney.setYshk(yshk);
			financeReceiveMoney.setYhshr(yhshr);
			financeReceiveMoney.setChuna(formFkfsh.getChuna());
			financeReceiveMoney.setNf(formFkfsh.getYear());
			financeReceiveMoneyMapper.insertFkfshLog(financeReceiveMoney);
		}
		if(StringUtils.isNotEmpty(fiwt.getYdbh())){
			financeReceiveMoneyMapper.updateHczzqdSourceBeizhu(fiwt.getYdbh(), ls_jishi);
			if ("款未付，等通知".equals(ls_jishi)) {
				financeReceiveMoneyMapper.updateTqsYxfhsj(fiwt.getYdbh());
			}
			financeReceiveMoneyMapper.updateFenliKucunBeizhu(fiwt.getYdbh(),ls_jishi);
		}
		return getResult(saveResult, "200", "保存收钱成功");
	}
	
	public JsonResult getJsonResult(String resultCode, String reason) {
		JsonResult result = new JsonResult();
		result.put("resultCode", resultCode);
		result.put("reason", reason);
		return result;
	}
	
	public JsonResult getJsonResult(FinanceWealthInfo wealthInfo,String resultCode, String reason) {
		JsonResult result = new JsonResult();
		result.put("resultCode", resultCode);
		result.put("reason", reason);
		result.put("entity", wealthInfo);
		return result;
	}
	
	
	@Override
	public JsonResult searchOffsetWealthInfo(FinanceReceiveMoneyForm formFkfsh,Integer userright) throws ParameterException, BusinessException {
		FinanceWealthInfo wealthInfo = new FinanceWealthInfo();
		//TODO 注释
		Integer ii_right = transportRightMapper.getRightNum(AuthorticationConstant.CWPZHCH, formFkfsh.getGrid());
		if(ii_right==null) ii_right = 0;
		//Integer superRight = transportRightMapper.getRightNum(AuthorticationConstant.SUP_CANCEL_RIGHT, formFkfsh.getGrid());
		//boolean isSuperRight = superRight!=null&&superRight>0;//是否是撤销超级权限
		//if(isSuperRight) {
		//	wealthInfo.setCanModify(true);
		//}else {
			wealthInfo.setCanModify(false);
		//}
		Date ld_edatetime;
		
		FinanceTransport fiwt = null;
		if(StringUtils.isEmpty(formFkfsh.getTransportCode()) && ObjectUtils.isEmpty(formFkfsh.getWealthNo())){
			return getJsonResult("400", "运单号和财凭号请任意输入一个！");
		}else if(ObjectUtils.isEmpty(formFkfsh.getWealthNo())) {//运单号
			//if(StringUtils.isEmpty(formFkfsh.getYear())) return getJsonResult("400", "年份不能为空");
			fiwt  = financeReceiveMoneyMapper.findTransportFinanceByTransportCode(formFkfsh.getTransportCode(), 0);
		}else {//财务凭证号
			if(StringUtils.isEmpty(formFkfsh.getCompanyCode())) return getJsonResult("400", "公司编码不能为空");
			Pattern pattern = Pattern.compile("[0-9]*");
			if (!pattern.matcher(formFkfsh.getWealthNo().toString()).matches()) {
				return getJsonResult("400", "财务凭证号必须全是数字");
			} 
			fiwt = financeReceiveMoneyMapper.findTransportFinance(formFkfsh.getCompanyCode(), formFkfsh.getWealthNo(), formFkfsh.getYear(),0);
		}
		if(fiwt==null) return getJsonResult("400", "该财凭号不存在或已冲红");
		long ldec_cwpzhbh= Long.valueOf(fiwt.getCwpzhbh());//财凭号
		String ls_nf = formFkfsh.getYear();//年份
		Map<String,String> chunaZhipiaoMap =  financeReceiveMoneyMapper.findChunaZhipiao(fiwt.getXianlu(), ldec_cwpzhbh, ls_nf, 0);
		
		if(chunaZhipiaoMap==null) return getJsonResult("400", "财凭信息查询不到！");
		int chonghongStatus = 0;
		String ls_ydbhid = fiwt.getYdbh();//运单号
		Date ld_scsj = fiwt.getScsj();//生成时间
		
		if(ii_right==null || ii_right==0) {
			if((new Date().getTime()-ld_scsj.getTime())>(1000*30*60)){//财凭生成30分钟以内可以冲红
				return getJsonResult("400", "大于30分钟，需要财凭冲红的权限才能冲红");
			}else{
				chonghongStatus = 1;
			}
		}
		
		int li_isreport = fiwt.getIsreport();//是否生成报表
		String ls_chuna = chunaZhipiaoMap.get("chuna");//出纳姓名
		String ls_zhipiao = chunaZhipiaoMap.get("zhipiao");//制票姓名
		if(StringUtils.isEmpty(ls_zhipiao) ) return getJsonResult("400", "财凭信息中未保存制票姓名！");
		
		String ls_fazhan = transportOrderCancelMapper.findFazhanByTransportCode(ls_ydbhid);//起始站，发站
		if(!formFkfsh.getCompanyName().equals(ls_fazhan)){
			return getJsonResult("400", "输入的财凭号不是本公司的！");
		}
		
		//检查是否回款，>0不能冲红，返回0可以冲红
		int moneyBackCount = financeReceiveMoneyMapper.checkMoneyBack(fiwt.getXianlu(), ldec_cwpzhbh, ls_nf, 0);
		if(moneyBackCount>0){
			return getJsonResult("400", "此财凭号财务人员已录入回款金额不能再冲红！");
		}
		List<FinanceReceiveMoneyPrint> financePrintList = financeReceiveMoneyMapper.findReceiveMoneyPrint(fiwt.getXianlu(), ldec_cwpzhbh, ls_nf, 0);
		if(financePrintList.isEmpty()){
			return getJsonResult("400", "输入的财务凭证号不存在，或是已被冲红！");
		}
		
		//把界面上需要的参数放入
		Double toubao = 0.0;
		FinanceReceiveMoneyPrint prints = new FinanceReceiveMoneyPrint();
		List<PrintData> list = null;
		for(FinanceReceiveMoneyPrint print:financePrintList){
			toubao +=print.getTbje();
			
			if (null != wealthInfo.getPrint()){	
				list = new ArrayList<PrintData>();
				PrintData printData = new PrintData(print.calcTotalTransport(), print.getJianshu(), print.getZhl(),print.getTiji(),print.getPinming());
				list.add(printData);
				//wealthInfo.setAgencyCharge(wealthInfo.getAgencyCharge()+print.getDsk());//代收款
				wealthInfo.setDeliveryFee(wealthInfo.getDeliveryFee()+print.getShshmf());//上门送货费
				wealthInfo.setHandleBillFee(wealthInfo.getHandleBillFee()+print.getBdf());//办单费
				wealthInfo.setOtherFee(wealthInfo.getOtherFee()+print.getQtfy());//其他费用
				wealthInfo.setPickupFee(wealthInfo.getPickupFee()+print.getShmshhf());//上门取货费						
				wealthInfo.setConveyFee(wealthInfo.getConveyFee()+print.calcTotalTransport());//运费合计
				wealthInfo.setInsuranceExpense(wealthInfo.getInsuranceExpense()+print.calcInsuranceExpense());//保险费
				wealthInfo.setForkliftFee(wealthInfo.getForkliftFee()+print.calcForkliftCharge());//叉车费
				wealthInfo.setLightLoadingFee(wealthInfo.getLightLoadingFee()+print.calcLightLoadingFee());//装卸费轻
				wealthInfo.setHeavyLoadingFee(wealthInfo.getHeavyLoadingFee()+print.calcHeavyLoadingFee());//装卸费重
				wealthInfo.setMiscellaneousFee(wealthInfo.getMiscellaneousFee()+print.calcSumTransportFees(wealthInfo.getForkliftFee(),
						wealthInfo.getLightLoadingFee(), wealthInfo.getInsuranceExpense(), 
						wealthInfo.getHeavyLoadingFee(), wealthInfo.getConveyFee()));//运杂费合计
				wealthInfo.setZhongLiang(wealthInfo.getZhongLiang()+print.getZhl());;
				wealthInfo.setJianShu(wealthInfo.getJianShu()+print.getJianshu());
				wealthInfo.setTiJi(wealthInfo.getTiJi()+print.getTiji());
				//wealthInfo.setTotalFee(wealthInfo.getTotalFee()+print.totalFee());//合计费用	
			}else {
				prints=print;
				wealthInfo.setPrint(print);
				wealthInfo.setAgencyCharge(print.getDsk());//代收款
				wealthInfo.setDeliveryFee(print.getShshmf());//上门送货费
				wealthInfo.setHandleBillFee(print.getBdf());//办单费
				wealthInfo.setOtherFee(print.getQtfy());//其他费用
				wealthInfo.setPickupFee(print.getShmshhf());//上门取货费
				
				wealthInfo.setTransportCode(ls_ydbhid);//货物运单号
				wealthInfo.setWealthCode(formFkfsh.getWealthNo());//财凭号
				wealthInfo.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ld_scsj));
				wealthInfo.setConveyFee(print.calcTotalTransport());//运费合计
				wealthInfo.setInsuranceExpense(print.calcInsuranceExpense());//保险费
				wealthInfo.setForkliftFee(print.calcForkliftCharge());//叉车费
				wealthInfo.setLightLoadingFee(print.calcLightLoadingFee());//装卸费轻
				wealthInfo.setHeavyLoadingFee(print.calcHeavyLoadingFee());//装卸费重
				wealthInfo.setMiscellaneousFee(print.calcSumTransportFees(wealthInfo.getForkliftFee(),
						wealthInfo.getLightLoadingFee(), wealthInfo.getInsuranceExpense(), 
						wealthInfo.getHeavyLoadingFee(), wealthInfo.getConveyFee()));//运杂费合计
				wealthInfo.setZhongLiang(print.getZhl());;
				wealthInfo.setJianShu(print.getJianshu());
				wealthInfo.setTiJi(print.getTiji());
				//wealthInfo.setTotalFee(print.totalFee());//合计费用
			}
		}
		wealthInfo.setPrintData(list);
		wealthInfo.setInsuranceExpense(prints.calcInsuranceExpense(toubao,prints.getBaolu()));
		wealthInfo.setTotalFee(new BigDecimal(prints.getHjje()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		
		//两个时间之间间隔是否小于8小时
		ld_edatetime = new Date();
		boolean lb_ineighthours = false;
		Double rtn = (new Double(ld_edatetime.getTime() - ld_scsj.getTime()) / 3600000.00);
		if (rtn < 8) lb_ineighthours = true;
		
		//判断货物是否已提或已送
		//查询提货签收单表,判断是否提货
		Map<String,Integer> thqsMap = financeReceiveMoneyMapper.findThqsd(ls_ydbhid,1);
		int li_received = 0;
		if(thqsMap == null || ObjectUtils.isEmpty(thqsMap.get("thqshdid")) || thqsMap.get("thqshdid")== 0){//未提
			//查询送货排查单，判断货物是否“已送”
			Map<String,Integer> tcarMap = financeReceiveMoneyMapper.findTcarOut(ls_ydbhid);
			Integer ll_shoujuhao = 0;
			Integer ll_caroutid = 0;
			if(tcarMap==null || ObjectUtils.isEmpty(ll_caroutid) || tcarMap.get("id")==0){
				li_received = 0;//未送
				ll_shoujuhao = 0;
			}else{
				ll_caroutid = tcarMap.get("id");
				ll_shoujuhao = tcarMap.get("shjhm");//收据号
				li_received = 1;	//已送
				if(ll_shoujuhao==null){
					ll_shoujuhao=0;
				}
			}
		}
		//检查冲红次数，冲过红则不能在30分钟内冲红
		int il_shoujuhao = 0;
		int chonghongCount = transportOrderCancelMapper.countFiwtChonghong(ls_ydbhid,formFkfsh.getCompanyCode());//已经冲红过的次数
		if(li_received==1){//该票货物“已提”或“已送”
			if(ii_right>=2){
				chonghongStatus = 1;
				wealthInfo.setCanModify(true);
			}else{
				chonghongStatus = 0;
				il_shoujuhao=0;
				wealthInfo.setCanModify(false);
				return getJsonResult( wealthInfo,"400", "没有冲红该财凭的权限！该票货物已提或已送，必须由财务经理冲红！");
			}
		}else if(li_received==0){//该票货物“未提”且“未送”
			if(!lb_ineighthours) {
				if(ii_right>=1){
					chonghongStatus = 1;
					il_shoujuhao=0;
					wealthInfo.setCanModify(true);
				}else{
					chonghongStatus = 0;
					il_shoujuhao=0;
					wealthInfo.setCanModify(false);
					return getJsonResult(wealthInfo,"400", "财凭生成时间超过8小时以后，必须由单证组长以上冲红!");
				}
			}else if(li_isreport!=0) {
				if(ii_right>=1){
					chonghongStatus = 1;
					il_shoujuhao=0;
					wealthInfo.setCanModify(true);
				}else{
					chonghongStatus = 0;
					il_shoujuhao=0;
					wealthInfo.setCanModify(false);
					return getJsonResult(wealthInfo,"400", "该财凭已生成过报表，必须由单证组长以上冲红!");
				}
			}else if(chonghongCount!=0) {
				if(ii_right>=1){
					chonghongStatus = 1;
					il_shoujuhao=0;
					wealthInfo.setCanModify(true);
				}else{
					chonghongStatus = 0;
					il_shoujuhao=0;
					wealthInfo.setCanModify(false);
					return getJsonResult(wealthInfo,"400", "该财凭已冲红过一次，必须由单证组长以上冲红!");
				}
			}else {
				if(StringUtils.isBlank(ls_chuna)){//出纳为空
					if(ls_zhipiao.equals(formFkfsh.getUserName())){//冲红操作员与制单相同
						chonghongStatus=1;
						il_shoujuhao=0;
						wealthInfo.setCanModify(true);
					}else{
						//冲红操作员与制单不同
						chonghongStatus=0;
						il_shoujuhao=0;
						wealthInfo.setCanModify(false);
						return getJsonResult(wealthInfo,"400","财凭生成30分钟以内，而且没有生成报表时，必须由本人冲红！");
					}
				}else{//出纳不为空
					if(ls_chuna.equals(formFkfsh.getUserName())){
						//冲红操作员与出纳相同
						chonghongStatus = 1;
						il_shoujuhao = 0;
						wealthInfo.setCanModify(true);
					}else{
						//冲红操作员与出纳不同
						chonghongStatus = 0;
						il_shoujuhao=0;
						wealthInfo.setCanModify(false);
						return getJsonResult(wealthInfo,"400", "财凭生成30分钟以内，而且没有生成报表时，必须由本人冲红！");
					}
				}
			}
		}else{
			//发生错误
			chonghongStatus=0;
			il_shoujuhao=0;
			wealthInfo.setCanModify(false);
		}
		wealthInfo.setOffsetStatus(chonghongStatus);
		wealthInfo.setReceiptNo(il_shoujuhao);
		wealthInfo.setWealthCode(fiwt.getCwpzhbh());
		return getJsonResult(wealthInfo, "200", "查询成功！");
	}
	
	@Override
	public JsonResult searchWealthPrint(FinanceReceiveMoneyForm formFkfsh) throws ParameterException, BusinessException {
		FinanceWealthInfo wealthInfo = new FinanceWealthInfo();
		FinanceTransport fiwt = null;
		if(StringUtils.isEmpty(formFkfsh.getTransportCode()) && ObjectUtils.isEmpty(formFkfsh.getWealthNo())){
			return getJsonResult("400", "运单号和财凭号请任意输入一个！");
		}else if(!ObjectUtils.isEmpty(formFkfsh.getTransportCode())) {//运单号不为空
			fiwt  = financeReceiveMoneyMapper.findTransportFinanceByTransportCode(formFkfsh.getTransportCode(), 0);
		}else {//财务凭证号
			if (!NumberUtils.isNumber(formFkfsh.getWealthNo().toString())) {
				return getJsonResult("400", "输入的财务凭证编号无效，不是数字");
			}
			fiwt = financeReceiveMoneyMapper.findTransportFinance(formFkfsh.getCompanyCode(), formFkfsh.getWealthNo(), formFkfsh.getYear(),0);
		}
		if(fiwt==null) return getJsonResult("400", "该运单的财凭不存在或已冲红");
		long ldec_cwpzhbh= Long.valueOf(fiwt.getCwpzhbh());//财凭号
		String ls_nf = fiwt.getNf();//年份
		Map<String,String> chunaZhipiaoMap =  financeReceiveMoneyMapper.findChunaZhipiao(fiwt.getXianlu(), ldec_cwpzhbh, ls_nf, 0);
		
		if(chunaZhipiaoMap==null) return getJsonResult("400", "财凭信息查询不到！");
		int chonghongStatus = 0;
		String ls_ydbhid = fiwt.getYdbh();//运单号
		Date ld_scsj = fiwt.getScsj();//生成时间
		
		String ls_zhipiao = chunaZhipiaoMap.get("zhipiao");//制票姓名
		if(StringUtils.isEmpty(ls_zhipiao)) return getJsonResult("400", "财凭信息中未保存制票姓名！");
		
		List<FinanceReceiveMoneyPrint> financePrintList = financeReceiveMoneyMapper.findReceiveMoneyPrint(fiwt.getXianlu(), ldec_cwpzhbh, ls_nf, 0);
		if(financePrintList.isEmpty()){
			return getJsonResult("400", "输入的财务凭证号不存在，或是已被冲红！");
		}
		
		//把界面上需要的参数放入
		Double toubao = 0.0;
		FinanceReceiveMoneyPrint prints = new FinanceReceiveMoneyPrint();
		List<PrintData> list = null;
		for(FinanceReceiveMoneyPrint print:financePrintList){
			toubao +=print.getTbje();
			
			if (null != wealthInfo.getPrint()){	
				list = new ArrayList<PrintData>();
				PrintData printData = new PrintData(print.calcTotalTransport(), print.getJianshu(), print.getZhl(),print.getTiji(),print.getPinming());
				list.add(printData);
				//wealthInfo.setAgencyCharge(wealthInfo.getAgencyCharge()+print.getDsk());//代收款
				wealthInfo.setDeliveryFee(wealthInfo.getDeliveryFee()+print.getShshmf());//上门送货费
				wealthInfo.setHandleBillFee(wealthInfo.getHandleBillFee()+print.getBdf());//办单费
				wealthInfo.setOtherFee(wealthInfo.getOtherFee()+print.getQtfy());//其他费用
				wealthInfo.setPickupFee(wealthInfo.getPickupFee()+print.getShmshhf());//上门取货费						
				wealthInfo.setConveyFee(wealthInfo.getConveyFee()+print.calcTotalTransport());//运费合计
				wealthInfo.setInsuranceExpense(wealthInfo.getInsuranceExpense()+print.calcInsuranceExpense());//保险费
				wealthInfo.setForkliftFee(wealthInfo.getForkliftFee()+print.calcForkliftCharge());//叉车费
				wealthInfo.setLightLoadingFee(wealthInfo.getLightLoadingFee()+print.calcLightLoadingFee());//装卸费轻
				wealthInfo.setHeavyLoadingFee(wealthInfo.getHeavyLoadingFee()+print.calcHeavyLoadingFee());//装卸费重
				wealthInfo.setMiscellaneousFee(wealthInfo.getMiscellaneousFee()+print.calcSumTransportFees(wealthInfo.getForkliftFee(),
						wealthInfo.getLightLoadingFee(), wealthInfo.getInsuranceExpense(), 
						wealthInfo.getHeavyLoadingFee(), wealthInfo.getConveyFee()));//运杂费合计
				wealthInfo.setZhongLiang(wealthInfo.getZhongLiang()+print.getZhl());;
				wealthInfo.setJianShu(wealthInfo.getJianShu()+print.getJianshu());
				wealthInfo.setTiJi(wealthInfo.getTiJi()+print.getTiji());
				//wealthInfo.setTotalFee(wealthInfo.getTotalFee()+print.totalFee());//合计费用	
			}else {
				prints=print;
				wealthInfo.setPrint(print);
				wealthInfo.setAgencyCharge(print.getDsk());//代收款
				wealthInfo.setDeliveryFee(print.getShshmf());//上门送货费
				wealthInfo.setHandleBillFee(print.getBdf());//办单费
				wealthInfo.setOtherFee(print.getQtfy());//其他费用
				wealthInfo.setPickupFee(print.getShmshhf());//上门取货费
				
				wealthInfo.setTransportCode(ls_ydbhid);//货物运单号
				wealthInfo.setWealthCode(formFkfsh.getWealthNo());//财凭号
				wealthInfo.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ld_scsj));
				wealthInfo.setConveyFee(print.calcTotalTransport());//运费合计
				wealthInfo.setInsuranceExpense(print.calcInsuranceExpense());//保险费
				wealthInfo.setForkliftFee(print.calcForkliftCharge());//叉车费
				wealthInfo.setLightLoadingFee(print.calcLightLoadingFee());//装卸费轻
				wealthInfo.setHeavyLoadingFee(print.calcHeavyLoadingFee());//装卸费重
				wealthInfo.setMiscellaneousFee(print.calcSumTransportFees(wealthInfo.getForkliftFee(),
						wealthInfo.getLightLoadingFee(), wealthInfo.getInsuranceExpense(), 
						wealthInfo.getHeavyLoadingFee(), wealthInfo.getConveyFee()));//运杂费合计
				wealthInfo.setZhongLiang(print.getZhl());;
				wealthInfo.setJianShu(print.getJianshu());
				wealthInfo.setTiJi(print.getTiji());
				//wealthInfo.setTotalFee(print.totalFee());//合计费用
			}
		}
		wealthInfo.setPrintData(list);
		wealthInfo.setInsuranceExpense(prints.calcInsuranceExpense(toubao,prints.getBaolu()));
		wealthInfo.setTotalFee(new BigDecimal(prints.getHjje()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		
		//判断货物是否已提或已送
		//查询提货签收单表,判断是否提货
		Map<String,Integer> thqsMap = financeReceiveMoneyMapper.findThqsd(ls_ydbhid,1);
		if(thqsMap == null || ObjectUtils.isEmpty(thqsMap.get("thqshdid")) || thqsMap.get("thqshdid")==0){//未提
			//查询送货排查单，判断货物是否“已送”
			Map<String,Integer> tcarMap = financeReceiveMoneyMapper.findTcarOut(ls_ydbhid);
			Integer ll_shoujuhao = 0;
			Integer ll_caroutid = 0;
			if(tcarMap==null || ObjectUtils.isEmpty(ll_caroutid) || tcarMap.get("id")==0){
				ll_shoujuhao = 0;
			}else{
				ll_caroutid = tcarMap.get("id");
				ll_shoujuhao = tcarMap.get("shjhm");//收据号
				if(ll_shoujuhao==null){
					ll_shoujuhao=0;
				}
			}
		}
		//检查冲红次数，冲过红则不能在30分钟内冲红
		int il_shoujuhao = 0;
		wealthInfo.setOffsetStatus(chonghongStatus);
		wealthInfo.setReceiptNo(il_shoujuhao);
		wealthInfo.setWealthCode(fiwt.getCwpzhbh());
		return getJsonResult(wealthInfo, "200", "查询成功！");
	}

	@Override
	@Transactional
	@SuppressWarnings("unused")
	public String offsetWealthInfo(FinanceReceiveMoneyForm formFkfsh) throws ParameterException, BusinessException {
		FinanceTransport fiwt = null;
		if(StringUtils.isEmpty(formFkfsh.getTransportCode()) && ObjectUtils.isEmpty(formFkfsh.getWealthNo())){
			Assert.notNull("formFkfsh.getWealthNo()", true, "运单号和财凭号请任意输入一个！");
		}else if(ObjectUtils.isEmpty(formFkfsh.getWealthNo())) {//运单号
			if(StringUtils.isEmpty(formFkfsh.getYear())) Assert.notNull("formFkfsh.getYear()", true, "年份不能为空");
			fiwt  = financeReceiveMoneyMapper.findTransportFinanceByTransportCode(formFkfsh.getTransportCode(), 0);
		}else {//财务凭证号
			Assert.trueIsWrong("formFkfsh.getCompanyCode()", StringUtils.isEmpty(formFkfsh.getCompanyCode()), "公司编码不能为空");
			Pattern pattern = Pattern.compile("[0-9]*");
			if (!pattern.matcher(formFkfsh.getWealthNo().toString()).matches()) {
				Assert.trueIsWrong("formFkfsh.getCompanyCode()", true, "财务凭证号必须全是数字");
			} 
			fiwt = financeReceiveMoneyMapper.findTransportFinance(formFkfsh.getCompanyCode(), formFkfsh.getWealthNo(), formFkfsh.getYear(),0);
		}
		Assert.notNull("formFkfsh.getDescription()",formFkfsh.getDescription(), "请先输入冲红原因！");
		FinanceReceiveMoneyCheckProcedure produre  = new FinanceReceiveMoneyCheckProcedure();
		produre.setNf(formFkfsh.getYear());
		produre.setCwpzhbh(formFkfsh.getWealthNo());
		produre.setGsbh(formFkfsh.getCompanyCode());
		financeReceiveMoneyMapper.searchOffsetWealthInfo(produre);
		Long lde_cwpzhbh = produre.getCwpzhbh();
		Assert.isTrue("financeReceiveMoneyMapper.createCwpzh(produre)", !(lde_cwpzhbh==null||lde_cwpzhbh<=0), "财务凭证创建失败。");
		FinanceChonghongProcedure procedure = new FinanceChonghongProcedure();
		procedure.setNf(formFkfsh.getYear());
		procedure.setCwpzhbh(formFkfsh.getWealthNo());
		procedure.setXianlu(formFkfsh.getCompanyCode());
		procedure.setChonghongzhuangtai(formFkfsh.getOffsetStatus());
		procedure.setShoujuhao(formFkfsh.getReceiptNo());
		procedure.setWhy(formFkfsh.getDescription());
		procedure.setChuna(formFkfsh.getUserName());
		procedure.setCwpzhbh2(lde_cwpzhbh);
		Long res = new Long(0);
		try{
			res = financeReceiveMoneyMapper.offsetWealthInfo(procedure);//红票财凭号
		}catch(Exception e){
			throw new BusinessException("财凭冲红执行失败",e);
		}
		try {
			TransportOrder transportOrder = transportOrderMapper.getTransportOrderByYdbhid(formFkfsh.getTransportCode());
			transportOrder.setCwpzhy(0);
			transportOrderMapper.updateTransportOrderByYdbhid(transportOrder);
		} catch (Exception e) {
			throw new BusinessException("财凭冲红执行修改运单 失败!",e);
		}
		
		
		//新增异常日志信息
		ExceptionLog exceptionLog = new ExceptionLog();
		exceptionLog.setOperatorName(formFkfsh.getUserName());
		exceptionLog.setOperatorAccount(formFkfsh.getGrid());
		exceptionLog.setOperatorCompany(formFkfsh.getCompanyName());
		exceptionLog.setIpAddress(IPUtil.getLocalIP());
		exceptionLog.setYdbhid(formFkfsh.getTransportCode());
		exceptionLog.setOperatingMenu(OPERATING_MENU);
		String operatingContent = "运单号为：" + formFkfsh.getTransportCode() + "，财凭号为：" +formFkfsh.getWealthNo()+ "的财凭记录冲红成功！";
		exceptionLog.setOperatingContent(operatingContent);
		exceptionLog.setOperatingTime(new Date());
		exceptionLog.setCreateName(formFkfsh.getUserName());
		exceptionLog.setCreateTime(new Date());
		exceptionLog.setUpdateName(formFkfsh.getUserName());
		exceptionLog.setUpdateTime(new Date());
		
		try {
			exceptionLogService.addExceptionLog(exceptionLog);
		} catch (Exception e) {			
			throw new BusinessException("新增异常日志信息失败", e);
		}
		
		return lde_cwpzhbh.toString();
		//ycr_user.of_login_log("冲红财凭("+sle_3.text+")"+ls_xianlu+" "+string(lde_cwpzhbh))
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,timeout=420)
	public FinanceReceiveMoneyResult saveMonthReceviceMoney(FinanceReceiveMoneyForm formFkfsh) throws ParameterException, BusinessException,Exception {
		FinanceReceiveMoneyResult searchResult = searchBeforeReceiveMoney(formFkfsh);//查询是否可以收钱
		FinanceReceiveMoney sessionMoney = searchResult.getSessionMoney();
		if(searchResult.isCanModify()&&searchResult.getYshzhk()>0){//可收钱且款未付>0
			formFkfsh.setCompanyCode(searchResult.getCompanyCode());
			formFkfsh.setWealthNo(searchResult.getWealthNo());
			formFkfsh.setYshzhk(0.00);//月结
			formFkfsh.setXianjin(searchResult.getXianjin());
			formFkfsh.setHdfk(searchResult.getHdfk());
			formFkfsh.setDsk(searchResult.getDsk());
			formFkfsh.setYshk(searchResult.getYshzhk());
			formFkfsh.setYhshr(searchResult.getYhshr());
			formFkfsh.setYear(formFkfsh.getYear());
			FinanceReceiveMoneyResult saveResult = saveReceviceMoney(formFkfsh,sessionMoney);
			saveResult.setYshk(searchResult.getYshzhk());
			saveResult.setYear(formFkfsh.getYear());
			saveResult.setUserName(formFkfsh.getChuna());
			saveResult.setCompanyName(formFkfsh.getCompanyName());
			saveResult.setGrid(formFkfsh.getGrid());
			saveResult.setWealthNo(formFkfsh.getWealthNo());
			return saveResult;
		}else if(searchResult.isCanModify()&&searchResult.getYshzhk()<=0){//虽然查询出可收钱，但是款未付金额不正常的不能收钱，需要确认数据
			searchResult.setGrid(formFkfsh.getGrid());
			searchResult.setUserName(formFkfsh.getChuna());
			searchResult.setCompanyName(formFkfsh.getCompanyName());
			if("200".equals(searchResult.getResultCode())){
				searchResult.setResultMsg("校验未通过，原因：可收钱的金额为："+searchResult.getYshzhk()+"，请确认是否已款清或数据问题");
				searchResult.setResultCode("400");
			}
			return searchResult;
		}else{//不能收钱，直接返回查询结果
			searchResult.setGrid(formFkfsh.getGrid());
			searchResult.setUserName(formFkfsh.getChuna());
			searchResult.setCompanyName(formFkfsh.getCompanyName());
			if("200".equals(searchResult.getResultCode())){
				searchResult.setResultMsg(searchResult.getCanotModifyReason());
				searchResult.setResultCode("400");
			}
			return searchResult;
		}
	}

	@Override
	public JsonResult updatePrintCountAndDate(String waybillNum) {
		int count = financeReceiveMoneyMapper.updatePrintCountAndDate(waybillNum);
		if (count != 1) {
			return JsonResult.getConveyResult("400", "记录打印次数和打印时间失败！");
		} else {
			return JsonResult.getConveyResult("200", "操作成功");
		}
	}

}
