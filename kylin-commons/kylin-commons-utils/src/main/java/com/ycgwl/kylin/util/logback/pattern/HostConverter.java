package com.ycgwl.kylin.util.logback.pattern;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.ycgwl.kylin.util.IPUtil;

public class HostConverter extends ClassicConverter {

	@Override
	public String convert(ILoggingEvent event) {
		return IPUtil.getLocalIP();
	}
}
