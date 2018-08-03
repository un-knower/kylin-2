package com.ycgwl.kylin.transport.service.impl;

import com.ycgwl.kylin.AuthorticationConstant;
import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.entity.*;
import com.ycgwl.kylin.transport.persistent.AdjunctSomethingMapper;
import com.ycgwl.kylin.transport.persistent.ArriveStationMapper;
import com.ycgwl.kylin.transport.persistent.TransportRightMapper;
import com.ycgwl.kylin.transport.persistent.UndoLoadingMapper;
import com.ycgwl.kylin.transport.service.api.*;
import com.ycgwl.kylin.util.IPUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.ycgwl.kylin.util.CommonDateUtil.HalfMonthWithIn;

@Service("kylin.transport.dubbo.local.undoLoadingService")
public class UndoLoadingServiceImpl implements UndoLoadingService{
	
	private final static String OPERATING_MENU = "撤销装载";
	
	@Resource
	private BundleReceiptService receiptService;
	@Resource
	private AdjunctSomethingService adjunctSomethingService;
	@Resource
	private UndoLoadingMapper undoLoadingMapper;
	@Resource
	private ITransportOrderService orderService;
	@Resource
	private AdjunctSomethingMapper somethingMapper;
	
	@Resource
	private IExceptionLogService exceptionLogService; 
	
	@Resource
	private ArriveStationMapper arriveStationMapper;

	/** 调用一下签收的mapper */
	@Resource
	private ITransportSignRecordService signRecordService;

	@Resource
	private TransportRightMapper rightMapper;
	
	@Override
	public JsonResult queryBundle(String ydbhid) throws Exception {
		List<BundleReceipt> receiptList = receiptService.queryBundleReceiptByYdbhidDesc(ydbhid);
		if(CollectionUtils.isEmpty(receiptList)) 
			return JsonResult.getConveyResult("400", "不存在该运单的装载清单");
		TransportOrder order = orderService.getTransportOrderByYdbhid(ydbhid);
		receiptList.forEach(bundleReceipt->{
			bundleReceipt.setYdbeginplace(order.getFazhan());
			bundleReceipt.setYdendplace(order.getDaozhan());
		});
		JsonResult result = JsonResult.getConveyResult("200", "查询成功");
		result.put("data", receiptList);
		return result;
	}

