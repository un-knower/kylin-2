package com.ycgwl.kylin.transport.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.ycgwl.kylin.AuthorticationConstant;
import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.entity.Page;
import com.ycgwl.kylin.entity.PageInfo;
import com.ycgwl.kylin.entity.RequestJsonEntity;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.dto.ConsignorDeliveryInstructDto;
import com.ycgwl.kylin.transport.entity.*;
import com.ycgwl.kylin.transport.entity.adjunct.Customer;
import com.ycgwl.kylin.transport.entity.adjunct.ReleaseWaiting;
import com.ycgwl.kylin.transport.persistent.*;
import com.ycgwl.kylin.transport.service.api.*;
import com.ycgwl.kylin.transport.vo.PhotoUrlAndNameVo;
import com.ycgwl.kylin.transport.vo.PhotoVo;
import com.ycgwl.kylin.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service("kylin.transport.dubbo.local.transportOrderService")
public class TransportOrderServiceImpl implements ITransportOrderService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private final static String OPERATING_MENU = "运单作废";

	@Resource
	private TransportOrderMapper transportOrderMapper;
	@Resource
	private TransportOrderDetailService orderDetailService;
	@Resource
	private FinanceCertifyMapper financeCertifyMapper;
	@Resource
	private AdjunctSomethingMapper adjunctSomethingMapper;
	@Resource
	private AdjunctSomethingService adjunctSomethingService;
	@Resource
	private BundleReceiptMapper bundleReceiptMapper;
	@Resource
	TransportOrderCancelMapper transportOrderCancelMapper;
	@Resource
	public TransportRightMapper transportRightMapper;
	
	@Resource
	public ISendMessageLogService sendMessageLogService;

	@Resource
	IExceptionLogService exceptionLogService;
	
	@Resource  
    private DataSourceTransactionManager transactionManager;
	
	@Resource
	private TransportWaybillService transportWaybillService;


	

	@Override
	@Deprecated
	public PageInfo<TransportOrderResult> pageTransportOrderRevision1(TransportOrderSearch transportOrderSearch,
			int pageNum, int pageSize) {
		Page<TransportOrderResult> page = transportOrderMapper.queryTransportOrderPage(transportOrderSearch,
				new RowBounds(pageNum, pageSize));
		return new PageInfo<>(page);
	}

	public TransportOrderCancelResult uf_set_editmode(TransportOrderCancelResult cancel, int ii_right) {
		// 页面按钮控制
		if (cancel.getCancelStatus() == TransportOrderCancelResult.CANCEL_STATUS_YES) {
			cancel.setCancelButton(false);// 设置作废按钮灰色不可以点击
			if (ii_right == 3) {
				cancel.setResumeButton(true);// 设置取消按钮可以点击
				cancel.setCanEditDesc(true);// 备注是否可以修改
			} else {
				cancel.setResumeButton(false);// 设置取消按钮可以点击
				cancel.setCanEditDesc(false);
			}
		} else {
			cancel.setResumeButton(false);
			if (ii_right == 1) {
				cancel.setCancelButton(false);// 设置取消按钮可以点击
				cancel.setCanEditDesc(false);// 备注是否可以修改
			} else if (ii_right == 2) {
				cancel.setCancelButton(true);// 设置取消按钮可以点击
				cancel.setCanEditDesc(true);// 备注是否可以修改
			} else if (ii_right == 3) {
				cancel.setCancelButton(true);// 设置取消按钮可以点击
				cancel.setCanEditDesc(true);// 备注是否可以修改
			}
		}
		return cancel;
	}

	public JsonResult getResult(TransportOrderCancelResult entity, String resultCode, String reason) {
		JsonResult result = new JsonResult();
		result.put("resultCode", resultCode);
		result.put("reason", reason);
		result.put("entity", entity);
		return result;
	}

	/**
	 * 作废货物运单查询
	 */
	@Override
	public JsonResult findCancelTransportOrder(TransportOrderCancelResult cancel, String curUserGrid) {
		int li_count;
		if (cancel == null) {
			return getResult(cancel, "400", "传入对象为空");
		}
		String ls_ydbhid = cancel.getTransportCode();// 界面输入的运单编号

		// 重置页面：运单编号、发站、状态、备注
		cancel.setResumeButton(false);
		cancel.setCancelButton(false);

		// 检查和校验运单号
//		Pattern patternH = Pattern.compile("H[0-9]{1,10}");
//		Pattern pattern = Pattern.compile("[0-9]{1,11}");
//		if ("远成储运".equals(cancel.getOrigStation()) || "总公司".equals(cancel.getOrigStation())) {// 判断发站是否是这2个公司
//			if (ls_ydbhid.length() <= 11) {// 如果运单<=11位，就在首位补全0
//				ls_ydbhid = CommonUtil.completeFirstTransportCode(ls_ydbhid, 10);
//			} else {
//				return getResult(cancel, "400", ls_ydbhid + "该运单编号格式不正确！");
//			}
//		} else if ((patternH.matcher(ls_ydbhid).matches() || pattern.matcher(ls_ydbhid).matches())
//				&& ls_ydbhid.length() <= 11) {// 除首位可能是字母，其他应都是数字
//			ls_ydbhid = CommonUtil.completeFirstTransportCode(ls_ydbhid, 10);
//		} else {
//			return getResult(cancel, "400", ls_ydbhid + "该运单编号格式不正确！");
//		}

		if (!"所有公司".equals(cancel.getCompanyName())) {
			li_count = transportOrderCancelMapper.countTransportOrderByCompanyName(ls_ydbhid, cancel.getCompanyName());
		} else {
			li_count = transportOrderCancelMapper.countTransportOrderByCode(ls_ydbhid);
		}
		if (li_count == 0) {
			return getResult(cancel, "400", ls_ydbhid + "该货物运单不存在或者发站不是" + cancel.getCompanyName());
		}

		// 若货物已发运，则提示不能作废
		li_count = transportOrderCancelMapper.countBundleReceiptByTransportCode(ls_ydbhid);
		if (li_count > 0) {
			return getResult(cancel, "400", ls_ydbhid + "该货物已发运，其货物运单不能作废！");
		}

		// 查询该运单号是否生成财凭（FIWT）
		li_count = transportOrderCancelMapper.countTransportFinanceByTransportCode(ls_ydbhid);
		if (li_count > 0) {
			return getResult(cancel, "400", ls_ydbhid + "该货物运单已生成财凭，请先冲红财凭再作废！");
		}
		List<TransportOrderCancelResult> resultList = null;
		if (!"所有公司".equals(cancel.getCompanyName())) {
			resultList = transportOrderCancelMapper.findTransportOrderCancelByCompany(ls_ydbhid,
					cancel.getCompanyName());
		} else {
			resultList = transportOrderCancelMapper.findTransportOrderCancel(ls_ydbhid);
		}
		String ls_fazhan = transportOrderCancelMapper.findFazhanByTransportCode(ls_ydbhid);
		cancel.setOrigStation(ls_fazhan);
		cancel.setTransportCode(ls_ydbhid);
		if (resultList.isEmpty()) {
			cancel.setCanEditDesc(true);
			cancel.setCancelButton(true);
			cancel.setResumeButton(false);
			cancel.setCancelStatus(TransportOrderCancelResult.CANCEL_STATUS_NO);
		} else if (resultList.get(0).getCancelStatus() == 1) {// 已经做了作废
			cancel.setCanEditDesc(false);
			cancel.setCancelButton(false);
			cancel.setResumeButton(true);
			cancel.setDescription(resultList.get(0).getDescription());
			cancel.setCancelStatus(TransportOrderCancelResult.CANCEL_STATUS_YES);
			return getResult(cancel, "400", ls_ydbhid + "该票已经被作废!");
		} else if (resultList.get(0).getCancelStatus() == 0) {
			cancel.setCanEditDesc(true);
			cancel.setCancelButton(true);
			cancel.setResumeButton(false);
			cancel.setDescription(resultList.get(0).getDescription());
			cancel.setCancelStatus(TransportOrderCancelResult.CANCEL_STATUS_NO);
		}
		return getResult(cancel, "200", "查询成功!");
	}

	/**
	 * 作废货物运单（作废）
	 */
	@Override
	@Transactional
	public JsonResult saveCancelWaybill(TransportOrderCancelResult cancel, String curUserGrid, String userName) {
		if (cancel == null) {
			return getResult(cancel, "400", "传入的信息不能为空");
		}
		cancel.setCzygh(curUserGrid);
		List<TransportOrderCancelResult> resultList = transportOrderCancelMapper
				.findTransportOrderCancel(cancel.getTransportCode());
		if (resultList.isEmpty()) {// 如果表里没有做过操作就insert
			transportOrderCancelMapper.saveCancelTransportOrder(cancel);
			transportOrderCancelMapper.saveCancelTransportOrderLog(cancel);
		} else {// 如果表里有做过废除操作就update
			if (TransportOrderCancelResult.CANCEL_STATUS_NO == cancel.getCancelStatus()) {// 取消作废
				cancel.setDescription(null);
				cancel.setCzygh(null);
				transportOrderCancelMapper.updateCancelTransportOrder(cancel);
				cancel.setCancelStatus(TransportOrderCancelResult.CANCEL_STATUS_OTHER);
				transportOrderCancelMapper.saveCancelTransportOrderLog(cancel);
				cancel.setCancelStatus(TransportOrderCancelResult.CANCEL_STATUS_NO);
				transportOrderCancelMapper.updateTransportCancelStatus(cancel.getTransportCode(),
						TransportOrderCancelResult.CANCEL_STATUS_NO);
			} else {
				transportOrderCancelMapper.updateCancelTransportOrder(cancel);
				transportOrderCancelMapper.saveCancelTransportOrderLog(cancel);
				// 通过触发器去维护，保证TMS里操作也同步transportOrderCancelMapper.updateTransportCancelStatus(cancel.getTransportCode(),TransportOrderCancelResult.CANCEL_STATUS_YES);
			}
		}
		if (cancel.getCancelStatus() == TransportOrderCancelResult.CANCEL_STATUS_NO) {
			cancel.setCancelButton(true);
			cancel.setCanEditDesc(true);
			cancel.setResumeButton(false);
		} else {
			cancel.setCancelButton(false);
			cancel.setCanEditDesc(false);
			cancel.setResumeButton(true);
		}

		// 新增异常日志信息
		ExceptionLog exceptionLog = new ExceptionLog();
		exceptionLog.setOperatorName(userName);
		exceptionLog.setOperatorAccount(curUserGrid);
		exceptionLog.setOperatorCompany(cancel.getCompanyName());
		exceptionLog.setIpAddress(IPUtil.getLocalIP());
		exceptionLog.setYdbhid(cancel.getTransportCode());
		exceptionLog.setOperatingMenu(OPERATING_MENU);
		String operatingContent = "编号为：" + cancel.getTransportCode() + "的运单作废！";
		exceptionLog.setOperatingContent(operatingContent);
		exceptionLog.setOperatingTime(new Date());
		exceptionLog.setCreateName(userName);
		exceptionLog.setCreateTime(new Date());
		exceptionLog.setUpdateName(userName);
		exceptionLog.setUpdateTime(new Date());

		try {
			exceptionLogService.addExceptionLog(exceptionLog);
		} catch (Exception e) {
			throw new BusinessException("新增异常日志信息失败", e);
		}
		return getResult(cancel, "200", "操作成功!");
	}

	@Override
	public Collection<TransportOrder> listTransportOrderByYdbhids(Collection<String> ydbhidList) {
		if (ydbhidList != null && ydbhidList.isEmpty()) {
			Collection<TransportOrder> selectTransportOrderByYdbhidList = transportOrderMapper
					.queryTransportOrderByYdbhids(ydbhidList);
			for (TransportOrder transportOrder : selectTransportOrderByYdbhidList) {
				if (transportOrder.getBeizhu() == null || transportOrder.getBeizhu() == "") {
					transportOrder.setBeizhu("无");
				}
				String ydbhid = transportOrder.getYdbhid();
				if (StringUtils.isNotEmpty(ydbhid)) {
					TransportOrderDetail transportOrderDetail = orderDetailService.getWayBillByYdbhid(ydbhid);
					if (transportOrderDetail != null) {
						transportOrder.setFhzhl(transportOrderDetail.getZhl());
						transportOrder.setTijihj(transportOrderDetail.getTiji());
						transportOrder.setJshhj(transportOrderDetail.getJianshu());
					}
				}
			}
			return selectTransportOrderByYdbhidList;
		}
		return Collections.emptyList();
	}

	@Override
	public PageInfo<TransportOrder> pageTransportOrderNotBundle(TransportOrder transportOrder, int pageNum,
			int pageSize) {
		if (transportOrder == null) {
			transportOrder = new TransportOrder();
		}
		if (transportOrder.getFhshj() == null) {
			// 发货时间为30天之前的
			transportOrder.setFhshj(org.apache.commons.lang.time.DateUtils.addDays(new Date(), -30));
		}
		Page<TransportOrder> page = transportOrderMapper.queryTransportOrderNotBundle(transportOrder,
				new RowBounds(pageNum, pageSize));

		return new PageInfo<>(page);
	}

	@Override
	public Collection<TransportOrder> listTransportOrderNotBundle(TransportOrder transportOrder) {
		if (transportOrder == null) {
			transportOrder = new TransportOrder();
		}
		if (transportOrder.getFhshj() == null) {
			// 发货时间为30天之前的
			transportOrder.setFhshj(org.apache.commons.lang.time.DateUtils.addDays(new Date(), -30));
		}
		return transportOrderMapper.queryTransportOrderNotBundle(transportOrder);
	}

	@Override
	public TransportOrder getTransportOrderByYdbhid(String ydbhid) {
		List<TransportOrder> list = transportOrderMapper.get(ydbhid);
		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}

	@Override
	public FinanceCertify getFinanceCertifyByYdbhid(String ydbhid) {
		return financeCertifyMapper.getFinanceCertifyByYdbhid(ydbhid);
	}

	public void addDefault(FinanceCertify financeCertify) {

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

	@Deprecated
	@Override
	public PageInfo<TransportOrderResult> pageTransportOrder(TransportOrderSearch transportOrderSearch, int pageNum,
			int pageSize) throws ParameterException, BusinessException {

		transportOrderSearch.setPageNums(pageNum);
		transportOrderSearch.setPageSizes(pageSize);

		// 查询分页总条数
		Long total = transportOrderMapper.queryTransportOrderPagesCount(transportOrderSearch);

		Collection<TransportOrderResult> result = new ArrayList<TransportOrderResult>();
		if (total != 0) {
			// 查询分页返回数据
			result = transportOrderMapper.queryTransportOrderPages(transportOrderSearch);
		}

		// 设置分页返回信息
		int startRow = 0;
		int endRow = 0;
		int pages = 0;
		if (pageNum > 0 && pageSize > 0) {
			startRow = (pageNum - 1) * pageSize;
			pages = (int) Math.ceil((double) total / pageSize);
		}
		endRow = startRow + pageSize;

		Page<TransportOrderResult> page = new Page<TransportOrderResult>(pageNum, pageSize, startRow, endRow, total,
				pages, result);
		return new PageInfo<TransportOrderResult>(page);
	}

	@Override
	public JsonResult checkOutConvey(RequestJsonEntity map) throws SQLServerException {
		// 校验运单
		JsonResult result = JsonResult.getConveyResult("200", "校验成功");
		String companyName = map.getString("company");
		String ydbhid = map.getString("ydbhid");
		String fhshj = map.getString("fhshj");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Date now = new Date();
		Date sendTime = null;
		try {
			sendTime = sdf.parse(fhshj);
		} catch (ParseException e1) {
			result.put("fhshj", fhshj+"时间格式不正确，请使用yyyy/MM/dd HH:mm的格式");
		}
		
		if(sendTime!=null) {
			 int days = (int) ((now.getTime() - sendTime.getTime()) / (1000*3600*24));
			 if(now.getTime()<sendTime.getTime()+1800*1000) {
				 result.put("fhshj", "托运时间不能大于当前系统时间");
			 }
			 if(days>3) {
				 result.put("fhshj", "托运时间不能比当前系统时间往前3天以上");
			 }
		}
		
		try {
			adjunctSomethingService.checkKhmessage(map.getString("khbm"), map.getString("fhdwmch"), companyName);
		} catch (ParameterException e) {
			String tipMessage = e.getTipMessage();
			if (tipMessage.indexOf("客户名称不存在") != -1)
				result.put("fhdwmch", tipMessage);
			result.put("khbm", tipMessage);
		}
		// second step: check for ydbhid
		if (StringUtils.isEmpty(ydbhid))
			result.put("ydbhid", adjunctSomethingService.createYdbhid("H", map.getString("grid")));
		else if (getTransportOrderByYdbhid(ydbhid) != null)
			result.put("ydbhid", "运单号" + ydbhid + "已存在!");

		// finally step: check for daozhan
		Collection<String> daozhanList = adjunctSomethingService.listStationCompanys().stream()
				.map(company -> company.getCompanyName()).collect(Collectors.toList());

		if (!daozhanList.contains(map.getString("daozhan")))
			result.put("daozhan", "到站不存在");

		return result;

	}

	/**
	 * 生成运输号码
	 * 
	 * @throws ParseException
	 */
	private String createYshhm(BatchTransportOrderEntity entity) throws ParseException {

		Date date = CommonDateUtil.StringToDate(entity.getFhshj());
		String dateString = CommonDateUtil.DateToString(date, "MMdd");// 取发货时间的月和日
		String fazhanShort = adjunctSomethingMapper.getCompanyByName(entity.getFazhan()).getCompanyShort();
		// 获取到站简称
		String daozhanShort = adjunctSomethingMapper.getCompanyByName(entity.getDaozhan()).getCompanyShort();
		return fazhanShort + daozhanShort + dateString + "-" + entity.getYdbhid();
	}

	/** 转化成运单 */
	private TransportOrder createTransportV2(BatchTransportOrderEntity entity, String company,String account)
			throws IllegalAccessException, InvocationTargetException, ParseException {
		TransportOrder order = new TransportOrder();
		BeanUtils.copyPropertiesContainDate(order, entity);
		Integer dsk = (int)Math.round(entity.getReceipt());
		order.setDashoukuan(dsk);
		String shrProvinces = entity.getShrProvinces();
		order.setDashoukuanYuan(entity.getReceipt());
		String yshhm = createYshhm(entity);
		order.setYshhm(yshhm);
		order.setZhipiao(account);
		order.setJshhj("取整".equals(entity.getIsRound())?0:1);

		// 从客户名称和客户编码获取客户信息放入运单的信息中
		Customer customer = adjunctSomethingService.checkKhmessage(entity.getKhbm(), entity.getFhdwmch(), company);
		order.setKhbm(customer.getCustomerKey());
		order.setFhdwdzh(customer.getAddress());
		order.setFhdwlxdh(customer.getPhone());
		order.setFhdwyb(customer.getMobile());
		order.setFhdwmch(customer.getCustomerName());
		//order.setJshhj(1);		//默认不取整

		if (StringUtils.isNotBlank(shrProvinces)) {
			String[] split = shrProvinces.split("-");
			if (split.length == 1) {
				order.setDhAddr(split[0].trim());
			} else if (split.length == 2) {
				order.setDhAddr(split[0].trim());
				order.setDhChengsi(split[1].trim());
			} else {
				order.setDhShengfen(split[0].trim());
				order.setDhChengsi(split[1].trim());
				order.setDhAddr(split[2].trim());
			}
		}
		String fwfs = entity.getFwfs().trim();
		switch (fwfs) {
		case "仓到站":
			order.setFwfs(0);
			break;
		case "仓到仓":
			order.setFwfs(1);
			break;
		case "站到仓":
			order.setFwfs(2);
			break;
		case "站到站":
			order.setFwfs(3);
			break;
		default:
			order.setFwfs(-1);
		}
		String isfd = entity.getIsfd();
		switch (isfd) {
		case "普通返单":
			order.setIsfd(1);
			break;
		case "电子返单":
			order.setIsfd(2);
			break;
		default:
			order.setIsfd(0);
		}
		return order;
	}

	private FinanceCertify createFinanceCertify(BatchTransportOrderEntity entity, FinanceStandardPrice price,String username) {
		FinanceCertify certify = new FinanceCertify();
		certify.setConveyKey(entity.getYdbhid());
		certify.setLightprice(entity.getLightprice());
		certify.setPiecework(entity.getPiecework());
		certify.setReceipt(entity.getReceipt());
		certify.setDelivery(entity.getDelivery());
		certify.setInvoice(entity.getInvoice());
		certify.setOtherFeeName("其他费用");
		certify.setOther(entity.getOther());
		certify.setTohome(entity.getTohome());
		certify.setPremiumRate(entity.getPremiumRate());
		certify.setWeightprice(entity.getWeightprice());
		certify.setJshhj("取整".equals(entity.getIsRound())?0:1);//是否取整
		
		//使用对象中的公式获取合计金额
		FinanceReceiveMoneyPrint print = new FinanceReceiveMoneyPrint();
		print.setBaolu(entity.getPremiumRate());//保率
		print.setJshhj("取整".equals(entity.getIsRound())?0:1);//是否取整
		print.setZhhyj(entity.getWeightprice());//重货运价
		print.setQhyj(entity.getLightprice());//轻货运价
		print.setAjyj(entity.getPiecework());//按件运价
		print.setJffs("重货".equals(entity.getJffs())?0:("轻货".equals(entity.getJffs())?1:("按件".equals(entity.getJffs())?2:0)));//计费方式
		print.setBdf(entity.getInvoice());//办单费
		print.setDsk(entity.getReceipt());//代收款
		print.setTbje(entity.getTbje());//投保金额
		print.setJianshu(entity.getJianshu());//件数
		print.setQtfy(entity.getOther());//其他费用
		print.setTiji(entity.getTiji());//体积
		print.setShshmf(entity.getDelivery());//送货上门费
		print.setShmshhf(entity.getTohome());//上门取货费
		print.setZhl(entity.getZhl());//重量
		print.setTiji(entity.getTiji());//体积
		print.setJianshu(entity.getJianshu());//件数
		Double totalCost = print.totalFee();
		certify.setCost(totalCost);//合计费用
		entity.setCost(totalCost);
		// 到站付款需设置 还是发站付
		if (certify.getReceipt() == 0) {// 如果代收款=0
			// 到站付款
			if ((entity.getDzfk() != null && entity.getDzfk() == 1)
					&& (entity.getFzfk() == null || entity.getFzfk() == 0)) {//只勾选到付
				certify.setDeliveryCash(certify.getCost());// 代收款
				certify.setDeliveryCashStatus(1);// 货到付款状态
				certify.setReceiveLackMoney(0.00);
				certify.setReceiveMoneyStatus(0);// 是否款清
				certify.setChuna(username);
			} else {// 匹配只勾选了发付 或者 发付到付都勾选了
				// 发站付款
				certify.setDeliveryCash(0.00);// 货到付款
				certify.setDeliveryCashStatus(0);// 货到付款状态
				certify.setReceiveLackMoney(certify.getCost());
				certify.setReceiveMoneyStatus(1);// 是否款清
				certify.setChuna("");
			}
			certify.setReceiptStatus(0);
		} else {
			if ((entity.getDzfk() != null && entity.getDzfk() == 1)
					&& (entity.getFzfk() == null || entity.getFzfk() == 0)) {
				certify.setDeliveryCash(certify.getCost() - certify.getReceipt());// 合计金额-代收款
				certify.setDeliveryCashStatus(1);// 货到付款状态
				certify.setReceiveLackMoney(0.00);
				certify.setReceiveMoneyStatus(0);// 是否款清
				certify.setChuna(username);
			} else {
				certify.setDeliveryCash(0.00);// 货到付款
				certify.setDeliveryCashStatus(0);// 货到付款状态
				certify.setReceiveLackMoney(certify.getCost() - certify.getReceipt());// 合计金额-代收款
				certify.setReceiveMoneyStatus(1);// 是否款清
				certify.setChuna("");
			}
			certify.setReceiptStatus(1);// 代收款状态
		}
		
		// 持久化财凭
		if (price != null) {
			//certify.setPremiumRate(price.getPremiumRate());
			certify.setLightHandlingPrice(price.getQhzhxfdj());
			certify.setHeavyHandlingPrice(price.getZhzhxfdj());
			certify.setForkliftPrice(price.getZhjxzyf());
		}
		certify.setReceiveLackMoney(certify.getCost()-certify.getReceipt()-certify.getDeliveryCash());
		return certify;
	}

	/**
	 * 生成运单明细
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private TransportOrderDetail createDeatail(BatchTransportOrderEntity entity)
			throws IllegalAccessException, InvocationTargetException {
		TransportOrderDetail detail = new TransportOrderDetail();
		BeanUtils.copyPropertiesContainDate(detail, entity);
		detail.setYdxzh(1);
		String jffs = entity.getJffs();
		switch (jffs) {
		case "重货":
			detail.setJffs(0);
			break;
		case "轻货":
			detail.setJffs(1);
			break;
		case "按件":
			detail.setJffs(2);
		}
		detail.setYfh(false);
		detail.setIsKaiyun((short) 0);
		return detail;
	}

	@Override
	@Transactional(timeout = 600, rollbackFor = Exception.class)
	public void saveConveyItemByItem(BatchTransportOrderEntity entity, FinanceStandardPrice price, String company,String account,String username)
			throws ParameterException, BusinessException, Exception {
		Function<Integer, Boolean> fun = (rea -> {
			return rea != null && rea == 1;
		});
		Boolean apply = fun.apply(entity.getReleaseWaiting()); // 等待发货通知
		TransportOrder order = createTransportV2(entity, company,account);
		order.setCwpzhy(1);
		transportOrderMapper.insert(order);
		ReleaseWaiting releaseWaiting = new ReleaseWaiting().setYdbhid(entity.getYdbhid()).setFazhan(entity.getFazhan())
				.setDdfhczshijian(new Date()).setDdfhczygh(entity.getZhipiao());
		if (apply)
			releaseWaiting.setDdfhzt(1).setTzfhzt(1);
		else
			releaseWaiting.setDdfhzt(0).setTzfhzt(0);
		adjunctSomethingMapper.insertReleaseWaiting(releaseWaiting);
		// 插明细
		TransportOrderDetail detail = createDeatail(entity);
		orderDetailService.addTransportOrderDetail(detail);
		
		if("是".equals(entity.getWithFinance())) {//是否录入财务凭证
			FinanceCertify certify = createFinanceCertify(entity, price,username);
			financeCertifyMapper.callProcedureInsert(certify);
			if (StringUtils.isNotBlank(certify.getMessage()))
				throw new BusinessException(certify.getMessage());
		}
		List<TransportOrderDetail> detailList = new ArrayList<TransportOrderDetail>();
		detailList.add(detail);
		order.setDetailList(detailList);
		sendMessageLogService.savePhoneSms(order, company);
	}

	@Override
	public PageInfo<TransportOrderResult> pageTransportOrderSecondRevision(TransportOrderSearch transportOrderSearch,
			int pageNum, int pageSize) throws ParameterException, BusinessException {
		transportOrderSearch.setPageNums(pageNum);
		transportOrderSearch.setPageSizes(pageSize);

		// 查询分页总条数
		Long total = 0l;
		try {
			total = transportOrderMapper.queryTransportOrderPagesCount(transportOrderSearch);
		} catch (Exception e) {
			throw new BusinessException("查询运单总条数失败", e);
		}

		Collection<TransportOrderResult> result = new ArrayList<TransportOrderResult>();
		if (total != 0) {
			try {
				// 查询分页返回数据
				result = transportOrderMapper.listTransportOrderPages(transportOrderSearch);
			} catch (Exception e) {
				throw new BusinessException("查询运单信息失败", e);
			}
		}

		if (!result.isEmpty() && result.size() > 0) {
			// 遍历查询结果添加明细及财凭信息
			for (TransportOrderResult transportOrderResult : result) {
				// 添加明细统计信息
				TransportOrderDetail transportOrderDetail = new TransportOrderDetail();
				try {
					// 查询运单明细数据
					transportOrderDetail = orderDetailService.getWayBillByYdbhid(transportOrderResult.getYdbhid());
				} catch (Exception e) {
					throw new BusinessException("查询运单信息失败", e);
				}
				if (transportOrderDetail != null) {
					transportOrderResult.setZhl(transportOrderDetail.getZhl());
					transportOrderResult.setTiji(transportOrderDetail.getTiji());
					transportOrderResult.setJianshu(transportOrderDetail.getJianshu());
				}

				// 添加财凭信息
				FinanceCertifyResult financeCertifyResult = new FinanceCertifyResult();
				try {
					// 查询财凭数据
					financeCertifyResult = financeCertifyMapper
							.getCwpzhbhAndHjjeByYdbhid(transportOrderResult.getYdbhid());
				} catch (Exception e) {
					throw new BusinessException("查询运单信息失败", e);
				}
				if (financeCertifyResult != null) {
					transportOrderResult.setCwpzhbh(financeCertifyResult.getCwpzhbh());
					transportOrderResult.setHjje(financeCertifyResult.getHjje());
				}
			}
		}		
			
		// 设置分页返回信息
		int startRow = 0;
		int endRow = 0;
		int pages = 0;
		if (pageNum > 0 && pageSize > 0) {
			startRow = (pageNum - 1) * pageSize;
			pages = (int) Math.ceil((double) total / pageSize);
		}
		endRow = startRow + pageSize;

		Page<TransportOrderResult> page = new Page<TransportOrderResult>(pageNum, pageSize, startRow, endRow, total,
				pages, result);
		return new PageInfo<TransportOrderResult>(page);
	}

	@Override
	public boolean isTransportExistByCode(String transportCode) {
		Integer count = transportOrderMapper.countTransportCode(transportCode);
		return count>0;
	}

	@Override
	public List<Map<String, Object>> exportTransportOrderList(TransportOrderSearch transportOrderSearch)
			throws ParameterException, BusinessException {
		Assert.notNull("transportOrderSearch", transportOrderSearch, "运单查询条件不能为空");
		//查询运单信息
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			result = transportOrderMapper.exportTransportOrderList(transportOrderSearch);
		} catch (Exception e) {
			throw new BusinessException("查询运单信息失败", e);
		}
		
		if(result.isEmpty() && result.size() == 0){
			throw new BusinessException("没有查询到运单信息");
		}
		
		result.forEach((Map<String, Object> l) -> {
			//绿色通道
			if(l.get("is_greenchannel") == null || l.get("is_greenchannel").toString() == "" || "0".equals(l.get("is_greenchannel").toString())){
				l.put("is_greenchannel", "否");
			}else if("1".equals(l.get("is_greenchannel").toString())){
				l.put("is_greenchannel", "是");
			}
			//发货时间
			if(l.get("fhshj") == null){
				l.put("fhshj", DateUtils.getCurrentHourAndMinutesTimeByStr(l.get("fhshj").toString()));
			}
			//是否生成财凭
			if(l.get("cwpzhy") == null || l.get("cwpzhy").toString() == "" || "0".equals(l.get("cwpzhy").toString())){
				l.put("cwpzhy", "否");
			}else if("1".equals(l.get("cwpzhy").toString())){
				l.put("cwpzhy", "是");
			}
			//业务类型
			if("0".equals(l.get("ywlx").toString())){
				l.put("ywlx", "普件（0）");
			}else if("1".equals(l.get("ywlx").toString())){
				l.put("ywlx", "快件(1)");
			}else if("2".equals(l.get("ywlx").toString())){
				l.put("ywlx", "特快(2)");
			}
			//计费方式
			if("0".equals(l.get("jffs").toString())){
				l.put("jffs", "重货");
			}else if("1".equals(l.get("jffs").toString())){
				l.put("jffs", "轻货");
			}else if("2".equals(l.get("jffs").toString())){
				l.put("jffs", "按件");
			}
			//计费重量
			if("0".equals(l.get("jffs").toString())){
				l.put("jfzl", Double.valueOf(l.get("zhl").toString()));
			}else if("1".equals(l.get("jffs").toString())){
				l.put("jfzl", 0);
			}else{
				if(Double.valueOf(l.get("tiji").toString()) <= 0){
					l.put("jfzl", Double.valueOf(l.get("zhl").toString()));
				}else{
					if(Double.valueOf(l.get("tiji").toString()) / Double.valueOf(l.get("tiji").toString()) >= 0.3){
						l.put("jfzl", Double.valueOf(l.get("zhl").toString()));
					}else{
						l.put("jfzl", 0);
					}
				}
			}
			//计费体积
			if("1".equals(l.get("jffs").toString())){
				l.put("jftj", Double.valueOf(l.get("tiji").toString()));
			}else if("0".equals(l.get("jffs").toString())){
				l.put("jftj", 0);
			}else{
				if(Double.valueOf(l.get("tiji").toString()) <= 0){
					l.put("jftj", 0);
				}else{
					if(Double.valueOf(l.get("tiji").toString()) / Double.valueOf(l.get("tiji").toString()) < 0.3){
						l.put("jftj", Double.valueOf(l.get("tiji").toString()));
					}else{
						l.put("jftj", 0);
					}
				}
			}
			//派送指示
			if(l.get("pszhsh") != null && "0".equals(l.get("pszhsh").toString())){
				l.put("pszhsh", "工作日内送货(0)");
			}else if(l.get("pszhsh") != null && "1".equals(l.get("ywlx").toString())){
				l.put("pszhsh", "节假日可送货(1)");
			}
			//服务方式
			if(l.get("fwfs") != null && "0".equals(l.get("fwfs").toString())){
				l.put("fwfs", "仓到站");
			}else if(l.get("fwfs") != null && "1".equals(l.get("fwfs").toString())){
				l.put("fwfs", "仓到仓");
			}else if(l.get("fwfs") != null && "2".equals(l.get("fwfs").toString())){
				l.put("fwfs", "站到仓");
			}else if(l.get("fwfs") != null && "3".equals(l.get("fwfs").toString())){
				l.put("fwfs", "站到站");
			}
			//付款方式
			if("f".equals(l.get("fkfs").toString())){
				l.put("fkfs", "发站付款");
			}else if("d".equals(l.get("fkfs").toString())){
				l.put("fkfs", "到站付款");
			}else if("s".equals(l.get("fkfs").toString())){
				l.put("fkfs", "双方付款");
			}else if("0".equals(l.get("fkfs").toString())){
				l.put("fkfs", "均不付款");
			}
			//等待指令放货
			if(l.get("pszhsh") != null && "0".equals(l.get("pszhsh").toString())){
				l.put("pszhsh", "等托指放货");
			}
		});			
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unused", "unchecked", "null" })
	@Override
	public JsonResult queryHistoryRecords(String account, ConsignorDeliveryInstructDto cdiDto) {
		// 判断是否有托运人发货指令权限
		Integer rightNum = transportRightMapper.getRightNum(AuthorticationConstant.HWYD_DDFH_LISHI,account);
		if (rightNum == null || rightNum < 1) {
			return JsonResult.getConveyResult("400", "你没有权限使用这个功能，请与系统管理员联系！");
		}
		TransportOrder transportOrder = null;
		if (!"".equals(cdiDto.getWaybillNum())) {
			// 查询货物运单表
			transportOrder = transportOrderMapper.getTransportOrderByYdbhid(cdiDto.getWaybillNum());
			
			//查询等拖放货表是否有数据
			ReleaseWaiting releaseWaiting = null;
			releaseWaiting = adjunctSomethingMapper.selectDdfhByYdbhid(cdiDto.getWaybillNum());
			if (null == releaseWaiting) {
				releaseWaiting = new ReleaseWaiting();
				releaseWaiting.setYdbhid(transportOrder.getYdbhid());
				releaseWaiting.setFazhan(transportOrder.getFazhan());
				releaseWaiting.setDdfhczshijian(null);
				releaseWaiting.setDdfhczygh(transportOrder.getZhipiao());
				releaseWaiting.setDdfhzt(0);// 不等待托运人放货通知
				releaseWaiting.setTzfhzt(0);// 通知状态0表示不通知
				// 持久化等待发货通知
				adjunctSomethingMapper.insertReleaseWaiting(releaseWaiting);
			}
		}
		
		
		
		Integer pageNum = cdiDto.getNum();
        Integer pageSize = cdiDto.getSize();
        com.github.pagehelper.Page<Object> page = PageHelper.startPage(pageNum, pageSize, true);
		List<Map<String, Object>> transportOrderList = transportOrderMapper.queryHistoryRecords(cdiDto);
		PageInfo pageInfo = new PageInfo(transportOrderList);
		if (null == transportOrderList || transportOrderList.size() <= 0) {
			return JsonResult.getConveyResult("200", "没有数据");
		}
		return JsonResult.getConveyResult("200", "查询成功", pageInfo);
	}

	@Override
	public JsonResult findWaitingForDelivery(String account, ConsignorDeliveryInstructDto cdiDto) {
		// 发货状态
		int deliveryStatus = 0;
		
		// 判断是否有设置托运人指令放货权限
		Integer rightNum = transportRightMapper.getRightNum(AuthorticationConstant.HWYD_DDFH_DENGDAI,account);
		if (rightNum == null || rightNum <1) {
			return JsonResult.getConveyResult("400", "你没有权限使用这个功能，请与系统管理员联系！");
		}
		
		// 获取运单号
		String waybillNum = cdiDto.getWaybillNum();
//		boolean matches = waybillNum.matches("^\\d{10,13}$");
//		if (!matches) {
//			return JsonResult.getConveyResult("400", "该运单编号格式不正确！");
//		}
		
		
		// 查询作废状态
//		int invalidatedState = transportOrderMapper.findInvalidatedState(waybillNum);
//		if (invalidatedState == 1) {
//			return new JsonResult("400", waybillNum + "该票货已作废，不能设置等待发货！");
//		}
//		if (invalidatedState < 1) {
//			return new JsonResult("400", waybillNum + "该票货不存在");
//		}
		
		// 查询此运单的数量
		int wayBillCount = transportOrderMapper.findWayBillCount(cdiDto);
		if (wayBillCount == 0) {
			return JsonResult.getConveyResult("400", waybillNum + "该货物运单不存在或者发站不是" + cdiDto.getCompanyName());
		}
		
		// 查询作废状态
		int invalidatedState = transportOrderMapper.findCancelState(waybillNum);
		if (invalidatedState == 1) {
			return JsonResult.getConveyResult("400", waybillNum + "该票货已作废，不能设置等待发货！");
		}
		
		// 查询提货签收单表此运单的数量
		int deliveryCount = transportOrderMapper.findDeliveryCount(cdiDto);
		if (deliveryCount > 0) {
			return JsonResult.getConveyResult("400", waybillNum + "该票货已提，不能设置等待发货！");
		}
		
		// 查询 送货派车单表 的此运单数量
		int sendCarCount = transportOrderMapper.findSendCarCount(cdiDto);
		if (sendCarCount > 0) {
			return JsonResult.getConveyResult("400", waybillNum + "该票货已送，不能设置等待发货！");
		}
		
		// 查询需要设置等待发货的运单
		List<ReleaseWaiting> releaseWaitingList = transportOrderMapper.findWaitingForDelivery(cdiDto);
		JSONObject jsonObject = new JSONObject();
		String station = transportOrderMapper.findStation(cdiDto);
		jsonObject.put("ydbhid", waybillNum);
		jsonObject.put("fazhan", station);
		jsonObject.put("ddfhzt", releaseWaitingList.size() == 0 ? 0 : releaseWaitingList.get(0).getDdfhzt());
		if (releaseWaitingList.size() == 0) {
			// 查询货物运单表的发站信息
		} else {
			deliveryStatus = releaseWaitingList.get(0).getTzfhzt();
			if (deliveryStatus == 1) {
				return JsonResult.getConveyResult("400", waybillNum + "该票货已通知发货，不能设置等待发货！");
			}
		}

		return JsonResult.getConveyResult("200", "查询成功", jsonObject);
	}

	@Transactional
	@Override
	public int updateDeliveryStatus(String account, ConsignorDeliveryInstructDto cdiDto) {
		cdiDto.setCurrentAcount(account);
		int count = transportOrderMapper.updateDeliveryStatus(cdiDto);
		return count;
	}

	@Transactional
	@Override
	public int saveDdfhLog(String account, ConsignorDeliveryInstructDto cdiDto) {
		cdiDto.setCurrentAcount(account);
		int count = transportOrderMapper.saveDdfhLog(cdiDto);
		return count;
	}

	@Override
	public JsonResult findNoticeDelivery(String account, ConsignorDeliveryInstructDto cdiDto) {
		// 判断是否有设置托运人指令放货权限
		Integer rightNum = transportRightMapper.getRightNum(AuthorticationConstant.HWYD_DDFH_FANGHUO,account);
		if (rightNum == null || rightNum < 1) {
			return JsonResult.getConveyResult("400", "你没有权限使用这个功能，请与系统管理员联系！");
		}
		
		// 获取运单号
		String waybillNum = cdiDto.getWaybillNum();
//		boolean matches = waybillNum.matches("^\\d{10,13}$");
//		if (!matches) {
//			return JsonResult.getConveyResult("400", "该运单编号格式不正确！");
//		}
		
		// 查询作废状态 
		int invalidatedState = transportOrderMapper.findCancelState(waybillNum);
		if (invalidatedState == 1) {
			return JsonResult.getConveyResult("400", waybillNum + "该票货已作废，不能设置等待发货！");
		}
//		int invalidatedState = transportOrderMapper.findInvalidatedState(waybillNum);
//		if (invalidatedState == 1) {
//			return new JsonResult("400", waybillNum + "该票货已作废，不能设置等待发货！");
//		}
//		if (invalidatedState < 1) {
//			return new JsonResult("400", waybillNum + "该票货不存在");
//		}
		
		// 查询此运单的数量
		int wayBillCount = transportOrderMapper.findWayBillCount(cdiDto);
		if (wayBillCount == 0) {
			return JsonResult.getConveyResult("400", waybillNum + "该货物运单不存在或者发站不是" + cdiDto.getCompanyName());
		}
		
		// 查看是否 已设置等待放货
		int count = transportOrderMapper.findEditDelivery(waybillNum);
		if (count == 0) {
			return JsonResult.getConveyResult("400", waybillNum + "该票货没有设置等待放货，不能通知发货!");
		}
		
		// 通知放货查询
		List<ReleaseWaiting> releaseWaitingList = transportOrderMapper.findNoticeDelivery(cdiDto);
		Integer ddfhzt = releaseWaitingList.get(0).getDdfhzt();
		if (ddfhzt == 0) {
			return JsonResult.getConveyResult("400", waybillNum + "该票货没有设置等待放货，不能通知发货!");
		}
		
		// 查询货物运单表的发站信息
		String station = transportOrderMapper.findStation(cdiDto);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ydbhid", waybillNum);
		jsonObject.put("fazhan", station);
		jsonObject.put("tzfhzt", releaseWaitingList.get(0).getTzfhzt());
		
		return JsonResult.getConveyResult("200", "查询成功", jsonObject);
		
	}

	@Override
	public JsonResult saveWaitingForDelivery(ReleaseWaiting releaseWaiting, String currentAccount, String ipAddr) {
		// 根据运单号查询该运单 
		TransportOrder transportOrder = transportOrderMapper.getTransportOrderByYdbhid(releaseWaiting.getYdbhid());
		// 获取运单录入人
		String zhipiao = transportOrder.getZhipiao();
		// 设置运单录入人
		releaseWaiting.setDdfhczygh(zhipiao);
		// 设置操作人的工号
		releaseWaiting.setXgrgonghao(currentAccount);
		// 设置操作人的ip
		releaseWaiting.setXgrjiqi(ipAddr);
		// 查询是否有等待放货表中是否有数据
		int findCount = transportOrderMapper.findDdfhCount(releaseWaiting.getYdbhid());
		int count = 0;
		if (findCount == 0) {
			// 等待放货保存
			count = transportOrderMapper.saveWaitingForDelivery(releaseWaiting);
		} else {
			// 更新等待放货
			count = transportOrderMapper.updateWaitingForDelivery(releaseWaiting);
		}
		
		if (1 == count) {
			return JsonResult.getConveyResult("200", "操作成功");
		}
		return JsonResult.getConveyResult("400", "操作失败");
	}

	@Override
	public String selectIsfdByYdbhid(String waybillNum) {
		return transportOrderMapper.selectIsfdByYdbhid(waybillNum);
	}

	/**
	 * 校验回单图片是否存在运单
	 */
	@Override
	public JsonResult checkReorder(List<PhotoVo> photoVolist) {
		for(int i=0; i<photoVolist.size(); i++) {
			photoVolist.get(i).getWaybillNum();
			TransportOrder transportOrder = transportOrderMapper.getTransportOrderByYdbhid(photoVolist.get(i).getWaybillNum());
			if (null == transportOrder) {
				photoVolist.get(i).setDescription("运单号不存在！");
			}else {
				photoVolist.get(i).setDescription("校验成功！");
			}
		}
		return JsonResult.getConveyResult("200", "操作成功",photoVolist);
	}

	@Transactional(timeout=70,rollbackFor=Exception.class)
	@Override
	public JsonResult saveReturnImageInfo(String account, Collection<HashMap<String, String>> photoinfo) throws Exception {
		// 保存回单信息
		HashMap<String,Object> map = new HashMap<>();
		map.put("account", account);
		map.put("photoinfo", photoinfo);
		transportOrderMapper.saveReturnImageInfo(map);
		// 修改回单的上传状态
		transportOrderMapper.updateReceiptStatus(photoinfo);
		return JsonResult.getConveyResult("200", "操作成功");
	}

	@Override
	public JsonResult selectReceipt(String ydbhid) {
		List<PhotoUrlAndNameVo> imgUrl = transportOrderMapper.selectReceipt(ydbhid);
		if (CollectionUtils.isEmpty(imgUrl)) {
			return JsonResult.getConveyResult("400", "未找到该回单");
		}
		return JsonResult.getConveyResult("200", "操作成功", imgUrl);
	}

}
