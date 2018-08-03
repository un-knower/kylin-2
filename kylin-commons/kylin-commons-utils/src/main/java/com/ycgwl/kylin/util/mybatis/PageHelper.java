package com.ycgwl.kylin.util.mybatis;

import com.ycgwl.kylin.entity.Page;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.List;

public class PageHelper extends com.github.pagehelper.PageHelper {

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object afterPage(List pageList, Object parameterObject, RowBounds rowBounds) {
		Object object = super.afterPage(pageList, parameterObject, rowBounds);
		if(com.github.pagehelper.Page.class.isAssignableFrom(object.getClass())){
			com.github.pagehelper.Page<?> page = (com.github.pagehelper.Page<?>) object;
			return new Page(page.getPageNum(), page.getPageSize(), page.getStartRow(), page.getEndRow(), page.getTotal(), page.getPages(), new ArrayList<Object>(page));
		}
		return object;
	}

}