	/**
	 * @see com.ycgwl.kylin.transport.service.api.UndoLoadingService#deleteBundle(java.lang.String, java.lang.String, java.lang.String, java.lang.String[])
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017-12-20 13:17:58
	 * @param company
	 * @param account
	 * @param grid
	 * @param xuhaos
	 * @return
	 * @throws ParameterException
	 * @throws Exception
	 */
	@Override
	@Transactional
	public JsonResult deleteBundle(String company,String account,String grid,List<String> xuhaos) throws ParameterException,BusinessException,Exception{
		 Integer rightNum = rightMapper.getRightNum(AuthorticationConstant.HCZZQD_CANCEL, account);
		 if(rightNum == null || rightNum <1) 
			 throw new BusinessException(AuthorticationConstant.MESSAGE);
		 
		 //根据运单号查询出所有的装载清单
		 List<BundleReceipt> allBundleList = null;
		 boolean isNewBill = true;
		 for (String xuhao : xuhaos) {
			 BundleReceipt receipt = receiptService.getBundleReceiptByXuhao(xuhao);
			 String ydbhid = receipt.getYdbhid();
			 if(allBundleList == null) {
				 allBundleList = receiptService.queryLastBundleReceiptByYdbhid(ydbhid);
				 if(allBundleList==null || allBundleList.isEmpty()) {//该运单的装载清单list不空
					 break;
				 }
				for (BundleReceipt bundleReceipt : allBundleList) {
					isNewBill = (bundleReceipt.getNewbill()!=null&&bundleReceipt.getNewbill()==1)?true:false;//通过装载清单序号来查分理库存
					if(isNewBill == false) break;//只要有一个不是就不是
				}
			 }
			Integer isReceived = adjunctSomethingService.isReceivedByYdbhid(ydbhid);
			if(isReceived !=null && isReceived > 0){	
				throw new ParameterException("已签收运单无法撤销");	
			}
			Integer checkuserdelrec = adjunctSomethingService.checkuserdelrec(ydbhid);
			if(checkuserdelrec == 1){
				throw new ParameterException("存在提货单或送货派车单，请先撤销提货单或送货派车单"); 
			}
			if(rightNum ==1 && !HalfMonthWithIn(receipt.getFchrq(), 15L))	
				throw new ParameterException("权限不足,仅能撤销15天之内的装载记录"); 
			
			if(receipt.getYdzh()>0) 
				throw new ParameterException("该装载已到站："+receipt.getDaozhan()+"，请联系到站公司做撤销到货后再撤销装载!");
			//时间:2018-01-31:增加一个逻辑判断,如果有做过提货/送货单的话不可以撤销
			Integer countTiHuodan = undoLoadingMapper.CountTiHuodan(ydbhid);
			Integer countSonghuoHuodan = undoLoadingMapper.CountSonghuoHuodan(ydbhid);
			if(countTiHuodan != null && countTiHuodan >0)
				throw new ParameterException("该运单已经生成提货签收单,无权限删除!");
			if(countSonghuoHuodan != null && countSonghuoHuodan >0)
				throw new ParameterException("该运单已经生成送货签收单,无权限删除!");
			//所有判断已经结束,正式开始删除装载清单操作
			//1.恢复库存表的数据
//				String zhchrq = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(receipt.getZhchrq());
//				List<BundleReceipt> bundleList = receiptService.queryBundleReceiptByYdbhidTime(ydbhid,zhchrq,receipt.getChxh());
//				for (BundleReceipt entity : bundleList) {
//					if(receipt.getYdbhid().equals(ydbhid) && entity.getYdxzh()==receipt.getYdxzh()) {
//						count+=1;
//					}
//				}
			if(receipt.getiType()==2) {		//提货
				try {
					adjunctSomethingService.plusKucun(receipt);
				} catch (Exception e) {
					throw new BusinessException("撤回的库存超出原有的数量，不予处理"); 
				}
			}else {
				if(isNewBill) {//使用序号来操作
					if("1".equals(receipt.getParentXuhao())) {	//只有一个装载清单，撤销以后只能到库存
						try {
							adjunctSomethingService.plusKucun(receipt);
						} catch (Exception e) {
							throw new BusinessException("撤回的库存超出原有的数量，不予处理"); 
						}
					}else {
						String parentXuhao[] = receipt.getParentXuhao().split(",");
						if(parentXuhao.length==1) {//父装载只有一个，那只要把重量、体积、件数加到父分理库存
							adjunctSomethingService.plusFenliKucunByXuhao(receipt,Integer.parseInt(parentXuhao[0]));//还原库存给上一个装载后到货的分理库存
						}else {
							BigDecimal tiji = receipt.getTiji();
							Integer jianshu = receipt.getJianshu();
							BigDecimal zhl = receipt.getZhl();
							//要撤销的装载的体积重量件数
							BigDecimal willDelTiji = receipt.getTiji();
							Integer willDelJianshu = receipt.getJianshu();
							BigDecimal willDelZhl = receipt.getZhl();
							for(String xhao:parentXuhao) {
								BundleReceipt rpt = receiptService.findBundleReceiptByXuhao(Integer.parseInt(xhao));
								willDelTiji = willDelTiji.add(rpt.getTiji());
								willDelJianshu = willDelJianshu+rpt.getJianshu();
								willDelZhl = willDelZhl.add(rpt.getZhl());
							}
							if(willDelTiji.compareTo(tiji)==0 && willDelZhl.compareTo(zhl)==0 && willDelJianshu.equals(jianshu)) {//分装的多个合并为1个一起装完的情况的撤销分离库存
								for(String xhao:parentXuhao) {
									BundleReceipt rpt = receiptService.findBundleReceiptByXuhao(Integer.parseInt(xhao));
									adjunctSomethingService.plusFenliKucunByXuhao(rpt,Integer.parseInt(xhao));
								}
							}else {
								//分装以后再分装
								for(String xhao:parentXuhao) {
									BundleReceipt rpt = receiptService.findBundleReceiptByXuhao(Integer.parseInt(xhao));
									List<FenliKucunEntity> list = arriveStationMapper.queryFenlikucunByXuhao(Integer.parseInt(xhao));
									FenliKucunEntity entity = list.get(0);
									BigDecimal needBackTiji = rpt.getTiji().subtract(new BigDecimal(entity.getTiji()));
									BigDecimal needBackZhl = rpt.getZhl().subtract(new BigDecimal(entity.getZhl()));
									Integer needBackJianshu = rpt.getJianshu()-Integer.parseInt(entity.getJianshu());
									rpt.setTiji(needBackTiji);
									rpt.setJianshu(needBackJianshu);
									rpt.setZhl(needBackZhl);
									adjunctSomethingService.plusFenliKucunByXuhao(rpt,Integer.parseInt(xhao));
								}
							}
						}
					}
				}else {
					Integer fenliCount = somethingMapper.countFenliKucunEntity(ydbhid, receipt.getYdxzh().intValue(), receipt.getFazhan());
					if(fenliCount==0) {	//没有查询到发站的分理库存
						try {
							adjunctSomethingService.plusKucun(receipt);
						} catch (Exception e) {
							throw new BusinessException("撤回的库存超出原有的数量，不予处理"); 
						}
					}else {
						adjunctSomethingService.plusFenliKucun(receipt);
					}
				}
			}
		
			//2.删除装载相关表的数据
			undoLoadingMapper.deleteHwydRoute(receipt.getXuhao(),ydbhid);
			undoLoadingMapper.deleteHczzqdSourceByXuhao(xuhao);
			undoLoadingMapper.deleteHczzqdBeizhuByXuhao(xuhao);
			//03-16 , 经财务统一,可以删除成本明细
			undoLoadingMapper.deleteIncome_DByxuhao(xuhao);
			
			UndoSignLog undoSignLog = new UndoSignLog();
			undoSignLog.setYdbhid(ydbhid);
			undoSignLog.setOpeXm(grid);
			undoSignLog.setOpeGrid(account);
			undoSignLog.setOpeType("撤消装载操作");
			Date now = new Date();
			undoSignLog.setOpeDate(now);
			undoSignLog.setRecgenDate(now);
			signRecordService.addUndoSignLog(undoSignLog);
			
			//新增异常日志信息
			ExceptionLog exceptionLog = new ExceptionLog();
			exceptionLog.setOperatorName(grid);
			exceptionLog.setOperatorAccount(account);
			exceptionLog.setOperatorCompany(company);
			exceptionLog.setIpAddress(IPUtil.getLocalIP());
			exceptionLog.setYdbhid(ydbhid);
			exceptionLog.setOperatingMenu(OPERATING_MENU);
			StringBuilder sb = new StringBuilder("");
			sb.append("运单编号为：").append(ydbhid).append("的装载记录撤销！");
			exceptionLog.setOperatingContent(sb.toString());
			exceptionLog.setOperatingTime(now);
			exceptionLog.setCreateName(grid);
			exceptionLog.setCreateTime(now);
			exceptionLog.setUpdateName(grid);
			exceptionLog.setUpdateTime(now);
			
			try {
				exceptionLogService.addExceptionLog(exceptionLog);
			} catch (Exception e) {			
				throw new BusinessException("新增异常日志信息失败", e);
			}
		 }
		return JsonResult.getConveyResult("200", "撤销成功");
	}

