package com.ycgwl.kylin.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
  * @Description: 配置文件读取工具类
  * @author <a href="mailto:108252@ycgwl.com">wyj</a>
  * @date 2017年9月25日 下午3:04:40
  * @version 需求对应版本号
  *
 */
public class SystemUtils {
	
	public static final String SEPARATOR = "/"; 

	private static final Map<String, String> properties = new ConcurrentHashMap<String, String>();
	
	/**
	  * @Description: 新增配置项
	  * @param key
	  * @param value
	  * @exception
	 */
	public static void put(String key,String value){
		properties.put(key, value);
	}
	
	/**
	  * @Description: 获取某配置项
	  * @param key
	  * @return
	  * @exception
	 */
	public static String get(String key){
		return properties.get(key);
	}
}
