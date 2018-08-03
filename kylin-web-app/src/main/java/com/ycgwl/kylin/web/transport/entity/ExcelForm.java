package com.ycgwl.kylin.web.transport.entity;

import java.util.List;

/**
 * resList
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月5日
 */
public class ExcelForm{
	
	/**
	 * 导出的list内容.
	 */
	private List<String[]> list;
	
	/**
	 * excel文件名称.
	 */
	private String fileName;
	
	/**
	 * sheet名称.
	 */
	private String sheetName;
	
	/**
	 * 第一行统计内容.
	 */
	private String totalSumContent;
	
	/**
	 * 第一行标题
	 * 
	 */
	
	private String total;
	
	/**
	 * 第二行标题
	 */
	private String implParam;
	

	public String getImplParam() {
		return implParam;
	}

	public void setImplParam(String implParam) {
		this.implParam = implParam;
	}

	/**
	 * excel表单第一行列名称.
	 */
	private String showColumnName[];
	
	/**
	 * 每列的宽度.
	 */
	private Integer showColumnWidth[];
	
	public String getTotalSumContent() {
		return totalSumContent;
	}

	public void setTotalSumContent(String totalSumContent) {
		this.totalSumContent = totalSumContent;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String[] getShowColumnName() {
		return showColumnName;
	}

	public void setShowColumnName(String[] showColumnName) {
		this.showColumnName = showColumnName;
	}

	public Integer[] getShowColumnWidth() {
		return showColumnWidth;
	}

	public void setShowColumnWidth(Integer[] showColumnWidth) {
		this.showColumnWidth = showColumnWidth;
	}

	public List<String[]> getList() {
		return list;
	}

	public void setList(List<String[]> list) {
		this.list = list;
	}
	
	
	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}


}
