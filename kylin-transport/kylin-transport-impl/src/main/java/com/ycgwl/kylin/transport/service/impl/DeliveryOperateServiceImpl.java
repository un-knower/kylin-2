package com.ycgwl.kylin.transport.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.util.StringUtil;
import com.microsoft.sqlserver.jdbc.StringUtils;
import com.ycgwl.kylin.AuthorticationConstant;
import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.entity.Page;
import com.ycgwl.kylin.entity.RequestJsonEntity;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.entity.*;
import com.ycgwl.kylin.transport.entity.adjunct.Employee;
import com.ycgwl.kylin.transport.persistent.DeliveryOperateMapper;
import com.ycgwl.kylin.transport.persistent.TransportRightMapper;
import com.ycgwl.kylin.transport.service.api.*;
import com.ycgwl.kylin.util.CommonDateUtil;
import com.ycgwl.kylin.util.DateUtils;
import com.ycgwl.kylin.util.UtilHelper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;

@Service("kylin.transport.dubbo.local.deliveryOperateService")
public class DeliveryOperateServiceImpl implements IDeliveryOperateService {

	private static final Short DEFAULT_SHORT = 0;

	private Function<String,Integer> FUNC = (yipai)-> org.apache.commons.lang.StringUtils.isEmpty(yipai) ? null :("true".equals(yipai) ? 1: 0);
	
	@Resource
	private BundleReceiptService receiptService;

	@Resource
	private ITransportOrderService orderService;

	@Resource
	private DeliveryOperateMapper deliveryOperateMapper;

	@Resource 
	private AdjunctSomethingService adjunctSomethingService;

	@Resource
	private ITransportSignRecordService signRecordService;

	@Resource
	private TransportRightMapper rightMapper;

	@Resource
	private TransportOrderDetailService detailService;
	
	@Resource
	private IExceptionLogService exceptionLogService;
	
	@Resource
	private IFinancialReceiptsService financialReceiptsService;
	
	/**
	 * PB中父窗口的公共校验方法
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2018-01-11 09:33:21
	 */
	public void CommonCheck(String ydbhid,String ydxzh,String grid) {
		//1.判断是否到站    前面已经判断过了,省略
		Map<String,Object> map = new HashMap<>();
		map.put("ydbhid", ydbhid);
		map.put("ydxzh", ydxzh);
		map.put("grid", grid);
		deliveryOperateMapper.excuteCheckfwfsandcar(map);
		int fwfsandcar = Integer.parseInt(String.valueOf(map.get("flag")));

		ShqsdResult payInfo = signRecordService.getReachAfterPayInfo(ydbhid);
		if(payInfo.getDsk() > 0 || payInfo.getHdfk() > 0 || fwfsandcar > 0) {
			FinancialReceiptsMaster financial = financialReceiptsService.queryFinancialReceiptsMasterByYdbhid(ydbhid);
			if(financial == null ) {
        throw new ParameterException("此运单号为"+ydbhid+",要求到站付款或代收款,在分理之前必须生成分理收据,请确认");
      }
			//2018-04-10： jira:943 提货单送货单：判断分理收据是否交钱
			//判断是否收过钱
			if(financial.getIsShq() != 1)	//没收过钱
      {
        throw new ParameterException("此运单号为"+ydbhid+",已生成分理收据,请进行分理数据交钱后再来");
      }
		}

	}

	/**
	 * 放货通知
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2018-01-10 13:22:01
	 */
	public Integer getdispatchAdvice(String ydbhid) {
		HashMap<String, Short> result = deliveryOperateMapper.getdispatchAdvice(ydbhid);
		if(result == null || result.getOrDefault("ddfhzt", DEFAULT_SHORT) == 0)		//不存在代表不用等待放货通知
    {
      return 2;
    }
		return result.getOrDefault("tzfhzt", DEFAULT_SHORT).intValue();
	}

