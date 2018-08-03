package com.ycgwl.kylin.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;

public class UtilHelper {
	
	private static volatile ObjectMapper defaultObjectMapper;
	
	public synchronized static ObjectMapper getdefaultObjectMapper() {
		if(defaultObjectMapper == null) 
			defaultObjectMapper = new ObjectMapper();
		return defaultObjectMapper;
	}
	
	public static ObjectMapper getObjectMapperWithDateFormat(String format) {
		return new ObjectMapper().setDateFormat(new SimpleDateFormat(format));
	}
	
}
