package com.ycgwl.kylin.security.persistent;

import com.ycgwl.kylin.security.entity.KylinGongSiEntity;

public interface KylinGongSiMapper extends BaseMapper<KylinGongSiEntity> {
	/**
	 * 根据页面的公司名称查新公司的编码
	 * @param companyName
	 * @return
	 */
	KylinGongSiEntity queryByName(String companyName);
	

}
