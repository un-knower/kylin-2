package com.ycgwl.kylin.web.transport.controller;

import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.security.client.ContextHepler;
import com.ycgwl.kylin.security.entity.User;
import com.ycgwl.kylin.transport.dto.ConsignorDeliveryInstructDto;
import com.ycgwl.kylin.transport.entity.adjunct.Company;
import com.ycgwl.kylin.transport.entity.adjunct.ReleaseWaiting;
import com.ycgwl.kylin.transport.service.api.AdjunctSomethingService;
import com.ycgwl.kylin.transport.service.api.ITransportOrderService;
import com.ycgwl.kylin.web.transport.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * 等托运人发货指令
 * @author chenxm
 *
 */
@Controller
@RequestMapping("/transport/delivery")
public class ConsignorDeliveryInstructController extends BaseController {
	
	@Resource
	private ITransportOrderService iTransportOrderService;
	
	@Resource
	private AdjunctSomethingService adjunctSomethingService;
	
	/**
	 * 跳转等拖发货界面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toDelivery")
	public String receiveSign(HttpServletRequest request){ 
		@SuppressWarnings("unchecked")
		Collection<Company> sessionList = (Collection<Company>) request.getSession().getAttribute("COMPANY_LIST");
		if (sessionList == null) {
			Collection<Company> list = adjunctSomethingService.queryStationCompany();
			request.getSession().setAttribute("COMPANY_LIST", list);
			request.getSession().setAttribute("CURR_COMPANY_NAME", ContextHepler.getCurrentUser().getCompany());
			request.getSession().setAttribute("CURR_COMPANY_CODE", ContextHepler.getCurrentUser().getCompanyCode());
		}
		return "/transport/arrive/delivery";
	}
	
	/**
	 * 查询等托运人发货指令 历史记录
	 * @author chenxm
	 * @serialData
	 */
	@RequestMapping(value="/query/historyRecord")
	@ResponseBody
	public JsonResult historyRecords(@RequestBody ConsignorDeliveryInstructDto cdiDto) {
		// 获取当前用户
		User user = ContextHepler.getCurrentUser();
		// 查询历史记录
		return iTransportOrderService.queryHistoryRecords(user.getAccount(), cdiDto);
	}
	
	/**
	 * 等待放货查询
	 * @author chenxm
	 */
	@RequestMapping(value="/query/findWaitingForDelivery")
	@ResponseBody
	public JsonResult findWaitingForDelivery(@RequestBody ConsignorDeliveryInstructDto cdiDto) {
		// 获取当前用户
		User user = ContextHepler.getCurrentUser();
		// 查询
		return iTransportOrderService.findWaitingForDelivery(user.getAccount(), cdiDto);
	}
	
	/**
	 * 设置(撤销)等待/通知放货状态
	 * @author chenxm
	 */
	@RequestMapping(value="/modify/setWaitingForDelivery")
	@ResponseBody
	public JsonResult setWaitingForDelivery(HttpServletRequest request, @RequestBody ConsignorDeliveryInstructDto cdiDto) {
		// 获取当前用户
		User user = ContextHepler.getCurrentUser();
		// 获取请求的真正ip
		String ipAddr = this.getIpAddr(request);
		cdiDto.setIpAddr(ipAddr);
		// 修改通知放货或者等待放货 状态
		int updateDeliveryStatus = iTransportOrderService.updateDeliveryStatus(user.getAccount(), cdiDto);
		// 添加日志
		int saveDdfhLog = iTransportOrderService.saveDdfhLog(user.getAccount(), cdiDto);
		
		if ((updateDeliveryStatus == 1) && (saveDdfhLog == 1)) {
			return JsonResult.getConveyResult("200", "修改成功");
		} else {
			return JsonResult.getConveyResult("400", "修改失败");
		}
	}

	/**
	 * 通知放货查询
	 * @author chenxm  
	 */
	@RequestMapping(value="/query/findNoticeDelivery")
	@ResponseBody
	public JsonResult findNoticeDelivery(ConsignorDeliveryInstructDto cdiDto) {
		// 获取当前用户
		User user = ContextHepler.getCurrentUser();
		// 通知放货查询
		return iTransportOrderService.findNoticeDelivery(user.getAccount(), cdiDto);
	}
	
	/**
	 * 等待放货保存
	 * @author chenxm  
	 */
	@RequestMapping(value="/saveWaitingForDelivery")
	@ResponseBody
	public JsonResult saveWaitingForDelivery(HttpServletRequest request, @RequestBody ReleaseWaiting releaseWaiting) {
		if (null == releaseWaiting.getYdbhid() || "".equals(releaseWaiting.getYdbhid())) {
			return JsonResult.getConveyResult("500", "运单号不能为空");
		}
		// 获取当前用户
		User user = ContextHepler.getCurrentUser();
		// 获取请求的真正ip
		String ipAddr = this.getIpAddr(request);
		// 通知放货查询
		return iTransportOrderService.saveWaitingForDelivery(releaseWaiting, user.getAccount(), ipAddr);
	}
	
	
	
}
