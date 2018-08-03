package com.ycgwl.kylin.util;

import com.alibaba.fastjson.JSONObject;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.SystemKey;
import com.ycgwl.kylin.util.http.HttpClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
  * @Description: 短信工具类
  * 	使用的是快运.net的短信接口,接口详情请询问:向华荣
  * @author <a href="mailto:工号108252">wyj</a>
  * @date 2017年9月22日 上午9:32:57
  * @version 需求对应版本号
  *
 */
public class SmsUtils {
	
	//远成的电话，用于发送短信给客户的同时发送给员工自己
	public static final String YC_TEL = "18984310645";
	
	private static Logger logger = LoggerFactory.getLogger(SmsUtils.class);
	
	
//	private static final  String URL = "http://172.16.36.49:8087";
//	private static final  String USERNAME = "YCYSZX";
//	private static final  String PASSWORD = "YuanChengYszxSms";
	

	private static final  String URL = "http://sms.ycgwl.com:8088";
	private static final  String USERNAME = "YCTMS";
	private static final  String PASSWORD = "YuanChengTmsSms";
	
	/**
	  * @Description: 获取口令
	  * @return
	  * @exception
	 */
	private static String getToken(){
		HttpClient httpClient = new HttpClient();
//		http://172.16.36.49:8087/login
//		httpClient.setUrlString(SystemUtils.get(SystemKey.PREFIXURL)+"/login");
		httpClient.setUrlString(URL + "/login");
		httpClient.setJson(true);
		httpClient.parameter("userName", USERNAME);
		httpClient.parameter("passWord",PASSWORD);
		httpClient.parameter("roles", SystemUtils.get(SystemKey.ROLES));
		try{
			String json = httpClient.json();
			if(StringUtils.isNotBlank(json)){
				JSONObject jsonObject = JSONObject.parseObject(json);
				if(jsonObject.containsKey("loginToken")){
					return jsonObject.getString("loginToken");
				}
			}
		}catch(Exception e){
			logger.error("发送短信时出现异常: " + e);
		}
		throw new BusinessException("获取口令失败");
	}
	/**
	  * @Description: 发送短信
	  * @param phone  要发送
	  * @param content
	  * @exception
	 */
	public static JSONObject sendMessage(String phone,String content){
		String token = getToken();
		if(StringUtils.isNotBlank(token)){
			HttpClient httpClient = new HttpClient();
			httpClient.setUrlString(URL+ "/SMS/SingeSendMessage");
			httpClient.setJson(true);
			httpClient.property("auth-token", token);
			httpClient.parameter("mobile", phone);
			httpClient.parameter("smsType", 6);
			httpClient.parameter("content", content);
			JSONObject jsonObject = null;
			try{
				String json = httpClient.json();
				logger.debug("发送短信请求信息 {}", httpClient);
				jsonObject = JSONObject.parseObject(json);
			}catch(Exception e){
				logger.error("短信发送失败,{} {}",phone,content,e);
			}
			return jsonObject;
		}else{
			throw new BusinessException("获取口令失败");
		}
	}
	public static void main(String[] args) {
//		System.out.println(getToken());
		sendMessage("18067909814", "短信接口调试通了没有????");
	}
	
}