	@Override
	public JsonResult getBillOfdeliveryByYdbhid(List<String> xuhaos,String account,String username,String company) throws ParameterException,BusinessException,Exception {
		Integer rightNum = rightMapper.getRightNum(AuthorticationConstant.THQSHD,account);
		if(rightNum == null || rightNum < 1) {
      return JsonResult.getConveyResult("400", AuthorticationConstant.MESSAGE);
    }
		
		String ydbhid = null;
		for (String xuhao : xuhaos) {
			BundleReceipt receipt = receiptService.getBundleReceiptByXuhao(xuhao.trim());
			if(ydbhid == null) {
        ydbhid = receipt.getYdbhid();
      } else if(!ydbhid .equals(receipt.getYdbhid())) {
        throw new ParameterException("请保持运单统一");
      }
			String ydxzh = String.valueOf(receipt.getYdxzh());
			//1.查询装载是否是装载到本公司的记录
			if( !(company .equals(receipt.getDaozhan())  || company .equals(receipt.getFazhan()))) {
        throw new ParameterException("该货物是装载到站"+receipt.getDaozhan()+"，发站是"+receipt.getFazhan()+"，只有发站到站公司可以生成提货单！");
      }
			//2.查看是否存在提货签收单
			Integer deliveryNumber = DeliveryNumber(ydbhid, ydxzh);
			if( deliveryNumber!=null &&deliveryNumber ==2 ) {
        throw new ParameterException("系统检测到运单号" + ydbhid+ "细则号" + ydxzh + "相应的送货单已生成，禁止继续生成！");
      }
			Integer dispatchAdvice = getdispatchAdvice(ydbhid);
			if(dispatchAdvice!=2 || dispatchAdvice <0) {
        throw new ParameterException(ydbhid + "该票货需要等客户通知才能放货！");
      }
			//3.判断是否已经到站
			if(receipt.getYdzh() ==0) {
        throw new ParameterException("运单号为 "+ydbhid+",细则号为 "+ydxzh+"的该货物还未到，不能生成提货签收单！");
      }
			//4.是否生成运输受理单
			FiwtResult fiwtResult = signRecordService.getXianluByYdbhid(ydbhid);
			if(fiwtResult == null || StringUtils.isEmpty(fiwtResult.getXianlu())) {
        throw new ParameterException("运单号为 "+ydbhid+"的货物还未生成财务凭证，不能生成提货签收单！");
      }
			//5.是否款未付并且未放货通知
			FkfshResult fkfshResult = signRecordService.getFkfshResult(fiwtResult);
			if(fkfshResult!= null  && fkfshResult.getYshzhk_b()) {
				if(deliveryOperateMapper.getUnpaidByYdbhid(ydbhid) == 0) {
          throw new ParameterException("运单号为 "+ydbhid+"的货物是款未付并且还未发放货通知，不能生成提货签收单！");
        }
			}
			TransportOrder order = orderService.getTransportOrderByYdbhid(ydbhid);
			Integer fzfk = order.getFzfk();
			if(fzfk!=null && fzfk==1 && fkfshResult.getYshzhk_b()) {
				throw new ParameterException("运单号为 "+ydbhid+"是发站付款，款清后才能生成提货单，请先收钱！");
			}
			
			//检查发付的必须收钱才能生成提货单
			//6.yiti字段的判断
			Integer yiti = receipt.getYiti();
			if(yiti == 1) {
        throw new ParameterException("运单号为 "+ydbhid+",细则号为 "+ydxzh+"的该货物已提，不能再生成提货签收单！");
      }
			if(yiti == 2) {
        throw new ParameterException("运单号为 "+ydbhid+",细则号为 "+ydxzh+"的该货物已送货，不能再生成提货签收单！");
      }
			if(yiti >= 3) {
        throw new ParameterException("运单号为 "+ydbhid+",细则号为 "+ydxzh+"的该货物已中转，不能再生成提货签收单！");
      }
			CommonCheck(ydbhid, ydxzh,account);
			//2018-01-31 :新增查询是否有成本信息,如果没有成本信息始不可以进行提货的
			checkIncome(xuhao);
		}
		JsonResult result = JsonResult.getConveyResult("200", "操作成功");
		result.put("cangwei", deliveryOperateMapper.getCangweiByYdbhid(ydbhid, company));
		result.put("kpr", username);
		return result;
	}
	
	public void checkIncome(String xuhao) {
		BigDecimal cost = deliveryOperateMapper.getTCostFromTIncomeDByXuhao(xuhao);
		if(cost == null )//|| cost.compareTo( BigDecimal.ZERO) != 1
    {
      throw new ParameterException("该装载没有生成成本,请先生成成本再进行提货");
    }
	}
	