	@Transactional
	public JsonResult superdeleteBundle(String company, String account, String grid, List<String> xuhaos) {
		xuhaos.forEach(xuhao->{
			BundleReceipt receipt = receiptService.getBundleReceiptByXuhao(xuhao);
			String ydbhid = receipt.getYdbhid();
			Integer isReceived = adjunctSomethingService.isReceivedByYdbhid(ydbhid);
			
			if(isReceived !=null && isReceived > 0)	
				throw new ParameterException("运单:"+ ydbhid + "已签收运单无法撤销");	
			if(adjunctSomethingService.checkuserdelrec(ydbhid) == 1)
				throw new ParameterException("存在提货单或送货派车单，请先撤销提货单或送货派车单"); 
			if(receipt.getYdzh() != null && receipt.getYdzh()>0) 
				throw new ParameterException("运单已经到货,请联系到站公司做撤销到货操作后再进行装载撤销!");
			//时间:2018-01-31:增加一个逻辑判断,如果有做过提货/送货单的话不可以撤销
			Integer countTiHuodan = undoLoadingMapper.CountTiHuodan(ydbhid);
			Integer countSonghuoHuodan = undoLoadingMapper.CountSonghuoHuodan(ydbhid);
			if(countTiHuodan != null && countTiHuodan >0)
				throw new ParameterException("该运单已经生成提货签收单,无权限删除!");
			if(countSonghuoHuodan != null && countSonghuoHuodan >0)
				throw new ParameterException("该运单已经生成送货签收单,无权限删除!");
			//所有判断已经结束,正式开始删除装载清单操作
			//1.恢复库存表的数据
			if(receipt.getiType()==2) {		//提货
				adjunctSomethingService.plusKucun(receipt);
			}else {									//干线或者配送一致
				List<FenliKucunEntity> fenlikucunList = adjunctSomethingService.queryFenliKucunEntity(ydbhid, receipt.getYdxzh().intValue(), receipt.getFazhan());
				if(!fenlikucunList.isEmpty()) {
					adjunctSomethingService.plusFenliKucun(receipt);
				}else {
					adjunctSomethingService.plusKucun(receipt);
				}
			}
		
			//2.删除装载相关表的数据
			undoLoadingMapper.deleteHwydRoute(receipt.getXuhao(),ydbhid);
			undoLoadingMapper.deleteHczzqdSourceByXuhao(xuhao);
			undoLoadingMapper.deleteHczzqdBeizhuByXuhao(xuhao);
			//03-16 , 经财务统一,可以删除成本明细
			undoLoadingMapper.deleteIncome_DByxuhao(xuhao);
			
			UndoSignLog undoSignLog = new UndoSignLog();
			undoSignLog.setYdbhid(ydbhid);
			undoSignLog.setOpeXm(grid);
			undoSignLog.setOpeGrid(account);
			undoSignLog.setOpeType("撤消装载操作");
			Date now = new Date();
			undoSignLog.setOpeDate(now);
			undoSignLog.setRecgenDate(now);
			signRecordService.addUndoSignLog(undoSignLog);
			
			//新增异常日志信息
			ExceptionLog exceptionLog = new ExceptionLog();
			exceptionLog.setOperatorName(grid);
			exceptionLog.setOperatorAccount(account);
			exceptionLog.setOperatorCompany(company);
			exceptionLog.setIpAddress(IPUtil.getLocalIP());
			exceptionLog.setYdbhid(ydbhid);
			exceptionLog.setOperatingMenu(OPERATING_MENU);
			StringBuilder sb = new StringBuilder("");
			sb.append("运单编号为：").append(ydbhid).append("的装载记录撤销！");
			exceptionLog.setOperatingContent(sb.toString());
			exceptionLog.setOperatingTime(now);
			exceptionLog.setCreateName(grid);
			exceptionLog.setCreateTime(now);
			exceptionLog.setUpdateName(grid);
			exceptionLog.setUpdateTime(now);
			
			try {
				exceptionLogService.addExceptionLog(exceptionLog);
			} catch (Exception e) {			
				throw new BusinessException("新增异常日志信息失败", e);
			}
			
		});
		return JsonResult.getConveyResult("200", "撤销成功");
	}

	public static void main(String[] args) {
		Short a = 1,b = 1;
		System.out.println(a== b);
		System.out.println(a.equals(b));
	}
}
