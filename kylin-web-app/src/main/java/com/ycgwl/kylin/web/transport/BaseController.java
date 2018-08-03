package com.ycgwl.kylin.web.transport;

import com.alibaba.dubbo.rpc.RpcException;
import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.security.client.ContextHepler;
import com.ycgwl.kylin.security.entity.User;
import com.ycgwl.kylin.web.transport.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class BaseController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected static final String DATE_FORMAT = "yyyy-MM-dd";
/*
	@InitBinder
	protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	*/
	
	protected void exception(Exception ex, Model model) {
		if(ex instanceof BusinessException){
			model.addAttribute(MessageUtil.ERROR_MESSAGE, ex.getMessage());
			logger.error("请求业务处理异常", ex);
		}else if(ex instanceof ParameterException){
			model.addAttribute(MessageUtil.WARNING_MESSAGE, ((ParameterException) ex).getMessage());
			logger.warn("请求参数异常 ：{}", ex.getMessage());
		}else if(ex instanceof RpcException){
			model.addAttribute(MessageUtil.ERROR_MESSAGE, "服务调用异常");
			logger.error("请求服务调用异常", ex);
		}else{
			model.addAttribute(MessageUtil.ERROR_MESSAGE, "服务器异常");
			logger.error("请求服务器异常", ex);
		}
	}
	
	public JsonResult getJsonResult(){
		return new JsonResult();
	}
	
	public JsonResult putJsonResult(JsonResult rest, String resultCode , String reason , Object message){
		JsonResult jsonResult = rest;
		if (null == jsonResult){
			jsonResult = this.getJsonResult();
		}
		jsonResult.put("resultCode", resultCode);
		jsonResult.put("reason", reason);
		jsonResult.put("message", message);
		return jsonResult;
	}
	
	/** 
     * 获取当前网络ip 
     * @param request 
     * @return 
     */  
    public String getIpAddr(HttpServletRequest request){  
        String ipAddress = request.getHeader("x-forwarded-for");  
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
                ipAddress = request.getHeader("Proxy-Client-IP");  
            }  
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
                ipAddress = request.getHeader("WL-Proxy-Client-IP");  
            }  
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
                ipAddress = request.getRemoteAddr();  
                if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){  
                    //根据网卡取本机配置的IP  
                    InetAddress inet=null;  
                    try {  
                        inet = InetAddress.getLocalHost();  
                    } catch (UnknownHostException e) {  
                        e.printStackTrace();  
                    }  
                    ipAddress= inet.getHostAddress();  
                }  
            }  
            //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
            if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15  
                if(ipAddress.indexOf(",")>0){  
                    ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));  
                }  
            }  
            return ipAddress;   
    }

    public User getCurrentUser(){
    	return  ContextHepler.getCurrentUser();
	}
}