	@Override
	public JsonResult deliverydocuments(List<String> xuhaos,String account,String company) throws BusinessException,ParameterException, Exception {
		Integer rightNum = rightMapper.getRightNum(AuthorticationConstant.PC_SHOUT,account);
		if(rightNum == null || rightNum < 1) {
      return JsonResult.getConveyResult("400", AuthorticationConstant.MESSAGE);
    }

		if(xuhaos.size()>3) {
      throw new ParameterException("选择的清单不能超过3条！");
    }
		String ydbhid = null;
		for (String xuhao : xuhaos) {
			BundleReceipt receipt = receiptService.getBundleReceiptByXuhao(xuhao);
			ydbhid = receipt.getYdbhid();
			String ydxzh = String.valueOf(receipt.getYdxzh());
//			if( !company.equals(receipt.getDaozhan()) )
//				throw new ParameterException("该货物是装载到"+receipt.getDaozhan()+"公司的货物！");
			if( !(company .equals(receipt.getDaozhan())  || company .equals(receipt.getFazhan()))) {
        throw new ParameterException("该货物是装载到站"+receipt.getDaozhan()+"，发站是"+receipt.getFazhan()+"，只有发站到站公司可以生成提货单！");
      }
			Integer deliveryNumber = DeliveryNumber(ydbhid, ydxzh);
			if(deliveryNumber == 2 ) {
        throw new ParameterException("该运单号的细则号" + ydxzh + "相应的送货单已生成，禁止继续生成！");
      }
			if(deliveryNumber == 1 ) {
        throw new ParameterException("该运单号的细则号" + ydxzh + "相应的提货单已生成，禁止继续生成！");
      }
			Integer dispatchAdvice = getdispatchAdvice(ydbhid);
			if(dispatchAdvice!= 2 || dispatchAdvice < 0) {
        throw new ParameterException(ydbhid + "该票货需要等客户通知才能放货！");
      }
			if(receipt.getYdzh() == 0) {
        throw new ParameterException("运单号的细则号为 "+ydxzh+"的该货物还未到，不能生成送货签收派车单！");
      }
			Integer yiti = receipt.getYiti();
			if(yiti == 1) {
        throw new ParameterException("运单号的细则号为 "+ydxzh+"的该货物已提，不能再生成送货签收派车单！");
      }
			if(yiti == 2) {
        throw new ParameterException("运单号的细则号为 "+ydxzh+"的该货物已送货，不能再生成送货签收派车单！");
      }
			if(yiti >= 3) {
        throw new ParameterException("该运单的细则号为 "+ydxzh+"的该货物已中转，不能再生成送货签收派车单！");
      }

			//等托指令放货勾选不能生成送货单
			if(adjunctSomethingService.isReleaseWaiting(ydbhid)) {
        throw new ParameterException("该运单是等托指令放货，没有放货通知,不能生成送货签收派车单");
      }
			
			Date yxfhsj = deliveryOperateMapper.getyxfhsjfromt_qs(ydbhid);
			LocalDate localDate = LocalDate.of(2002, 01, 01);	
			Date date = new Date(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
			if(yxfhsj == null || yxfhsj.before(date) || yxfhsj.after(new Date())) {
        throw new ParameterException("该运单的货物暂时还不能送货，请等候发站公司的放货通知！");
      }

			ShqsdResult payInfo = signRecordService.getReachAfterPayInfo(ydbhid);
			FiwtResult fiwtResult = signRecordService.getXianluByYdbhid(ydbhid);
			if(fiwtResult == null || StringUtils.isEmpty(fiwtResult.getXianlu())) {
        throw new ParameterException("该运单货物还未生成财凭，不能生成送货签收单！");
      }
			FkfshResult fkfshResult = signRecordService.getFkfshResult(fiwtResult);
			if(fkfshResult!= null  && fkfshResult.getYshzhk_b()) {
				if(deliveryOperateMapper.getUnpaidByYdbhid(ydbhid) == 0) {
          throw new ParameterException("此运单号的货物是款未付，不能生成送货签收单！");
        }
			}
			Map<String,Object> map = new HashMap<>();
			map.put("ydbhid", ydbhid);
			map.put("ydxzh", ydxzh);
			map.put("grid", account);
			deliveryOperateMapper.excuteCheckfwfsandcar(map);
			int fwfsandcar = Integer.parseInt(String.valueOf(map.get("flag")));
			
			//到付款和代收款业务：司机需要先生成送货单，送货上门，然后才能拿到钱，再进行收钱
			if(payInfo.getDsk() > 0 || payInfo.getHdfk() > 0 || fwfsandcar > 0) {
				//Integer id = deliveryOperateMapper.getT_CWSJ_MASTERId(ydbhid);
//				if(id == null || id <= 0)
//					throw new ParameterException("此运单要求到站付款或代收款,请生成分理收据");
//				if(financial.getIsShq() != 1)	//提货单送货单：判断分理收据是否交钱
//						throw new ParameterException("此运单号为"+ydbhid+",已生成分理收据,请进行分理数据交钱后再来");
				FinancialReceiptsMaster financial = financialReceiptsService.queryFinancialReceiptsMasterByYdbhid(ydbhid);
				if(financial == null ) {
          throw new ParameterException("该运单要求到站付款或代收款,在分理之前必须生成分理收据,请确认");
        }
			}
			//CommonCheck(ydbhid, ydxzh,account);
		}
		TransportOrder order = orderService.getTransportOrderByYdbhid(ydbhid);
		String beizhu = getBeizhu(ydbhid);
		JsonResult result = JsonResult.getConveyResult("200", "操作成功");
		result.put("fhdh", order.getFhdwlxdh());
		result.put("shlxr", order.getShhrlxdh());
		result.put("shdh", order.getShhd());
		result.put("compname", company);
		result.put("beizhu", beizhu);
		return result;
	}


	public String getBeizhu(String ydbhid) {
		Map<String, Object> v_shqsd = deliveryOperateMapper.getSomethingFromV_SHQSDByYdbhid(ydbhid);
		StringBuilder sb = new StringBuilder();
		sb.append("受理单号："+ v_shqsd.get("xianlu"))
		.append(String.format("%07d",Integer.parseInt( v_shqsd.get("cwpzhbh").toString()) ))
		.append("  金额: ")
		.append(v_shqsd.get("hjje"))
		.append("元");
		//v_shqsd视图中与钱相关的字段都是 money的类型.,与java的bigdecimal对应
		BigDecimal xianjin = (BigDecimal)v_shqsd.get("xianjin");
		BigDecimal hjje = (BigDecimal)v_shqsd.get("hjje");
		BigDecimal yhshr = (BigDecimal)v_shqsd.get("yhshr");
		BigDecimal yshk = (BigDecimal)v_shqsd.get("yshk");
		BigDecimal hdfk = (BigDecimal)v_shqsd.get("hdfk");
		BigDecimal yshzhk = (BigDecimal)v_shqsd.get("yshzhk");
		BigDecimal dsk = (BigDecimal)v_shqsd.get("dsk");

		if(xianjin .compareTo(hjje) == 0 ||
				yhshr.compareTo(hjje) == 0 ||
				(xianjin.add(yhshr)) .compareTo(hjje) == 0) {
      sb.append("款清");
    }
		if(yshk.compareTo(BigDecimal.ZERO) != -1 && hdfk .compareTo(BigDecimal.ZERO) != 1 ) {
      sb.append("月结");
    }
		if(yshzhk.compareTo(BigDecimal.ZERO) == 1) {
      sb.append("款未付,等通知");
    }
		if(hdfk.compareTo(BigDecimal.ZERO) == 1) {
      sb.append("货单付款:").append(hdfk);
    }
		if(dsk.compareTo(BigDecimal.ZERO) == 1) {
      sb.append("代收款:").append(dsk);
    }
		return sb.toString();
	}
	/**
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2018-01-10 13:15:07
	 *  0--->不存在 , 1 --->提货单已存在 , 2 --->送货单已存在 ,  -1 --> 检测失败
	 */
	public Integer DeliveryNumber(String ydbhid,String ydxzh) {
		try {
			Integer result = deliveryOperateMapper.getthqshdidByYdbhidAndYdxzh(ydbhid, ydxzh);
			if(result != null &&  result > -1)	//代表有提货单号
      {
        return 1;		//提货单已经存在
      }
			//不存在提货单号,继续查找是否存在派车单号
			result = deliveryOperateMapper.getCarOutDetail1IdByYdbhidAndYdxzh(ydbhid,ydxzh);
			if(result != null &&  result > -1) {
        return 2;
      } else {
        return 0;
      }
		}catch(Exception e) {
			return -1;
		}
	}
	public Integer deliveryNumber(String ydbhid) {
		try {
			Integer result = deliveryOperateMapper.top1thqshdid(ydbhid);
			if(result != null &&  result > -1)	//代表有提货单号
      {
        return 1;		//提货单已经存在
      }
			//不存在提货单号,继续查找是否存在派车单号
			result = deliveryOperateMapper.top1tCarOut(ydbhid);
			if(result != null &&  result > -1) {
        return 2;
      } else {
        return 0;
      }
		}catch(Exception e) {
			return -1;
		}
	}

	/**
	 * entity:
	 * 	{
	 * 	"xuhaos":["234234234","23423423"]
	 * 	"thrmch": "张三"				//提货人姓名
	 *	"thrsfzhm":"********"		//提货人身份证号码
	 * 	"shhrsfzhm":"dfsdfa"		//收货人身份证号码
	 * 	"kpr":"李四"					//开票人	
	 * 	"beizhu2":"beizu"
	 * 
	 * 	 * *
	 * 	改了
	 * 	xuhaos的数组改为:
	 * 
	 * 	"list":[
	 * 		{"xuhao":"234234","cangwei1":"某某某"},
	 * 	]
	 * *
	 * 	}
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public JsonResult saveBillOfdelivery(RequestJsonEntity entity) throws ParameterException,BusinessException,Exception {
		//thqshdid提货签收id就是数据库中最大值加一;  thqsd表
		
		Integer rightNum = rightMapper.getRightNum(AuthorticationConstant.THQSHD,entity.getString("account"));
		if(rightNum == null || rightNum < 1) {
      return JsonResult.getConveyResult("400", AuthorticationConstant.MESSAGE);
    }
		
		String company = entity.getString("company");
		Integer thqshdid = deliveryOperateMapper.getMaxThqsdByCompany(company);
		entity.put("thqshdid", thqshdid);
		Integer thqsdxz = 0;
		entity.put("shijian", entity.getDate("shijian"));
		String ydbhid = null;
		
		List<Map<String,Object>> list = (ArrayList<Map<String,Object>>)entity.get("list");
		for (Map<String, Object> submap : list) {
			String xuhao = String.valueOf(submap.get("xuhao"));
			String cangwei1 = String.valueOf(submap.get("cangwei1"));
			BundleReceipt receipt = receiptService.getBundleReceiptByXuhao(xuhao);
			if(ydbhid == null) {
        ydbhid = receipt.getYdbhid();
      } else if(!ydbhid.equals(receipt.getYdbhid())) {
        throw new ParameterException("运单号不一致");
      }
			Integer ydxzh = receipt.getYdxzh();
			//读取财凭看是不是到付或代收款
			Map<String, BigDecimal> hdfkAndDsk = deliveryOperateMapper.getHdfkAndDskByYdbhid(ydbhid);
			if(hdfkAndDsk == null  || hdfkAndDsk.get("hdfk") == null || hdfkAndDsk.get("dsk") == null ) {
        throw new BusinessException("此票货物发站未生成财凭请先生成财凭!");
      }
			if(hdfkAndDsk.get("hdfk").compareTo(BigDecimal.ZERO) != 1  || 
					hdfkAndDsk.get("dsk").compareTo(BigDecimal.ZERO) != 1) {
				if(signRecordService.getXianluByYdbhid(ydbhid)==null) {
          throw new BusinessException("此运单号为"+ydbhid+",要求到站付款或代收款,在分理之前必须生成分理收据,请确认");
        }
			}
			//查询分理库存
			List<FenliKucunEntity> fenlikucunList = adjunctSomethingService.queryFenliKucunEntity(ydbhid, ydxzh.intValue(), company);
			if(fenlikucunList.isEmpty()) {
				throw new BusinessException("该运单没有"+company+"公司的分理库存");
			}
			Integer fenliJianshu = 0;
			BigDecimal fenliTiji = new BigDecimal(0);
			BigDecimal fenliZhl = new BigDecimal(0);
			for(FenliKucunEntity fenli:fenlikucunList) {
				fenliJianshu += Integer.parseInt(fenli.getJianshu());
				fenliTiji = fenliTiji.add(new BigDecimal(fenli.getTiji()));
				fenliZhl = fenliZhl.add(new BigDecimal(fenli.getZhl()));
			}
			if(fenliTiji.compareTo(receipt.getTiji()) == -1 ||	fenliZhl.compareTo(receipt.getZhl()) == -1 || fenliJianshu < receipt.getJianshu()) {
        throw new BusinessException("库存数量小于本次装载到货的数量！");
      }
			Integer deliveryNumber = DeliveryNumber(ydbhid, ydxzh.toString());
			if(deliveryNumber == 2) {
        throw new BusinessException("系统检测到运单号" + ydbhid+ "细则号" + ydxzh + "相应的送货单已生成，当前提货单禁止保存！");
      }

			//开始更新相关表中的数据
			deliveryOperateMapper.modifyHczzqdSourceYitiByXuhao(xuhao,1);
			//			deliveryOperateMapper.modifyT_QSFlag(ydbhid,0+"");			//不明确的用处,刘娇20170615 增加的是否撤销签收
			//			deliveryOperateMapper.deleteSignHWYD_ROUTEByYdbhid(ydbhid);
			//时间原因,先这样,以后优先考虑优化
			if(StringUtil.isEmpty(entity.getString("beizhu2"))) {
        entity.put("beizhu2", receipt.getBeizhu());
      }
			entity.put("dzshhd", receipt.getDzshhd());
			entity.put("ydbhid", receipt.getYdbhid());
			entity.put("ydxzh", receipt.getYdxzh());
			entity.put("shhrmch", receipt.getShhrmch());
			entity.put("pinming", receipt.getPinming());
			entity.put("jianshu", receipt.getJianshu());
			entity.put("zhl", receipt.getZhl());
			entity.put("tiji", receipt.getTiji());
			entity.put("SHHRLXDH", receipt.getShhrlxdh());
			entity.put("beizhu", receipt.getBeizhu());
			entity.put("xuhao", xuhao);
			entity.put("cangwei1", cangwei1);
			entity.put("thqsdxz", ++thqsdxz);
			entity.put("bzh", detailService.getOrderDetailByYdbhidAndYdxzh(ydbhid, receipt.getYdxzh().intValue()).getBzh());
			deliveryOperateMapper.insertIntoTHQSD(entity);
			entity.put("zhl", fenliZhl.subtract(receipt.getZhl()));
			entity.put("tiji", fenliTiji.subtract(receipt.getTiji()));
			entity.put("jianshu", fenliJianshu - receipt.getJianshu());
			entity.put("yiti", 1);
			deliveryOperateMapper.modifyT_fenlikucun(entity);
			Integer cxflag = deliveryOperateMapper.getCxFlagFromT_QSByYdbhid(ydbhid);
			if(cxflag == null || cxflag == 0) {
				TransportSignRecord transportSignRecord = new TransportSignRecord();
				transportSignRecord.setYdbhid(ydbhid);
				Date oldTime = new Date(new Date().getTime()+1000*60);
				transportSignRecord.setOldQsTime(oldTime);
				transportSignRecord.setRecordQsTime(oldTime);
				transportSignRecord.setLrr(entity.getString("username"));
				signRecordService.modifyTransportSignRecordByYdbhid(transportSignRecord);
			}
			
		}
		JsonResult result = JsonResult.getConveyResult("200", "提货单保存成功");
		result.put("thqshdid", thqshdid);
		String id = "";
		result.put("id", id = deliveryOperateMapper.getT_CWSJ_MASTERIdByydbhid(ydbhid) == null ? "":id);
		return result;
	}

	/**
	 * {
	 * 	"ywlx":"业务类型",
	 * 	"fhdwmch":"发货单位",
	 * 	"fhdwlxdh":"发货电话",
	 * 	"shhrmch":"收货单位"
	 * 	"shlxr":"送货联系人",
	 * 	"shhrdzh":"送货地址",
	 * 	"shhrlxdh":"送货电话",
	 * 	"kdtime":"开单时间"
	 * 	"jhshtime":"计划送货时间",
	 * 	"thtime":"提货时间",
	 * 	"zxbz":"装卸班组",
	 * 	"cgyqm":"仓管员签名",
	 * 	"common":"备注"
	 * 	"xuhaos":[
	 * 			"324342","23423","234234234"
	 * 		]
	 * }
	 * 
	 * @see
	 * @return
	 */
	@Override
	@Transactional
	public JsonResult savedeliverydocuments(RequestJsonEntity entity) {
		Integer rightNum = rightMapper.getRightNum(AuthorticationConstant.PC_SHOUT,entity.getString("account"));
		if(rightNum == null || rightNum < 1) {
      return JsonResult.getConveyResult("400", AuthorticationConstant.MESSAGE);
    }

		List<String> xuhaos = JSON.parseArray(entity.getString("xuhaos"), String.class);
		String company = entity.getString("company");
		entity.put("id", deliveryOperateMapper.getMaxT_CAR_OUTByCompany(company));
		
		if(com.alibaba.dubbo.common.utils.StringUtils.isBlank(entity.getString("kdtime"))) {
      entity.put("kdtime", new Date());
    }
		if(org.apache.commons.lang.StringUtils.isBlank(entity.getString("jhshtime")))//如果传入空值,就给个null.防止到数据库中是一个1970年的数据
    {
      entity.put("jhshtime", null);
    }

		String ydbhid = null;
		TransportOrder order = null;
		for (String xuhao : xuhaos) {
			BundleReceipt receipt = receiptService.getBundleReceiptByXuhao(xuhao);
			if(ydbhid == null ) {
        ydbhid = receipt.getYdbhid();
      } else if(!ydbhid.equals(receipt.getYdbhid())) {
        throw new BusinessException("数据有误,选中的运单号不一致");
      }
			if(order == null) {
        order = orderService.getTransportOrderByYdbhid(ydbhid);
      }

			Integer ydxzh = receipt.getYdxzh();

			if(DeliveryNumber(ydbhid, ydxzh.toString()) ==1 ) {
        throw new BusinessException("系统检测到运单号" + ydbhid+ "细则号" + ydxzh + "相应的提货单已生成，禁止继续生成！");
      }
			
			List<FenliKucunEntity> fenlikucunList = adjunctSomethingService.queryFenliKucunEntity(ydbhid, ydxzh.intValue(), company);
			if(fenlikucunList.isEmpty()) {
				throw new BusinessException("该运单没有"+company+"公司的分理库存");
			}
			Integer fenliJianshu = 0;
			BigDecimal fenliTiji = new BigDecimal(0);
			BigDecimal fenliZhl = new BigDecimal(0);
			for(FenliKucunEntity fenli:fenlikucunList) {
				fenliJianshu += Integer.parseInt(fenli.getJianshu());
				fenliTiji = fenliTiji.add(new BigDecimal(fenli.getTiji()));
				fenliZhl = fenliZhl.add(new BigDecimal(fenli.getZhl()));
			}
			if(fenliTiji.compareTo(receipt.getTiji()) == -1 ||	
					fenliZhl.compareTo(receipt.getZhl()) == -1 || fenliJianshu < receipt.getJianshu()) {
        throw new BusinessException("提货数量有错！");
      }
			
			deliveryOperateMapper.modifyHczzqdSourceYitiByXuhao(xuhao,2);

			String cangwei = deliveryOperateMapper.getCangweiByYdbhid(ydbhid,entity.getString("company"));
			if(org.apache.commons.lang.StringUtils.isNotEmpty(cangwei)) {
        entity.put("beizhu", entity.getString("beizhu")+" 货物仓位"+ cangwei );
      }
			entity.put("ydbhid", ydbhid);
			entity.put("yshhm", order.getYshhm());
			entity.put("dzshhd", order.getDzshhd());
			entity.put("tiebieshuoming", order.getTiebieshuoming());
			entity.put("pinming", receipt.getPinming());
			deliveryOperateMapper.insertIntoT_CAR_OUT_Detail2(entity);

			entity.put("ydxzh", ydxzh);
			entity.put("chxh", receipt.getChxh());
			entity.put("isfd", order.getIsfd());
			entity.put("xuhao", receipt.getXuhao());
			entity.put("zhl", receipt.getZhl());
			entity.put("tiji", receipt.getTiji());
			entity.put("jianshu", receipt.getJianshu());
			deliveryOperateMapper.insertIntoT_CAR_OUT_DETAIL1(entity);

			entity.put("zhl", fenliZhl.subtract(receipt.getZhl()));
			entity.put("tiji",fenliTiji.subtract(receipt.getTiji()));
			entity.put("jianshu", fenliJianshu - receipt.getJianshu());
			entity.put("yiti", 2);
			deliveryOperateMapper.modifyT_fenlikucun(entity);
		}
		deliveryOperateMapper.insertIntoT_CAR_OUT(entity);			
		//		deliveryOperateMapper.modifyT_QSFlag(ydbhid,0+"");
		//		deliveryOperateMapper.deleteSignHWYD_ROUTEByYdbhid(ydbhid);
		JsonResult result = JsonResult.getConveyResult("200", "保存成功");
		result.put("id",entity.getString("id"));
		return result;
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public JsonResult billOfdeliverymanage(BillOfdeliveryRequestEntity entity) throws ParameterException,BusinessException,Exception{
		if("总公司".equals(entity.getGs()) )		//总公司可以查询所有
    {
      entity.setGs("");
    }
		Page<BillOfdeliveryEntity> page = deliveryOperateMapper.pagebillOfdeliverymanage(entity,new RowBounds(entity.getPageNums(), entity.getPageSizes()));
		JsonResult result = JsonResult.getConveyResult("200", "查询成功");
		List  resultList = new ArrayList<>(page.getPageSize());
		ObjectMapper objectMapper = UtilHelper.getObjectMapperWithDateFormat(CommonDateUtil.PATTERN1);
		for (BillOfdeliveryEntity billOfdeliveryEntity : page) {
			Map map = objectMapper.convertValue(billOfdeliveryEntity, Map.class);
			resultList.add(map);
		}
		result.put("collection", resultList);
		result.put("total", page.getTotal());
		return result;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<HashMap<String,Object>> deliverydocumentsmanage(RequestJsonEntity entity) throws ParameterException,Exception {
		Integer rightNum = rightMapper.getRightNum(AuthorticationConstant.PC_SHOUT_QRY,entity.getString("account"));
		if(rightNum == null || rightNum < 1) {
      throw new ParameterException(AuthorticationConstant.MESSAGE);
    }
		if("总公司".equals(entity.getString("company")))		//总公司可以查询所有
    {
      entity.put("company", "");
    }
		entity.put("yipai", FUNC.apply(entity.getString("yipai")));
		entity.put("endtime", DateUtils.fromDays(entity.getString("endtime"),1));
		entity.put("starttime", DateUtils.fromDays(entity.getString("starttime"),-1));
		List<DeliveryOperateEntity> page = deliveryOperateMapper.pagedeliverydocumentsmanage(entity);
		List<HashMap<String,Object>> list = new ArrayList<>();
		ObjectMapper objectMapper = UtilHelper.getObjectMapperWithDateFormat(CommonDateUtil.PATTERN1);
		for (DeliveryOperateEntity deliveryOperateEntity : page) {
			HashMap<String,Object> map = objectMapper.convertValue(deliveryOperateEntity, HashMap.class);
			map.put("pcyes", deliveryOperateEntity.getPcyes() == 1 ?"已派车":"未派车");
			Integer pszhsh = deliveryOperateEntity.getPszhsh();
			map.put("pszhsh", pszhsh == null ? "": ( pszhsh == 1?"假日运达(1)":"平日运达(0)"));
			list.add(map);
		}
		return list;
	}
	
	@Override
	public List<HashMap<String, Object>> deliverydocumentsmanageForDriver(RequestJsonEntity entity) throws ParameterException{
		Integer rightNum = rightMapper.getRightNum(AuthorticationConstant.PC_SHOUT_QRY,entity.getString("account"));
		if(rightNum == null || rightNum < 1) {
      throw new ParameterException(AuthorticationConstant.MESSAGE);
    }
		entity.put("yipai", FUNC.apply(entity.getString("yipai")));
		entity.put("endtime", DateUtils.fromDays(entity.getString("endtime"),1));
		entity.put("starttime", DateUtils.fromDays(entity.getString("starttime"),-1));
		List<HashMap<String, Object>> page = deliveryOperateMapper.pagedeliverydocumentsmanageForDriver(entity);
		for (HashMap<String, Object> submap : page) {
			String ydbhid = String.valueOf(submap.get("ydbhid"));
			submap.put("ModeOfTransportation", deliveryOperateMapper.getYsfsByydbhid(ydbhid));
			Double chargeableTotalWeight = 0.00;
			Double chargeableTotalVolume = 0.00;
			for (TransportOrderDetail detail : detailService.queryTransportOrderDetailByYdbhid(ydbhid)) {
				Integer jffs = detail.getJffs();
				if(jffs == 0) {
          chargeableTotalWeight += detail.getZhl();
        }
				if(jffs == 1) {
          chargeableTotalVolume += detail.getTiji();
        }
				if(jffs == 2 ) {
					if(detail.getTiji()<0){//体积小于0
						chargeableTotalWeight += detail.getZhl();
					}else {
						if ((detail.getZhl()-detail.getTiji()) < 0.3) {
              chargeableTotalVolume += detail.getTiji();
            } else {
              chargeableTotalWeight += detail.getZhl();
            }
					}
				}
			}
			submap.put("ChargeableTotalWeight", chargeableTotalWeight);
			submap.put("ChargeableTotalVolume", chargeableTotalVolume);
			String pcyes = String.valueOf(submap.get("pcyes"));
			if(com.alibaba.dubbo.common.utils.StringUtils.isBlank(pcyes)) {
        submap.put("pcyes","未派车");
      } else {
        submap.put("pcyes",Integer.valueOf(pcyes) ==1 ?"已派车":"未派车") ;
      }
			Integer pszhsh = (Integer)submap.get("pszhsh");
			
			submap.put("pszhsh", pszhsh == null ? "": ( pszhsh == 1?"假日运达(1)":"平日运达(0)"));
		}
		
		return CommonDateUtil.FormatDate(page);
	}
	
	@Override
	@Transactional
	public void cancelBillOfdelivery(RequestJsonEntity entity) throws ParameterException,Exception {
		Integer rightNum = rightMapper.getRightNum(AuthorticationConstant.TMSWEIHUMEN_1,entity.getString("account"));
		if(rightNum == null || rightNum < 1) {
      throw new ParameterException(AuthorticationConstant.MESSAGE);
    }
		String gs = entity.getString("gs");
		String ydbhid = entity.getString("ydbhid");
		Integer qszt = adjunctSomethingService.isReceivedByYdbhid(ydbhid);
		if(qszt != null && qszt > 0) {
      throw new ParameterException("运单号:"+ydbhid+"存在签收状态请先撤销签收再执行撤销");
    }
		//开删
		deliveryOperateMapper.deleteTHQSDByYdbhidAndGs(ydbhid,gs);
		
		//删除在途表:hwyd_route
		deliveryOperateMapper.deleteArriveLoadingHWYD_ROUTEByYdbhid(ydbhid,gs);
		//更新库存
		for (TransportOrderDetail detail : detailService.queryTransportOrderDetailByYdbhid(ydbhid)) {
			deliveryOperateMapper.modifyT_fenlikucunByTransportOrderDetail(detail,gs);
		}
		//更新装载表yiti字段
		deliveryOperateMapper.modifyHczzqdSourceYitiByYdbhidAndGs(ydbhid,gs,0);
		//修改运单表的到站库存状态
		deliveryOperateMapper.modifyHwydYxzt(ydbhid);
		//更新签收表
		deliveryOperateMapper.modifyT_QSInit(ydbhid);
		//插入在途表当前状态
		HwydRoute hwydRoute = new HwydRoute()
				.setCztype("已撤销签收")
				.setShiJian(new Date())
				.setYdbhid(ydbhid)
				.setGrid(entity.getString("username"));
		signRecordService.addHwydRoute(hwydRoute);
		entity.put("logtype", "撤销提货签收单");
		entity.put("menu", "撤销提货单");
		entity.put("content", new StringBuilder().append("运单编号为:")
				.append(ydbhid)
				.append("的撤销提货操作"));
		exceptionLogService.CancelLogInsert(entity);
	}

	/**
	 * 页面传参三个:
	 * 	id:送货单号
	 * 	gsid:送货单的公司id
	 * 	gs:送货单的公司
	 * @see
	 * <p>
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2018-01-30 21:13:41
	 * @param entity
	 * @throws ParameterException
	 * @throws Exception
	 */
	@Override
	public void canceldeliverydocuments(RequestJsonEntity entity)throws ParameterException,Exception {
		Integer rightNum = rightMapper.getRightNum(AuthorticationConstant.TMSWEIHUMEN_1,entity.getString("account"));
		if(rightNum == null || rightNum < 1) {
      throw new ParameterException(AuthorticationConstant.MESSAGE);
    }
		String id = entity.getString("id");
		String gsid = entity.getString("gsid");
		String gs = entity.getString("gs");
		String ydbhid = deliveryOperateMapper.getYDBHIDFromT_CAR_OUTByIdAndGsid(id,gsid);
		Integer qszt = adjunctSomethingService.isReceivedByYdbhid(ydbhid);
		if(qszt != null && qszt > 0) {
      throw new ParameterException("运单号:"+ydbhid+"存在签收状态请先撤销签收再执行撤销");
    }
		if(StringUtils.isEmpty(ydbhid)) {
      throw new ParameterException("参数有误,不存在的数据");
    }
		
		deliveryOperateMapper.deleteT_CAR_OUTByIdAndGsid(id,gsid);
		deliveryOperateMapper.deleteT_CAR_OUTDetail1ByIdAndGsid(id,gsid);
		deliveryOperateMapper.deleteT_CAR_OUTDetail2ByIdAndGsid(id,gsid);
		deliveryOperateMapper.deleteT_CAR_OUTDetail3ByIdAndGsid(id,gsid);
		deliveryOperateMapper.deleteT_CAR_OUTDetail4ByIdAndGsid(id,gsid);
		deliveryOperateMapper.deleteT_CAR_OUTDetail5ByIdAndGsid(id,gsid);
	
		
		//删除在途表:hwyd_route
		deliveryOperateMapper.deleteArriveLoadingHWYD_ROUTEByYdbhid(ydbhid,gs);
		//更新库存
		for (TransportOrderDetail detail : detailService.queryTransportOrderDetailByYdbhid(ydbhid)) {
			deliveryOperateMapper.modifyT_fenlikucunByTransportOrderDetail(detail,gs);
		}
		//更新装载表yiti字段
		deliveryOperateMapper.modifyHczzqdSourceYitiByYdbhidAndGs(ydbhid,gs,0);
		//修改运单表的到站库存状态
		deliveryOperateMapper.modifyHwydYxzt(ydbhid);
		//更新签收表
		deliveryOperateMapper.modifyT_QSInit(ydbhid);
		//插入在途表当前状态
		HwydRoute hwydRoute = new HwydRoute()
				.setCztype("已撤销签收")
				.setShiJian(new Date())
				.setYdbhid(ydbhid)
				.setGrid(entity.getString("username"));
		signRecordService.addHwydRoute(hwydRoute);
		entity.put("logtype", "撤销送货签收单");
		entity.put("menu", "撤销送货单");
		entity.put("content", new StringBuilder().append("运单编号为:")
				.append(ydbhid)
				.append("的撤销送货操作"));
		entity.put("ydbhid", ydbhid);
		exceptionLogService.CancelLogInsert(entity);
	}

	@SuppressWarnings({ "rawtypes", "unchecked"})
	@Override
	public JsonResult searchBillOfdelivery(RequestJsonEntity entity) {
		List<HashMap<String, Object>> resultList = deliveryOperateMapper.searchBillOfdelivery(entity);
		List<HashMap> list =new ArrayList<>();
		HashMap<String,Object> resultmap = null;
		for (HashMap<String, Object> submap : resultList) {
			Object cangwei = submap.get("cangwei");
			if(cangwei == null  || "null".equals(cangwei)) {
        submap.put("cangwei", "");
      }
			HashMap<String, Object> currentMap = CommonDateUtil.FormatDate(submap);
			if(resultmap == null) {
        resultmap = (HashMap<String, Object>) currentMap.clone();
      }
			list.add(currentMap);
		}
		resultmap.put("sub", list);
		//修改jira：952引起的查询字段显示问题:将开票人从工号——>姓名
		String kpr = String.valueOf(resultmap.get("kpr"));
		Employee employee = adjunctSomethingService.getEmployeeByNumber(kpr);
		if(employee != null) {
      resultmap.put("kpr", employee.getEmplyeeName());
    }
		JsonResult res = JsonResult.getConveyResult("200", "查询成功");
		res.put("reason", resultmap);
		return res;
	}
	/**
	 {
	 	"thqshdid":23243
	 	"thrmch": "张三",
    	"thrsfzhm":"********",
    	"shhrsfzhm":"dfsdfa",
    	"kpr":"李四",
    	"beizhu2":"sfsdfjl"
	 	"list":[
	 		{
	 			"xuhao":3223,"cangwei1":"普陀"
	 		}
	 	]
	 	
	 	2018-02-28:修改参数多加一个shijian;
	 	"shijian":2018-02-28
	 }
	 * @see
	 * <p>
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2018-02-02 09:33:23
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JsonResult modifyBillOfdelivery(RequestJsonEntity entity) {
		List<Map<String,Object>> list = (ArrayList<Map<String,Object>>)entity.get("list");
		for (Map<String, Object> map : list) {
			 entity.put("xuhao", map.get("xuhao").toString());
		     entity.put("cangwei1", map.get("cangwei1").toString());
		     deliveryOperateMapper.modifyTHQSD(entity);
		}
		return JsonResult.getConveyResult("200", "修改提货单成功");
	}
	
}
