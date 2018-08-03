package com.ycgwl.kylin.web.transport.controller;

import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.security.client.ContextHepler;
import com.ycgwl.kylin.security.entity.User;
import com.ycgwl.kylin.transport.dto.FreightRecordDetailSerachDto;
import com.ycgwl.kylin.transport.dto.FreightRecordDto;
import com.ycgwl.kylin.transport.dto.FreightRecordInputDto;
import com.ycgwl.kylin.transport.dto.FreightRecordSerachDto;
import com.ycgwl.kylin.transport.service.api.AdjunctSomethingService;
import com.ycgwl.kylin.web.transport.BaseController;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 货运记录
 * @author Acer
 *
 */
@Controller
@RequestMapping("/transport")
public class FreightRecordController extends BaseController {

	@Resource
	private AdjunctSomethingService adjunctSomethingService;
	
	/**
	 * 跳转到货运记录界面
	 * @return
	 */
	@RequestMapping("/toFreightRecord")
	public String toFreightRecord() {
		return "/transport/freightRecords/freightRecords";
	}
	
	/**
	 * 跳转到货云记录详情页面界面
	 * @return
	 */
	@RequestMapping("/toPrintFreightRecords")
	public String toPrintFreightRecords() {
		return "/transport/freightRecords/printFreightRecords";
	}

	/**
	 * 跳转到货运记录录入界面
	 * @return
	 */
	@RequestMapping("/toFreightRecordsEdit")
	public String toFreightRecordsEdit() {
		return "/transport/freightRecords/freightRecordsEdit";
	}
	
	/**
	 * 跳转到货运记录审核页面
	 * @return
	 */
	@RequestMapping("/toCheckFreightRecords")
	@RequiresPermissions("record:comment:check")
	public String toCheckFreightRecords() {
		return "/transport/freightRecords/checkFreightRecords";
	}
	
	/**
	 * 货运记录 查询
	 * @return
	 */
	@RequestMapping("/query/freightRecord")
	@RequiresPermissions("record:query:freightCheck")
	@ResponseBody
	public JsonResult findFreightRecord (@RequestBody FreightRecordDto freightRecordDto) {
		// 获取当前用户
		User user = ContextHepler.getCurrentUser();
		freightRecordDto.setCompany(user.getCompany());
		return adjunctSomethingService.findFreightRecord(user.getAccount(), freightRecordDto);
	}
	 
	/**
	 * 货运记录 录入查询
	 * @return
	 */
	@RequestMapping("/saveFreightRecordselect")
	@ResponseBody
	public JsonResult saveFreightRecordselect (@RequestBody FreightRecordSerachDto freightRecordSerachDto) {
		// 获取当前用户
		User user = ContextHepler.getCurrentUser();
		return adjunctSomethingService.saveFreightRecordselect(user.getUserName(),user.getAccount(),user.getCompany(), freightRecordSerachDto);
	}
	
	/**
	 * 添加货物异常信息
	 * @return
	 */
	@RequestMapping("/save/freightRecord")
	@ResponseBody
	public JsonResult saveFreightRecord (@RequestBody FreightRecordInputDto freightRecordInputDto) {
		// 获取当前用户
		User user = ContextHepler.getCurrentUser();
		freightRecordInputDto.setCurrUserName(user.getUserName());
		freightRecordInputDto.setCompany(user.getCompany());
		return adjunctSomethingService.saveFreightRecord(user.getAccount(), freightRecordInputDto);
	}
	
	/**
	 * 添加意见
	 * @return
	 */
	@RequestMapping("/addComments")
	//@RequiresPermissions("record:comment:save")
	@ResponseBody
	public JsonResult addComments (@RequestBody FreightRecordInputDto freightRecordInputDto) {
		// 获取当前用户
		User user = ContextHepler.getCurrentUser();
		freightRecordInputDto.setCurrAccount(user.getAccount());
		return adjunctSomethingService.addComments(freightRecordInputDto);
	}
	
	/**
	 * 货运记录查询详情
	 * @return
	 */
	@RequestMapping("/query/freightRecordDetail")
	@ResponseBody
	public JsonResult selectFreightRecordDetail (@RequestBody FreightRecordDetailSerachDto FreightRecordDetailSerachDto) {
		return adjunctSomethingService.selectFreightRecordDetail(FreightRecordDetailSerachDto);
	}
	
}
