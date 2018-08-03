package com.ycgwl.kylin.web.transport.controller;

import com.ycgwl.kylin.SystemKey;
import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.security.client.ContextHepler;
import com.ycgwl.kylin.security.entity.User;
import com.ycgwl.kylin.transport.dto.ReorderDto;
import com.ycgwl.kylin.transport.entity.ReorderConfigure;
import com.ycgwl.kylin.transport.service.api.ITransportOrderService;
import com.ycgwl.kylin.transport.service.api.ITransportSignRecordService;
import com.ycgwl.kylin.transport.vo.PhotoVo;
import com.ycgwl.kylin.util.SystemUtils;
import com.ycgwl.kylin.web.transport.BaseController;
import com.ycgwl.kylin.web.transport.util.FileUploadHelper;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionTimedOutException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author chenxiaoman
 * 返单
 */
@Controller  
@RequestMapping("/transport/reorder")
public class ReorderManageController extends BaseController {
	
	@Resource
	private ITransportSignRecordService transportSignRecordService;
	@Resource
	private ITransportOrderService transportOrderService;
	
	/**
	 * 跳转到返单发送界面checkReorder
	 * @return
	 */
	@RequestMapping("/toSendReorder")
	public String toSendReorder() {
		return "/transport/reorder/reorderSend";
	}
	
	/**
	 * 跳转到返单接收界面
	 * @return
	 */
	@RequestMapping("/toReceiveReorder")
	public String toReceiveReorder() {
		return "/transport/reorder/reorderAccept";
	}
	
	/**
	 * 跳转到返单配置界面
	 * @return
	 */
	@RequestMapping("/toReorderConfig")
	public String toReorderConfig() {
		return "/transport/reorder/reorderConfig";
	}
	
	
	/**
	 * 返单录入查询
	 * @param waybillNum
	 * @return
	 */
	@RequestMapping("/selectSendReorder")
	@ResponseBody
	public JsonResult selectSendReorder(@RequestBody ReorderDto reorderDto) {
		// 获取当前登录用户
		User user = ContextHepler.getCurrentUser();
		reorderDto.setFdfsr(user.getUserName());
		return transportSignRecordService.selectReorder(user.getAccount(), reorderDto);
	}
	
	/**
	 * 返单发送录入
	 * @param waybillNum
	 * @return
	 */
	@RequestMapping("saveSendReorder")
	@ResponseBody
	public JsonResult saveReorder(@RequestBody ReorderDto reorderDto) {
		// 获取当前登录用户
		User user = ContextHepler.getCurrentUser();
		reorderDto.setFdfsrgrid(user.getAccount());
		reorderDto.setFdfsr(user.getUserName());
		return transportSignRecordService.saveReorder(user.getAccount(), reorderDto);
	}
	 
	/**
	 * 返单确认查询
	 * @param waybillNum
	 * @return
	 */
	@RequestMapping("selectReceiveReorder")
	@ResponseBody
	public JsonResult selectReceiveReorder(@RequestBody ReorderDto reorderDto) {
		// 获取当前登录用户
		User user = ContextHepler.getCurrentUser();
		reorderDto.setQrr(user.getUserName());
		return transportSignRecordService.selectReceiveReorder(user.getAccount(), user.getCompany(), reorderDto);
	}
	
	/**
	 * 返单确认录入
	 * @param waybillNum
	 * @return
	 */
	@RequestMapping("saveReceiveReorder")
	@ResponseBody
	public JsonResult saveReceiveReorder(@RequestBody ReorderDto reorderDto) {
		// 获取当前登录用户
		User user = ContextHepler.getCurrentUser();
		reorderDto.setFdfsrgrid(user.getAccount());
		reorderDto.setQrr(user.getUserName());
		return transportSignRecordService.saveReceiveReorder(reorderDto);
	}
	
	/**
	 * 查询返单配置
	 * @return
	 */
	@RequestMapping("selectReorderConfigure")
	@ResponseBody
	public JsonResult selectReorderConfigure() {
		// 获取当前登录用户
		User user = ContextHepler.getCurrentUser();
		return transportSignRecordService.selectReorderConfigure(user.getAccount());
	}
	
	/**
	 * 修改返单配置
	 * @param reorderConfigure
	 * @return
	 */
	@RequestMapping("updateReorderConfigure")
	@ResponseBody
	public JsonResult updateReorderConfigure(@RequestBody ReorderConfigure reorderConfigure) {
		return transportSignRecordService.updateReorderConfigure(reorderConfigure);
	}
	
	/**
	 * 上傳回單
	 * @param reorderConfigure
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("uploadReorder")
	@ResponseBody
	public JsonResult uploadReorder(HttpServletRequest request) throws IOException {
		// 上傳回單
		Collection<HashMap<String,String>> photoinfo = new ArrayList<HashMap<String,String>>();
		try {
			photoinfo = FileUploadHelper.saveImage(request, SystemUtils.get(SystemKey.REORDER_IMAGE_PATH), new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		} catch (Exception e) {
			return JsonResult.getConveyResult("400", e.getMessage());
		}
		User user = ContextHepler.getCurrentUser();
		JsonResult saveReturnImageInfo = null;
		try {
			saveReturnImageInfo = transportOrderService.saveReturnImageInfo(user.getAccount(),photoinfo);
		} catch (TransactionTimedOutException e) {
			return JsonResult.getConveyResult("400", "请求超时");
		} catch (Exception e) {
			return JsonResult.getConveyResult("400", e.getMessage());
		}
		return saveReturnImageInfo;
	}
	
	/**
	 * 校验回单图片是否存在运单
	 * @param reorderConfigure
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("checkReorder")
	@ResponseBody
	public JsonResult checkReorder(@RequestBody List<PhotoVo> photoVolist) throws IOException {
		return transportOrderService.checkReorder(photoVolist);
	}
	
	/**
	 * 查看回单图片
	 * @param reorderConfigure
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("selectReceipt")
	@ResponseBody
	public JsonResult selectReceipt(HttpServletRequest request) throws IOException {
		String ydbhid = request.getParameter("ydbhid");
		if (StringUtils.isEmpty(ydbhid)) {
			return JsonResult.getConveyResult("400", "运单号为空");
		}
		return transportOrderService.selectReceipt(ydbhid);
	}
}
