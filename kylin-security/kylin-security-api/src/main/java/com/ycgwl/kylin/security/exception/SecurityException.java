package com.ycgwl.kylin.security.exception;

public class SecurityException extends RuntimeException {
	
	private static final long serialVersionUID = -4001905418317069664L;
	
	public SecurityException(String msg, Throwable t) {
		super(msg, t);
	}
	public SecurityException(String msg) {
		super(msg);
	}
}
