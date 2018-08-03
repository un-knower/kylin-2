package com.ycgwl.kylin.transport.vo;

import com.ycgwl.kylin.SystemKey;
import com.ycgwl.kylin.util.SystemUtils;

import java.io.Serializable;

public class PhotoUrlAndNameVo implements Serializable {

	private static final long serialVersionUID = -8175236481081649373L;
	
	public String imgAddress;  
	public String imgName;
	public String getImgAddress() {
		if (imgAddress != null) {
			return SystemUtils.get(SystemKey.FILE_SERVER_URL) + imgAddress;
		}
		return imgAddress;
	}
	public void setImgAddress(String imgAddress) {
		this.imgAddress = imgAddress;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

}
