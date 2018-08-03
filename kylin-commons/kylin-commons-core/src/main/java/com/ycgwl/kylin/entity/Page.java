package com.ycgwl.kylin.entity;

import java.util.ArrayList;
import java.util.Collection;

public class Page<T> extends ArrayList<T> {

	private static final long serialVersionUID = -7655364611936344461L;

    private int pageNum;
    private int pageSize;
    private int startRow;
    private int endRow;
    private long total;
    private int pages;

    
	public Page() {
		super();
	}

	public Page(int pageNum, int pageSize, int startRow, int endRow, long total, int pages, Collection<T> collection) {
		super(collection);
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.startRow = startRow;
		this.endRow = endRow;
		this.total = total;
		this.pages = pages;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}
}
