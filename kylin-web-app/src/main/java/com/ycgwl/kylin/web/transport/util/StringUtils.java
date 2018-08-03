package com.ycgwl.kylin.web.transport.util;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * <p>
 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2017-11-16 17:10:23
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {

	public static final String EMPTY = "";
	public static final String EMPTY_NULL = "null";


	private static final ToStringStyle STRING_STYLE = new StringStyle();

	/**
	 * 对象toString的方式
	 * <p>
	 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2017-11-16 17:10:37
	 */
	private static final class StringStyle extends ToStringStyle {

		private static final long serialVersionUID = 1L;

		public StringStyle() {
			super();
			this.setUseClassName(true);
			this.setUseShortClassName(true);
		}

		@Override
		public void append(StringBuffer buffer, String fieldName, Object value, Boolean fullDetail) {
			if (value != null) {
				appendFieldStart(buffer, fieldName);
				appendInternal(buffer, fieldName, value, isFullDetail(fullDetail));
				appendFieldEnd(buffer, fieldName);
			}
		}

		@Override
		protected void appendDetail(StringBuffer buffer, String fieldName, Object value) {
			if (value instanceof java.util.Date) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				buffer.append(simpleDateFormat.format(value));
			} /*else if (value instanceof Calendar) {
				buffer.append(DateFormatUtils.format((Calendar) value));
			} else */{
				buffer.append(value);
			}
		}

        private Object readResolve() {
            return StringUtils.STRING_STYLE;
        }
	}

	public static boolean isNotBlank(String str) {
		if("null".equals(str) || "NULL".equals(str)){
			return false;
		}
		return !org.apache.commons.lang.StringUtils.isBlank(str);
	}


	public static boolean equals(String source, String target){
		if(source != null){
			return source.equals(target);
		}
		if(target != null){
			return target.equals(source);
		}
		return true;
	}

	/**
	 * toString对象
	 * <p>
	 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2017-11-16 17:11:01
	 * @param object
	 * @return
	 */
	public static String toString(Object object) {
		if(object != null) {
			try {
				return ToStringBuilder.reflectionToString(object, STRING_STYLE);
			} catch (Exception e) {
				return object.toString();
			}
		}
		return null;
	}

	/**
	 * UUID字符串，去掉'-'
	 * <p>
	 * @developer Create by <a href="mailto:110686@ycgwl.com">dingxf</a> at 2017-11-16 17:11:12
	 * @return
	 */
	public static final String UUID() {
		return java.util.UUID.randomUUID().toString().replaceAll("-", "");
	}

	 /**
	 * 判断字符串是null.
	 * @param str 传进来的字符串
	 * @return boolean
	 */
    public static boolean isNull(String str) {
        return (null == str) || "".equals(str.trim()) || Pattern.matches("^[ ]+$", str);
    }
    public static Integer[] integerArray(String source) {
    	if(isNotBlank(source)) {
    		String[] arrays = source.split("[^0-9]+");
        	if(arrays != null && arrays.length > 0) {
        		Integer[] integers = new Integer[arrays.length];
        		for (int i = 0; i < arrays.length; i++) {
        			integers[i] = Integer.parseInt(arrays[i]);
    			}
        		return integers;
        	}
    	}
    	return null;
    }
	public static Collection<Integer> integerCollection(String source) {
		if(isNotBlank(source)) {
			String[] arrays = source.split("[^0-9]+");
			if(arrays != null && arrays.length > 0) {
				Collection<Integer> collection = new ArrayList<Integer>(arrays.length);
				for (int i = 0; i < arrays.length; i++) {
					if(null != arrays[i] && !"".equals(arrays[i])){
						collection.add(Integer.parseInt(arrays[i]));
					}
				}
				return collection;
			}
		}
		return null;
	}

	public static Long[] longArray(String source) {
		if(isNotBlank(source)) {
			String[] arrays = source.split("[^0-9]+");
			if(arrays != null && arrays.length > 0) {
				Long[] longs = new Long[arrays.length];
				for (int i = 0; i < arrays.length; i++) {
					if (null != arrays[i] && !"".equals(arrays[i])) {
						longs[i] = Long.parseLong(arrays[i]);
					}
				}
				return longs;
			}
		}
		return null;
	}
	public static Collection<Long> longCollection(String source) {
		if(isNotBlank(source)) {
			String[] arrays = source.split("[^0-9]+");
			if(arrays != null && arrays.length > 0) {
				Collection<Long> collection = new ArrayList<Long>(arrays.length);
				for (int i = 0; i < arrays.length; i++) {
					collection.add(Long.parseLong(arrays[i]));
				}
				return collection;
			}
		}
		return null;
	}

	public static boolean isEmptyNull(String src){
		return EMPTY_NULL.equalsIgnoreCase(src);
	}


	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
			//多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if(index != -1){
				return ip.substring(0,index);
			}else{
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
			return ip;
		}
		return request.getRemoteAddr();
	}
}
