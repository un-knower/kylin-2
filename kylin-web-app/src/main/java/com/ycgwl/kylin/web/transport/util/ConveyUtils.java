package com.ycgwl.kylin.web.transport.util;

import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.entity.BatchTransportOrderEntity;
import com.ycgwl.kylin.transport.entity.FinanceCertify;
import com.ycgwl.kylin.transport.entity.TransportOrder;
import com.ycgwl.kylin.transport.entity.TransportOrderDetail;
import com.ycgwl.kylin.util.CommonDateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

/**
 * @Description: 运单使用的转换工具类
 * @author <a href="mailto:108252@ycgwl.com">万玉杰</a>
 * @date 2017年10月11日 上午9:53:45
 * @version 需求对应版本号
 *
 */
public class ConveyUtils {
	private static final String  FFFSERROR= "付费方式必须选择一项";

	private static final String  WEIGHT = "重货";

	private static final String  VOLUME = "体积";

	private static final String  PIECE = "按件";
	/**
	 * @Description: 
	 * @param exp
	 * @return
	 * @exception
	 */
	public static String getErrorField(Exception exp){
		if(exp instanceof ParameterException){
			String parameterName = ((ParameterException) exp).getParameterName();
			if(StringUtils.isNotBlank(parameterName)){
				String[] split = parameterName.trim().split("\\.");
				return split[split.length-1];
			}
			String tipMessage = ((ParameterException) exp).getTipMessage();
			if( FFFSERROR.equals(tipMessage)){
				//付费方式错误
				return "fffs";
			}
		}
		if(exp instanceof BusinessException){
			BusinessException businessException = (BusinessException) exp;
			String tipMessage = businessException.getTipMessage();
			if(tipMessage.startsWith("运单号") && tipMessage.endsWith("已经存在")){
				return "ydbhid";
			}
		}
		return "";
	}

	/**
	 * @Description: 将运单按照运单编号进行分类
	 * @param requestParam
	 * @return
	 * @exception
	 */
	public static Map<String,List<BatchTransportOrderEntity>> reserve(BatchTransportOrderEntity[] requestParam){
		Map<String,List<BatchTransportOrderEntity>> map = new HashMap<String,List<BatchTransportOrderEntity>>();
		for (BatchTransportOrderEntity batchTransportOrderEntity : requestParam) {
			if(map.containsKey(batchTransportOrderEntity.getYdbhid())){
				map.get(batchTransportOrderEntity.getYdbhid()).add(batchTransportOrderEntity);
			}else{
				List<BatchTransportOrderEntity> list = new ArrayList<BatchTransportOrderEntity>();
				list.add(batchTransportOrderEntity);
				map.put(batchTransportOrderEntity.getYdbhid(), list);
			}
		}
		return map;
	}

	public static TransportOrder getTransportOrder(BatchTransportOrderEntity entity){
		List<BatchTransportOrderEntity> list = new ArrayList<BatchTransportOrderEntity>();
		list.add(entity);
		return getTransportOrder(list);
	}
	/**
	 * @Description: 从一个批量运单的运单中获取运单基本信息
	 * @return
	 * @exception
	 */
	public static TransportOrder getTransportOrder(List<BatchTransportOrderEntity> list){
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		BatchTransportOrderEntity entity = list.get(0);
		TransportOrder order = new TransportOrder();
		order.setYdbhid(entity.getYdbhid());
		try {
			order.setFhshj(CommonDateUtil.StringToDate(entity.getFhshj()));
		} catch (ParseException e) {
			order.setFhshj(new Date());
		}
		order.setZhipiao(entity.getZhipiao());
		order.setFazhan(entity.getFazhan());
		order.setDaozhan(entity.getDaozhan());
		order.setBeginPlacename(entity.getBeginPlacename());
		order.setEndPlacename(entity.getEndPlacename());
		order.setFhdwmch(entity.getFhdwmch());
		order.setKhbm(entity.getKhbm());
		order.setFhdwdzh(entity.getFhdwdzh());
		order.setFhdwlxdh(entity.getFhdwlxdh());
		order.setFhdwyb(entity.getFhdwyb());
		order.setFhkhhy(entity.getFhkhhy());
		order.setShhrmch(entity.getShhrmch());
		order.setShhryb(entity.getShhryb());
		order.setDzshhd(entity.getDzshhd());

		String shrProvinces = entity.getShrProvinces();
		if(StringUtils.isNotBlank(shrProvinces)){
			String[] split = shrProvinces.split("-");
			if(split.length == 1){
				order.setDhAddr(split[0].trim());
			}else if(split.length == 2){
				order.setDhAddr(split[0].trim());
				order.setDhChengsi(split[1].trim());
			}else{
				order.setDhShengfen(split[0].trim());
				order.setDhChengsi(split[1].trim());
				order.setDhAddr(split[2].trim());
			}
		}

		order.setShhrdzh(entity.getShhrdzh());
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
		order.setYsfs(entity.getYsfs());
		order.setDaodatianshu(entity.getDaodatianshu());
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

		order.setFdyq(entity.getFdyq());
		order.setFzfk(entity.getFzfk());
		order.setDzfk(entity.getDzfk());
		order.setBaoxianfei(entity.getBaoxianfei());
		order.setBaozhuangfei(entity.getBaozhuangfei());
		order.setZhuangxiefei(entity.getZhuangxiefei());
		order.setBandanfei(entity.getBandanfei());
		order.setKhdh(entity.getKhdh());
		return order;
	}
	public static List<TransportOrderDetail> getOrderDetail(BatchTransportOrderEntity entity){
		List<BatchTransportOrderEntity> list = new ArrayList<BatchTransportOrderEntity>();
		list.add(entity);
		return getOrderDetail(list);
	}

