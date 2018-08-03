package com.ycgwl.kylin.transport.entity.adjunct;

import com.ycgwl.kylin.entity.BaseEntity;

public class PageAble extends BaseEntity{

	private static final long serialVersionUID = -7330219561494818841L;

	
	private int pageNums; 
	
	private int pageSizes;
	
	private int rowNumberStart;
	
	private int rowNumberEnd;

	public int getPageNums() {
		return pageNums;
	}

	public void setPageNums(int pageNums) {
		this.pageNums = pageNums;
	}

	public int getPageSizes() {
		return pageSizes;
	}

	public void setPageSizes(int pageSizes) {
		this.pageSizes = pageSizes;
	}

	public int getRowNumberStart() {
		rowNumberStart = 0;
		if(pageNums == 0 && pageSizes > 0){
			rowNumberStart =  0;
		}
		if(pageNums > 0 && pageSizes > 0){
			rowNumberStart =  (pageNums - 1) * pageSizes;
		}
		return rowNumberStart;
	}

	public void setRowNumberStart(int rowNumberStart) {
		this.rowNumberStart = rowNumberStart;
	}

	public int getRowNumberEnd() {
		rowNumberEnd = 0;
		if(pageNums == 0 && pageSizes > 0){
			rowNumberEnd =  pageSizes;
		}
		if(pageNums > 0 && pageSizes > 0){
			rowNumberEnd =  rowNumberStart + pageSizes;
		}
		return rowNumberEnd;
	}

	public void setRowNumberEnd(int rowNumberEnd) {
		this.rowNumberEnd = rowNumberEnd;
	}
	
}
