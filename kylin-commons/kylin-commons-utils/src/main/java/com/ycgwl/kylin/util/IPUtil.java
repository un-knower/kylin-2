package com.ycgwl.kylin.util;

import org.apache.commons.lang.StringUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class IPUtil {
	
	private static String Local_IP;
	
	/**
	 * 获得主机IP
	 *
	 * @return String
	 */
	public static boolean isWindowsOS() {
		boolean isWindowsOS = false;
		String osName = System.getProperty("os.name");
		if(StringUtils.isNotBlank(osName)){
			if (osName.toLowerCase().indexOf("windows") > -1) {
				isWindowsOS = true;
			}
		}
		return isWindowsOS;
	}

	/**
	 * 获取本机ip地址，并自动区分Windows还是linux操作系统
	 * @return String
	 */
	public static String getLocalIP() {
		if (Local_IP == null || Local_IP.length() <= 0) {
			synchronized (IPUtil.class) {
				InetAddress address = getInetAddress();
				if(address != null){
					Local_IP = address.getHostAddress();
					if(Local_IP == null || Local_IP.length() <= 0){
						String[] items = address.toString().split("/");
						if(items.length > 1){
							Local_IP = items[1];
						}
					}
				}
			}
		}
		return Local_IP;
	}
	
	public static InetAddress getInetAddress() {
		try {
			if (isWindowsOS()) {// 如果是Windows操作系统
				return InetAddress.getLocalHost();
			} else {// 如果是Linux操作系统
				Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
				while (netInterfaces.hasMoreElements()) {
					NetworkInterface ni = netInterfaces.nextElement();
					// 遍历所有ip,特定情况，可以考虑用ni.getName判断
					if (ni.getName().contains("et") || ni.getName().contains("en")) {//ni.getName().contains("eth") || ni.getName().contains("enp")
						Enumeration<InetAddress> ips = ni.getInetAddresses();
						while (ips.hasMoreElements()) {
							InetAddress address = (InetAddress) ips.nextElement();
							if (address.isSiteLocalAddress() && !address.isLoopbackAddress() // 127.开头的都是lookback地址
									&& address.getHostAddress().indexOf(":") == -1) {
								return address;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