	/**
	 * @Description: 从批量装载的一个运单中获取运单明细的信息
	 * @return
	 * @exception
	 */
	public static List<TransportOrderDetail> getOrderDetail(List<BatchTransportOrderEntity> list){
		List<TransportOrderDetail> detailList = new ArrayList<TransportOrderDetail>();
		for (BatchTransportOrderEntity entity : list) {
			TransportOrderDetail detail = new TransportOrderDetail();
			detail.setYdbhid(entity.getYdbhid());
			detail.setYdxzh(1);
			detail.setPinming(entity.getPinming());
			detail.setTiji(entity.getTiji());
			detail.setZhl(entity.getZhl());
			detail.setTbje(entity.getTbje());
			detail.setBzh(entity.getBzh());
			detail.setXh(entity.getXh());
			detail.setJianshu(entity.getJianshu());
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

			detail.setYunjia(entity.getYunjia());
			detailList.add(detail);
		}
		return detailList;
	}
	public static FinanceCertify getFinanceCertify(BatchTransportOrderEntity entity){
		List<BatchTransportOrderEntity> list = new ArrayList<BatchTransportOrderEntity>();
		list.add(entity);
		return getFinanceCertify(list);
	}
	/**
	 * @Description: 生成财凭
	 * @param list
	 * @return
	 * @exception
	 */
	public static FinanceCertify getFinanceCertify(List<BatchTransportOrderEntity> list){
		if(list == null || list.isEmpty()){
			return null;
		}
		BatchTransportOrderEntity entity = list.get(0);
		FinanceCertify certify = new FinanceCertify();
		certify.setConveyKey(entity.getYdbhid());
		certify.setLightprice(entity.getLightprice());
		certify.setPiecework(entity.getPiecework());
		certify.setReceipt(entity.getReceipt());
		certify.setDelivery(entity.getDelivery());
		certify.setInvoice(entity.getInvoice());
		certify.setOther(entity.getOther());
		certify.setTohome(entity.getTohome());
		certify.setWeightprice(entity.getWeightprice());
		//计算合计费用
		Double cost = 0.00;
		if(ConveyUtils.WEIGHT.equals(entity.getJffs())){//重货
			cost = entity.getZhl()*entity.getWeightprice()+
					entity.getReceipt()+entity.getInvoice()+entity.getTohome()+entity.getDelivery()
					+entity.getOther();
		}
		if(ConveyUtils.VOLUME.equals(entity.getJffs())){//轻货
			cost = entity.getTiji()*entity.getLightprice()+entity.getReceipt()
					+entity.getInvoice()+entity.getTohome()+entity.getDelivery()+entity.getOther();
		}
		if(ConveyUtils.PIECE.equals(entity.getJffs())){//按件
			cost =Integer.valueOf(entity.getJianshu())*entity.getPiecework()+entity.getReceipt()+
					entity.getInvoice()+entity.getTohome()+entity.getDelivery()+entity.getOther();
		}
		certify.setCost(cost);

		return certify;
	}


	//excel导出

