package com.ycgwl.kylin.util.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;


/**
 * 操作json的工具类
 * <p>
 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017年11月29日
 */
public class JsonUtils {

	/**
	 * 将字符串转化成list<map>集合
	 * <p>
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2017年11月29日
	 * @param str
	 * @return
	 */
	public static List<JSONObject> converList(String str){
		str = str.replaceAll("=", ":");
		JSONArray parseArray = JSONArray.parseArray(str);
		return parseArray.toJavaList(JSONObject.class);
	}
	public static JSONObject converMap(String str){
		str = str.replaceAll("=", ":");
		return  JSONObject.parseObject(str);
	}

	public static String BeanToJsonObject(Object o) {
		return JSONObject.toJSONString(o);
	}
	public static String BeanToJsonArray(Object o) {
		return JSONArray.toJSONString(o);
	}

}
