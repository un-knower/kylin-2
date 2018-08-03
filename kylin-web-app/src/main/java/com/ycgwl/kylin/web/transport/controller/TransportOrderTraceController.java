package com.ycgwl.kylin.web.transport.controller;


import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.transport.entity.OnTheWay;
import com.ycgwl.kylin.transport.entity.TransportOrderTrace;
import com.ycgwl.kylin.transport.service.api.ITransportSignRecordService;
import com.ycgwl.kylin.web.transport.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 运单跟踪
 * <p>
 * @developer Create by <a href="mailto:62337@ycgwl.com">chenxj</a> at 2018年03月01日
 */
@Controller  
@RequestMapping("/transport/trace")
public class TransportOrderTraceController extends BaseController {
	
	@Resource
	private ITransportSignRecordService transportSignRecordService;
	
	@RequestMapping(value = "/logistics")
	public String receiveSign(Model model){  		
		return "/transport/arrive/logistics";
	}
	
	@RequestMapping(value = "/transit")
	public String totransit(Model model){  		
		return "/transport/arrive/transit";
	}
	/**
	 * 录入在途车辆跟踪
	 * @author xinjiang.chen
	 * @version 2018.02.27
	 */
	@RequestMapping(value = " /transportTracesave")
	@ResponseBody 
	public JsonResult transportTracesave(TransportOrderTrace transportOrderTrace){
		
		JsonResult result =new JsonResult();
		try{
			transportSignRecordService.addTransportOrderTrace(transportOrderTrace);
			result.put("200", "保存成功");
		}catch(Exception e){
		    result.put("400", "保存失败");
		}
		return result;
	}
	
	/**
	 * 在途跟踪查询
	 * @author chenxm
	 */
	@RequestMapping(value = "/selectTrackInfo/{wayBillNum}")
	@ResponseBody 
	public JsonResult selectTrackInfo(@PathVariable String wayBillNum){
		try{
			List<OnTheWay> onTheWayList = transportSignRecordService.selectTrackInfo(wayBillNum);
			return JsonResult.getConveyResult("200", "查询成功", onTheWayList);
		}catch(Exception e){
			return  JsonResult.getConveyResult("400", "保存失败");
		}
	}
	

}