	public static HSSFCellStyle getHssFFont(HSSFWorkbook workbook, Integer type){
		switch (type) {
		case 1:
			return createYellowCellStyle(workbook);
		case 2: 
			return createBrownCellStyle(workbook);
		default:
			return createblueCellStyle(workbook);
		}
	}
	/**
	 * 字体样式
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static HSSFFont createBlackFontStyle(HSSFWorkbook workbook){
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 10);
		font.setColor(HSSFColor.BLACK.index);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		return font;
	}
	/**
	 * 字体样式
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static HSSFFont createRedFontStyle(HSSFWorkbook workbook){
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 10);
		font.setColor(HSSFColor.RED.index);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		return font;
	}
	/**
	 * 设置黄色背景的单元格
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static HSSFCellStyle createYellowCellStyle(HSSFWorkbook workbook){
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		// 背景色
		style.setFillForegroundColor(HSSFColor.ORANGE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillBackgroundColor(HSSFColor.ORANGE.index);
		//设置字体
		return style;
	}
	/**
	 * 设置蓝色背景的单元格
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static HSSFCellStyle createblueCellStyle(HSSFWorkbook workbook){
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillBackgroundColor(HSSFColor.SKY_BLUE.index);
		//设置字体
		return style;
	}
	/**
	 * 设置棕色背景的单元格
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static HSSFCellStyle createBrownCellStyle(HSSFWorkbook workbook){
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		style.setFillForegroundColor(HSSFColor.BROWN.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillBackgroundColor(HSSFColor.BROWN.index);
		//设置字体
		return style;
	}


	public static void buildRow(HSSFRow row, BatchTransportOrderEntity entity, boolean flag) {
		int i = 0;
		HSSFCell cell = row.createCell(i++);
		cell.setCellValue(entity.getYdbhid());
		HSSFCell cell1 = row.createCell(i++);
		cell1.setCellValue(entity.getFazhan());
		HSSFCell cell2 = row.createCell(i++);
		cell2.setCellValue(entity.getDaozhan());
		HSSFCell cell3 = row.createCell(i++);
		cell3.setCellValue(entity.getBeginPlacename());
		HSSFCell cell4 = row.createCell(i++);
		cell4.setCellValue(entity.getEndPlacename());
		HSSFCell cell3_2 = row.createCell(i++);
		cell3_2.setCellValue(entity.getShhd());
		HSSFCell cell3_1 = row.createCell(i++);
		cell3_1.setCellValue(entity.getDzshhd());

		HSSFCell cell4_1 = row.createCell(i++);
		cell4_1.setCellValue(entity.getFhshj());

		HSSFCell cell5 = row.createCell(i++);
		cell5.setCellValue(entity.getFhdwmch());
		HSSFCell cell6 = row.createCell(i++);
		cell6.setCellValue(entity.getKhbm());
		//		HSSFCell cell7 = row.createCell(i++);
		//		cell7.setCellValue(entity.getFhdwdzh());
		//		HSSFCell cell8 = row.createCell(i++);
		//		cell8.setCellValue(entity.getFhdwyb());
		HSSFCell cell9 = row.createCell(i++);
		cell9.setCellValue(entity.getFhkhhy());
		//收货人信息
		HSSFCell cell10 = row.createCell(i++);
		cell10.setCellValue(entity.getShhrmch());
		HSSFCell cell11 = row.createCell(i++);
		cell11.setCellValue(entity.getShhryb());
		HSSFCell cell12 = row.createCell(i++);
		cell12.setCellValue(entity.getShrProvinces());
		HSSFCell cell13 = row.createCell(i++);
		cell13.setCellValue(entity.getShhrdzh());
		//运单运输的信息
		HSSFCell cell14 = row.createCell(i++);
		cell14.setCellValue(entity.getFwfs());

		HSSFCell cell14_2 = row.createCell(i++);
		cell14_2.setCellValue(entity.getHyy());
		HSSFCell cell14_3 = row.createCell(i++);
		Integer releaseWaiting = entity.getReleaseWaiting();
		if(releaseWaiting != null) {
      cell14_3.setCellValue(releaseWaiting);
    }
		HSSFCell cell14_4 = row.createCell(i++);
		cell14_4.setCellValue(entity.getYsfs());


		HSSFCell cell15 = row.createCell(i++);
		Integer daodatianshu = entity.getDaodatianshu();
		if(daodatianshu != null) {
      cell15.setCellValue(daodatianshu);
    }
		HSSFCell cell16 = row.createCell(i++);
		cell16.setCellValue(entity.getIsfd());
		HSSFCell cell17 = row.createCell(i++);
		cell17.setCellValue(entity.getFdyq());
		HSSFCell cell18 = row.createCell(i++);
		cell18.setCellValue(entity.getFffs());
		//		HSSFCell cell19 = row.createCell(i++);
		//		cell19.setCellValue(entity.getBaoxianfei().doubleValue());
		//		HSSFCell cell20 = row.createCell(i++);
		//		cell20.setCellValue(entity.getBaozhuangfei().doubleValue());
		//		HSSFCell cell21 = row.createCell(i++);
		//		cell21.setCellValue(entity.getZhuangxiefei().doubleValue());
		HSSFCell cell23 = row.createCell(i++);
		cell23.setCellValue(entity.getKhdh());
		//		HSSFCell cell22 = row.createCell(i++);
		//		cell22.setCellValue(entity.getBandanfei().doubleValue());
		HSSFCell cell24 = row.createCell(i++);
		cell24.setCellValue(entity.getPinming());
		HSSFCell cell25 = row.createCell(i++);
		cell25.setCellValue(entity.getXh());
		HSSFCell cell26 = row.createCell(i++);
		Integer jianshu = entity.getJianshu();
		if(jianshu != null) {
      cell26.setCellValue(jianshu);
    }
		HSSFCell cell26_5 = row.createCell(i++);
		cell26_5.setCellValue(entity.getBzh());
		HSSFCell cell27 = row.createCell(i++);
		cell27.setCellValue(entity.getZhl().doubleValue());
		HSSFCell cell28 = row.createCell(i++);
		cell28.setCellValue(entity.getTiji().doubleValue());
		HSSFCell cell35 = row.createCell(i++);
		cell35.setCellValue(entity.getReceipt().doubleValue());
		HSSFCell cell29 = row.createCell(i++);
		cell29.setCellValue(entity.getTbje().doubleValue());
		HSSFCell cell30 = row.createCell(i++);
		cell30.setCellValue(entity.getJffs());
		HSSFCell cell31 = row.createCell(i++);
		cell31.setCellValue(entity.getYunjia().doubleValue());
		HSSFCell cell99 = row.createCell(i++);
		cell99.setCellValue(entity.getTiebieshuoming());
		HSSFCell cell40 = row.createCell(i++);
		cell40.setCellValue(entity.getWithFinance());
		HSSFCell cell41 = row.createCell(i++);
		cell41.setCellValue(entity.getPremiumRate());
		HSSFCell cell42 = row.createCell(i++);
		cell42.setCellValue(entity.getIsRound());
		HSSFCell cell32 = row.createCell(i++);
		cell32.setCellValue(entity.getWeightprice().doubleValue());
		HSSFCell cell33 = row.createCell(i++);
		cell33.setCellValue(entity.getLightprice().doubleValue());
		HSSFCell cell34 = row.createCell(i++);
		cell34.setCellValue(entity.getPiecework().doubleValue());
		HSSFCell cell36 = row.createCell(i++);
		cell36.setCellValue(entity.getInvoice().doubleValue());
		HSSFCell cell37 = row.createCell(i++);
		cell37.setCellValue(entity.getTohome().doubleValue());
		HSSFCell cell38 = row.createCell(i++);
		cell38.setCellValue(entity.getDelivery().doubleValue());
		HSSFCell cell39 = row.createCell(i++);
		cell39.setCellValue(entity.getOther().doubleValue());
		//		HSSFCell cell40 = row.createCell(i++);
		//		cell40.setCellValue(entity.getCost().doubleValue());
		//HSSFCell cell45 = row.createCell(i++);
		//cell45.setCellValue(entity.getCost());
		if(flag) {		//成功的时候
			HSSFCell cell31_6 = row.createCell(i);
			cell31_6.setCellValue(entity.getInsNo());
		}else {			//失败的时候
			HSSFCell cell31_6 = row.createCell(i);
			cell31_6.setCellValue(entity.getErrorMsg());
		}
	}

	public static void buildBundleRow(HSSFRow row, HashMap<String, Object> entity) {
		int i = 0;
		row.createCell(i++).setCellValue(handleToString(entity.get("CHXH")));
		row.createCell(i++).setCellValue(handleToString(entity.get("YDBHID")));
		row.createCell(i++).setCellValue(handleToString(entity.get("ydxzh")));
		row.createCell(i++).setCellValue(handleToString(entity.get("hwyd_fazhan")));
		row.createCell(i++).setCellValue(handleToString(entity.get("hwyd_daozhan")));
		row.createCell(i++).setCellValue(handleToString(entity.get("FAZHAN")));
		row.createCell(i++).setCellValue(handleToString(entity.get("fzshhd")));
		row.createCell(i++).setCellValue(handleToString(entity.get("DAOZHAN")));
		row.createCell(i++).setCellValue(handleToString(entity.get("dzshhd")));
		Date fhshj = handleToDate(entity.get("FHSHJ"));
		row.createCell(i++).setCellValue(fhshj == null?"":CommonDateUtil.DateToString(fhshj, CommonDateUtil.PATTERN1));
		row.createCell(i++).setCellValue(handleToString(entity.get("FAZHAN")));
		row.createCell(i++).setCellValue(handleToString(entity.get("DAOZHAN")));
		Date zhchrq = handleToDate(entity.get("ZHCHRQ"));
		row.createCell(i++).setCellValue(zhchrq == null?"":CommonDateUtil.DateToString(zhchrq, CommonDateUtil.PATTERN1));
		row.createCell(i++).setCellValue(handleToString(entity.get("FHDWMCH"))); 
		row.createCell(i++).setCellValue(handleToString(entity.get("PINMING"))); 
		row.createCell(i++).setCellValue(handleToString(entity.get("hwyd_jianshu"))); 
		row.createCell(i++).setCellValue(handleToString(entity.get("JIANSHU"))); 
		row.createCell(i++).setCellValue(handleToString(entity.get("ZHL"))); 
		row.createCell(i++).setCellValue(handleToString(entity.get("TIJI"))); 
		row.createCell(i++).setCellValue(handleToString(entity.get("SHHRMCH")));
		String shhrlxdh = handleToString(entity.get("SHHRLXDH"));
		row.createCell(i++).setCellValue(StringUtils.isNotEmpty(shhrlxdh)?shhrlxdh:handleToString(entity.get("shhryb")) );
		Boolean ziti = handleToBoolean(entity.get("ZITI"));
		row.createCell(i++).setCellValue(ziti?"送货":"自提"); 
		row.createCell(i++).setCellValue(handleToString(entity.get("beizhu"))); 
		row.createCell(i++).setCellValue(handleToString(entity.get("xh"))); 
		row.createCell(i++).setCellValue(handleToString(entity.get("hyy"))); 
		row.createCell(i++).setCellValue(handleToString(entity.get("zhipiao2"))); 
		row.createCell(i++).setCellValue(handleToString(entity.get("zhipiao")));
		Integer ydzh = handleToInteger(entity.get("ydzh"));
		row.createCell(i++).setCellValue(ydzh==1?"已到":"未到"); 
		row.createCell(i++).setCellValue(handleToString(entity.get("TBJE"))); 
		row.createCell(i++).setCellValue(handleToString(entity.get("ysfs"))); 		
		row.createCell(i++).setCellValue(handleToString(entity.get("grid"))); 
		Date lrsj = handleToDate(entity.get("lrsj"));
		row.createCell(i++).setCellValue(lrsj == null?"":CommonDateUtil.DateToString(lrsj, CommonDateUtil.PATTERN1));
		
		row.createCell(i++).setCellValue(handleToString(entity.get("dhgrid"))); 
		Date yjddshj = handleToDate(entity.get("yjddshj"));
		row.createCell(i++).setCellValue(yjddshj == null?"":CommonDateUtil.DateToString(yjddshj, CommonDateUtil.PATTERN1));
		Date dhsj = handleToDate(entity.get("dhsj"));
		row.createCell(i++).setCellValue(dhsj == null?"":CommonDateUtil.DateToString(dhsj, CommonDateUtil.PATTERN1));
		Date lrtime = handleToDate(entity.get("lrtime"));
		row.createCell(i++).setCellValue(lrtime == null?"":CommonDateUtil.DateToString(lrtime, CommonDateUtil.PATTERN1));
		row.createCell(i++).setCellValue(handleToString(entity.get("yxzt"))); 
		row.createCell(i++).setCellValue(handleToString(entity.get("hdfk"))); 
		row.createCell(i++).setCellValue(handleToString(entity.get("dsk"))); 
		row.createCell(i++).setCellValue(handleToString(entity.get("hjje"))); 
		Integer isfd = handleToInteger(entity.get("isfd"));
		row.createCell(i++).setCellValue(isfd ==1 ?"是":"否"); 		
		Integer ywlx = handleToInteger(entity.get("ywlx"));
		String ywlxPrint = "";
		if(ywlx == 2) {
      ywlxPrint ="特快(2)";
    }
		if(ywlx == 1) {
      ywlxPrint ="快件(1)";
    }
		if(ywlx == 0) {
      ywlxPrint ="普件(0)";
    }
		if(ywlx == -1) {
      ywlxPrint ="慢件(-1)";
    }
		row.createCell(i++).setCellValue(ywlxPrint); 
		Integer jffs = handleToInteger(entity.get("jffs"));
		BigDecimal zhl = handleToBigDecimal(entity.get("ZHL"));
		BigDecimal tiji = handleToBigDecimal(entity.get("TIJI"));
		String jffsPrint = "";
		BigDecimal zhljf = BigDecimal.ZERO;
		BigDecimal tijijf = BigDecimal.ZERO;
		if(jffs == 0) {
			jffsPrint = "重货";
			zhljf = zhl;
			if(tiji .compareTo(BigDecimal.ZERO) == 1 && zhl.divide(tiji,2,BigDecimal.ROUND_HALF_DOWN).compareTo(new BigDecimal(0.3))!= 1 ) {
				zhljf = BigDecimal.ZERO;		//体积更多,按照体积计费
				tijijf = tiji;
			}
		}
		if(jffs == 1) {
			jffsPrint = "轻货";
			tijijf = tiji;
			if(zhl.compareTo(BigDecimal.ZERO) == 1 && tiji.divide(zhl,2,BigDecimal.ROUND_HALF_DOWN).compareTo(new BigDecimal(0.3)) != 1 ) {
				tijijf = BigDecimal.ZERO;
				zhljf = zhl;
			}
		}
		if(jffs == 2) {
			jffsPrint = "按件";
		}
		row.createCell(i++).setCellValue(jffsPrint); 
			
		//计费重量
		row.createCell(i++).setCellValue(zhljf.doubleValue()); 
		//计费体积
		row.createCell(i++).setCellValue(tijijf.doubleValue()); 
		row.createCell(i++).setCellValue(handleToInteger(entity.get("pszhsh")) ==1 ?"节假日可送货(1)":"工作日内送货(0)"); 
		Integer ydts = handleToInteger(entity.get("ydts"));
		row.createCell(i++).setCellValue((ydts =(ydts ==null ? 0 :ydts))>8?"8天以上":""+ydts); 
		row.createCell(i++).setCellValue(handleToString(entity.get("SHHRDZH"))); 
		row.createCell(i++).setCellValue(handleToString(entity.get("tzfhzt"))); 
		row.createCell(i++).setCellValue(handleToString(entity.get("wx_item"))); 
		row.createCell(i++).setCellValue(handleToString(entity.get("wx_name"))); 
		row.createCell(i++).setCellValue(handleToString(entity.get("wx_con_name"))); 
		row.createCell(i++).setCellValue(handleToString(entity.get("wx_tel"))); 
		row.createCell(i++).setCellValue(handleToString(entity.get("xiaoshoucode"))); 
		row.createCell(i++).setCellValue(handleToString(entity.get("fhkhhy"))); 
		row.createCell(i++).setCellValue(handleToString(entity.get("list_no"))); 
		Integer type = handleToInteger(entity.get("i_type"));
		row.createCell(i++).setCellValue(type==2?"提货成本":(type == 0?"干线成本":(type==1?"配送成本":"未知成本")));
		row.createCell(i++).setCellValue(handleToString(entity.get("is_kaiyun")));
		row.createCell(i++).setCellValue(handleToString(entity.get("ky_kind")));
	}
	public static Boolean handleToBoolean(Object o) {
		if(o == null) {
      return false;
    }
		if(o instanceof Boolean) {
      return (Boolean) o;
    }
		if(o instanceof Integer) {
			Integer flag = (Integer) o;
			return flag == 1?true:false;
		}
		return false;
	}
	public static String handleToString(Object o) {
		if(o == null) {
      return "";
    }
		if(o instanceof String) {
      return (String) o;
    }
		return String.valueOf(o);
	}
	public static Integer handleToInteger(Object o) {
		if(o == null) {
      return 0;
    }
		if(o instanceof Integer) {
      return (Integer) o;
    }
		return Integer.parseInt(handleToString(o));
	}
	public static BigDecimal handleToBigDecimal(Object o) {
		if(o == null) {
      return BigDecimal.ZERO;
    }
		if(o instanceof BigDecimal) {
      return (BigDecimal) o;
    }
		return new BigDecimal(handleToString(o));
	}
	public static Date handleToDate(Object o) {
		if(o instanceof Date || o instanceof Timestamp) {
      return (Date) o;
    }
		return null;
	}
}





