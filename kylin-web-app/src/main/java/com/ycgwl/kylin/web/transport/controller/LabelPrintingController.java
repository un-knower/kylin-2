package com.ycgwl.kylin.web.transport.controller;

import com.alibaba.fastjson.JSONObject;
import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.transport.service.api.AdjunctSomethingService;
import com.ycgwl.kylin.web.transport.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@RequestMapping("/labelPrint")
@Controller
public class LabelPrintingController extends BaseController {
	@Resource
	private AdjunctSomethingService adjunctSomethingService;
	
	/**
	 * 查询客户标签
	 * @param wayBillNum
	 * @return
	 */
	@RequestMapping("/findCustomerLabel/{wayBillNum}")
	@ResponseBody 
	public JsonResult findCustomerLabel(@PathVariable String wayBillNum) {
		JSONObject jsonObject = adjunctSomethingService.findCustomerLabel(wayBillNum);
		if (null != jsonObject) {
			return JsonResult.getConveyResult("200", "查询成功", jsonObject);
		}
		return JsonResult.getConveyResult("400", "查询失败");
	}
}
