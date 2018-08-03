package com.ycgwl.kylin.util.logback;

import ch.qos.logback.classic.PatternLayout;
import com.ycgwl.kylin.util.logback.pattern.HostConverter;


public class FrameworkPatternLayout extends PatternLayout {

	static{
		defaultConverterMap.put("host", HostConverter.class.getName());
	}
}
