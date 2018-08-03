package com.ycgwl.kylin.web.transport.util;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class DateRangeUtil {
	/*正则表达式，匹配日期时间 yyyy-MM-dd HH:mm*/
	public static final String FORMATSTRSEC = "yyyy-MM-dd";
	public static final String FORMATSTRSEC2= "MM-dd";
	public static final String FORMATSTRSECDETAIL = "MM/dd-HH:mm";
	public static final String FORMATSTRSECEXCEL = "yyyy/MM/dd";
	public static SimpleDateFormat sdf = new SimpleDateFormat(FORMATSTRSEC2);
	public static SimpleDateFormat sdf_2 = new SimpleDateFormat(FORMATSTRSEC);
	public static SimpleDateFormat sdf_3 = new SimpleDateFormat(FORMATSTRSECDETAIL);
	public static SimpleDateFormat sdf_4 = new SimpleDateFormat(FORMATSTRSECEXCEL);
	
	private static final Pattern DATE_REGX = Pattern.compile("((?:19|20)\\d\\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])( ([01][0-9]|2[0-3]):[0-5][0-9])?"); 
	
	public static boolean validate(String dateString){
		return DATE_REGX.matcher(dateString).matches();
	}
	
	public static String[] finds(String dateString, int count){
		if(StringUtils.isNotBlank(dateString)){
			String[] array = new String[count];
			Matcher matcher = DATE_REGX.matcher(dateString);
			int i = 0;
			while (matcher.find() && i < count) {
				array[i++] = matcher.group();
			}
			return array;
		}
		return ArrayUtils.EMPTY_STRING_ARRAY;
	}
	
	public static List<String> finds(String dateString){
		if(StringUtils.isNotBlank(dateString)){
			List<String> list = new ArrayList<String>();
			Matcher matcher = DATE_REGX.matcher(dateString);
			while (matcher.find()) {
				list.add(matcher.group());
			}
			return list;
		}
		return Collections.emptyList();
	}
	
}
