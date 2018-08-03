package com.ycgwl.kylin.transport.util;

public abstract class Constant {

	public static final String WAIXIAN = "waixian";
	public static final String WAICHE = "waiche";
	public static final String ZIYOUCHE = "ziyouche";
	
	public static final String ZHENGCHE = "zhengche";
	public static final String LINGDAN = "lingdan";
	
	
	//获取customer的id
	public static synchronized String getId(int maxId)
	  
	  {
		int customerId=maxId+1;
	    String defaultvaule = "000000";
	    String str =defaultvaule+customerId;
	    String id = str.substring(5, str.length());
	 
	    return id.toString();
	  }
	
	   //获取ReportConfig的report_id
		public static synchronized int getReportId(int maxId)
		  
		  {
			int reportId=maxId+1;
			return reportId;

		  }
	
}
