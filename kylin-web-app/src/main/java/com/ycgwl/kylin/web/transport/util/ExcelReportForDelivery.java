package com.ycgwl.kylin.web.transport.util;

import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.entity.BundleArriveEntity;
import com.ycgwl.kylin.transport.entity.DispatchCarPickGoods;
import com.ycgwl.kylin.transport.entity.TransportBasicData;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 送货派车/送货派车司机 excel导出
 * TODO Add description
 * <p>
 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2018-02-05 11:53:01
 */
public class ExcelReportForDelivery {

	
	/**
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2018-02-05 13:15:46
	 * @param list	导出数据的集合
	 * @param excelPath	导入的模板路径
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static HSSFWorkbook BuildDeliveryWorkSheet(List<Map<String,Object>> list,String excelPath) throws FileNotFoundException, IOException{
		if(CollectionUtils.isEmpty(list)) {
      throw new ParameterException("无数据,无意义的操作");
    }
		HSSFWorkbook workBook = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(excelPath)));
		HSSFSheet sheet = workBook.getSheetAt(0);
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			HSSFRow row = sheet.createRow(i + 1);	 //第二行开始输出
			int num = 0;
			row.createCell(num++).setCellValue(checkValue(map.get("gs")));
			row.createCell(num++).setCellValue(checkValue(map.get("id")));
			row.createCell(num++).setCellValue(checkValue(map.get("yshm")));
			row.createCell(num++).setCellValue(checkValue(map.get("ydbhid")));
			row.createCell(num++).setCellValue(checkValue(map.get("shhd")));
			row.createCell(num++).setCellValue(checkValue(map.get("ywlx")));
			row.createCell(num++).setCellValue(checkValue(map.get("fhdw")));
			row.createCell(num++).setCellValue(checkValue(map.get("fhdh")));
			row.createCell(num++).setCellValue(checkValue(map.get("shdw")));
			row.createCell(num++).setCellValue(checkValue(map.get("shlxr")));
			row.createCell(num++).setCellValue(checkValue(map.get("shdz")));
			row.createCell(num++).setCellValue(checkValue(map.get("shdh")));
			row.createCell(num++).setCellValue(checkValue(map.get("JS")));
			row.createCell(num++).setCellValue(checkValue(map.get("ZL")));
			row.createCell(num++).setCellValue(checkValue(map.get("TJ")));
			row.createCell(num++).setCellValue(checkValue(map.get("dsk")));
			row.createCell(num++).setCellValue(checkValue(map.get("hdfk")));
			row.createCell(num++).setCellValue(checkValue(map.get("dzy")));
			row.createCell(num++).setCellValue(checkValue(map.get("dzygrid")));
			row.createCell(num++).setCellValue(checkValue(map.get("kdtime")));
			row.createCell(num++).setCellValue(checkValue(map.get("jhshtime")));
			row.createCell(num++).setCellValue(checkValue(map.get("pcdd")));
			row.createCell(num++).setCellValue(checkValue(map.get("pcddgrid")));
			row.createCell(num++).setCellValue(checkValue(map.get("pcyes")));
			row.createCell(num++).setCellValue(checkValue(map.get("thtime")));
			row.createCell(num++).setCellValue(checkValue(map.get("zxbz")));
			row.createCell(num++).setCellValue(checkValue(map.get("cgyqm")));
			row.createCell(num++).setCellValue(checkValue(map.get("qsr")));
			row.createCell(num++).setCellValue(checkValue(map.get("qstime")));
			row.createCell(num++).setCellValue(checkValue(map.get("pcpctime")));
			row.createCell(num++).setCellValue(checkValue(map.get("pcqsd")));
			row.createCell(num++).setCellValue(checkValue(map.get("tjhsr")));
			row.createCell(num++).setCellValue(checkValue(map.get("tjhsrgrid")));
			row.createCell(num++).setCellValue(checkValue(map.get("tjtime")));
			row.createCell(num).setCellValue(checkValue(map.get("pszhsh")));
		}
		return workBook;
	}
	
	public static HSSFWorkbook BuildDeliveryDriverWorkSheet(List<Map<String, Object>> list, String excelPath) throws FileNotFoundException, IOException {
		if(CollectionUtils.isEmpty(list)) {
      throw new ParameterException("无数据,无意义的操作");
    }
		HSSFWorkbook workBook = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(excelPath)));
		HSSFSheet sheet = workBook.getSheetAt(0);
		for (int i = 0; i < list.size() ; i++) {
			Map<String, Object> map = list.get(i);
			HSSFRow row = sheet.createRow(i + 2);	 //第二行开始输出
			int num = 0;
			row.createCell(num++).setCellValue(checkValue(map.get("gs")));
			row.createCell(num++).setCellValue(checkValue(map.get("id")));
			row.createCell(num++).setCellValue(checkValue(map.get("yshm")));
			row.createCell(num++).setCellValue(checkValue(map.get("ydbhid")));
			row.createCell(num++).setCellValue(checkValue(map.get("ModeOfTransportation")));
			row.createCell(num++).setCellValue(checkValue(map.get("ChargeableTotalVolume")));
			row.createCell(num++).setCellValue(checkValue(map.get("ChargeableTotalWeight")));
			row.createCell(num++).setCellValue(checkValue(map.get("shhd")));
			row.createCell(num++).setCellValue(checkValue(map.get("ywlx")));
			row.createCell(num++).setCellValue(checkValue(map.get("fhdw")));
			row.createCell(num++).setCellValue(checkValue(map.get("fhdh")));
			row.createCell(num++).setCellValue(checkValue(map.get("shdw")));
			row.createCell(num++).setCellValue(checkValue(map.get("shlxr")));
			row.createCell(num++).setCellValue(checkValue(map.get("shdz")));
			row.createCell(num++).setCellValue(checkValue(map.get("shdh")));
			row.createCell(num++).setCellValue(checkValue(map.get("JS")));
			row.createCell(num++).setCellValue(checkValue(map.get("ZL")));
			row.createCell(num++).setCellValue(checkValue(map.get("TJ")));
			row.createCell(num++).setCellValue(checkValue(map.get("dsk")));
			row.createCell(num++).setCellValue(checkValue(map.get("hdfk")));
			row.createCell(num++).setCellValue(checkValue(map.get("dzy")));
			row.createCell(num++).setCellValue(checkValue(map.get("dzygrid")));
			row.createCell(num++).setCellValue(checkValue(map.get("kdtime")));
			row.createCell(num++).setCellValue(checkValue(map.get("jhshtime")));
			row.createCell(num++).setCellValue(checkValue(map.get("pcdd")));
			row.createCell(num++).setCellValue(checkValue(map.get("pcddgrid")));
			row.createCell(num++).setCellValue(checkValue(map.get("pcyes")));
			row.createCell(num++).setCellValue(checkValue(map.get("thtime")));
			row.createCell(num++).setCellValue(checkValue(map.get("zxbz")));
			row.createCell(num++).setCellValue(checkValue(map.get("cgyqm")));
			row.createCell(num++).setCellValue(checkValue(map.get("qsr")));
			row.createCell(num++).setCellValue(checkValue(map.get("qstime")));
			row.createCell(num++).setCellValue(checkValue(map.get("pcpctime")));
			row.createCell(num++).setCellValue(checkValue(map.get("pcqsd")));
			row.createCell(num++).setCellValue(checkValue(map.get("pcshd")));
			row.createCell(num++).setCellValue(checkValue(map.get("tjhsr")));
			row.createCell(num++).setCellValue(checkValue(map.get("tjhsrgrid")));
			row.createCell(num++).setCellValue(checkValue(map.get("tjtime")));
			row.createCell(num++).setCellValue(checkValue(map.get("pszhsh")));
			row.createCell(num++).setCellValue(checkValue(map.get("ch")));
			row.createCell(num++).setCellValue(checkValue(map.get("sj")));
			row.createCell(num++).setCellValue(checkValue(map.get("hwmc")));
			row.createCell(num++).setCellValue(checkValue(map.get("pccomm")));
		}
		return workBook;
	}
	public static String checkValue(Object o) {
		if(o == null) {
      return "";
    }
		return String.valueOf(o);
	}
	
	/**
	 * 调度派车导出
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static HSSFWorkbook VehicleDispatching(List<DispatchCarPickGoods> list, String excelPath) throws FileNotFoundException, IOException{
		if(CollectionUtils.isEmpty(list)) {
			return new HSSFWorkbook();
		}
		HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(excelPath)));
		HSSFSheet sheet = wb.getSheetAt(0);
		for (int i = 0; i < list.size() ; i++) {
			HSSFRow row = sheet.createRow(i + 1);	 //第二行开始输出
			DispatchCarPickGoods goods = list.get(i);
			int num = 0;
			String ywlx = "";
			Integer ywlxStatus=goods.getYwlx();
			switch (ywlxStatus==null?-2:ywlxStatus) {
			
			case 0:
				ywlx ="普件";
				break;
			case -1:
				ywlx ="慢件";
				break;
			case 1:
				ywlx ="快件";
				break;
			case 2:
				ywlx ="特快";
				break;
			default:
				break;
			}
			row.createCell(num++).setCellValue(checkValue(goods.getGsid()));
			row.createCell(num++).setCellValue(checkValue(goods.getXdtime()));
			row.createCell(num++).setCellValue(checkValue(goods.getOrderNo()));
			row.createCell(num++).setCellValue(checkValue(goods.getId()));	
			row.createCell(num++).setCellValue(checkValue(goods.getYywd()));
			
			row.createCell(num++).setCellValue(checkValue(goods.getKfy()));
			row.createCell(num++).setCellValue(checkValue(goods.getHwdaozhan()));
			row.createCell(num++).setCellValue(checkValue(ywlx));
			row.createCell(num++).setCellValue(checkValue(goods.getYsfs()));
			row.createCell(num++).setCellValue(checkValue(goods.getJs()));
			row.createCell(num++).setCellValue(checkValue(goods.getZl()));
			row.createCell(num++).setCellValue(checkValue(goods.getTj()));
			row.createCell(num++).setCellValue(checkValue(goods.getFhr()));
			row.createCell(num++).setCellValue(checkValue(goods.getQhadd()));
			row.createCell(num++).setCellValue(checkValue(goods.getJhqhtime()));
			row.createCell(num++).setCellValue(checkValue(goods.getPcyes() == 1 ?"已派" : "未派"));
			row.createCell(num++).setCellValue(checkValue(goods.getDdpcdd()));
			row.createCell(num++).setCellValue(checkValue(goods.getDdpctime()));			
    }
		return wb;
	}
	@SuppressWarnings("unlikely-arg-type")
	public static HSSFWorkbook BuildBundleArriveWorkSheet(List<BundleArriveEntity> list,
			String excelPath,List<TransportBasicData> basicList) throws FileNotFoundException, IOException {
		if(CollectionUtils.isEmpty(list)) {
      throw new ParameterException("无数据,无意义的操作");
    }
		HSSFWorkbook workBook = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(excelPath)));
		HSSFSheet sheet = workBook.getSheetAt(0);
		
		HashMap<String, HashMap<String, String>> basicMap = converTransportBasicData(basicList);
		for (int i = 0; i < list.size() ; i++) {
			BundleArriveEntity entity = list.get(i);
			HSSFRow row = sheet.createRow(i + 1);	 //第二行开始输出
			int num = 0;
			row.createCell(num++).setCellValue(checkValue(basicMap.get(BasicKeyName.BIAOSHI).get(entity.getIsTiShi())));
			row.createCell(num++).setCellValue(entity.getIsKaiyun()==1 ? "快运":"");
			row.createCell(num++).setCellValue(entity.getIsGreenChannel()==1 ? "是":"否");
			row.createCell(num++).setCellValue(checkValue(entity.getChxh()));
			row.createCell(num++).setCellValue(checkValue(entity.getYdbhid()));
			row.createCell(num++).setCellValue(checkValue(entity.getYdxzh()));
			row.createCell(num++).setCellValue(checkValue(entity.getFazhanHwyd()));
			row.createCell(num++).setCellValue(checkValue(entity.getShhd()));
			row.createCell(num++).setCellValue(checkValue(entity.getDaozhanHwyd()));
			row.createCell(num++).setCellValue(checkValue(entity.getDzshhdHwyd()));
			row.createCell(num++).setCellValue(checkValue(entity.getFhshj()));
			row.createCell(num++).setCellValue(checkValue(entity.getFazhan()));
			row.createCell(num++).setCellValue(checkValue(entity.getDaozhan()));
			row.createCell(num++).setCellValue(checkValue(entity.getZhchrq()));
			row.createCell(num++).setCellValue(checkValue(entity.getFhdwmch()));
			row.createCell(num++).setCellValue(checkValue(entity.getPinming()));
			row.createCell(num++).setCellValue(checkValue(entity.getJianshuHwydxz()));
			row.createCell(num++).setCellValue(checkValue(entity.getJianshu()));
			row.createCell(num++).setCellValue(checkValue(entity.getZhl()));
			row.createCell(num++).setCellValue(checkValue(entity.getTiji()));
			row.createCell(num++).setCellValue(checkValue(entity.getShhrmch()));
			row.createCell(num++).setCellValue(checkValue(entity.getShhrlxdh()));
			row.createCell(num++).setCellValue(checkValue(basicMap.get(BasicKeyName.ZITI).get(entity.getZiti())));
			row.createCell(num++).setCellValue(checkValue(entity.getBeizhu()));
			row.createCell(num++).setCellValue(checkValue(entity.getXh()));
			row.createCell(num++).setCellValue(checkValue(entity.getHyy()));
			row.createCell(num++).setCellValue(checkValue(entity.getZhipiao2()));
			row.createCell(num++).setCellValue(checkValue(entity.getZhipiao()));
			row.createCell(num++).setCellValue(checkValue(basicMap.get(BasicKeyName.YDZH).get(entity.getYdzh())));
			row.createCell(num++).setCellValue(checkValue(entity.getTbjeHwydxz()));
			row.createCell(num++).setCellValue(checkValue(entity.getYsfs()));
			row.createCell(num++).setCellValue(checkValue(entity.getGrid()));
			row.createCell(num++).setCellValue(checkValue(entity.getLrsj()));
			row.createCell(num++).setCellValue(checkValue(entity.getDhgrid()));
			row.createCell(num++).setCellValue(checkValue(entity.getDhsj()));
			row.createCell(num++).setCellValue(checkValue(entity.getVhjje()));
			row.createCell(num++).setCellValue(checkValue(entity.getVhdfk()));
			row.createCell(num++).setCellValue(checkValue(entity.getVdsk()));
			row.createCell(num++).setCellValue(checkValue(basicMap.get(BasicKeyName.ISFD).get(entity.getIsfd())));
			row.createCell(num++).setCellValue(checkValue(basicMap.get(BasicKeyName.YWLX).get(entity.getYwlx())));
			row.createCell(num++).setCellValue(checkValue(basicMap.get(BasicKeyName.JFFS).get(entity.getJffsHwydxz())));
			row.createCell(num++).setCellValue(checkValue(entity.getZhlCalc()));
			row.createCell(num++).setCellValue(checkValue(entity.getTijiCalc()));
			row.createCell(num++).setCellValue(checkValue(basicMap.get(BasicKeyName.PSZHSH).get(entity.getPszhsh())));
			row.createCell(num++).setCellValue(checkValue(entity.getYdts()));
			row.createCell(num++).setCellValue(checkValue(entity.getShhrdzh()));
			row.createCell(num++).setCellValue(checkValue(entity.getLrtime()));
			row.createCell(num++).setCellValue(checkValue(entity.getYxztHwyd()));
			row.createCell(num++).setCellValue(checkValue(basicMap.get(BasicKeyName.WAIT_SEND).get(entity.getTzfhzt())));
		}
		return workBook;
	}
	
	interface BasicKeyName{ 
		String BIAOSHI	 = "标记";
		String ISFD		 = "是否返单";
		String YWLX		 = "业务类型";
		String PSZHSH	 = "派送指示";
		String JFFS 	 = "计费方式";
		String WAIT_SEND = "等托放货";
		String YDZH 	 = "是否到站";
		String ZITI  	 = "提货方式";
	}

	private static HashMap<String,HashMap<String,String>> converTransportBasicData(List<TransportBasicData> basicList){
		HashMap<String, HashMap<String, String>> result = new HashMap<String,HashMap<String,String>>();
			for (TransportBasicData transportBasicData : basicList) {
				String name = transportBasicData.getName();
				
				if(result.containsKey(name)) {
          result.get(name).put(transportBasicData.getKeyValue(), transportBasicData.getKeyName());
        } else {
					HashMap<String, String> submap = new HashMap<String,String>();
					submap.put(transportBasicData.getKeyValue(), transportBasicData.getKeyName());
					result.put(name, submap);
				}
			}
		return result;
	}

}
